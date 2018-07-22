@CMV
@delCiQualityRule
Feature: CMV_仪表盘规则_CiQualityRule

  @Smoke
  Scenario Outline: CiQualityRule_新建、查询、修改、激活、删除
    When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    And 再次新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则失败,kw="<kw>"
    When 查询类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则
    Then 成功查询类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则
    When 修改类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则名称为"<newRuleName>",描述为"<newRuleDesc>"
    Then 成功修改类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则名称为"<newRuleName>",描述为"<newRuleDesc>"
    When 查询类型为"<ruleType>",名称为"<newRuleName>"的仪表盘规则
    Then 成功查询类型为"<ruleType>",名称为"<newRuleName>"的仪表盘规则
    When 激活类型为"<ruleType>"名称为"<newRuleName>"的仪表盘规则
    Then 成功激活类型为"<ruleType>"名称为"<newRuleName>"的仪表盘规则
    When 删除类型为"<ruleType>"名称为"<newRuleName>"的仪表盘规则
    Then 成功删除类型为"<ruleType>"名称为"<newRuleName>"的仪表盘规则

    Examples: 
      | common    | ruleType | ruleName   | ruleDesc   | newRuleName   | newRuleDesc |kw|
      | 孤儿        | 孤儿规则     | 规则名称       |            | 新名称           | 新描述         |nullnull已存在|
      | 过期        | 过期规则     | 规则名称       | 孤儿规则描述     | new@#￥%……&*—— |   |nullnull已存在|
      | 修改和原来一模一样 | 完整性规则    | 中ing文      | 中ing文      | 中ing文         | 中ing文       |nullnull已存在|
      | 合规性       | 合规性规则    | 规则名称       | 孤儿规则描述     | 中ing文         | 中ing文       |nullnull已存在|
      | 孤儿        | 孤儿规则     | @#￥%……&*—— | @#￥%……&*—— |    1234567890 |  1234567890 |nullnull已存在|
      | 过期        | 过期规则     | 中ing文      | 中英文ing     | AbcdEfGh      | AbcdEfGh    |nullnull已存在|
      | 完整性       | 完整性规则    | 1234567890 | 1234567890 | 新名称           | 新描述         |nullnull已存在|
      | 大小写       | 合规性规则    | AbcdEfGh   | AbcdEfGh   | 新名称           | 新描述         |nullnull已存在|
      | 仪表盘规则名称_中文_最大20个      | 合规性规则    | 测试仪表盘规则名称最大中文字符二十个汉字   | AbcdEfGh   | 新名称           | 新描述         |nullnull已存在|
			| 仪表盘规则描述_中文_最大500个      | 合规性规则    | 测试仪表盘规则名   | AbcdEfGh   | 新名称           | 描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述|nullnull已存在|

  Scenario Outline: CiQualityRule_搜索仪表盘规则
    When 按照关键字"<searchKey>"搜索"<ruleType>"类型的仪表盘规则
    Then 成功按照关键字"<searchKey>"搜索"<ruleType>"类型的仪表盘规则

    Examples: 搜索关键字
      | case | searchKey           | ruleType |
      | 分类全名 | Application         | 准确性规则    |
      | 小写   | app                 | 合规性规则    |
      | 空    |                     | 完整性规则    |
      | 下划线  | _                   | 准确性规则    |
      | 不存在的 | Applications1234567 | 合规性规则    |
      | 特殊字符 | @                   | 准确性规则    |
      | 特殊字符 | &                   | 完整性规则    |
      | 特殊字符 | %                   | 合规性规则    |

  @Smoke
  Scenario: CiQualityRule_不同类型的仪表盘规则名称相同
    When 新建如下仪表盘规则:
      | common | 类型    | 名称    | 描述    |
      | 准确性规则  | 准确性规则 | 仪表盘名称 | 仪表盘描述 |
      | 合规性规则  | 合规性规则 | 仪表盘名称 | 仪表盘描述 |
      | 完整性规则  | 完整性规则 | 仪表盘名称 | 仪表盘描述 |
    Then 成功新建如下仪表盘规则:
      | common | 类型    | 名称    | 描述    |
      | 准确性规则  | 准确性规则 | 仪表盘名称 | 仪表盘描述 |
      | 合规性规则  | 合规性规则 | 仪表盘名称 | 仪表盘描述 |
      | 完整性规则  | 完整性规则 | 仪表盘名称 | 仪表盘描述 |

  @Smoke
  Scenario Outline: CiQualityRule_合规性仪表盘规则定义
    When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
      | 操作 | className   | attrName | ruleOp | ruleVal        | condition |
      | 或  | Application | CI Code  | =      | 400网关1         | 或         |
      | 且  | Application | Name     | =      | 400网关          | 且         |
      | 或  | Application | Name     | >=     | 400网关          | 或         |
      |    | Cluster     | CI Code  | =      | BJ_AppGateWay1 |           |
      | 或  | Cluster     | Name     | =      | BJ_AppGateWay  | 或         |
    And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
      | common | className   | attrName | cdtOp | cdtValue     |
      | 常用     | Application | CI Code  | =     | 400网关        |
      | 常用     | Application | Name     | =     | 400网         |
      | 常用     | Cluster     | Name     | =     | BJ_AppGateWa |
    When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    #When 预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    #Then 成功预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则

    Examples: 
      | common | ruleType | ruleName | ruleDesc |
      | 合规性    | 合规性规则    | 规则名称     |          |

  @Smoke
  Scenario Outline: CiQualityRule_完整性仪表盘规则定义
    When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
      | 操作 | className   | attrName | ruleOp | ruleVal | condition |
      | 或  | Application |          |        |         |           |
    And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
      | common | className   | attrName |
      | 常用     | Application | CI Code  |
      | 常用     | Application | Name     |
    When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    #When 预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    #Then 成功预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则

    Examples: 
      | common | ruleType | ruleName | ruleDesc |
      | 合规性    | 完整性规则    | 规则名称     |          |

  @Smoke
  Scenario Outline: CiQualityRule_过期规则仪表盘定义
    When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
      | 操作 | className   | attrName | ruleOp | ruleVal        | condition |
      | 或  | Application | CI Code  | =      | 400网关1         | 或         |
      | 且  | Application | Name     | =      | 400网关          | 且         |
      | 或  | Application | Name     | =      | 400网关          | 或         |
      |    | Cluster     | CI Code  | =      | BJ_AppGateWay1 |           |
      | 或  | Cluster     | Name     | =      | BJ_AppGateWay  | 或         |
    And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
      | common | className   | day | hour | minute |
      | 常用     | Application |     |      |        |
      |        | Cluster     |   0 |    0 |      1 |
    When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    #When 预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    #Then 成功预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则

    Examples: 
      | common | ruleType | ruleName | ruleDesc |
      | 合规性    | 过期规则     | 规则名称     |          |

  #@cleanRltCls @CleanCiCls @CleanCiClsDir @Smoke
  @cleanRltCls @cleanAll @Smoke
  Scenario Outline: CiQualityRule_孤儿规则仪表盘定义_关系一对一
    When 创建名称为孤儿规则的CiClass目录
    Then 系统中存在名称孤儿规则的CiClass目录
    When 在"孤儿规则"目录下,创建名称为"test1"的ci分类,使用图标为"Default"
    And 给"test1"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test1"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test1"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test1  |
      | 正常      | 孤儿规则test11 |
    Then "test1"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test1  |
      | 正常      | 孤儿规则test11 |
    When 在"孤儿规则"目录下,创建名称为"test2"的ci分类,使用图标为"Default"
    And 给"test2"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test2"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test2"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test2  |
      | 正常      | 孤儿规则test22 |
    Then "test2"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test2  |
      | 正常      | 孤儿规则test22 |
    When 创建名称为"testRlt12"的关系分类
    When 新建分类"test1"到分类"test2"的关系"testRlt12"
    Then 成功新建分类"test1"到分类"test2"的关系"testRlt12"
    When 在关系分类"testRlt12"下,创建源分类为"test1",源对象为"孤儿规则test1",目标分类为"test2",目标对象为"孤儿规则test2"关系数据
    Then "testRlt12"下存在只存在1条"孤儿规则test1"与"孤儿规则test2"的关系数据
    When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
      | 操作 | className | attrName | ruleOp | ruleVal | condition |
      | 或  | test1     |          |        |         |           |
    And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
      | common | className | name | rltType   | rltClsId | condition |
      | 常用     | test1     | A    | testRlt12 | test2    |           |
    When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    #When 预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    #Then 成功预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    When 删除"testRlt12"关系下,属性值前匹配"孤儿规则test2"的关系数据
    Then "testRlt12"关系下,不存在属性值前匹配"孤儿规则test2"的关系数据
    When 删除分类"test1"到分类"test2"的关系"testRlt12"
    Then 分类关系分类"test1"到分类"test2"的关系"testRlt12"删除成功
    When 删除名称为"testRlt12"的关系分类及数据
    Then 关系分类"testRlt12"分类及数据删除成功
    When 删除"test1"分类及数据
    Then 名称为"test1"的分类删除成功
    When 删除"test2"分类及数据
    Then 名称为"test2"的分类删除成功
    When 删除名称为孤儿规则的CiClass目录
    Then 系统中不存在名称为孤儿规则的CiClass目录

    Examples: 
      | common | ruleType | ruleName | ruleDesc |
      | 合规性    | 孤儿规则     | 规则名称     |          |

  #@Debug @cleanRltCls @CleanCiCls @CleanCiClsDir @Smoke
   @cleanRltCls @cleanAll @Smoke
  Scenario Outline: CiQualityRule_孤儿规则仪表盘定义_关系一对多
    When 创建名称为孤儿规则的CiClass目录
    Then 系统中存在名称孤儿规则的CiClass目录
    When 在"孤儿规则"目录下,创建名称为"test1"的ci分类,使用图标为"Default"
    And 给"test1"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test1"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test1"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test1  |
      | 正常      | 孤儿规则test11 |
    Then "test1"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test1  |
      | 正常      | 孤儿规则test11 |
    When 在"孤儿规则"目录下,创建名称为"test2"的ci分类,使用图标为"Default"
    And 给"test2"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test2"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test2"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test2  |
      | 正常      | 孤儿规则test22 |
    Then "test2"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test2  |
      | 正常      | 孤儿规则test22 |
    When 在"孤儿规则"目录下,创建名称为"test3"的ci分类,使用图标为"Default"
    And 给"test3"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test3"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test3"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test3  |
      | 正常      | 孤儿规则test33 |
    Then "test3"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test3  |
      | 正常      | 孤儿规则test33 |
    When 在"孤儿规则"目录下,创建名称为"test4"的ci分类,使用图标为"Default"
    And 给"test4"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test4"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test4"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test4  |
      | 正常      | 孤儿规则test44 |
    Then "test4"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test4  |
      | 正常      | 孤儿规则test44 |
    When 创建名称为"testRlt12"的关系分类
    When 新建分类"test1"到分类"test2"的关系"testRlt12"
    Then 成功新建分类"test1"到分类"test2"的关系"testRlt12"
    When 在关系分类"testRlt12"下,创建源分类为"test1",源对象为"孤儿规则test1",目标分类为"test2",目标对象为"孤儿规则test2"关系数据
    Then "testRlt12"下存在只存在1条"孤儿规则test1"与"孤儿规则test2"的关系数据
    When 创建名称为"testRlt13"的关系分类
    When 新建分类"test1"到分类"test3"的关系"testRlt13"
    Then 成功新建分类"test1"到分类"test3"的关系"testRlt13"
    When 在关系分类"testRlt13"下,创建源分类为"test1",源对象为"孤儿规则test1",目标分类为"test3",目标对象为"孤儿规则test3"关系数据
    Then "testRlt13"下存在只存在1条"孤儿规则test1"与"孤儿规则test3"的关系数据
    When 创建名称为"testRlt14"的关系分类
    When 新建分类"test1"到分类"test4"的关系"testRlt14"
    Then 成功新建分类"test1"到分类"test4"的关系"testRlt14"
    When 在关系分类"testRlt14"下,创建源分类为"test1",源对象为"孤儿规则test1",目标分类为"test4",目标对象为"孤儿规则test4"关系数据
    Then "testRlt14"下存在只存在1条"孤儿规则test1"与"孤儿规则test4"的关系数据
    When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
      | 操作 | className | attrName | ruleOp | ruleVal | condition |
      | 或  | test1     |          |        |         |           |
    And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
      | common | className | name | rltType   | rltClsId | condition |
      | 常用     | test1     | A    | testRlt12 | test2    |           |
      | &      | test1     | B    | testRlt13 | test3    | 且         |
      | 或      | test1     | C    | testRlt14 | test4    | 或         |
    When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    #When 预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    #Then 成功预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    When 删除"testRlt12"关系下,属性值前匹配"孤儿规则test2"的关系数据
    Then "testRlt12"关系下,不存在属性值前匹配"孤儿规则test2"的关系数据
    When 删除"testRlt13"关系下,属性值前匹配"孤儿规则test3"的关系数据
    Then "testRlt13"关系下,不存在属性值前匹配"孤儿规则test3"的关系数据
    When 删除"testRlt14"关系下,属性值前匹配"孤儿规则test4"的关系数据
    Then "testRlt14"关系下,不存在属性值前匹配"孤儿规则test4"的关系数据
    When 删除分类"test1"到分类"test2"的关系"testRlt12"
    Then 分类关系分类"test1"到分类"test2"的关系"testRlt12"删除成功
    When 删除分类"test1"到分类"test3"的关系"testRlt13"
    Then 分类关系分类"test1"到分类"test3"的关系"testRlt13"删除成功
    When 删除分类"test1"到分类"test4"的关系"testRlt14"
    Then 分类关系分类"test1"到分类"test4"的关系"testRlt14"删除成功
    When 删除名称为"testRlt12"的关系分类及数据
    Then 关系分类"testRlt12"分类及数据删除成功
    When 删除名称为"testRlt13"的关系分类及数据
    Then 关系分类"testRlt13"分类及数据删除成功
    When 删除名称为"testRlt14"的关系分类及数据
    Then 关系分类"testRlt14"分类及数据删除成功
    When 删除"test1"分类及数据
    Then 名称为"test1"的分类删除成功
    When 删除"test2"分类及数据
    Then 名称为"test2"的分类删除成功
    When 删除"test3"分类及数据
    Then 名称为"test3"的分类删除成功
    When 删除"test4"分类及数据
    Then 名称为"test4"的分类删除成功
    When 删除名称为孤儿规则的CiClass目录
    Then 系统中不存在名称为孤儿规则的CiClass目录

    Examples: 
      | common | ruleType | ruleName | ruleDesc |
      | 合规性    | 孤儿规则     | 规则名称     |          |

  #@Debug @cleanRltCls @CleanCiCls @CleanCiClsDir @Smoke
   @cleanRltCls @cleanAll @Smoke
  Scenario Outline: CiQualityRule_孤儿规则仪表盘定义_关系多对多
    When 创建名称为孤儿规则的CiClass目录
    Then 系统中存在名称孤儿规则的CiClass目录
    When 在"孤儿规则"目录下,创建名称为"test1"的ci分类,使用图标为"Default"
    And 给"test1"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test1"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test1"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test1  |
      | 正常      | 孤儿规则test11 |
    Then "test1"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test1  |
      | 正常      | 孤儿规则test11 |
    When 在"孤儿规则"目录下,创建名称为"test2"的ci分类,使用图标为"Default"
    And 给"test2"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test2"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test2"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test2  |
      | 正常      | 孤儿规则test22 |
    Then "test2"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test2  |
      | 正常      | 孤儿规则test22 |
    When 在"孤儿规则"目录下,创建名称为"test3"的ci分类,使用图标为"Default"
    And 给"test3"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test3"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test3"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test3  |
      | 正常      | 孤儿规则test33 |
    Then "test3"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test3  |
      | 正常      | 孤儿规则test33 |
    When 在"孤儿规则"目录下,创建名称为"test4"的ci分类,使用图标为"Default"
    And 给"test4"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    Then "test4"分类属性更新成功
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
      | 字符串  | 字符串  |  1 |     1 |     |       1 |     |
    When 给"test4"添加如下数据:
      | commont | 字符串        |
      | 正常      | 孤儿规则test4  |
      | 正常      | 孤儿规则test44 |
    Then "test4"分类数据添加成功
      | commont | 字符串        |
      | 正常      | 孤儿规则test4  |
      | 正常      | 孤儿规则test44 |
    When 创建名称为"testRlt12"的关系分类
    When 新建分类"test1"到分类"test2"的关系"testRlt12"
    Then 成功新建分类"test1"到分类"test2"的关系"testRlt12"
    When 在关系分类"testRlt12"下,创建源分类为"test1",源对象为"孤儿规则test1",目标分类为"test2",目标对象为"孤儿规则test2"关系数据
    Then "testRlt12"下存在只存在1条"孤儿规则test1"与"孤儿规则test2"的关系数据
    When 创建名称为"testRlt34"的关系分类
    When 新建分类"test3"到分类"test4"的关系"testRlt34"
    Then 成功新建分类"test3"到分类"test4"的关系"testRlt34"
    When 在关系分类"testRlt34"下,创建源分类为"test3",源对象为"孤儿规则test3",目标分类为"test4",目标对象为"孤儿规则test4"关系数据
    Then "testRlt34"下存在只存在1条"孤儿规则test3"与"孤儿规则test4"的关系数据
    When 新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    Then 成功新建类型为"<ruleType>",名称为"<ruleName>",描述为"<ruleDesc>"的仪表盘规则
    When 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置CI筛选范围为:
      | 操作 | className | attrName | ruleOp | ruleVal | condition |
      | 或  | test1     |          |        |         |           |
      | 或  | test3     |          |        |         |           |
    And 给类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则增加规则定义-设置检查条件:
      | common | className | name | rltType   | rltClsId | condition |
      | 常用     | test1     | A    | testRlt12 | test2    |           |
      | 常用     | test3     | A    | testRlt34 | test4    |           |
    When 激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功激活类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    #When 预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    #Then 成功预览类型为"<ruleType>",名称为"<ruleName>"的仪表盘规则不合规的CI数据
    When 删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    Then 成功删除类型为"<ruleType>"名称为"<ruleName>"的仪表盘规则
    When 删除"testRlt12"关系下,属性值前匹配"孤儿规则test2"的关系数据
    Then "testRlt12"关系下,不存在属性值前匹配"孤儿规则test2"的关系数据
    When 删除"testRlt34"关系下,属性值前匹配"孤儿规则test4"的关系数据
    Then "testRlt34"关系下,不存在属性值前匹配"孤儿规则test4"的关系数据
    When 删除分类"test1"到分类"test2"的关系"testRlt12"
    Then 分类关系分类"test1"到分类"test2"的关系"testRlt12"删除成功
    When 删除分类"test3"到分类"test4"的关系"testRlt34"
    Then 分类关系分类"test3"到分类"test4"的关系"testRlt34"删除成功
    When 删除名称为"testRlt12"的关系分类及数据
    Then 关系分类"testRlt12"分类及数据删除成功
    When 删除名称为"testRlt34"的关系分类及数据
    Then 关系分类"testRlt34"分类及数据删除成功
    When 删除"test1"分类及数据
    Then 名称为"test1"的分类删除成功
    When 删除"test2"分类及数据
    Then 名称为"test2"的分类删除成功
    When 删除"test3"分类及数据
    Then 名称为"test3"的分类删除成功
    When 删除"test4"分类及数据
    Then 名称为"test4"的分类删除成功
    When 删除名称为孤儿规则的CiClass目录
    Then 系统中不存在名称为孤儿规则的CiClass目录

    Examples: 
      | common | ruleType | ruleName | ruleDesc |
      | 合规性    | 孤儿规则     | 规则名称     |          |
