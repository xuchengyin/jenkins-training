package com.uinnova.test.step_definitions.api.dmv.ci;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

/**
 * @author wsl
 * 根据id读取CI信息
 *
 */
public class QueryById extends RestApi{
	public JSONObject queryById(String ciCode){
		String url = ":1511/tarsier-vmdb/dmv/ci/queryById";
		CiUtil ciu = new CiUtil();
		return doRest(url, ciu.getCiId(ciCode).toString(), "POST");
	}
}
