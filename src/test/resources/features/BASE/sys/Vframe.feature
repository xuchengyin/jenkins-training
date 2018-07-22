@BASE
Feature: BASE_读取菜单_Vframe
     
   	 
    Scenario Outline: vframe_获取菜单
     	When 查询模块编码<rootCode>授权菜单
     	#Then 成功查询模块编码<rootCode>授权菜单
     	
     	Examples:
     	|编码|rootCode|
     	|BASE|02|