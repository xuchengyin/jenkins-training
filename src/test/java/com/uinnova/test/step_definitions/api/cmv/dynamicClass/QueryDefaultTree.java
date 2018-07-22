package com.uinnova.test.step_definitions.api.cmv.dynamicClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryDefaultTree extends RestApi{

	public JSONObject queryDefaultTree(){
		String url = ":1511/tarsier-vmdb/cmv/dynamicClass/queryDefaultTree";
//		返回结果格式：
//		CcDynamicClassNode[] //查询结果
//		 [{
//		 icon : String, //节点图标
//		 text : String, //节点名称
//		 children : CcDynamicClassNode[], //直属子节点列表
//		 id : String, //节点ID
//		 parentId : String, //上级节点ID, 1级为0
//		 bindId : Long, //绑定的数据ID, type=2为tagId, type=3为ciid
//		 data : Object, //绑定数据
//		 type : Integer //节点类型 : 1=目录 2=标签	3=CI
//		 }] 
		return doRest(url, "", "POST");
	}
}
