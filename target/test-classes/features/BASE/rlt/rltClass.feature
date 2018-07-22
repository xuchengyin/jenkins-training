@BASE
@cleanRltCls
Feature: BASE_关系管理_rltClass

  @Smoke
  Scenario Outline: rltClass_创建不同名称、线型及属性、删除
    When 创建名称为"<rltName>"的关系分类，其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名                    | 属性类型 | 枚举值 |
      | 字符串                    |    3 | ""  |
      | 整数                     |    1 | ""  |
      | 小数                     |    2 | ""  |
      | 文本                     |    4 | ""  |
      | 枚举                     |    6 | 月,日 |
      | 日期                     |    7 | ""  |
      | @&_-34567654sdfghjklljhgfd订房卡大附是  收到 多少爱的风格户籍科玩儿天圆|    2 | ""  |
      | 关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系 |    2 | ""  |
    Then 名称为"<rltName>"的关系分类创建成功,其动态效果为"<lineAnime>",关系样式为"<lineType>",关系宽度为"<lineBorder>",关系箭头为"<lineDirect>",显示类型为"<lineDispType>",属性定义如下的:
      | 属性名                    | 属性类型 | 枚举值 |
      | 字符串                    |    3 | ""  |
      | 整数                     |    1 | ""  |
      | 小数                     |    2 | ""  |
      | 文本                     |    4 | ""  |
      | 枚举                     |    6 |  月,日 |
      | 日期                     |    7 | ""  |
      | @&_-34567654sdfghjklljhgfd订房卡大附是  收到 多少爱的风格户籍科玩儿天圆|    2 | ""  |
      | 关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系属性名称关系 |    2 | ""  |
   	When 复制"<rltName>"关系分类为"<copyRltName>"	
   	Then "<rltName>"关系分类成功复制为"<copyRltName>"
    When 删除名称为"<rltName>"的关系分类及数据
    Then 关系分类"<rltName>"分类及数据删除成功
    When 删除名称为"<copyRltName>"的关系分类及数据
    Then 关系分类"<copyRltName>"分类及数据删除成功

    Examples: 
      | common   | rltName               | lineAnime | lineType | lineBorder | lineDirect   | copyRltName     |lineDispType|
      | 正常数据     | 正常字符串123              |         1 | solid    |          1 | classic      | @& _-           |1|
      | 关系名称_最大长度31 | 测试关系名称最大长度为三十一的关系分类名称测试最大长度为三十一 |         0 | dashed   |          2 | block        | kasjdlkaa中文1345 |1|
      | 关系名称特殊字符 | 1234567890-&_@ asldjf|         0 | dashed   |          2 | block        | kasjdlkaa中文1345 |1|
      | 不同线型     | 第3次                   |         1 | dotted   |          3 | none         | 第9次             |1|
      | 不同线型     | 第4次                   |         1 | dotted   |          4 | open         | 第10次            |1|
      | 不同线型     | 第5次                   |         1 | dotted   |          5 | diamondThin  | 第11次            |1|
      | 不同线型容器| 第6次                   |         1 | dotted   |          6 | diamondTrans | 第12次            |2|
      | 不同线型容器| 第7次                   |         1 | dotted   |          8 | diamondTrans | 第13次            |2|
      | 不同线型容器| 第8次                   |         1 | dotted   |         10 | diamondTrans | 第14次            |2|

  Scenario Outline: 关系分类_搜索
    When 创建名称为"<rltClassName>"的关系分类
    And 搜索名称包含"<searchKey>"的关系分类
    Then 包含"<searchKey>"关键字的的关系分类全部搜索出来
    When 删除名称为"<rltClassName>"的关系分类及数据
    Then 关系分类"<rltClassName>"分类及数据删除成功

    Examples: 
      | common | rltClassName          | searchKey             |
      | 部分匹配   | 正常字符大串123             | 字符大串1                 |
      | 全匹配    | 测试关系名称最大长度为三十一的关系分类名称测试最大长度为三十一 | 测试关系名称最大长度为三十一的关系分类名称测试最大长度为三十一 |
      | 匹配符    | 关系名称_最大长度31及特殊字符串     | %大%串                  |
