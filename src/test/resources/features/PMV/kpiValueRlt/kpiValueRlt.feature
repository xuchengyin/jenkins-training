  @PMV
  Feature: PMV_指标值映射_kpiValueRlt

  Scenario Outline: 推送性能数据、添加值映射，再次添加值映射，
    When 推送性能数据"<perfdate>"        
    Then 存在指标类为"<kpiclass>",指标为"<metric>",指标值为"<key>"的数据
    When 给指标类为"<kpiclass>"指标为"<metric>"的数据添加值映射
     |key|value|
     |null|30|
    Then 验证指标类为"<kpiclass>"指标为"<metric>"的数据添加值映射成功
     |key|value|
     |null|30|   
    When 推送性能数据"<perfdate>"     
    Then 验证存在指标类为"<kpiclass>"指标为"<metric>"指标值为"30"的性能数据       
    When 删除指标为"<metric>"的性能数据  
    Then 指标为"<metric>"的性能数据删除成功     
    When 给指标类为"<kpiclass>"指标为"<metric>"的数据添加值映射
     |key|value|
     |null|99|
    Then 验证指标类为"<kpiclass>"指标为"<metric>"的数据添加值映射成功
     |key|value|
     |null|99|
    When 推送性能数据"<perfdate>"         
    Then 验证存在指标类为"<kpiclass>"指标为"<metric>"指标值为"99"的性能数据     
    When 删除指标为"<metric>"的性能数据  
    Then 指标为"<metric>"的性能数据删除成功  
    When 删除指标类为"<kpiclass>"指标为"<metric>"指标值为"null"的值映射
    Then 指标类为"<kpiclass>"指标为"<metric>"指标值为"null"的值映射删除成功
    Examples: 字符类型校验
    |kpiclass |metric|key|perfdate|
    |KCPU|kyn_pmv1 |null|{ "ciCode": "wyn1","instance": "CPU","kpiclass": "KCPU","metric": "kyn_pmv1","metricAttrs": {"kpiDesc": "kpi描述信息","mid": "052","sid": "08"},"value": "null"}|

  Scenario Outline: 清除指标信息
    When 删除指标类为"<kpiclass>",指标为"<metric>"的char数据  
    Then 指标类为"<kpiclass>",指标为"<metric>"的char数据删除成功
     Examples: 字符类型校验
     |kpiclass |metric|
     |KCPU|kyn_pmv1 |

