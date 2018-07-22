@BASE
@cleanRltCls
Feature:BASE_一键导入_导入关系

    @Smoke @cleanRltCls @deleteImportClass
	Scenario:导入关系_无关系属性
		Given 系统中已存在如下分类及数据:"Router,Firewall"
		When 创建名称为"导入关系测试_无属性"的关系分类，其动态效果为"1",关系样式为"solid",关系宽度为"1",关系箭头为"classic",显示类型为"1",属性定义如下的:
     	 | 属性名                    | 属性类型 | 枚举值 |
   		 | 端口                    |    1 |   |
		When 上传关系导入所需excel表:"一键导入关系_映射多sheet.xls"
		Then 成功获取如下excel表中各sheet关系数据:
		  |commons|excelName|sheetName|
	 	  |关系映射_映射多sheet|一键导入关系_映射多sheet|无属性映射|
		When 按照如下设置进行关系映射,并上传:
		  |commons|excelName|sheetName|excelSourceCICode|excelSourceCICodeIndex|excelDistCICode|excelDistCICodeIndex|rltName|excelAttr|excelAttrIndex|rltAttrName|
		  |关系映射_映射多sheet_添加属性(无)|一键导入关系_映射多sheet|无属性映射|CI Code|2|CI Code|4|导入关系测试_无属性||||
	    Then 获取"关系数据"上传报告:
	    #全部导入,igroneType 0:全部导入;1:忽略数量未超过100;2:忽略数量超过100
	    #无属性映射 14  有属性映射 14  sheetName是导入的分类名,不是excel的sheet名
	    |igroneType|errMessagesFieldsArray|ignoreNum|igroneDatasArray|fileName|updateNum|sheetName|insertNum|titles|
	    |0|null|0|null|一键导入关系_映射多sheet.xls|0|导入关系测试_无属性|14|:源ci_code:目标ci_code|
	    #|1|null|0|null|一键导入关系_映射多sheet.xls|0|有属性映射|14|titles|
	    
	    And 如下关系数据导入成功:
	 	  |commons|excelName|sheetName|rltName|
	 	  |关系映射_映射多sheet|一键导入关系_映射多sheet|无属性映射|导入关系测试_无属性|
	   # When 删除名称为"导入关系测试_无属性"的关系分类及数据
       # Then 关系分类"导入关系测试_无属性"分类及数据删除成功
       # When 删除"Router"分类及数据
       # Then 名称为"Router"的分类删除成功
       # When 删除"Firewall"分类及数据
       # Then 名称为"Firewall"的分类删除成功

    @Smoke @cleanRltCls @deleteImportClass
    Scenario:导入关系_有关系属性
		Given 系统中已存在如下分类及数据:"Switch,Firewall"
		When 创建名称为"导入关系测试_新增属性"的关系分类，其动态效果为"1",关系样式为"solid",关系宽度为"1",关系箭头为"classic",显示类型为"1",属性定义如下的:
         | 属性名                    | 属性类型 | 枚举值 |
         | 端口                    |    1 |   |
		When 上传关系导入所需excel表:"一键导入关系_映射多sheet.xls"
		Then 成功获取如下excel表中各sheet关系数据:
			|commons|excelName|sheetName|
	 		|关系映射_映射多sheet|一键导入关系_映射多sheet|有属性映射|
		When 按照如下设置进行关系映射,并上传:
			|commons|excelName|sheetName|excelSourceCICode|excelSourceCICodeIndex|excelDistCICode|excelDistCICodeIndex|rltName|excelAttr|excelAttrIndex|rltAttrName|
			|关系映射_映射多sheet_添加属性(新增)|一键导入关系_映射多sheet|有属性映射|CI Code|2|CI Code|6|导入关系测试_新增属性|源分类|0|新增属性|
			|关系映射_映射多sheet_添加属性(已有)|一键导入关系_映射多sheet|有属性映射|CI Code|2|CI Code|6|导入关系测试_新增属性|当前状态|1|端口|
	    Then 获取"关系数据"上传报告:
	    #全部导入,igroneType 1:忽略数量未超过100;2:忽略数量超过100
	    #无属性映射 14  有属性映射 14
	    |igroneType|errMessagesFieldsArray|ignoreNum|igroneDatasArray|fileName|updateNum|sheetName|insertNum|titles|
	    #|1|null|0|null|一键导入关系_映射多sheet.xls|0|导入关系测试_新增属性|14|:源ci_code:目标ci_code|
	    |0|null|0|null|一键导入关系_映射多sheet.xls|0|导入关系测试_新增属性|14|:源ci_code:目标ci_code|
	    
	    And 如下关系数据导入成功:
	 		|commons|excelName|sheetName|rltName|
	 		|关系映射_映射多sheet|一键导入关系_映射多sheet|有属性映射|导入关系测试_新增属性|
	    #When 删除名称为"导入关系测试_新增属性"的关系分类及数据
        #Then 关系分类"导入关系测试_新增属性"分类及数据删除成功
        #When 删除"Switch"分类及数据
        #Then 名称为"Switch"的分类删除成功
        #When 删除"Firewall"分类及数据
        #Then 名称为"Firewall"的分类删除成功
	 
	 
	 
    @Smoke @cleanRltCls @deleteImportClass
	Scenario:导入关系_多sheet映射
		Given 系统中已存在如下分类及数据:"Firewall"
		When 创建名称为"导入关系测试_属性类型"的关系分类，其动态效果为"1",关系样式为"solid",关系宽度为"1",关系箭头为"classic",显示类型为"1",属性定义如下的:
   		   | 属性名                    | 属性类型 | 枚举值 |
   		   | 端口                    |    1 |  |
   		   | 小数                     |    2 |    |
   		   | 文本                     |    4 |    |
   		   | 枚举                     |    6 |  ["有代理","无代理"]|
   		   | 日期                     |    7 |   |
		When 上传关系导入所需excel表:"一键导入关系_映射多属性.xls"
		Then 成功获取如下excel表中各sheet关系数据:
			|commons|excelName|sheetName|
	 		|关系映射_映射多属性_已有属性_日期|一键导入关系_映射多属性|映射多属性|
		When 按照如下设置进行关系映射,并上传:
			|commons|excelName|sheetName|excelSourceCICode|excelSourceCICodeIndex|excelDistCICode|excelDistCICodeIndex|rltName|excelAttr|excelAttrIndex|rltAttrName|
			|关系映射_映射多属性_新属性_字符串|一键导入关系_映射多属性|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|线型|5|线型|
			|关系映射_映射多属性_已有属性_整数|一键导入关系_映射多属性|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|端口|6|端口|
			|关系映射_映射多属性_已有属性_小数|一键导入关系_映射多属性|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|IP缩写|8|小数|
			|关系映射_映射多属性_已有属性_文本|一键导入关系_映射多属性|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|目标分类|2|文本|
			|关系映射_映射多属性_已有属性_枚举|一键导入关系_映射多属性|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|代理类型|4|枚举|
			|关系映射_映射多属性_已有属性_日期|一键导入关系_映射多属性|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|日期|7|日期|
	 	Then 获取"关系数据"上传报告:
	    #全部导入,igroneType 0:全部导入1:忽略数量未超过100;2:忽略数量超过100
	    #无属性映射 14  有属性映射 14
	    |igroneType|errMessagesFieldsArray|ignoreNum|igroneDatasArray|fileName|updateNum|sheetName|insertNum|titles|
	    |0|null|0|null|一键导入关系_映射多属性.xls|0|导入关系测试_属性类型|31|:源ci_code:目标ci_code|
	    #|1|null|0|null|一键导入关系_映射多sheet.xls|0|导入关系测试_属性类型|14|:源ci_code:目标ci_code|
	    
		 And 如下关系数据导入成功:
	 		|commons|excelName|sheetName|rltName|
	 		|关系映射_映射多属性_已有属性_日期|一键导入关系_映射多属性|映射多属性|导入关系测试_属性类型|
		#When 删除名称为"导入关系测试_属性类型"的关系分类及数据
   		#Then 关系分类"导入关系测试_属性类型"分类及数据删除成功
   		#When 删除"Firewall"分类及数据
   		#Then 名称为"Firewall"的分类删除成功
	 		
	 		
#由于再次导入现在不能用，先这样，以后修改，这个先当作导入失败来验证	 		
	 @Debug @cleanRltCls @deleteImportClass
	 Scenario:导入关系_再次导入被忽略的关系数据
		Given 系统中已存在如下分类及数据:"Firewall"
		When 创建名称为"导入关系测试_属性类型"的关系分类，其动态效果为"1",关系样式为"solid",关系宽度为"1",关系箭头为"classic",显示类型为"1",属性定义如下的:
   		   | 属性名                    | 属性类型 | 枚举值 |
   		   | 端口                    |    1 |  |
   		   | 小数                     |    2 |    |
   		   | 文本                     |    4 |    |
   		   | 枚举                     |    6 |  ["有代理","无代理"]|
   		   | 日期                     |    7 |   |
		When 上传关系导入所需excel表:"一键导入关系_映射多属性导入有失败.xls"
		Then 成功获取如下excel表中各sheet关系数据:
			|commons|excelName|sheetName|
	 		|关系映射_映射多属性_已有属性_日期|一键导入关系_映射多属性导入有失败|映射多属性|
		When 按照如下设置进行关系映射,并上传:
			|commons|excelName|sheetName|excelSourceCICode|excelSourceCICodeIndex|excelDistCICode|excelDistCICodeIndex|rltName|excelAttr|excelAttrIndex|rltAttrName|
			|关系映射_映射多属性_新属性_字符串|一键导入关系_映射多属性导入有失败|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|线型|5|线型|
			|关系映射_映射多属性_已有属性_整数|一键导入关系_映射多属性导入有失败|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|端口|6|端口|
			|关系映射_映射多属性_已有属性_小数|一键导入关系_映射多属性导入有失败|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|IP缩写|8|小数|
			|关系映射_映射多属性_已有属性_文本|一键导入关系_映射多属性导入有失败|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|目标分类|2|文本|
			|关系映射_映射多属性_已有属性_枚举|一键导入关系_映射多属性导入有失败|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|代理类型|4|枚举|
			|关系映射_映射多属性_已有属性_日期|一键导入关系_映射多属性导入有失败|映射多属性|源对象|1|目标对象|3|导入关系测试_属性类型|日期|7|日期|
	 	Then 获取"关系数据"上传报告:
	    #全部导入,igroneType 0:全部导入1:忽略数量未超过100;2:忽略数量超过100
	    #验证数量是否正确
	    |igroneType|errMessagesFieldsArray|ignoreNum|igroneDatasArray|fileName|updateNum|sheetName|insertNum|titles|
	    |1|3|3|3|一键导入关系_映射多属性导入有失败.xls|0|导入关系测试_属性类型|28|:源ci_code:目标ci_code|
	    Then 用上一步上传返回的fileName下载错误信息
	    #慎重使用...
	    Then 验证返回信息是否正确:
	    |errMessagesFieldsArray|igroneDatasArray|igroneNum|
	    |源ci_code|-属性 : 源ci_code , 不存在;源ci_code-2333;目标ci_code-Firewall_29;端口-1031|3|
	    |目标ci_code|-属性 : 目标ci_code , 不存在;源ci_code-非金融交换1;目标ci_code-33333;端口-1031|3|
	    |源ci_code:目标ci_code|-属性 : 目标ci_code , 不存在;源ci_code-2220;目标ci_code-22220;端口-1031|3|
	    
	    #这个只要找到双方的ci_Code就会成功,这个功能无法解决不是ci_Code报错引起的忽略,开发说后续会优化
	    When 用以下数据再次导入被忽略的关系数据成功:
	 	|关系名称|源ciCode|目标ciCode|
	 	|导入关系测试_属性类型|自助银行1|Firewall_29|	
	    |导入关系测试_属性类型|非金融交换1|Firewall_30|	
	    |导入关系测试_属性类型|黄金交易1|Firewall_31|	
	    
		#When 删除名称为"导入关系测试_属性类型"的关系分类及数据
   		#Then 关系分类"导入关系测试_属性类型"分类及数据删除成功
   		#When 删除"Firewall"分类及数据
   		#Then 名称为"Firewall"的分类删除成功
   		