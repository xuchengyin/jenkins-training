package com.uinnova.test.step_definitions.api.base.tagRule;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class RemoveDefById extends RestApi{

	/**
	 * @param tagRuleName
	 * @return
	 */
	public JSONObject removeDefById(String tagRuleName){
		String url  = ":1511/tarsier-vmdb/cmv/tagRule/removeDefById";
		BigDecimal tagId = (new TagRuleUtil()).getTagId(tagRuleName);
		return  doRest(url, String.valueOf(tagId), "POST");
	}
}
