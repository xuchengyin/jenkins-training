package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import org.json.JSONArray;
import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn 	
 * 查询配置的对象分类
 */
public class GetConfigCIClassNames extends RestApi {

	public JSONArray getConfigCIClassNames() {
		String url = ":1518/pmv-web/sTag/getConfigCIClassNames";
		JSONObject result = doRest(url, "{}", "POST");
		JSONArray ClassName = result.getJSONArray("data");
		return ClassName;
	}

}
