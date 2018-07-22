package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-03-05
 * 编写人:wsl
 * 功能介绍:不分页查询CI分类关系的源和目标信息
 */
public class QuerySAndTClassRltInfoList extends RestApi{
	
	
	public JSONObject querySAndTClassRltInfoList(){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/querySAndTClassRltInfoList";
		JSONObject param = new JSONObject();
		param.put("cdt", new JSONObject());
		param.put("sourceAndTargetIds", new JSONArray());
		param.put("orders", "");
		return doRest(url,"{}","POST");
	}
	/*
	 
参数：如果所有参数均为空则查询左右分类的关系信息
{
cdt:{选填参数暂时没用},
sourceAndTargetIds:[分类ID数组，既作为源也作为目标],
orders: 排序字段，选填参数
}
	 * 
	 * */
}
