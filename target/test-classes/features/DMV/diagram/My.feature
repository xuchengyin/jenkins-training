@DMV
@delDiagram
@delDiagramDir
Feature: DMV_我的_My
    @Smoke
    Scenario Outline: My_新建文件夹,移动视图,重命名文件夹,删除文件夹
		When 创建名称为"<dirName>"的文件夹
		Then 名称为"<dirName>"的文件夹创建成功
		And  再次创建名称为"<dirName>"的文件夹失败,kw="<kw>"
		When 文件夹名称为"<dirName>"重命名为"<updateDirName>"
		Then 文件夹名称成功重命名为"<updateDirName>"
		When 新建视图"测试视图wwqq1122",描述为"描述3eerr",文件夹为"我的"
		Then 成功新建视图"测试视图wwqq1122"
	    When 给视图"测试视图wwqq1122"增加CI"信用卡决策1"坐标为"400""600"
	    Then 成功给视图"测试视图wwqq1122"增加CI"信用卡决策1"坐标为"400""600"
        When 将视图"测试视图wwqq1122"移动到文件夹"<updateDirName>"
        Then 视图"测试视图wwqq1122"成功移动到文件夹"<updateDirName>"
        When 删除名称为"测试视图wwqq1122"的视图
	    Then 成功删除名称为"测试视图wwqq1122"的视图
        When 删除文件夹"<updateDirName>"
        Then 成功删除文件夹"<updateDirName>"
    Examples:
	 | common | dirName  |updateDirName|kw|
     | 中英文数    |文件夹234www|重命名文件夹||
     |特殊字符     |文件夹%￥#@！|重命名文件夹123||
     |最大长度    |文件夹3er45wwwwer546ew文件夹3er45wwwwer546ew|重命名文件夹wqe||
     |最大长度汉字50|长度长度长度长度长度长度长度长度长度长度长度长度长度长度长度长度长度长度长度长度度长度长度长度度长度|重命名文件夹12||
    @Debug
    Scenario: My_移动文件夹
       When 创建名称为"test321"的文件夹
       Then 名称为"test321"的文件夹创建成功
       When 将文件夹"test321"移动到新建文件夹"test4rtt"
       Then 文件夹"test321"成功被移动到文件夹"test4rtt"
       When 删除文件夹"test321"
       Then 成功删除文件夹"test321"
       When 删除文件夹"test4rtt"
       Then 成功删除文件夹"test4rtt"
    
    Scenario Outline: My_文件夹内根据输入内容搜索视图
       When 创建名称为"testewq"的文件夹
       Then 名称为"testewq"的文件夹创建成功
       When 新建视图"测试视图1122",描述为"测试视图1122",文件夹为"我的"
	   Then 成功新建视图"测试视图1122"
	   When 给视图"测试视图1122"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图1122"增加CI"信用卡决策1"坐标为"400""600"
       When 将视图"测试视图1122"移动到文件夹"testewq"
       Then 视图"测试视图1122"成功移动到文件夹"testewq"
       When 新建视图"测试视图1133",描述为"测试视图1133",文件夹为"我的"
	   Then 成功新建视图"测试视图1133"
	   When 给视图"测试视图1133"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图1133"增加CI"信用卡决策1"坐标为"400""600"
       When 将视图"测试视图1133"移动到文件夹"testewq"
       Then 视图"测试视图1133"成功移动到文件夹"testewq"
       When 在文件夹"testewq"内搜索视图名称为"<searchKey>"的视图
       Then 在文件夹"testewq"内视图名称为"<searchKey>"的视图成功搜索出来
       When 删除多个视图"测试视图1122,测试视图1133"
	   Then 成功删除多个视图"测试视图1122,测试视图1133"
       When 删除文件夹"testewq"
       Then 成功删除文件夹"testewq"
     Examples:  
	 | common   |searchKey|
     |视图名全匹配      |测试视图1122|
     |视图匹配符          |测试视图|
     |视图名部分匹配  |视图11|
    
    Scenario: My_文件夹内搜索视图
       When 创建名称为"testewq12"的文件夹
       Then 名称为"testewq12"的文件夹创建成功
       When 新建视图"测试视图wwqq",描述为"测试视图1122",文件夹为"我的"
	   Then 成功新建视图"测试视图wwqq"
	   When 给视图"测试视图wwqq"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图wwqq"增加CI"信用卡决策1"坐标为"400""600"
       When 将视图"测试视图wwqq"移动到文件夹"testewq12"
       Then 视图"测试视图wwqq"成功移动到文件夹"testewq12"
       When 新建视图"测试视图wwee",描述为"测试视图ttyy",文件夹为"我的"
	   Then 成功新建视图"测试视图wwee"
	   When 给视图"测试视图wwee"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图wwee"增加CI"信用卡决策1"坐标为"400""600"
       When 将视图"测试视图wwee"移动到文件夹"testewq12"
       Then 视图"测试视图wwee"成功移动到文件夹"testewq12"     
       When 在文件夹"testewq12"内搜索视图"测试视图wwqq"
       Then 在文件夹"testewq12"内视图"测试视图wwqq"被成功搜索出来
       When 删除名称为"测试视图wwqq"的视图
	   Then 成功删除名称为"测试视图wwqq"的视图
	   When 删除名称为"测试视图wwee"的视图
	   Then 成功删除名称为"测试视图wwee"的视图
       When 删除文件夹"testewq12"
       Then 成功删除文件夹"testewq12"
       
   Scenario: My_文件夹内搜索文件夹
      When 创建名称为"testWeu56"的文件夹
      Then 名称为"testWeu56"的文件夹创建成功
      When 将文件夹"testWeu56"移动到新建文件夹"test4rtt"
      Then 文件夹"testWeu56"成功被移动到文件夹"test4rtt"
      When 在文件夹"test4rtt"内搜索文件夹"testWeu56"
      Then 在文件夹"test4rtt"内文件夹"testWeu56"被成功搜索出来
      When 删除文件夹"testWeu56"
      Then 成功删除文件夹"testWeu56"

   Scenario: My_文件夹内搜索组合视图
      When 创建名称为"testewq125432"的文件夹
      Then 名称为"testewq125432"的文件夹创建成功
      When 新建视图"测试视图wwqq",描述为"描述3eerr",文件夹为"我的"
      Then 成功新建视图"测试视图wwqq"
      When 给视图"测试视图wwqq"增加CI"信用卡决策1"坐标为"400""600"
	  Then 成功给视图"测试视图wwqq"增加CI"信用卡决策1"坐标为"400""600"      
      When 根据视图"测试视图wwqq"创建组合视图"组合视图1122"同时将组合视图移动到文件夹"testewq125432"
      When 在文件夹"testewq125432"内搜索组合视图
      Then 在文件夹"testewq125432"内组合视图被成功搜索出来
      When 删除组合视图"组合视图1122"
      Then 成功删除名称为"组合视图1122"的视图
      When 删除名称为"测试视图wwqq"的视图
	  Then 成功删除名称为"测试视图wwqq"的视图
      When 删除文件夹"testewq125432"
      Then 成功删除文件夹"testewq125432" 

    @Smoke
    Scenario Outline: My_回收站搜索视图，清除视图
       When 在回收站内清除视图"测试视图%#wwqq"
       Then 视图"测试视图%#wwqq"被成功清除
       When 新建视图"测试视图%#wwqq",描述为"描述3eerr",文件夹为"我的"
	   Then 成功新建视图"测试视图%#wwqq"
	   When 给视图"测试视图%#wwqq"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图%#wwqq"增加CI"信用卡决策1"坐标为"400""600" 
	   When 删除名称为"测试视图%#wwqq"的视图
	   Then 成功删除名称为"测试视图%#wwqq"的视图
       When 在回收站内搜索视图名称为"<searchKey>"的视图
       Then 回收站内视图名称为"<searchKey>"的视图成功搜索出来
       When 在回收站内清除视图"测试视图%#wwqq"
       Then 视图"测试视图%#wwqq"被成功清除
    
    Examples:
     | common   |searchKey|
     |视图名全匹配      |测试视图%#wwqq|
     |视图匹配符          |测试视图%#|
     |视图名部分匹配  |%#wwqq|

    @Smoke
    Scenario: My_回收站还原视图
       When 新建视图"测试视图qqYYY",描述为"描述3eerr",文件夹为"我的"
	   Then 成功新建视图"测试视图qqYYY"
	   When 给视图"测试视图qqYYY"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图qqYYY"增加CI"信用卡决策1"坐标为"400""600" 
	   When 删除名称为"测试视图qqYYY"的视图
	   Then 成功删除名称为"测试视图qqYYY"的视图
       When 在回收站内搜索视图名称为"测试视图qqYYY"的视图
       Then 回收站内视图名称为"测试视图qqYYY"的视图成功搜索出来
       When 将回收站的视图"测试视图qqYYY"还原
       Then 回收站的视图"测试视图qqYYY"成功被还原
       When 删除名称为"测试视图qqYYY"的视图
	   Then 成功删除名称为"测试视图qqYYY"的视图
	   
    @Smoke
    Scenario: My_视图重命名，删除视图
       When 新建视图"测试视图qqUUU",描述为"描述3eerr",文件夹为"我的"
       Then 成功新建视图"测试视图qqUUU"
       When 给视图"测试视图qqUUU"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图qqUUU"增加CI"信用卡决策1"坐标为"400""600" 
       When 给视图"测试视图qqUUU"重命名为"视图重命名3errr"
       Then 视图"测试视图qqUUU"重命名为"视图重命名3errr"成功
       When 删除名称为"视图重命名3errr"的视图
	   Then 成功删除名称为"视图重命名3errr"的视图

    @Smoke
    Scenario: My_克隆视图
       When 新建视图"测试视图qqUUU",描述为"描述3eerr",文件夹为"我的"
	   Then 成功新建视图"测试视图qqUUU"
	   When 给视图"测试视图qqUUU"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图qqUUU"增加CI"信用卡决策1"坐标为"400""600" 
       When 视图"测试视图qqUUU"克隆为"克隆视图1122"
       Then 视图名为"克隆视图1122"成功克隆
       When 删除名称为"测试视图qqUUU"的视图
	   Then 成功删除名称为"测试视图qqUUU"的视图
	   
    @Smoke
    Scenario: My_将视图转化为模板
       When 新建视图"测试视图qqYYTT",描述为"描述3eerr",文件夹为"我的"
	   Then 成功新建视图"测试视图qqYYTT"
	   When 给视图"测试视图qqYYTT"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图qqYYTT"增加CI"信用卡决策1"坐标为"400""600" 
       When 将视图"测试视图qqYYTT"转化为模板
       Then 视图"测试视图qqYYTT"成功转化为模板
       When 删除名称为"测试视图qqYYTT"的视图
	   Then 成功删除名称为"测试视图qqYYTT"的视图
    
    Scenario: My_查看视图版本
      When 新建视图"测试视图qqUUII",描述为"描述3eerr",文件夹为"我的" 
	  Then 成功新建视图"测试视图qqUUII"
	  When 给视图"测试视图qqUUII"增加CI"信用卡决策1"坐标为"400""600"
	  Then 成功给视图"测试视图qqUUII"增加CI"信用卡决策1"坐标为"400""600" 
      And  给视图"测试视图qqUUII"创建名称为"eewwww"版本号为"v1.1.0"的版本
      When 取得视图"测试视图qqUUII"版本
      Then 视图版本为"eewwww"版本号为"v1.1.0"正确取得
      When 删除名称为"测试视图qqUUII"的视图
	  Then 成功删除名称为"测试视图qqUUII"的视图
	  
#现在由前台导出不调后台了
#    Scenario: My_查看视图导出PNG文件
#      When 新建视图"测试视图qqUUIIeee",描述为"描述3eerr",文件夹为"我的" 
#	  Then 成功新建视图"测试视图qqUUIIeee"
#	  When 给视图"测试视图qqUUIIeee"增加CI"信用卡决策1"坐标为"400""600"
#	  Then 成功给视图"测试视图qqUUIIeee"增加CI"信用卡决策1"坐标为"400""600" 
#      When 查看视图"测试视图qqUUIIeee"导出PNG文件
#      Then 查看视图成功导出PNG文件
#      When 删除名称为"测试视图qqUUIIeee"的视图
#	  Then 成功删除名称为"测试视图qqUUIIeee"的视图
	  
   @Smoke
   Scenario: My_查看视图导出配置信息
	  When 新建视图"测试视图1122",描述为"描述3eerr",文件夹为"我的" 
	  Then 成功新建视图"测试视图1122"
	  When 给视图"测试视图1122"增加CI"ATMP1"坐标为"400""600"
	  Then 成功给视图"测试视图1122"增加CI"ATMP1"坐标为"400""600" 
	  When 查看CI为"ATMP1"的视图"测试视图1122"导出配置信息
	  Then 查看CI为"ATMP1"的视图成功导出配置信息
	  When 删除名称为"测试视图1122"的视图
	  Then 成功删除名称为"测试视图1122"的视图
	  
    @Smoke 
    Scenario: My_查看视图详情信息
       When 新建视图"测试视图ewrtyyu",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图ewrtyyu"
	   When 给视图"测试视图ewrtyyu"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图ewrtyyu"增加CI"信用卡决策1"坐标为"400""600" 
       When 创建多个小组,小组名称分别为"test小组1,test小组2,test小组3",小组描述为"查看小组视图描述"的小组
	   And  将视图"测试视图ewrtyyu"分享到多个小组
	   When 查看视图点击详情查看小组
	   Then 查看视图详情小组成功显示
	   When 删除名称为"测试视图ewrtyyu"的视图
	   Then 成功删除名称为"测试视图ewrtyyu"的视图
	   When 删除小组名称为"test小组1,test小组2,test小组3"的多个小组
	   Then 成功删除小组名称为"test小组1,test小组2,test小组3"的多个小组
	   
   @Smoke
   Scenario: My_查看视图搜索CI,关系
	   When 新建视图"测试视图1122ww22333",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图1122ww22333"
	  # When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"信用卡工单1",目标分类为"s@&_-",目标对象为"eight"关系数据
	   #Then "AppRlt"下存在只存在1条"信用卡工单1"与"eight"的关系数据
	   When 给视图"测试视图1122ww22333"增加CI"信用卡工单1"坐标为"400""500"
	   Then 成功给视图"测试视图1122ww22333"增加CI"信用卡工单1"坐标为"400""500"
	   When 给视图"测试视图1122ww22333"增加CI"SZ_FrontServer1"坐标为"400""500"
	   Then 成功给视图"测试视图1122ww22333"增加CI"SZ_FrontServer1"坐标为"400""500"
	   When 查看视图搜索CI名称为"信用卡工单1","SZ_FrontServer1",关系"AppRlt"
	   Then 查看视图成功搜索CI名称为"信用卡工单1","SZ_FrontServer1",关系"AppRlt"
	   When 删除名称为"测试视图1122ww22333"的视图
	   Then 成功删除名称为"测试视图1122ww22333"的视图
	   
   @Smoke
   Scenario: My_查看视图查询告警
	   When 新建视图"测试视图1122ww",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图1122ww"
	   When 给视图"测试视图1122ww"增加CI"信用卡工单1"坐标为"600""700"
	   Then 成功给视图"测试视图1122ww"增加CI"信用卡工单1"坐标为"600""700"
	   When 给事件源为"5",SourceEventID为"60",CI为"信用卡工单1"推送告警"响应时间",事件级别为"1",值为"Critical",状态为"1",主题为"业务响应时间达到 143ms，已超过设定的阈值 125ms！",时间为"2017-12-12 15:53:12"
	   When 查看ciCode为"信用卡工单1"的关系为"AppRlt"视图查询告警
	   Then 查看视图CI为"信用卡工单1"成功查询告警
	   When 删除名称为"测试视图1122ww"的视图
	   Then 成功删除名称为"测试视图1122ww"的视图
	  
   Scenario: My_查看视图查看配置
	   When 新建视图"测试视图1122ww",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图1122ww"
	   When 给视图"测试视图1122ww"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图1122ww"增加CI"信用卡决策1"坐标为"400""600" 
	   When 查看"配置"设置
	   Then 成功查看"配置"设置
	   When 删除名称为"测试视图1122ww"的视图
	   Then 成功删除名称为"测试视图1122ww"的视图
	 
	Scenario: My_查看视图查看标签类
	   When 新建视图"测试视图1122wwqqq",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图1122wwqqq"
	   When 给视图"测试视图1122wwqqq"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图1122wwqqq"增加CI"信用卡决策1"坐标为"400""600"
	   And  给视图"测试视图1122wwqqq"新建名称为"eerrrtt"的标签
	   When 查看视图"测试视图1122wwqqq"搜索标签
	   Then 查看视图"测试视图1122wwqqq"成功搜索标签
	   When 删除名称为"测试视图1122wwqqq"的视图
	   Then 成功删除名称为"测试视图1122wwqqq"的视图
	   
	Scenario: My_查看视图查询用户类
	   When 新建视图"测试视图",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图"
	   When 给视图"测试视图"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图"增加CI"信用卡决策1"坐标为"400""600"
	   When 查看视图"测试视图"查看用户
	   Then 查看视图"测试视图"成功查看用户
	   When 删除名称为"测试视图"的视图
	   Then 成功删除名称为"测试视图"的视图

    #Scenario: My_查看视图查询CI类
       #When 删除名称为"测试视图"的视图
	   #Then 成功删除名称为"测试视图"的视图
	   #When 新建视图"测试视图",描述为"描述3eerr",文件夹为"我的" 
	   #Then 成功新建视图"测试视图"
	   #And  为视图"测试视图"添加分类为"Application"的CI
	   #When 查看视图"测试视图"查询分类为"Application"的CI类
	   #Then 查看视图"测试视图"成功查询CI类
	   #When 删除名称为"测试视图"的视图
	   #Then 成功删除名称为"测试视图"的视图
  	
	Scenario: My_查看视图查询CI外部链接
	   When 新建视图"测试视图",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图"
	   And  为视图"测试视图"添加分类为"Application"的CI
	   When 创建链接名称为"链接123"，链接地址为"baidu.com"，窗口显示宽度为"123"，高度为"23"，分类名称为"Application"
	   When 查看视图CI外挂属性
	   Then 成功查看视图CI外挂属性,分类名称为"Application"
	   When 删除链接名称为"链接123"动态链接
	   Then 动态链接"链接123"成功删除	
	   When 删除名称为"测试视图"的视图
	   Then 成功删除名称为"测试视图"的视图
   @Smoke  
   @CleanCiCls
   Scenario: My_查看视图3D查询图标
	   When 新建视图"测试视图1122wwqqyyy",描述为"描述3eerr",文件夹为"我的" 
	   Then 成功新建视图"测试视图1122wwqqyyy"
	   And  为视图"测试视图1122wwqqyyy"添加分类为"Application"的CI
	   When 查看视图3d查看分类"Application"图标
	   Then 查看视图成功查询分类"Application"图标
	   When 删除名称为"测试视图1122wwqqyyy"的视图
	   Then 成功删除名称为"测试视图1122wwqqyyy"的视图    
	    
    @delDiagramComb
	Scenario Outline: My_收藏夹_搜索
	   When 创建多个视图名称为"视图1467,%￥#,视图333",视图描述为"新建视图"的视图
	   Then 系统中存在多个视图名称为"视图1467,%￥#,视图333",视图描述为"新建视图"的视图
	   When 给视图"视图1467"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"视图1467"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"%￥#"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"%￥#"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"视图333"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"视图333"增加CI"信用卡决策1"坐标为"400""600"
	   And 将多个视图"视图1467,%￥#,视图333"移动到收藏夹
	   When 在收藏夹中通过关键字"<searchKey>"来搜索视图
	   Then 在收藏夹中通过关键字"<searchKey>"成功搜索视图
	   When 删除多个视图"视图1467,%￥#,视图333"
	   Then 成功删除多个视图"视图1467,%￥#,视图333"
	 Examples:
	   | common   |searchKey|
       |视图名全匹配      |视图1456|
       |视图匹配符          |%￥#|
       |视图名部分匹配  |视图|

     @Smoke
     Scenario: My_清空回收站
       When 新建视图"测试视图%#wwqq",描述为"描述3eerr",文件夹为"我的"
	   Then 成功新建视图"测试视图%#wwqq"
	   When 给视图"测试视图%#wwqq"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试视图%#wwqq"增加CI"信用卡决策1"坐标为"400""600"
	   When 删除名称为"测试视图%#wwqq"的视图
	   Then 成功删除名称为"测试视图%#wwqq"的视图
       When 清空回收站
       Then 回收站被成功清空

     @Smoke
     Scenario Outline: My_查看视图追加视图描述
        When 新建视图"追加视图描述",描述为"视图描述",文件夹为"我的"
	    Then 成功新建视图"追加视图描述"
	    When 给视图"追加视图描述"追加视图描述"<diagramDesc>"
	    Then 成功给视图"追加视图描述"追加视图描述"<diagramDesc>"
     	When 删除名称为"追加视图描述"的视图
	    Then 成功删除名称为"追加视图描述"的视图
     Examples:
        | common   |diagramDesc|
        |中英文数字混合 |描述1456uitrue|
        |最大长度    |dffsde4567描述描述dddddddddddddttttttttttteerwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww描述|
        |特殊字符  |视图%$#@!(*)|
        |最大长度汉字100|视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述视图描述|
        
    @delCiRltRule
    @delDiagram    
    Scenario: My_查看视图CI相册查询配置信息
        Given 系统中已存在如下ci分类:"Application,s@&_-,Cluster"
	 	And   系统中已存在如下关系分类:"AppRlt"
	 	When 新建"Application"到"Cluster"关系为"AppRlt"的朋友圈规则"CI相册朋友圈规则"
	 	Then 成功新建"Application"到"Cluster"关系为"AppRlt"的朋友圈规则"CI相册朋友圈规则"
        When 新建视图"CI相册配置查询视图",描述为"CI相册视图描述",文件夹为"我的"
	    Then 成功新建视图"CI相册配置查询视图"
	    When 给视图"CI相册配置查询视图"增加CI"信用卡工单1"坐标为"400""500"
	    Then 成功给视图"CI相册配置查询视图"增加CI"信用卡工单1"坐标为"400""500"
	    When 给视图"CI相册配置查询视图"增加CI"SZ_FrontServer1"坐标为"400""500"
	    Then 成功给视图"CI相册配置查询视图"增加CI"SZ_FrontServer1"坐标为"400""500"
	    When 查询CI相册CI为"SZ_FrontServer1,信用卡工单1"的配置查询信息
	    Then 成功查询CI相册CI为"SZ_FrontServer1,信用卡工单1"的配置查询信息
	    When 删除名称为"CI相册配置查询视图"的视图
		Then 成功删除名称为"CI相册配置查询视图"的视图
	    When 删除朋友圈规则"CI相册朋友圈规则"
	    Then 成功删除朋友圈规则"CI相册朋友圈规则"
	    
	@delDiagram 
	Scenario: My_查看视图转换为系统模板
	    When 新建视图"查看视图转换为系统模板",描述为"查看视图转换为系统模板",文件夹为"我的"
	    Then 成功新建视图"查看视图转换为系统模板"
	    When 给视图"查看视图转换为系统模板"增加CI"信用卡工单1"坐标为"400""500"
	    Then 成功给视图"查看视图转换为系统模板"增加CI"信用卡工单1"坐标为"400""500"
	    #When 给角色名称为"admin"的角色添加如下功能权限:
		 #     | 功能权限     | 权限id  |
         #     | 系统模板     |    1047|
         #     | 对象模型     |     660 |
         #     | 关系模型     |     661 |
         #     | 图标管理     |     662 |
         #     |数据中心列表 |    705 |
         #     |全局资源管理 |    707 |
        #Then 角色名称为"admin"的角色成功添加功能权限如下:
         #	  | 功能权限     | 权限id  |
         #    | 系统模板     |    1047|
         #    | 对象模型     |     660 |
         #    | 关系模型     |     661 |
         #    | 图标管理     |     662 |
         #    |数据中心列表 |    705 |
         #    |全局资源管理 |    707 |
	    When 将视图"查看视图转换为系统模板"转换为系统模板
	    Then 视图"查看视图转换为系统模板"成功转换为系统模板
	    When 删除名称为"查看视图转换为系统模板"的视图
		Then 成功删除名称为"CI相册配置查询视图"的视图
	
   Scenario: My_查看视图告警级别从Base读取
        When 新建视图"告警级别从Base读取",描述为"告警级别从Base读取",文件夹为"我的"
	    Then 成功新建视图"告警级别从Base读取"
	    When 告警级别从Base读取
	    Then 成功告警级别从Base读取
	    
   @delDiagramComb
   Scenario: My_在我的视图内能搜索文件夹内的视图
       When 创建名称为"搜索文件夹内视图"的文件夹
       Then 名称为"搜索文件夹内视图"的文件夹创建成功
       When 创建多个视图名称为"搜索视图er1,搜索视图er2,搜索视图3",视图描述为"新建视图"的视图
	   Then 系统中存在多个视图名称为"搜索视图er1,搜索视图er2,搜索视图3",视图描述为"新建视图"的视图
	   When 给视图"搜索视图er1"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"搜索视图er1"增加常用图标"DB"坐标为"200""300"
	   When 给视图"搜索视图er2"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"搜索视图er2"增加常用图标"DB"坐标为"200""300"
	   When 给视图"搜索视图3"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"搜索视图3"增加常用图标"DB"坐标为"200""300"
       And  将多个视图"搜索视图er1,搜索视图er2,搜索视图3"移动到文件夹"搜索文件夹内视图"
       When 在文件夹"搜索文件夹内视图"内搜索视图名称为"搜索视图"的视图
       Then 在文件夹"搜索文件夹内视图"内视图名称为"搜索视图"的视图成功搜索出来
	   When 删除多个视图"搜索视图er1,搜索视图er2,搜索视图3"
	   Then 成功删除多个视图"搜索视图er1,搜索视图er2,搜索视图3"
       When 删除文件夹"搜索文件夹内视图"
       Then 成功删除文件夹"搜索文件夹内视图"
       
   @delDiagramComb
   Scenario: My_在我的视图内帅选模板视图
       When 创建名称为"文件夹内搜索模板"的文件夹
       Then 名称为"文件夹内搜索模板"的文件夹创建成功      
       When 创建多个视图名称为"系统视图1,系统视图2,系统视图3",视图描述为"新建视图"的视图
	   Then 系统中存在多个视图名称为"系统视图1,系统视图2,系统视图3",视图描述为"新建视图"的视图
	   When 给视图"系统视图1"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"系统视图1"增加常用图标"DB"坐标为"200""300"
	   When 给视图"系统视图2"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"系统视图2"增加常用图标"DB"坐标为"200""300"
	   When 给视图"系统视图3"增加常用图标"DB"坐标为"200""300"
	   Then 成功给视图"系统视图3"增加常用图标"DB"坐标为"200""300"	   
	   When 将视图"系统视图1"转换为系统模板
	   Then 视图"系统视图1"成功转换为系统模板
	   When 将视图"系统视图2"转换为系统模板
	   Then 视图"系统视图2"成功转换为系统模板
	   When 将视图"系统视图3"转换为系统模板
	   Then 视图"系统视图3"成功转换为系统模板
	   And  将多个视图"系统视图1,系统视图2"移动到文件夹"文件夹内搜索模板"
	   When 帅选文件夹为"文件夹内搜索模板"模板视图
	   Then 成功帅选文件夹为"文件夹内搜索模板"模板视图
	   When 删除多个视图"系统视图1,系统视图2,系统视图3"
	   Then 成功删除多个视图"系统视图1,系统视图2,系统视图3"
	   When 删除文件夹"文件夹内搜索模板"
       Then 成功删除文件夹"文件夹内搜索模板"