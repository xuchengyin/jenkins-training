@BASE
Feature: BASE_对象管理_CiClass

  @Smoke
  @CleanCiCls
  Scenario Outline: ciClass_增删改查
    When 在"<dirName>"目录下,创建名称为"<ciClassName>"的ci分类,使用图标为"<imageName>"
    Then 系统中存在名称为"<ciClassName>"的ci分类
    And "<ciClassName>"的ci分类在"<dirName>"目录下,使用图标为"<imageName>"
    When 修改"<ciClassName>"分类的名称为"<ciClassNameModify>"
    Then 更新成功,"<ciClassName>"分类名称更新为"<ciClassNameModify>"
    When 复制"<ciClassNameModify>"分类,名称修改为"<copyCiClassName>"
    Then "<ciClassNameModify>"分类成功复制为"<copyCiClassName>"
    When 删除"<ciClassNameModify>"分类及数据
    Then 名称为"<ciClassNameModify>"的分类删除成功
    When 删除"<copyCiClassName>"分类及数据
    Then 名称为"<copyCiClassName>"的分类删除成功

    Examples: 字符类型校验
      | commons         | ciClassName                                 | dirName   | imageName | ciClassNameModify               | copyCiClassName                 |  |
      | 类名_正常             | testCi34567                                 | 业务领域    | Default   | testCi345678                    | testCi345671                    |  |
      | 类名_特殊字符      | @&_-                                        | 业务领域    | Default   | """="中                          | @&_-1                           |  |
      | 类名_最大长度31汉字  | 新建分类名称新建分类名称新建分类名称新建分类名称新建分类名称类| 业务领域    | Default   | @&_-1234567阿斯蒂芬他各环节阿萨德覆盖号wesdrf | 玩儿体育我我还看见和2能够部分vdcsxadertyn2341 |  |


  @Smoke
  @CleanCiCls
  Scenario: ciClass属性_添加不同类型的属性、并设置label、更改主键
    When 在"业务领域"目录下,创建名称为"ciTest"的ci分类,使用图标为"Default"
    Then 系统中存在名称为"ciTest"的ci分类
    When 给"ciTest"分类添加如下属性:
      | 属性名称    | 属性类型                                | 必填 | Lable | 枚举值           | CI Code | 默认值                    |
      | 编号           | 整数                                       |  1  |     0 |    ""       |       0 |         12345678 |
      | 重量           | 小数                                       |  0  |     0 |    ""       |       0 | 234.000000000000 |
      | 描述           | 文本                                       |  1  |     1 |    ""       |       0 | werdfghnbvc      |
      | 备注           | 文章                                       |  0  |     0 |    ""       |       0 | @&()__——（）         |
      | 月份           | 枚举                                       |  0  |     1 | ["月","日"] |       0 |                  |
      | 时间           | 日期                                       |  0  |     1 | ""          |       0 | 1990-10-90       |
      | 名称           | 字符串                                    |  1  |     1 | ""          |       1 |                  |
      | 字典_type | 数据字典_type_Application |  1  |     1 | ""          |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0  |     1 | ""          |       0 |                  |
    Then "ciTest"分类属性更新成功
      | 属性名称    | 属性类型                                | 必填 | Lable | 枚举值           | CI Code | 默认值                    |
      | 编号           | 整数                                       |  1  |     0 |    ""       |       0 |         12345678 |
      | 重量           | 小数                                       |  0  |     0 |    ""       |       0 | 234.000000000000 |
      | 描述           | 文本                                       |  1  |     1 |    ""       |       0 | werdfghnbvc      |
      | 备注           | 文章                                       |  0  |     0 |    ""       |       0 | @&()__——（）         |
      | 月份           | 枚举                                       |  0  |     1 | ["月","日"] |       0 |                  |
      | 时间           | 日期                                       |  0  |     1 | ""          |       0 | 1990-10-90       |
      | 名称           | 字符串                                    |  1  |     1 | ""          |       1 |                  |
      | 字典_type | 数据字典_type_Application |  1  |     1 | ""          |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0  |     1 | ""          |       0 |                  |

	  When 复制"ciTest"分类,名称修改为"ciTest1"
	  Then "ciTest"分类成功复制为"ciTest1"
	  When 删除"ciTest"分类及数据
	  Then 系统中不存在名称为"ciTest"的分类
	  When 删除"ciTest1"分类及数据
	  Then 系统中不存在名称为"ciTest1"的分类

  @Smoke
  @CleanCiCls
  Scenario: ciClass属性_添加不同类型的属性、修改、删除属性（名称 必填 lable 枚举值 cicode 默认值， 顺序， 属性类型的修改放在ci中结合ci进行）
    When 在"业务领域"目录下,创建名称为"ciTest"的ci分类,使用图标为"Default"
    Then 系统中存在名称为"ciTest"的ci分类
    When 给"ciTest"分类添加如下属性:
      | 属性名称    | 属性类型                                | 必填 | Lable | 枚举值           | CI Code | 默认值                    |
      | 编号           | 整数                                       |  1  |     0 |    ""       |       0 |         12345678 |
      | 重量           | 小数                                       |  0  |     0 |    ""       |       0 | 234.000000000000 |
      | 描述           | 文本                                       |  1  |     1 |    ""       |       0 | werdfghnbvc      |
      | 备注           | 文章                                       |  0  |     0 |    ""       |       0 | @&()__——（）         |
      | 月份           | 枚举                                       |  0  |     1 | ["月","日"] |       0 |                  |
      | 时间           | 日期                                       |  0  |     1 | ""          |       0 | 1990-10-90       |
      | 名称           | 字符串                                    |  1  |     1 | ""          |       1 |                  |
      | 字典_type | 数据字典_type_Application |  1  |     1 | ""          |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0  |     1 | ""          |       0 |                  |
    Then "ciTest"分类属性更新成功
      | 属性名称    | 属性类型                                | 必填 | Lable | 枚举值           | CI Code | 默认值                    |
      | 编号           | 整数                                       |  1  |     0 |    ""       |       0 |         12345678 |
      | 重量           | 小数                                       |  0  |     0 |    ""       |       0 | 234.000000000000 |
      | 描述           | 文本                                       |  1  |     1 |    ""       |       0 | werdfghnbvc      |
      | 备注           | 文章                                       |  0  |     0 |    ""       |       0 | @&()__——（）         |
      | 月份           | 枚举                                       |  0  |     1 | ["月","日"] |       0 |                  |
      | 时间           | 日期                                       |  0  |     1 | ""          |       0 | 1990-10-90       |
      | 名称           | 字符串                                    |  1  |     1 | ""          |       1 |                  |
      | 字典_type | 数据字典_type_Application |  1  |     1 | ""          |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0  |     1 | ""          |       0 |                  |
      
    #When 给"ciTest"分类修改属性如下:
      #| 属性名称    |属性新名称         | 属性类型                                | 必填 | Lable | 枚举值           | CI Code | 默认值                    |orderNo|
      #| 编号           | 编号修改           | 整数                                       |  1  |     1 |    ""       |       1 |         12345678 |1      |
      #| 重量           | 重量修改           | 小数                                       |  1  |     1 |    ""       |       0 | 234.0000 |0      |
      #| 描述           | 描述修改           | 文本                                       |  0  |     1 |    ""       |       0 | werdfghnbvc      |2      |
      #| 备注           | 备注修改           | 文章                                       |  0  |     0 |    ""       |       0 | @&()__——（）         |3      |
      #| 月份           | 月份修改           |枚举                                         |  0  |     0 | ["月","日"] |       0 |                  |4      |
      #| 时间           | 时间修改           | 日期                                       |  0  |     0 | ""          |       0 | 1990-10-90       |5      |
      #| 名称           | 名称修改           |字符串                                     |  0  |     0 | ""          |       0 |                  |7      |
      #| 字典_type | 字典_type修改  | 数据字典_type_Application |  0  |     0 | ""          |       0 |                  |6      |
      #| 字典_tag  | 字典_tag修改    |数据字典_tag_APP           |  0  |     0 | ""          |       0 |                  |8      |
    #Then 给"ciTest"分类修改属性成功
      #| 属性名称    |属性新名称         | 属性类型                                | 必填 | Lable | 枚举值           | CI Code | 默认值                    |orderNo|
      #| 编号           | 编号修改           | 整数                                       |  1  |     1 |    ""       |       1 |         12345678 |1      |
      #| 重量           | 重量修改           | 小数                                       |  1  |     1 |    ""       |       0 | 234.0000 |0      |
      #| 描述           | 描述修改           | 文本                                       |  0  |     1 |    ""       |       0 | werdfghnbvc      |2      |
      #| 备注           | 备注修改           | 文章                                       |  0  |     0 |    ""       |       0 | @&()__——（）         |3      |
      #| 月份           | 月份修改           |枚举                                         |  0  |     0 | ["月","日"] |       0 |                  |4      |
      #| 时间           | 时间修改           | 日期                                       |  0  |     0 | ""          |       0 | 1990-10-90       |5      |
      #| 名称           | 名称修改           |字符串                                     |  0  |     0 | ""          |       0 |                  |7      |
      #| 字典_type | 字典_type修改  | 数据字典_type_Application |  0  |     0 | ""          |       0 |                  |6      |
      #| 字典_tag  | 字典_tag修改    |数据字典_tag_APP           |  0  |     0 | ""          |       0 |                  |8      |
	  
	  When 删除"ciTest"分类及数据
	  Then 系统中不存在名称为"ciTest"的分类
	 
	  
	  
 	@CleanCiCls
    Scenario: ciClass属性_属性名最大长度
   	 When 在"业务领域"目录下,创建名称为"ciTest"的ci分类,使用图标为"Default"
  	 Then 系统中存在名称为"ciTest"的ci分类
  	 When 给"ciTest"分类添加如下属性:
       | 属性名称                                          | 属性类型 | 必填  | Lable | 枚举值 | CI Code | 默认值 |
   	   | ciCode_最大长度31及特殊字符.txt | 字符串    |   1  |     1 | ""    |       1 |       |
  	 Then "ciTest"分类属性更新成功
       | 属性名称                                          | 属性类型 | 必填 | Lable | 枚举值 | CI Code | 默认值 |
   	   | ciCode_最大长度31及特殊字符.txt | 字符串    |  1  |     1 | ""     |       1 |       | 
  	 When 删除"ciTest"分类及数据
   	 Then 系统中不存在名称为"ciTest"的分类
   	  
   	  
  	@CleanCiCls
 	Scenario Outline: ciClass_导入、导出
    	When 在"业务领域"目录下,创建名称为"<className>"的ci分类,使用图标为"Default"
    	And 导入"<className>"分类属性
    	Then "<className>"分类属性导入成功
    	When 导出"<className>"分类
    	Then "<className>"分类导出成功
    	And 删除"<className>"分类及数据
    	Then 系统中不存在名称为"<className>"的分类
    Examples: 分类名称的最大长度(31)、特殊字符,属性的最大长度(50)、特殊字符
    	| className                                   |
      	| @工商银行&_机柜-                             |
      	| 玩儿体育我我还看见和2能够部分vdcsxadertyn2345 |

 	Scenario: ciClass_下载模板
    	When 下载"分类模板"
    	Then "分类模板"文件下载成功

 	Scenario: ciClass_导出全部
   	 	When 导出全部分类
    	Then 分类数据导出成功
     
  	@Smoke
  	Scenario: ciClass_查询所有分类及其属性
  		When 查询所有分类及其属性
  		Then 成功查询所有分类及其属性
  	@Debug
	Scenario: ciClass_验证可视化建模模板内容	
		When 用"可视化建模.tarsier"文件测试可视化建模模板上传
		Then 用以下fileName验证可视化建模模板内容:
		|序号|文件名|
		|1|VisualizedModelingDiagram/CI-ClassificationInformation.xls|
		|2|VisualizedModelingDiagram/VisualizedModelingDiagram.xml|
		|3|VisualizedModelingDiagram/Relationship-ClassificationInformation.xls|
		|4|VisualizedModelingDiagram/CI-ClassificationRelationship.xls|
		|5|VisualizedModelingDiagram/CI-ClassificationCatalog.xls|
		|6|VisualizedModelingDiagram/CI-ClassificationIcons.xls|
		|7|VisualizedModelingDiagram/CI-ClassificationIcons.zip|