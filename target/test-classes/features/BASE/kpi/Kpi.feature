@BASE
@delKpi
Feature: BASE_指标模型_Kpi

  @Smoke
  Scenario Outline: Kpi_新建指标、更改指标、删除指标
    Given 系统中已存在如下ci分类:"Application,Cluster"
    When 创建名称为"<kpiCode>"，别名为"<kpiName>"，指标描述为"<kpiDesc>"，单位为"<unitName>"的指标
    Then 系统中存在名称为"<kpiCode>"，别名为"<kpiName>"，指标描述为"<kpiDesc>"，单位为"<unitName>"的指标
    When 将指标名称"<kpiCode>"的指标修改指标的名称为"<updateKpiCode>"，指标别名为"<updateKpiName>"，指标描述为"<updateKpiDesc>"，单位为"<updateUnitName>"的指标
    Then 指标的名称为"<updateKpiCode>"，指标别名为"<updateKpiName>"，指标描述为"<updateKpiDesc>"，单位为"<updateUnitName>"的指标修改成功
    When 删除名称为"<updateKpiCode>"的指标
    Then 系统中不存在名称为"<updateKpiCode>"的指标

    Examples: 
      | common         | kpiCode                                  | kpiName                                  | kpiDesc   | unitName                                           | updateKpiCode                            | updateKpiName | updateKpiDesc | updateUnitName |
      | 正常数据           | 正yu符串123                                 | 别名                                       | 测试指标      | K                                                  | 修改后指标名                                   | 修改后别名         | 修改后描述         | 修改后单位UUU       |
      | 特殊字符           | @&_--  $!@#%                             | *&                                       | 测试指标11222 | U                                                  | 修改后指标名22                                 | 修改后别名22       | 修改后描述23333    | 修改后单位UUU11111  |
      | 只有名字_修改为最大中文字符 | 只有名字                                     |                                          |           |                                                    | 测试指标名称最大中文字符四十个测试指标名称最大中文字符四十个测试指标名称最大中文 |               |               |                |
      | 修改不变           | 修改不变                                     | 修改不变                                     | 修改不变      | 修改不变                                               | 修改不变                                     | 修改不变          | 修改不变          |                |
      | 指标名称_中文_最大字符40 | 测试指标名称最大中文字符四十个测试指标名称最大中文字符四十个测试指标名称最大中文 | 修改不变                                     | 修改不变      | 修改不变                                               | 修改不变                                     |               |               |                |
      | 指标别名_中文_最大字符40 | 别名最大字符                                   | 测试指标别名最大中文字符四十个测试指标别名最大中文字符四十个测试指标别名最大中文 | 修改不变      | 修改不变                                               | 修改不变                                     | 修改不变          | 修改不变          |                |
      | 指标描述_中文_最大字符40 | 修改不变                                     | 修改不变                                     | 修改不变      | 修改不变                                               | 修改不变                                     | 修改不变          | 修改不变          |                |
      | 单位_中文_最大字符5    | 单位最大中文字符                                 | 修改不变                                     | 修改不变      | 测试指标单位最大中文字符四十个测试指标单位最大中文字符四十个测试指标单位最大中文测试指标单位最大中文 | 修改不变                                     | 修改不变          | 修改不变          |                |

  @Smoke 
  Scenario Outline: Kpi_新建指标并关联对象组、更改对象组、删除对象组
    Given 系统中已存在如下ci分类:"Application,Cluster,s@&_-"
    And 系统中已存在如下tag分类:"APP,Clu,@&_-~！@#￥%……&*（））——，,【、；‘。、《》？’】234567是￥"
    And 系统中已存在如下关系分类:"AppRlt,特殊"
    When 创建含有对象组的名称为"<kpiCode>"，别名为"<kpiName>"，指标描述为"<kpiDesc>"，单位为"<unitName>"，分类对象组为"<classGroups>"，标签对象组为"<tagGroups>"，关系对象组为"<rltGroups>"的指标
    Then 系统中存在含有对象组的名称为"<kpiCode>"，别名为"<kpiName>"，指标描述为"<kpiDesc>"，单位为"<unitName>"，分类对象组为"<classGroups>"，标签对象组为"<tagGroups>"，关系对象组为"<rltGroups>"的指标
    When 将指标名称"<kpiCode>"的指标修改分类对象组为"<updateClassGroups>"，标签对象组为"<updateTagGroups>"，关系对象组为"<updateRltGroups>"的指标
    Then 指标的名称为"<kpiCode>"，分类对象组为"<updateClassGroups>"，标签对象组为"<updateTagGroups>"，关系对象组为"<updateRltGroups>"的指标修改成功
    When 删除名称为"<kpiCode>"的指标
    Then 系统中不存在名称为"<kpiCode>"的指标

    Examples: 
      | common | kpiCode  | kpiName | kpiDesc | unitName | classGroups | tagGroups | rltGroups | updateClassGroups   | updateTagGroups | updateRltGroups |
      | 正常数据   | 正yu符串123 | 别名      | 测试指标    | K        | Application | APP、Clu   | AppRlt、特殊 | Application、Cluster | APP             |                 |
      | 特殊字符   | 只有名字     |         |         |          |             |           |           | Application、Cluster | APP             | AppRlt          |

  @Smoke @Debug
  Scenario Outline: Kpi_新建含有指标状态的指标
    Given 系统中已存在如下ci分类:"Application,Cluster,s@&_-"
    And 系统中已存在如下tag分类:"APP,Clu,@&_-~！@#￥%……&*（））——，,【、；‘。、《》？’】234567是￥"
    And 系统中已存在如下关系分类:"AppRlt,特殊"
    When 创建含有指标状态的指标，名称为"<kpiCode>"，别名为"<kpiName>"，指标描述为"<kpiDesc>"，单位为"<unitName>"，分类对象组为"Application"，标签对象组为"APP"，关系对象组为"AppRlt",指标状态如下：
      | 值     | 图标目录名 | 图标名         |
      |     3 | 常用图标  | Application |
      | 数据库图标 | 常用图标  | DB          |
    Then 系统中存在含有如下指标，名称为"<kpiCode>"，别名为"<kpiName>"，指标描述为"<kpiDesc>"，单位为"<unitName>"，分类对象组为"Application"，标签对象组为"APP"，关系对象组为"AppRlt"，指标状态如下：
      | 值     | 图标目录名 | 图标名         |
      |     3 | 常用图标  | Application |
      | 数据库图标 | 常用图标  | DB          |
    When 修改指标状态，名称为"<kpiCode>",指标状态如下：
      | 值     | 图标目录名 | 图标名         |
      |     3 | 常用图标  | Application |
      | 数据库图标 | 常用图标  | DB          |
    Then 成功修改指标状态，名称为"<kpiCode>",指标状态如下：
      | 值     | 图标目录名 | 图标名         |
      |     3 | 常用图标  | Application |
      | 数据库图标 | 常用图标  | DB          |
    When 将指标名称"<kpiCode>"的指标修改分类对象组为""，标签对象组为""，关系对象组为""的指标
    Then 指标的名称为"<kpiCode>"，分类对象组为""，标签对象组为""，关系对象组为""的指标修改成功
    When 删除名称为"<kpiCode>"的指标
    Then 系统中不存在名称为"<kpiCode>"的指标

    Examples: 
      | common | kpiCode | kpiName | kpiDesc | unitName |
      | 指标状态指标 | 指标状态    | 别名      | 测试指标    | K        |

  @Smoke
  Scenario Outline: Kpi_指标搜索
    When 创建名称为"<kpiCode>"，别名为"<kpiName>"，指标描述为"<kpiDesc>"，单位为"<unitName>"的指标
    And 搜索名称包含<searchKey>的指标模型
    Then 包含<searchKey>关键字的的指标模型全部搜索出来
    When 删除名称为"<kpiCode>"的指标
    Then 系统中不存在名称为"<kpiCode>"的指标

    Examples: 
      | common  | kpiCode  | kpiName  | kpiDesc | unitName | searchKey |
      | 指标名部分匹配 | 正常字符串123 | 别名       | 测试指标    | K        | %字符串1%    |
      | 指标名全匹配  | 指标名称全匹配  | 指标名称全匹配  | 测试2     | U        | 指标名称全匹配   |
      | 指标名匹配符  | 测试匹配符$#  | 别名1234   | 测标11222 | U        | %配符$#%    |
      | 别名部分匹配  | 指标名      | 字符别名1234 | 测标11222 | U        | %别名1234%  |

  Scenario: Kpi_下载模板、导入、导出
    When 指标模型下载指标模板
    Then 指标模型按如下顺序成功下载指标模板:
      | commons | kpiCode | kpiName | kpiDesc | unitName | objName | tagName | rltName |
      | 正向      | 指标名     | 别名      | 描述      | 单位       | 分类对象组   | 标签对象组   | 关系对象组   |
    When 指标模型导入名称为"指标模型数据-20180315-221722.xls"指标模板
    Then 指标模型成功导入名称为"指标模型数据-20180315-221722.xls"指标模板
    When 指标模型导出指标模板
    Then 指标模型按顺序成功导出指标模板
    And 删除以下指标模板:
      | commons | kpiCode      |
      | 正向      | 测试导入         |
      | 正向      | 测试导入wrt$#@11 |
      | 正向      | 测试导入WYUTI22  |
      | 正向      | 测试导入auiyt33  |
