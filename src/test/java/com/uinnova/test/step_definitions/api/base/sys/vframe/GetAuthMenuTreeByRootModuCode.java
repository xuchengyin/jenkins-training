package com.uinnova.test.step_definitions.api.base.sys.vframe;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 根据模块编码获得授权的菜单树
 *
 */
public class GetAuthMenuTreeByRootModuCode extends RestApi{
	public JSONObject getAuthMenuTreeByRootModuCode(String rootCode){
		JSONObject param = new JSONObject();
		param.put("rootCode", rootCode);
		String url = ":1511/tarsier-vmdb/cmv/sys/vframe/getAuthMenuTreeByRootModuCode";
		return doRest(url, param.toString(), "POST");
	}
}
