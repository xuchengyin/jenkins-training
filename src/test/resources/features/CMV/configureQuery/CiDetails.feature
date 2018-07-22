@CMV
Feature: CMV_配置查询_CI详情


	@Smoke
	Scenario: 修改表单

     When 修改表单如下:
    	| title | formCols | orderNo | attrName | formRowNum | formColNum | className |
      	| 测试修改    |    3   |    1   | 整数 |     0     |    0    | s@&_- |
      	| 测试修改    |    3   |    1   | 小数 |     0     |    1    | s@&_- |
      	| 测试修改    |    3   |    1   | 文本 |     0     |    2    | s@&_- |
      	| 测试修改1 |    3   |    2   | 日期 |     0     |    0    | s@&_- |
      	| 测试修改1 |    3   |    2   | 名称 |     0     |    1    | s@&_- |
      	| 测试修改1 |    3   |    2   | 枚举 |     0     |    2    | s@&_- |
      	
	@cleanRltCls
	@Smoke
	Scenario: 新建CI关联关系
    When 创建名称为"ciDetails测试"的关系分类
	When 新建分类"s@&_-"到分类"Application"的关系"ciDetails测试"
	Then 成功新建分类"s@&_-"到分类"Application"的关系"ciDetails测试"
	
	When 创建名称为"ciDetails测试1"的关系分类
	When 新建分类"s@&_-"到分类"Cluster"的关系"ciDetails测试1"
	Then 成功新建分类"s@&_-"到分类"Cluster"的关系"ciDetails测试1"
	Then 创建如下CI关系:
	|关系名称|sourceCiCode|targetCiCode|
	|ciDetails测试|six|400网关1|
	|ciDetails测试|six|金融卡-IC系统1|
	|ciDetails测试|six|电子支付平台1|
	|ciDetails测试|six|微信银行1|
	|ciDetails测试|nine|金融卡-IC系统1|
	|ciDetails测试|nine|电子支付平台1|
	|ciDetails测试|eight|微信银行1|
	
	Then 导出当前关系,导出源ciCode为"six",目标ciClass为"Application",关系名为"ciDetails测试"的所有关系
#下面targetCiCode选和six有关联的任意一个即可
	Then 用rltName为"ciDetails测试",sourceCiCode为"six",targetCiCode为"400网关1"进行导出验证验证

	Then 用如下数据验证关系数量:
	|源或目标ciCode|源或目标ciClassName|关系分类|数量|
	|six|Application|ciDetails测试|5|
	|six|Cluster|ciDetails测试1|0|

#下面注意，cicode，前面是源后面是目标
	Then 删除关系名为"ciDetails测试"以及CICODE为"nine"和"电子支付平台1"的CI关系
#下面targetCiCode选和six有关联的任意一个即可
	Then 删除全部关系,删除源ciCode为"six",目标ciClass为"Application",目标ciCode为"400网关1",关系名为"ciDetails测试"的所有关系
	
	
	@cleanRltCls
	@Smoke @Debug
	Scenario: RCA测试
	When 创建名称为"RCA测试"的关系分类
	When 新建分类"s@&_-"到分类"Application"的关系"RCA测试"
	Then 成功新建分类"s@&_-"到分类"Application"的关系"RCA测试"
	When 新建分类"Application"到分类"Cluster"的关系"RCA测试"
	Then 成功新建分类"Application"到分类"Cluster"的关系"RCA测试"
	
	When 保存可视化建模中影响分析关系模型热点分类为"s@&_-"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |s@&_-|Application|RCA测试|
     	
	Then 成功保存可视化建模中影响分析关系模型热点分类为"s@&_-"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |s@&_-|Application|RCA测试|
     	     	
    When 保存可视化建模中影响分析关系模型热点分类为"Application"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |Application|Cluster|RCA测试|
     	
    Then 成功保存可视化建模中影响分析关系模型热点分类为"Application"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |Application|Cluster|RCA测试|
     	
	When 在关系分类"RCA测试"下,创建源分类为"s@&_-",源对象为"six",目标分类为"Application",目标对象为"400网关1"关系数据
	When 在关系分类"RCA测试"下,创建源分类为"Application",源对象为"400网关1",目标分类为"Cluster",目标对象为"WEB-Nginx1"关系数据
	
	Then 验证RCA是否创建成功
	|sourceCiCode|targetCiCode|rltClassName|depth|
	|six|400网关1|RCA测试|1|
#第一个sourceCiCode，必须是初始的cICode
	Then 验证depRCA是否创建成功
	|sourceCiCode|targetCiCode|rltClassName|depth|
	|six|400网关1|RCA测试|3|
	|400网关1|WEB-Nginx1|RCA测试|3|
	
	Scenario: 增加CI目录
	  When 创建名称为测试配置查询的CiClass目录
	
	Scenario Outline:增加CIClass
	  When 在"<dirName>"目录下,创建名称为"<ciClassName>"的ci分类,使用图标为"<imageName>"
	  Examples: 添加多种ci类
      | commons   | ciClassName                     |dirName| imageName|
      | 配置_首校验@_尾校验_ | @啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_ |测试配置查询| Default   |
      | 配置_首校验&_尾校验& | &啊啊啊啊where啊啊啊啊ABC啊啊啊啊啊啊啊啊啊啊啊啊啊@ |测试配置查询| Default   |
      | 配置_首校验__尾校验& | _啊啊啊啊or啊啊啊啊啊啊啊呵呵呵啊啊人啊啊啊啊啊啊啊啊啊& |测试配置查询| Default   |
    
    Scenario: 增加属性
    And 给"@啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_"分类添加如下属性:
    	| 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |         |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |       |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |         |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |        |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"]|       0 |                  |
      | 日期     | 日期                    |  0 |     1 |       |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |         |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |         |       0 | 去玩儿提法国红酒考拉海购                 |
      | 字典_type | 数据字典_type_Application |  0 |     1 |        |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |        |       0 |                  |
    
    And 给"&啊啊啊啊where啊啊啊啊ABC啊啊啊啊啊啊啊啊啊啊啊啊啊@"分类添加如下属性:
    	|属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |         |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |       |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |         |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |        |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"]|       0 |                  |
      | 日期     | 日期                    |  0 |     1 |       |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |         |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |         |       0 | 去玩儿提法国红酒考拉海购                 |
      | 字典_type | 数据字典_type_Application |  0 |     1 |        |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |        |       0 |                  |
      
    And 给"_啊啊啊啊or啊啊啊啊啊啊啊呵呵呵啊啊人啊啊啊啊啊啊啊啊啊&"分类添加如下属性:
    	| 属性名称    | 属性类型                  | 必填 | Lable | 枚举值       | CI Code | 默认值              |
      | 整数      | 整数                    |  1 |     0 |         |       0 |         12345678 |
      | 小数      | 小数                    |  0 |     0 |       |       0 | 234.000000000000 |
      | 文本      | 文本                    |  1 |     1 |         |       0 | werdfghnbvc      |
      | 文章      | 文章                    |  0 |     0 |        |       0 | @&()__——（）       |
      | 枚举      | 枚举                    |  0 |     1 | ["月","日"]|       0 |                  |
      | 日期     | 日期                    |  0 |     1 |       |       0 | 1990-10-90       |
      | 字符串     | 字符串                   |  1 |     1 |         |       1 |                  |
      | 名称      | 字符串                   |  1 |     1 |         |       0 | 去玩儿提法国红酒考拉海购                 |
      | 字典_type | 数据字典_type_Application |  0 |     1 |        |       0 |                  |
      | 字典_tag  | 数据字典_tag_APP          |  0 |     1 |        |       0 |                  |
      
    Scenario: 添加数据
        When 给"@啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_"添加如下数据:
	  	|commont|整数| 小数|文本|文章|枚举|日期|字符串|名称| 字典_type |字典_tag  | 
	    |  正常  |1  |00005|afdkaja中阿达佛挡杀佛123%……&|2345sdfg直接抛能够&￥%。|月|4567890-已囧ok|第1-0次|""| ||
	    |整数_最大长度|1234567890009876|0|""|""|日|""|第2-0次|""| ||
	    |小数_最大长度|1|99999999999.9999|""|""|""|""|第3-0次|""| ||
	    |文本_最大长度|1|0|文本_最大长度1000及特殊字符.txt|""|""|""|第4-0次|""| ||
	    |文本_js|1|0|<div class="container">|""|""|""|第5-0次|""| ||
	    |文本_最大长度|1|0|""|文章_长度2000及特殊字符.txt|""|""|第6-0次|""| ||
	    |文本_最大长js度|1|0|""|<div class="container">|""|""|第7-0次|""| ||
	    |主键_最大长度|1|0|""|""|""|""|~！@#￥%……&*（）—+—+——————·|""| ||
	    |字符串_最大长度|1|0|""|""|""|""|"第9-0次"|字符串_最大长度200及特殊字符.txt| ||
	    |字符串_js|1|0|""|""|""|""|"第10-0次"|<div class="container">| Application||
	    |默认值| | | | |""| |"第11-0次"|| ||
	    |默认值| | | | |""| |"第12-0次"|| |APP|
	    
	    When 给"&啊啊啊啊where啊啊啊啊ABC啊啊啊啊啊啊啊啊啊啊啊啊啊@"添加如下数据:
	  	|commont|整数| 小数|文本|文章|枚举|日期|字符串|名称| 字典_type |字典_tag  | 
	    |  正常  |1  |0005|afdkaja中阿达佛挡杀佛123%……&|2345sdfg直接抛能够&￥%。|月|4567890-已囧ok|"第1-1次"|""| ||
	    |整数_最大长度|1234567890009876|0|""|""|日|""|"第2-1次|""| ||
	    |小数_最大长度|1|99999999999.9999|""|""|""|""|"第3-1次"|""| ||
	    |文本_最大长度|1|0|文本_最大长度1000及特殊字符.txt|""|""|""|第4-1次|""| ||
	    |文本_js|1|0|<div class="container">|""|""|""|第5-1次|""| ||
	    |文本_最大长度|1|0|""|文章_长度2000及特殊字符.txt|""|""|第6-1次|""| ||
	    |文本_最大长js度|1|0|""|<div class="container">|""|""|第7-1次|""| ||
	    |主键_最大长度|1|0|""|""|""|""|~！@#￥%……&*（）——————————·|""| ||
	    |字符串_最大长度|1|0|""|""|""|""|"第91次"|字符串_最大长度200及特殊字符.txt| ||
	    |字符串_js|1|0|""|""|""|""|"第10-1次"|<div class="container">| Application||
	    |默认值| | | | |""| |"第11-1次"|| ||
	    |默认值| | | | |""| |"第12-1次"|| |APP|
	    
	    When 给"_啊啊啊啊or啊啊啊啊啊啊啊呵呵呵啊啊人啊啊啊啊啊啊啊啊啊&"添加如下数据:
	  	|commont|整数| 小数|文本|文章|枚举|日期|字符串|名称| 字典_type |字典_tag  | 
	    |  正常  |1  |0005|afdkaja中阿达佛挡杀佛123%……&|2345sdfg直接抛能够&￥%。|月|4567890-已囧ok|"第1-2次"|""| ||
	    |整数_最大长度|1234567890009876|0|""|""|日|""|"第2-2次|""| ||
	    |小数_最大长度|1|99999999999.9999|""|""|""|""|"第3-2次"|""| ||
	    |文本_最大长度|1|0|文本_最大长度1000及特殊字符.txt|""|""|""|第4-2次|""| ||
	    |文本_js|1|0|<div class="container">|""|""|""|第5-2次|""| ||
	    |文本_最大长度|1|0|""|文章_长度2000及特殊字符.txt|""|""|第6-2次|""| ||
	    |文本_最大长js度|1|0|""|<div class="container">|""|""|第7-2次|""| ||
	    |主键_最大长度|1|0|""|""|""|""|~！@#￥%……&*（）———+——————·|""| ||
	    |字符串_最大长度|1|0|""|""|""|""|"第9-2次"|字符串_最大长度200及特殊字符.txt| ||
	    |字符串_js|1|0|""|""|""|""|"第10-2次"|<div class="container">| Application||
	    |默认值| | | | |""| |"第11-2次"|| ||
	    |默认值| | | | |""| |"第12-2次"|| |APP|
	    


	    

	Scenario: 删除CI
	Then 用以下参数进行授权Auth:
  	|ciClassNames|roleName|dataType|see|edit|delete|add|
  	|@啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_|admin|2|true|true|true|true|
	
	Then 批量删除以下主键的ci：
	    | className | ci_code |主键名|
      	| @啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_ |    第1-0次   |字符串|
      	| @啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_ |    第2-0次   |字符串|
    And 用以下数据进行查询操作:
		 | commons  | ci_name |tag_name | key_world|
      	 |正向 | @啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_ |  |  |
    Then 验证删除操作

	

	@cleanAll
	Scenario: 修改CI
	Then 批量修改以下主键的ci：
	    | className | ci_codes |attrs|
      	| @啊啊啊啊and啊啊啊啊啊啊123啊_啊天啊啊啊啊啊啊啊啊啊_ | 第7-0次:第6-0次:第5-0次   |整数:1,小数:1.258,文本:文本,文章:文章,枚举:月,日期:日期,名称:名称,字典_type:400网关1|
	Then 查询className为"Application",关键字为"后台流程1"的ci
		
	Scenario: Event_推送多条告警
      When 给CI推送多个告警:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|lastOccurrence|
   		|noah推送|5      |60          |信用卡决策1     |响应时间 000     |5        |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|2018-03-09 14:09:23|
   		|noah推送|3      |61          |信用卡决策1     |响应时间 111     |4       |Critical      |1|业务响应时间达到 14555ms，已超过设定的阈值 1267ms！|2018-03-09 15:24:23|
   		|noah推送|2      |62          |信用卡决策1     |响应时间 222     |3       |Critical      |1|业务响应时间达到 14666ms，已超过设定的阈值 1268ms！|2018-03-09 15:25:23|
   		|noah推送|4      |63          |信用卡决策1     |响应时间333     |2      |Critical      |1|业务响应时间达到 14777ms，已超过设定的阈值 1269ms！|2018-03-09 15:26:23|
   		|noah推送|1      |64          |信用卡决策1     |响应时间 444     |1      |Critical      |1|业务响应时间达到 14888ms，已超过设定的阈值 1270ms！|2018-03-09 15:27:23|
   	 Then 成功给CI推送多个告警:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|lastOccurrence|
   		|noah推送|5      |60          |信用卡决策1     |响应时间 000    |5       |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|2018-03-09 14:09:23|
   		|noah推送|3      |61          |信用卡决策1     |响应时间 111    |4       |Critical      |1|业务响应时间达到 14555ms，已超过设定的阈值 1267ms！|2018-03-09 15:24:23|
   		|noah推送|2      |62          |信用卡决策1     |响应时间 222    |3       |Critical      |1|业务响应时间达到 14666ms，已超过设定的阈值 1268ms！|2018-03-09 15:25:23|
   	    |noah推送|4      |63          |信用卡决策1     |响应时间333     |2       |Critical      |1|业务响应时间达到 14777ms，已超过设定的阈值 1269ms！|2018-03-09 15:26:23|
   		|noah推送|1      |64          |信用卡决策1     |响应时间 444    |1       |Critical      |1|业务响应时间达到 14888ms，已超过设定的阈值 1270ms！|2018-03-09 15:27:23|
   	And 删除CICODE为"信用卡决策1"的多条告警
   	
  @delKpi
  Scenario: Performance_挂载指标推送多个性能
    When 创建含有对象组的名称为"CPU使用率"，别名为"测试CPU使用率"，指标描述为"测试CPU使用率"，单位为"K"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
    Then 系统中存在含有对象组的名称为"CPU使用率"，别名为"测试CPU使用率"，指标描述为"测试CPU使用率"，单位为"K"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
    When 创建含有对象组的名称为"磁盘空间"，别名为"测试磁盘空间"，指标描述为"测试磁盘空间"，单位为"U"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
    Then 系统中存在含有对象组的名称为"磁盘空间"，别名为"测试磁盘空间"，指标描述为"测试磁盘空间"，单位为"U"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
    When 创建含有对象组的名称为"湿度"，别名为"测试湿度"，指标描述为"测试湿度"，单位为"M"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
    Then 系统中存在含有对象组的名称为"湿度"，别名为"测试湿度"，指标描述为"测试湿度"，单位为"M"，分类对象组为"Application"，标签对象组为"APP、Clu"，关系对象组为"AppRlt、特殊"的指标
    When 给CI推送多个性能:
      | common | ciName | kpiName | kpiDesc | kpiValue |
      | noah推送 | 信用卡决策1 | CPU使用率  | 描述      | 50%      |
      | noah推送 | 信用卡决策1 | 磁盘空间    | 描述      | 30%      |
      | noah推送 | 信用卡决策1 | 湿度      | 描述      | 40%      |
    Then 成功给CI推送多个性能:
      | common | ciName | kpiName | kpiDesc | kpiValue |
      | noah推送 | 信用卡决策1 | CPU使用率  | 描述      | 50%      |
      | noah推送 | 信用卡决策1 | 磁盘空间    | 描述      | 30%      |
      | noah推送 | 信用卡决策1 | 湿度      | 描述      | 40%      |
    And 删除CICODE为"信用卡决策1"的多条性能
    When 删除名称为"CPU使用率"的指标
    Then 系统中不存在名称为"CPU使用率"的指标
    When 删除名称为"磁盘空间"的指标
    Then 系统中不存在名称为"磁盘空间"的指标
    When 删除名称为"湿度"的指标
    Then 系统中不存在名称为"湿度"的指标

  Scenario: Performance_无挂载指标推送多个性能
    When 给CI推送多个性能:
      | common | ciName | kpiName | kpiDesc | kpiValue |
      | noah推送 | 信用卡决策1 | 交易量     | 描述      | 50%      |
      | noah推送 | 信用卡决策1 | 成功率     | 描述      | 50%      |
      | noah推送 | 信用卡决策1 | 响应时间    | 描述      | 30%      |
      | noah推送 | 信用卡决策1 | 响应率     | 描述      | 40%      |
    Then 成功给CI推送多个性能:
      | common | ciName | kpiName | kpiDesc | kpiValue |
      | noah推送 | 信用卡决策1 | 交易量     | 描述      | 50%      |
      | noah推送 | 信用卡决策1 | 成功率     | 描述      | 50%      |
      | noah推送 | 信用卡决策1 | 响应时间    | 描述      | 30%      |
      | noah推送 | 信用卡决策1 | 响应率     | 描述      | 40%      |
    And 删除CICODE为"信用卡决策1"的多条性能

    
    
    
    
    
    
    
    
    
    
    
    
    
    

	@Smoke @cleanRltCls
	Scenario: 通过影响规则查询CI的故障根因
	When 创建名称为"故障根因"的关系分类
	When 新建分类"s@&_-"到分类"Application"的关系"故障根因"
	Then 成功新建分类"s@&_-"到分类"Application"的关系"故障根因"
	When 新建分类"Application"到分类"Cluster"的关系"故障根因"
	Then 成功新建分类"Application"到分类"Cluster"的关系"故障根因"
	
	When 保存可视化建模中影响分析关系模型热点分类为"s@&_-"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |s@&_-|Application|故障根因|
     	
	Then 成功保存可视化建模中影响分析关系模型热点分类为"s@&_-"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |s@&_-|Application|故障根因|
     	     	
    When 保存可视化建模中影响分析关系模型热点分类为"Application"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |Application|Cluster|故障根因|
     	
    Then 成功保存可视化建模中影响分析关系模型热点分类为"Application"，分类关系如下:
     	|common|sourceClassName|targetClassName|rltClassName|
     	|描述    |Application|Cluster|故障根因|
     	
	When 在关系分类"故障根因"下,创建源分类为"s@&_-",源对象为"six",目标分类为"Application",目标对象为"400网关1"关系数据
	When 在关系分类"故障根因"下,创建源分类为"Application",源对象为"400网关1",目标分类为"Cluster",目标对象为"WEB-Nginx1"关系数据
	
	Then 查询故障根因,用以下参数:
	|ciCodes|rltClassName|depth|
	|six:400网关1|故障根因|1|
	Then 验证故障跟因查询结果,用以下参数:
	|sourceCiCode|targetCiCode|ciRltClassName|
	|six|400网关1|故障根因|
	|400网关1|WEB-Nginx1|故障根因|



	@Smoke @cleanRltCls
	Scenario: 配置查询,关联关系正反查询
	When 创建名称为"配置查询关联关系正反"的关系分类
	When 新建分类"s@&_-"到分类"Application"的关系"配置查询关联关系正反"
	Then 成功新建分类"s@&_-"到分类"Application"的关系"配置查询关联关系正反"
	When 新建分类"Application"到分类"Cluster"的关系"配置查询关联关系正反"
	Then 成功新建分类"Application"到分类"Cluster"的关系"配置查询关联关系正反"
	
	When 在关系分类"配置查询关联关系正反"下,创建源分类为"s@&_-",源对象为"six",目标分类为"Application",目标对象为"400网关1"关系数据
	When 在关系分类"配置查询关联关系正反"下,创建源分类为"Application",源对象为"400网关1",目标分类为"Cluster",目标对象为"WEB-Nginx1"关系数据
    
    Then 用如下参数查询关系:
    |className|targetOrSource|order|queryTargetName|
    |s@&_-|source|1|Application|
    |Application|source|1|Cluster:s@&_-|
    |Application|target|1|s@&_-|
    |Cluster|target|1|Application|
    |Application|sAndt|1|Cluster:s@&_-|
    