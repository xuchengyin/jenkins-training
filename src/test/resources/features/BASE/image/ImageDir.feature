@BASE
@CleanImgDir
Feature: BASE_图标管理_ImageDIr

  @Smoke
  Scenario Outline: ImageDIr_增删改查
    When 创建名称为"<dirName>"的ImageClass目录
    Then 系统中存在名称"<dirName>"的ImageClass目录
    When 修改ImageClass目录<dirName>的名称为<dirNameModify>
    Then ImageClass目录名称从<dirName>更新为<dirNameModify>
    When 删除名称为"<dirNameModify>"的ImageClass目录
    Then 系统中不存在名称为"<dirNameModify>"的ImageClass目录

    Examples: 字符类型校验
      | case | dirName              | dirNameModify        | 
      | 中英文  | 中ing1                | 修改后                  | 
      | 空字符串 | UUUU&&&&&&&&&&       | !@#$%^&*()_+         |  
      | 特殊字符 | !@%^^^^^^^^^^        | 、.《                  | 
      | 最大长度 | 只能123456ing只能123456i | 只能123456ing只能12345中文 |
      | 最大长度_中文 | 测试图标目录最大中文长度为三十的中文图标测试图标目录最大中文 |测试图标目录最大中文长度为三十的中文图标测试图标目录最大中文 |

  #    @Smoke
  #   Scenario Outline:ImageDIr_导入、导出——老接口 不用了，先留着
  #		When 图标管理导入<dirName>图片
  #		Then <dirName>图片导入成功
  #		When 导出<dirName>图片
  #		Then <dirName>图片导出成功
  #		When 删除名称为"<dirName>"的ImageClass目录
  #		Then 系统中不存在名称为"<dirName>"的ImageClass目录
  #		Examples: 图标名称的最大长度(40)、特殊字符
  #			|dirName|
  #			| 工商银行&_机柜-|
  #			| 玩体育我我还看见和能够部分看见和太天而再|
  @Smoke
  Scenario Outline: ImageDIr_导入、导出_新接口
    When 图标管理导入图片文件"<dirName>"
    Then <dirName>图片导入成功
    When 导出<dirName>图片
    Then <dirName>图片导出成功
    When 删除名称为"<dirName>"的ImageClass目录
    Then 系统中不存在名称为"<dirName>"的ImageClass目录

    Examples: 图标名称的最大长度(40)、特殊字符
      | dirName              |
      | 工商银行&_机柜-            |
      | 玩体育我我还看见和能够部分看见和太天而再 |

  @Smoke
  Scenario: ImageDIr_重名检查
    When 创建名称为"imageTest"的ImageClass目录
    Then 系统中存在名称"imageTest"的ImageClass目录
    When 再次创建名称为"imageTest"的ImageClass目录，创建失败，kw="目录名称[imageTest]已存在"
    When 删除名称为"imageTest"的ImageClass目录
    Then 系统中不存在名称为"imageTest"的ImageClass目录

  @Debug
  Scenario: ImageDIr_不同目录下添加相同的图标
    When 创建多个ImageClass目录:
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
    Then 成功创建多个ImageClass目录:
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
    When 给多个目录添加"1122uu"图片:
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
    Then 给多个目录添加"1122uu"图片成功:
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
    When 给多个目录删除名称为"1122uu"的图片:
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
    Then 多个目录系统中不存在名称为"1122uu"的图片:
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
    When 删除多个ImageClass目录：
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
    Then 系统中不存在多个ImageClass目录:
      | dirName |
      | 图标目录1   |
      | 图标目录2   |
