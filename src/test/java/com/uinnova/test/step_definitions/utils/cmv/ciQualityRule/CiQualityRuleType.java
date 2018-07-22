package com.uinnova.test.step_definitions.utils.cmv.ciQualityRule;

/**
 * @author 
 * 仪表盘规则枚举类
 *
 */
public enum CiQualityRuleType {
	/**
	 * ci完整性(通过属性)
	 * ci attr integrity
	 */
	CIATTRINTEGRITY(10,1001),
	
	/**
	 * ci合规性检查
	 * ci attr compliance
	 */
	CIATTRCOMPLIANCE(11,1101),
	/**
	 * 准确性过期的
	 */
	VERACITYOVERDUE(12,1201),
	/**
	 * 正确性孤儿节点
	 */
	VERACITYALONE(12,1202);
	
	private CiQualityRuleType(Integer ruleType, Integer ruleSubType) {
		this.ruleType = ruleType;
		this.ruleSubType = ruleSubType;
	}

	private Integer ruleType;
	private Integer ruleSubType;
	
	
    public Integer getRuleType() {
		return ruleType;
	}

	public Integer getRuleSubType() {
		return ruleSubType;
	}



	public static CiQualityRuleType valueOf(int v) throws Exception {
    	switch(v) {
    		case 1001: return CIATTRINTEGRITY;
    		case 1101: return CIATTRCOMPLIANCE;
    		case 1201: return VERACITYOVERDUE;
    		case 1202: return VERACITYALONE;
    		default : throw new Exception(" is wrong value:'"+v+"'! ");
    	}
    }
}
