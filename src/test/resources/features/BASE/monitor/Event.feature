@BASE
Feature: BASE_模拟告警_Event

	@Smoke
    Scenario Outline:Event_发送模拟告警
		When 发送模拟告警:告警对象"后台流程1"告警指标"<sourceAlertKey>"告警级别"<Severity>"告警详情"<Summary>"告警状态"<Status>"
		Then 数据库存在或不存在一条告警记录:告警对象"后台流程1"告警指标"<sourceAlertKey>"告警级别"<Severity>"告警详情"<Summary>"告警状态"<Status>"
		
    Examples: 
      |common   |sourceAlertKey|Severity|Summary|Status| 
      |模拟告警  | 告警指标aa | 严重 | 告警描述| 打开 | 
      |模拟告警  | 告警指标aa | 重要 | 告警描述| 关闭 | 
      |模拟告警  | 告警指标cc | 一般 | 告警描述| 打开 |
      |模拟告警  | 告警指标dd | 其他 | 告警描述| 关闭|
      |模拟告警  | @&_-~！@#￥%……&*（））——， | 严重 |@&_-~！@#￥%……&*（））——，| 打开 | 
      |模拟告警  |【、；‘。、《》？’】234567是￥| 严重 |【、；‘。、《》？’】234567是￥| 打开 | 		
      |模拟告警  |abc_123| 严重 |abc_123| 打开 | 	
  
