package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-04-18
 * 编写人:sunsl
 * 功能介绍:根据code查询组合视图
 */
public class QueryDiagramInfoByAppRltCiCode extends RestApi{
	public JSONObject queryDiagramInfoByAppRltCiCode(String ciCode){
		String url = ":1511/tarsier-vmdb/dmv/diagram/queryDiagramInfoByAppRltCiCode";
		return doRest(url,ciCode,"POST");
	}

}
