package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 不分页查询分类设计图信息-可视化建模
 *
 */
public class QueryCiClassDiagramInfoList extends RestApi{

	public JSONObject queryCiClassDiagramInfoList() {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/queryCiClassDiagramInfoList";
		return doRest(url,"{}", "POST");
	}
}
