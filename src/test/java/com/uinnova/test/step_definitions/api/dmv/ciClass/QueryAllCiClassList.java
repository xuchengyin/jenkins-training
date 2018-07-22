package com.uinnova.test.step_definitions.api.dmv.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编辑时间:2018-05-09
 * 编辑人:sunsl
 * 功能介绍:条件查询分类列表，没有条件查询所有,不通过配置过滤
 */
public class QueryAllCiClassList extends RestApi{
    public JSONObject queryAllCiClassList(){
    	String url =":1511/tarsier-vmdb/dmv/ciClass/queryAllCiClassList";
    	JSONObject param = new JSONObject();
    	param.put("ciType", 1);
    	return doRest(url,param.toString(),"POST");
    }
}
