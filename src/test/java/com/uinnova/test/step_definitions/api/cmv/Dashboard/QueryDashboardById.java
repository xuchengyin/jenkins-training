package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.DashboardUtil;

/**
 * 根据主键查询数据质量仪表盘信息
 * @author wsl
 *
 */
public class QueryDashboardById extends RestApi{
	
	public JSONObject queryDashboardById(String dashboardName){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/queryDashboardById";
		return doRest(url, String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)), "POST");
	}

}
