package com.uinnova.test.step_definitions.api.base.visualmodeling;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltUtil;

import cucumber.api.DataTable;

/**
 * 保存可视化建模中影响分析关系模型
 * @author wsl
 */
public class SaveOrUpdateImpactPath extends RestApi{

	/**
	 * 根据id保存
	 * @param focusClassId
	 * @param ciClassRltIds
	 * @return
	 */
	public JSONObject saveOrUpdateImpactPath(BigDecimal focusClassId, JSONArray ciClassRltIds){
		String url =":1511/tarsier-vmdb/cmv/visualmodeling/saveOrUpdateImpactPath";
		JSONObject param = new JSONObject();
		param.put("focusClassId",focusClassId);
		param.put("ciClassRltIds",ciClassRltIds);
		return this.doRest(url, param.toString(), "POST");
	}

	/**
	 * 用名称保存
	 * @param focusClassName
	 * @param table
	 * @return
	 */
	public JSONObject saveOrUpdateImpactPath(String focusClassName, DataTable table){
		 JSONArray ciClassRltIds  = new JSONArray();
		 RltUtil ru = new RltUtil();
		 for (int i =1; i<table.raw().size(); i++){
			 List<String> row = table.raw().get(i);
			 ciClassRltIds.put(ru.getCiClassRltId(row.get(3), row.get(1), row.get(2)));
		 }
		return saveOrUpdateImpactPath((new CiClassUtil()).getCiClassId(focusClassName), ciClassRltIds);
	}
}
