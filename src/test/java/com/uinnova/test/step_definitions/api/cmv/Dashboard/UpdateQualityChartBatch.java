package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.DashboardUtil;

/**
 * 批量更新图表信息(拖动和换位置)
 * @author wsl
 *
 */
public class UpdateQualityChartBatch extends RestApi {
	
	public JSONObject updateQualityChartBatch(String dashboardName){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/updateQualityChartBatch";
		JSONObject param = new JSONObject();
		param.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		return doRest(url, param.toString(), "POST");
	}
	
	/*
	 * 
	 {
	"dashboardId": "100000000000011",
	"charts": [{
		"id": 100000000000001,
		"dashboardRowNum": 0,
		"dashboardColNum": 0,
		"dashboardWidth": 50,
		"dashboardHeight": 270
	}, {
		"id": 100000000000004,
		"dashboardRowNum": 0,
		"dashboardColNum": 1,
		"dashboardWidth": 50,
		"dashboardHeight": 270
	}, {
		"id": 100000000000002,
		"dashboardRowNum": 1,
		"dashboardColNum": 0,
		"dashboardWidth": 50,
		"dashboardHeight": 270
	}, {
		"id": 100000000000003,
		"dashboardRowNum": 1,
		"dashboardColNum": 1,
		"dashboardWidth": 50,
		"dashboardHeight": 270
	}, {
		"id": 100000000000005,
		"dashboardRowNum": 2,
		"dashboardColNum": 0,
		"dashboardWidth": 100,
		"dashboardHeight": 270
	}]
}
	 * */

}
