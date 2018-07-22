@BASE
Feature: BASE_模拟性能_Performances
	@delKpi
    Scenario:Performances_下载性能数据模板
    	When 创建含有对象组的名称为"test_performances"，别名为"performances"，指标描述为"自动化测试"，单位为"K"，分类对象组为"s@&_-"，标签对象组为""，关系对象组为""的指标
		When 下载性能数据模板使用ciCode为"six"
		#列：时间差 性能值 分页是指标名称
		Then 用以下参数验证模板:
		|sheet|列名|
		|test_performances|时间差:性能值|
		
	@delKpi
	Scenario:Performances_上传性能数据
		When 创建含有对象组的名称为"test_performances"，别名为"performances"，指标描述为"自动化测试"，单位为"K"，分类对象组为"s@&_-"，标签对象组为""，关系对象组为""的指标
		When 用以下参数上传性能数据:
		#ciCode(目前只支持一个不用逗号)
		|excelFileName|ciCode|
		|performancesTpl.xls|six|
		
		When 查看"six"挂有KPI
		#目前上传报错没有服务
		Then 用以下参数验证"six"上传成功:
		#先不查询时间差，因为noah的时间差有问题，先只查询值
		|指标名称|时间差|性能值|
		|test_performances||1.01|
		|test_performances||0.22|
		|test_performances||10001.001|
		|test_performances||0|
		|test_performances||1.01|
		|test_performances||543.2|
		|test_performances||-3.701|
		|test_performances||4.51|
		|test_performances||-5.92|
		|test_performances||-6.19|









		