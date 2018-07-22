package com.uinnova.test.step_definitions.api.base.tagRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class QueryDefExtInfoById extends RestApi{

	/**
	 * @param tagName
	 * @return
	 */
	public JSONObject queryDefExtInfoById(String tagName){
		String url = ":1511/tarsier-vmdb/cmv/tagRule/queryDefExtInfoById";
		BigDecimal tagId = (new TagRuleUtil()).getTagId(tagName);
		return  doRest(url, String.valueOf(tagId), "POST");
	}
}
