package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 分页查询数据质量仪表盘信息
 * @author wsl
 *
 */
public class QueryDashBoardPage extends RestApi{
	
	/**
	 * @return
	 */
	public JSONObject queryDashBoardPage(String dashboardName, int pageNum, int pageSize){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/queryDashBoardPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("dashboardType", 1);
		cdt.put("status", 1);
		cdt.put("dashboardName", "%"+dashboardName+"%");
		param.put("cdt", cdt);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		return doRest(url, param.toString(), "POST");
	}

}
