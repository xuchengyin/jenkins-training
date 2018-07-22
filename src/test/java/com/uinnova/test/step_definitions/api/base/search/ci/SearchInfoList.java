package com.uinnova.test.step_definitions.api.base.search.ci;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class SearchInfoList extends RestApi{

	/**
	 * @param ciClsNames
	 * @param tagNames
	 * @param keys
	 * @return
	 */
	public JSONObject searchInfoList(List<String> ciClsNames,List<String> tagNames,List<String> keys){
		String url = ":1511/tarsier-vmdb/cmv/search/ci/searchInfoList";
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

	/**
	 * 获取ciId
	 * @param ciClsName
	 * @param ciCode
	 * @return
	 */
	public BigDecimal searchInfoList_getSingleCiId(String ciClsName,String  ciCode){
		ArrayList<String> ciClsNames = new ArrayList<String>(); 
		ciClsNames.add(ciClsName);
		ArrayList<String> tagNames = new ArrayList<String>(); 
		ArrayList<String> keys = new ArrayList<String>(); 
		keys.add(ciCode);
		JSONObject sf = searchInfoList(ciClsNames,tagNames, keys);
		int totalCount = sf.getJSONObject("data").getInt("totalCount");
		BigDecimal ciId = new BigDecimal(0);
		String recordTmp = ((JSONArray)sf.getJSONObject("data").getJSONArray("records")).toString();
		if(totalCount==1 && recordTmp!=null){
			ciId = ((JSONObject)sf.getJSONObject("data").getJSONArray("records").get(0)).getJSONObject("ci").getBigDecimal("id");
		}else{
			int i=0;
			while((totalCount==0 || recordTmp==null)&&i<10){
				sf = searchInfoList(ciClsNames,tagNames, keys);
				totalCount = sf.getJSONObject("data").getInt("totalCount");
				i++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(totalCount==0){
				QaUtil.report("=====读取数据失败=====");
				fail("未查询到数据ciClsName: "+ ciClsName+"   ciCode:"+ciCode);
			}
			ciId = ((JSONObject)sf.getJSONObject("data").getJSONArray("records").get(0)).getJSONObject("ci").getBigDecimal("id");
		}
		return ciId;
	}
}
