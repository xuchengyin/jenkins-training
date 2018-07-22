@BASE
Feature: BASE_可视化建模_Visual

  @Smoke
  Scenario Outline: visual_新建分类关系
    When 新建分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    Then 成功新建分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    When 删除分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    Then 分类关系分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"删除成功

    Examples: 数据
      | case       | sourceClassName | targetClassName | rltClassName   |
      | 分类和关系都存在   | Cluster         | Application     | AppRlt         |
      | 分类存在关系不存在  | Application     | Cluster         | AppRlt99900    |
      | 分类不存在关系不存在 | Application     | Cluster9900     | AppRlt99900%%% |
      | 分类不存在关系存在  | Application9900 | Cluster         | AppRlt         |

  Scenario: visual_查询所有分类关系
    When 查询所有分类关系
    Then 所有分类关系查询正确

  @Smoke
  Scenario: visual_分组查询分类_显示CI数量
    When 分组查询对象分类
    Then 成功分组查询对象分类

  @Smoke @cleanRltCls @CleanCiCls @CleanCiClsDir
  Scenario Outline: visual_新建分类目录、新建分类、新建分类之间的关系
    When 创建名称为<ciClassDir>的CiClass目录
    When 在"<ciClassDir>"目录下,创建名称为"<sourceClassName>"的ci分类,使用图标为"<imageName>"
    Then 系统中存在名称为"<sourceClassName>"的ci分类
    When 在"<ciClassDir>"目录下,创建名称为"<targetClassName>"的ci分类,使用图标为"<imageName>"
    Then 系统中存在名称为"<targetClassName>"的ci分类
    When 创建名称为"<rltClassName>"的关系分类
    When 新建分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    Then 成功新建分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    When 删除分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    Then 分类关系分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"删除成功
    When 删除名称为"<rltClassName>"的关系分类及数据
    When 删除"<sourceClassName>"分类及数据
    When 删除"<targetClassName>"分类及数据
    When 删除名称为<ciClassDir>的CiClass目录
    Then 系统中不存在名称为<ciClassDir>的CiClass目录

    Examples: 字符类型校验
      | case    | ciClassDir | sourceClassName | imageName | targetClassName | rltClassName |
      | 可视化建模使用 | 可视化建模      | 可视化建模源分类        | Default   | 可视化建模目的分类       | 可视化建模分类关系    |

  @Smoke
  Scenario: visual_打开可视化建模页面
    When 读取导航栏信息
    When 查询所有分类及其属性
    When 保存可视化建模
    When 查询可视化建模
    Then 成功查询可视化建模

  @Smoke
  Scenario: visual_更新
    When 查询所有分类及其属性
    Then 成功查询所有分类及其属性
    When 分组查询对象分类
    Then 成功分组查询对象分类
    When 查询所有分类关系
    Then 所有分类关系查询正确

  Scenario Outline: visual_搜索CI分类
    When 按照关键字"<keyName>"进行CI分类搜索
    Then 成功按照关键字"<keyName>"进行CI分类搜索

    Examples: 搜索关键字
      | case | keyName             |
      | 分类全名 | Application         |
      | 小写   | app                 |
      | 空    |                     |
      | 下划线  | _                   |
      | 不存在的 | Applications1234567 |
      | 特殊字符 | @                   |
      | 特殊字符 | &                   |
      | 特殊字符 | %                   |

  @Smoke @delKpiTpl @delKpi @Debug
  Scenario Outline: visual_给CI分类挂载指标模板
    When 创建名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    Then 系统中存在名称为"指标模型"，别名为"模型"，指标描述为"描述"，单位为"K"的指标
    When 创建模板名称为"<tplName>"，别名为"dddd"，模板描述为"3333"，指标应用类型为"1"，指标模型名称为"指标模型"，分类对象组为""，标签对象组为""的指标模板
    Then 系统中存在模板名称为"<tplName>"，别名为"dddd"，模板描述为"3333"，指标应用类型为"1"，指标模型名称为"指标模型"，分类对象组为""，标签对象组为""的指标模板
    When 给CI分类"<ciClsName>"挂载指标模板"<tplName>"
    Then 成功给CI分类"<ciClsName>"挂载指标模板"<tplName>"
    When 查看CI分类"<ciClsName>"挂载指标模板信息
    Then CI分类"<ciClsName>"挂载指标模板信息中包含"<tplName>"

    Examples: 
      | case | ciClsName   | tplName |
      | 分类全名 | Application |   66666 |

  @Smoke @CleanCiCls @CleanCiClsDir
  Scenario Outline: visual_全场景
    When 创建名称为<ciClassDir>的CiClass目录
    Then 系统中存在名称<ciClassDir>的CiClass目录
    When 在"<ciClassDir>"目录下,创建名称为"<ciClassName>"的ci分类,使用图标为"<imageName>"
    And 给"<ciClassName>"分类添加如下属性:
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
    Then "<ciClassName>"分类属性更新成功
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
    When 给"<ciClassName>"添加如下数据:
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                 | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |             0005 | afdkaja中阿达佛挡杀佛123%……&   | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1次"               | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | ""                      | ""                      | 日  | ""           | "第2次                | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ""                      | ""                      | "" | ""           | "第3次"               | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | 文本_最大长度1000及特殊字符.txt    | ""                      | "" | ""           | 第4次                 | ""                      |             |        |
      | 文本_js     |                1 |                0 | <div class="container"> | ""                      | "" | ""           | 第5次                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ""                      | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6次                 | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 | ""                      | <div class="container"> | "" | ""           | 第7次                 | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | ""                      | ""                      | "" | ""           | 主键值_最大长度50及特殊字符.txt | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 | ""                      | ""                      | "" | ""           | "第9次"               | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 | ""                      | ""                      | "" | ""           | "第10次"              | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11次"              |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12次"              |                         |             | APP    |
    Then "<ciClassName>"分类数据添加成功
      | commont   | 整数               | 小数               | 文本                      | 文章                      | 枚举 | 日期           | 字符串                 | 名称                      | 字典_type     | 字典_tag |
      | 正常        |                1 |             0005 | afdkaja中阿达佛挡杀佛123%……&   | 2345sdfg直接抛能够&￥%。       | 月  | 4567890-已囧ok | "第1次"               | ""                      |             |        |
      | 整数_最大长度   | 1234567890009876 |                0 | ""                      | ""                      | 日  | ""           | "第2次                | ""                      |             |        |
      | 小数_最大长度   |                1 | 99999999999.9999 | ""                      | ""                      | "" | ""           | "第3次"               | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | 文本_最大长度1000及特殊字符.txt    | ""                      | "" | ""           | 第4次                 | ""                      |             |        |
      | 文本_js     |                1 |                0 | <div class="container"> | ""                      | "" | ""           | 第5次                 | ""                      |             |        |
      | 文本_最大长度   |                1 |                0 | ""                      | 文章_长度2000及特殊字符.txt      | "" | ""           | 第6次                 | ""                      |             |        |
      | 文本_最大长js度 |                1 |                0 | ""                      | <div class="container"> | "" | ""           | 第7次                 | ""                      |             |        |
      | 主键_最大长度   |                1 |                0 | ""                      | ""                      | "" | ""           | 主键值_最大长度50及特殊字符.txt | ""                      |             |        |
      | 字符串_最大长度  |                1 |                0 | ""                      | ""                      | "" | ""           | "第9次"               | 字符串_最大长度200及特殊字符.txt    |             |        |
      | 字符串_js    |                1 |                0 | ""                      | ""                      | "" | ""           | "第10次"              | <div class="container"> | Application |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第11次"              |                         |             |        |
      | 默认值       |                  |                  |                         |                         | "" |              | "第12次"              |                         |             | APP    |
    When 删除"<ciClassName>"分类及数据
    Then 名称为"<ciClassName>"的分类删除成功
    When 删除名称为<ciClassDir>的CiClass目录
    Then 系统中不存在名称为<ciClassDir>的CiClass目录

    Examples: 字符类型校验
      | case    | ciClassDir | ciClassName | imageName |
      | 可视化建模使用 | 可视化建模      | 可视化建模源分类    | Default   |

  @Smoke @cleanRltCls @CleanCiCls @CleanCiClsDir
  Scenario Outline: visual_新建影响分析关系模型
    When 创建名称为<ciClassDir>的CiClass目录
    When 在"<ciClassDir>"目录下,创建名称为"<sourceClassName>"的ci分类,使用图标为"<imageName>"
    Then 系统中存在名称为"<sourceClassName>"的ci分类
    When 在"<ciClassDir>"目录下,创建名称为"<targetClassName>"的ci分类,使用图标为"<imageName>"
    Then 系统中存在名称为"<targetClassName>"的ci分类
    When 创建名称为"<rltClassName>"的关系分类
    When 新建分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    Then 成功新建分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    When 保存可视化建模中影响分析关系模型热点分类为"<sourceClassName>"，分类关系如下:
      | common | sourceClassName | targetClassName | rltClassName |
      | 描述     | 可视化建模源分类        | 可视化建模目的分类       | 可视化建模分类关系    |
    Then 成功保存可视化建模中影响分析关系模型热点分类为"<sourceClassName>"，分类关系如下:
      | common | sourceClassName | targetClassName | rltClassName |
      | 描述     | 可视化建模源分类        | 可视化建模目的分类       | 可视化建模分类关系    |
    When 删除分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"
    Then 分类关系分类"<sourceClassName>"到分类"<targetClassName>"的关系"<rltClassName>"删除成功
    When 删除名称为"<rltClassName>"的关系分类及数据
    When 删除"<sourceClassName>"分类及数据
    When 删除"<targetClassName>"分类及数据
    When 删除名称为<ciClassDir>的CiClass目录
    Then 系统中不存在名称为<ciClassDir>的CiClass目录

    Examples: 字符类型校验
      | case    | ciClassDir | sourceClassName | imageName | targetClassName | rltClassName |
      | 可视化建模使用 | 可视化建模      | 可视化建模源分类        | Default   | 可视化建模目的分类       | 可视化建模分类关系    |
