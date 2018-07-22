@DMV
@delDiagram
Feature: DMV_绘图_新建视图_视图评论_Comment
	Background: 
		When 新建视图"测试评论视图",描述为"",文件夹为""
		Then 成功新建视图"测试评论视图"
		
    @Smoke
    Scenario Outline: Comment_评论视图
		When 给名称为"<diagramName>"的视图新建评论"<commentDesc>"
		Then 成功给名称为"<diagramName>"的视图新建评论
		When 查询名称为"<diagramName>"的视图评论
		Then 成功查询名称为"<diagramName>"的视图评论
		When 删除视图名称为"<diagramName>"的评论
		Then 成功删除视图名称为"<diagramName>"的评论

    Examples:
	 | common    | diagramName|diagramDesc|dirName|commentDesc|
     |评论测试    |测试评论视图|||新建评论| 
     
    @Smoke @Debug
    Scenario Outline: Comment_增加评论
		When 给名称为"<diagramName>"的视图新建评论"<commentDesc>"
		Then 成功给名称为"<diagramName>"的视图新建评论
		When 删除视图名称为"<diagramName>"的评论
		Then 成功删除视图名称为"<diagramName>"的评论
		
	Examples:
	 |common   | diagramName|commentDesc|
     |中文    |测试评论视图| 新增加评论|
     |字母数字    |测试评论视图|abc2222|
     |纯数字   |测试评论视图| 1234567890|  
     |特殊字符    |测试评论视图| @#￥%……&*（）——|
     |评论最大长度汉字300|测试评论视图|评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论|
        
     