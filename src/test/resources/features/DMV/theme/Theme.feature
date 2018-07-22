@DMV@delDiagram
Feature: DMV_个性化设置_Theme
    @Smoke
    Scenario: Theme_添加配置标签设置、更新配置标签设置、删除配置
		 When 给分类名称为"Application"的分类添加配置
		 Then 分类名称为"Application"的分类添加配置成功
		 When 分类名称为"Application"的分类配置属性排序
		 Then 分类名称为"Application"的分类的配置属性成功排序
		 When 分类名称为"Application"的分类更新配置属性
		 Then 分类名称为"Application"的分类配置属性更新成功
		 When 删除分类名称为"Application"的分类的配置
		 Then 分类名称为"Application"的分类成功删除配置
        
   Scenario Outline: Theme_搜索配置标签设置
		 When 给分类名称为"Application"的分类添加配置
		 Then 分类名称为"Application"的分类添加配置成功
		 When 搜索分类名称为"<className>"的配置
		 Then 分类名称为"<className>"的配置成功搜索出来
		 When 删除分类名称为"Application"的分类的配置
		 Then 分类名称为"Application"的分类成功删除配置
	Examples:  
	 | common   |className|
     |分类名全匹配     |importCiData|
     |分类名部分匹配  |Data|
     
    @Smoke
	Scenario Outline: Theme_自动化工具关联增改删
	   Given 系统中已存在如下ci分类:"Application,Cluster"
	   When 创建链接名称为"<proName>"，链接地址为"<proVal1>"，窗口显示宽度为"<width>"，高度为"<height>"，分类名称为"Application"
	   Then 链接名称为"<proName>"，链接地址为"<proVal1>"，窗口显示宽度为"<width>"，高度为"<height>"，分类名称为"Application"的动态链接成功创建
	   When 链接名称为"<proName>"，修改链接名称为"<updateProName>"，修改链接地址为"<updateProVal1>"，修改窗口宽度为"<updateWidth>"，高度为"<updateHeight>"，分类名称为"Cluster"
	   Then 链接名称为"<updateProName>"，链接地址为"<updateProVal1>"，窗口宽度为"<updateWidth>"，高度为"<updateHeight>"，分类名称为"Cluster"的动态链接修改成功
	   When 删除链接名称为"<updateProName>"动态链接
	   Then 动态链接"<updateProName>"成功删除	   
	Examples:
	   |common|proName|proVal1|width|height|updateProName|updateProVal1|updateWidth|updateHeight|
	   |中英文数|动态链接wer345|baidu.com|123|22|修改后动态链接|sina.com|342|221|
	   |特殊字符|动态链接$#@|baidu.com|23|34|修改后动态链接名称123|sina.com|23|56|
	   |最大长度|链接名称234儿童水电费链接名称234儿童   水电费称234儿童水电费234儿童水电费r|baidu.com|125|26|修改后动态链接4er|sina.com|21|24|
       |链接名称最大长度汉字50|链接名称链接名称链接名称链接名称链接名称链接名称链接名称链接名称链接名称链接名称链接名称测试测试测试|baidu1.com|127|28|修改后动态链接rty|sina11.com|25|26|
       |链接地址最大长度汉字2000|链接名称|proVal1.txt|124|23|修改后动态链接11|sina12.com|34245|2214|
      
    @Smoke@delDiagram
    Scenario Outline: Theme_设置开关
	    When 新建视图"设置开关",描述为"设置开关",文件夹为"我的" 
	    Then 成功新建视图"设置开关"
        When 设置开关"<cfgCode>",cfgContent为"<cfgContent>"
        Then 成功设置开关"<cfgCode>"
        And  删除设置开关"<cfgCode>"
        When 删除名称为"设置开关"的视图
	    Then 成功删除名称为"设置开关"的视图
      Examples:
      |cfgCode|cfgContent|
      |showCiHighLight|0|
      |showCiKpiType|0|
      |showConfigTree|1|
      |notUsedCiClassCodes|放样物体,独立设备|
      
    @Smoke
    Scenario Outline: Theme_设置刷新频率
	  When 新建视图"设置刷新频率",描述为"设置刷新频率",文件夹为"我的" 
	  Then 成功新建视图"设置刷新频率"
      When 设置刷新频率"<cfgCode>",cfgContent为"<cfgContent>"
      Then 成功设置模板"<cfgCode>","<cfgContent>"
      When 删除名称为"设置刷新频率"的视图
	  Then 成功删除名称为"设置刷新频率"的视图
      Examples:
      |cfgCode|cfgContent|
      |alarmRefreshTime|60|
      |propertyRefreshTime|300|
   @Smoke    
   Scenario Outline: Theme_关系分类设置不同线型
       When 修改名称为"<rltName>"的关系分类，其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",关系颜色为"<lineColor>"
       Then 成功修改名称为"<rltName>"的关系分类创建成功,其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",关系颜色为"<lineColor>"
      Examples: 
      | common   | rltName| lineAnime | lineType | lineBorder | lineDirect  |lineColor|
      | 正常数据     | AppRlt |         1 | solid    |          1 | classic      |#c0504d|
      | 关系名称     | AppRlt |         0 | dashed   |          2 | block        |#ccc0d9|
      | 不同线型     | AppRlt |         1 | dotted   |          3 | none         |#e5b8b7|
      | 不同线型     | AppRlt |         1 | dotted   |          4 | open         |#8db3e2|
      | 不同线型     | AppRlt |         1 | dotted   |          5 | diamondThin  |#bfbfbf|
      | 不同线型     | AppRlt |         1 | dotted   |          6 | diamondTrans |#bfbfbf|
      | 不同线型     | AppRlt |         1 | dotted   |          8 | diamondTrans |#e5b8b7|
      | 不同线型     | AppRlt |         1 | dotted   |         10 | diamondTrans |#ccc0d9|
      
   @Smoke
   Scenario Outline: Theme_设置模板
	  When 新建视图"设置模板",描述为"设置模板",文件夹为"我的" 
	  Then 成功新建视图"设置模板"
      When 设置模板"<cfgCode>",cfgContent为"<cfgContent>"
      Then 成功设置模板"<cfgCode>","<cfgContent>"
      When 删除名称为"设置模板"的视图
	  Then 成功删除名称为"设置模板"的视图
      Examples:
      |cfgCode|cfgContent|
      |bigScreenColorOrTemplate|1|
      |bigScreenColorOrTemplate|2|
      
   @Smoke   
   Scenario Outline: Theme_菜单顺序
   	  When 新建视图"设置菜单顺序",描述为"设置菜单顺序",文件夹为"我的"
   	  Then 成功新建视图"设置菜单顺序"
   	  When 设置菜单顺序"<cfgCode>",cfgContent为"<cfgContent>"
   	  Then 成功改变菜单顺序"<cfgCode>","<cfgContent>"
   	  When 删除名称为"设置菜单顺序"的视图
   	  Then 成功删除名称为"设置菜单顺序"的视图
   	  Examples:
   	  |cfgCode|cfgContent|
   	  |configMenuOrder|[{\"code\":\"0404\",\"name\":\"mine\",\"text\":\"我的\",\"url\":\"tarsier.diagrams.mine\",\"includes\":[\"tarsier.diagrams.mine\",\"tarsier.diagrams.trash\",\"tarsier.diagrams.favor\"],\"showInput\":false},{\"code\":\"0403\",\"name\":\"square\",\"text\":\"广场\",\"url\":\"tarsier.diagrams.square\",\"showInput\":false},{\"code\":\"0405\",\"name\":\"setting\",\"text\":\"设置\",\"url\":\"tarsier.diagrams.setting.config\",\"includes\":[\"tarsier.diagrams.setting\"],\"showInput\":false},{\"code\":\"0402\",\"name\":\"team\",\"text\":\"协作\",\"url\":\"tarsier.diagrams.team\",\"includes\":[\"tarsier.diagrams.team\",\"tarsier.diagrams.teamDetail\"],\"showInput\":false},{\"code\":\"0406\",\"name\":\"draw\",\"text\":\"绘图\",\"url\":\"tarsier.diagrams.draw\",\"showInput\":false},{\"code\":\"0407\",\"name\":\"sheets\",\"text\":\"表格\",\"url\":\"tarsier.diagrams.sheets\",\"showInput\":false}]|
   	    	  
   	 @Smoke@Debug
   	 Scenario: Theme_查询分类列表
   	   When 查询分类列表
   	   Then 成功查询分类列表