package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.DashboardUtil;

/**
 * 不分页查询指定仪表盘下的图表信息
 * @author wsl
 *
 */
public class QueryQualityChartInfoList extends RestApi{
	
	/**
	 * @param dashboardName
	 * @return
	 */
	public JSONObject queryQualityChartInfoList(String dashboardName){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/queryQualityChartInfoList";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("dashBoardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		param.put("cdt", cdt);
		param.put("orders", "DASHBOARD_ROW_NUM,DASHBOARD_COL_NUM");
		return doRest(url, param.toString(), "POST");
	}
}
