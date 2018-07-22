package com.uinnova.test.step_definitions.api.cmv.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

public class SearchInfoListWithDataAuth extends RestApi{
	
	public JSONObject SearchInfoListWithDataAuth(List<String> ciClsNames,List<String> tagNames,List<String> keys){
		String url = ":1511/tarsier-vmdb/cmv/search/ci/searchInfoListWithDataAuth";
		JSONObject param =  new JSONObject();
		JSONObject cdt =  new JSONObject();
		ArrayList<BigDecimal> classIds = null;
		if(ciClsNames!=null && !ciClsNames.get(0).trim().equals("")){
			classIds = new ArrayList<BigDecimal>();
			for(String clsName:ciClsNames){
				BigDecimal ciClsId = (new CiClassUtil()).getCiClassId(clsName);
				classIds.add(ciClsId);
			}
		}
		cdt.put("classIds",classIds);
		param.put("pageNum", 1);
		param.put("pageSize", 20);
		param.put("cdt", cdt);
		ArrayList<BigDecimal> tagIds = null;
		if(tagNames!=null && tagNames.size()>0 && !tagNames.get(0).trim().equals("")){
			tagIds = new ArrayList<BigDecimal>();
			for(String tagName:tagNames){
				BigDecimal tagId = (new TagRuleUtil()).getTagId(tagName);
				tagIds.add(tagId);

			}
		}
		param.put("tagIds",tagIds);
		ArrayList<String> words = null;
		if(keys!=null && !keys.get(0).trim().equals("")){
			words = new ArrayList<String>();
			for(String key:keys){
				words.add(key.toUpperCase());
			}
		}
		param.put("words",words);
		param.put("orders","CREATE_TIME DESC, CI_CODE");
		return doRest(url, param.toString(), "POST");

	}
}
