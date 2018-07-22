package com.uinnova.test.step_definitions.utils.common;


import org.json.JSONObject;

public class AssertResult {

	/**
	 * 处理接口返回的字符串，接口调用成功返回接口返回的json串，调用失败返回{"result":${返回内容}}
	 * @param result
	 * @return
	 */
	public JSONObject assertRes(String result) {
		if (result.startsWith("{")) {
			return new JSONObject(result);
		} else {
			JSONObject resuleObj = new JSONObject();
			resuleObj.put("result", result);
			return resuleObj;
		}
	}

}
