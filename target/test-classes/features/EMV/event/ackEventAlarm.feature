@EMV
Feature: EMV_确认告警_ackEventAlarm


    Scenario Outline: ackEventAlarm_确认告警
    	When 来自事件源"<sourceID>",数据源的事件序列号为"<sourceEventID>"的,告警对象"<sourceCIName>"的告警指标为"<sourceAlertKey>",发生了事件级别为"<severity>",源事件级别为"<sourceSeverity>",状态为"<status>",告警内容为"<summary>"的告警
    	Then 告警对象"<sourceCIName>"的告警指标"<sourceAlertKey>"成功入库
    	
    	Given 当用户所在角色管理中没有确认权限
    	When 确认如下告警
    	|sourceCIName|sourceAlertKey|
    	|WAP-YL--Host03     |响应时间                        |
    	Then 确认失败，提示:因权限不足，确认失败
    	Examples:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|
   		|noah推送|1       |1          |WAP-YL--Host03     |响应时间                        |1       |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|
    	
    Scenario Outline: ackEventAlarm_确认告警
    	Given 当前用户所在角色管理中有确认操作权限，且当前用户为admin用户
		When 确认如下告警,确认信息为："确认该告警"：
    	|sourceCIName|sourceAlertKey|
    	|WAP-YL--Host03     |响应时间                        |
    	Then 该告警成功被确认，状态变为已确认
    	Examples:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|
   		|noah推送|1       |1          |WAP-YL--Host03     |响应时间                        |1       |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|
     Scenario Outline: ackEventAlarm_确认告警	
    	Given 当前用户所在角色管理中有确认操作权限，当前用户为非admin用户，用户所在团队对该推送告警所属事件源没有确认权限
		When 确认如下告警
    	|sourceCIName|sourceAlertKey|
    	|WAP-YL--Host03     |响应时间                        |
    	Then 确认失败，提示:因权限不足，确认失败
    	Examples:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|
   		|noah推送|1       |1          |WAP-YL--Host03     |响应时间                        |1       |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|
     Scenario Outline: ackEventAlarm_确认告警	
    	Given 当前用户所在角色管理中有确认操作权限，当前用户为非admin用户，用户所在团队对该推送告警所属事件源有确认权限
		When 确认如下告警，确认信息为:"确认该告警"
    	|sourceCIName|sourceAlertKey|
    	|WAP-YL--Host03     |响应时间                        |
    	Then 该告警成功被确认，状态变为已确认    
    	Examples:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|
   		|noah推送|1       |1          |WAP-YL--Host03     |响应时间                        |1       |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|    
    
       	
    
   		
	