package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author kyn 
 * 查询对象分类
 */
public class GetClassTree extends RestApi {

	public JSONObject getClassTree(String className) {
		String url = ":1518/pmv-web/integration/getClassTree";
		JSONObject param = new JSONObject();
		if (!className.isEmpty()) {
			param.put("className", className);
		}
		JSONObject result = doRest(url, param.toString(), "POST");

		return result;

	}

}
