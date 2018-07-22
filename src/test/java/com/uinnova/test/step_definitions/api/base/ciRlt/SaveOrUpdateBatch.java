package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

import cucumber.api.DataTable;


public class SaveOrUpdateBatch extends RestApi{

	/**
	 * 批量增加关系接口
	 * @author lidw
	 *
	 * @请求方式  post
	 * */
	
	public JSONObject saveOrUpdateBatch(DataTable dt){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/saveOrUpdateBatch";
		
		RltClassUtil rcu = new RltClassUtil();
		List <List<String>> list = dt.raw();
		JSONObject batch = new JSONObject();
		BigDecimal rltClassId = rcu.getRltClassId(list.get(1).get(0));
		JSONArray rltRecords = new JSONArray();
		
		for(int i = 1; i< list.size(); i++){
			JSONObject temp = new JSONObject();
			temp.put("sourceCiCode", list.get(i).get(1));
			temp.put("targetCiCode", list.get(i).get(2));
			rltRecords.put(temp);
		}
		batch.put("rltClassId", rltClassId);
		batch.put("rltRecords", rltRecords);
		
		return doRest(url, batch.toString(), "POST");
	}

}
