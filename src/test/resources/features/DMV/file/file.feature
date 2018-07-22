@DMV
@delFileName
Feature: DMV_文件
    @Debug@delFormDir
    Scenario Outline: DMV_文件_文件导入,文件查看,文件下载,文件删除
        When 在表格中创建名称为"文件夹112"的文件夹
	    Then 在表格中名称为"文件夹112"的文件夹创建成功
    	When 在文件夹"文件夹112"内导入"<fileName>"文件
    	Then 成功导入"<fileName>"的"<sheetName>"文件
    	When 查看"<fileName>"表格
        Then 成功查看"<fileName>"表格		
		#When 下载"<fileName>"文件
		#Then 成功下载"<fileName>"文件
		When 删除"<fileName>"文件
		Then 成功删除"<fileName>"文件
        When 在表格中删除文件夹"文件夹112"
        Then 在表格中成功删除文件夹"文件夹112"
     Examples:
       |commons|fileName|sheetName|
	   |单个sheet| CI-单个sheet.xls|刀片|
	   |多个sheet| CI-多个sheet.xls|放样物体集合,VM,测试分类1122|
	   |mp4|菜单顺序.mp4||
	   |docx|用户故事地图.docx||
	   |pptx|AutomationTraining_0427.pptx||
	   |pdf|document.pdf||
	   |exe|TeamViewer_13.1.3629.0.exe||
	   
	 @delFormDir
	 Scenario Outline: DMV_file_文件搜索
	   When 在表格中创建名称为"文件夹113"的文件夹
	   Then 在表格中名称为"文件夹113"的文件夹创建成功
	   Given 向文件夹"文件夹113"导入多个"CI-单个sheet.xls,CI-多个sheet.xls,CI-%$#@.xls,菜单顺序.mp4,用户故事地图.docx"表格
	   When 在文件夹"文件夹113"中搜索名称包含"<searchKey>"的表格
       Then 在文件夹"文件夹113"中包含"<searchKey>"关键字的的表格全部搜索出来
       When 删除"CI-单个sheet.xls,CI-多个sheet.xls,CI-%$#@.xls,菜单顺序.mp4,用户故事地图.docx"多个表格
       Then 成功删除"CI-单个sheet.xls,CI-多个sheet.xls,CI-%$#@.xls,菜单顺序.mp4,用户故事地图.docx"多个表格
       When 在表格中删除文件夹"文件夹113"
       Then 在表格中成功删除文件夹"文件夹113"
    Examples:
       |commons|searchKey|
	   |全匹配| CI-单个sheet|
	   |部分匹配|sheet|
	   |匹配符| %$#@|
	   |部分匹配|用户故事|
	   |部分匹配|菜单| 
	   
	   