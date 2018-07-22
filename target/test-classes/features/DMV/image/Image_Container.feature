@DMV
@Smoke
@delCiRltRule
@delDiagram
Feature: DMV_绘图_新建视图_容器画图
	@Debug 
	@cleanRlt
	Scenario Outline: Image_Container_编辑规则帅选条件
	  	Given 系统中已存在如下ci分类:"Application,s@&_-"
	 	And 系统中已存在如下关系分类:"AppRlt"
	 	When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"信用卡决策1",目标分类为"s@&_-",目标对象为"eight"关系数据
	 	Then "AppRlt"下存在只存在1条"信用卡决策1"与"eight"的关系数据
	 	When 新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
	 	Then 成功新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
	 	When 新建视图"<diagramName>",描述为"",文件夹为""
		Then 成功新建视图"<diagramName>"
	 	When 给视图"<diagramName>"增加容器坐标为"400""600"
		Then 成功给视图"<diagramName>"增加容器坐标为"400""600"
		When 给视图"<diagramName>"增加容器坐标为"200""200"
		Then 成功给视图"<diagramName>"增加容器坐标为"200""200"
		
		When 给视图"<diagramName>"中坐标为"200""200"的容器编辑规则筛选条件类名"Application"条件如下:
		|common|关键字|条件|关键值|
		|CI Code|CI Code|=|信用卡决策1|
		|name|Name|!=|信用卡决策2|
		|name|Name|like|信用卡工单|
		|Supporter|Supporter|not like|信用卡决策2|
		|Service Time|Service Time|in|7*24|
		|Important Level|Important Level|not in|信用卡决策2|
		Then 成功给视图"<diagramName>"中坐标为"200""200"的容器编辑规则筛选条件类名"Application"条件如下:
		|common|关键字|条件|关键值|
		|CI Code|CI Code|=|信用卡决策1|
		|name|Name|!=|信用卡决策2|
		|name|Name|like|信用卡工单|
		|Supporter|Supporter|not like|信用卡决策2|
		|Service Time|Service Time|in|7*24|
		|Important Level|Important Level|not in|信用卡决策2|
		
		When 给视图"<diagramName>"中坐标为"400""600"的容器编辑规则筛选条件类名"s@&_-"条件如下:
		|common|关键字|条件|关键值|
		|=|CI Code|=|eight|
	    Then 成功给视图"<diagramName>"中坐标为"400""600"的容器编辑规则筛选条件类名"s@&_-"条件如下:
		|common|关键字|条件|关键值|
		|=|CI Code|=|eight|
		
		When 给视图"<diagramName>"添加影响关系规则"<friendName>"
		Then 给视图"<diagramName>"添加影响关系规则"<friendName>"成功
		When 将视图"<diagramName>"转化为模板
        Then 视图"<diagramName>"成功转化为模板
        When 利用模板"<diagramName>"创建名称为"容器模板视图",视图描述为""的视图
	    Then 系统中存在名称为"容器模板视图",视图描述为""的视图
	    When 删除名称为"容器模板视图"的视图
	    Then 成功删除名称为"容器模板视图"的视图
	    When 删除朋友圈规则"<friendName>"
	 	Then 成功删除朋友圈规则"<friendName>"


	 	Examples:
		 | common   | diagramName|friendName|
    	 |视图|容器视图|新建朋友圈规则|
 	 
    	 
    	 
 