package com.uinnova.test.step_definitions.api.pmv.Event;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author 
 * 当前告警count
 */
public class QueryEventCount extends RestApi {
	public int queryEventCount(String param) {
		String url = ":1518/pmv-web/event/queryEventCount";
		int result = doRest(url, param.toString(), "POST").getInt("data");
		return result;

	}
}
