package com.uinnova.test.step_definitions.api.ITV.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wjx
 * @param ids  组合视图中视图ID       
 * @请求方式 post
 * 功能介绍：获取CI节点信息
 */
public class GetViewPointsForDcvByIds extends RestApi {
	public JSONObject getViewInformation(ArrayList viewID) {
		
		String url = ":1511/tarsier-vmdb/dcv/vcDiagram/view/getViewPointsForDcvByIds";

		JSONObject ids = new JSONObject();
		JSONArray view = new JSONArray();

		for (int i = 0; i < viewID.size(); i++) {
			Object id = viewID.get(i);
			view.put(id);
		}
		ids.put("ids", view);

		return doRest(url, ids.toString(), "POST");
	}
	
}
