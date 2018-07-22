@PMV 
Feature: PMV_标签管理_TagManagement 
Scenario: 导入分类 
	Given 在"业务领域"目录下,创建名称为"PC虚拟机"的ci分类,使用图标为"Default" 
	And 给"PC虚拟机"分类添加如下属性: 
		| 属性名称  | 属性类型  | 必填 | Lable |枚举值   |CI Code| 默认值   |
		| 编号          | 字符串      |  1 |   0   |     |   1   |      |
		| 名称          | 字符串      |  0 |   0   |     |   0   |      |
		| owner | 字符串      |  0 |   0   |     |   0   |      |
		| 类型          | 字符串      |  0 |   0   |     |   0   |      |
		| status| 字符串      |  0 |   0   |     |   0   |      |
		| 所属主机  | 字符串      |  0 |   0   |     |   0   |      |                  
	When 导入excel文档"pmv-CI-Data.xls" 
	Then 成功导入excel文档"pmv-CI-Data.xls" 
@delStandardTag 
Scenario Outline: 设置标签不排序 
	When 对CI分类"PC虚拟机"的属性设置标签，标准标签为空 
		|attr|checked|
		|名称|0|
	Then CI分类"PC虚拟机"的属性标签设置成功 
		|attr|
		|名称|
	When 对CI分类"PC虚拟机"的属性设置标签，标准标签为空 
		|attr|checked|
		|名称|1| 
	Then 显示对象分类"PC虚拟机" 
	And  存在对象分类"PC虚拟机"的属性"<attr>" 
	When 推送性能数据"<perfdate>" 
	Then  验证存在对象分类属性"<attr>"的标签值 
	When 新建名称为"<STagName>"的标准标签 
	Then 成功新建名称为"<STagName>"的标准标签 
	When 更新设置CI分类"PC虚拟机"的属性"<attr>"标准标签为"<STagName>"的标签，设置显示 
	Then 存在CI分类"PC虚拟机"的属性"<attr>"标准标签为"<STagName>"的标签 
	And  存在对象分类"PC虚拟机"的属性"<STagName>" 
	When 推送性能数据"<perfdate>" 
	Then  验证存在对象分类属性"<STagName>"的标签值 
	When 清除CI分类为"PC虚拟机"标签 
	Then CI分类为"PC虚拟机"的标签清除成功 
	When 删除指标为"<metric>"的性能数据 
	Then 指标为"<metric>"的性能数据删除成功 
	Examples: 
		|metric|attr|STagName|perfdate|
		|kyn_pmv|名称|Name|{ "ciCode": "sz_pmv_1","instance": "CPU","kpiclass": "KCPU","metric": "kyn_pmv","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "92"}|
		
		
	Scenario Outline: 标签管理_CI分类搜索 
		When 搜索名称包含关键字"<searchKey>"的CI分类 
		Then 包含"<searchKey>"关键字的CI分类全部搜索出来 
		Examples: 
			| case  | searchKey     |      
			| 全匹配      |  PMV_Demo      |
			| 大小写      |  PMV          |      
			| 空              |               |
			| 下划线      | _             |
			| 不存在的  | PMV1234567    |
			| 特殊字符  | @             |
			| 特殊字符  | &             |
			| 特殊字符  | %             |
		Scenario: 设置标签排序 
			When 对CI分类"PC虚拟机"的属性设置标签，标准标签为空 
				|attr|checked|
				|名称|1|
				|owner|1|
			Then CI分类"PC虚拟机"的属性标签设置成功 
				|attr|
				|名称|
				|owner|
			When 对CI分类"PC虚拟机"的属性设置标签，标准标签为空 
				|attr|checked|    
				|owner|1|
				|名称|1|
			Then 验证CI分类"PC虚拟机"的标签排序"owner"在"名称"之前 
			When 清除CI分类为"PC虚拟机"标签 
			Then CI分类为"PC虚拟机"的标签清除成功 
		Scenario: 清除分类 
			When 删除如下分类及数据:"PC虚拟机" 
			Then 如下分类数据删除成功:"PC虚拟机" 
	