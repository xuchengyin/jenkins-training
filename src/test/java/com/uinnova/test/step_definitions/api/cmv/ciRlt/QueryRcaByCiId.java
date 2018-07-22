package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

public class QueryRcaByCiId extends RestApi{

	public JSONObject queryRcaByCiId(String ciCode,int depth){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryRcaByCiId";
		CiUtil cu = new CiUtil();
		BigDecimal ciId = cu.getCiId(ciCode);
		JSONObject param = new JSONObject();
		param.put("ciId", ciId);
		param.put("depth", depth);
		return doRest(url, param.toString(), "POST");
	}
	
	
	//通过影响规则查询CI的故障根因.

	public JSONObject queryRcaByCiIds(List<String> ciCodes,int depth){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryRcaByCiIds";
		CiUtil cu = new CiUtil();
		ArrayList ciIds = new ArrayList();
		for (int i = 0; i < ciCodes.size(); i++) {
			BigDecimal ciId = cu.getCiId(ciCodes.get(i));
			ciIds.add(ciId);
		}
		JSONObject param = new JSONObject();
		param.put("ciIds", ciIds);
		param.put("depth", depth);
		return doRest(url, param.toString(), "POST");
	}
}
