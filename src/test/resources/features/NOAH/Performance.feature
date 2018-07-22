@NOAH
Feature: NOAH_推送性能指标_Performance

  Scenario Outline: Performance_推送性能
    When 给CI为"<ciName>"推送指标"<kpiName>",描述为"<kpiDesc>",值为"<kpiValue>"

    Examples: 
      | common | ciName | kpiName | kpiDesc | kpiValue |
      | noah推送 | 市场     | 响应时间    | 描述      |        5 |
