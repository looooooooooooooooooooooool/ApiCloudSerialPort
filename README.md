# ApiCloud-Plugin-SerialPort

**使用此插件必须root**

基于 [xmaihh/Android-Serialport](https://github.com/xmaihh/Android-Serialport) 开发的 ApiCloud 插件，Android平台上的usb串口通信插件，支持串口号、波特率、数据位、校验位、停止位、流控等参数设置，能够控制数据的收发。  


## apicloud

将 FvvSerialPort.zip 上传到自定义模块，打包自定义loader即可体验。

参考文档 [ApiCloud上传自定义模块](https://docs.apicloud.com/Module-Dev/Upload-custom-module)


### 使用方法

设备必须root

#### 引用方式
```javascript
var serialPort = api.require("FvvSerialPort");
```

#### API

//获取设备信息  
**[getAllDeviceList(callback)](#getAllDeviceList)**  
**[getAllDeviceListSync()](#getAllDeviceListSync)**  
**[getAllDevicePath(callback)](#getAllDevicePath)**  
**[getAllDevicePathSync()](#getAllDevicePathSync)**  

//设置串口连接信息  
**[setBaudRate(baudRate)](#setBaudRate)**  
**[setStopBits(stopBits)](#setStopBits)**  
**[setDataBits(dataBits)](#setDataBits)**  
**[setParity(parity)](#setParity)**  
**[setFlowCon(flowCon)](#setFlowCon)**   

//串口操作  
**[open(string)](#open)**  
**[close()](#close)**  
**[isOpen()](#isOpen)**  

//收发消息  
**[ApiCloud监听消息](#listen)**  
**[send(string)](#send)**  
**[setReceiveType(string)](#setReceiveType)**  
**[setSendType(string)](#setSendType)**  

------------

**<span id="getAllDeviceList">获取所有设备列表 - 异步</span>**  
**<font color=#ef5656 >getAllDeviceList(callback)</font>**  

参数说明  

| 参数 | 类型 | 必填 | 说明 |
| :------------: | :------------: | :------------: | :------------:|
| callback | function | 否 | 回调函数 |

callback(arr)

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| ret.list | array | 设备列表 |

示例
```javascript
serialPort.getAllDeviceList(function(res){
	console.log(res.list) //设备列表
})
```  



  **<span id="getAllDeviceListSync">获取所有设备列表 - 同步</span>**  
**<font color=#ef5656 >getAllDeviceListSync()</font>**  

返回值 object

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| ret.list | array | 设备列表 |

示例
```javascript
var list = serialPort.getAllDeviceList().list
console.log(list)
```  



**<span id="getAllDevicePath">获取所有设备路径 - 异步</span>**   
**<font color=#ef5656 >getAllDevicePath(callback)</font>**  


参数说明

| 参数 | 类型 | 必填 | 说明 |
| :------------: | :------------: | :------------: | :------------:|
| callback | function | 否 | 回调函数 |

callback(ret)

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| ret.list | array | 路径列表 |

示例
```javascript
serialPort.getAllDevicePath(function(res){
	console.log(res.list) //路径列表
})
```  
  **<span id="getAllDevicePathSync">获取所有设备路径 - 同步</span>**  
**<font color=#ef5656 >getAllDevicePathSync()</font>**  

返回值 object

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| ret.list | array | 路径列表 |

示例
```javascript
var list = serialPort.getAllDevicePathSync().list
console.log(list)
```   

**<span id="setBaudRate">设置波特率</span>**  
**<font color=#ef5656 >setBaudRate(baudRate)</font>**  

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| baudRate | int | 设置波特率，默认9600


示例
```javascript
serialPort.setBaudRate(115200)
```

**<span id="setStopBits">设置停止位</span>**  
**<font color=#ef5656 >setStopBits(stopBits)</font>**  

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| stopBits | int | 设置停止位 默认值为1 |


示例
```javascript
serialPort.setStopBits(1)
```

**<span id="setDataBits">设置数据位</span>**  
**<font color=#ef5656 >setDataBits(dataBits)</font>**  

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| dataBits | int | 设置数据位 默认值为8 |


示例
```javascript
serialPort.setDataBits(8)
```

**<span id="setParity">设置检验位</span>**  
**<font color=#ef5656 >setParity(parity)</font>**  

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| parity | int | 设置检验位 默认值为0无校验，1奇校验，2偶校验 |


示例
```javascript
serialPort.setParity(0)
```  
**<span id="setFlowCon">设置流控</span>**  
**<font color=#ef5656 >setFlowCon(flowCon)</font>**  

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| flowCon | int | 设置流控 默认值为0，1硬件，2软件|


示例
```javascript
serialPort.setFlowCon(0)
```

**<span id="open">打开串口</span>**  
**<font color=#ef5656 >open(path)</font>**  

参数说明  

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| path | string | 打开串口的路径 |

返回值

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| bool | bool | null打开失败，true打开成功 |

示例
```javascript
var open = serialPort.open("/dev/ttyS1")
console.log(open)
```

**<span id="close">关闭当前串口</span>**  
**<font color=#ef5656 >close()</font>**  


示例
```javascript
serialPort.close()
```

**<span id="isOpen">获取打开状态</span>**  
**<font color=#ef5656 >isOpen()</font>**  

返回值

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| isOpen | bool | 当前打开状态 |

示例
```javascript
var isOpen = serialPort.isOpen()
```
**<span id="listen">监听消息</span>**  
**<font color=#ef5656 >使用 apicloud 的 api.addEventListener 进行监听</font>**


示例
```javascript
api.addEventListener({
	name:"FvvSerialPort" //必须监听 FvvSerialPort
}, function(ret){
	var msg = ret.value  //模块发送过来的消息
})
```   


**<span id="sendBytes">发送消息</span>**  
**<font color=#ef5656 >send(string)</font>**

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| string | string | 发送的消息 |


示例
```javascript
serialPort.send("hello fvv")
```   

**<span id="setReceiveType">设置接收消息类型</span>**  
**<font color=#ef5656 >setReceiveType(string)</font>**

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| string | string | 接收类型，默认ASCII，HEX，BYTE |


示例
```javascript
serialPort.setReceiveType("HEX")
```   

**<span id="setSendType">设置发送消息类型</span>**  
**<font color=#ef5656 >setSendType(string)</font>**

参数说明

| 参数 | 类型 | 说明 |
| :------------: | :------------: | :------------: |
| string | string | 发送消息类型，默认ASCII，HEX，BYTE |


示例
```javascript
serialPort.setSendType("HEX")
```   


## android

已集成离线打包及插件开发环境，可以使用离线打包来调试插件和ApiCloud项目。  

使用android studio导入此工程，run 'app' 即可体验！  

参考文档 [ApiCloud模块开发指南](https://docs.apicloud.com/Module-Dev/module-dev-guide-for-android-studio)    
