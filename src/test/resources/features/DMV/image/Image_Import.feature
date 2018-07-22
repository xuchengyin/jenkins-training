@DMV
@delImportDiagram
Feature: DMV_绘图_导入视图
	@Smoke@Debug  	 
	Scenario: Image_Import_导入Visio
		When 导入如下Visio视图:
		|说明|文件名|
		|visio2013黄金交易(demo)|visio2013黄金交易(demo).vsdx|
		#|多sheet生成多张视图|海关-H4A架构图(多sheet).vsdx|
		
	    Then 成功导入如下Visio视图:
		|说明|文件名|
		|visio2013黄金交易(demo)|visio2013黄金交易(demo).vsdx|
		#|多sheet生成多张视图|海关-H4A架构图(多sheet).vsdx|
		
    @Smoke
	Scenario: Image_Import_导入xml
		When 导入如下xml视图:
		|说明|文件名|
		|有CI和关系|导入xml视图.xml|
		Then 成功导入如下xml视图:
		|说明|文件名|
		|有CI和关系|导入xml视图.xml|
	@Smoke	
	Scenario: Image_Import_导入配置信息
		When 导入如下配置信息视图:
		|说明                            |文件名|
		|导入有CI和关系的文件|导入视图配置信息-有ci有关系.xls|
		|导入有CI没关系的文件|导入视图配置信息-有ci没关系.xls|
		|系统中不存在的CI和关系|导入视图配置信息-系统中不存在的CI和关系.xls|
		
		Then 成功导入如下配置信息视图:
		|说明                            |文件名|
		|ci和关系系统中都存在|导入视图配置信息-有ci有关系.xls|
		|导入有CI没关系的文件|导入视图配置信息-有ci没关系.xls|
		|系统中不存在的CI和关系|导入视图配置信息-系统中不存在的CI和关系.xls|
		
		
	@Smoke	
	Scenario: Image_Import_导入图片
	    When 导入如下图片视图:
	    |common|fileName|
		|jpg|导入图片.jpg|
		
		Then 成功导入如下图片视图:
	    |common|fileName|
		|jpg|导入图片.jpg|
	  
	 	
	 	
	
    	 
    	 
   	 


    	     	 
	 
		
	