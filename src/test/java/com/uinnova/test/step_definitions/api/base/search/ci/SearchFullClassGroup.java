package com.uinnova.test.step_definitions.api.base.search.ci;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 分组查询分类
 *
 */
public class SearchFullClassGroup extends RestApi{

	/**
	 * @return
	 */
	public JSONObject searchFullClassGroup() {
		String url = ":1511/tarsier-vmdb/cmv/search/ci/searchFullClassGroup";
		return doRest(url,"{}", "POST");
	}
}
