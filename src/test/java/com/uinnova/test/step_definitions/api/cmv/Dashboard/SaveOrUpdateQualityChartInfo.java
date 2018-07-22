package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.cmv.ciQualityRule.QueryCiQualityChartDataInfoByRuleId;
import com.uinnova.test.step_definitions.utils.cmv.DashboardUtil;
import com.uinnova.test.step_definitions.utils.cmv.ciQualityRule.CiQualityRuleUtil;

/**
 * 保存或者更新数据质量仪表盘下图表信息
 * @author wsl
 *
 */
public class SaveOrUpdateQualityChartInfo extends RestApi {

	/**
	 * 给仪表盘创建时间序列图标
	 * @param dashboardName
	 * @param title
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public JSONObject saveOrUpdateQualityChartInfo(String dashboardName, String title, String ruleTypeName, String ruleName){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateQualityChartInfo";
		JSONObject chart = new JSONObject();
		chart.put("chartType", "1");
		chart.put("dashboardRowNum", 0);
		chart.put("dashboardColNum", 0);
		chart.put("dashboardWidth", 100);
		chart.put("dashboardHeight", 270);
		chart.put("ruleId", String.valueOf(CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName)));
		chart.put("title", title);		
		JSONObject param = new JSONObject();
		param.put("chart", chart);
		param.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		JSONArray chartSeries = new JSONArray();
		QueryCiQualityChartDataInfoByRuleId queryCiQualityChartDataInfoByRuleId = new QueryCiQualityChartDataInfoByRuleId();
		JSONObject ChartDataInfo = queryCiQualityChartDataInfoByRuleId.queryCiQualityChartDataInfoByRuleId(ruleTypeName, ruleName);
		ChartDataInfo = ChartDataInfo.getJSONObject("data");
		if (ChartDataInfo.has("series")){
			JSONArray seriesArr = ChartDataInfo.getJSONArray("series");
			for (int i=0; i<seriesArr.length(); i++){
				JSONObject chartSeriesObj = new JSONObject();
				chartSeriesObj.put("classId", seriesArr.getJSONObject(i).get("classId"));
				chartSeriesObj.put("seriesColor", "经典");
				chartSeriesObj.put("seriesType", 1);
				chartSeries.put(chartSeriesObj);
			}
		}
		param.put("chartSeries", chartSeries);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * 再次创建同名的图表
	 * @param dashboardName
	 * @param title
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public JSONObject saveOrUpdateQualityChartInfoAgain(String dashboardName, String title, String ruleTypeName, String ruleName, String kw){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateQualityChartInfo";
		JSONObject chart = new JSONObject();
		chart.put("chartType", "1");
		chart.put("dashboardRowNum", 0);
		chart.put("dashboardColNum", 0);
		chart.put("dashboardWidth", 100);
		chart.put("dashboardHeight", 270);
		chart.put("ruleId", String.valueOf(CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName)));
		chart.put("title", title);		
		JSONObject param = new JSONObject();
		param.put("chart", chart);
		param.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		QueryCiQualityChartDataInfoByRuleId queryCiQualityChartDataInfoByRuleId = new QueryCiQualityChartDataInfoByRuleId();
		JSONObject ChartDataInfo = queryCiQualityChartDataInfoByRuleId.queryCiQualityChartDataInfoByRuleId(ruleTypeName, ruleName);
		ChartDataInfo = ChartDataInfo.getJSONObject("data");
		JSONArray chartSeries = new JSONArray();
		if (ChartDataInfo.has("series")){
			JSONArray seriesArr = ChartDataInfo.getJSONArray("series");
			for (int i=0; i<seriesArr.length(); i++){
				JSONObject chartSeriesObj = new JSONObject();
				chartSeriesObj.put("classId", seriesArr.getJSONObject(i).get("classId"));
				chartSeriesObj.put("seriesColor", "经典");
				chartSeriesObj.put("seriesType", 1);
				chartSeries.put(chartSeriesObj);
			}
		}
		param.put("chartSeries", chartSeries);
		return doFailRest(url, param.toString(), "POST", kw);
	}

	/**
	 * 修改图表
	 * @param dashboardName
	 * @param newDashboardName
	 * @param title
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public JSONObject updateQualityChartInfo(String dashboardName, String title, String newTitle, String ruleTypeName, String ruleName){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateQualityChartInfo";
		JSONObject chart = new JSONObject();
		chart.put("chartType", "1");
		chart.put("dashboardRowNum", 0);
		chart.put("dashboardColNum", 0);
		chart.put("dashboardWidth", 100);
		chart.put("dashboardHeight", 270);
		chart.put("id", DashboardUtil.getChartIDByTitle(dashboardName, title));
		chart.put("ruleId", String.valueOf(CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName)));
		chart.put("title", newTitle);	
		chart.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		JSONObject param = new JSONObject();
		param.put("chart", chart);
		param.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		JSONArray chartSeries = new JSONArray();
		QueryCiQualityChartDataInfoByRuleId queryCiQualityChartDataInfoByRuleId = new QueryCiQualityChartDataInfoByRuleId();
		JSONObject ChartDataInfo = queryCiQualityChartDataInfoByRuleId.queryCiQualityChartDataInfoByRuleId(ruleTypeName, ruleName);
		ChartDataInfo = ChartDataInfo.getJSONObject("data");
		if (ChartDataInfo.has("series")){
			JSONArray seriesArr = ChartDataInfo.getJSONArray("series");
			for (int i=0; i<seriesArr.length(); i++){
				JSONObject chartSeriesObj = new JSONObject();
				chartSeriesObj.put("classId", seriesArr.getJSONObject(i).get("classId"));
				chartSeriesObj.put("seriesColor", "经典");
				chartSeriesObj.put("seriesType", 1);
				chartSeries.put(chartSeriesObj);
			}
		}
		param.put("chartSeries", chartSeries);
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * 给仪表盘创建当前时间图标
	 * @param dashboardName
	 * @param title
	 * @param ruleTypeName
	 * @param ruleName
	 * @param thresholdFloorValue
	 * @param thresholdUpperValue
	 * @return
	 */
	public JSONObject saveOrUpdateQualityChartInfo(String dashboardName, String title, String ruleTypeName, 
			String ruleName, String thresholdFloorValue, String thresholdUpperValue){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateQualityChartInfo";
		JSONObject chart = new JSONObject();
		chart.put("chartType", "2");
		chart.put("dashboardRowNum", 0);
		chart.put("dashboardColNum", 0);
		chart.put("dashboardWidth", 100);
		chart.put("dashboardHeight", 270);
		chart.put("ruleId", String.valueOf(CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName)));
		chart.put("title", title);		
		chart.put("thresholdFloorValue", thresholdFloorValue);	
		chart.put("thresholdUpperValue", thresholdUpperValue);
		chart.put("thresholdUpperStyle", "#3bbc7b");	
		chart.put("thresholdFloorStyle", "#eb4949");	
		chart.put("thresholdMiddleStyle", "#ffe63b");	
		JSONObject param = new JSONObject();
		param.put("chart", chart);
		param.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * 再次新建重名的当前值类型图表
	 * @param dashboardName
	 * @param title
	 * @param ruleTypeName
	 * @param ruleName
	 * @param thresholdFloorValue
	 * @param thresholdUpperValue
	 * @return
	 */
	public JSONObject saveOrUpdateQualityChartInfoAgain(String dashboardName, String title, String ruleTypeName, 
			String ruleName, String thresholdFloorValue, String thresholdUpperValue, String kw){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateQualityChartInfo";
		JSONObject chart = new JSONObject();
		chart.put("chartType", "2");
		chart.put("dashboardRowNum", 0);
		chart.put("dashboardColNum", 0);
		chart.put("dashboardWidth", 100);
		chart.put("dashboardHeight", 270);
		chart.put("ruleId", String.valueOf(CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName)));
		chart.put("title", title);		
		chart.put("thresholdFloorValue", thresholdFloorValue);	
		chart.put("thresholdUpperValue", thresholdUpperValue);
		chart.put("thresholdUpperStyle", "#3bbc7b");	
		chart.put("thresholdFloorStyle", "#eb4949");	
		chart.put("thresholdMiddleStyle", "#ffe63b");	
		JSONObject param = new JSONObject();
		param.put("chart", chart);
		param.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		return doFailRest(url, param.toString(), "POST", kw);
	}

	/**
	 * @param dashboardName
	 * @param title
	 * @param newTitle
	 * @param ruleTypeName
	 * @param ruleName
	 * @param thresholdFloorValue
	 * @param thresholdUpperValue
	 * @return
	 */
	public JSONObject updateQualityChartInfo(String dashboardName, String title, String newTitle,String ruleTypeName, 
			String ruleName, String thresholdFloorValue, String thresholdUpperValue){
		String url = ":1511/tarsier-vmdb/cmv/Dashboard/saveOrUpdateQualityChartInfo";
		JSONObject chart = new JSONObject();
		chart.put("chartType", "2");
		chart.put("dashboardRowNum", 0);
		chart.put("dashboardColNum", 0);
		chart.put("dashboardWidth", 100);
		chart.put("dashboardHeight", 270);
		chart.put("id", DashboardUtil.getChartIDByTitle(dashboardName, title));
		chart.put("ruleId", String.valueOf(CiQualityRuleUtil.getIDByNameAndRuleType(ruleTypeName, ruleName)));
		chart.put("title", newTitle);		
		chart.put("thresholdFloorValue", thresholdFloorValue);	
		chart.put("thresholdUpperValue", thresholdUpperValue);
		chart.put("thresholdUpperStyle", "#3bbc7b");	
		chart.put("thresholdFloorStyle", "#eb4949");	
		chart.put("thresholdMiddleStyle", "#ffe63b");
		chart.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		JSONObject param = new JSONObject();
		param.put("chart", chart);
		param.put("dashboardId", String.valueOf(DashboardUtil.getDashbordIDByName(dashboardName)));
		return doRest(url, param.toString(), "POST");
	}

}
