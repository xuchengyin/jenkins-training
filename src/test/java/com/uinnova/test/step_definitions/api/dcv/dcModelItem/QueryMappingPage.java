package com.uinnova.test.step_definitions.api.dcv.dcModelItem;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 *编写时间: 2018-2-2
 *编写人:wjx
 *功能介绍:模型映射搜索
 */
public class QueryMappingPage extends RestApi{
	/*
	 * pageNumber = 1   为搜索
	 * pageNumber = X 为翻页
	 * 
	 * */
	public JSONObject searchModel(Integer pageNumber,String modelName){
		String url = ":1511/tarsier-vmdb/dcv/dcModelItem/queryMappingPage";
		
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		param.put("pageNum", pageNumber);
		param.put("pageSize", 10);
		param.put("orders", "");
		cdt.put("userModel", modelName);
		param.put("cdt", cdt);
		String result = null;
		try {
			result = QaUtil.loadRest(url, param.toString(), "POST");
		} catch (Exception e) {
		}
		AssertResult as = new AssertResult();
		return as.assertRes(result);
	
	}

}