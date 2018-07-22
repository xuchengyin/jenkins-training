@CMV
Feature: CMV_环境变量
   
	Scenario: Varible_更新_查询
	
	When 修改logo用如下参数:
	|位置|变量名|变量值|描述|
	|登录页面的logo|LOGO_PATH_LOGIN|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|123333|
	|页签上的logo|LOGO_PATH_FAV|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|223333|
	|左上角导航栏的logo|LOGO_PATH_NAV|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|323333|
	
	Then 用如下参数验证修改正确:
	|位置|变量名|变量值|描述|
	|登录页面的logo|LOGO_PATH_LOGIN|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|123333|
	|页签上的logo|LOGO_PATH_FAV|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|223333|
	|左上角导航栏的logo|LOGO_PATH_NAV|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|323333|
	
	#由于这个东西只有一个选择图标的选项，所以不做失败测试了
	#When 修改logo用如下参数失败:
	#|位置|变量名|变量值|描述|kw|
	#|登录页面的logo|LOGO_PATH_LOGIN|/vmdb-sso/rsm/cli/read/122/12501/23333333333.png|123333||
	#|页签上的logo|LOGO_PATH_ABC|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|123333||
	#|左上角导航栏的logo|LOGO_PATH_NAV|/vmdb-sso/rsm/cli/read/122/12501/AppSystem.png|汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字汉字||
	
	@Debug
	Scenario: Varible_还原Logo
	
	When 还原logo用如下参数:
	|位置|varName|varType|varDesc|
	|登录页面的logo|LOGO_PATH_LOGIN|2|登录页面的LOGO|
	|页签上的logo|LOGO_PATH_FAV|2|页签上的logo|
	|左上角导航栏的logo|LOGO_PATH_NAV|2|左上角导航栏的logo|
	Then 正确还原logo用如下参数:
	|位置|varName|varType|varValue|varDesc|
	|登录页面的logo|LOGO_PATH_LOGIN|2|/vmdb-sso/rsm/cli/read/122/logos/logo.gif|登录页面的LOGO|
	|页签上的logo|LOGO_PATH_FAV|2|/vmdb-sso/rsm/cli/read/122/logos/favicon.png|页签上的logo|
	|左上角导航栏的logo|LOGO_PATH_NAV|2|/vmdb-sso/rsm/cli/read/122/logos/Logo_path_nav.png|左上角导航栏的logo|
	
	
	