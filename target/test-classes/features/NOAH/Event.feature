@NOAH
Feature: NOAH_推送告警_Event

    Scenario Outline: Event_推送告警
    	When 给事件源为"<sourceID>",SourceEventID为"<sourceEventID>",CI为"<sourceCIName>"推送告警"<sourceAlertKey>",事件级别为"<severity>",值为"<sourceSeverity>",状态为"<status>",主题为"<summary>",时间为"<lastOccurrence>"
    	
    Examples:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|lastOccurrence|
   		|noah推送|5       |60           |three1      |响应时间                        |1       |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|2017-12-12 13:49:23|
