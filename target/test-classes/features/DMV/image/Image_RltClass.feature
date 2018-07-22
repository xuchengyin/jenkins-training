@DMV @delDiagram
Feature: DMV_绘图_新建视图_ci画图：关系右键菜单

  @Smoke @Debug @cleanRlt 
  Scenario Outline: Image_给连线创建关系、删除关系
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"<ciCode>",目标分类为"s@&_-",目标对象为"<targetCiCode>"关系数据
    Then "AppRlt"下存在只存在1条"<ciCode>"与"<targetCiCode>"的关系数据
    When 给视图"<diagramName>"增加CI"<ciCode>"坐标为"<x>""<y>"
    Then 成功给视图"<diagramName>"增加CI"<ciCode>"坐标为"<x>""<y>"
    When 给视图"<diagramName>"增加CI"<targetCiCode>"坐标为"500""700"
    Then 成功给视图"<diagramName>"增加CI"<targetCiCode>"坐标为"500""700"
    When 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"创建关系"特殊"
    Then 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"创建关系"特殊"成功
    When 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"特殊"右键删除关系
    Then 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"特殊"右键删除关系成功
    When 删除"AppRlt"关系下,属性值前匹配"<targetCiCode>"的关系数据
    Then "AppRlt"关系下,不存在属性值前匹配"<targetCiCode>"的关系数据
    When 删除"特殊"关系下,源分类为"Application",源对象为"<ciCode>",目标分类为"s@&_-",目标对象为"<targetCiCode>"的关系数据
    Then "特殊"关系下,不存在源分类为"Application",源对象为"<ciCode>",目标分类为"s@&_-",目标对象为"<targetCiCode>"的关系数据

    Examples: 
      | common | diagramName | ciCode | targetCiCode | x   | y   |
      | 视图     | 关系视图        | 400网关1 | two          | 400 | 600 |

  @Smoke @cleanRlt
  Scenario Outline: Image_RLT_RightClick_添加、修改、删除钻取视图
    When 新建视图"<diagramName>",描述为"",文件夹为""
    Then 成功新建视图"<diagramName>"
    When 新建视图"<directDiagram>",描述为"<diagramDesc>",文件夹为"<dirName>"
    Then 成功新建视图"<directDiagram>"
    When 新建视图"被钻取视图2",描述为"<diagramDesc>",文件夹为"<dirName>"
    Then 成功新建视图"被钻取视图2"
    When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"<ciCode>",目标分类为"s@&_-",目标对象为"<targetCiCode>"关系数据
    Then "AppRlt"下存在只存在1条"<ciCode>"与"<targetCiCode>"的关系数据
    When 给视图"<diagramName>"增加CI"<ciCode>"坐标为"<x>""<y>"
    When 给视图"<diagramName>"增加CI"<targetCiCode>"坐标为"500""700"
    When 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"右键添加钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"成功添加钻取视图"<directDiagram>"
    When 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"右键添加钻取视图"被钻取视图2"
    Then 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"成功添加钻取视图"被钻取视图2"
    When 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"右键删除钻取视图"<directDiagram>"
    Then 在视图"<diagramName>"中"<ciCode>"到"<targetCiCode>"的关系"AppRlt"成功删除钻取视图"<directDiagram>"
    When 删除"AppRlt"关系下,属性值前匹配"<targetCiCode>"的关系数据
    Then "AppRlt"关系下,不存在属性值前匹配"<targetCiCode>"的关系数据

    Examples: 
      | common | diagramName | ciCode | targetCiCode | x   | y   | directDiagram |
      | 视图     | 关系视图        | 400网关1 | five         | 400 | 600 | 被钻取视图         |
