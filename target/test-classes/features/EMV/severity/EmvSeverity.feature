@EMV
Feature: EMV_Severity_事件级别定义
    Scenario Outline:Severity_事件级别定义增删改查
		When 新建级别数值为"<severity>"，颜色值为"<color>"，告警声音为"<voiceName>"，中文名为"<chineseName>"，英文名为"<englishName>"的事件级别
		Then 字典表存在级别数值为"<severity>"，颜色值为"<color>"，告警声音为"<voiceName>"，中文名为"<chineseName>"，英文名为"<englishName>"的事件级别
		And  再次新建级别数值为"<severity>"的事件级别，创建失败，kw="<kw>"
		When 将级别数值为"<severity>"事件级别修改为"<updateSeverity>"，颜色值修改为"<updateColor>"，告警声音修改为"<updateVoiceName>"，中文名修改为"<updateChineseName>"，英文名修改为"<updateEnglishName>"的事件级别
		Then 系统中存在级别数值为"<updateSeverity>"，颜色值为"<updateColor>"，告警声音为"<updateVoiceName>"，中文名为"<updateChineseName>"，英文名为"<updateEnglishName>"的事件级别
		When 删除级别数值为"<updateSeverity>"的事件级别
		Then 系统中不存在级别数值为"<updateSeverity>"的事件级别
		
    Examples: 
      | common  | severity| color    |voiceName |chineseName|englishName| updateSeverity| updateColor| updateVoiceName|updateChineseName|updateEnglishName|kw|
      | 正常数据     | 5       |  #f0453b | 钢琴曲01.mp3   |       5级   |five      |7|#daed50|钢琴曲03.mp3|7级|seven|null[5]已存在|
      | 正常数据     | 6       |  #b4c826 | 钢琴曲02.mp3   |       6级|six   |8|#5fb3e0|钢琴曲04.mp3|8级|eight|null[6]已存在|
    
    
#    Scenario: Severity_删除多个事件级别定义
#      Given 创建多个级别数值为"5,6,7,8"，颜色值为"#548dd4"，告警声音为"3.mp3"，中文名为"严重"，英文名为"Major"的事件级别定义
#      When 删除多个级别数值为"5,6,7,8"的事件级别定义
#	  Then 系统中不存在多个级别数值为"5,6,7,8"的事件级别定义