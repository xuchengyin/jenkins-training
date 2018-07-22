package com.uinnova.test.step_definitions.api.base.pv;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 增加一次点击记录
 * @author wsl
 * 2018-3-16
 */
public class AddAClickRecord extends RestApi{
	
	/**
	 * @return
	 */
	public JSONObject addAClickRecord(String targetCode){
		String url =":1511/tarsier-vmdb/cmv/pv/addAClickRecord";
		JSONObject param = new JSONObject();
		param.put("targetCode", targetCode);
		return this.doRest(url, param.toString(), "POST");
	}
	

}
