@DMV
@delKpi
Feature: DMV_监控指标设置_Theme_Kpi
     @Smoke
     Scenario: Theme_KPI添加分类指标设置、删除分类指标设置
       Given 系统中已存在如下ci分类:"Application,Cluster"
	   When 创建含有对象组的名称为"测试指标www"，别名为"指标别名"，指标描述为"测试指标"，单位为"K"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
       Then 系统中存在含有对象组的名称为"测试指标www"，别名为"指标别名"，指标描述为"测试指标"，单位为"K"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
	   When 创建"Application"指标设置
	   Then "Application"成功创建指标设置
	   When "Application"删除指标设置
	   Then "Application"成功删除指标设置
	   When 删除名称为"测试指标www"的指标
	   Then 系统中不存在名称为"测试指标www"的指标
	 @Smoke
	 @cleanRltCls@Debug
     Scenario: Theme_KPI添加关系指标设置、删除关系指标设置
      When 创建名称为"rltTest"的关系分类
	  When 创建含有指标状态的指标，名称为"开关"，别名为"指标名称"，指标描述为"指标"，单位为"U"，分类对象组为"Application"，标签对象组为"APP"，关系对象组为"AppRlt",指标状态如下：
      | 值     | 图标目录名 | 图标名         |
      |     3 | 常用图标  | Application |
      | 数据库图标 | 常用图标  | DB          |
      Then 系统中存在含有如下指标，名称为"开关"，别名为"指标名称"，指标描述为"指标"，单位为"U"，分类对象组为"Application"，标签对象组为"APP"，关系对象组为"AppRlt"，指标状态如下：
      | 值     | 图标目录名 | 图标名         |
      |     3 | 常用图标  | Application |
      | 数据库图标 | 常用图标  | DB   |
      When 创建指标名称为"开关"关系名称为"rltTest"的关系指标设置
      Then 成功创建指标名称为"开关"关系名称为"rltTest"的关系指标设置
      When 删除名称为"rltTest"的关系分类及数据
      Then 关系分类"rltTest"分类及数据删除成功
	  When "rltTest"删除指标设置
	  Then "rltTest"成功删除指标设置
      When 删除名称为"开关"的指标
	  Then 系统中不存在名称为"开关"的指标
	  
	Scenario Outline: Theme_KPI搜索指标设置
	   Given 系统中已存在如下ci分类:"Application,Cluster"
	   When 创建含有对象组的名称为"测试指标www1122"，别名为"指标别名"，指标描述为"测试指标"，单位为"K"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
       Then 系统中存在含有对象组的名称为"测试指标www1122"，别名为"指标别名"，指标描述为"测试指标"，单位为"K"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
	   When 创建"Application"指标设置
	   When 搜索分类名称为"<className>"的指标配置
	   Then 分类名称为"<className>"的指标配置成功搜索出来
	   When "Application"删除指标设置
	   Then "Application"成功删除指标设置
	   When 删除名称为"测试指标www1122"的指标
	   Then 系统中不存在名称为"测试指标www1122"的指标
	   
	 Examples:
	  | common| className|
	  | 分类名全匹配| Application|
	  | 分类名部分匹配| App|
