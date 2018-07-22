package com.uinnova.test.step_definitions.api.base.kpi.tpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.KpiUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.base.TplUtil;

/**
 * 编写时间:2017-11-09 编写人:sunsl 功能介绍:增加指标模板类
 * 2018-4-19重构
 */
public class SaveOrUpdateInfo extends RestApi{
	/**
	 * 新增指标模板
	 * @param tplName
	 * @param tplAlias
	 * @param tplDesc
	 * @param kpiUseType
	 * @param kpiName
	 * @param classGroups
	 * @param tagGroups
	 * @return
	 */
	public JSONObject saveOrUpdateInfo(String tplName, String tplAlias, String tplDesc, String kpiUseType,String kpiName,String classGroups, String tagGroups) {
		String url = ":1511/tarsier-vmdb/cmv/kpi/tpl/saveOrUpdateInfo";
		JSONObject param = new JSONObject();
		// 设置指标模板值
		JSONObject kpiTpl = new JSONObject();
		JSONArray tplItems = new JSONArray();
		kpiTpl.put("tplName", tplName);
		kpiTpl.put("tplAlias", tplAlias);
		kpiTpl.put("tplDesc", tplDesc);
		JSONObject tplItem = new JSONObject();
		if (!kpiName.isEmpty()){
			String kpiId = (new KpiUtil()).getKpiIdByKpiCode(kpiName);
			// 给指标模板添加指标模型的指标
			tplItem.put("rltObjName", kpiName);
			tplItem.put("rltObjId", kpiId);
			tplItem.put("rltObjType", 1);
			tplItem.put("kpiUseType", kpiUseType);
			tplItems.put(tplItem);
		}
		
		if (!classGroups.isEmpty()){
			String[] classArr = classGroups.split("、");
			for (int i=0; i<classArr.length; i++){
				String clsName = classArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				kpiCiGroup.put("rltObjType", "2");
				kpiCiGroup.put("rltObjId", String.valueOf(new CiClassUtil().getClassIdByClassName(clsName,new BigDecimal(1))));
				kpiCiGroup.put("rltObjName", clsName);
				tplItems.put(kpiCiGroup);
			}
		}
		if (!tagGroups.isEmpty()){
			String[] tagArr = tagGroups.split("、");
			for (int i=0; i<tagArr.length; i++){
				String tagName = tagArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				kpiCiGroup.put("rltObjType", "3");
				kpiCiGroup.put("rltObjId", String.valueOf(new TagRuleUtil().getTagId(tagName)));
				kpiCiGroup.put("rltObjName", tagName);
				tplItems.put(kpiCiGroup);
			}
		}
		param.put("kpiTpl", kpiTpl);
		param.put("tplItems", tplItems);
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * 修改指标模板
	 * @param tplName
	 * @param updateTplName
	 * @param updateTplAlias
	 * @param updateTplDesc
	 * @param updateKpiUseType
	 * @param kpiName
	 * @param updateClassGroups
	 * @param updateTagGroups
	 * @return
	 */
	public JSONObject saveOrUpdateInfo(String tplName, String updateTplName, String updateTplAlias,
			String updateTplDesc, String updateKpiUseType,String kpiName, String updateClassGroups, String updateTagGroups) {		
		String url = ":1511/tarsier-vmdb/cmv/kpi/tpl/saveOrUpdateInfo";
		JSONObject param = new JSONObject();
		QueryKpiInfoById qkib = new QueryKpiInfoById();
		TplUtil tplUtil = new TplUtil();
		String kpiTplId = tplUtil.getKpiTplId(tplName);
		JSONObject result = qkib.queryKpiInfoById(kpiTplId);
		JSONObject kpiTpl = new JSONObject();
		kpiTpl = result.getJSONObject("data").getJSONObject("kpiTpl");
		kpiTpl.put("tplName", updateTplName);
		kpiTpl.put("tplAlias", updateTplAlias);
		kpiTpl.put("tplDesc", updateTplDesc);
		JSONArray oldTplItems = new JSONArray();
		if (result.getJSONObject("data").has("tplItems")){
			oldTplItems = result.getJSONObject("data").getJSONArray("tplItems"); 
		}
		JSONArray newTplItems = new JSONArray(); 
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		for (int k=0; k<oldTplItems.length(); k++){
			JSONObject obj = oldTplItems.getJSONObject(k);
			map.put(obj.get("rltObjType")+"_"+obj.getBigDecimal("rltObjId"), obj);
		}

		if (!kpiName.isEmpty()){
			JSONObject tplItem = new JSONObject();
			String kpiId = (new KpiUtil()).getKpiIdByKpiCode(kpiName);
			if (map.containsKey("1_"+kpiId)){
				tplItem = map.get("1_"+kpiId);
			}else{
				tplItem.put("rltObjName", kpiName);
				tplItem.put("rltObjId", kpiId);
				tplItem.put("rltObjType", 1);
				tplItem.put("kpiUseType", updateKpiUseType);
			}
			newTplItems.put(tplItem);
		}
		
		if (!updateClassGroups.isEmpty()){
			String[] classArr = updateClassGroups.split("、");
			for (int i=0; i<classArr.length; i++){
				String clsName = classArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new CiClassUtil().getClassIdByClassName(clsName,new BigDecimal(1));
				if (map.containsKey("2_"+objGroupId)){
					kpiCiGroup = map.get("2_"+objGroupId);
				}else{
					kpiCiGroup.put("rltObjType", "2");
					kpiCiGroup.put("rltObjId", String.valueOf(objGroupId));
					kpiCiGroup.put("rltObjName", clsName);
				}
				newTplItems.put(kpiCiGroup);
			}
		}
		if (!updateTagGroups.isEmpty()){
			String[] tagArr = updateTagGroups.split("、");
			for (int i=0; i<tagArr.length; i++){
				String tagName = tagArr[i];
				JSONObject kpiCiGroup = new JSONObject();
				BigDecimal objGroupId = new TagRuleUtil().getTagId(tagName);
				if (map.containsKey("3_"+objGroupId)){
					kpiCiGroup = map.get("3_"+objGroupId);
				}else{
					kpiCiGroup.put("rltObjType", "3");
					kpiCiGroup.put("rltObjId", String.valueOf(objGroupId));
					kpiCiGroup.put("rltObjName", tagName);
				}
				newTplItems.put(kpiCiGroup);
			}
		}
		param.put("kpiTpl", kpiTpl);
		param.put("tplItems", newTplItems);
		return doRest(url, param.toString(), "POST");
	}
}
