package com.uinnova.test.step_definitions.api.base.ci;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class SearchRealInfoPage extends RestApi{

	/**
	 * @param tagName
	 * @return
	 */
	public JSONObject searchRealInfoPage(String tagName){
		String url = ":1511/tarsier-vmdb/cmv/search/ci/searchRealInfoPage";
		JSONObject param  = new JSONObject();
		JSONObject cdt  = new JSONObject();
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 400);
		JSONArray tagIds =  new JSONArray();
		BigDecimal tagId = (new TagRuleUtil()).getTagId(tagName);
		tagIds.put(tagId);
		param.put("tagIds", tagIds);
		JSONArray words =  new JSONArray();
		param.put("words", words);
		return doRest(url, param.toString(), "POST");
	}
}
