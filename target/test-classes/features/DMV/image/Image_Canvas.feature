@DMV
@delDiagram
Feature: DMV_绘图_新建视图_ci画图：画布左、右侧菜单
      @Smoke
      @delDiagramDir
	  Scenario Outline: Image_画布左侧菜单_搜索图标
	    When 创建名称为"测试图标123"的ImageClass目录
		Then 系统中存在名称"测试图标123"的ImageClass目录
		When 给"测试图标123"目录添加"$%#"图片
		Then 这个"测试图标123"目录"$%#"图片添加成功
        When 根据图标关键字"<searchKey>"来搜索图标
        Then 返回所有包含"<searchKey>"关键字的图片
        When 删除名称为"测试图标123"的ImageClass目录
		Then 系统中不存在名称为"测试图标123"的ImageClass目录

      Examples:
        | common |  searchKey |
	    | 部分匹配     |	AppClu    |
	    | 全匹配         |  AppCluster| 
	    | 特殊字符    |    $%#     |
	    | 空搜索        |            |
	    

	 Scenario: Image_画布左侧菜单_去除宝马形状,写入配置文件
	    When 新建视图"去除宝马形状",描述为"去除宝马形状",文件夹为"我的"
	    Then 成功新建视图"去除宝马形状"
	    When 读取配置文件
	    Then 成功读取配置文件
	
     @Smoke
	 Scenario Outline: Image_画布左侧菜单_CI配置查询
	 	When 新建视图"CI配置查询",描述为"CI配置查询",文件夹为"我的"
	    Then 成功新建视图"CI配置查询"
        When 根据CI关键字"<searchKey>"来搜索CI
        Then 根据CI关键字"<searchKey>"成功来搜索CI
      Examples:
        | common |  searchKey |
	    | 部分匹配     |	BJ|
	    | 全匹配         | 信用卡工单1|
	    |特殊字符|$#@|
	    

	@Smoke
	@delCiRltRule
	Scenario Outline: Image_画布左侧菜单_规则查询
		Given 系统中已存在如下ci分类:"Application,s@&_-"
	 	And 系统中已存在如下关系分类:"特殊"
	 	When 新建"Application"到"s@&_-"关系为"特殊"的朋友圈规则"<friendName>"
	 	Then 成功新建"Application"到"s@&_-"关系为"特殊"的朋友圈规则"<friendName>"
	 	When 查询在朋友圈规则"<friendName>"下的CiCode为"<searchKey>"的朋友圈
	 	Then 成功查询在朋友圈规则"<friendName>"下的CiCode为"<searchKey>"的朋友圈
     	When 删除朋友圈规则"<friendName>"
	 	Then 成功删除朋友圈规则"<friendName>"

	 Examples:
		 | common   | friendName|searchKey|
    	 |视图|新建朋友圈规则123ew|信用卡工单1|
    	 |视图|新建朋友圈规则123ewewr|金融交换1|
    	 |视图|新建朋友圈规则123ewtyu|信用卡决策1|
    	 
    @delCiRltRule
	Scenario Outline: Image_画布左侧菜单_规则查询空特殊字符测试
		Given 系统中已存在如下ci分类:"Application,s@&_-"
	 	And 系统中已存在如下关系分类:"特殊"
	 	When 新建"Application"到"s@&_-"关系为"特殊"的朋友圈规则"<friendName>"
	 	Then 成功新建"Application"到"s@&_-"关系为"特殊"的朋友圈规则"<friendName>"
	 	And  查询在朋友圈规则"<friendName>"下的CiCode为空,特殊字符"<searchKey>"的朋友圈,kw="<kw>"
     	When 删除朋友圈规则"<friendName>"
	 	Then 成功删除朋友圈规则"<friendName>"

	 Examples:
		 | common   | friendName|searchKey|kw|
    	 |空|新建朋友圈规则123ewtrwq||error,  the 'ci' is null argument|
    	 |特殊字符|新建朋友圈规则123iurewwq|%$#@|error,  the 'ci' is null argument|
    	 
	  
      @Smoke
      @CleanCiCls
	  Scenario Outline: Image_画布右侧菜单_数据开关
	     When 新建视图"测试数据开关",描述为"数据开关",文件夹为"我的"
	     Then 成功新建视图"测试数据开关"
	     When 给视图"测试数据开关"增加CI"信用卡工单1"坐标为"400""600"
	     Then 成功给视图"测试数据开关"增加CI"信用卡工单1"坐标为"400""600"
	     When 为视图"测试数据开关"设置数据开关为"<dataUpType>"
	     Then 为视图"测试数据开关"的CI"信用卡工单1"成功设置数据开关"<dataUpType>"
	     When 删除名称为"测试数据开关"的视图
	     Then 成功删除名称为"测试数据开关"的视图
	  Examples:
	     | common |  dataUpType |
	     | 禁止更新     |	1|
	     | 手动更新      |  2|
	     | 自动更新      |  3| 
    
    @Smoke
    @CleanCiCls@Debug
	Scenario Outline: Image_画布右侧菜单_配置扫描
	     When 在"业务领域"目录下,创建名称为"TestDataImage"的ci分类,使用图标为"Default"
   		 And 给"TestDataImage"分类添加如下属性:
	     | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 字符串     | 字符串                   |  1 |     1 |         |       1 |                  |
     
    	 Then "TestDataImage"分类属性更新成功 
	   	 | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 字符串     | 字符串                   |  1 |     1 |         |       1 |                  |
    
    	 When 给"TestDataImage"添加如下数据:
	  	 |commont|字符串|
	     |  正常  |$%&|
	     |  正常  |two2|
    	 Then "TestDataImage"分类数据添加成功
	   	 |commont|字符串|
	     |  正常  |$%&|
	     |  正常  |two2|
	    When 新建视图"测试配置扫描",描述为"数据扫描",文件夹为"我的"
	    Then 成功新建视图"测试配置扫描"
		When 给视图"测试配置扫描"增加常用图标"DB"坐标为"200""300"
		Then 成功给视图"测试配置扫描"增加常用图标"DB"坐标为"200""300"
	    When 为关键字"<words>"配置扫描
	    Then 为关键字"<words>"成功配置扫描
	    When 删除名称为"测试配置扫描"的视图
	    Then 成功删除名称为"测试配置扫描"的视图
	    When 删除"TestDataImage"分类及数据
	    Then 系统中不存在名称为"TestDataImage"的分类
	    
	  Examples:
	    |common|words|
	    |单个关键字|App|
	    |多个关键字|黄金交易,核心交易|
	    |ip|192.168.4.41,192.168.4.13|
	    |特殊字符|$%&|  
	    

    @Smoke
	Scenario Outline: Image_新建视图新建版本,更新版本,删除版本
       When 新建视图"测试版本",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试版本"
	   When 给视图"测试版本"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试版本"增加CI"信用卡决策1"坐标为"400""600"
       And  给视图"测试版本"创建名称为"<versionDesc>"版本号为"<versionNo>"的版本
       When 取得视图"测试版本"版本
       Then 视图版本为"<versionDesc>"版本号为"<versionNo>"正确取得
       When 将视图"测试版本"的版本"<versionDesc>"更新成"<updateDesc>"版本号更新为"<updateVersionNo>"
       Then 成功将视图"测试版本"的版本"<versionDesc>"更新成"<updateDesc>"版本号更新为"<updateVersionNo>"
       When 删除视图11"测试版本"的版本"<updateDesc>"
       Then 成功删除视图"测试版本"的版本"<updateDesc>"
       When 删除名称为"测试版本"的视图
	   Then 成功删除名称为"测试版本"的视图
	   
	Examples:
	   |common|versionDesc|updateDesc|versionNo|updateVersionNo|
       |中文  |测试历史版本|更新后版本|v1.1.0|v2.1.0|
       |字母数字|ytreIOwq345678|更新后uiuTY|v1.2.0|v2.2.0|
       |纯数字    |7346980212345|更新后tyueiwe|v1.3.0|v2.3.0|
       |特殊字符|￥%&#@！()*&|更新后iuytrwe|v1.4.0|v2.4.0|
       |版本最大长度汉字4000|version.txt|更新版本|v1.5.0|v2.5.0|
 
    @Smoke
	Scenario Outline: Image_画布右侧菜单_标签
	   When 新建视图"测试视图1122wwqqq",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图1122wwqqq"
	   When 给视图"测试视图1122wwqqq"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图1122wwqqq"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"测试视图1122wwqqq"新建名称为"<tagName>"的标签
	   Then 成功新建标签"<tagName>"
	   When 删除名称为"测试视图1122wwqqq"的视图
	   Then 成功删除名称为"测试视图1122wwqqq"的视图
    Examples:
       |common|tagName|
       |中文  |测试标签|
       |字母数字|ytreIOwq345678|
       |纯数字    |7346980212345|
       |特殊字符|￥%&#@！()*&|
       |最大长度|标签4ttyyutrrr|
       |标签名称最大长度汉字12|标签测试标签标签测试标签|
       
    @Smoke@cleanRlt
 	Scenario: Image_画布右侧菜单_历史
 		Given 系统中已存在如下ci分类:"Application,Cluster"
	 	And 系统中已存在如下关系分类:"AppRlt"
	 	When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"信用卡工单1",目标分类为"Cluster",目标对象为"BJ_Reporter1"关系数据
	 	Then "AppRlt"下存在只存在1条"信用卡工单1"与"BJ_Reporter1"的关系数据
	 	When 新建视图"新建视图历史",描述为"",文件夹为""
		Then 成功新建视图"新建视图历史"
		
	    When 给视图"新建视图历史"增加CI"信用卡工单1"坐标为"400""600"
	    Then 成功给视图"新建视图历史"增加CI"信用卡工单1"坐标为"400""600"
	    When 给视图"新建视图历史"增加CI"BJ_Reporter1"坐标为"500""700"
	    Then 成功给视图"新建视图历史"增加CI"BJ_Reporter1"坐标为"500""700"
	    
		When 删除"AppRlt"关系下,属性值前匹配"BJ_Reporter1"的关系数据
	 	Then "AppRlt"关系下,不存在属性值前匹配"BJ_Reporter1"的关系数据
	 	
	 	When 给视图"新建视图历史"创建了"管理员删除了配置信用卡工单1与配置BJ_Reporter1的AppRlt关系"的历史记录
	 	Then 成功给视图"新建视图历史"创建了"管理员删除了配置信用卡工单1与配置BJ_Reporter1的AppRlt关系"的历史记录
	 	
	 	When 删除名称为"新建视图历史"的视图
	    Then 成功删除名称为"新建视图历史"的视图
	 @CleanCiCls
	 Scenario: Image_画布左侧菜单_查询CI
	 	 When 在"业务领域"目录下,创建名称为"交换机"的ci分类,使用图标为"Default"
   		 And 给"交换机"分类添加如下属性:
	     | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 设备名称   | 字符串                   |  1 |     1 |         |       1 |                  |
     
    	 Then "交换机"分类属性更新成功 
	   	 | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 设备名称     | 字符串                   |  1 |     1 |         |       1 |                  |
    
    	 When 给"交换机"添加如下数据:
	  	 |commont|设备名称|
	     |  正常  |交换机001|
	     |  正常  |交换机002|
    	 Then "交换机"分类数据添加成功
	   	 |commont|设备名称|
	     |  正常  |交换机001|
	     |  正常  |交换机002|
	     When 在"业务领域"目录下,创建名称为"端口"的ci分类,使用图标为"Default"
   		 And 给"端口"分类添加如下属性:
	     | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 端口号   | 字符串                   |  1 |     1 |         |       1 |           |
         |所属设备  |数据字典_type_交换机   |1   |1      |         |0        |           |
    	 Then "端口"分类属性更新成功 
	   	 | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 端口号    | 字符串                   |  1 |     1 |         |       1|           |
         |所属设备  |数据字典_type_交换机      |1   |1      |         |      0 |           |
    	 When 给"端口"添加如下数据:
	  	 |commont|端口号| 所属设备 |
	     |  正常  |001_001| 交换机001 |
	     |  正常  |001_002| 交换机001 |
    	 Then "端口"分类数据添加成功
	   	 |commont|端口号|  所属设备 |
	     |  正常  |001_001|交换机001|
	     |  正常  |001_002|交换机001|
	    When 查询CICODE为"交换机001"的CI
	    Then 正确查出分类为"端口"CICODE为"交换机001"的CI
	    When 删除"交换机"分类及数据
	    Then 系统中不存在名称为"交换机"的分类
	    When 删除"端口"分类及数据
	    Then 系统中不存在名称为"端口"的分类
	    
  	 @CleanCiCls@cleanRlt
	 Scenario Outline: Image_画布左侧菜单_查询路由关系
	 	 When 在"业务领域"目录下,创建名称为"AAAAA"的ci分类,使用图标为"Default"
   		 And 给"AAAAA"分类添加如下属性:
	     | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | CICode| 字符串                   |  1 |     1 |         |       1 |         |
     
    	 Then "AAAAA"分类属性更新成功 
	   	 | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | CICode| 字符串                   |  1 |     1 |         |       1 |         |
    
    	 When 给"AAAAA"添加如下数据:
	  	 |commont|CICode|
	     |  正常  |A1|
	     |  正常  |A2|
    	 Then "AAAAA"分类数据添加成功
	   	 |commont|CICode|
	     |  正常  |A1|
	     |  正常  |A2|
	     When 在"业务领域"目录下,创建名称为"路由器"的ci分类,使用图标为"Default"
   		 And 给"路由器"分类添加如下属性:
	     | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 设备序列号| 字符串                   |  1 |     1 |         |       1 |           |
         | 设备类别    | 字符串                   |1   |     1 |         |0        |           |
    	 Then "路由器"分类属性更新成功 
	   	 | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
         | 设备序列号 | 字符串          |  1 |     1 |         |       1|           |
         | 设备类别    | 字符串           |1   |     1 |         |      0 |           |
    	 When 给"路由器"添加如下数据:
	  	 |commont|设备序列号| 设备类别 |
	     |  正常  |路1| 交换机001 |
	     |  正常  |路2| 交换机002 |
    	 Then "路由器"分类数据添加成功
	   	 |commont|设备序列号|  设备类别 |
	     |  正常  |路1|交换机001|
	     |  正常  |路2|交换机002|
	     When 创建名称为"<rltName>"的关系分类，其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
         | 属性名  | 属性类型 | 枚举值 | 是否cicode |
         | ATTRNAME  |    3 | ""  |        1 |

         Then 名称为"<rltName>"的关系分类创建成功,其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
         | 属性名  | 属性类型 | 枚举值 | 是否cicode |
         | ATTRNAME  |    3 | ""  |        1 |
         When 在关系分类"<rltName>"下,创建源分类为"AAAAA",源对象为"A1",目标分类为"路由器",目标对象为"路1"关系数据,属性为:
         | 属性名  | 属性值  |
         | ATTRNAME   | 路1 |
         Then 成功在关系分类"<rltName>"下,创建源分类为"AAAAA",源对象为"A1",目标分类为"路由器",目标对象为"路1"关系数据,属性为:
         | 属性名  | 属性值  |
         | ATTRNAME|路1 |	     
	    When 查询CICODE为"路1"的路由关系
	    Then 正确查出分类为"AAAAA"CICODE为"A1"的路由关系
	    When 删除"AAAAA"分类及数据
	    Then 系统中不存在名称为"AAAAA"的分类
	    When 删除"路由器"分类及数据
	    Then 系统中不存在名称为"路由器"的分类
	    When 删除"<rltName>"关系下,属性值前匹配"路1"的关系数据
        Then "<rltName>"关系下,不存在属性值前匹配"路1"的关系数据
        When 删除名称为"<rltName>"的关系分类及数据
        Then 关系分类"<rltName>"分类及数据删除成功
	  Examples: 
      | common   | rltName               | lineAnime | lineType | lineBorder | lineDirect   | copyRltName     |lineDispType|
      | 正常数据     |AccessRelation            |         1 | solid    |          1 | classic      | @& _-           |1|  