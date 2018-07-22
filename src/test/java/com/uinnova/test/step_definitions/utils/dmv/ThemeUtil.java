package com.uinnova.test.step_definitions.utils.dmv;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ciClass.QueryList;
import com.uinnova.test.step_definitions.api.dmv.theme.QueryKpiInfoByClassId;
import com.uinnova.test.step_definitions.api.dmv.theme.QueryKpiList;
import com.uinnova.test.step_definitions.api.dmv.theme.SaveOrUpdateThemeField;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import junit.framework.Assert;

/**
 * 编写时间:2017-12-04
 * 编写人:sunsl
 * 功能介绍:DMV设置的工具类
 */
public class ThemeUtil {
	public BigDecimal getClassId(String className){
		BigDecimal classId= new BigDecimal(0);
		// 取得CI分类
		QueryList queryList = new QueryList();
		JSONObject result = queryList.queryList(1);
		JSONArray data = result.getJSONArray("data");
		// 取得CI分类ID
		for (int i = 0; i < data.length(); i++) {
			JSONObject obj = (JSONObject) data.get(i);
			JSONObject ciClass = obj.getJSONObject("ciClass");
			if (ciClass.getString("className").equals(className)) {
				classId = ciClass.getBigDecimal("id");
				break;
			}
		} 
		return classId;
	}
	public BigDecimal getRltClassId(String rltClassName){
		QueryList ql = new QueryList();
		BigDecimal classId = new BigDecimal(0);
		JSONObject result = ql.queryList(2);
		JSONArray data = result.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			JSONObject themeRltKpiObj = (JSONObject) data.get(i);
			JSONObject ciClass = themeRltKpiObj.getJSONObject("ciClass");
			if (ciClass.getString("className").equals(rltClassName)) {
				classId = ciClass.getBigDecimal("id");
				break;
			}
		}

		return classId;
	}
	/**
	 * 获得动态链接
	 */
	public HashMap getPluginAttr(String proName){
		String sql = "SELECT ID,CLASS_ID,PRO_NAME,PRO_VAL_1,PRO_VAL_2 FROM cc_ci_plugin_attr WHERE PRO_NAME = '" + proName 
				+ "' AND DATA_STATUS = 1 and DOMAIN_ID=" + QaUtil.domain_id;
		List pluginList = JdbcUtil.executeQuery(sql);
		HashMap pluginHashMap = new HashMap();
		if (pluginList!=null && pluginList.size()>0){
			pluginHashMap = (HashMap)pluginList.get(0);
		}
		return pluginHashMap;
	}
	/**
	 * @param className 
	 */
	public HashMap addTheme(String className){
		QueryList queryList = new QueryList();
		HashMap retHashMap = new HashMap();
		JSONObject result = queryList.queryList(1);
		BigDecimal classId=new BigDecimal(0);
		List themeFieldList = new ArrayList();
		JSONArray themeFields = new JSONArray();
		JSONArray data = result.getJSONArray("data");
		boolean flag = false;
		for (int i = 0; i < data.length(); i++) {
			JSONObject obj = (JSONObject) data.get(i);
			JSONObject ciClass = obj.getJSONObject("ciClass");
			if (ciClass.getString("className").equals(className)) {
				classId = ciClass.getBigDecimal("id");
				JSONArray attrDefs = obj.getJSONArray("attrDefs");
				for (int j = 0; j < attrDefs.length(); j++) {
					JSONObject attrDef = attrDefs.getJSONObject(j);
					JSONObject themeField = new JSONObject();
					themeField.put("fieldId", attrDef.getBigDecimal("id"));
					themeField.put("fieldName", attrDef.getString("proName"));
					themeFields.put(themeField);
					themeFieldList.add(themeField);
					if (j == 4) {
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
			}
		}
		retHashMap.put("classId", classId);
		retHashMap.put("themeFields", themeFields);
		retHashMap.put("themeFieldList", themeFieldList);
		return retHashMap;
	}

	public JSONArray updateAttrDefs(String className,List themeFieldList){
		QueryList queryList = new QueryList();
		JSONObject result = queryList.queryList(1,className);
		JSONArray data = result.getJSONArray("data");
		JSONObject obj = (JSONObject) data.get(0);
		JSONArray attrDefs = obj.getJSONArray("attrDefs");
		JSONObject themeObj = (JSONObject) themeFieldList.get(0);
		for (int i = 0; i < attrDefs.length(); i++) {
			JSONObject attrDedsObj = (JSONObject) attrDefs.get(i);
			if (themeObj.getString("fieldName").equals(attrDedsObj.getString("proName"))) {
				attrDedsObj.put("orderNo", 1);
				break;
			}
		}
		return attrDefs;
	}
	public HashMap updateTheme(String className){
		QueryList queryList = new QueryList();
		JSONObject result = queryList.queryList(1,className);
		JSONArray data = result.getJSONArray("data");
		JSONObject obj = (JSONObject) data.get(0);
		JSONArray attrDefs = obj.getJSONArray("attrDefs");
		HashMap  retHashMap = new HashMap();
		JSONArray themeFields = new JSONArray();
		List themeFieldList = new ArrayList();
		for (int j = 4; j < attrDefs.length(); j++) {
			JSONObject attrDef = (JSONObject) attrDefs.get(j);
			JSONObject themeField = new JSONObject();
			themeField.put("fieldId", attrDef.getBigDecimal("id"));
			themeField.put("fieldName", attrDef.getString("proName"));
			themeFields.put(themeField);
			themeFieldList.add(themeField);
			if (j == 6) {
				break;
			}
		}
		retHashMap.put("themeFields", themeFields);
		retHashMap.put("themeFieldList", themeFieldList);
		return retHashMap;
	}

	public HashMap createThemeKpi(String className){
		HashMap retHashMap = new HashMap();
		HashMap themeFieldHashMap = new HashMap();
		BigDecimal classId = getClassId(className);
		// 取得CI分类的指标
		QueryKpiInfoByClassId qkib = new QueryKpiInfoByClassId();
		JSONObject result = qkib.queryKpiInfoByClassId(classId,2);
		JSONObject qkData = result.getJSONObject("data");
		JSONArray kpis = qkData.getJSONArray("kpis");
		JSONObject kpi = (JSONObject) kpis.get(0);
		JSONArray themeFields = new JSONArray();
		JSONObject themeField = new JSONObject();
		themeField.put("fieldId", kpi.getBigDecimal("id"));
		themeField.put("fieldName", kpi.getString("kpiCode"));
		themeFieldHashMap.put("fieldId", kpi.getBigDecimal("id"));
		themeFieldHashMap.put("fieldName", kpi.getString("kpiCode"));
		themeFields.put(themeField);
		retHashMap.put("themeFieldHashMap", themeFieldHashMap);
		retHashMap.put("themeFields", themeFields);
		return retHashMap;
	}

	/**
	 * 创建关系指标设置
	 * @param kpiCOde
	 * @param rltClassName
	 * @return result
	 */
	public JSONObject createThemeRltKpi(String kpiCOde,String rltClassName){
		JSONObject result;
		SaveOrUpdateThemeField su = new SaveOrUpdateThemeField();
		QueryKpiList queryKpiList = new QueryKpiList();
		result = queryKpiList.queryKpiList();
		JSONArray data = result.getJSONArray("data");
		JSONArray themeFields = new JSONArray();
		JSONObject themeField = new JSONObject();
		for(int i = 0 ; i < data.length(); i++){
			JSONObject obj = (JSONObject)data.get(i);
			if(obj.getString("kpiCode").equals(kpiCOde)){
				String kpiExtPros = obj.getString("kpiExtPros");
				if(kpiExtPros.contains("数据库图标")){
					assertTrue(true);
				}
				themeField.put("fieldId", obj.getBigDecimal("id"));
				themeField.put("fieldName", obj.getString("kpiCode"));
				themeFields.put(themeField);
				break;
			}
		}

		result = su.saveOrUpdateThemeField(2,2,getRltClassId(rltClassName),themeFields);
		return result;
	}
}