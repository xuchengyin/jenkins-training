@DMV @DelDmvGroup
Feature: DMV_小组_Group

  @Smoke@Debug
  Scenario Outline: Group_小组增删改
    When 创建小组名称为"<groupName>",小组描述为"<groupDesc>"的小组
    Then 系统中存在小组名称为"<groupName>",小组描述为"<groupDesc>"的小组
    When 小组名称为"<groupName>"的小组修改小组名称为"<updateGroupName>",小组描述为"<updateGroupDesc>"
    Then 小组名称为"<updateGroupName>",小组描述为"<updateGroupDesc>"的小组修改成功
    When 删除小组名称为"<updateGroupName>"的小组
    Then 系统中不存在小组名称为"<updateGroupName>"的小组

    Examples: 
      | common        | groupName                                         | groupDesc                                                                                                                                                                                                                                                                                              | updateGroupName | updateGroupDesc |
      | 中英文数          | 小组名称er342                                         | 小组描述                                                                                                                                                                                                                                                                                                   | 修改后小组名称         | 修改后小组描述         |
      | 特殊字符          | 小组名%￥#@！                                          | 小组描述32werf&^%$#                                                                                                                                                                                                                                                                                        | 修改后小组名称231      | 修改后小组描述453      |
      | 中英文           | 小组名eeerrrrrr小组名eeerrrrrr小组名eeerr                  | 小组描述                                                                                                                                                                                                                                                                                                   | 修改后小组名称er       | 修改后小组描述uytr     |
      | 小组名称最大汉字长度50  | 小组名称小组名称小组名称小组名称小组名称小组名称小组名称小组名称小组名称小组名称小组名称组名称名称 | 小组描述                                                                                                                                                                                                                                                                                                   | 修改后小组名称er       | 修改后小组描述uytr     |
      | 小组描述最大汉字长度300 | 小组                                                |小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述组描述描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述组描述描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述组描述描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述组描述描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述组描述描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述小组描述组描述描述描述描述描述| 修改后小组名称erdd     |  修改后小组描述               |

  @Smoke @delDiagram
  Scenario: Group_查看小组
    When 新建视图"测试视图ew",描述为"视图描述tr",文件夹为"我的"
    Then 成功新建视图"测试视图ew"
    When 创建小组名称为"查看小组",小组描述为"查看小组视图描述"的小组
    Then 系统中存在小组名称为"查看小组",小组描述为"查看小组视图描述"的小组
    And 将视图"测试视图ew"分享到小组"查看小组"
    When 查看小组小组名称为"查看小组"
    Then 小组"查看小组"的视图查询出来
    When 删除名称为"测试视图ew"的视图
    Then 成功删除名称为"测试视图ew"的视图
    When 删除小组名称为"查看小组"的小组
    Then 系统中不存在小组名称为"查看小组"的小组

  @Smoke @DelUser
  Scenario: Group_转让所有权,动态日志
    When 创建小组名称为"转让小组",小组描述为"转让小组描述"的小组
    Then 系统中存在小组名称为"转让小组",小组描述为"转让小组描述"的小组
    When 创建登录名为"sunslwwww"，用户名为"sunshuangli"，所属组织为"组织"，初始密码为"123456"，电子邮箱为"test@qq.com"，联系方式为"18201119883"，描述为"用户"的用户
    Then 系统中存在登录名为"sunslwwww"，用户名为"sunshuangli"，所属组织为"组织"，初始密码为"123456"，电子邮箱为"test@qq.com"，联系方式为"18201119883"，描述为"用户"的用户
    When 小组"转让小组"转让所有权
    Then 小组"转让小组"成功转让所有权
    Then 小组"转让小组"成功记录动态日志
    When 删除小组名称为"转让小组"的小组
    Then 系统中不存在小组名称为"转让小组"的小组
    When 删除用户名为"sunslwwww"的用户
    Then 系统中不存在名称为"sunslwwww"的用户
    
   @DelDmvGroup@delDiagramComb
   Scenario: Group_小组查询视图数
     When 创建多个视图名称为"单视图111,单视图112,单视图113,单视图114,单视图115",视图描述为"新建视图"的视图
	 Then 系统中存在多个视图名称为"单视图111,单视图112,单视图113,单视图114,单视图115",视图描述为"新建视图"的视图
	 When 给视图"单视图111"增加常用图标"DB"坐标为"200""300"
	 Then 成功给视图"单视图111"增加常用图标"DB"坐标为"200""300"
	 When 给视图"单视图112"增加常用图标"DB"坐标为"200""300"
	 Then 成功给视图"单视图112"增加常用图标"DB"坐标为"200""300"
	 When 给视图"单视图113"增加常用图标"DB"坐标为"200""300"
	 Then 成功给视图"单视图113"增加常用图标"DB"坐标为"200""300"
	 When 给视图"单视图114"增加常用图标"DB"坐标为"200""300"
	 Then 成功给视图"单视图114"增加常用图标"DB"坐标为"200""300"
	 When 给视图"单视图115"增加常用图标"DB"坐标为"200""300"
	 Then 成功给视图"单视图115"增加常用图标"DB"坐标为"200""300"
	 When 创建小组名称为"测试小组1155",小组描述为"查看小组视图描述"的小组
     Then 系统中存在小组名称为"测试小组1155",小组描述为"查看小组视图描述"的小组
     When 创建小组名称为"测试小组1166",小组描述为"查看小组视图描述"的小组
     Then 系统中存在小组名称为"测试小组1166",小组描述为"查看小组视图描述"的小组
     And  将多个视图"单视图111,单视图112"分享到小组"测试小组1155"
     And  将多个视图"单视图113,单视图114,单视图115"分享到小组"测试小组1166"
     When 小组查询视图数
     Then 小组成功查询视图数
     When 删除多个视图"单视图111,单视图112,单视图113,单视图114,单视图115"
	 Then 成功删除多个视图"单视图111,单视图112,单视图113,单视图114,单视图115"
	 When 删除小组名称为"测试小组1155"的小组
     Then 系统中不存在小组名称为"测试小组1155"的小组
     When 删除小组名称为"测试小组1166"的小组
     Then 系统中不存在小组名称为"测试小组1166"的小组
      