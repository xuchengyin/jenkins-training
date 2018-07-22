package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.cmv.DashboardUtil;

/**
 * 根据主键删除指定图表信息
 * @author wsl
 *
 */
public class RemoveQualityChartById extends RestApi{
	
	/**
	 * 根据仪表盘名称和图标标题删除执行图表信息
	 * @param dashboardName
	 * @param chartTitle
	 * @return
	 */
	public JSONObject removeQualityChartById(String dashboardName, String chartTitle ){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/removeQualityChartById";
		return doRest(url, String.valueOf(DashboardUtil.getChartIDByTitle(dashboardName, chartTitle)), "POST");
	}

}
