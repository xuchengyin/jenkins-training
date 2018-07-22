@DMV @delDiagram
Feature: DMV_绘图_新建视图

  @Smoke
  Scenario Outline: Image_新建、删除视图
    When 新建视图"<diagramName>",描述为"<diagramDesc>",文件夹为"<dirName>"
    Then 成功新建视图"<diagramName>"
    And 再次新建视图"<diagramName>",描述为"<diagramDesc>",文件夹为"<dirName>"失败,kw="<kw>"
    When 删除名称为"<diagramName>"的视图
    Then 成功删除名称为"<diagramName>"的视图

    Examples: 
      | common    | diagramName | diagramDesc | dirName |kw|
      #|参数是空新建默认名字的视图||||视图名称[新建视图名称]已存在|
      | 新建中文名称视图  | 新建视图名称      | 描述描述        | 我的      |视图名称[新建视图名称]已存在|
      | 新建英文加数字视图 | aaabbb1245  | aaabbb1245  | 我的      |视图名称[aaabbb1245]已存在|
      | 新建特殊字符    | @#￥%……&&**（ | ！@#￥#%……&&& | 我的      |视图名称[@#￥%……&&**（]已存在|
      |视图名称最大长度汉字50|视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图|视图描述|我的|视图名称[视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图]已存在|
      |视图描述最大长度汉字100|视图名称|视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述|我的|视图名称[视图名称]已存在|
     

  @Smoke
  Scenario Outline: Image_新建视图、修改、另存
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 保存视图"<diagramName>"
    Then 成功保存视图"<diagramName>"
    When 另存"<diagramName>"为新名称"<newDiagranName>",描述为"<newDiagramDesc>",文件夹为"<newDirName>"
    Then 成功另存视图为"<newDiagranName>",描述为"<newDiagramDesc>",文件夹为"<newDirName>"

    Examples: 
      | common | diagramName | newDiagranName | diagramDesc | dirName | newDiagramDesc | newDirName |
      | 评论测试   | 自动新建视图      | 另存为新视图         | 描述描述        | 我的      | 新描述            | 我的         |

  Scenario Outline: Image_给画布添加一个CI
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 给视图"<diagramName>"增加CI"<ciCode>"坐标为"<x>""<y>"
    Then 成功给视图"<diagramName>"增加CI"<ciCode>"坐标为"<x>""<y>"
    When 给视图"<diagramName>"删除CI"<ciCode>"
    Then 给视图"<diagramName>"删除CI"<ciCode>"成功

    Examples: 
      | common | diagramName | ciCode | x   | y   |
      | 视图     | 自动新建视图      | 信用卡决策1 | 400 | 600 |

  @Smoke @cleanRlt
  Scenario Outline: Image_给画布添加两个有关系的CI
    Given 系统中已存在如下ci分类:"Application,s@&_-"
    And 系统中已存在如下关系分类:"AppRlt"
    #When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据
    #Then "AppRlt"下存在只存在1条"400网关1"与"eight"的关系数据
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 给视图"<diagramName>"增加CI"400网关1"坐标为"<x>""<y>"
    Then 成功给视图"<diagramName>"增加CI"400网关1"坐标为"<x>""<y>"
    When 给视图"<diagramName>"增加CI"BJ_DB_Reporter1"坐标为"500""700"
    Then 成功给视图"<diagramName>"增加CI"BJ_DB_Reporter1"坐标为"500""700"
    When 给视图"<diagramName>"删除CI"400网关1"
    Then 给视图"<diagramName>"删除CI"400网关1"成功
    When 给视图"<diagramName>"删除CI"BJ_DB_Reporter1"
    Then 给视图"<diagramName>"删除CI"BJ_DB_Reporter1"成功
    #When 删除"AppRlt"关系下,属性值前匹配"eight"的关系数据
    #Then "AppRlt"关系下,不存在属性值前匹配"eight"的关系数据

    Examples: 
      | common | diagramName |  |  | x   | y   |
      | 视图     | 自动新建视图      |  |  | 400 | 600 |

  @Smoke
  Scenario: Image_给画布增加多个新增的CI
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 给视图"自动新建视图"增加如下CI:
      | ciCode | x   | y   |
      | 金融交换1  | 400 | 600 |
      | 后台流程1  | 300 | 500 |
      | 信用卡决策1 | 200 | 700 |
      | 影像系统1  | 100 | 600 |
      | 人行征信1  | 300 | 300 |
      | 微信银行1  | 200 | 300 |
    Then 成功给视图"自动新建视图"增加如下CI:
      | ciCode | x   | y   |
      | 金融交换1  | 400 | 600 |
      | 后台流程1  | 300 | 500 |
      | 信用卡决策1 | 200 | 700 |
      | 影像系统1  | 100 | 600 |
      | 人行征信1  | 300 | 300 |
      | 微信银行1  | 200 | 300 |

  @Smoke
  Scenario Outline: Image_画布右键_清空画布
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 给视图"<diagramName>"增加CI"<ciCode>"坐标为"<x>""<y>"
    Then 成功给视图"<diagramName>"增加CI"<ciCode>"坐标为"<x>""<y>"
    When 视图"<diagramName>"右键清空画布
    Then 视图"<diagramName>"清空画布成功
    When 给视图"<diagramName>"删除CI"<ciCode>"
    Then 给视图"<diagramName>"删除CI"<ciCode>"成功

    Examples: 
      | common | diagramName | ciCode | x   | y   |
      | 视图     | 自动新建视图      | 微信银行1  | 500 | 300 |

  @Smoke
  Scenario Outline: Image_画布右键_挂载、编辑、取消动态节点
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 视图"<diagramName>"中画布挂载动态节点
      | commont | 动态节点  |
      | 动态节点1   | 人行征信1 |
      | 动态节点2   | 微信银行1 |
    Then 视图"<diagramName>"中画布成功挂载动态节点
      | commont | 动态节点  |
      | 动态节点1   | 人行征信1 |
      | 动态节点2   | 微信银行1 |
    When 视图"<diagramName>"中画布取消动态节点
    Then 视图"<diagramName>"中画布成功取消动态节点

    Examples: 
      | common | diagramName |
      | 视图     | 自动新建视图      |

  @Smoke
  Scenario Outline: Image_画布右键_添加、编辑、删除影响关系规则
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    #When 新建朋友圈"<friendName>"
    #When 新建朋友圈2 待朋友圈做完补充
    When 给视图"<diagramName>"添加影响关系规则"<friendName>"
    Then 给视图"<diagramName>"添加影响关系规则"<friendName>"成功
    When 视图"<diagramName>"画布右键编辑影响关系规则"<friendName>"
    Then 视图"<diagramName>"画布右键编辑影响关系规则"<friendName>"成功
    When 视图"<diagramName>"画布右键删除影响关系规则
    Then 视图"<diagramName>"画布右键删除影响关系规则成功

    Examples: 
      | common | diagramName | friendName |
      | 视图     | 自动新建视图      | 朋友圈关系      |

  Scenario Outline: Image_画布右键_清除背景
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 给视图"<diagramName>"选择图片"rttt.jpg"为背景
    Then 视图"<diagramName>"成功选择图片"rttt.jpg"为背景
    When 视图"<diagramName>"画布右键清除背景
    Then 视图"<diagramName>"画布右键清除背景成功

    Examples: 
      | common | diagramName |
      | 视图     | 自动新建视图      |

  Scenario Outline: Image_画图_包含多个关系
    Given 系统中已存在如下ci分类:"Application,Cluster"
    And 系统中已存在如下关系分类:"AppRlt"
    When 新建视图"自动新建视图",描述为"",文件夹为""
    Then 成功新建视图"自动新建视图"
    When 给视图"<diagramName>"增加"Application,Cluster"分类的"25"个CI
    Then 成功给视图"<diagramName>"增加"Application,Cluster"分类的"25"个CI

    Examples: 
      | common | diagramName |
      | 视图     | 自动新建视图      |

  @Debug
  Scenario: Image_根据base的菜单权限，控制显示创建CI和关系的按钮
    When 新建视图"显示创建CI和关系的按钮",描述为"显示创建CI和关系的按钮",文件夹为""
    Then 成功新建视图"显示创建CI和关系的按钮"
    When 根据目录Code"02"获得权限
    Then 成功获得"对象管理","关系管理"的权限
