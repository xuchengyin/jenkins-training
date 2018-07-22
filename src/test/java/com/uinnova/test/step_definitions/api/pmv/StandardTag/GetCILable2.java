package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn 
 * 查询监控对象lable
 */
public class GetCILable2 extends RestApi {

	public JSONArray getCILable2(String ID) {
		String url = ":1518/pmv-web/sTag/getConfigCIClassNames";
		JSONObject result = doRest(url, ID, "POST");
		JSONArray ClassName = result.getJSONArray("data");
		return ClassName;
	}

}
