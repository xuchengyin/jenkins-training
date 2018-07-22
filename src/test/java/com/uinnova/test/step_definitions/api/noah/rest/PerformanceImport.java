package com.uinnova.test.step_definitions.api.noah.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

import cucumber.api.DataTable;

/**
 * @author wsl
 * NOAH推送ci性能数据
 *
 */
public class PerformanceImport extends RestApi{
	public JSONObject performanceImport(String ciName, String kpiName, String kpiDesc, String kpiValue){
		List<Map<String, Object>> pbs = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JdbcUtil ju = new JdbcUtil();
		String sql = "DELETE FROM performance where ci_name = '"+ciName+"'";
		ju.executeUpdate(sql);
		map.put("ci", ciName);
		map.put("kpi", kpiName);
		map.put("value",  kpiValue);
		map.put("desc", kpiDesc);
		map.put("time", System.currentTimeMillis());
		pbs.add(map);
		String url = ":1532/rest/tarsier/rest/performance/import";
		return doRest(url,pbs.toString(), "POST");

	}
	
	public JSONObject performanceImport(DataTable dt){
		
		List<Map<String,Object>> pbs = new ArrayList<Map<String,Object>>();
		List<List<String>> list = dt.raw();
		JdbcUtil ju = new JdbcUtil();
		String sql = "DELETE FROM performance where ci_name = '"+list.get(1).get(1)+"'";
		ju.executeUpdate(sql);
		for(int i = 1; i < list.size(); i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ci", list.get(i).get(1));
			map.put("kpi", list.get(i).get(2));
			map.put("value", list.get(i).get(4).substring(0, list.get(i).get(4).length()-1));
			map.put("desc", list.get(i).get(3));
			map.put("time", System.currentTimeMillis());
			map.put("unit", list.get(i).get(4).substring(list.get(i).get(4).length()-1, list.get(i).get(4).length()));
			pbs.add(map);
		}
		String url = ":1532/rest/tarsier/rest/performance/import";
		return doRest(url,pbs.toString(),"POST");		
	}
}
