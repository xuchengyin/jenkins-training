@DMV
@delDiagram
Feature: DMV_Wall_应用墙
  @CleanCiCls
  Scenario: Wall_应用墙_查询应用墙上的应用
      When 在"业务领域"目录下,创建名称为"应用"的ci分类,使用图标为"DiskArray"
   	  And 给"应用"分类添加如下属性:
	    | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
        | 应用名称     | 字符串                   |  1 |     1 |         |       1 |                  |
     
      Then "应用"分类属性更新成功 
	   	| 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
        | 应用名称     | 字符串                   |  1 |     1 |         |       1 |                  |
    
      When 给"应用"添加如下数据:
	  	|commont|应用名称|
	    |  正常  |应用ci1|
	    |  正常  |应用ci2|
	    |  正常  |应用ci3|
      Then "应用"分类数据添加成功
	   	|commont|应用名称|
	    |  正常  |应用ci1|
	    |  正常  |应用ci2|
	    |  正常  |应用ci3|
     When 查询应用墙上的应用
     Then 应用墙上的分类为"应用"属性为"应用名称"的CI被成功查询出来
     When 删除"应用"分类及数据
     Then 系统中不存在名称为"应用"的分类
     
  @CleanCiCls@delDiagramComb@Debug  
  Scenario: Wall_应用墙_根据code查询组合视图
      When 在"业务领域"目录下,创建名称为"应用123"的ci分类,使用图标为"DiskArray"
   	  And 给"应用123"分类添加如下属性:
	    | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
        | 应用名称     | 字符串                   |  1 |     1 |         |       1 |                  |
     
      Then "应用123"分类属性更新成功 
	   	| 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
        | 应用名称     | 字符串                   |  1 |     1 |         |       1 |                  |
    
      When 给"应用123"添加如下数据:
	  	|commont|应用名称|
	    |  正常  |应用ci1|
	    |  正常  |应用ci2|
	    |  正常  |应用ci3|
      Then "应用123"分类数据添加成功
	   	|commont|应用名称|
	    |  正常  |应用ci1|
	    |  正常  |应用ci2|
	    |  正常  |应用ci3|
     When 创建多个视图名称为"应用墙视图1,应用墙视图2,应用墙视图3",视图描述为"新建视图"的视图
	 Then 系统中存在多个视图名称为"应用墙视图1,应用墙视图2,应用墙视图3",视图描述为"新建视图"的视图
     When 给视图"应用墙视图1"增加CI"信用卡决策1"坐标为"400""600"
     Then 成功给视图"应用墙视图1"增加CI"信用卡决策1"坐标为"400""600"
     When 给视图"应用墙视图2"增加CI"信用卡工单1"坐标为"400""600"
     Then 成功给视图"应用墙视图2"增加CI"信用卡工单1"坐标为"400""600"
     When 给视图"应用墙视图3"增加CI"信用卡工单1"坐标为"400""600"
     Then 成功给视图"应用墙视图3"增加CI"信用卡工单1"坐标为"400""600"
     When 应用墙中根据多个视图"应用墙视图1,应用墙视图2"创建名称为"应用墙组合视图1",描述为"应用墙组合视图描述",组合行为"3",组合列为"2",关联APP为"应用ci1"的组合视图
     Then 应用墙中成功创建名称为"应用墙组合视图1",描述为"应用墙组合视图描述",组合行为"3",组合列为"2",关联APP为"应用ci1"的组合视图
     When 应用墙中根据多个视图"应用墙视图1,应用墙视图3"创建名称为"应用墙组合视图2",描述为"应用墙组合视图描述",组合行为"3",组合列为"2",关联APP为"应用ci1"的组合视图
     Then 应用墙中成功创建名称为"应用墙组合视图2",描述为"应用墙组合视图描述",组合行为"3",组合列为"2",关联APP为"应用ci1"的组合视图
     When 应用墙中根据多个视图"应用墙视图2,应用墙视图3"创建名称为"应用墙组合视图3",描述为"应用墙组合视图描述",组合行为"3",组合列为"2",关联APP为"应用ci2"的组合视图
     Then 应用墙中成功创建名称为"应用墙组合视图3",描述为"应用墙组合视图描述",组合行为"3",组合列为"2",关联APP为"应用ci2"的组合视图
     When 根据CICODE为"应用ci1"查询组合视图
     Then 根据CICODE为"应用ci1"成功查询组合视图
     When 删除多个视图"应用墙视图1,应用墙视图2,应用墙视图3,应用墙组合视图1,应用墙组合视图2,应用墙组合视图3"
	 Then 成功删除多个视图"应用墙视图1,应用墙视图2,应用墙视图3,应用墙组合视图1,应用墙组合视图2,应用墙组合视图3"
	 When 删除"应用123"分类及数据
     Then 系统中不存在名称为"应用123"的分类
     
#     Scenario Outline: Wall_应用墙_保存应用墙排序信息
#       When 给应用墙保存"<param>"排序信息
#       Then 应用墙成功保存"<param>"的排序信息
#     Examples:
#	  | common| param|
#	  | 英文| Application|
#	  | 汉字|查询|
#	  | 英汉|Appli查询 |
#	  |特殊字符|查询123%$#|