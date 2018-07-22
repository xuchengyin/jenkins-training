@BASE
@cleanRlt
Feature: BASE_关系分类_ciRlt

  @Smoke
  Scenario: 关系数据_源对象或目标对象中搜索数据
    Given 系统中已存在如下ci分类:"Application,Cluster,s@&_-"
    And 系统中已存在如下tag分类:"APP,Clu,@&_-~！@#￥%……&*（））——，,【、；‘。、《》？’】234567是￥"
    When 按照如下条件进行筛选:
      | commons              | ciClsNames                | tagNames                 | searchKeys                                                 |
      | ciclass              |                           |                          |                                                            |
      | ciclass              | Application               |                          |                                                            |
      | ciclass_特殊字符         | s@&_-                     |                          |                                                            |
      | ciclass_多个_并集        | Application、Cluster、s@&_- |                          |                                                            |
      | tag                  |                           | APP                      |                                                            |
      | tag_特殊字符1            |                           | @&_-~！@#￥%……&*（））——，     |                                                            |
      | tag_特殊字符2            |                           | 【、；‘。、《》？’】234567是￥      |                                                            |
      | tag_多个_交集            |                           | APP、@&_-~！@#￥%……&*（））——， |                                                            |
      | 关键字                  |                           |                          | 重要                                                         |
      | 关键字_特殊字符             |                           |                          | ~!@#$%^&*()_+`-={}【：“；‘《》，。~!@#$%^&*()_+`-={}[]:";<>?,./？￥ |
      | 关键字_多个_交集            |                           |                          | 重要、H                                                       |
      | 组合_ciClass,tag_(并、交) | Application、s@&_-         | APP、@&_-~！@#￥%……&*（））——， |                                                            |
      | 组合_ciClass,key_(并、交) | Application、Cluster       |                          | 1、0                                                        |
      | 组合_全部                | Application、Cluster       | APP、Clu                  | 重要、银                                                       |
    Then 按照如下条件筛选结果正确:
      | commons              | ciClsNames                | tagNames                 | searchKeys                                                 |
      | ciclass              |                           |                          |                                                            |
      | ciclass              | Application               |                          |                                                            |
      | ciclass_特殊字符         | s@&_-                     |                          |                                                            |
      | ciclass_多个_并集        | Application、Cluster、s@&_- |                          |                                                            |
      | tag                  |                           | APP                      |                                                            |
      | tag_特殊字符1            |                           | @&_-~！@#￥%……&*（））——，     |                                                            |
      | tag_特殊字符2            |                           | 【、；‘。、《》？’】234567是￥      |                                                            |
      | tag_多个_交集            |                           | APP、@&_-~！@#￥%……&*（））——， |                                                            |
      | 关键字                  |                           |                          | 重要                                                         |
      | 关键字_特殊字符             |                           |                          | ~!@#$%^&*()_+`-={}【：“；‘《》，。~!@#$%^&*()_+`-={}[]:";<>?,./？￥ |
      | 关键字_多个_交集            |                           |                          | 重要、H                                                       |
      | 组合_ciClass,tag_(并、交) | Application、s@&_-         | APP、@&_-~！@#￥%……&*（））——， |                                                            |
      | 组合_ciClass,key_(并、交) | Application、Cluster       |                          | 1、0                                                        |
      | 组合_全部                | Application、Cluster       | APP、Clu                  | 重要、银                                                       |

  @Smoke
  Scenario: 关系数据_获取分类和tag列表
    When 获取系统所有ciClass和tag列表
    Then 返回结果中包含所有ciClass和tag

  @Smoke
  Scenario: 关系数据_增查删(全部清除已在initRltData中测试,不支持修改)
    Given 系统中已存在如下ci分类:"Application,s@&_-"
    And 系统中已存在如下关系分类:"AppRlt"
    When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据
    Then "AppRlt"下存在只存在1条"400网关1"与"eight"的关系数据
    When 删除"AppRlt"关系下,属性值前匹配"eight"的关系数据
    Then "AppRlt"关系下,不存在属性值前匹配"eight"的关系数据

  Scenario: 关系数据_下载模板
    Given 系统中已存在如下关系分类:"AppRlt"
    When 下载关系分类"AppRlt"的关系数据模板
    Then "AppRlt"的关系数据模板下载成功

  Scenario: 关系数据_下载当前分类数据
    Given 系统中已存在如下关系分类:"AppRlt"
    When 下载当前关系分类"AppRlt"的关系数据
    Then 当前关系分类"AppRlt"的数据下载成功
    
  Scenario: 关系数据_下载全部关系数据
    Given 系统中已存在如下关系分类:"AppRlt,特殊"
    When 下载全部关系数据
    Then 全部关系数据下载成功
    When 清除关系分类"特殊"下的所有数据
    And 给关系分类"特殊"下导入关系数据
    #下一步:由于需要判断的多，所以简单写法，无法大量批评不同
    Then 用以下数据验证导入关系报告:
    |结果|
    |分类[AppRlt]处理31条数据,添加0条,忽略0条,更新31条分类[特殊]处理10条数据,添加10条,忽略0条,更新0条|
    Then "特殊"分类的关系数据导入成功

  @Smoke @cleanRltCls  
  Scenario Outline: 关系数据_修改关系属性值
    When 创建名称为"<rltName>"的关系分类，其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名 | 属性类型 | 枚举值 |
      | 字符串 |    3 | ""  |
      | 整数  |    1 | ""  |
      | 小数  |    2 | ""  |
      | 文本  |    4 | ""  |
      | 枚举  |    6 | 月,日 |
      | 日期  |    7 | ""  |
    Then 名称为"<rltName>"的关系分类创建成功,其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名 | 属性类型 | 枚举值       |
      | 字符串 |    3 | ""        |
      | 整数  |    1 | ""        |
      | 小数  |    2 | ""        |
      | 文本  |    4 | ""        |
      | 枚举  |    6 | 月,日 |
      | 日期  |    7 | ""        |
    When 在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值      |
      | 字符串 | abc      |
      | 整数  |        1 |
      | 小数  |   2.2000 |
      | 文本  | 文本       |
      | 枚举  | 月        |
      | 日期  | 20180315 |
    Then 成功在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值      |
      | 字符串 | abc      |
      | 整数  |        1 |
      | 小数  |   2.2000 |
      | 文本  | 文本       |
      | 枚举  | 月        |
      | 日期  | 20180315 |
    When 在关系分类"<rltName>"下,源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,修改属性为:
      | 属性名 | 属性值      |
      | 字符串 | abcd     |
      | 整数  |       12 |
      | 小数  |   3.2000 |
      | 文本  | 文本3      |
      | 枚举  | 日        |
      | 日期  | 20180316 |
    Then 在关系分类"<rltName>"下,源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,修改如下属性成功
      | 属性名 | 属性值      |
      | 字符串 | abcd     |
      | 整数  |       12 |
      | 小数  |   3.2000 |
      | 文本  | 文本3      |
      | 枚举  | 日        |
      | 日期  | 20180316 |
    When 删除"<rltName>"关系下,属性值前匹配"eight"的关系数据
    Then "<rltName>"关系下,不存在属性值前匹配"eight"的关系数据
    When 删除名称为"<rltName>"的关系分类及数据
    Then 关系分类"<rltName>"分类及数据删除成功

    Examples: 
      | common | rltName  | lineAnime | lineType | lineBorder | lineDirect | copyRltName | lineDispType |
      | 不同线型   | 修改属性关系分类 |         1 | dotted   |          3 | none       | 第9次         |            1 |

  @Smoke @cleanRltCls
  Scenario Outline: 关系数据_关系属性增加子code ci之间可以创建多条关系
    When 创建名称为"<rltName>"的关系分类，其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名 | 属性类型 | 枚举值 | 是否cicode |
      | 字符串 |    3 | ""  |        1 |
      | 整数  |    1 | ""  |        1 |
    Then 名称为"<rltName>"的关系分类创建成功,其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名 | 属性类型 | 枚举值 | 是否cicode |
      | 字符串 |    3 | ""  |        1 |
      | 整数  |    1 | ""  |        1 |
    When 在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据
    Then "<rltName>"下存在只存在1条"400网关1"与"eight"的关系数据
    When 在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值 |
      | 字符串 | abc |
    Then 成功在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值 |
      | 字符串 | abc |
    When 在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值 |
      | 整数  |  12 |
    Then 成功在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值 |
      | 整数  |  12 |
    When 在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值  |
      | 字符串 | abcd |
      | 整数  |   12 |
    Then 成功在关系分类"<rltName>"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据,属性为:
      | 属性名 | 属性值  |
      | 字符串 | abcd |
      | 整数  |   12 |
    When 删除"<rltName>"关系下,属性值前匹配"eight"的关系数据
    Then "<rltName>"关系下,不存在属性值前匹配"eight"的关系数据
    When 删除名称为"<rltName>"的关系分类及数据
    Then 关系分类"<rltName>"分类及数据删除成功

    Examples: 
      | common | rltName    | lineAnime | lineType | lineBorder | lineDirect | copyRltName | lineDispType |
      | 不同线型   | 测试zicode分类 |         1 | dotted   |          3 | none       | 第9次         |            1 |



    @cleanAll @deleteTagDir @cleanRltCls
    Scenario: 根据规则自动构建CI关系
      
    When 创建名称为自动构建CI关系的CiClass目录

    When 在"自动构建CI关系"目录下,创建名称为"sourceClass"的ci分类,使用图标为"Default"
	When 在"自动构建CI关系"目录下,创建名称为"targetClass"的ci分类,使用图标为"Default"

    And 给"sourceClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |
    And 给"targetClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |

    When 给"sourceClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |            00005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-0次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb                      | ""                      | 日  | ""           | "第2-0次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc                      | ""                      | "" | ""           | "第3-0次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd    | ""                      | "" | ""           | 第4-0次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-0次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-0次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 | hhh| <div class="container"> | "" | ""           | 第7-0次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）—+—+——————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 |ggg| ""                      | "" | ""           | "第9-0次"                 | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |kkk | ""                      | "" | ""           | "第10-0次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-0次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-0次"                |                         |             | APP    |
    When 给"targetClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |             0005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-1次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb | ""                      | 日  | ""           | "第2-1次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc | ""                      | "" | ""           | "第3-1次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd | ""                      | "" | ""           | 第4-1次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-1次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-1次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 |hhh| <div class="container"> | "" | ""           | 第7-1次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）——————————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 | ggg | ""                      | "" | ""           | "第91次"                  | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |zzz| ""                      | "" | ""           | "第10-1次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-1次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-1次"                |                         |             | APP    |
     
     
    When 创建名称为"自动构建CI关系"的关系分类
	When 新建分类"sourceClass"到分类"targetClass"的关系"自动构建CI关系"
	Then 成功新建分类"sourceClass"到分类"targetClass"的关系"自动构建CI关系"
	
	Then 自动构建关系,用如下数据:
	|rltClassName|sourceClassName|targetClassName|sourceAttrName|targetAttrName|
	|自动构建CI关系|sourceClass|targetClass|文本|文本|
 	
 	Then 成功自动构建关系"13",更新关系"0"

	#type为0，用前三个参数传递；type为1，用分类关系线ID传值(也需要用到前三个参数)
	Then 不分页查询自动构建关系数据的规则信息,用如下参数:
	|rltClassName|sourceCiClassName|targetCiClassName|sourceAttrName|targetAttrName|type|
	|自动构建CI关系|sourceClass|targetClass|文本|文本|0|

	
	
	
	
	
	@cleanAll @deleteTagDir @cleanRltCls @Debug
    Scenario: CMV查询关系,不分方向
      
    When 创建名称为查询关系不分方向的CiClass目录

    When 在"查询关系不分方向"目录下,创建名称为"sourceClass"的ci分类,使用图标为"Default"
	When 在"查询关系不分方向"目录下,创建名称为"targetClass"的ci分类,使用图标为"Default"

    And 给"sourceClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |
    And 给"targetClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |

    When 给"sourceClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |            00005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-0次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb                      | ""                      | 日  | ""           | "第2-0次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc                      | ""                      | "" | ""           | "第3-0次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd    | ""                      | "" | ""           | 第4-0次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-0次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-0次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 | hhh| <div class="container"> | "" | ""           | 第7-0次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）—+—+——————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 |ggg| ""                      | "" | ""           | "第9-0次"                 | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |kkk | ""                      | "" | ""           | "第10-0次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-0次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-0次"                |                         |             | APP    |
    When 给"targetClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |             0005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-1次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb | ""                      | 日  | ""           | "第2-1次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc | ""                      | "" | ""           | "第3-1次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd | ""                      | "" | ""           | 第4-1次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-1次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-1次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 |hhh| <div class="container"> | "" | ""           | 第7-1次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）——————————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 | ggg | ""                      | "" | ""           | "第91次"                  | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |zzz| ""                      | "" | ""           | "第10-1次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-1次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-1次"                |                         |             | APP    |
     
    When 创建名称为"查询关系不分方向"的关系分类
	When 新建分类"sourceClass"到分类"targetClass"的关系"查询关系不分方向"
	Then 成功新建分类"sourceClass"到分类"targetClass"的关系"查询关系不分方向"
	When 新建分类"targetClass"到分类"sourceClass"的关系"查询关系不分方向"
	Then 成功新建分类"targetClass"到分类"sourceClass"的关系"查询关系不分方向"
	Then 自动构建关系,用如下数据:
	|rltClassName|sourceClassName|targetClassName|sourceAttrName|targetAttrName|
	|查询关系不分方向|sourceClass|targetClass|文本|文本|
 	
 	Then 不分方向查询分类关系:
 	|comment|sourceCiClass|targetCiClass|order|rltClassName|classMark|kw|
 	|正常|sourceClass|targetClass|1|查询关系不分方向|all||
 	|正常|targetClass|sourceClass|1|查询关系不分方向|all||
 	|错误的源分类|233333|targetClass|1|查询关系不分方向|all||
 	|错误的目标分类|sourceClass|233333|1|查询关系不分方向|all||
 	|错误的排序|sourceClass|targetClass|;|查询关系不分方向|all|错误的排序字段|
 	|错误的排序|sourceClass|targetClass|2333|查询关系不分方向|all|Unknown column|
#错误的排序字段
#Unknown column 	
 	
 	
 	
 	
 	
 	
 	
 		
	@cleanAll @deleteTagDir @cleanRltCls
    Scenario: 根据关系条件不分关系方向分页查询关系中目标或者源的CI信息
      
    When 创建名称为查询关系不分方向的CiClass目录

    When 在"查询关系不分方向"目录下,创建名称为"sourceClass"的ci分类,使用图标为"Default"
	When 在"查询关系不分方向"目录下,创建名称为"targetClass"的ci分类,使用图标为"Default"

    And 给"sourceClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |
    And 给"targetClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |

    When 给"sourceClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |            00005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-0次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb                      | ""                      | 日  | ""           | "第2-0次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc                      | ""                      | "" | ""           | "第3-0次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd    | ""                      | "" | ""           | 第4-0次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-0次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-0次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 | hhh| <div class="container"> | "" | ""           | 第7-0次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）—+—+——————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 |ggg| ""                      | "" | ""           | "第9-0次"                 | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |kkk | ""                      | "" | ""           | "第10-0次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-0次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-0次"                |                         |             | APP    |
    When 给"targetClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |             0005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-1次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb | ""                      | 日  | ""           | "第2-1次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc | ""                      | "" | ""           | "第3-1次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd | ""                      | "" | ""           | 第4-1次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-1次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-1次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 |hhh| <div class="container"> | "" | ""           | 第7-1次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）——————————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 | ggg | ""                      | "" | ""           | "第91次"                  | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |zzz| ""                      | "" | ""           | "第10-1次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-1次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-1次"                |                         |             | APP    |
     
    When 创建名称为"查询关系不分方向"的关系分类
	When 新建分类"sourceClass"到分类"targetClass"的关系"查询关系不分方向"
	Then 成功新建分类"sourceClass"到分类"targetClass"的关系"查询关系不分方向"
	When 新建分类"targetClass"到分类"sourceClass"的关系"查询关系不分方向"
	Then 成功新建分类"targetClass"到分类"sourceClass"的关系"查询关系不分方向"
	Then 自动构建关系,用如下数据:
	|rltClassName|sourceClassName|targetClassName|sourceAttrName|targetAttrName|
	|查询关系不分方向|sourceClass|targetClass|文本|文本|
 	
 	Then 根据关系条件不分关系方向分页查询关系中目标或者源的CI信息:
 	|rltClassName|ciCode|classId|orders|targetCiCode|结果长度|
 	|查询关系不分方向|"第1-0次"|targetClass|1|"第1-1次"|1|

 	
 	
 	
 	
 	
 	
 	
 	
 	@cleanAll @deleteTagDir @cleanRltCls
    Scenario: CMV-配置查询，删除单条/当前关系
      
    When 创建名称为配置查询删除关系的CiClass目录

    When 在"配置查询删除关系"目录下,创建名称为"sourceClass"的ci分类,使用图标为"Default"
	When 在"配置查询删除关系"目录下,创建名称为"targetClass"的ci分类,使用图标为"Default"

    And 给"sourceClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |
    And 给"targetClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |

    When 给"sourceClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |            00005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-0次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb                      | ""                      | 日  | ""           | "第2-0次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc                      | ""                      | "" | ""           | "第3-0次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd    | ""                      | "" | ""           | 第4-0次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-0次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-0次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 | hhh| <div class="container"> | "" | ""           | 第7-0次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）—+—+——————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 |ggg| ""                      | "" | ""           | "第9-0次"                 | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |kkk | ""                      | "" | ""           | "第10-0次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-0次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-0次"                |                         |             | APP    |
    When 给"targetClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |             0005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-1次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb | ""                      | 日  | ""           | "第2-1次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc | ""                      | "" | ""           | "第3-1次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd | ""                      | "" | ""           | 第4-1次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-1次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-1次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 |hhh| <div class="container"> | "" | ""           | 第7-1次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）——————————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 | ggg | ""                      | "" | ""           | "第91次"                  | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |zzz| ""                      | "" | ""           | "第10-1次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-1次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-1次"                |                         |             | APP    |
     
    When 创建名称为"配置查询删除关系"的关系分类
	When 新建分类"sourceClass"到分类"targetClass"的关系"配置查询删除关系"
	Then 成功新建分类"sourceClass"到分类"targetClass"的关系"配置查询删除关系"
	When 新建分类"targetClass"到分类"sourceClass"的关系"配置查询删除关系"
	Then 成功新建分类"targetClass"到分类"sourceClass"的关系"配置查询删除关系"
	Then 自动构建关系,用如下数据:
	|rltClassName|sourceClassName|targetClassName|sourceAttrName|targetAttrName|
	|配置查询删除关系|sourceClass|targetClass|文本|文本|
 	
 	Then 配置查询,删除单条关系,用如下数据：
 	|commont|rltClassName|sourceCiCode|targetCiCode|deleteNum|kw|
 	|正常|配置查询删除关系:配置查询删除关系|"第1-0次":"第2-0次|"第1-1次":"第2-1次|2|kw|
 	|错误rltClassName|23333|"第1-0次"|"第1-1次"|0|目标CI_IDnull不可为空|
 	|错误sourceCiCode|配置查询删除关系|23333|"第1-1次"|0|目标CI_IDnull不可为空|
 	|错误targetCiCode|配置查询删除关系|"第1-0次"|23333|0|目标CI_IDnull不可为空|
 	
 	Then 配置查询,删除当前关系,用如下数据：
 	|commont|rltClassName|sourceCiCode|targetCiClass|deleteNum|kw|
 	|正常|配置查询删除关系|"第1-0次"|targetClass|2333|kw|
 	|错误rltClassName|23333|"第1-0次"|targetClass|0|kw|
 	|错误sourceCiCode|配置查询删除关系|23333|targetClass|0|kw|
 	|错误targetCiCode|配置查询删除关系|"第1-0次"|23333|0|kw|
 	
 	
 	
 	
 	
 	
 	
 	@cleanAll @deleteTagDir @cleanRltCls
    Scenario: CMV_根据规则自动构建CI关系,另起进程
      
    When 创建名称为自动构建CI关系的CiClass目录

    When 在"自动构建CI关系"目录下,创建名称为"sourceClass"的ci分类,使用图标为"Default"
	When 在"自动构建CI关系"目录下,创建名称为"targetClass"的ci分类,使用图标为"Default"

    And 给"sourceClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |
    And 给"targetClass"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |           |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |           |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |           |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |           |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"] |       0 |                  |
      | 日期      | 日期                    |  0 |     1 |           |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |           |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |           |       0 | 去玩儿提法国红酒考拉海购     |
      | 字典_type | 数据字典_type_Application |  0 |     1 |           |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |           |       0 |                  |

    When 给"sourceClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |            00005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-0次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb                      | ""                      | 日  | ""           | "第2-0次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc                      | ""                      | "" | ""           | "第3-0次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd    | ""                      | "" | ""           | 第4-0次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-0次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-0次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 | hhh| <div class="container"> | "" | ""           | 第7-0次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）—+—+——————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 |ggg| ""                      | "" | ""           | "第9-0次"                 | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |kkk | ""                      | "" | ""           | "第10-0次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-0次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-0次"                |                         |             | APP    |
    When 给"targetClass"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                     | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |             0005 | aaa | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1-1次"                 | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | bbb | ""                      | 日  | ""           | "第2-1次                  | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ccc | ""                      | "" | ""           | "第3-1次"                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ddd | ""                      | "" | ""           | 第4-1次                   | ""                      |             |        |
      | 文本_js     |                1 |                0 | eee | ""                      | "" | ""           | 第5-1次                   | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | fff | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6-1次                   | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 |hhh| <div class="container"> | "" | ""           | 第7-1次                   | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | iii | ""                      | "" | ""           | ~！@#￥%……&*（）——————————· | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 | ggg | ""                      | "" | ""           | "第91次"                  | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 |zzz| ""                      | "" | ""           | "第10-1次"                | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11-1次"                |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12-1次"                |                         |             | APP    |
     
     
    When 创建名称为"自动构建CI关系"的关系分类
	When 新建分类"sourceClass"到分类"targetClass"的关系"自动构建CI关系"
	Then 成功新建分类"sourceClass"到分类"targetClass"的关系"自动构建CI关系"
	
	Then 异步自动构建关系,用如下数据:
	|rltClassName|sourceClassName|targetClassName|sourceAttrName|targetAttrName|
	|自动构建CI关系|sourceClass|targetClass|文本|文本|
 	#更新状态，0为正在进行，-1为完成 
 	#由于去掉了此功能，所以暂时注释
 	#Then 异步成功自动构建关系"13",更新关系"0",用key:"autosaveRlt",更新状态为:"-1"
 	