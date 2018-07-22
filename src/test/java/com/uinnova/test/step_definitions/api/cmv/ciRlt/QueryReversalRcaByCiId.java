package com.uinnova.test.step_definitions.api.cmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;

public class QueryReversalRcaByCiId extends RestApi{

	public JSONObject queryReversalRcaByCiId(String ciCode, String depth){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/queryReversalRcaByCiId";
		JSONObject param = new JSONObject();
		CiUtil cu = new CiUtil();
		BigDecimal ciId = cu.getCiId(ciCode);
		param.put("ciId", ciId);
		param.put("depth", Integer.parseInt(depth));
		return doRest(url, param.toString(), "POST");
	}
}
