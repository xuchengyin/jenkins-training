@NOAH
Feature: NOAH_推送告警_EventToMq

    Scenario Outline: EventToMq_推送告警
    	When 来自事件源"<sourceID>",数据源的事件序列号为"<sourceEventID>"的,告警对象"<sourceCIName>"的告警指标为"<sourceAlertKey>",发生了事件级别为"<severity>",源事件级别为"<sourceSeverity>",状态为"<status>",告警内容为"<summary>"的告警
    	Then 告警对象"<sourceCIName>"的告警指标"<sourceAlertKey>"成功入库 
    	
    Examples:
   	 	|common|sourceID|sourceEventID|sourceCIName|sourceAlertKey|severity|sourceSeverity|status|summary|
   		|noah推送|1       |2        |test02     |响应时间                        |1       |Critical      |1|业务响应时间达到 143ms，已超过设定的阈值 125ms！|
   		
	