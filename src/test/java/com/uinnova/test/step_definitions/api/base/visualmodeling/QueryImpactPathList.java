package com.uinnova.test.step_definitions.api.base.visualmodeling;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

/**
 * 查询可视化建模中影响分析关系模型
 * @author wsl
 *
 */
public class QueryImpactPathList extends RestApi{
	
	/**
	 * 根据热点分类id查询
	 * @param focusClassId
	 * @return
	 */
	public JSONObject queryImpactPathList(BigDecimal focusClassId){
		String url =":1511/tarsier-vmdb/cmv/visualmodeling/queryImpactPathList";
		JSONObject param = new JSONObject();
		param.put("focusClassId", focusClassId);
		return this.doRest(url, param.toString(), "POST");
	}
	
	/**
	 * 根据热点分类名称查询
	 * @param focusClassName
	 * @return
	 */
	public JSONObject queryImpactPathList(String focusClassName){
		return queryImpactPathList((new CiClassUtil()).getCiClassId(focusClassName));
	}

}
