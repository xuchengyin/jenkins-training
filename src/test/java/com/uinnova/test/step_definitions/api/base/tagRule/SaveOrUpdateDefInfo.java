package com.uinnova.test.step_definitions.api.base.tagRule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;

public class SaveOrUpdateDefInfo extends RestApi{

	/**
	 * 创建tagRule
	 * @param dirName
	 * @param tagName
	 * @return
	 */
	public JSONObject saveDefInfo(String dirName,String tagName){
		String url = ":1511/tarsier-vmdb/cmv/tagRule/saveOrUpdateDefInfo";
		BigDecimal dirId = (new TagRuleUtil()).getDirId(dirName);
		if(dirId.compareTo(new BigDecimal(0))==0){
			JSONObject sd = (new SaveOrUpdateDir()).saveOrUpdateDir(dirName);
			dirId = sd.getBigDecimal("data");
		}
		JSONObject param = new JSONObject();
		JSONObject def = new JSONObject();
		def.put("dirId", String.valueOf(dirId));
		def.put("id", "");
		def.put("tagName", tagName);
		def.put("tagType", 1);
		param.put("def", def);
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * 更新tagRule
	 * @param sourceTagName
	 * @param targetTagName
	 * @return
	 */
	public JSONObject UpdateDefInfo(String sourceTagName,String targetTagName){
		String url = ":1511/tarsier-vmdb/cmv/tagRule/saveOrUpdateDefInfo";
		BigDecimal tagId = (new TagRuleUtil()).getTagId(sourceTagName);
		BigDecimal dirId = (new TagRuleUtil()).getDirIdByTagName(sourceTagName);
		JSONObject param = new JSONObject();
		JSONObject def = new JSONObject();
		def.put("dirId", String.valueOf(dirId));
		def.put("id", String.valueOf(tagId));
		def.put("tagName", targetTagName);
		def.put("tagType", 1);
		param.put("def", def);
		JSONArray rules = new JSONArray();
		param.put("rules", rules);
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * 创建tagRuleItem
	 * @param tagName
	 * @param ruleTable
	 * @return
	 */
	public JSONObject saveDefInfo(String tagName,DataTable ruleTable){
		String url = ":1511/tarsier-vmdb/cmv/tagRule/saveOrUpdateDefInfo";
		BigDecimal dirId = (new TagRuleUtil()).getDirIdByTagName(tagName);
		BigDecimal tagId = (new TagRuleUtil()).getTagId(tagName);
		//构造参数
		JSONObject param = new JSONObject();
		JSONObject def = new JSONObject();
		JSONArray rules  =  new JSONArray();
		//构建def
		def.put("dirId", String.valueOf(dirId));
		def.put("id", tagId);
		def.put("tagName", tagName);
		def.put("tagType", 1);

		//获取rules
		List<Map<String,String>> ruleTableList = ruleTable.asMaps(String.class, String.class);
		//			ArrayList<String> ruleNumList = new ArrayList<String>();
		//			for(int i=0;i<ruleTableList.size();i++){
		//				String ruleNum = ruleTableList.get(i).get("ruleNum");
		//				ruleNumList.add(ruleNum);
		//			}
		HashMap<String,Integer> ruleNumMap = QaUtil.getNumMap(ruleTable,"ruleNum");
		//			HashMap<String,Integer> ruleNumMap = (new tagRuleUtil()).getRuleNumMap(ruleTable,"ruleNum");
		//			HashMap<String,Integer> ruleNumMap = (new tagRuleUtil()).getRuleNumMap(ruleTable);
		//			HashMap<String,Integer> ruleNumMap = getStringNum(ruleNumList);

		JSONObject ruleObj = null;
		JSONArray items = null;
		JSONObject item = null;
		for(int j=0;j<ruleTableList.size();j++){
			String ruleNum = ruleTableList.get(j).get("ruleNum");
			String ciClsName = ruleTableList.get(j).get("ciClsName");
			int ruleNumCount = ruleNumMap.get(ruleNum);
			ruleObj = new JSONObject();
			JSONObject rule = new JSONObject();
			items =  new JSONArray();
			for(int rc=0;rc<ruleNumCount;rc++){
				String attrName = ruleTableList.get(j).get("attrName");
				String rltClsName = ruleTableList.get(j).get("ciFriendName");
				String ruleOpName = ruleTableList.get(j).get("ruleOp");
				String ruleOp = (new TagRuleUtil()).getRuleOp(ruleOpName);
				String ruleVal = ruleTableList.get(j).get("ruleVal");
				if(rc==0){
					rule.put("classId", (new CiClassUtil()).getCiClassId(ciClsName));
					ruleObj.put("rule", rule);
				}
				item = new JSONObject();
				item.put("classAttrId", (new TagRuleUtil()).getAttrId(ciClsName, attrName));
				item.put("classId", (new CiClassUtil()).getCiClassId(ciClsName));
				item.put("rltClassId", (new CiClassUtil()).getCiClassId(rltClsName));
				item.put("ruleOp", ruleOp);
				item.put("ruleType","1");
				item.put("ruleVal", ruleVal);
				items.put(item);
				j++;
			}
			j = j-1;
			ruleObj.put("items", items);
			rules.put(ruleObj);
		}
		param.put("def", def);
		param.put("rules", rules);
		return doRest(url, param.toString(), "POST");
	}

}
