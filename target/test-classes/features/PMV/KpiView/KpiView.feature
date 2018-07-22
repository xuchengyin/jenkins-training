@PMV
Feature: PMV_监控指标_KpiView 
  @Smoke
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
	
Scenario Outline: 推送性能数据 
	When 推送性能数据"<perfdate>" 
	Examples: 
		|perfdate|
		|{ "ciCode": "sz_pmv_1","instance": "CPU","kpiclass": "KCPU","metric": "kyn_pmv","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "92"}| 
		|{ "ciCode": "sz_pmv_2","instance": "CPU","kpiclass": "KCPU","metric": "test_pmv","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "16.5"}| 
		|{ "ciCode": "sz_pmv_3","instance": "CPU","kpiclass": "KCPU","metric": "成功率","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "72"}| 
		|{ "ciCode": "sz_pmv_4","instance": "CPU","kpiclass": "KCPU","metric": "Avg_CPU_Busy","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "3.5"}| 
		|{ "ciCode": "sz_pmv_5","instance": "CPU","kpiclass": "KCPU","metric": "Avg_CPU_Sys","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "82"}| 
		|{ "ciCode": "sz_pmv_6","instance": "CPU","kpiclass": "KCPU","metric": "交易量","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "62"}| 
		|{ "ciCode": "sz_pmv_7","instance": "CPU","kpiclass": "KCPU","metric": "响应时间","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "52"}| 
		|{ "ciCode": "sz_pmv_8","instance": "CPU","kpiclass": "KCPU","metric": "Avg_CPU_Usr","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "42"}| 
		|{ "ciCode": "sz_pmv_9","instance": "CPU","kpiclass": "KCPU","metric": "吞吐量","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "10.9"}| 
		|{ "ciCode": "sz_pmv_10","instance": "CPU","kpiclass": "KCPU","metric": "磁盘空间","metricAttrs":{"kpiDesc":"kpi描述信息","snemid":"12","mid":"052","sneid":"12","sid":"08"},"value": "99"}| 
		
Scenario: 监控指标 
	When  查询监控指标列表 
	Then  成功查询全部指标 
	When  查看指标"kyn_pmv"的详细信息 
	Then  成功查询指标"kyn_pmv"的详细信息 
	
Scenario Outline: 清除性能数据 
	When 删除指标为"<metric>"的性能数据 
	Then 指标为"<metric>"的性能数据删除成功 
	Examples: 
		|metric|
		|kyn_pmv| 
		|test_pmv| 
		|成功率| 
		|Avg_CPU_Busy| 
		|Avg_CPU_Sys| 
		|交易量| 
		|响应时间| 
		|Avg_CPU_Usr| 
		|吞吐量| 
		|磁盘空间| 
		
		
Scenario: 清除分类 
 
    When 删除如下分类及数据:"PC虚拟机"
    Then 如下分类数据删除成功:"PC虚拟机"
	