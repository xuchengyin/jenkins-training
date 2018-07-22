package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

public class QueryRltClassCiInfoPage extends RestApi{
	/**
	 * 查询CI关系接口
	 * @author lidw
	 *
	 * @请求方式  post
	 * */
	
	public JSONObject queryRltClassCiInfoPage(String rltName, String sourceCiCode, String targetCiCode){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryRltClassCiInfoPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		RltClassUtil rcu = new RltClassUtil();
		
		cdt.put("sourceCiId", cu.getCiId(sourceCiCode));
		cdt.put("sourceClassId", ccu.getCiClassId(ccu.getCiClassNameByCiCode(sourceCiCode)));
		cdt.put("targetClassId", ccu.getCiClassId(ccu.getCiClassNameByCiCode(targetCiCode)));
		cdt.put("classId", rcu.getRltClassId(rltName));	//这个是关系分类ID
		param.put("pageSize", 10000);
		param.put("pageNum", 1);
		param.put("cdt", cdt);
//		{"cdt":{"sourceCiId":100000000010992,"sourceClassId":100000000000598,"targetClassId":"100000000000596","classId":100000000000729},"pageSize":100,"pageNum":1}
		return doRest(url, param.toString(), "POST");
	}
}
