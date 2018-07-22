@BASE
Feature: Base_Severity_事件级别定义
    @Smoke
    Scenario Outline:Severity_事件级别定义增删改查
		When 创建级别数值为"<severity>"，颜色值为"<color>"，告警声音为"<filename>"，中文名为"<chineseName>"，英文名为"<englishName>"的事件级别定义
		Then 系统中存在级别数值为"<severity>"，颜色值为"<color>"，告警声音为"<filename>"，中文名为"<chineseName>"，英文名为"<englishName>"的事件级别定义
		When 将级别数值为"<severity>"事件级别修改为"<updateSeverity>"，颜色值修改为"<updateColor>"，告警声音修改为"<updateFilename>"，中文名修改为"<updateChineseName>"，英文名修改为"<updateEnglishName>"的事件级别定义
		Then 系统中存在级别数值为"<updateSeverity>"，颜色值为"<updateColor>"，告警声音为"<updateFilename>"，中文名为"<updateChineseName>"，英文名为"<updateEnglishName>"的事件级别定义
		When 删除级别数值为"<updateSeverity>"的事件级别定义
		Then 系统中不存在级别数值为"<updateSeverity>"的事件级别定义
		
    Examples: 
      | common  | severity| color    |filename |chineseName|englishName| updateSeverity| updateColor| updateFilename|updateChineseName|updateEnglishName|
      | 正常数据     | 5       |  #548dd4 | 3.mp3   |       严重   |Major      | 6             |  #fabf8f   | 4.mp3    |轻微|light|
      | 正常数据     | 7       |  #fbd4b4 | 5.mp3   |       重量级|Major123   | 8             |  #e5dfec   | 2.mp3    |轻度|light123|
      | 中文名称最大字符30个中文     | 7       |  #fbd4b4 | 5.mp3   |       测试事件级别中文名称最大字符三十个测试事件级别中文名称最大字|Major123   | 8             |  #e5dfec   | 2.mp3    |轻度|light123|
      | 英文名称最大字符30个中文     | 7       |  #fbd4b4 | 5.mp3   |       中文名称|测试事件级别中文名称最大字符三十个测试事件级别中文名称最大字   | 8             |  #e5dfec   | 2.mp3    |轻度|light123|
    
    @Smoke
    Scenario: Severity_删除多个事件级别定义
      Given 创建多个级别数值为"5,6,7,8"，颜色值为"#548dd4"，告警声音为"3.mp3"，中文名为"严重"，英文名为"Major"的事件级别定义
      When 删除多个级别数值为"5,6,7,8"的事件级别定义
	  Then 系统中不存在多个级别数值为"5,6,7,8"的事件级别定义