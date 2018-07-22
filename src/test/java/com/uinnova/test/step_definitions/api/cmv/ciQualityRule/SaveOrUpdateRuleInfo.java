package com.uinnova.test.step_definitions.api.cmv.ciQualityRule;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;

import cucumber.api.DataTable;

/**
 * 保存仪表盘规则信息
 * @author wsl
 *
 */
public class SaveOrUpdateRuleInfo extends RestApi {

	/**
	 * 保存空规则定义
	 * @param ruleName
	 * @param ruleDesc
	 * @param ruleType
	 * @param ruleSubType
	 * @param tagId
	 * @param status
	 * @return
	 */
	public JSONObject saveOrUpdateRuleInfo(String ruleTypeName, String ruleName, DataTable table){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/saveOrUpdateRuleInfo";
		JSONObject param = new JSONObject();
		QueryCiQualityInfoById queryCiQualityInfoById = new QueryCiQualityInfoById();
		JSONObject result = queryCiQualityInfoById.queryCiQualityInfoById(ruleTypeName, ruleName);
		JSONObject ciQualityRuleObj = result.getJSONObject("data").getJSONObject("ciQualityRule");
		JSONObject ciQualityRule = new JSONObject();
		ciQualityRule.put("id", ciQualityRuleObj.get("id"));
		ciQualityRule.put("ruleName", ciQualityRuleObj.get("ruleName"));
		ciQualityRule.put("ruleDesc", ciQualityRuleObj.get("ruleDesc"));
		ciQualityRule.put("ruleType", ciQualityRuleObj.get("ruleType"));
		ciQualityRule.put("ruleSubType", ciQualityRuleObj.get("ruleSubType"));
		ciQualityRule.put("status", ciQualityRuleObj.get("status"));
		JSONArray rules = new JSONArray();
		JSONObject rulesObj = new JSONObject();
		JSONArray items = new JSONArray();
		rulesObj.put("items", items);
		JSONObject rule = new JSONObject();
		//rule.put("classId", value);
		rulesObj.put("rule", rule);
		rules.put(rulesObj);
		JSONObject tagRuleInfo = new JSONObject();
		tagRuleInfo.put("rules", rules);
		JSONArray ciQualityRuleAttrs = new JSONArray();
		param.put("ciQualityRule", ciQualityRule);
		param.put("tagRuleInfo", tagRuleInfo);
		param.put("ciQualityRuleAttrs", ciQualityRuleAttrs);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * 同时保存CI筛选条件和检查条件
	 * @param ruleTypeName
	 * @param ruleName
	 * @param ciTable   CI筛选条件
	 * @param table     规则检查条件
	 * @return
	 */
	public JSONObject saveOrUpdateRuleInfoSetRule(String ruleTypeName, String ruleName, DataTable ciTable, DataTable table){
		String url = ":1511/tarsier-vmdb/cmv/ciQualityRule/saveOrUpdateRuleInfo";
		JSONObject param = new JSONObject();
		QueryCiQualityInfoById queryCiQualityInfoById = new QueryCiQualityInfoById();
		JSONObject result = queryCiQualityInfoById.queryCiQualityInfoById(ruleTypeName, ruleName);
		JSONObject ciQualityRuleObj = result.getJSONObject("data").getJSONObject("ciQualityRule");
		JSONObject ciQualityRule = new JSONObject();
		ciQualityRule.put("id", ciQualityRuleObj.get("id"));
		ciQualityRule.put("ruleName", ciQualityRuleObj.get("ruleName"));
		if (ciQualityRuleObj.has("ruleDesc"))
			ciQualityRule.put("ruleDesc", ciQualityRuleObj.get("ruleDesc"));
		ciQualityRule.put("ruleType", ciQualityRuleObj.get("ruleType"));
		ciQualityRule.put("ruleSubType", ciQualityRuleObj.get("ruleSubType"));
		ciQualityRule.put("status", ciQualityRuleObj.get("status"));
		CiClassUtil ciclsU = new CiClassUtil();
		TagRuleUtil tagRuleUtil = new TagRuleUtil();
		JSONObject tagRuleInfo = new JSONObject();
		//		if (result.getJSONObject("data").has("tagRuleInfo"))
		//			tagRuleInfo = result.getJSONObject("data").getJSONObject("tagRuleInfo");
		JSONArray rules = new JSONArray();
		//		if (tagRuleInfo.has("rules"))
		//			rules = tagRuleInfo.getJSONArray("rules");
		int rows = ciTable.raw().size();
		JSONObject rulesObj = new JSONObject();
		JSONArray items = new JSONArray();
		int count =0;
		for(int i=1;i<rows;i++){
			String className = ""; 
			String attrName = "";
			String ruleOpName = "";
			String ruleVal= "";
			String condition = "";
			List<String> row = ciTable.raw().get(i);
			className=row.get(1);
			attrName=row.get(2);
			ruleOpName=row.get(3);
			ruleVal=row.get(4);
			condition = row.get(5);
			BigDecimal classId = ciclsU.getCiClassId(className);
			BigDecimal attrId = tagRuleUtil.getAttrId(className, attrName);
			if (condition.isEmpty() || condition.compareToIgnoreCase("且")!=0){
				rulesObj = new JSONObject();
				items = new JSONArray();
				count++;
			}
			else
				rules.remove(count-1);
			JSONObject item = new JSONObject();
			item.put("classId", String.valueOf(classId));
			item.put("rltClassId", String.valueOf(classId));
			item.put("classAttrId", String.valueOf(attrId));
			item.put("ruleType", "1");
			item.put("ruleVal", ruleVal);
			String ruleOp = (new TagRuleUtil()).getRuleOp(ruleOpName);
			item.put("ruleOp", ruleOp);
			if (!attrName.isEmpty() && !ruleOp.isEmpty() && !ruleVal.isEmpty())
				items.put(item);

			rulesObj.put("items", items);
			JSONObject rule = new JSONObject();
			rule.put("classId", String.valueOf(classId));
			rulesObj.put("rule", rule);
			rules.put(rulesObj);
		}
		tagRuleInfo.put("rules", rules);
		if (ruleTypeName.compareToIgnoreCase(Contants.CIQUALITYRULE_CIATTRCOMPLIANCE)==0){//合规性
			JSONArray ciQualityRuleAttrs = new JSONArray();
			String className = ""; 
			String attrName = "";
			String cdtName = "";
			String cdtValue= "";
			int failRows = table.raw().size();
			for(int i=1;i<failRows;i++){
				List<String> row = table.raw().get(i);
				className=row.get(1);
				attrName=row.get(2);
				cdtName=row.get(3);
				cdtValue=row.get(4);
				JSONObject ciQualityRuleAttrsObj = new JSONObject();
				ciQualityRuleAttrsObj.put("classId", String.valueOf(ciclsU.getCiClassId(className)));
				BigDecimal attrId = tagRuleUtil.getAttrId(className, attrName);
				ciQualityRuleAttrsObj.put("attrId", String.valueOf(attrId));
				String cdtOp = (new TagRuleUtil()).getRuleOp(cdtName);
				ciQualityRuleAttrsObj.put("cdtOp", cdtOp);
				ciQualityRuleAttrsObj.put("cdtVal", cdtValue);
				ciQualityRuleAttrs.put(ciQualityRuleAttrsObj);
			}
			param.put("ciQualityRuleAttrs", ciQualityRuleAttrs);
		}
		if (ruleTypeName.compareToIgnoreCase(Contants.CIQUALITYRULE_CIATTRINTEGRITY)==0){//完整性
			JSONArray ciQualityRuleAttrs = new JSONArray();
			String className = ""; 
			String attrName = "";
			int failRows = table.raw().size();
			for(int i=1;i<failRows;i++){
				List<String> row = table.raw().get(i);
				className=row.get(1);
				attrName=row.get(2);
				JSONObject ciQualityRuleAttrsObj = new JSONObject();
				ciQualityRuleAttrsObj.put("classId", String.valueOf(ciclsU.getCiClassId(className)));
				BigDecimal attrId = tagRuleUtil.getAttrId(className, attrName);
				ciQualityRuleAttrsObj.put("attrId", String.valueOf(attrId));
				ciQualityRuleAttrsObj.put("cdtVal", "1");
				ciQualityRuleAttrs.put(ciQualityRuleAttrsObj);
			}
			param.put("ciQualityRuleAttrs", ciQualityRuleAttrs);
		}

		if (ruleTypeName.compareToIgnoreCase(Contants.CIQUALITYRULE_VERACITYOVERDUE)==0){//过期
			JSONArray ciQualityRuleRlts = new JSONArray();
			String className = ""; 
			int failRows = table.raw().size();
			for(int i=1;i<failRows;i++){
				List<String> row = table.raw().get(i);
				className=row.get(1);
				String day =row.get(2);
				String hour =row.get(3);
				String minute =row.get(4);
				JSONObject ciQualityRuleRltsObj = new JSONObject();
				ciQualityRuleRltsObj.put("classId", String.valueOf(ciclsU.getCiClassId(className)));
				ciQualityRuleRltsObj.put("rltExp", "{\"day\":\""+day+"\",\"hour\":\""+hour+"\",\"minute\":\""+minute+"\"}");
				ciQualityRuleRlts.put(ciQualityRuleRltsObj);
			}
			param.put("ciQualityRuleRlts", ciQualityRuleRlts);
		}

		if (ruleTypeName.compareToIgnoreCase(Contants.CIQUALITYRULE_VERACITYALONE)==0){//孤儿
			JSONArray ciQualityRuleRlts = new JSONArray();
			String className = ""; 
			int failRows = table.raw().size();
			JSONObject ciQualityRuleRltsObj = new JSONObject();
			JSONArray rlts = new JSONArray();
			String exp = "";
			count =0;
			for(int i=1;i<failRows;i++){
				List<String> row = table.raw().get(i);
				className=row.get(1);
				String name =row.get(2);
				String rltType =row.get(3);  
				String rltClsId =row.get(4);
				String condition = row.get(5);
				if (condition.isEmpty() || (condition.compareToIgnoreCase("且")!=0 &&condition.compareToIgnoreCase("或")!=0)){
					ciQualityRuleRltsObj = new JSONObject();
					rlts = new JSONArray();
					exp = "";
					count++;
				}
				else
					ciQualityRuleRlts.remove(count-1);

				JSONObject rltObj = new JSONObject();
				rltObj.put("check", "1");
				rltObj.put("name", name);
				rltObj.put("rltType", String.valueOf((new RltClassUtil()).getRltClassId(rltType)));
				rltObj.put("rltClsId", String.valueOf(ciclsU.getCiClassId(rltClsId)));

				rlts.put(rltObj);
				JSONObject rltExpObj = new JSONObject();
				rltExpObj.put("rlts", rlts);
				if (!condition.isEmpty() && condition.compareToIgnoreCase("且")==0 ){
					exp = exp+"&"+name;
				}else
					if (!condition.isEmpty() && condition.compareToIgnoreCase("或")==0 ){
						exp = exp+"|"+name;
					}else
						exp = name;
				rltExpObj.put("exp", exp);
				ciQualityRuleRltsObj.put("classId", String.valueOf(ciclsU.getCiClassId(className)));
				ciQualityRuleRltsObj.put("rltExp", rltExpObj.toString());
				ciQualityRuleRlts.put(ciQualityRuleRltsObj);
			}
			param.put("ciQualityRuleRlts", ciQualityRuleRlts);
		}

		param.put("ciQualityRule", ciQualityRule);
		param.put("tagRuleInfo", tagRuleInfo);
		return doRest(url, param.toString(), "POST");
	}
}
