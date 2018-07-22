@BASE
@DelUser
Feature: BASE_用户管理_User

  @Smoke @switchDefaultUser
  Scenario Outline: User_新建用户、更改用户、重置密码、删除用户
    Given 角色管理里有三个角色
    When 创建登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
    Then 系统中存在登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
    When 登录名为"<opCode>"的用户修改用户名为"<updateOpName>"，所属组织为"<updateCustom1>"，电子邮箱为"<updateEmailAdress>"，联系方式为"<updateMobileNo>"，描述为"<updateNotes>"的用户
    Then 登录名为"<opCode>"的用户的用户名为"<updateOpName>"，所属组织为"<updateCustom1>"，电子邮箱为"<updateEmailAdress>"，联系方式为"<updateMobileNo>"，描述为"<updateNotes>"的用户修改成功
    When 登录名为"<opCode>"的用户,修改密码为"<updateLoginPasswd>"
    Then 登录名为"<opCode>"的用户 的原密码为"<loginPasswd>",密码修改为"<updateLoginPasswd>"
    And 登录名为"<opCode>"的用户密码修改为"<updateLoginPasswd>"后登录成功
    When 删除用户名为"<opCode>"的用户
    Then 系统中不存在名称为"<opCode>"的用户

    Examples: 
      | common          | opCode          | opName               | custom1 | loginPasswd | emailAdress     | mobileNo             | notes    | updateOpName | updateCustom1 | updateLoginPasswd | updateEmailAdress | updateMobileNo | updateNotes                                                                                                                                                                                              |
      | 正常数据            | sunsl123        | sunshuangli          | 组织      |      123456 | 447549@test.com |          18201119884 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述                                                                                                                                                                                                    |
      | 特殊字符            | denglumingd$#@! | denglumingd$#@!      | 组织123   |      234567 | 447549@test.com |          18201119885 | 描述123456 | 修改后名123      | 修改后组织22       | afterPasswd123    | 556677@test.com   |    18201119887 | 修改后描述1234                                                                                                                                                                                                |
      | 修改前后一样          | 不变修改            | 不变修改                 | 组织      |      123456 | 447549@test.com |          18201119884 | 不变修改     | 不变修改         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 不变修改                                                                                                                                                                                                     |
      | 特殊字符_最大15字符     | denglumingd$#@! | 用户名用户1$#@!           | 组织123   |      234567 | 447549@test.com |          18201119885 | 描述123456 | 修改后名123      | 修改后组织22       | afterPasswd123    | 556677@test.com   |    18201119887 | 修改后描述1234                                                                                                                                                                                                |
      | 用户名_最大20中文字符    | denglumingd$#@! | 测试用户名最大二十个中文字符测试用户名称 | 组织123   |      234567 | 447549@test.com |          18201119885 | 描述123456 | 修改后名123      | 修改后组织22       | afterPasswd123    | 556677@test.com   |    18201119887 | 修改后描述1234                                                                                                                                                                                                |
      | 组织_最大20中文字符     | denglumingd$#@! | 测试用户名最大二十个中文字符测试用户名称 | 组织123   |      234567 | 447549@test.com |          18201119885 | 描述123456 | 修改后名123      | 修改后组织22       | afterPasswd123    | 556677@test.com   |    18201119887 | 修改后描述1234                                                                                                                                                                                                |
      | 联系方式_最大20数字     | denglumingd$#@! | 测试用户名最大二十个中文字符测试用户名称 | 组织123   |      234567 | 447549@test.com | 12345678901234567890 | 描述123456 | 修改后名123      | 修改后组织22       | afterPasswd123    | 556677@test.com   |    18201119887 | 修改后描述1234                                                                                                                                                                                                |
      | 修改描述_中文_最大200字符 | deng            | 测试                   | 组织123   |      234567 | 449@test.com    |          13000000001 | 描述123456 | 修改后名123      | 修改后组织22       | afterPasswd123    | 556677@test.com   |    18201119887 | 拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最高权限拥有的最 |

  Scenario: User_下载模板
    When 下载"用户模板"
    Then "用户模板"文件下载成功

  @switchDefaultUser
  Scenario: User_导入
    When 导入用户信息"用户信息.xls"
    Then 用户信息"用户信息.xls"导入成功
    And 用户信息"用户信息.xls"中的用户导入后登录成功
    When 删除导入的用户信息"用户信息.xls"
    Then 用户信息"用户信息.xls"中的用户登录失败

  Scenario Outline: User_导出
    When 创建登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
    When 导出用户信息
    Then 用户信息导出成功
    When 删除用户名为"<opCode>"的用户
    Then 系统中不存在名称为"<opCode>"的用户

    Examples: 
      | common | opCode          | opName      | custom1 | loginPasswd | emailAdress     | mobileNo    | notes    | updateOpName | updateCustom1 | updateLoginPasswd | updateEmailAdress | updateMobileNo | updateNotes |
      | 正常数据   | sunsl123        | sunshuangli | 组织      |      123456 | 447549@test.com | 18201119884 | 描述       | 修改后名         | 修改后组织         | afterPasswd       | 5566@test.com     |    18201119886 | 修改后描述       |
      | 特殊字符   | denglumingd$#@! | 用户名用户1$#@!  | 组织123   |      234567 | 447549@test.com | 18201119885 | 描述123456 | 修改后名123      | 修改后组织22       | afterPasswd123    | 556677@test.com   |    18201119887 | 修改后描述1234   |

  #Scenario: User_超出用户数导入
  #When 超出用户数导入用户信息
  #Then 超出用户数用户信息导入失败
  @Debug
  Scenario Outline: User_用户搜索
    When 创建登录名为"<opCode>"，用户名为"<opName>"，所属组织为"<custom1>"，初始密码为"<loginPasswd>"，电子邮箱为"<emailAdress>"，联系方式为"<mobileNo>"，描述为"<notes>"的用户
    And 搜索登录名包含"<searchKey>"的用户
    Then 包含"<searchKey>"关键字的的用户全部搜索出来
    When 删除用户名为"<opCode>"的用户
    Then 系统中不存在名称为"<opCode>"的用户

    Examples: 
      | common  | opCode         | opName      | custom1 | loginPasswd | emailAdress     | mobileNo    | notes    | searchKey    |
      | 登录名全匹配  | sunsl          | sunshuangli | 组织      |      123456 | 447549@test.com | 18201119884 | 描述       | sunsl        |
      | 登录名部分匹配 | 用户名用户1$#@!     | 用户名用户1$#@!  | 组织123   |      234567 | 447549@test.com | 18201119885 | 描述123456 | denglu       |
      | 登录名匹配符  | dengluming%$   | 用户名56ty     | 组织123   |      234567 | 447549@test.com | 18201119885 | 描述123456 | dengluming%$ |
      | 用户名部分匹配 | dengluming34er | 用户名3er      | 组织123   |      234567 | 447549@test.com | 18201119885 | 描述123456 | 用户名          |

   @switchDefaultUser @Debug
   Scenario: User_普通用户修改用户信息
     When 创建登录名为"test个人信息"，用户名为"test个人信息"，所属组织为"test"，初始密码为"admin123"，电子邮箱为"123@qq.com"，联系方式为"23333"，描述为"23333"的用户
     And  登录名为"test个人信息"密码为"admin123"的用户可以登录成功
	 Then 修改用户信息,用如下表单:
	 	|属性|值|
	    |custom1|test|
	    |superUserFlag| 0|
	    |userDomainId| 1|
	    |status| 1|
	    |loginFlag| 0|
	    |emailAdress|123@qq.com|
	    #|id| 100000000000045|
	    |modifier|test个人信息[test个人信息]|
	    |opKind| 2|
	    |opCode|test个人信息|
	    |lockFlag| 0|
	    #|domainId| 1|
	    |dataStatus| 1|
	    |opName|test个人信息2333|
	    |shortName|test个人信息|
	    |notes|233333333333332333|
	    |mobileNo|12345678902333|
	    #|lastLoginTime|20180423012645|
	    |loginCode|test个人信息|
	    |custom3| null|
	 Then 验证修改，用如下表单:
	 	|属性|值|
	    |custom1|test|
	    |superUserFlag| 0|
	    |userDomainId| 1|
	    |status| 1|
	    |loginFlag| 0|
	    |emailAdress|123@qq.com|
	    #|id| 100000000000045|
	    #|modifier|[test个人信息test个人信息]|
	    |opKind| 2|
	    |opCode|test个人信息|
	    |lockFlag| 0|
	    #|domainId| 1|
	    |dataStatus| 1|
	    |opName|test个人信息2333|
	    |shortName|test个人信息|
	    |notes|233333333333332333|
	    |mobileNo|12345678902333|
	    #|lastLoginTime|20180423012645|
	    |loginCode|test个人信息|
	    #|custom3| null|
   	When 删除用户名为"test个人信息"的用户
    Then 系统中不存在名称为"test个人信息"的用户