Feature: DCV_全局资源管理_productModel
#    Scenario Outline: 上传产品库
#		When 上传名称为"<fileName>"产品库文件
#		Then 成功上传名称为"<fileName>"产品库文件
#		When 上传名称为"<fileErrorName>"错误产品库文件
#		Then 上传名称为"<fileErrorName>"产品库文件失败					

#		Examples:
#		| 	fileName 		| fileErrorName |
#		| product_lib.zip 	| lct.zip 	|				

#	Scenario Outline: productModel_模型库管理_模型库
#		When 下载端口映射文件
#		Then 成功下载端口映射文件
#		When 上传名称为"<productPort>"端口映射文件
#		Then 成功上传端口映射文件"<productPort>"
#		When 模型库页面搜索名称为"<modelName>"的模型
#		Then 显示搜索结果"<modelName>"
#		When 模型库页面点击下一页
#		Then 正确显示下一页内容
#		When 模型库页面跳至"<skipNum>"页
#		Then 显示跳转页"<skipNum>"的模型
		
#	    Examples: 
#		|  common   |   productPort        | common |modelName|  pageNum  |skipNum|
#		|  映射文件           |product_lib_port.json |搜索内容      |ABB_进线开关柜|    2      |1      |
		
#	Scenario Outline: productModel_模型库管理_模型映射
#		When 下载模型映射文件
#		Then 成功下载模型映射文件
#		When 上传名称为"<productMapping>"模型映射文件
#		Then 成功上传模型映射文件"<productMapping>"
#		When 模型映射页面搜索名称为"<productName>"的模型
#		Then 模型映射界面搜索"<productName>"结果正确
#		When 模型映射界面点击下一页
#		Then 显示下一页模型映射内容
#		When 模型映射页面跳至"<pageNum>"页
#		Then 显示跳转页"<pageNum>"模型映射内容
#		Examples:
#		|common|    productMapping |common |productName |pageNum|
#		|映射文件   | modelMapping.json |搜索内容   |H3C_SR6608|   1   |  