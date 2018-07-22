package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 删除分类之间的关系
 */
public class RemoveCiClassRltInfo extends RestApi{

	/**
	 * @param ciClassRltId
	 * @return
	 */
	public JSONObject removeCiClassRltInfo(String ciClassRltId) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", ciClassRltId);
		String url = ":1511/tarsier-vmdb/cmv/ciClass/removeCiClassRltInfo";
		return doRest(url,jsonObj.toString(), "POST");
	}

}


