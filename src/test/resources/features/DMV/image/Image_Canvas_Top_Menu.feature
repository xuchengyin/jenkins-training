@DMV
@delDiagram
Feature: DMV_绘图_画布顶部菜单
    @Smoke
	Scenario: Canvas_Top_Menu_文件打开
	   When 新建视图"测试打开视图",描述为"测试打开视图",文件夹为"我的"
	   Then 成功新建视图"测试打开视图"
	   When 给视图"测试打开视图"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"测试打开视图"增加CI"信用卡决策1"坐标为"400""600"
	   When 文件打开视图
	   Then 文件成功打开视图"测试打开视图"
       When 删除名称为"测试打开视图"的视图
	   Then 成功删除名称为"测试打开视图"的视图
	   
    @Smoke
	Scenario: Canvas_Top_Menu_插入图片
	   When 新建视图"插入图片",描述为"插入图片",文件夹为"我的"
	   Then 成功新建视图"插入图片"
	   When 为视图"插入图片"添加图片"rttt.jpg"
	   Then 为视图"插入图片"成功添加图片"rttt.jpg"
	   When 删除名称为"插入图片"的视图
	   Then 成功删除名称为"插入图片"的视图
   
	Scenario Outline: Canvas_Top_Menu_绘制面板,清除面板
	   When 新建视图"绘制面板",描述为"绘制面板",文件夹为"我的"
	   Then 成功新建视图"绘制面板"
	   When 给视图"绘制面板"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"绘制面板"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"绘制面板"绘制面板宽度为"<width>"高度为"<height>"
	   Then 视图"绘制面板"成功绘制面板宽度为"<width>"高度为"<height>"
	   When 视图"绘制面板"清除面板宽度为"<graphWidth>"高度为"<graphHeight>"
	   Then 视图"绘制面板"成功清除面板宽度为"<graphWidth>"高度为"<graphHeight>"
	   When 删除名称为"绘制面板"的视图
	   Then 成功删除名称为"绘制面板"的视图
	Examples:
		 | common  |  width |height|graphWidth|graphHeight|
	     | 正常高度     |	700|500|1625|346|
	     | 超大高度     |  1000|900|1625|346|
	     | 超小高度     |  300| 200|1625|346|
	     
    @Smoke
	Scenario: Canvas_Top_Menu_页面_背景颜色
	   When 新建视图"背景颜色",描述为"页面背景颜色",文件夹为"我的"
	   Then 成功新建视图"背景颜色"
	   When 给视图"背景颜色"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"背景颜色"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"背景颜色"选择背景颜色"#c0504d"
	   Then 视图"背景颜色"成功选择背景颜色"#c0504d"
	   When 删除名称为"背景颜色"的视图
	   Then 成功删除名称为"背景颜色"的视图

	Scenario: Canvas_Top_Menu_页面_插入背景
	   When 新建视图"插入背景",描述为"插入背景颜色",文件夹为"我的"
	   Then 成功新建视图"插入背景"
	   When 给视图"插入背景"选择图片"rttt.jpg"为背景
	   Then 视图"插入背景"成功选择图片"rttt.jpg"为背景
	   When 删除名称为"插入背景"的视图
	   Then 成功删除名称为"插入背景"的视图
	   
    @Smoke
	Scenario Outline: Canvas_Top_Menu_设置_不透明度
	   When 新建视图"不透明度",描述为"不透明度",文件夹为"我的"
	   Then 成功新建视图"不透明度"
       When 给视图"不透明度"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"不透明度"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"不透明度"设置不透明度"<opacity>"
	   Then 给视图"不透明度"成功设置不透明度"<opacity>"
	   When 删除名称为"不透明度"的视图
	   Then 成功删除名称为"不透明度"的视图
	 Examples:
	     | common  | opacity|
	     | 不透明度     |  25%|
	     | 不透明度     |  50%|
	     | 不透明度     |  75%|
	     | 不透明度     |  100%|

	Scenario Outline: Canvas_Top_Menu_设置_图标颜色
	   When 新建视图"图标颜色",描述为"图标颜色",文件夹为"我的"
	   Then 成功新建视图"图标颜色"
       When 给视图"图标颜色"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"图标颜色"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"图标颜色"CICode为"信用卡决策1"设置图标颜色"<utfilterValue>"
	   Then 给视图"图标颜色"CICode为"信用卡决策1"成功设置图标颜色"<utfilterValue>"
	   When 删除名称为"图标颜色"的视图
	   Then 成功删除名称为"图标颜色"的视图
	 Examples:
	     | common  | utfilterValue|
	     | 图标颜色     |  red|
	     | 图标颜色     |  green|
	     | 图标颜色     |  blue|
	     | 图标颜色     |  grey|
	     
	@Smoke
	Scenario: Canvas_Top_Menu_设置_图标大小_小
	   When 新建视图"图标大小",描述为"图标大小",文件夹为"我的"
	   Then 成功新建视图"图标大小"
       When 给视图"图标大小"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"图标大小"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"图标大小"CICode为"信用卡决策1"设置图标大小小"40,40"
	   Then 给视图"图标大小"CICode为"信用卡决策1"成功设置图标大小小"40,40"
	   When 删除名称为"图标大小"的视图
	   Then 成功删除名称为"图标大小"的视图
	   
    @Smoke
	Scenario: Canvas_Top_Menu_设置_图标大小_中
	   When 新建视图"图标大小",描述为"图标大小",文件夹为"我的"
	   Then 成功新建视图"图标大小"
       When 给视图"图标大小"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"图标大小"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"图标大小"CICode为"信用卡决策1"设置图标大小中"60,60"
	   Then 给视图"图标大小"CICode为"信用卡决策1"成功设置图标大小中"60,60"
	   When 删除名称为"图标大小"的视图
	   Then 成功删除名称为"图标大小"的视图
	   
	@Smoke
	Scenario: Canvas_Top_Menu_设置_图标大小_大
	   When 新建视图"图标大小",描述为"图标大小",文件夹为"我的"
	   Then 成功新建视图"图标大小"
       When 给视图"图标大小"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"图标大小"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"图标大小"CICode为"信用卡决策1"设置图标大小大"80,80"
	   Then 给视图"图标大小"CICode为"信用卡决策1"成功设置图标大小大"80,80"
	   When 删除名称为"图标大小"的视图
	   Then 成功删除名称为"图标大小"的视图	
	     
   
	Scenario Outline: Canvas_Top_Menu_设置_字体样式
	   When 新建视图"字体样式",描述为"字体样式",文件夹为"我的"
	   Then 成功新建视图"字体样式"
       When 给视图"字体样式"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"字体样式"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"字体样式"CICode为"信用卡决策1"设置字体样式"<horizontal>"
	   Then 给视图"字体样式"CICode为"信用卡决策1"成功设置字体样式"<horizontal>"
	   When 删除名称为"字体样式"的视图
	   Then 成功删除名称为"字体样式"的视图	    
     Examples:
        | common  | horizontal|
	    | 垂直显示     |  0|
	    | 水平显示     |  1|
	    
	@Smoke
	Scenario Outline: Canvas_Top_Menu_设置_自定义形状及资源包
	   When 新建视图"自定义形状及资源包",描述为"自定义形状及资源包",文件夹为"我的"
	   Then 成功新建视图"自定义形状及资源包"
	   When 给视图"自定义形状及资源包"增加常用图标"DB"坐标为"50""60"
	   Then 成功给视图"自定义形状及资源包"增加常用图标"DB"坐标为"50""60"
	   When 给图标设置"<imageName>","<imagexml>"
	   Then 给图标成功设置"<imageName>","<imagexml>"
	   When 删除名称为"自定义形状及资源包"的视图
	   Then 成功删除名称为"自定义形状及资源包"的视图
	   When 删除名称为"<imageName>"的图片
	   Then 系统中不存在名称为"<imageName>"的图片	
	   Examples:
        | common  | imageName|imagexml|
	    | 自定义形状  | 测试形状|<mxGraphModel><root width=\"52.951219512195124\" height=\"61\"><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\"\" imgCode=\"DB\" image_node=\"true\" size-ms=\"34.63414634146341,40\" size-mx=\"51.951219512195124,60\" size-ml=\"69.26829268292683,80\" id=\"2\"><mxCell style=\"image;html=1;image=http://192.168.1.82:1512/vmdb-sso/rsm/cli/read/122/10517.png\" vertex=\"1\" utimgCode=\"DB\" utimage_node=\"true\" utimgFullName=\"常用图标|DB\" utsize-ms=\"34.63414634146341,40\" utsize-mx=\"51.951219512195124,60\" utsize-ml=\"69.26829268292683,80\" parent=\"1\"><mxGeometry width=\"51.951219512195124\" height=\"60\" as=\"geometry\"/><Object as=\"_geometry\"/></mxCell></UserObject></root></mxGraphModel>|
	    | 创建资源包模块|资源包|<mxGraphModel><root width=\"52.951219512195124\" height=\"61\"><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\"\" imgCode=\"DB\" image_node=\"true\" size-ms=\"34.63414634146341,40\" size-mx=\"51.951219512195124,60\" size-ml=\"69.26829268292683,80\" id=\"2\"><mxCell style=\"image;html=1;image=http://192.168.1.82:1512/vmdb-sso/rsm/cli/read/122/10517.png\" vertex=\"1\" utimgCode=\"DB\" utimage_node=\"true\" utimgFullName=\"常用图标|DB\" utsize-ms=\"34.63414634146341,40\" utsize-mx=\"51.951219512195124,60\" utsize-ml=\"69.26829268292683,80\" utdefaultClassAttr=\"{&quot;10632&quot;:{&quot;CI Code&quot;:&quot;&quot;,&quot;Name&quot;:&quot;资源包&quot;,&quot;Supporter&quot;:&quot;资源包&quot;,&quot;Service Time&quot;:&quot;&quot;,&quot;Important Level&quot;:&quot;&quot;}}\" utdefaultClassId=\"10632\" parent=\"1\"><mxGeometry width=\"51.951219512195124\" height=\"60\" as=\"geometry\"/><Object as=\"_geometry\"/></mxCell></UserObject></root></mxGraphModel>|
	    | 特殊字符     | 测试形状%￥#@*()|<mxGraphModel><root width=\"52.951219512195124\" height=\"61\"><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\"\" imgCode=\"DB\" image_node=\"true\" size-ms=\"34.63414634146341,40\" size-mx=\"51.951219512195124,60\" size-ml=\"69.26829268292683,80\" id=\"2\"><mxCell style=\"image;html=1;image=http://192.168.1.82:1512/vmdb-sso/rsm/cli/read/122/10517.png\" vertex=\"1\" utimgCode=\"DB\" utimage_node=\"true\" utimgFullName=\"常用图标|DB\" utsize-ms=\"34.63414634146341,40\" utsize-mx=\"51.951219512195124,60\" utsize-ml=\"69.26829268292683,80\" parent=\"1\"><mxGeometry width=\"51.951219512195124\" height=\"60\" as=\"geometry\"/><Object as=\"_geometry\"/></mxCell></UserObject></root></mxGraphModel>|
	    | 英文加数字|eyrut7865432|<mxGraphModel><root width=\"52.951219512195124\" height=\"61\"><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\"\" imgCode=\"DB\" image_node=\"true\" size-ms=\"34.63414634146341,40\" size-mx=\"51.951219512195124,60\" size-ml=\"69.26829268292683,80\" id=\"2\"><mxCell style=\"image;html=1;image=http://192.168.1.82:1512/vmdb-sso/rsm/cli/read/122/10517.png\" vertex=\"1\" utimgCode=\"DB\" utimage_node=\"true\" utimgFullName=\"常用图标|DB\" utsize-ms=\"34.63414634146341,40\" utsize-mx=\"51.951219512195124,60\" utsize-ml=\"69.26829268292683,80\" utdefaultClassAttr=\"{&quot;10632&quot;:{&quot;CI Code&quot;:&quot;&quot;,&quot;Name&quot;:&quot;资源包&quot;,&quot;Supporter&quot;:&quot;资源包&quot;,&quot;Service Time&quot;:&quot;&quot;,&quot;Important Level&quot;:&quot;&quot;}}\" utdefaultClassId=\"10632\" parent=\"1\"><mxGeometry width=\"51.951219512195124\" height=\"60\" as=\"geometry\"/><Object as=\"_geometry\"/></mxCell></UserObject></root></mxGraphModel>|
   	    | 最大长度   |自定义形eeeeeeeeee34577自定义形eeeeeeeeee3|<mxGraphModel><root width=\"52.951219512195124\" height=\"61\"><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\"\" imgCode=\"DB\" image_node=\"true\" size-ms=\"34.63414634146341,40\" size-mx=\"51.951219512195124,60\" size-ml=\"69.26829268292683,80\" id=\"2\"><mxCell style=\"image;html=1;image=http://192.168.1.82:1512/vmdb-sso/rsm/cli/read/122/10517.png\" vertex=\"1\" utimgCode=\"DB\" utimage_node=\"true\" utimgFullName=\"常用图标|DB\" utsize-ms=\"34.63414634146341,40\" utsize-mx=\"51.951219512195124,60\" utsize-ml=\"69.26829268292683,80\" parent=\"1\"><mxGeometry width=\"51.951219512195124\" height=\"60\" as=\"geometry\"/><Object as=\"_geometry\"/></mxCell></UserObject></root></mxGraphModel>|
  	    |自定义形状最大长度50汉字|自定义形状自定义形状自定义形状自定义形状自定义形状自定义形状自定义形状自定义形状自定义形状自定义形测|"<mxGraphModel><root width="51.85" height="30.878037383177567"><mxCell id="0"/><mxCell id="1" parent="0"/><UserObject label="" imgCode="DBlnst" image_node="true" size-ms="40,23.551401869158877" size-mx="60,35.32710280373831" size-ml="80,47.10280373831775" id="2"><mxCell style="image;html=1;image=http://192.168.1.194/vmdb-sso/rsm/cli/read/122/10518.png" vertex="1" utimgCode="DBlnst" utimage_node="true" utimgFullName="常用图标|DBlnst" utsize-ms="40,23.551401869158877" utsize-mx="60,35.32710280373831" utsize-ml="80,47.10280373831775" parent="1"><mxGeometry width="60" height="35.32710280373831" as="geometry"/><Object as="_geometry"/></mxCell></UserObject></root></mxGraphModel>|
        |资源包最大长度50汉字|资源包测试资源包资源包测试资源包资源包测试资源包资源包测试资源包资源包测试资源包资源包测试资源包源包|<mxGraphModel><root width="51.85" height="30.878037383177567"><mxCell id="0"/><mxCell id="1" parent="0"/><UserObject label="" imgCode="DBlnst" image_node="true" size-ms="40,23.551401869158877" size-mx="60,35.32710280373831" size-ml="80,47.10280373831775" id="2"><mxCell style="image;html=1;image=http://192.168.1.194/vmdb-sso/rsm/cli/read/122/10518.png" vertex="1" utimgCode="DBlnst" utimage_node="true" utimgFullName="常用图标|DBlnst" utsize-ms="40,23.551401869158877" utsize-mx="60,35.32710280373831" utsize-ml="80,47.10280373831775" utdefaultClassAttr="{&quot;100000000005001&quot;:{&quot;CI Code&quot;:&quot;&quot;,&quot;Name&quot;:&quot;&quot;,&quot;Supporter&quot;:&quot;&quot;,&quot;Service Time&quot;:&quot;&quot;,&quot;Important Level&quot;:&quot;&quot;}}" utdefaultClassId="100000000005001" parent="1"><mxGeometry width="60" height="35.32710280373831" as="geometry"/><Object as="_geometry"/></mxCell></UserObject></root></mxGraphModel>|
  
   	Scenario Outline: Canvas_Top_Menu_设置_外部链接
	   When 新建视图"外部链接",描述为"外部链接",文件夹为"我的"
	   Then 成功新建视图"外部链接"
	   When 给视图"外部链接"增加CI"信用卡决策1"坐标为"400""600"
	   Then 成功给视图"外部链接"增加CI"信用卡决策1"坐标为"400""600"
	   When 给视图"外部链接"CICode为"信用卡决策1"设置外部链接名称为"<linkName>"链接地址为"<linkAddress>"
	   Then 视图"外部链接"CICode为"信用卡决策1"成功设置外部链接名称为"<linkName>"链接地址为"<linkAddress>"
	   When 删除名称为"外部链接"的视图
	   Then 成功删除名称为"外部链接"的视图
    Examples:
        | common  | linkName|linkAddress|
        |正常|链接|http://baidu.com|
        |最大长度|链接wer3455632链接wer34556|http://www.sina.com|
        |特殊字符|链接%$#@!|http://www.google.com|

    @Smoke @cleanRlt@Debug
    Scenario Outline: Canvas_Top_Menu_设置_生成链路,删除链路
        Given 系统中已存在如下ci分类:"Application,s@&_-"
	 	And 系统中已存在如下关系分类:"AppRlt"
	 	When 在关系分类"AppRlt"下,创建源分类为"Application",源对象为"400网关1",目标分类为"s@&_-",目标对象为"eight"关系数据
	 	Then "AppRlt"下存在只存在1条"400网关1"与"eight"的关系数据
	 	
	 	When 新建视图"生成链路",描述为"生成链路",文件夹为"我的"
		Then 成功新建视图"生成链路"
		
	    When 给视图"生成链路"增加CI"400网关1"坐标为"700""600"
	    When 给视图"生成链路"增加CI"eight"坐标为"500""700"
	    
	    When 给视图"生成链路"添加链路名称为"<linkedName>"链路颜色为"<linkedColor>"的ciCodes为"400网关1,eight"
	    Then 给视图"生成链路"成功添加链路名称为"<linkedName>"链路颜色为"<linkedColor>"的ciCodes为"400网关1,eight"
	    
	    When 删除视图"生成链路"的链路
	    Then 成功删除视图"生成链路"的链路
	    
	    When 删除名称为"生成链路"的视图
	    Then 成功删除名称为"生成链路"的视图
	    When 删除"AppRlt"关系下,属性值前匹配"eight"的关系数据
	 	Then "AppRlt"关系下,不存在属性值前匹配"eight"的关系数据
	Examples:
	    | common  | linkedName|linkedColor|
        |正常|链路123mju|12345|
        |最大长度|链路wer3455632链路wer34556|345567|
        |特殊字符|链路%$#@!|432567|
        |链路名称最大长度汉字30|成链路生成链路生成链路生成链路测试生成链路生成链路路生测试生|456732|

    Scenario Outline: Canvas_Top_Menu_吐槽
      When 新建视图"用户反馈",描述为"用户反馈",文件夹为"我的"
	  Then 成功新建视图"用户反馈"
	  When 给视图"用户反馈"增加CI"信用卡决策1"坐标为"400""600"
	  Then 成功给视图"用户反馈"增加CI"信用卡决策1"坐标为"400""600"
      When 用户给系统提供问题为"<content>"的反馈
      Then 用户成功给系统提供问题为"<content>"的反馈
      And  删除用户给系统提供问题为"<content>"的反馈
      When 删除名称为"用户反馈"的视图
	  Then 成功删除名称为"用户反馈"的视图
    Examples:
       |common|content|
       |正常|创建视图很方便|
       |最大长度|feedback.txt|
       |特殊字符|创建视图很方便%$#@!|
       
     Scenario: Canvas_Top_Menu_吐槽_导出
        When 新建视图"用户反馈",描述为"用户反馈",文件夹为"我的"
	    Then 成功新建视图"用户反馈"
	    When 给视图"用户反馈"增加CI"信用卡决策1"坐标为"400""600"
	    Then 成功给视图"用户反馈"增加CI"信用卡决策1"坐标为"400""600"
	    And  用户给系统提供以下问题的反馈:
	         |content|
             |创建视图很方便|
             |创建视图特别方便|
             |创建视图很方便%$#@!|
       When 用户导出反馈
       Then 用户成功导出以下反馈:
             |content|
             |创建视图很方便|
             |创建视图特别方便|
             |创建视图很方便%$#@!|
        And 删除以下用户反馈:
             |content|
             |创建视图很方便|
             |创建视图特别方便|
             |创建视图很方便%$#@!|
         