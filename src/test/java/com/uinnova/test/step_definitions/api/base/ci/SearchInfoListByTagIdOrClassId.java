package com.uinnova.test.step_definitions.api.base.ci;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class SearchInfoListByTagIdOrClassId extends RestApi{

	/**
	 * @param tagName
	 * @param pageNum
	 * @return
	 */
	public JSONObject searchInfoListByTagIdOrClassId(String tagName,int pageNum) {
		String url = ":1511/tarsier-vmdb/cmv/search/ci/searchInfoListByTagIdOrClassId";
		JSONObject param = new JSONObject();
		JSONArray words = new JSONArray();
		BigDecimal tagId = (new TagRuleUtil()).getTagId(tagName);
		param.put("pageNum", pageNum);
		param.put("pageSize", 20);
		param.put("tagId", tagId);
		param.put("words",words );
		return  doRest(url, param.toString(), "POST");
	}
}
