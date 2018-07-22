package com.uinnova.test.step_definitions.api.dcv.dcCiClassMp;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 
 * @author wjx
 * 查询分类映射列表
 */
public class QueryCiClassMpPageList extends RestApi{
	public JSONObject queryCiClassMpPageList(){
		String url = "1511/tarsier-vmdb/dcv/dcCiClassMp/queryCiClassMpPageList";
		JSONObject parameter = new JSONObject();
		JSONObject cdt = new JSONObject();
		parameter.put("pageNum", 1);
		parameter.put("pageSize", 1000);
		parameter.put("orders", "");
		cdt.put("mpName","");
		cdt.put("classType", 1);
		parameter.put("cdt", cdt);
		return doRest(url, parameter.toString(), "POST");
	}

}
