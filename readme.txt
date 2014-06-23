

一、测试环境

硬件环境：
	Windows 32位
软件环境：
	Java 1.6
	Tomcat 6
	TestNG （eclipse自带插件或本地下载jar包均可）
	WebDriver（以官网最新jar包为准）
	Ant （eclipse自带插件或本地下载jar包均可）
	ReportNG 1.1.4 （测试报告插件，可选）
	
二、eclipse项目的目录结构

DCV3Test：
├─bin         编译目录
├─lib         第三方jar文件
├─.settings   eclipse配置目录，自动生成
└─src         源文件夹     
    ├─resources   参数文件xml	
    └─test
		├─appObjects  Web 页面元素定位信息，如按钮与文本框等
		├─tasks		     测试步骤中可复用的行为
		├─testcases   由 tasks 组成的测试用例
		└─utilities   公用方法，如监听器、数据解析类  

├─log          日志输出文件
├─utils		       公用组件，如iedriver
└─report       测试报告输出文件    
        

