package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetClassTree extends RestApi{

	/**
	 * 按照关键字搜索CI分类
	 * @param className
	 * @return
	 */
	public JSONObject getClassTree(String keyName) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/getClassTree";
		JSONObject param = new JSONObject();
		if (!keyName.isEmpty()){
			param.put("className", keyName);
		}
		return doRest(url, param.toString(), "POST");
	}
	
	
	
	/**
	 * @author ldw
	 * //查询树状的CI分类结构, 0表示不查子分类,选填
	 * @param keyName parendId
	 * @return
	 * */
	public JSONObject getClassTree(String keyName, String parentId) {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/getClassTree";
		JSONObject param = new JSONObject();
		if (!keyName.isEmpty() && !parentId.isEmpty()){
			param.put("className", keyName);
			param.put("parentId", Integer.parseInt(parentId));
		}else{
			return null;
		}
		return doRest(url, param.toString(), "POST");
	}

}
