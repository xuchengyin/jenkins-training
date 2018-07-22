@PMV
Feature: PMV_推送性能数据_Performance

  Scenario Outline: Performance_推送性能
    When 推送性能数据"<perfdate>"
    Examples: 
    |perfdate|
    |{ "ciCode": "wyn1","instance": "CPU","kpiclass": "kCPU5","metric": "test_pmv","metricAttrs": {"kpiDesc": "kpi描述信息","mid": "052","sid": "08"},"value": 13.5}|

