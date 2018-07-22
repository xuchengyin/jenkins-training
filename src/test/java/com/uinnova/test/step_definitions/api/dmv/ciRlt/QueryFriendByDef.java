package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.dmv.CiRltUtil;

/**
 * @author wsl
 * 待朋友圈开发完完善
 */
public class QueryFriendByDef extends RestApi{
	public JSONObject queryFriendByDef(String friendName, String ciCode){
		String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryFriendByDef";
		CiRltUtil crlu = new CiRltUtil();
		CiUtil ciUtil = new CiUtil();
		BigDecimal friendDefId = crlu.getDefIdByDefName(friendName);
		JSONObject param = new JSONObject();
		param.put("friendDefId", friendDefId);
		param.put("ciId", ciUtil.getCiId(ciCode));
		return doRest(url, param.toString(), "POST");
	}
	
	public JSONObject queryFriendByDefNull(String friendName, String ciCode, String kw){
		String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryFriendByDef";
		CiRltUtil crlu = new CiRltUtil();
		CiUtil ciUtil = new CiUtil();
		BigDecimal friendDefId = crlu.getDefIdByDefName(friendName);
		JSONObject param = new JSONObject();
		param.put("friendDefId", friendDefId);
		param.put("ciId", ciUtil.getCiId(ciCode));
		return doFailRest(url, param.toString(), "POST", kw);
	}
}
