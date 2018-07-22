@BASE
Feature: BASE_标签管理

  @Smoke @deleteTagDir
  Scenario Outline: 标签管理_tagDir的增删改
    When 创建名称为"<tagDirName>"的标签目录
    Then 名称为"<tagDirName>"的标签目录创建成功
    When 修改标签目录"<tagDirName>"的名称为"<modifyTagDirName>"
    Then 标签目录"<tagDirName>"的名称修改为"<modifyTagDirName>"
    When 删除名称为"<modifyTagDirName>"的标签目录
    Then 系统中不存在名称为"<modifyTagDirName>"的标签目录

    Examples: 
      | commons       | tagDirName           | modifyTagDirName     |
      | 正常字符          | 我去儿童 123543hjk       | tag测试                |
      | 目录_特殊字符1&最大长度 | ~！@#￥%……&*（）——+·——+{ | ……&*（）               |
      | 目录_特殊字符2      | }                    | 【】、：“；‘《》？，。、`[]{}}  |
      | 目录_特殊字符3_js   | ；’：”<>?,./           | <span>agName</span>  |
      | 目录_修改名称和原来一样  | 测试修改标签分组不改名          | 测试修改标签分组不改名          |
      | 目录_中文_最大长度20  | 测试标签分组最大长度为二十标签分组的名称 | 测试标签分组最大长度为二十标签分组的名称 |

  @Smoke @deleteTag @deleteTagDir
  Scenario Outline: 标签管理_tag的增删改
    When 创建名称为"<tagDirName>"的标签目录
    Then 名称为"<tagDirName>"的标签目录创建成功
    When 在名称为"<tagDirName>"的标签目录下,创建名称为"<tagName>"的标签
    Then 名称为"<tagName>"的标签创建成功
    When 将名称为"<tagName>"的标签修改为"<modifyTagName>"
    Then 名称为"<tagName>"的标签名称修改为"<modifyTagName>"
    When 删除名称为"<modifyTagName>"的标签
    Then 系统中不存在名称为"<modifyTagName>"的标签
    When 删除名称为"<tagDirName>"的标签目录
    Then 系统中不存在名称为"<tagDirName>"的标签目录

    Examples: 
      | commons       | tagDirName | tagName              | modifyTagName       |
      | 正常字符          | 标签管理1      | 我去儿童 123543hjk       | 我去儿童                |
      | 标签_特殊字符1&最大长度 | 标签管理2      | ~！@#￥%……&*（）——+·——+{ | ……&*                |
      | 标签_特殊字符2_js   | 标签管理3      | }【】、：“；‘《》？，。、`[]{}  | <span>agName</span> |
      | 标签_特殊字符3      | 标签管理       | ；’：”<>?,./           | 中间                  |
      | 标签_特殊字符3      | 标签管理       | 标签名称                 | 标签名称                |
      | 标签_最大中文长度20   | 标签管理       | 新建最大长度标签名称新建最大长度标签名称 | 中间                  |

  @deleteTagDir
  Scenario: 标签管理_tag搜索
    When 创建名称为"tagTest"的标签目录
    Then 名称为"tagTest"的标签目录创建成功
    When 在名称为"tagTest"的标签目录下,创建名称如下的标签:"App1、Application、~！@#￥%……&*（）——+·——+{、}|【】、：“；‘《》？，。、`[]{},；’：”<>?./"
    And 查询名称包含关键字的标签:
      | commons        | searchKey       |
      | 前匹配+多匹配        | App             |
      | 部分匹配_特殊字符+唯一匹配 | ￥%……&*（）        |
      | 模糊匹配_匹配字符%     | %P              |
      | 匹配字符_^         | ^A              |
      | 全匹配            | `[]{},；’：”<>?./ |
    Then 标签查询返回结果正确
      | commons        | searchKey       |
      | 前匹配+多匹配        | App             |
      | 部分匹配_特殊字符+唯一匹配 | ￥%……&*（）        |
      | 模糊匹配_匹配字符%     | %P              |
      | 匹配字符_^         | ^A              |
      | 全匹配            | `[]{},；’：”<>?./ |
    When 删除名称为"tagTest"的标签目录
    Then 系统中不存在名称为"tagTest"的标签目录

  @Smoke @CleanCiCls @deleteTagDir @Debug
  Scenario: 标签管理_添加items
    Given 系统中已存在如下ci分类:"Cluster"
    When 在"业务领域"目录下,创建名称为"ciTagData"的ci分类,使用图标为"Default"
    And 给"ciTagData"分类添加如下属性:
      | 属性名称     | 属性类型 | 必填 | Lable | 枚举值         | CICode | 默认值         |
      | 优先级（整数）  | 整数   |  1 |     0 |             |      0 |           0 |
      | 承重（KG）   | 小数   |  0 |     0 |             |      0 |       14.56 |
      | 描述（文章）   | 文章   |  1 |     1 |             |      0 | werdfghnbvc |
      | CI编号（主键） | 字符串  |  0 |     0 |             |      1 |             |
      | 资产编号（文本） | 文本   |  0 |     1 |             |      0 |             |
      | 所属组织（枚举） | 枚举   |  0 |     1 | ["宝马","一汽"] |      0 |             |
      | 投运时间（日期） | 日期   |  1 |     1 |             |      0 | 1990-10-90  |
    Then "ciTagData"分类属性更新成功
      | 属性名称     | 属性类型 | 必填 | Lable | 枚举值         | CICode | 默认值         |
      | 优先级（整数）  | 整数   |  1 |     0 |             |      0 |           0 |
      | 承重（KG）   | 小数   |  0 |     0 |             |      0 |       14.56 |
      | 描述（文章）   | 文章   |  1 |     1 |             |      0 | werdfghnbvc |
      | CI编号（主键） | 字符串  |  0 |     0 |             |      1 |             |
      | 资产编号（文本） | 文本   |  0 |     1 |             |      0 |             |
      | 所属组织（枚举） | 枚举   |  0 |     1 | ["宝马","一汽"] |      0 |             |
      | 投运时间（日期） | 日期   |  1 |     1 |             |      0 | 1990-10-90  |
    When 将"ciTagData.xls"表中的数据导入"ciTagData"分类数据
    Then "ciTagData.xls"表中的数据成功导入"ciTagData"数据
    When 创建名称为"tagTest"的标签目录
    Then 名称为"tagTest"的标签目录创建成功
    When 在名称为"tagTest"的标签目录下,创建名称为"第三方_规则"的标签
    And 在名称为"第三方_规则"的标签下,添加如下单条规则:
      | commons         | ruleNum | ciClsName | ciFriendName | attrName | ruleOp   | ruleVal               |
      | 遍历各运算符_=        |       1 | ciTagData | ciTagData    | 优先级（整数）  | =        |                     0 |
      | 遍历各运算符_!=       |       1 | ciTagData | ciTagData    | 所属组织（枚举） | !=       | 宝马                    |
      | 遍历各运算符_<        |       1 | ciTagData | ciTagData    | 承重（KG）   | <        |                 57.89 |
      | 遍历各运算符_<=       |       1 | ciTagData | ciTagData    | 承重（KG）   | <=       |                 54.89 |
      | 遍历各运算符_>        |       1 | ciTagData | ciTagData    | 承重（KG）   | >        |                 17.89 |
      | 遍历各运算符_>=       |       1 | ciTagData | ciTagData    | 承重（KG）   | >=       |                 17.89 |
      | 遍历各运算符_like     |       1 | ciTagData | ciTagData    | CI编号（主键） | like     | %SEC-FW-%             |
      | 遍历各运算符_not like |       1 | ciTagData | ciTagData    | CI编号（主键） | not like | %SEC-WAF-%            |
      | 遍历各运算符_in       |       2 | Cluster   | Cluster      | IP       | in       | 10.16.16.1,10.16.16.2 |
      | 遍历各运算符_not in   |       2 | Cluster   | Cluster      | IP       | not in   | 10.7.3.55             |
    Then 名称为"第三方_规则"的标签下,如下规则创建成功
      | commons         | ruleNum | ciClsName | ciFriendName | attrName | ruleOp   | ruleVal               |
      | 遍历各运算符_=        |       1 | ciTagData | ciTagData    | 优先级（整数）  | =        |                     0 |
      | 遍历各运算符_!=       |       1 | ciTagData | ciTagData    | 所属组织（枚举） | !=       | 宝马                    |
      | 遍历各运算符_<        |       1 | ciTagData | ciTagData    | 承重（KG）   | <        |                 57.89 |
      | 遍历各运算符_<=       |       1 | ciTagData | ciTagData    | 承重（KG）   | <=       |                 54.89 |
      | 遍历各运算符_>        |       1 | ciTagData | ciTagData    | 承重（KG）   | >        |                 17.89 |
      | 遍历各运算符_>=       |       1 | ciTagData | ciTagData    | 承重（KG）   | >=       |                 17.89 |
      | 遍历各运算符_like     |       1 | ciTagData | ciTagData    | CI编号（主键） | like     | %SEC-FW-%             |
      | 遍历各运算符_not like |       1 | ciTagData | ciTagData    | CI编号（主键） | not like | %SEC-WAF-%            |
      | 遍历各运算符_in       |       2 | Cluster   | Cluster      | IP       | in       | 10.16.16.1,10.16.16.2 |
      | 遍历各运算符_not in   |       2 | Cluster   | Cluster      | IP       | not in   | 10.7.3.55             |
    When 获取"第三方_规则"标签下,符合如下规则的数据
      | commons         | ruleNum | ciClsName | ciFriendName | attrName | ruleOp   | ruleVal               |
      | 遍历各运算符_=        |       1 | ciTagData | ciTagData    | 优先级（整数）  | =        |                     0 |
      | 遍历各运算符_!=       |       1 | ciTagData | ciTagData    | 所属组织（枚举） | !=       | 宝马                    |
      | 遍历各运算符_<        |       1 | ciTagData | ciTagData    | 承重（KG）   | <        |                 57.89 |
      | 遍历各运算符_<=       |       1 | ciTagData | ciTagData    | 承重（KG）   | <=       |                 54.89 |
      | 遍历各运算符_>        |       1 | ciTagData | ciTagData    | 承重（KG）   | >        |                 17.89 |
      | 遍历各运算符_>=       |       1 | ciTagData | ciTagData    | 承重（KG）   | >=       |                 17.89 |
      | 遍历各运算符_like     |       1 | ciTagData | ciTagData    | CI编号（主键） | like     | %SEC-FW-%             |
      | 遍历各运算符_not like |       1 | ciTagData | ciTagData    | CI编号（主键） | not like | %SEC-WAF-%            |
      | 遍历各运算符_in       |       2 | Cluster   | Cluster      | IP       | in       | 10.16.16.1,10.16.16.2 |
      | 遍历各运算符_not in   |       2 | Cluster   | Cluster      | IP       | not in   | 10.7.3.55             |
    Then "第三方_规则"标签下,符合如下规则的数据返回正确
      | commons         | ruleNum | ciClsName | ciFriendName | attrName | ruleOp   | ruleVal               |
      | 遍历各运算符_=        |       1 | ciTagData | ciTagData    | 优先级（整数）  | =        |                     0 |
      | 遍历各运算符_!=       |       1 | ciTagData | ciTagData    | 所属组织（枚举） | !=       | 宝马                    |
      | 遍历各运算符_<        |       1 | ciTagData | ciTagData    | 承重（KG）   | <        |                 57.89 |
      | 遍历各运算符_<=       |       1 | ciTagData | ciTagData    | 承重（KG）   | <=       |                 54.89 |
      | 遍历各运算符_>        |       1 | ciTagData | ciTagData    | 承重（KG）   | >        |                 17.89 |
      | 遍历各运算符_>=       |       1 | ciTagData | ciTagData    | 承重（KG）   | >=       |                 17.89 |
      | 遍历各运算符_like     |       1 | ciTagData | ciTagData    | CI编号（主键） | like     | %SEC-FW-%             |
      | 遍历各运算符_not like |       1 | ciTagData | ciTagData    | CI编号（主键） | not like | %SEC-WAF-%            |
      | 遍历各运算符_in       |       2 | Cluster   | Cluster      | IP       | in       | 10.16.16.1,10.16.16.2 |
      | 遍历各运算符_not in   |       2 | Cluster   | Cluster      | IP       | not in   | 10.7.3.55             |
    When 删除名称为"第三方_规则"的标签
    Then 系统中不存在名称为"第三方_规则"的标签
    When 删除"ciTagData"分类及数据
    Then 名称为"ciTagData"的分类删除成功
