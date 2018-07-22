@DMV @delDiagram
Feature: DMV_绘图_新建视图_CI画图_右键菜单

  @Smoke @CleanCiCls
  Scenario Outline: Image_CI_RightClick_解除CI、创建CI
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    When 在视图"<diagramName>"中右键CI"<ciCode>"解除CI
    Then 在视图"<diagramName>"中右键CI"<ciCode>"成功解除CI
    When 在"业务领域"目录下,创建名称为"ciDataRlt"的ci分类,使用图标为"Default"
    And 给"ciDataRlt"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "ciDataRlt"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"ciDataRlt"添加如下数据:
      | commont | 字符串  |
      | 正常      | 创建ci |
    Then "ciDataRlt"分类数据添加成功
      | commont | 字符串  |
      | 正常      | 创建ci |
    When 在视图"<diagramName>"中右键CI"<ciCode>"创建CI"创建ci"坐标为"<x>""<y>"
    Then 在视图"<diagramName>"中右键CI"<ciCode>"成功创建CI"创建ci"
    When 给视图"<diagramName>"删除CI"重新创建ci"
    Then 给视图"<diagramName>"删除CI"重新创建ci"成功
    When 删除"ciDataRlt"分类及数据
    Then 系统中不存在名称为"ciDataRlt"的分类

    Examples: 
      | common | diagramName | ciClsName   | ciCode | x   | y   |
      | 视图     | CI画图视图      | Application | 信用卡决策1 | 400 | 600 |

  @Smoke
  Scenario Outline: Image_CI_RightClick_关系绘图
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"<ciCode>"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"<ciCode>"坐标为"400""600"
    Given 系统中已存在如下ci分类:"Application,Cluster"
    And 系统中已存在如下关系分类:"AppRlt"
    Given 在关系分类"AppRlt"下,存在源分类为"Application",源对象为"<ciCode>",目标分类为"Cluster",目标对象为"<targetCiCode>"的关系数据
    When 在视图"<diagramName>"中右键CI"<ciCode>"关系绘图
    Then 在视图"<diagramName>"中右键CI"<ciCode>"关系绘图成功

    Examples: 
      | common | diagramName | ciCode | targetCiCode |
      | 视图     | CI画图视图      | 信用卡决策1 | 数据库-Mysql1   |

  @Smoke
  Scenario Outline: Image_CI_RightClick_添加、修改、删除钻取视图
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    When 新建视图"<directDiagram>",描述为"<diagramDesc>",文件夹为"<dirName>"
    Then 成功新建视图"<directDiagram>"
    When 新建视图"被钻取视图2",描述为"<diagramDesc>",文件夹为"<dirName>"
    Then 成功新建视图"被钻取视图2"
    When 在视图"<diagramName>"中给CI"<ciCode>"添加钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中给CI"<ciCode>"成功添加钻取视图"<directDiagram>"
    When 在视图"<diagramName>"中给CI"<ciCode>"添加钻取视图"被钻取视图2"
    Then 在视图"<diagramName>"中给CI"<ciCode>"成功添加钻取视图"被钻取视图2"
    When 在视图"<diagramName>"中给CI"<ciCode>"删除钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中给CI"<ciCode>"成功删除钻取视图"<directDiagram>"
    When 给视图"<diagramName>"删除CI"<ciCode>"
    Then 给视图"<diagramName>"删除CI"<ciCode>"成功

    Examples: 
      | common | diagramName | ciCode | directDiagram |
      | 视图     | CI画图视图      | 信用卡决策1 | 被钻取视图         |

  @Smoke
  Scenario Outline: Image_CI_RightClick_挂载、编辑、取消挂载数据
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    When 视图"<diagramName>"中CI"<ciCode>"挂载数据"金融交换1"
    Then 视图"<diagramName>"中CI"<ciCode>"成功挂载数据"金融交换1"
    When 视图"<diagramName>"中CI"<ciCode>"挂载数据"后台流程1"
    Then 视图"<diagramName>"中CI"<ciCode>"成功挂载数据"后台流程1"
    When 视图"<diagramName>"中CI"<ciCode>"取消挂载数据
    Then 视图"<diagramName>"中CI"<ciCode>"成功取消挂载数据
    When 给视图"<diagramName>"删除CI"后台流程1"
    When 给视图"<diagramName>"删除CI"金融交换1"
    When 给视图"<diagramName>"删除CI"<ciCode>"

    Examples: 
      | common | diagramName | ciCode |
      | 视图     | CI画图视图      | 信用卡决策1 |

  @Smoke
  Scenario Outline: Image_CI_RightClick_CI根据条件挂载数据
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    When 视图"<diagramName>"中CI"<ciCode>"挂载数据分类为"Application"属性名为"Name"的属性值为"ATMP"的CI
    Then 视图"<diagramName>"中CI"<ciCode>"成功挂载数据分类为"Application"属性名为"Name"的属性值为"ATMP"的CI

    Examples: 
      | common | diagramName | ciCode |
      | 视图     | CI画图视图      | 信用卡决策1 |

  Scenario Outline: Image_CI_RightClick_Label位置（上下左右）
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    When 视图"<diagramName>"中CI"<ciCode>"label位置设置为"上"
    Then 视图"<diagramName>"中CI"<ciCode>"label位置成功设置为"上"
    When 视图"<diagramName>"中CI"<ciCode>"label位置设置为"左"
    Then 视图"<diagramName>"中CI"<ciCode>"label位置成功设置为"左"
    When 视图"<diagramName>"中CI"<ciCode>"label位置设置为"右"
    Then 视图"<diagramName>"中CI"<ciCode>"label位置成功设置为"右"
    When 视图"<diagramName>"中CI"<ciCode>"label位置设置为"下"
    Then 视图"<diagramName>"中CI"<ciCode>"label位置成功设置为"下"
    When 给视图"<diagramName>"删除CI"<ciCode>"
    Then 给视图"<diagramName>"删除CI"<ciCode>"成功

    Examples: 
      | common | diagramName | ciCode |
      | 视图     | CI画图视图      | 信用卡决策1 |

  @Smoke
  Scenario Outline: Image_CI_RightClick_添加至容器
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    When 在视图"<diagramName>"中将CI"<ciCode>"添加至容器
    Then 在视图"<diagramName>"中将CI"<ciCode>"添加至容器成功
    When 给视图"<diagramName>"删除CI"<ciCode>"
    Then 给视图"<diagramName>"删除CI"<ciCode>"成功

    Examples: 
      | common | diagramName | ciCode |
      | 视图     | CI画图视图      | 信用卡决策1 |

  Scenario Outline: Image_CI_RightClick_显示关系
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Given 在关系分类"AppRlt"下,存在源分类为"Application",源对象为"信用卡决策1",目标分类为"Cluster",目标对象为"数据库-Mysql1"的关系数据
    When 给视图"<diagramName>"增加CI"数据库-Mysql1"坐标为"500""700"
    Then 成功给视图"<diagramName>"增加CI"数据库-Mysql1"坐标为"500""700"
    #When 在视图"<diagramName>"中"信用卡决策1"到"three"的关系"AppRlt"右键隐藏关系
    #Then 在视图"<diagramName>"中"信用卡决策1"到"three"的关系"AppRlt"右键隐藏关系成功
    When 在视图"<diagramName>"中"信用卡决策1"右键显示到"数据库-Mysql1"的关系"AppRlt"
    Then 在视图"<diagramName>"中"信用卡决策1"右键显示到"数据库-Mysql1"的关系"AppRlt"成功

    Examples: 
      | common | diagramName | ciCode |
      | 视图     | CI画图视图      | 信用卡决策1 |

  
  Scenario Outline: Image_CI_RightClick_还原关系
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"<ciCode>"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"<ciCode>"坐标为"400""600"
    Given 在关系分类"AppRlt"下,存在源分类为"Application",源对象为"信用卡决策1",目标分类为"Cluster",目标对象为"数据库-Mysql1"的关系数据
    When 给视图"<diagramName>"增加CI"<targetCiCode>"坐标为"500""700"
    Then 成功给视图"<diagramName>"增加CI"<targetCiCode>"坐标为"500""700"
    When 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"右键删除关系
    Then 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"右键删除关系成功
    When 在视图"<diagramName>"中"<ciCode>"右键还原与"<targetCiCode>"的关系
    Then 成功在视图"<diagramName>"中"<ciCode>"右键还原与"<targetCiCode>"的关系

    Examples: 
      | common | diagramName | ciCode | targetCiCode |
      | 视图     | CI画图视图      | 信用卡决策1 | 数据库-Mysql1         |

  @Smoke @delDiagram
  Scenario Outline: Image_CI_RightClick_查看配置
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡决策1"坐标为"400""600"
    When 在视图"<diagramName>"中查看配置
    Then 成功在视图"<diagramName>"中查看配置
    When 给视图"<diagramName>"删除CI"<ciCode>"
    Then 给视图"<diagramName>"删除CI"<ciCode>"成功

    Examples: 
      | common | diagramName | ciCode |
      | 视图     | CI画图视图      | 信用卡决策1 |

  @cleanRltCls
  Scenario Outline: Image_CI_在画布中直接创建包含关系，被包含对象变为容器
    When 新建视图"CI画图视图",描述为"",文件夹为""
    Then 成功新建视图"CI画图视图"
    When 给视图"CI画图视图"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"CI画图视图"增加CI"信用卡决策1"坐标为"400""600"
    When 创建名称为"<rltName>"的关系分类，其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名 | 属性类型 | 枚举值 |
      | 字符串 |    3 | ""  |
      | 整数  |    1 | ""  |
      | 小数  |    2 | ""  |
      | 文本  |    4 | ""  |
      | 枚举  |    6 | 月,日 |
      | 日期  |    7 | ""  |
    Then 名称为"<rltName>"的关系分类创建成功,其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名 | 属性类型 | 枚举值 |
      | 字符串 |    3 | ""  |
      | 整数  |    1 | ""  |
      | 小数  |    2 | ""  |
      | 文本  |    4 | ""  |
      | 枚举  |    6 | 月,日 |
      | 日期  |    7 | ""  |

    Examples: 
      | common | rltName | lineAnime | lineType | lineBorder | lineDirect | lineDispType |
      | 正常数据   | 包含关系    |         1 | solid    |          1 | classic    |            2 |

  @cleanRltCls
  Scenario Outline: Image_CI_创建关系
    Given 系统中已存在如下ci分类:"Application,s@&_-"
    When 新建视图"创建关系视图",描述为"创建关系视图",文件夹为""
    Then 成功新建视图"创建关系视图"
    When 创建名称为"<rltName>"的关系分类，其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名  | 属性类型 | 枚举值 | 是否cicode |
      | 源端口  |    1 | ""  |        1 |
      | 目标端口 |    1 | ""  |        1 |
    Then 名称为"<rltName>"的关系分类创建成功,其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名  | 属性类型 | 枚举值 | 是否cicode |
      | 源端口  |    1 | ""  |        1 |
      | 目标端口 |    1 | ""  |        1 |
    When 在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名  | 属性值  |
      | 源端口  | 1511 |
      | 目标端口 | 1622 |
    Then 成功在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名  | 属性值  |
      | 源端口  | 1511 |
      | 目标端口 | 1622 |
    When 给视图"创建关系视图"增加CI"400网关1"坐标为"400""600"
    Then 成功给视图"创建关系视图"增加CI"400网关1"坐标为"400""600"
    When 给视图"创建关系视图"增加CI"eight"坐标为"500""700"
    Then 成功给视图"创建关系视图"增加CI"eight"坐标为"500""700"
    When 在"<rltName>"关系下,创建源分类为"Application"源对象为"400网关1"到目标分类为"s@&_-",目标对象为"eight"的关系
    Then 成功在"<rltName>"关系下,创建源分类为"Application"源对象为"400网关1"到目标分类为"s@&_-",目标对象为"eight"的关系
    When 删除"<rltName>"关系下,属性值前匹配"eight"的关系数据
    Then "<rltName>"关系下,不存在属性值前匹配"eight"的关系数据
    When 删除名称为"<rltName>"的关系分类及数据
    Then 关系分类"<rltName>"分类及数据删除成功

    Examples: 
      | common | rltName    | lineAnime | lineType | lineBorder | lineDirect | lineDispType |
      | 正常数据   | AppRlt创建关系 |         1 | solid    |          1 | classic    |            2 |

  @Smoke
  Scenario Outline: Image_CI_RightClick_下钻视图的告警传递到当前视图
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 新建视图"告警视图",描述为"",文件夹为""
    Then 成功新建视图"告警视图"
    When 给事件源为"5",SourceEventID为"60",CI为"金融交换1"推送告警"响应时间",事件级别为"1",值为"Critical",状态为"1",主题为"业务响应时间达到 143ms，已超过设定的阈值 125ms！",时间为"2018-04-10 11:19:12"
    When 给视图"<diagramName>"增加CI"信用卡工单1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡工单1"坐标为"400""600"
    When 给视图"告警视图"增加CI"后台流程1"坐标为"400""600"
    Then 成功给视图"告警视图"增加CI"后台流程1"坐标为"400""600"
    When 新建视图"<directDiagram>",描述为"",文件夹为""
    Then 成功新建视图"<directDiagram>"
    When 新建视图"被钻取视图2",描述为"",文件夹为""
    Then 成功新建视图"被钻取视图2"
    When 给视图"<directDiagram>"增加CI"金融交换1"坐标为"400""600"
    Then 成功给视图"<directDiagram>"增加CI"金融交换1"坐标为"400""600"
    When 给视图"被钻取视图2"增加CI"金融交换1"坐标为"400""600"
    Then 成功给视图"被钻取视图2"增加CI"金融交换1"坐标为"400""600"
    When 在视图"<diagramName>"中给CI"<ciCode>"添加钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中给CI"<ciCode>"成功添加钻取视图"<directDiagram>"
    When 在视图"<diagramName>"中给CI"<ciCode>"添加钻取视图"被钻取视图2"
    Then 在视图"<diagramName>"中给CI"<ciCode>"成功添加钻取视图"被钻取视图2"
    When 在视图"告警视图"中给CI"后台流程1"添加钻取视图"<directDiagram>"
    Then 在视图"告警视图"中给CI"后台流程1"成功添加钻取视图"<directDiagram>"
    When 在视图"告警视图"中给CI"后台流程1"添加钻取视图"被钻取视图2"
    Then 在视图"告警视图"中给CI"后台流程1"成功添加钻取视图"被钻取视图2"
    When 下钻视图的告警传递到"被钻取视图,被钻取视图2"视图
    Then 下钻视图的CI"金融交换1"告警成功传递到"被钻取视图,被钻取视图2"视图
    When 删除名称为"<diagramName>"的视图
    Then 成功删除名称为"<diagramName>"的视图
    When 删除名称为"<directDiagram>"的视图
    Then 成功删除名称为"<directDiagram>"的视图
    When 删除名称为"被钻取视图2"的视图
    Then 成功删除名称为"被钻取视图2"的视图
    When 删除名称为"告警视图"的视图
    Then 成功删除名称为"告警视图"的视图

    Examples: 
      | common | diagramName | ciCode | directDiagram |
      | 视图     | CI画图视图      | 信用卡工单1 | 被钻取视图         |

  Scenario: Image_CI_RightClick_编辑CI
    And 给角色"admin"的分类"Application"设置编辑权限
    When 新建视图"编辑CI",描述为"",文件夹为""
    Then 成功新建视图"编辑CI"
    When 给视图"编辑CI"增加CI"信用卡决策1"坐标为"400""600"
    Then 成功给视图"编辑CI"增加CI"信用卡决策1"坐标为"400""600"
    When 查询角色所有数据权限
    Then 分类"Application"的权限为编辑权限
    When 在视图"编辑CI"中右键编辑分类为"Application"的CI"信用卡决策1"的属性"Important Level"的值为"次重要"
    Then 在视图"编辑CI"中右键成功编辑分类为"Application"的CI"信用卡决策1"的属性"Important Level"的值为"次重要"
    When 删除名称为"编辑CI"的视图
    Then 成功删除名称为"编辑CI"的视图
    And 删除角色"admin"的数据设置

  @Debug
  Scenario Outline: Image_CI_RightClick_查关联视图告警
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 给事件源为"5",SourceEventID为"60",CI为"金融交换1"推送告警"响应时间",事件级别为"3",值为"Critical",状态为"1",主题为"业务响应时间达到 143ms，已超过设定的阈值 125ms！",时间为"2018-05-29 15:39:12"
    When 给视图"<diagramName>"增加CI"信用卡工单1"坐标为"400""600"
    Then 成功给视图"<diagramName>"增加CI"信用卡工单1"坐标为"400""600"
    When 新建视图"<directDiagram>",描述为"",文件夹为""
    Then 成功新建视图"<directDiagram>"
    When 新建视图"被钻取视图2",描述为"",文件夹为""
    Then 成功新建视图"被钻取视图2"
    When 给视图"<directDiagram>"增加CI"金融交换1"坐标为"400""600"
    Then 成功给视图"<directDiagram>"增加CI"金融交换1"坐标为"400""600"
    When 给视图"被钻取视图2"增加CI"金融交换1"坐标为"400""600"
    Then 成功给视图"被钻取视图2"增加CI"金融交换1"坐标为"400""600"
    When 在视图"<diagramName>"中给CI"<ciCode>"添加钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中给CI"<ciCode>"成功添加钻取视图"<directDiagram>"
    When 在视图"<diagramName>"中给CI"<ciCode>"添加钻取视图"被钻取视图2"
    Then 在视图"<diagramName>"中给CI"<ciCode>"成功添加钻取视图"被钻取视图2"
    When 在视图"<diagramName>"中添加下钻视图"被钻取视图,被钻取视图2"
    Then 在视图"<diagramName>"中成功添加下钻视图"被钻取视图,被钻取视图2"
    When 删除名称为"<diagramName>"的视图
    Then 成功删除名称为"<diagramName>"的视图
    When 删除名称为"<directDiagram>"的视图
    Then 成功删除名称为"<directDiagram>"的视图
    When 删除名称为"被钻取视图2"的视图
    Then 成功删除名称为"被钻取视图2"的视图

    Examples: 
      | common | diagramName | ciCode | directDiagram |
      | 视图     | CI画图视图      | 信用卡工单1 | 被钻取视图         |