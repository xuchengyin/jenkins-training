@CMV
@delCiRltRule
Feature: CMV_影响分析_CiRltRule

  Scenario Outline: CiRltRule_有源朋友圈视图规则增改删
    Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
    And 系统中已存在如下关系分类:"AppRlt"
    When 新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
    Then 成功新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
    When 修改"Application"到"Cluster"关系为"AppRlt"的朋友圈规则"<friendName>"为修改后朋友圈规则"<updateFriendName>"
    Then 成功修改"Application"到"Cluster"关系为"AppRlt"的朋友圈规则"<friendName>"为修改后朋友圈规则"<updateFriendName>"
    When 删除朋友圈规则"<updateFriendName>"
    Then 成功删除朋友圈规则"<updateFriendName>"

    Examples: 
      | common | friendName       | updateFriendName |
      | 正常规则   | 新建朋友圈规则          | 修改后朋友圈规则         |
      | 英文数字   | vy35664eeeee     | 修改后euuytt67uiop  |
      | 特殊字符   | %$#@!规则          | 修改后&*()          |
      | 最大长度   | 最大长度uuutruewetyu | 修改后最大长度uuutru    |
      | 最大长度中文长度20   | 测试有源朋友圈名称最大中文字符二十个汉字 | 测试有源朋友圈名称最大中文字符二十个汉字    |

  Scenario Outline: CiRltRule_无源朋友圈视图规则增改删
    Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
    And 系统中已存在如下关系分类:"AppRlt"
    When 新建"Application"和"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
    Then 成功新建"Application"和"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
    When 修改"Application"和"Cluster"关系为"AppRlt"的朋友圈规则"<friendName>"为修改后朋友圈规则"<updatefriendName>"
    Then 成功修改"Application"和"Cluster"关系为"AppRlt"的朋友圈规则"<friendName>"为修改后朋友圈规则"<updatefriendName>"
    When 删除朋友圈规则"<updatefriendName>"
    Then 成功删除朋友圈规则"<updatefriendName>"

    Examples: 
      | common | friendName       | updatefriendName |
      | 正常规则   | 新建无源朋友圈规则        | 修改后无源朋友圈规则       |
      | 英文数字   | 无源vy35664eee     | 修改后无源t67uiop     |
      | 特殊字符   | 无源%$#@!规则        | 修改后无源&*()        |
      | 最大长度   | 最大无源uuutruewetyu | 修改后最大无源uuutru    |
      | 最大长度中文长度20   | 测试无源朋友圈名称最大中文字符二十个汉字 | 测试无源朋友圈名称最大中文字符二十个汉字    |

  Scenario Outline: CiRltRule_搜索朋友圈规则
    Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
    And 系统中已存在如下关系分类:"AppRlt"
    When 新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
    Then 成功新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<friendName>"
    When 新建"Application"和"s@&_-"关系为"AppRlt"的朋友圈规则"<nofriendName>"
    Then 成功新建"Application"和"s@&_-"关系为"AppRlt"的朋友圈规则"<nofriendName>"
    When 新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<searchFrindName>"
    Then 成功新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"<searchFrindName>"
    When 新建"Application"和"s@&_-"关系为"AppRlt"的朋友圈规则"<searchNofriendName>"
    Then 成功新建"Application"和"s@&_-"关系为"AppRlt"的朋友圈规则"<searchNofriendName>"
    When 搜索关键字为"<searchKey>"的朋友圈规则
    Then 成功搜索关键字为"<searchKey>"的朋友圈规则
    When 删除朋友圈规则"<friendName>"
    Then 成功删除朋友圈规则"<friendName>"
    When 删除朋友圈规则"<nofriendName>"
    Then 成功删除朋友圈规则"<nofriendName>"
    When 删除朋友圈规则"<searchFrindName>"
    Then 成功删除朋友圈规则"<searchFrindName>"
    When 删除朋友圈规则"<searchNofriendName>"
    Then 成功删除朋友圈规则"<searchNofriendName>"

    Examples: 
      | common | friendName | nofriendName | searchFrindName | searchNofriendName | searchKey |
      | 部分匹配   | 新建有源朋友圈规则  | 无源朋友圈规则      | 搜索朋友圈规则         | 搜索无源朋友圈规则          | 朋友圈规则     |
      | 全匹配    | 新建规则       | 测试规则1        | 测试规则2           | 测试规则3              | 新建规则      |
      | 空搜索    | 空搜索规则      | 空规则1         | 空规则2            | 空规则3               |           |
      | 特殊字符   | 规则1$#@     | 规则2$#@       | 规则3$#@          | 规则4$#@             | $#@       |

  Scenario Outline: CiRltRule_搜索CI分类
    Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
    When 搜索关键字为"<searchKey>"的CI分类
    Then 成功搜索关键字为"<searchKey>"的CI分类

    Examples: 
      | common | searchKey |
      | 部分匹配   | cation    |
      | 全匹配    | Cluster   |
      | 特殊字符   | s@&_-     |

  Scenario Outline: CiRltRule_搜索CI
    Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
    When 新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"搜索CI朋友圈规则"
    Then 成功新建"Application"到"s@&_-"关系为"AppRlt"的朋友圈规则"搜索CI朋友圈规则"
    When 搜索CI分类为"Application"关键字为"<searchKey>"的CI
    Then 成功搜索CI分类为"Application"关键字为"<searchKey>"的CI

    Examples: 
      | common | searchKey |
      | 全匹配    | 信用卡工单1    |
      | 部分匹配   | 交换        |
      | 特殊字符   | %$#@      |
      | 空搜索    |           |

  Scenario: CiRltRule_故障诊断
    Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
    When 新建"Application"到"Cluster"关系为"AppRlt"的朋友圈规则"故障诊断朋友圈规则"
    Then 成功新建"Application"到"Cluster"关系为"AppRlt"的朋友圈规则"故障诊断朋友圈规则"
    When 给事件源为"5",SourceEventID为"60",CI为"信用卡工单1"推送告警"响应时间",事件级别为"1",值为"Critical",状态为"1",主题为"业务响应时间达到 143ms，已超过设定的阈值 125ms！",时间为"2017-12-12 15:53:12"
    When 给事件源为"5",SourceEventID为"60",CI为"SZ_FrontServer1"推送告警"响应时间",事件级别为"1",值为"Critical",状态为"1",主题为"业务响应时间达到 143ms，已超过设定的阈值 125ms！",时间为"2017-12-12 15:53:12"
    When 给CI为"信用卡工单1,SZ_FrontServer1"做故障诊断
    Then 成功为CI"信用卡工单1,SZ_FrontServer1"做故障诊断
    When 删除朋友圈规则"故障诊断朋友圈规则"
    Then 成功删除朋友圈规则"故障诊断朋友圈规则"

  Scenario: CiRltRule_无源朋友圈规则_CI关系
    Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
    When 新建"Application"和"Cluster"关系为"AppRlt"的朋友圈规则"无源朋友圈CI关系"
    Then 成功新建"Application"和"Cluster"关系为"AppRlt"的朋友圈规则"无源朋友圈CI关系"
    When 查询朋友圈规则"无源朋友圈CI关系"的CI关系
    Then 成功查询朋友圈规则"无源朋友圈CI关系"的CI关系
    When 删除朋友圈规则"无源朋友圈CI关系"
    Then 成功删除朋友圈规则"无源朋友圈CI关系"

  @Smoke @delKpiTpl @delKpi
  Scenario Outline: CiRltRule_有源朋友圈规则_监控指标模型
    When 创建名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    Then 系统中存在名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    When 创建模板名称为"<tplName>"，别名为"dddd"，模板描述为"3333"，指标应用类型为"1"，指标模型名称为"指标模型"，分类对象组为""，标签对象组为""的指标模板
    Then 系统中存在模板名称为"<tplName>"，别名为"dddd"，模板描述为"3333"，指标应用类型为"1"，指标模型名称为"指标模型"，分类对象组为""，标签对象组为""的指标模板
    When 给CI分类"<ciClsName>"挂载指标模板"<tplName>"
    Then 成功给CI分类"<ciClsName>"挂载指标模板"<tplName>"
    When 新建"<ciClsName>"到"s@&_-"关系为"AppRlt"的朋友圈规则"搜索CI朋友圈规则"
    Then 成功新建"<ciClsName>"到"s@&_-"关系为"AppRlt"的朋友圈规则"搜索CI朋友圈规则"
    When 分类"Application"中的所有CI监控指标模板

    Examples: 
      | case | ciClsName   | tplName |
      | 分类全名 | Application |   66666 |

  @cleanRlt @cleanRltCls @Smoke
  Scenario: 搜索朋友圈
    When 创建名称为"ciRltRule测试"的关系分类
    When 新建分类"s@&_-"到分类"Application"的关系"ciRltRule测试"
    Then 成功新建分类"s@&_-"到分类"Application"的关系"ciRltRule测试"
    When 新建分类"Application"到分类"Cluster"的关系"ciRltRule测试"
    Then 成功新建分类"Application"到分类"Cluster"的关系"ciRltRule测试"
    When 创建名称为"ciRltRule测试1"的关系分类
    When 新建分类"s@&_-"到分类"Application"的关系"ciRltRule测试1"
    Then 成功新建分类"s@&_-"到分类"Application"的关系"ciRltRule测试1"
    When 新建分类"Application"到分类"Cluster"的关系"ciRltRule测试1"
    Then 成功新建分类"Application"到分类"Cluster"的关系"ciRltRule测试1"
    Then 创建如下CI关系:
      | 关系名称        | sourceCiCode | targetCiCode |
      | ciRltRule测试 | six          | 400网关1       |
    Then 创建如下CI关系:
      | 关系名称         | sourceCiCode | targetCiCode |
      | ciRltRule测试1 | 400网关1       | 订单-Tomcat1   |
    Then 创建如下CI关系:
      | 关系名称        | sourceCiCode | targetCiCode |
      | ciRltRule测试 | six          | 后台流程1        |
    Then 创建如下CI关系:
      | 关系名称         | sourceCiCode | targetCiCode |
      | ciRltRule测试1 | 后台流程1        | 订单-Tomcat1   |
    #以下的six/six为ciCode,无论多少行参数，这两个东西要保持统一
    Then 查询如下CI关系路径:
      | startCiCode | endCiCode  | ciClassRltPaths                                                |
      | six         | 订单-Tomcat1 | ciRltRule测试:s@&_-:Application=ciRltRule测试1:Application:Cluster |
    #|six|订单-Tomcat1|ciRltRule测试:s@&_-:Application=ciRltRule测试1:Application:Cluster|
    #只验证ciRltLines部分
    Then 验证CI关系路径查询结果:
      | sourceClassName | targetClassName | sourceCiCode | targetCiCode | RltClassName |
      | s@&_-           | Application     | six          | 400网关1       | ciRltRule测试  |
      | Application     | Cluster         | 400网关1       | 订单-Tomcat1   | ciRltRule测试1 |
      | s@&_-           | Application     | six          | 后台流程1        | ciRltRule测试  |
      | Application     | Cluster         | 后台流程1        | 订单-Tomcat1   | ciRltRule测试1 |

      
      
      
	@cleanRlt @cleanRltCls @Debug
  Scenario: 网络寻路
    When 创建名称为"网络寻路测试"的关系分类
    When 新建分类"s@&_-"到分类"Application"的关系"网络寻路测试"
    Then 成功新建分类"s@&_-"到分类"Application"的关系"网络寻路测试"
    When 新建分类"Application"到分类"Cluster"的关系"网络寻路测试"
    Then 成功新建分类"Application"到分类"Cluster"的关系"网络寻路测试"
    When 创建名称为"网络寻路测试1"的关系分类
    When 新建分类"s@&_-"到分类"Application"的关系"网络寻路测试1"
    Then 成功新建分类"s@&_-"到分类"Application"的关系"网络寻路测试1"
    When 新建分类"Application"到分类"Cluster"的关系"网络寻路测试1"
    Then 成功新建分类"Application"到分类"Cluster"的关系"网络寻路测试1"
    Then 创建如下CI关系:
      | 关系名称        | sourceCiCode | targetCiCode |
      | 网络寻路测试 | six          | 400网关1       |
    Then 创建如下CI关系:
      | 关系名称         | sourceCiCode | targetCiCode |
      | 网络寻路测试1| 400网关1       | 订单-Tomcat1   |
    Then 创建如下CI关系:
      | 关系名称        | sourceCiCode | targetCiCode |
      | 网络寻路测试1 | 400网关1     | WEB-Nginx1        |


    When 用以下内容搜索网络寻路:
    #depth-1,2,3
    #网络寻路测试:网络寻路测试1
    |startCiCodes|endCiCodes|filterCiClassNames|rltLvls|depth|
    |six|订单-Tomcat1:WEB-Nginx1|Application|2:1|3|
    Then 用以下内容验证网络寻路:
    |sourceCiCode|rltName|targetCiCode|
    |six|网络寻路测试|400网关1|
    |400网关1|网络寻路测试1|订单-Tomcat1|
    |400网关1|网络寻路测试1|WEB-Nginx1|
    
     @cleanRlt @cleanRltCls
    Scenario: 影响分析查询
    When 创建名称为"影响分析查询"的关系分类
    When 新建分类"s@&_-"到分类"Application"的关系"影响分析查询"
    Then 成功新建分类"s@&_-"到分类"Application"的关系"影响分析查询"
    When 新建分类"Application"到分类"Cluster"的关系"影响分析查询"
    Then 成功新建分类"Application"到分类"Cluster"的关系"影响分析查询"
    When 创建名称为"影响分析查询1"的关系分类
    When 新建分类"s@&_-"到分类"Application"的关系"影响分析查询1"
    Then 成功新建分类"s@&_-"到分类"Application"的关系"影响分析查询1"
    When 新建分类"Application"到分类"Cluster"的关系"影响分析查询1"
    Then 成功新建分类"Application"到分类"Cluster"的关系"影响分析查询1"
    Then 创建如下CI关系:
      | 关系名称        | sourceCiCode | targetCiCode |
      | 影响分析查询 | six          | 400网关1       |
    Then 创建如下CI关系:
      | 关系名称         | sourceCiCode | targetCiCode |
      | 影响分析查询1| 400网关1       | 订单-Tomcat1   |
    Then 创建如下CI关系:
      | 关系名称        | sourceCiCode | targetCiCode |
      | 影响分析查询1 | 400网关1     | WEB-Nginx1        |
      

      
    When 保存可视化建模中影响分析关系模型热点分类为"s@&_-"，分类关系如下:
      | common | sourceClassName | targetClassName | rltClassName |
      | 描述     |    s@&_-     |   Application     | 影响分析查询    |
      
    When 保存可视化建模中影响分析关系模型热点分类为"Application"，分类关系如下:
      | common | sourceClassName | targetClassName | rltClassName |
      | 描述     |    Application     |    Cluster    | 影响分析查询1    |
      
    When 保存可视化建模中影响分析关系模型热点分类为"Cluster"，分类关系如下:
      | common | sourceClassName | targetClassName | rltClassName |
      | 描述     |    Application     |    Cluster    | 影响分析查询1    |

	#层级为1，查询一层，其他查询所有
	When 查询ciCode为"400网关1",层级为"2"的影响分析
	Then 用以下数据验证影响分析查询结果:
	|rltName|sourceCiCode|targetCiCode|
	|影响分析查询|six|400网关1|
	|影响分析查询1|400网关1|订单-Tomcat1|
	|影响分析查询1|400网关1|WEB-Nginx1|
