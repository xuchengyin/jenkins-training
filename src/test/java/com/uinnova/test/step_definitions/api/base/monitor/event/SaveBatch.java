package com.uinnova.test.step_definitions.api.base.monitor.event;

import static org.junit.Assert.assertNotNull;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.monitor.severity.QuerySeverityDropList;
import com.uinnova.test.step_definitions.api.cmv.monitor.event.QueryDefaultUrl;

/**
 * @author wsl
 * 2018-1-8
 * 模拟告警
 *
 */
public class SaveBatch extends RestApi{
	/**
	 * @param sourceCIName  告警对象
	 * @param sourceAlertKey 告警指标
	 * @param Severity 告警级别
	 * @param summary
	 * @param status
	 * @return
	 */
	public JSONObject saveBatch(String sourceCIName, String sourceAlertKey, String Severity, String summary, String status){
		String url = ":1511/tarsier-vmdb/cmv/monitor/event/saveBatch";
		JSONArray paramArr = new JSONArray();
		JSONObject alertObj = new JSONObject();
		alertObj.put("sourceCIName", sourceCIName);
		alertObj.put("sourceAlertKey", sourceAlertKey);
		QuerySeverityDropList qServityDropList= new QuerySeverityDropList();
		JSONObject servityResult = qServityDropList.querySeverityDropList();
		JSONArray servityArr = servityResult.getJSONArray("data");
		for (int i=0; i<servityArr.length(); i++){
			JSONObject tempObj = servityArr.getJSONObject(i);
			if (Severity.compareToIgnoreCase(tempObj.getString("chineseName"))==0){
				alertObj.put("severity", String.valueOf(tempObj.getInt("severity")));
				break;
			}
		}
		alertObj.put("lastOccurrence", System.currentTimeMillis());
		alertObj.put("status",status);
		alertObj.put("summary", summary);
		alertObj.put("sourceID", 10);
		alertObj.put("sourceEventID", String.valueOf(System.currentTimeMillis()));
		alertObj.put("sourceSeverity", "1");
		paramArr.put(alertObj);
		JSONObject param = new JSONObject();
		QueryDefaultUrl qdu = new QueryDefaultUrl();
		JSONObject resultObj = qdu.queryDefaultUrl();
		assertNotNull(resultObj.getString("data"));
		String pushUrl =resultObj.getString("data");
		param.put("url", pushUrl);
		param.put("events", paramArr);
		return doRest(url, param.toString(), "POST");
	}
}
