@CMV
Feature: CMV_仪表盘_Dashboard

	@Smoke
	@delDashboard
	@Debug
	Scenario Outline: Dashboard_新建、查询、修改、删除
		When 新建名称为"<dashboardName>"的仪表盘
		Then 成功新建名称为"<dashboardName>"的仪表盘
		And 再次新建名称为"<dashboardName>"的仪表盘失败,kw="<kw>"
		When 按照关键字"<dashboardName>"搜索仪表盘
		Then 成功按照关键字"<dashboardName>"搜索仪表盘
		When 修改名称为"<dashboardName>"的仪表盘名称为"<newDashboardName>"
		Then 成功修改名称为"<dashboardName>"的仪表盘名称为"<newDashboardName>"
		When 按照关键字"<newDashboardName>"搜索仪表盘
		Then 成功按照关键字"<newDashboardName>"搜索仪表盘
		When 删除名称为"<newDashboardName>"的仪表盘
		Then 成功删除名称为"<newDashboardName>"的仪表盘
	Examples:
		 | common   | dashboardName|newDashboardName|kw|
    	 |新建 |新建仪表盘|新建仪表盘|nullnull不可为空|
    	 |新建 |aaabbb1245|中文abc|nullnull不可为空|
    	 |新建特殊字符 |@#￥%……&&**（|dddd123|nullnull不可为空|
    	 |新建最大中文字符100个 |测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表|dddd123|nullnull不可为空|
    	 |修改最大中文字符100个 |@#￥%……&&**（|测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表盘名称最大中文一百个字符测试仪表|nullnull不可为空|

	Scenario Outline: CiQualityRule_搜索仪表盘规则
		When 按照关键字"<searchKey>"搜索仪表盘
		Then 成功按照关键字"<searchKey>"搜索仪表盘
       Examples: 搜索关键字
		|case|searchKey|
		|分类全名|Application|
		|小写       |app|
		|空          ||
		|下划线   |_|
		|不存在的|Applications1234567|
		|特殊字符|@|
		|特殊字符|&|
		|特殊字符|%|
		
	
	
	@Smoke
	@delChart
	@delCiQualityRule
	@delDashboard
	@Debug
	Scenario Outline: Dashboard_添加时间序列图表
		When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
		Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
		When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
		|操作|className|attrName|ruleOp|ruleVal|condition|
		|或|Application|CI Code|=|400网关1||
		And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
		|common|className|attrName|cdtOp|cdtValue|
		|常用|Application|Name|=|ATMP|
		When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
		Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
		When 新建名称为"<dashboardName>"的仪表盘
		Then 成功新建名称为"<dashboardName>"的仪表盘
		When 给仪表盘"<dashboardName>"添加"<chartType>"图表,标题为"<title>"规则为"<ruleType>""<ruleName>"
		Then 成功给仪表盘"<dashboardName>"添加"<chartType>"图表,标题为"<title>"规则为"<ruleType>""<ruleName>"
		And  再次给仪表盘"<dashboardName>"添加"<chartType>"图表,标题为"<title>"规则为"<ruleType>""<ruleName>"失败,kw="<kw>"
		When 修改仪表盘"<dashboardName>"标题为"<title>"的图表新标题为"<newTitle>"规则为"<ruleType>""<ruleName>"
		Then 成功修改仪表盘"<dashboardName>"标题为"<title>"的图表新标题为"<newTitle>"规则为"<ruleType>""<ruleName>"
		When 删除仪表盘"<dashboardName>"的图表"<title>"
		Then 成功删除仪表盘"<dashboardName>"的图表"<title>"
		When 删除名称为"<dashboardName>"的仪表盘
		Then 成功删除名称为"<dashboardName>"的仪表盘
		When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
		Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则		
	Examples:
		 | common   |ruleType|ruleName|ruleDesc|dashboardName|chartType|title|newTitle|kw|
    	 |新建 |合规性规则|仪表盘规则|仪表盘规则描述|新建仪表盘|时间序列|图表标题|新图表|null[图表标题]重复|
    	
 
 	@Smoke
	@delChart
	@delCiQualityRule
	@delDashboard
	@Debug
	Scenario Outline: Dashboard_添加当前值图表
		When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
		Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
		When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
		|操作|className|attrName|ruleOp|ruleVal|condition|
		|或|Application|CI Code|=|400网关1||
		And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
		|common|className|attrName|cdtOp|cdtValue|
		|常用|Application|Name|=|ATMP|
		When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
		Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
		When 新建名称为"<dashboardName>"的仪表盘
		Then 成功新建名称为"<dashboardName>"的仪表盘
		When 给仪表盘"<dashboardName>"添加"<chartType>"类型的图表,标题为"<title>"规则为"<ruleType>""<ruleName>"低阈值为"<thresholdFloorValue>"高阈值为"<thresholdUpperValue>"
		Then 成功给仪表盘"<dashboardName>"添加"<chartType>"类型的图表,标题为"<title>"规则为"<ruleType>""<ruleName>"低阈值为"<thresholdFloorValue>"高阈值为"<thresholdUpperValue>"
		And  再次给仪表盘"<dashboardName>"添加"<chartType>"类型的图表,标题为"<title>"规则为"<ruleType>""<ruleName>"低阈值为"<thresholdFloorValue>"高阈值为"<thresholdUpperValue>"失败,kw="<kw>"
		When 修改仪表盘"<dashboardName>"标题为"<title>"的当前值图表新标题为"<newTitle>"规则为"<ruleType>""<ruleName>"低阈值为"<thresholdFloorValue>"高阈值为"<thresholdUpperValue>"
		Then 成功修改仪表盘"<dashboardName>"标题为"<title>"的当前值图表新标题为"<newTitle>"规则为"<ruleType>""<ruleName>"低阈值为"<thresholdFloorValue>"高阈值为"<thresholdUpperValue>"
		When 查看仪表盘"<dashboardName>"的图表"<title>"的数据
		Then 成功查看仪表盘"<dashboardName>"的图表"<title>"的数据
		When 删除仪表盘"<dashboardName>"的图表"<title>"
		Then 成功删除仪表盘"<dashboardName>"的图表"<title>"
		When 删除名称为"<dashboardName>"的仪表盘
		Then 成功删除名称为"<dashboardName>"的仪表盘
		When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
		Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则		
	Examples:
		 | common   |ruleType|ruleName|ruleDesc|dashboardName|chartType|title|newTitle|thresholdFloorValue|thresholdUpperValue|kw|
    	 |新建 |合规性规则|仪表盘规则|仪表盘规则描述|新建仪表盘|当前值|图表标题|新图表|20|80|null[图表标题]重复|   	 
    	 
	
	
	
	#Scenario Outline: Dashboard_更新仪表盘
	#queryDashboardById
	#queryQualityChartInfoList
	#queryCiQualityChartDataInfoByRuleId
	#queryLastSumByRuleId
	
	
	#图表 导出
	#图表查看数据
	
	
	
		