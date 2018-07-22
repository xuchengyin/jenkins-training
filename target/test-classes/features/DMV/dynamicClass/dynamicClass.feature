Feature: DMV_树状图

  @deleteTpl
  Scenario: DynamicClass_树状图_查询
    When 用以下参数创建树状图:
	|dataStatus|tplName|levelNameList|ciClassNames|
	|1|测试树状图|CI Code:整数:小数:枚举:日期|s@&_-:Application:Cluster|
	
	Then 用以下参数验证树状图创建成功:
	|dataStatus|tplName|levelNameList|ciClassNames|
	|1|测试树状图|CI Code:整数:小数:枚举:日期|s@&_-:Application:Cluster|	
	And 给角色"admin"的分类"Application"设置编辑权限
	When 查询树状图
	Then 树状图正确被查出
	And 删除角色"admin"的数据设置
	
	