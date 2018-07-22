package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class RemoveByCdt extends RestApi{

	/**
	 * 删除ci关系接口
	 * @author lidw
	 *
	 * @请求方式  post
	 * */
	
	public JSONObject removeByCdt(String sourceCiCode,String targetCiCode){
//		{"sourceCiId":100000000010992,"targetCiId":100000000010930}
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeByCdt";
		CiUtil cu = new CiUtil();
		JSONObject param = new JSONObject();
		param.put("sourceCiId", cu.getCiId(sourceCiCode));
		param.put("targetCiId", cu.getCiId(targetCiCode));
		return doRest(url, param.toString(), "POST");
		
	}
	//删除当前ci关系(批量)
//	{"sourceCiId":100000000010992,"targetClassId":"100000000000596","classId":100000000000729}
	public JSONObject removeByCdt(String sourceCiCode,String targetClassName,String rltClassName){
//		{"sourceCiId":100000000010992,"targetCiId":100000000010930}
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/removeByCdt";
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		RltClassUtil rcu = new RltClassUtil();
		JSONObject param = new JSONObject();
		param.put("sourceCiId", cu.getCiId(sourceCiCode));
		param.put("targetClassId", ccu.getCiClassId(targetClassName));
		param.put("classId", rcu.getRltClassId(rltClassName));
		return doRest(url, param.toString(), "POST");
		
	}
}
