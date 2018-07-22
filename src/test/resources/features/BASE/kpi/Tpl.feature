@BASE
@delKpiTpl @delKpi
Feature: BASE_指标模板_Tpl

  @Smoke
  Scenario Outline: Tpl_新建指标模板、更改指标模板、删除指标模板
    When 创建名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    Then 系统中存在名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    When 创建模板名称为"<tplName>"，别名为"<tplAlias>"，模板描述为"<tplDesc>"，指标应用类型为"<kpiUseType>"，指标模型名称为"<kpiName>"，分类对象组为"<classGroups>"，标签对象组为"<tagGroups>"的指标模板
    Then 系统中存在模板名称为"<tplName>"，别名为"<tplAlias>"，模板描述为"<tplDesc>"，指标应用类型为"<kpiUseType>"，指标模型名称为"<kpiName>"，分类对象组为"<classGroups>"，标签对象组为"<tagGroups>"的指标模板
    When 创建名称为"更新指标模型"，别名为"更新模型"，指标描述为"描述"，单位为"U"的指标
    Then 系统中存在名称为"更新指标模型"，别名为"更新模型"，指标描述为"描述"，单位为"U"的指标
    When 模板名称为"<tplName>"的指标模板修改模板名称为"<updateTplName>"，别名为"<updateTplAlias>"，模板描述为"<updateTplDesc>"，指标应用类型为"<updateKpiUseType>"，指标模型名称为"<updateKpiName>"，分类对象组为"<updateClassGroups>"，标签对象组为"<updateTagGroups>"的指标模板
    Then 模板名称为"<updateTplName>"，别名为"<updateTplAlias>"，模板描述为"<updateTplDesc>"，指标应用类型为"<updateKpiUseType>"，指标模型名称为"<updateKpiName>"，分类对象组为"<updateClassGroups>"，标签对象组为"<updateTagGroups>"的指标模板修改成功
    When 删除模板名称为"<updateTplName>"的指标模板
    Then 系统中不存在模板名称为"<updateTplName>"的指标模板
    When 删除名称为"指标模型"的指标
    Then 系统中不存在名称为"指标模型"的指标
    When 删除名称为"更新指标模型"的指标
    Then 系统中不存在名称为"更新指标模型"的指标

    Examples: 
      | common         | tplName                                  | tplAlias                                 | tplDesc   | kpiUseType | kpiName | updateTplName | updateTplAlias | updateTplDesc | updateKpiUseType | updateKpiName | classGroups | tagGroups | updateClassGroups   | updateTagGroups |  |
      | 正常数据           | 指标模板1122yyy                              | 指标模板别名23yy                               | 模板描述      |          1 | 指标模型    | 修改后指标模板       | 修改后模板别名        | 修改后模板描述       |                2 |               | Application | APP、Clu   | Application、Cluster | APP             |  |
      | 特殊字符           | 指标模板模板1￥#@%&模                 | 指标模板别名23yy￥#@&*指标模tt                     | 模板描述22333 |          2 |         |特殊字符    | 修改后模板别名123     | 修改后模板描述1122   |                1 | 指标模型          |             |           | Application、Cluster | APP             |  |
      | 只有名字           | 只有名字             |                                          |           |            |         | 只有名字    |                |               |                  |               |             |           |                     |                 |  |
      | 模板名称_中文_最大字符40 | 测试模板名称最大中文字符四十个测试模板名称最大中文字符四十个测试模板名称最大中文 | 指标模板别名23yy￥#@&*指标模tt                     | 模板描述22333 |          2 |         | 修改后指标模板123    | 修改后模板别名123     | 修改后模板描述1122   |                1 |               |             | APP       | Application、Cluster | APP             |  |
      | 模板别名_中文_最大字符40 | 指标模板模板111112222222￥#@%&模                 | 测试模板别名最大中文字符四十个测试模板别名最大中文字符四十个测试模板别名最大中文 | 模板描述22333 |          2 |         | 修改   | 修改后模板别名123     | 修改后模板描述1122   |                1 |               | Application | APP、Clu   |                     |                 |  |
      | 模板描述_中文_最大字符40 | 指标模板模板111112222222￥#@%&模                 | 指标模板别名23yy￥#@&*指标模tt                     | 模板描述22333 |          2 |         | 最大字符   | 修改后模板别名123     | 修改后模板描述1122   |                1 |               | Application | APP、Clu   |                     |                 |  |

  Scenario Outline: Tpl_指标搜索
    When 创建名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    Then 系统中存在名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    When 创建模板名称为"<tplName>"，别名为"<tplAlias>"，模板描述为"<tplDesc>"，指标应用类型为"<kpiUseType>"，指标模型名称为"指标模型"，分类对象组为""，标签对象组为""的指标模板
    Then 系统中存在模板名称为"<tplName>"，别名为"<tplAlias>"，模板描述为"<tplDesc>"，指标应用类型为"<kpiUseType>"，指标模型名称为"指标模型"，分类对象组为""，标签对象组为""的指标模板
    And 搜索名称包含"<searchKey>"的指标模板
    Then 包含"<searchKey>"关键字的指标模板全部搜索出来
    When 删除模板名称为"<tplName>"的指标模板
    Then 系统中不存在模板名称为"<tplName>"的指标模板
    When 删除名称为"指标模型"的指标
    Then 系统中不存在名称为"指标模型"的指标

    Examples: 
      | common     | tplName                  | tplAlias             | tplDesc      | kpiUseType | searchKey                |
      | 指标模板名部分匹配  | 指标模板12yyy                | 指标模板别名23yy           | 模板描述         |          1 | 模板12                     |
      | 指标模板名全部匹配  | 指标模板模板111112222222￥#@%&模 | 指标模板别名23yy￥#@&*指标模tt | 模板描述22333    |          2 | 指标模板模板111112222222￥#@%&模 |
      | 指标模板别名部分匹配 | 指标模板456                  | 指标模板别名675yyy         | 模板描述5tyytppp |          1 | 模板别名675y                 |
      | 指标模板别名全部匹配 | 指标模板rtye                 | 指标模板别名23yy￥#@&*      | 模板描述TyyRR    |          2 | 指标模板别名23yy￥#@&*          |

      #Scenario: Tpl_删除指标模板中的指标_对象_标签
      	#When 创建名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
      	#When 创建模板名称为"<tplName>"，别名为"<tplAlias>"，模板描述为"<tplDesc>"，指标应用类型为"<kpiUseType>"，指标模型名称为"<kpiName>"，分类对象组为"<classGroups>"，标签对象组为"<tagGroups>"的指标模板