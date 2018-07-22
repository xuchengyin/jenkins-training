package com.uinnova.test.step_definitions.utils.cmv.ciQualityRule;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author  wsl
 * 仪表盘规则工具类
 *
 */
public class CiQualityRuleUtil {
	
	/**
	 * 根据规则名称获取规则类型
	 * @param ruleTypeName
	 * @return
	 */
	public static int getRuleType(String ruleTypeName){
		int ruleType = 10;
		switch (ruleTypeName){
		case Contants.CIQUALITYRULE_VERACITY:
		case Contants.CIQUALITYRULE_VERACITYALONE:
		case Contants.CIQUALITYRULE_VERACITYOVERDUE:
			ruleType = CiQualityRuleType.VERACITYALONE.getRuleType();
			break;
		case Contants.CIQUALITYRULE_CIATTRCOMPLIANCE:
			ruleType = CiQualityRuleType.CIATTRCOMPLIANCE.getRuleType();
			break;
		case Contants.CIQUALITYRULE_CIATTRINTEGRITY:
			ruleType = CiQualityRuleType.CIATTRINTEGRITY.getRuleType();
			break;
		default:
			fail(Contants.CIQUALITYRULE_UNLAWFULRULE+"-"+ruleTypeName);
		}
		return ruleType;
	}
	
	/**
	 * 根据规则名称获取规则子类型
	 * @param ruleTypeName
	 * @return
	 */
	public static int getRuleSubType(String ruleTypeName){
		int ruleSubType = 1001;
		switch (ruleTypeName){
			case Contants.CIQUALITYRULE_VERACITY://准确性默认子类型为孤儿规则
			case Contants.CIQUALITYRULE_VERACITYALONE:
				ruleSubType = CiQualityRuleType.VERACITYALONE.getRuleSubType();
				break;
			case Contants.CIQUALITYRULE_VERACITYOVERDUE:
				ruleSubType = CiQualityRuleType.VERACITYOVERDUE.getRuleSubType();
				break;
			case Contants.CIQUALITYRULE_CIATTRCOMPLIANCE:
				ruleSubType = CiQualityRuleType.CIATTRCOMPLIANCE.getRuleSubType();
				break;
			case Contants.CIQUALITYRULE_CIATTRINTEGRITY:
				ruleSubType = CiQualityRuleType.CIATTRINTEGRITY.getRuleSubType();
				break;
			default:
				fail(Contants.CIQUALITYRULE_UNLAWFULRULE+ruleTypeName);
			}
		return ruleSubType;
	}
	
	/**
	 * 根据规则和名称获取ID
	 * @param ruleTypeName
	 * @param ruleName
	 * @return
	 */
	public static BigDecimal getIDByNameAndRuleType(String ruleTypeName, String ruleName){
		int ruleType = CiQualityRuleUtil.getRuleType(ruleTypeName);
		String sql = "  select ID from CC_CI_QUALITY_RULE   where   RULE_NAME = '"+ruleName.trim()+"' "
				+" and RULE_TYPE = "+ruleType
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1  ";
		List dbList =  JdbcUtil.executeQuery(sql);
		Map map = (Map) dbList.get(0);
		return  (BigDecimal) map.get("ID");
	}
}
