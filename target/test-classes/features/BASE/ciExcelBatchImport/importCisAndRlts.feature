@BASE
Feature: BASE_一键导入

  Scenario: 导入分类_映射新分类(覆盖属性类型、忽略属性、新增属性、多sheet、多excel表、全量上传)
    When 上传分类导入所需excel表:"importDatas_新建分类.xls,importDatas_已有分类.xls"
    Then 成功获取如下excel表中各sheet分类数据:
      | commons         | excelName        | sheetName |
      | 映射新分类_已有属性(主键)  | importDatas_新建分类 | 新建分类映射1   |
      | 映射新分类_多个sheet_1 | importDatas_新建分类 | 新建分类映射2   |
      | 映射新分类_多个sheet_2 | importDatas_新建分类 | 新建分类映射3   |
      | 映射新分类_多个excel_2 | importDatas_已有分类 | 已有分类映射    |
    And 按照如下设置进行分类映射,并全量上传:
      #proType的类型为：1--整数 2--小数  3--字符串  4--文本  5--文章 6--枚举  7--日期  8--字典
      | commons                         | excelName        | sheetName | clsName | excelAttr | clsAttr   | ignore | proType | isMajor | isCiDisp | isRequired | importType |
      | 映射新分类_已有属性(主键)                  | importDatas_新建分类 | 新建分类映射1   | ciTag   | CI 名称     | CI Code   |      0 |       1 |       1 |        1 |          1 |          1 |
      | 映射新分类_新建属性_excelAttr与clsAttr不一致 | importDatas_新建分类 | 新建分类映射1   | ciTag   | 公司        | company   |      0 |       3 |       0 |        0 |          1 |          1 |
      | 映射新分类_新建属性_修改属性类型               | importDatas_新建分类 | 新建分类映射1   | ciTag   | LUN大小     | LUN Level |      0 |       2 |       0 |        0 |          1 |          1 |
      | 映射新分类_新建属性_属性忽略                 | importDatas_新建分类 | 新建分类映射1   | ciTag   | 状态        | 状态        |      1 |       0 |       0 |        0 |          0 |          0 |
      | 映射新分类_多个sheet_1                 | importDatas_新建分类 | 新建分类映射2   | ciTag5  | CI编号      | CI Code   |      0 |       3 |       1 |        0 |          0 |          1 |
      | 映射新分类_多个sheet_2                 | importDatas_新建分类 | 新建分类映射3   | ciTag6  | LUN名称     | CI Code   |      0 |       3 |       1 |        0 |          0 |          0 |
      | 映射新分类_多个excel_2                 | importDatas_已有分类 | 已有分类映射    | 多excel表 | ci 名称     | CI Code   |      0 |       3 |       1 |        0 |          0 |          0 |
    Then 获取"分类数据"上传报告
    And 如下分类数据通过"一键导入"全量上传成功:
      | commons         | excelName        | sheetName | clsName |
      | 映射新分类_已有属性(主键)  | importDatas_新建分类 | 新建分类映射1   | ciTag   |
      | 映射新分类_多个sheet_1 | importDatas_新建分类 | 新建分类映射2   | ciTag5  |
      | 映射新分类_多个sheet_2 | importDatas_新建分类 | 新建分类映射3   | ciTag6  |
      | 映射新分类_多个excel_2 | importDatas_已有分类 | 已有分类映射    | 多excel表 |
    When 删除如下分类及数据:"ciTag,ciTag6,ciTag5,多excel表"
    Then 如下分类数据删除成功:"ciTag,ciTag6,ciTag5,多excel表"

  @CleanCiCls
  Scenario: 导入分类_映射已有分类，匹配多个属性字段(覆盖属性类型、忽略属性、新增属性、全量上传、多sheet、多excel表、全量上传)
    Given 在"业务领域"目录下,创建名称为"existCiCls"的ci分类,使用图标为"Default"
    And 给"existCiCls"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值           | CI Code | 默认值              |
      | CI 名称   | 字符串                   |  1 |     0 |               |       1 |         12345678 |
      | 层级      | 整数                    |  0 |     0 |               |       0 | 234.000000000000 |
      | 是否双活    | 小数                    |  0 |     0 |               |       0 | werdfghnbvc      |
      | 所属存储池   | 文本                    |  0 |     0 |               |       0 | @&()__——（）       |
      | 监控情况    | 枚举                    |  0 |     0 | ["有代理","无代理"] |       0 |                  |
      | 分配日期    | 日期                    |  0 |     0 |               |       0 | 1990-10-90       |
      | 关联主机端口  | 文章                    |  0 |     0 |               |       0 |                  |
      | 字典_type | 数据字典_type_Application |  0 |     0 |               |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     0 |               |       0 |                  |
    And 在"业务领域"目录下,创建名称为"已有属性_多sheet"的ci分类,使用图标为"Default"
    And 给"已有属性_多sheet"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值      |
      | CI名称 | 字符串  |  1 |     0 |     |       1 | 12345678 |
    And 在"业务领域"目录下,创建名称为"moreSheet"的ci分类,使用图标为"Default"
    And 给"moreSheet"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值      |
      | CI名称 | 字符串  |  1 |     0 |     |       1 | 12345678 |
    When 上传分类导入所需excel表:"importDatas_已有分类.xls,importDatas_新建分类.xls"
    Then 成功获取如下excel表中各sheet分类数据:
      | commons         | excelName        | sheetName |
      | 映射新分类_已有属性(主键)  | importDatas_已有分类 | 已有分类映射    |
      | 映射新分类_多个sheet_1 | importDatas_已有分类 | 已有分类映射2   |
      | 映射新分类_多个excel_2 | importDatas_新建分类 | 新建分类映射2   |
    And 按照如下设置进行分类映射,并全量上传:
      #proType的类型为：1--整数 2--小数  3--字符串  4--文本  5--文章 6--枚举  7--日期  8--字典
      | commons                      | excelName        | sheetName | clsName     | excelAttr | clsAttr | ignore | proType | isMajor | isCiDisp | isRequired | importType |
      | 匹配已有属性_主键                    | importDatas_已有分类 | 已有分类映射    | existCiCls  | ci 名称     | CI 名称   |      0 |       3 |       1 |        1 |          1 |          1 |
      | 匹配已有属性_数据类型映射_忽略属性(bug)      | importDatas_已有分类 | 已有分类映射    | existCiCls  | 层级        | 整数      |      1 |       1 |       0 |        0 |          0 |          1 |
      | 匹配已有属性_数据类型映射_已有属性类型         | importDatas_已有分类 | 已有分类映射    | existCiCls  | 是否双活      | 是否双活    |      0 |       2 |       0 |        0 |          0 |          1 |
      | 匹配已有属性_数据类型映射_新增属性(属性只能为字符串) | importDatas_已有分类 | 已有分类映射    | existCiCls  | 所属存储池     | 文本      |      0 |       4 |       0 |        0 |          0 |          1 |
      | 匹配已有属性_多个sheet表              | importDatas_已有分类 | 已有分类映射2   | 已有属性_多sheet | CI 名称     | CI名称    |      0 |       3 |       0 |        0 |          0 |          1 |
      | 匹配已有属性_多excel表               | importDatas_新建分类 | 新建分类映射2   | moreSheet   | ci名称      | CI名称    |      0 |       3 |       1 |        0 |          0 |          1 |
    Then 获取"分类数据"上传报告
    And 如下分类数据通过"一键导入"全量上传成功:
      | commons         | excelName        | sheetName | clsName     |
      | 映射新分类_已有属性(主键)  | importDatas_已有分类 | 已有分类映射    | existCiCls  |
      | 映射新分类_多个sheet_1 | importDatas_已有分类 | 已有分类映射2   | 已有属性_多sheet |
      | 映射新分类_多个excel_2 | importDatas_新建分类 | 新建分类映射2   | moreSheet   |
    When 删除如下分类及数据:"existCiCls,已有属性_多sheet,moreSheet"
    Then 如下分类数据删除成功:"existCiCls,已有属性_多sheet,moreSheet"

  Scenario: 导入分类_映射新分类(增量)
    When 上传分类导入所需excel表:"importDatas_增量测试1.xls"
    And 按照如下设置进行分类映射,并全量上传:
      #proType的类型为：1--整数 2--小数  3--字符串  4--文本  5--文章 6--枚举  7--日期  8--字典
      | commons           | excelName         | sheetName | clsName | excelAttr | clsAttr | ignore | proType | isMajor | isCiDisp | isRequired | importType |
      | 映射分类_增量上传测试       | importDatas_增量测试1 | 增量测试1     | 增量测试1   | Name      | CI Code |      0 |       1 |       1 |        1 |          1 |          0 |
      | 映射分类_增量上传多sheet_2 | importDatas_增量测试1 | 增量测试2     | 增量测试2   | Name      | CI Code |      0 |       3 |       1 |        0 |          0 |          0 |
    And 如下分类数据通过"一键导入"全量上传成功:
      | commons           | excelName         | sheetName | clsName |
      | 映射分类_增量上传测试       | importDatas_增量测试1 | 增量测试1     | 增量测试1   |
      | 映射分类_增量上传多sheet_2 | importDatas_增量测试1 | 增量测试2     | 增量测试2   |
    When 上传分类导入所需excel表:"importDatas_增量测试2.xls"
    Then 成功获取如下excel表中各sheet分类数据:
      | commons           | excelName         | sheetName |
      | 映射分类_增量上传测试       | importDatas_增量测试2 | 增量测试3     |
      | 映射分类_增量上传多sheet_2 | importDatas_增量测试2 | 增量测试4     |
    And 按照如下设置进行分类映射,并增量上传:
      #proType的类型为：1--整数 2--小数  3--字符串  4--文本  5--文章 6--枚举  7--日期  8--字典
      | commons           | excelName         | sheetName | clsName | excelAttr | clsAttr | ignore | proType | isMajor | isCiDisp | isRequired | importType |
      | 映射分类_增量上传测试       | importDatas_增量测试2 | 增量测试3     | 增量测试1   | Name      | CI Code |      0 |       3 |       1 |        0 |          0 |          0 |
      | 映射分类_增量上传多sheet_1 | importDatas_增量测试2 | 增量测试4     | 增量测试2   | Name      | CI Code |      0 |       1 |       1 |        1 |          0 |          0 |
    Then 如下分类数据通过"一键导入"增量上传成功:
      | commons           | excelName         | sheetName | clsName |
      | 映射分类_增量上传测试       | importDatas_增量测试2 | 增量测试3     | 增量测试1   |
      | 映射分类_增量上传多sheet_2 | importDatas_增量测试2 | 增量测试4     | 增量测试2   |
    When 删除如下分类及数据:"增量测试1,增量测试2"
    Then 如下分类数据删除成功:"增量测试1,增量测试2"

  @Debug @Smoke @CleanImportCiCls
  Scenario Outline: 导入分类_新建分类
    When 导入excel文档"<fileName>"
    Then 成功导入excel文档"<fileName>"

    Examples: 
      | desc   | fileName             |
      | 不存在的分类 | importDatas_新建分类.xls |
      | 不存在的分类 | importDatas_新建分类.xls |

  @Smoke @CleanCiCls
  Scenario: 导入分类_已有分类，匹配多个属性字段(覆盖属性类型、忽略属性、新增属性、全量上传、多sheet、多excel表、全量上传)
    Given 在"业务领域"目录下,创建名称为"已有分类映射"的ci分类,使用图标为"Default"
    And 给"已有分类映射"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值           | CI Code | 默认值              |
      | ci 名称   | 字符串                   |  1 |     0 |               |       1 |         12345678 |
      | 层级      | 整数                    |  0 |     0 |               |       0 | 234.000000000000 |
      | 是否双活    | 小数                    |  0 |     0 |               |       0 | werdfghnbvc      |
      | 所属存储池   | 文本                    |  0 |     0 |               |       0 | @&()__——（）       |
      | 监控情况    | 枚举                    |  0 |     0 | ["有代理","无代理"] |       0 |                  |
      | 分配日期    | 日期                    |  0 |     0 |               |       0 | 1990-10-90       |
      | 关联主机端口  | 文章                    |  0 |     0 |               |       0 |                  |
      | 字典_type | 数据字典_type_Application |  0 |     0 |               |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     0 |               |       0 |                  |
    And 在"业务领域"目录下,创建名称为"已有分类映射2"的ci分类,使用图标为"Default"
    And 给"已有分类映射2"分类添加如下属性:
      | 属性名称  | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值      |
      | CI 名称 | 字符串  |  1 |     0 |     |       1 | 12345678 |
    When 导入excel文档"importDatas_已有分类.xls"
    Then 成功导入excel文档"importDatas_已有分类.xls"
    When 删除如下分类及数据:"已有分类映射,已有分类映射2"
    Then 如下分类数据删除成功:"已有分类映射,已有分类映射2"

  @Debug @Smoke @CleanCiCls
  Scenario: 导入分类_已有分类分类名称大小写
    Given 在"业务领域"目录下,创建名称为"aaa"的ci分类,使用图标为"Default"
    And 给"aaa"分类添加如下属性:
      | 属性名称    | 属性类型                  | 必填 | Lable | 枚举值           | CI Code | 默认值              |
      | CI 名称   | 字符串                   |  1 |     0 |               |       1 |         12345678 |
      | 层级      | 整数                    |  0 |     0 |               |       0 | 234.000000000000 |
      | 是否双活    | 小数                    |  0 |     0 |               |       0 | werdfghnbvc      |
      | 所属存储池   | 文本                    |  0 |     0 |               |       0 | @&()__——（）       |
      | 监控情况    | 枚举                    |  0 |     0 | ["有代理","无代理"] |       0 |                  |
      | 分配日期    | 日期                    |  0 |     0 |               |       0 | 1990-10-90       |
      | 关联主机端口  | 文章                    |  0 |     0 |               |       0 |                  |
      | 字典_type | 数据字典_type_Application |  0 |     0 |               |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     0 |               |       0 |                  |
    And 在"业务领域"目录下,创建名称为"BBB"的ci分类,使用图标为"Default"
    And 给"BBB"分类添加如下属性:
      | 属性名称 | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值      |
      | CI编号 | 字符串  |  1 |     0 |     |       1 | 12345678 |
    When 导入excel文档"importDatas_分类名称为英文.xls"
    Then 成功导入excel文档"importDatas_分类名称为英文.xls"
    When 删除如下分类及数据:"aaa,BBB"
    Then 如下分类数据删除成功:"aaa,BBB"

  @Debug @Smoke @CleanImportCiCls
  Scenario Outline: 导入分类_sheet名称为特殊符号和大于30个字符
    When 导入excel文档"<fileName>"
    Then 成功导入excel文档"<fileName>"

    Examples: 
      | desc   | fileName                    |
      | 不存在的分类 | importDatas_分类名称含合法特殊字符.xls |

  @Smoke @CleanImportCiCls
  Scenario Outline: 导入分类_空文档
    When 导入excel文档"<fileName>"
    Then 导入excel文档"<fileName>"失败

    Examples: 
      | desc          | fileName            |
      | 内容为空的excell文档 | importDatas_空文件.xls |

  @Debug @Smoke
  Scenario Outline: 导入分类_非excel文件失败
    When 上传非excel文档"<fileName>"失败

    Examples: 
      | desc     | fileName    |
      | 非excel文档 | 文本文件.txt    |
      | 非excel文档 | 图片.jpg      |
      | 非excel文档 | zipfile.zip |
