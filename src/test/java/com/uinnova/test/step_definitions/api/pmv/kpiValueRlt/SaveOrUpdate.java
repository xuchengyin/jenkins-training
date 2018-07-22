package com.uinnova.test.step_definitions.api.pmv.kpiValueRlt;

import org.json.JSONObject;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 给指标值添加映射
 * @author kyn
 */
public class SaveOrUpdate extends RestApi {

	/**
	 * 添加值映射
	 * @param ruleTypeName 
	 * @param ruleName
	 * @param ruleDesc
	 * @param newRuleName
	 * @param newRuleDesc
	 * @return
	 */
	public JSONObject saveOrUpdate(String param){
			String url = ":1518/pmv-web/kpiValueRlt/saveOrUpdate";
			
			
			return doRest(url, param, "POST");
		}
	}

