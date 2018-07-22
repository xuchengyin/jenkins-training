@DMV
@Smoke
Feature: DMV_绘图_查询图标单接口验证


	Scenario: Image_查询图标目录
		When 查询图标目录
		Then 成功查询图标目录


	Scenario: Image_查询常用图标
		When 查询常用图标
		Then 常用图标查询成功
		
		 
	Scenario: Image_查询自定义图标
		When 查询自定义图标
		Then 自定义图标查询成功	
			
		 
	