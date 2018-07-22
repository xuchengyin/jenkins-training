@PMV
Feature: PMV_指标标签_StandardTag 

@delStandardTag 
Scenario Outline: 设置标签，更新标签， 清除标签
	When 推送性能数据"<perfdate>" 
	When 对指标类为"<kpiclass>"，指标为"<metric>"的属性"<attr>"设置标签，标准标签为空 
	Then 指标类为"<kpiclass>"，指标为"<metric>"的属性"<attr>"的标签设置成功 
	When 推送性能数据"<perfdate>" 
	Then 指标为"<metric>"属性为"<attr>"的指标标签已存在 	
	And  验证存在指标为"<metric>"属性为"<attr>"的指标标签 	 
	When 新建名称为"<STagName>"的标准标签 
	Then 成功新建名称为"<STagName>"的标准标签  
	When 更新设置指标类为"<kpiclass>"，指标为"<metric>"的属性"<attr>"，标准标签为"<STagName>"的标签
	When 推送性能数据"<perfdate>"
	Then 指标为"<metric>"属性为"<STagName>"的指标标签已存在 	  
	When 清除指标类为"<kpiclass>"，指标为"<metric>"的指标标签
	Then 指标类为"<kpiclass>"，指标为"<metric>"的标签清除成功
	When 删除指标为"<metric>"的性能数据  
    Then 指标为"<metric>"的性能数据删除成功
	Examples: 
		|kpiclass|metric|attr|STagName|perfdate|
		|KCPU|kyn_pmv|mid|中间值|{ "ciCode": "wyn1","instance": "CPU","kpiclass": "KCPU","metric": "kyn_pmv","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "92"}|
		
		

Scenario Outline: 标准标签的增删 
	When 新建名称为"<STagName>"的标准标签 
	Then 成功新建名称为"<STagName>"的标准标签 
	And 再次新建名称为"<STagName>"的标准标签失败,kw="<kw>" 
	When 删除名称为"<STagName>"的标准标签 
	Then 成功删除名称为"<STagName>"的标准标签 
	Examples: 
		| commons           | STagName   |kw| 
		| 正常字符                                      | 业务服务器              |标签[业务服务器]已存在| 
		| 标签_特殊字符                           | @#￥%……&&**（            |标签[@#￥%……&&**（]已存在|
		| 标签_最大中文长度15    |集群服务器物理机虚拟机业务系统服务|标签[集群服务器物理机虚拟机业务系统服务]已存在|
		

Scenario Outline: 指标标签管理_指标搜索 
	When 搜索名称包含关键字"<searchKey>"的指标 
	Then 包含"<searchKey>"关键字的指标全部搜索出来 
	Examples: 
		| case  | searchKey     |      
		| 全匹配      |  kyn_pmv      |
		| 大小写      |  Kyn          |      
		| 空              |               |
		| 下划线      | _             |
		| 不存在的  | kyn1234567    |
		| 特殊字符  | @             |
		| 特殊字符  | &             |
		| 特殊字符  | %             |
#	Scenario Outline: 清除指标 数据
#    When 清除指标类为"<kpiclass>"，指标为"<metric>"的指标数据
#	Then 指标类为"<kpiclass>"，指标为"<metric>"的数据清除成功
#		Examples: 
#		|kpiclass|metric|
#		|KCPU|kyn_pmv|
		