package com.uinnova.test.step_definitions.api.pmv.StandardTag;

import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 新建标准标签
 * @author kyn
 */
public class SaveOrUpdate extends RestApi {

	/**
	 * 添加标准标签
	 * @param stag
	 * @return
	 */
	public JSONObject saveOrUpdate(String stag){
		String url = ":1518/pmv-web/sTag/saveOrUpdate";		
		JSONObject param = new JSONObject();
		param.put("tagType", 1);
		param.put("name", stag);
		param.put("code", stag);
		param.put("originalTag", "");
		JSONObject result =   doRest(url, param.toString(), "POST");
		//JSONObject kpidata  =  result.getJSONObject("data").getJSONArray("data").getJSONObject(0);
		return result;
	}
	/**
	 * 创建重名的标准标签
	 * @param stag
	 * @return
	 */
	public JSONObject saveOrUpdateAgain(String stag,String kw){
		String url = ":1518/pmv-web/sTag/saveOrUpdate";
		JSONObject param = new JSONObject();
		param.put("tagType", 1);
		param.put("name", stag);
		param.put("code", stag);
		param.put("originalTag", "");
		return doFailRest(url, param.toString(), "POST",kw);
	}
}