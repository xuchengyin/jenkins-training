package com.uinnova.test.step_definitions.api.base.ci;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

/**
 * 删除单个CI
 * @author uinnova
 *
 */
public class RemoveById extends RestApi{

	/**
	 * @param ciId
	 * @return
	 */
	public JSONObject removeById(BigDecimal ciId){
		String url = ":1511/tarsier-vmdb/cmv/ci/removeById";
		return doRest(url, String.valueOf(ciId), "POST");
	}
	
	/**
	 * 根据ciCode删除CI
	 * @param ciCode
	 * @return
	 */
	public JSONObject removeById(String ciCode){
		return removeById((new CiUtil()).getCiId(ciCode));
	}
}
