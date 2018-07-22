@EMV @DelEmvGroup
Feature: EMV_团队_team

	
    Scenario Outline: team_新增成员
    	When 创建登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
    	Then 系统中存在登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
    	Examples:
      | common | opCode | opName  | custom1 | loginPasswd | emailAdress | mobileNo | notes | updateOpName | updateCustom1 | updateLoginPasswd | updateEmailAdress | updateMobileNo | updateNotes |
      | 正常数据      | yll    | 颜莉莉          | 组织      | 123456 | yanlili@uinnova.com | 15901368164 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |
      | 正常数据       | jyn  | 江一南       | 组织      | 123456 | 2871972383@qq.com | 15101652723 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |
      | 正常数据       | hb | 韩斌       | 组织      | 123456 | 2871972383@qq.com | 15101652723 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |
      | 正常数据       | pll  | 彭乐乐       | 组织      | 123456 | 2871972383@qq.com | 15101652723 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |
    
    Scenario Outline: team_团队增改删
    	When 创建团队名称为"<groupName>",团队成员为,管理范围为的团队:
    	|name|sourceId|
    	|yll |1|
    	|jyn|2|
    	Then 系统中存在团队名称为"<groupName>",管理范围为的团队
    	|name|sourceId|
    	|yll |1|
    	|jyn|2|
    	And 再次新建团队名称为"<groupName>"的团队:
    	When 团队名称为"<groupName>"修改为"<newGroupName>",团队成员修改为,管理范围修改为的团队:
    	|name|sourceId|
    	|yll |1|
    	|hb|3|
    	|pll|4|
    	Then 系统中存在团队名称为"<newGroupName>",团队成员为,管理范围为的团队
    	|name|sourceId|
    	|yll |1|
    	|hb|3|
    	|pll|4|
    	When 删除团队名称为"<newGroupName>"的团队    	
    	Then 团队管理页面不存在团队名称为"<newGroupName>"的团队
		Examples:
      | common | groupName |newGroupName|
      |正常数据，包括中文、数字、字母、符号|EMV_团队01|修改后的EMV_团队|
      |特殊字符|<b>team</b>!@#|NULL|
      |团队名称最大长度|事件管理可视化测试团队管理长度|修改后的团队长度|
      @DelUser
      Scenario Outline: team_删除成员
      When 删除用户名为"<opCode>"的用户
      Then 系统中不存在名称为"<opCode>"的用户
      Examples:
      | common | opCode | opName  | custom1 | loginPasswd | emailAdress | mobileNo | notes | updateOpName | updateCustom1 | updateLoginPasswd | updateEmailAdress | updateMobileNo | updateNotes |
      | 正常数据      | yll    | 颜莉莉          | 组织      | 123456 | yanlili@uinnova.com | 15901368164 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |
      | 正常数据       | jyn  | 江一南       | 组织      | 123456 | 2871972383@qq.com | 15101652723 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |
      | 正常数据       | hb | 韩斌       | 组织      | 123456 | 2871972383@qq.com | 15101652723 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |
      | 正常数据       | pll  | 彭乐乐       | 组织      | 123456 | 2871972383@qq.com | 15101652723 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述      |