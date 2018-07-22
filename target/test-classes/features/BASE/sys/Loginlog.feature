@BASE
@switchDefaultUser
@DelUser
Feature: BASE_登录日志_Loginlog

    @Smoke   	
    Scenario Outline: Loginlog_登录日志查询
	 		When 创建登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
			Then 系统中存在登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
			And  登录名为"<opCode>"密码为"<loginPasswd>"的用户可以登录成功
  	 	When 按照如下关键字搜索登录日志
     	| common  |loginname|
     	|真实的名字|sunsl123|
     	|空||
     	|下划线|_|
     	|百分号|%|
    	|特殊字符|@|
     	Then 按照如下关键字搜索登录日志成功
     	| common  |loginname|
     	|真实的名字|sunsl123|
    	|空||
     	|下划线|_|
     	|百分号|%|
     	|特殊字符|@|
     	Examples: 
      | common   | opCode          | opName | custom1     | loginPasswd|emailAdress| mobileNo         |notes|updateOpName   | updateCustom1 | updateLoginPasswd     | updateEmailAdress   |updateMobileNo|updateNotes|
      | 正常数据  | sunsl123       |    sunshuangli | 组织 |       123456 | 447549@test.com|18201119884|描述|修改后名        | 修改后组织         |     afterPasswd       | 5566@test.com       |18201119886   | 修改后描述  |
     	
    @Smoke  @Debug
    Scenario Outline: Loginlog_登录日志统计搜索特定用户登录次数
	 		When 创建登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
			Then 系统中存在登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
			And  登录名为"<opCode>"密码为"<loginPasswd>"的用户可以登录成功
			
  	 	When 按照如下关键字统计登录次数
     	| common  |loginname|
     	|真实的名字|sunsl123|
     	|空||
     	|下划线|_|
     	|百分号|%|
     	|特殊字符|@|
     	Then 按照如下关键字统计登录次数成功
     	| common  |loginname|
     	|真实的名字|sunsl123|
     	|空||
     	|下划线|_|
     	|百分号|%|
     	|特殊字符|@|
     	Examples: 
      | common   | opCode          | opName | custom1     | loginPasswd|emailAdress| mobileNo         |notes|updateOpName   | updateCustom1 | updateLoginPasswd     | updateEmailAdress   |updateMobileNo|updateNotes|
      | 正常数据  | sunsl123       |    sunshuangli@ | 组织 |       123456 | 447549@test.com|18201119884|描述|修改后名        | 修改后组织         |     afterPasswd       | 5566@test.com       |18201119886   | 修改后描述  |
      
      
    Scenario: Login_log查询全部登录日志
    	When 查询全部登录日志
    	Then 查询全部登录日志成功
    	
    Scenario: Login_log统计全部登录次数
    	When 统计全部登录次数
    	Then 统计全部登录次数成功
 