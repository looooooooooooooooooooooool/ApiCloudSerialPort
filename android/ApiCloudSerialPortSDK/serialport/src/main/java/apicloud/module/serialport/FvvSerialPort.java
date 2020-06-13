package apicloud.module.serialport;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.uzmodule.ModuleResult;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android_serialport_api.SerialPortFinder;
import tp.xmaihh.serialport.SerialHelper;
import tp.xmaihh.serialport.bean.ComBean;
import tp.xmaihh.serialport.stick.AbsStickPackageHelper;
import tp.xmaihh.serialport.utils.ByteUtil;


public class FvvSerialPort extends UZModule {

    private SerialHelper serialHelper;
    private SerialPortFinder serialPortFinder;
    private boolean isOpen = false;

    private String receiveType = "ASCII";
    private String sendType = "ASCII";

    public FvvSerialPort(final UZWebView webView) {
        super(webView);
        serialPortFinder = new SerialPortFinder();
        serialHelper = new SerialHelper("/dev/ttyS1",9600) {
            @Override
            protected void onDataReceived(final ComBean comBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            onMessage(comBean);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        serialHelper.setStickPackageHelper(new AbsStickPackageHelper() {
            @Override
            public byte[] execute(InputStream is) {
            try {
                int available = is.available();
                if (available > 0) {
                    byte[] buffer = new byte[available];
                    int size = is.read(buffer);
                    if (size > 0) {
                        return buffer;
                    }
                } else {
                    SystemClock.sleep(50L);
                }
            } catch (IOException var5) {
                var5.printStackTrace();
            }
            return null;
            }
        });
    }

    public void jsmethod_test(final UZModuleContext uzModuleContext){
        String msg = uzModuleContext.optString("msg");
        Toast.makeText(uzModuleContext.getContext(),msg,Toast.LENGTH_LONG).show();
    }

    //获取所有设备
    public void jsmethod_getAllDeviceList(final UZModuleContext uzModuleContext)  {
        String[] list =  serialPortFinder.getAllDevices();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        uzModuleContext.success(new org.json.JSONObject(jsonObject));
    }

    //获取所有设备，同步
    public ModuleResult jsmethod_getAllDeviceListSync_sync(final UZModuleContext uzModuleContext)  {
        String[] list =  serialPortFinder.getAllDevices();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        return new ModuleResult(new org.json.JSONObject(jsonObject));
    }

    //获取所有设备路径
    public void jsmethod_getAllDevicePath(final UZModuleContext uzModuleContext) {
        String[] list =  serialPortFinder.getAllDevicesPath();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        uzModuleContext.success(new org.json.JSONObject(jsonObject));
    }

    //获取所有设备路径，同步
    public ModuleResult jsmethod_getAllDevicePathSync_sync(final UZModuleContext uzModuleContext) {
        String[] list =  serialPortFinder.getAllDevicesPath();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",list);
        return new ModuleResult(new org.json.JSONObject(jsonObject));
    }

    //设置路径
//    public void jsmethod_setPath(final UZModuleContext uzModuleContext) {
//        if(serialHelper.isOpen()){
//            serialHelper.close();
//            Log.i("opening","close");
//        }
//        serialHelper.setPort(uzModuleContext.optString("arg"));
//    }

    //设置波特率
    public void jsmethod_setBaudRate(final UZModuleContext uzModuleContext) {
        serialHelper.setBaudRate(uzModuleContext.optInt("arg"));
    }

    //设置停止位
    public void jsmethod_setStopBits(final UZModuleContext uzModuleContext) {
        serialHelper.setStopBits(uzModuleContext.optInt("arg"));
    }

    //设置数据位
    public void jsmethod_setDataBits(final UZModuleContext uzModuleContext) {
        serialHelper.setDataBits(uzModuleContext.optInt("arg"));
    }

    //设置校验位
    public void jsmethod_setParity(final UZModuleContext uzModuleContext) {
        serialHelper.setParity(uzModuleContext.optInt("arg"));
    }

    //设置流控
    public void jsmethod_setFlowCon(final UZModuleContext uzModuleContext) {
        serialHelper.setFlowCon(uzModuleContext.optInt("arg"));
    }

    //打开串口
    public ModuleResult jsmethod_open_sync(final UZModuleContext uzModuleContext) {
        if(serialHelper.isOpen()){
            serialHelper.close();
            Log.i("opening","close");
        }
        serialHelper.setPort(uzModuleContext.optString("arg"));
        try{
            serialHelper.open();
        }catch (IOException e){
            Log.i("open fail",e.getMessage());
            return new ModuleResult(false);
        }
        return new ModuleResult(true);
    }

    //监听数据
    public void onMessage(ComBean comBean) throws UnsupportedEncodingException {
        String res = new String(comBean.bRec,"GBK");
        if(this.receiveType.toUpperCase().equals("HEX")){
            res = ByteUtil.ByteArrToHex(comBean.bRec);
        }
        if(this.receiveType.toUpperCase().equals("BYTE")){
            sendMessage("receive",new String(res.getBytes()));
            return;
        }
        sendMessage("receive",res);
    }

    //发送错误消息
    public void sendMessage(String type,String data){
        this.execScript("","","api.sendEvent({name:'FvvSerialPort',extra:{type:'"+ type +"',msg:'"+ data +"'}})");
    }

    //设置监听接收类型
    public void jsmethod_setReceiveType(final UZModuleContext uzModuleContext) {
        this.receiveType = uzModuleContext.optString("arg");
    }

    //设置消息发送类型
    public void jsmethod_setSendType(final UZModuleContext uzModuleContext) {
        this.sendType = uzModuleContext.optString("arg");
    }

    //发送消息
    public void jsmethod_send(final UZModuleContext uzModuleContext) {
        if(this.sendType.toUpperCase().equals("ASCII")){
            Log.i("test",uzModuleContext.optString("arg"));
            serialHelper.sendTxt(uzModuleContext.optString("arg"));
        }
        if(this.sendType.toUpperCase().equals("HEX")){
            serialHelper.sendHex(uzModuleContext.optString("arg"));
        }
        if(this.sendType.toUpperCase().equals("BYTE")){
            serialHelper.send(uzModuleContext.optString("arg").getBytes());
        }
    }

    //断开连接
    public void jsmethod_close(final UZModuleContext uzModuleContext) {
        serialHelper.close();
    }

    //获取打开状态
    public ModuleResult jsmethod_isOpen_sync(UZModuleContext uzModuleContext) {
        return new ModuleResult(serialHelper.isOpen());
    }


}
