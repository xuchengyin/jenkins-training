package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.DashboardUtil;

public class RemoveDashBoardById extends RestApi{
	
	public JSONObject removeDashBoardById(String dashboardName ){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/removeDashBoardById";
		return doRest(url, String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)), "POST");
	}

}
