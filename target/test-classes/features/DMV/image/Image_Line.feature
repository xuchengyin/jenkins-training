@DMV @delDiagram
Feature: DMV_绘图_新建视图_ci画图：连线右键菜单

  Background: 
    When 新建视图"连线视图",描述为"",文件夹为""
    Then 成功新建视图"连线视图"
    When 给视图"连线视图"增加CI"400网关1"坐标为"400""600"
    Then 成功给视图"连线视图"增加CI"400网关1"坐标为"400""600"
    When 给视图"连线视图"增加CI"three"坐标为"500""700"
    Then 成功给视图"连线视图"增加CI"three"坐标为"500""700"
    When 在视图"连线视图"中增加"400网关1"到"three"的连线
    Then 成功在视图"连线视图"中增加"400网关1"到"three"的连线

  Scenario Outline: Image_给两个CI的连线、箭头反向、删除连线
    When 在视图"<diagramName>"中增加"<ciCode>"到"<ciCode2>"的连线箭头反向
    Then 成功在视图"<diagramName>"中增加"<ciCode>"到"<ciCode2>"的连线箭头反向
    When 在视图"<diagramName>"中删除"<ciCode>"到"<ciCode2>"的连线
    Then 成功在视图"<diagramName>"中删除"<ciCode>"到"<ciCode2>"的连线

    Examples: 
      | common | diagramName | ciCode | ciCode2 |
      | 视图     | 连线视图        | 400网关1 | three   |

  @Smoke @cleanRlt
  Scenario Outline: Image_给连线创建关系
    When 在视图"<diagramName>"中"<ciCode>"到"<ciCode2>"的连线右键创建关系"AppRlt"
    Then 在视图"<diagramName>"中"<ciCode>"到"<ciCode2>"的连线右键创建关系"AppRlt"成功
    When 在视图"<diagramName>"中"<ciCode>"到"<ciCode2>"的关系"AppRlt"右键删除关系
    Then 在视图"<diagramName>"中"<ciCode>"到"<ciCode2>"的关系"AppRlt"右键删除关系成功
    When 删除"AppRlt"关系下,源分类为"Application",源对象为"<ciCode>",目标分类为"s@&_-",目标对象为"<ciCode2>"的关系数据
    Then "AppRlt"关系下,不存在源分类为"Application",源对象为"<ciCode>",目标分类为"s@&_-",目标对象为"<ciCode2>"的关系数据

    Examples: 
      | common | diagramName | ciCode | ciCode2 |
      | 视图     | 连线视图        | 400网关1 | three   |

  @Smoke
  Scenario Outline: Image_Line 给两个CI增加连线 添加钻取视图
    When 新建视图"<directDiagram>",描述为"<diagramDesc>",文件夹为"<dirName>"
    Then 成功新建视图"<directDiagram>"
    When 新建视图"连线钻取视图2",描述为"<diagramDesc>",文件夹为"<dirName>"
    Then 成功新建视图"连线钻取视图2"
    When 在视图"<diagramName>"中给"<ciCode>"到"<ciCode2>"的连线添加钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中给"<ciCode>"到"<ciCode2>"的连线成功添加钻取视图"<directDiagram>"
    When 在视图"<diagramName>"中给"<ciCode>"到"<ciCode2>"的连线添加钻取视图"连线钻取视图2"
    Then 在视图"<diagramName>"中给"<ciCode>"到"<ciCode2>"的连线成功添加钻取视图"连线钻取视图2"
    When 在视图"<diagramName>"中给"<ciCode>"到"<ciCode2>"的连线删除钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中给"<ciCode>"到"<ciCode2>"的连线成功删除钻取视图"<directDiagram>"

    Examples: 
      | common | diagramName | ciCode | ciCode2 | directDiagram |
      | 视图     | 连线视图        | 400网关1 | three   | 连线钻取视图        |
