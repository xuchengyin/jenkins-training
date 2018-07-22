package com.uinnova.test.step_definitions.utils.base;

import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.uinnova.test.step_definitions.api.base.sys.user.QueryInfoPage;

/**
 * 编写时间:2017-12-18
 * 编写人:sunsl
 * 功能介绍:根据登录名取得UserID
 */
public class UserUtil {
	/**
	 * 根据用户名称获取用户ID
	 * @param opCode
	 * @return
	 */
	public String getUserId(String opCode){
		QueryInfoPage qi = new QueryInfoPage();
		JSONObject result = qi.queryInfoPage(opCode);
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		assertEquals(1, data.length());
		return data.getJSONObject(0).getJSONObject("op").getBigDecimal("id").toString();
		
	}
}
