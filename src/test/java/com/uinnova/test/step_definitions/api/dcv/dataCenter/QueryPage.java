package com.uinnova.test.step_definitions.api.dcv.dataCenter;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 
 * @author wjx
 * 数据中心列表查询
 */
public class QueryPage extends RestApi{
	public JSONObject queryPage(){
		String url = "1511/tarsier-vmdb/dcv/dataCenter/queryPage";
		return doRest(url, null, "POST");
	}
}
