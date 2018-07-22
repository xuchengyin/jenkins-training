package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 查询准确性孤儿节点中分类关联的分类
 */
public class QueryRelatedCiClassInfo extends RestApi {

	/**
	 * 查询准确性孤儿节点中分类关联的分类
	 * @return
	 */
	public JSONObject queryRelatedCiClassInfo(){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/queryRelatedCiClassInfo";
		return doRest(url, "{}", "POST");
	}

}
