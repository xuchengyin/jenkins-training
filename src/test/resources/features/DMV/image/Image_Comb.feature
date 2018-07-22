@DMV
@delDiagram
Feature: DMV_绘图_组合绘图
   @Smoke@Debug@delDiagramComb
   Scenario Outline: Comb_组合视图
	   When 创建多个视图名称为"视图1,视图2,视图3",视图描述为"新建视图"的视图
	   Then 系统中存在多个视图名称为"视图1,视图2,视图3",视图描述为"新建视图"的视图
	   When 给视图"视图1"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"视图1"增加常用图标"DB"坐标为"200""300"
	   When 给视图"视图2"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"视图2"增加常用图标"DB"坐标为"200""300"
	   When 给视图"视图3"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"视图3"增加常用图标"DB"坐标为"200""300"
	   When 根据多个视图"视图1,视图2,视图3"创建名称为"<name>",描述为"<diagramDesc>",组合行为"<combRows>",组合列为"<combCols>"的组合视图
	   Then 成功创建名称为"<name>",描述为"<diagramDesc>",组合行为"<combRows>",组合列为"<combCols>"的组合视图
	   When 删除多个视图"视图1,视图2,视图3"
	   Then 成功删除多个视图"视图1,视图2,视图3"
	   When 删除名称为"<name>"的视图
	   Then 成功删除名称为"<name>"的视图

    Examples:
	 | common  | name        |diagramDesc|combRows|combCols|
     | 中英文数    |组合视图er342| 视图描述     |3|3|
     |特殊字符     |组合视图名%￥#@！|视图描述32werf&^%$#|2|2|
     |最大长度    |组合视图名3e视图名视图名3e视图名ytrr|视图描述|1|2|
     |组合视图名称最大长度汉字50|视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图|视图描述|2|3|
     |组合视图描述最大长度汉字100|组合视图名称|视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述|1|3|
     
   @delDiagramComb  
   Scenario Outline: Comb_搜索视图
	   When 创建多个视图名称为"搜索视图er1,视图%#$,搜索视图3",视图描述为"新建视图"的视图
	   Then 系统中存在多个视图名称为"搜索视图er1,视图%#$,搜索视图3",视图描述为"新建视图"的视图
	   When 给视图"搜索视图er1"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"搜索视图er1"增加常用图标"DB"坐标为"200""300"
	   When 给视图"视图%#$"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"视图%#$"增加常用图标"DB"坐标为"200""300"
	   When 给视图"搜索视图3"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"搜索视图3"增加常用图标"DB"坐标为"200""300"
       When 在组合视图中搜索关键字为"<searchKey>"的单个视图
       Then 在组合视图中成功搜索关键字为"<searchKey>"的单个视图
	   When 删除多个视图"搜索视图er1,视图%#$,搜索视图3"
	   Then 成功删除多个视图"搜索视图er1,视图%#$,搜索视图3"

    Examples:
	 | common    | searchKey |
     | 视图名全匹配   |搜索视图er1    |
     |视图名部分匹配 |视图3       |
     |视图名匹配符    |%#$         |
     |空搜索              |        |