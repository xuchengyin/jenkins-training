package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 设置标签
 * @author kyn
 */
public class SaveOrUpdatePcTagManageRlts extends RestApi {

	/**
	 * 设置标签
	 * @param stag
	 * @return
	 */	
	public JSONObject saveOrUpdatePcTagManageRlts(String param){
		String url = ":1518/pmv-web/sTag/saveOrUpdatePcTagManageRlts";	
		JSONObject result =   doRest(url, param, "POST");
            return result;
	}

}