package com.uinnova.test.step_definitions.api.ITV.cirel;

import org.apache.poi.hssf.dev.ReSave;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;


/**
 *@author wjx
 *@请求方式 POST
 *@param viewID 视图中CIID
 *功能介绍:获取CI关系
 */

public class QueryOneUpAndDown {
	public JSONObject releation(String[] viewID){
		String url = ":1511/tarsier-vmdb/dcv/vcDiagram/cirel/queryOneUpAndDown";
		
		JSONObject param = new JSONObject();
		JSONArray ids = new JSONArray();
		
		for(int i = 0; i < viewID.length; i++){
			ids.put(viewID[i]);
		}
		
		param.put("ids", ids);
		param.put("up", true);
		param.put("down", true);
		
		String result = QaUtil.loadRest(url, param.toString(), "POST");
		AssertResult as = new AssertResult();
		return as.assertRes(result);
		
	}
}
