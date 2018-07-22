package com.uinnova.test.step_definitions.utils.cmv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class CiRltRuleUtil {
	public static BigDecimal getDefIdByName(String defName){
		BigDecimal defId= new BigDecimal(0);
		String sql ="select ID from CC_RLT_RULE_DEF where DATA_STATUS=1 and DOMAIN_ID ="+QaUtil.domain_id+" and DEF_name ='"+defName+"'";
		List  ruleList = JdbcUtil.executeQuery(sql);
		if (ruleList!=null && ruleList.size()>0){
			HashMap defHashMap = (HashMap) ruleList.get(0);
			defId = (BigDecimal) defHashMap.get("ID");
		}
		return defId;
	}

}
