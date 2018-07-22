package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 保存或者更新数据质量仪表盘信息
 * @author wsl
 *
 */
public class SaveOrUpdateDashboard extends RestApi{

	/**
	 * @param dashboardName
	 * @return
	 */
	public JSONObject saveOrUpdateDashboard(String dashboardName){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateDashboard";
		JSONObject param = new JSONObject();
		param.put("dashboardType", 1);
		param.put("dashboardName", dashboardName);
		return doRest(url, param.toString(), "POST");
	}



	/**
	 * 创建重名的仪表盘
	 * @param dashboardName
	 * @return
	 */
	public JSONObject saveOrUpdateDashboardAgain(String dashboardName, String kw){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateDashboard";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("dashboardType", 1);
		cdt.put("dashboardName", dashboardName);
		return doFailRest(url, param.toString(), "POST", kw);
	}
	
	
	/**
	 * 修改仪表盘
	 * @param dashboardName
	 * @param newdashboardName
	 * @return
	 */
	public JSONObject updateDashboard(String dashboardName, String newdashboardName){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateDashboard";
		JSONObject param = new JSONObject();
		QueryDashboardById query = new QueryDashboardById();
		JSONObject result = query.queryDashboardById(dashboardName);
		if (result.has("data")){
			JSONObject data = result.getJSONObject("data");
			BigDecimal dashboardId = new BigDecimal(0);
			if (data!=null && data.has("id")){
				dashboardId = data.getBigDecimal("id");
				param.put("id", dashboardId);
			}
		}
		param.put("dashboardType", 1);
		param.put("dashboardName", newdashboardName);
		return doRest(url, param.toString(), "POST");
	}

}
