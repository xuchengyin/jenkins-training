@BASE
@CleanImgDir
Feature: BASE_图标管理_Image

  @Smoke
  Scenario Outline: Image_添加图片、更改图片、删除图片
    When 创建名称为"<dirName>"的ImageClass目录
    Then 系统中存在名称"<dirName>"的ImageClass目录
    When 给"<dirName>"目录添加"<addImage>"图片
    Then 这个"<dirName>"目录"<addImage>"图片添加成功
    When 将"<addImage>"图片替换为"<updateImage>"图片
    Then 图片成功更新为"<addImage>"
    When 删除名称为"<addImage>"的图片
    Then 系统中不存在名称为"<addImage>"的图片
    When 删除名称为"<dirName>"的ImageClass目录
    Then 系统中不存在名称为"<dirName>"的ImageClass目录

    Examples: 图标名称的最大长度(200)、特殊字符
      | dirName         | addImage                                        | updateImage |
      | 中ing1222        | 1122uu                                          |      445555 |
      | UUUU&$$$$$$$$$$ | UUUUUYYYYYYYYYYYYYYYYYYYYYYOOOOOOOOOOOOOOOOO%#@ | YYYPP       |

  Scenario Outline: 图标管理_图片搜索
    When 图标管理导入图片文件"<dirName>"
    And 搜索名称包含"<searchKey>"的图片
    Then 包含"<searchKey>"关键字的的图片全部搜索出来
    When 删除名称为"<dirName>"的ImageClass目录
    Then 系统中不存在名称为"<dirName>"的ImageClass目录

    Examples: 
      | common | dirName                 | searchKey                       |
      | 部分匹配   | 工商银行&_机柜-111            | 字符大串1                           |
      | 全匹配    | 玩体育我我还看见和能够部分看见和太天而再222 | 1234567890-&_@ asldjf中首发大刷卡机法轮大 |
      | 全匹配    | 玩体育我我还看见和能够部分看见和太天而再    | 1234567890-&_@ asldjf中首发大刷卡机法轮大 |
      | 匹配符    |                  112233 | %大%串                            |

  @Smoke
  Scenario: Image_图片移动位置，删除多张图片
    When 创建名称为"imageTest"的ImageClass目录
    Then 系统中存在名称"imageTest"的ImageClass目录
    When 给"imageTest"目录添加"1122uu,445555"多张图片
    When 给这个"imageTest"目录 的图片"1122uu"移动位置
    Then 图片"1122uu"位置成功移动
    When 删除名称为"1122uu,445555"的多张图片
    Then 系统中不存在名称为"1122uu,445555"的多张图片
    When 删除名称为"imageTest"的ImageClass目录
    Then 系统中不存在名称为"imageTest"的ImageClass目录

  @Smoke
  Scenario Outline: Image_图片挂载DCV3D图标
    When 创建名称为"<dirName>"的ImageClass目录
    Then 系统中存在名称"<dirName>"的ImageClass目录
    When 给"<dirName>"目录添加"<addImage>"图片
    Then 这个"<dirName>"目录"<addImage>"图片添加成功
    When 给图片"<addImage>"添加"Tarsier DCV-3D"的"Cluster"的3D图标
    Then 成功给图片"<addImage>"添加"Tarsier DCV-3D"的"Cluster"的3D图标
    When 删除名称为"<addImage>"的图片
    Then 系统中不存在名称为"<addImage>"的图片
    When 删除名称为"<dirName>"的ImageClass目录
    Then 系统中不存在名称为"<dirName>"的ImageClass目录

    Examples: 
      | dirName     | addImage                                        |
      | 中ing122266  | 1122uu                                          |
      | UUUU&$$$666 | UUUUUYYYYYYYYYYYYYYYYYYYYYYOOOOOOOOOOOOOOOOO%#@ |

  @Smoke@Debug
  Scenario Outline: Image_图片挂载DMV3D图标
    When 创建名称为"<dirName>"的ImageClass目录
    Then 系统中存在名称"<dirName>"的ImageClass目录
    When 给"<dirName>"目录添加"<addImage>"图片
    Then 这个"<dirName>"目录"<addImage>"图片添加成功
    When 给图片"<addImage>"添加"Tarsier DMV-3D"的"Application"的3D图标
    Then 成功给图片"<addImage>"添加"Tarsier DMV-3D"的"Application"的3D图标
    When 删除名称为"<addImage>"的图片
    Then 系统中不存在名称为"<addImage>"的图片
    When 删除名称为"<dirName>"的ImageClass目录
    Then 系统中不存在名称为"<dirName>"的ImageClass目录

    Examples: 
      | dirName | addImage                                        |
      | 挂载3D图标  | 1122uu                                          |
      | 挂载图标    | UUUUUYYYYYYYYYYYYYYYYYYYYYYOOOOOOOOOOOOOOOOO%#@ |

	@Smoke
    Scenario: 可视化建模无法搜索到3D图标
    When 搜索名称为"AP"的3D图标
 	Then 返回结果不包含如下目录的图标:
 	|imageDir|
 	|Tarsier DCV-3D|
 	|Tarsier DMV-3D|
 	Then 返回结果包含"Tarsier|Application"
