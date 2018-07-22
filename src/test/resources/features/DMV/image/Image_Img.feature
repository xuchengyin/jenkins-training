@DMV
@delDiagram
Feature: DMV_绘图_新建视图_图标画图
	
	Scenario Outline: Image_给画布增加、删除一个常用图标
	    When 新建视图"<diagramName>",描述为"",文件夹为""
		Then 成功新建视图"<diagramName>"
		When 给视图"<diagramName>"增加常用图标"DB"坐标为"<x>""<y>"
		Then 成功给视图"<diagramName>"增加常用图标"DB"坐标为"<x>""<y>"
		When 给视图"<diagramName>"删除常用图标"DB"坐标为"<x>""<y>"
		Then 成功给视图"<diagramName>"删除常用图标"DB"坐标为"<x>""<y>"
	
	Examples:
		 | common   | diagramName|x|y|
    	 |增加图标视图|图标视图|200|300|
    	 
	
	Scenario Outline: Image_图标右键创建、解除CI #这里视图名和坐标必须和Background保持一致
		When 新建视图"<diagramName>",描述为"",文件夹为""
		Then 成功新建视图"<diagramName>"
		When 给视图"<diagramName>"增加常用图标"DB"坐标为"<x>""<y>"
		Then 成功给视图"<diagramName>"增加常用图标"DB"坐标为"<x>""<y>"
		When 给视图"<diagramName>"中常用图标"DB"坐标为"<x>""<y>"创建CI
		Then 成功给视图"<diagramName>"中常用图标"DB"坐标为"<x>""<y>"创建CI
		When 给视图"<diagramName>"中常用图标"DB"坐标为"<x>""<y>"解除CI
		Then 成功给视图"<diagramName>"中常用图标"DB"坐标为"<x>""<y>"解除CI
	
	Examples:
		 | common   | diagramName|x|y|
    	 |增加图标视图|图标视图|200|300|

    
	
    	 
    	 
      	 
    	 
 




		
