@DMV
@Smoke
@delDiagram
Feature: DMV_绘图_模板绘图
    @Debug
   Scenario Outline: Template_模板创建视图
       When 新建视图"测试视图qqYYTT",描述为"描述3eerr",文件夹为"我的"
	   Then 成功新建视图"测试视图qqYYTT"
	   When 给视图"测试视图qqYYTT"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图qqYYTT"增加CI"信用卡决策1"坐标为"400""600"
       When 将视图"测试视图qqYYTT"转化为模板
       Then 视图"测试视图qqYYTT"成功转化为模板
	   When 利用模板"测试视图qqYYTT"创建名称为"<name>",视图描述为"<diagramDesc>"的视图
	   Then 系统中存在名称为"<name>",视图描述为"<diagramDesc>"的视图
	   When 给视图"<name>"增加CI"微信银行1"坐标为"400""600"
	   Then 成功给视图"<name>"增加CI"微信银行1"坐标为"400""600"
	   When 删除名称为"<name>"的视图
	   Then 成功删除名称为"<name>"的视图
	   When 删除名称为"测试视图qqYYTT"的视图
	   Then 成功删除名称为"测试视图qqYYTT"的视图

    Examples:
	 | common    | name        |diagramDesc|
     | 中英文数    |测试视图er342| 视图描述     |
     |特殊字符     |视图名%￥#@！|视图描述32werf&^%$#|
     |最大长度    |视图名3e视图名视图名3e视图名ytrrewoi|视图描述|
     |视图名称最大长度汉字50|视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图名视图|视图描述|
     |视图描述最大长度汉字100|视图名称|视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述|
     