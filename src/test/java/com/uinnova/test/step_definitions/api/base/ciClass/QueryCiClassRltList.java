package com.uinnova.test.step_definitions.api.base.ciClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 可视化建模-查询分类之间的的关系
 * @author wsl
 *
 */
public class QueryCiClassRltList extends RestApi{
	
	/**
	 * @return
	 */
	public JSONObject queryCiClassRltList() {
		String url = ":1511/tarsier-vmdb/cmv/ciClass/queryCiClassRltList";
		return doRest(url, "{}", "POST");
	}

}
