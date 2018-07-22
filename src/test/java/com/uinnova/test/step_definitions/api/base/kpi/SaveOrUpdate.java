package com.uinnova.test.step_definitions.api.base.kpi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.search.ci.QuerySeeSearchKindList;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

import cucumber.api.DataTable;

public class SaveOrUpdate extends RestApi{

	/**
	 * @param kpiCode
	 * @param kpiName
	 * @param kpiDesc
	 * @param unitName
	 * @return
	 */
	public JSONObject saveOrUpdate(String kpiCode, String kpiName, String kpiDesc, String unitName,String classGroups, String tagGroups, String rltGroups, DataTable table) {
		String url = ":1511/tarsier-vmdb/cmv/kpi/saveOrUpdate";
		JSONObject kpi = new JSONObject();
		JSONArray kpiCiGroups = new JSONArray();
		kpi.put("kpiName", kpiName);
		kpi.put("kpiCode", kpiCode);
		kpi.put("kpiDesc", kpiDesc);
		kpi.put("unitName", unitName);
		if(table!=null){
			int rows = table.raw().size();
			JSONArray kpiExtPros = new JSONArray();
			ImageUtil iUtil = new ImageUtil();
			for (int i=1;i<rows;i++){
				JSONObject kpiExtProsObj = new JSONObject();
				List<String> row = table.raw().get(i);
				kpiExtProsObj.put("val", row.get(0));
				kpiExtProsObj.put("img", iUtil.getImageUrl(row.get(2)));
				kpiExtPros.put(kpiExtProsObj);
			}
			kpi.put("kpiExtPros", kpiExtPros.toString());
		}

		if (!classGroups.isEmpty()){
			String[] classArr = classGroups.split("、");
			for (int i=0; i<classArr.length; i++){
				String clsName = classArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				kpiCiGroup.put("objGroupType", "2");
				kpiCiGroup.put("objGroupId", String.valueOf(new CiClassUtil().getClassIdByClassName(clsName,new BigDecimal(1))));
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		if (!tagGroups.isEmpty()){
			String[] tagArr = tagGroups.split("、");
			for (int i=0; i<tagArr.length; i++){
				String tagName = tagArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				kpiCiGroup.put("objGroupType", "1");
				kpiCiGroup.put("objGroupId", String.valueOf(new TagRuleUtil().getTagId(tagName)));
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		if (!rltGroups.isEmpty()){
			String[] rltArr = rltGroups.split("、");
			for (int i=0; i<rltArr.length; i++){
				String rltName = rltArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				kpiCiGroup.put("objGroupType", "3");
				kpiCiGroup.put("objGroupId", String.valueOf(new RltClassUtil().getRltClassId(rltName)));
				kpiCiGroups.put(kpiCiGroup);
			}
		}

		JSONObject param = new JSONObject();
		param.put("kpi", kpi);
		param.put("kpiCiGroups", kpiCiGroups);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param kpiCode
	 * @param updateKpiCode
	 * @param updateKpiName
	 * @param updateKpiDesc
	 * @param updateUnitName
	 * @return
	 */
	public JSONObject update(String kpiCode, String updateKpiCode, String updateKpiName, String updateKpiDesc,
			String updateUnitName, String classGroups, String tagGroups, String rltGroups, DataTable table) {
		int j = 0;
		String url = ":1511/tarsier-vmdb/cmv/kpi/saveOrUpdate";
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		JSONObject kpiResult = qkib.queryKpiInfoById(kpiCode);
		QuerySeeSearchKindList qskl = new QuerySeeSearchKindList();
		JSONObject qsklResult = qskl.querySeeSearchKindList();
		JSONArray data = qsklResult.getJSONArray("data");
		JSONObject kpi = kpiResult.getJSONObject("data").getJSONObject("kpi");

		if (updateKpiCode!=null)
			kpi.put("kpiCode", updateKpiCode);
		if (updateKpiDesc!=null)
			kpi.put("kpiDesc", updateKpiDesc);
		if (updateKpiName!=null)
			kpi.put("kpiName", updateKpiName);	
		if (updateKpiCode!=null)
			kpi.put("unitName", updateUnitName);
		JSONObject param = new JSONObject();
		JSONArray kpiExtPros = new JSONArray();
		if(table!=null){
			int rows = table.raw().size();
			ImageUtil iUtil = new ImageUtil();
			for (int i=1;i<rows;i++){
				JSONObject kpiExtProsObj = new JSONObject();
				List<String> row = table.raw().get(i);
				kpiExtProsObj.put("val", row.get(0));
				kpiExtProsObj.put("img", iUtil.getImageUrl(row.get(2)));
				kpiExtPros.put(kpiExtProsObj);
			}

		}
		kpi.put("kpiExtPros", kpiExtPros.toString());
		param.put("kpi", kpi);

		JSONArray kpiCiGroups = new JSONArray();
		JSONArray oldKpiCiGroups = new JSONArray();
		if (kpiResult.getJSONObject("data").has("kpiCiGroups")){
			oldKpiCiGroups = kpiResult.getJSONObject("data").getJSONArray("kpiCiGroups");
		}
		Map<BigDecimal, JSONObject> map = new HashMap<BigDecimal, JSONObject>();
		for (int k=0; k<oldKpiCiGroups.length(); k++){
			JSONObject obj = oldKpiCiGroups.getJSONObject(k);
			map.put(obj.getBigDecimal("objGroupId"), obj);
		}

		if (classGroups!=null && !classGroups.isEmpty()){
			String[] classArr = classGroups.split("、");
			for (int i=0; i<classArr.length; i++){
				String clsName = classArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new CiClassUtil().getClassIdByClassName(clsName,new BigDecimal(1));
				if (map.containsKey(objGroupId)){
					kpiCiGroup = map.get(objGroupId);

				}else{
					kpiCiGroup.put("objGroupType", Contants.KPICIGROUPS_GROUPTYPE_CLASSGROUPS);
					kpiCiGroup.put("objGroupId", String.valueOf(objGroupId));
				}
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		if (tagGroups!=null && !tagGroups.isEmpty()){
			String[] tagArr = tagGroups.split("、");
			for (int i=0; i<tagArr.length; i++){
				String tagName = tagArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new TagRuleUtil().getTagId(tagName);
				if (map.containsKey(objGroupId)){
					kpiCiGroup = map.get(objGroupId);
				}else{
					kpiCiGroup.put("objGroupType", Contants.KPICIGROUPS_GROUPTYPE_TAGGROUPS);
					kpiCiGroup.put("objGroupId", String.valueOf(objGroupId));
				}
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		if (rltGroups!=null && !rltGroups.isEmpty()){
			String[] rltArr = rltGroups.split("、");
			for (int i=0; i<rltArr.length; i++){
				String rltName = rltArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new RltClassUtil().getRltClassId(rltName);
				if (map.containsKey(objGroupId)){
					kpiCiGroup = map.get(objGroupId);
				}else{
					kpiCiGroup.put("objGroupType", Contants.KPICIGROUPS_GROUPTYPE_RLTGROUPS);
					kpiCiGroup.put("objGroupId", String.valueOf(objGroupId));
				}
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		param.put("kpiCiGroups", kpiCiGroups);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param kpiCode
	 * @param classGroups
	 * @param tagGroups
	 * @param rltGroups
	 * @return
	 *//*
	public JSONObject updateCiGroups(String kpiCode, String classGroups, String tagGroups, String rltGroups) {
		int j = 0;
		String url = ":1511/tarsier-vmdb/cmv/kpi/saveOrUpdate";
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		JSONObject kpiResult = qkib.queryKpiInfoById(kpiCode);
		QuerySearchKindList qskl = new QuerySearchKindList();
		JSONObject qsklResult = qskl.querySearchKindList();
		JSONArray data = qsklResult.getJSONArray("data");
		JSONObject kpi = kpiResult.getJSONObject("data").getJSONObject("kpi");
		JSONArray kpiCiGroups = new JSONArray();
		JSONArray oldKpiCiGroups = new JSONArray();
		if (kpiResult.getJSONObject("data").has("kpiCiGroups")){
			oldKpiCiGroups = kpiResult.getJSONObject("data").getJSONArray("kpiCiGroups");
		}
		Map<BigDecimal, JSONObject> map = new HashMap<BigDecimal, JSONObject>();
		for (int k=0; k<oldKpiCiGroups.length(); k++){
			JSONObject obj = oldKpiCiGroups.getJSONObject(k);
			map.put(obj.getBigDecimal("objGroupId"), obj);
		}

		if (!classGroups.isEmpty()){
			String[] classArr = classGroups.split("、");
			for (int i=0; i<classArr.length; i++){
				String clsName = classArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new CiClassUtil().getClassIdByClassName(clsName,new BigDecimal(1));
				if (map.containsKey(objGroupId)){
					kpiCiGroup = map.get(objGroupId);

				}else{
					kpiCiGroup.put("objGroupType", Contants.KPICIGROUPS_GROUPTYPE_CLASSGROUPS);
					kpiCiGroup.put("objGroupId", String.valueOf(objGroupId));
				}
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		if (!tagGroups.isEmpty()){
			String[] tagArr = tagGroups.split("、");
			for (int i=0; i<tagArr.length; i++){
				String tagName = tagArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new TagRuleUtil().getTagId(tagName);
				if (map.containsKey(objGroupId)){
					kpiCiGroup = map.get(objGroupId);
				}else{
					kpiCiGroup.put("objGroupType", Contants.KPICIGROUPS_GROUPTYPE_TAGGROUPS);
					kpiCiGroup.put("objGroupId", String.valueOf(objGroupId));
				}
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		if (!rltGroups.isEmpty()){
			String[] rltArr = rltGroups.split("、");
			for (int i=0; i<rltArr.length; i++){
				String rltName = rltArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new RltClassUtil().getRltClassId(rltName);
				if (map.containsKey(objGroupId)){
					kpiCiGroup = map.get(objGroupId);
				}else{
					kpiCiGroup.put("objGroupType", Contants.KPICIGROUPS_GROUPTYPE_RLTGROUPS);
					kpiCiGroup.put("objGroupId", String.valueOf(objGroupId));
				}
				kpiCiGroups.put(kpiCiGroup);
			}
		}
		JSONObject param = new JSONObject();
		param.put("kpi", kpi);
		param.put("kpiCiGroups", kpiCiGroups);
		return doRest(url, param.toString(), "POST");
	}*/
}
