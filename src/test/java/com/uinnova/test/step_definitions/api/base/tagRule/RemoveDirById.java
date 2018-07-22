package com.uinnova.test.step_definitions.api.base.tagRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class RemoveDirById extends RestApi{

	/**
	 * @param dirName
	 * @return
	 */
	public JSONObject removeDirById(String dirName){
		BigDecimal tagId = (new TagRuleUtil()).getDirId(dirName);
		String url = ":1511/tarsier-vmdb/cmv/tagRule/removeDirById";
		return doRest(url, String.valueOf(tagId), "POST");
	}
}
