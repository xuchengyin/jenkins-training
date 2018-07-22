package com.uinnova.test.step_definitions.api.cmv.ciRltRule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author wsl
 * 保存朋友圈规则
 *
 */
public class SaveOrUpdateDefAndRule extends RestApi{
	/**
	 * @param defName  朋友圈名称
	 * @param sourceClsName  触发ciCLass
	 * @param targetClsName  目的ciClass
	 * @param rltClsName  关系名称
	 * @return
	 */
	public JSONObject saveOrUpdateDefAndRuleHasEntrance(String sourceClsName, String targetClsName, String rltClsName, String defName){
		String url = ":1511/tarsier-vmdb/cmv/ciRltRule/saveOrUpdateDefAndRule";
		JSONObject param = saveOrUpdateDefAndRule(sourceClsName, targetClsName, rltClsName, defName, true);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param defName  朋友圈名称
	 * @param sourceClsName  触发ciCLass
	 * @param targetClsName  目的ciClass
	 * @param rltClsName  关系名称
	 * @return
	 */
	public JSONObject saveOrUpdateDefAndRuleHasEntrance(String sourceClsName, String targetClsName, String rltClsName, String defName,String updateDefName){
		String url = ":1511/tarsier-vmdb/cmv/ciRltRule/saveOrUpdateDefAndRule";
		JSONObject param = saveOrUpdateDefAndRule(sourceClsName, targetClsName, rltClsName, defName, updateDefName,true);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param sourceClsName
	 * @param targetClsName
	 * @param rltClsName
	 * @param defName
	 * @return
	 * 建立无入口朋友圈
	 */
	public JSONObject saveOrUpdateDefAndRuleNoEntrance(String sourceClsName, String targetClsName, String rltClsName, String defName){
		String url = ":1511/tarsier-vmdb/cmv/ciRltRule/saveOrUpdateDefAndRule";
		JSONObject param = saveOrUpdateDefAndRule(sourceClsName, targetClsName, rltClsName, defName, false);
		return doRest(url, param.toString(), "POST");
	}

	/**
	 * @param sourceClsName
	 * @param targetClsName
	 * @param rltClsName
	 * @param defName
	 * @return
	 * 建立无入口朋友圈
	 */
	public JSONObject saveOrUpdateDefAndRuleNoEntrance(String sourceClsName, String targetClsName, String rltClsName, String defName,String updatefriendName){
		String url = ":1511/tarsier-vmdb/cmv/ciRltRule/saveOrUpdateDefAndRule";
		JSONObject param = saveOrUpdateDefAndRule(sourceClsName, targetClsName, rltClsName, defName, updatefriendName,false);
		return doRest(url, param.toString(), "POST");
	}

	private JSONObject saveOrUpdateDefAndRule(String sourceClsName, String targetClsName, String rltClsName, String defName, boolean hasEntrance){
		CiClassUtil ciClsUtil = new CiClassUtil();
		BigDecimal sourceClsId = ciClsUtil.getCiClassId(sourceClsName);
		BigDecimal targetClsId = ciClsUtil.getCiClassId(targetClsName);
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClsId = rltClassUtil.getRltClassId(rltClsName);
		JSONArray lines = new JSONArray();
		JSONObject lineObj = new JSONObject();
		lineObj.put("clsRltTypeId", String.valueOf(rltClsId));
		lineObj.put("clsStartId", String.valueOf(sourceClsId));
		lineObj.put("clsEndId", String.valueOf(targetClsId));
		lineObj.put("nodeStartId", 1);
		lineObj.put("nodeEndId", 2);
		lines.put(lineObj);

		JSONArray nodes = new JSONArray();
		JSONObject sourceNodeObj = new JSONObject();
		sourceNodeObj.put("nodeClassId", sourceClsId);
		sourceNodeObj.put("pageNodeId", 1);
		sourceNodeObj.put("x", 400);
		sourceNodeObj.put("y", 100);
		JSONObject nodeObj1 = new JSONObject();
		nodeObj1.put("node", sourceNodeObj);
		nodes.put(nodeObj1);

		JSONObject targetNodeObj = new JSONObject();
		targetNodeObj.put("nodeClassId", targetClsId);
		targetNodeObj.put("pageNodeId", 2);
		targetNodeObj.put("x", 500);
		targetNodeObj.put("y", 300);
		JSONObject nodeObj2 = new JSONObject();
		nodeObj2.put("node", targetNodeObj);
		nodes.put(nodeObj2);

		JSONObject def = new JSONObject();
		if (hasEntrance){
			def.put("defType", 1);
			def.put("classId", sourceClsId);
			def.put("pageNodeId", 1);
		}else{
			def.put("defType", 2);
		}
		def.put("defName", defName);
		JSONObject param = new JSONObject();
		param.put("lines", lines);
		param.put("nodes", nodes);
		param.put("def", def);
		return param;
	}

	private JSONObject saveOrUpdateDefAndRule(String sourceClsName, String targetClsName, String rltClsName, String defName,String updateDefName, boolean hasEntrance){
		String sql = "";
		if(hasEntrance){
			sql = "SELECT ID FROM cc_rlt_rule_def WHERE DEF_NAME = '" + defName + "' AND DEF_TYPE = 1 AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
		}else{
			sql = "SELECT ID FROM cc_rlt_rule_def WHERE DEF_NAME = '" + defName + "' AND DEF_TYPE = 2 AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
		}
		List list = JdbcUtil.executeQuery(sql);
		BigDecimal id = new BigDecimal(0);
		if(list !=null && list.size()>0){
			HashMap map =(HashMap)list.get(0);
			id = (BigDecimal)map.get("ID");
		}


		CiClassUtil ciClsUtil = new CiClassUtil();
		BigDecimal sourceClsId = ciClsUtil.getCiClassId(sourceClsName);
		BigDecimal targetClsId = ciClsUtil.getCiClassId(targetClsName);
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClsId = rltClassUtil.getRltClassId(rltClsName);
		JSONArray lines = new JSONArray();
		JSONObject lineObj = new JSONObject();
		lineObj.put("clsRltTypeId", String.valueOf(rltClsId));
		lineObj.put("clsStartId", String.valueOf(sourceClsId));
		lineObj.put("clsEndId", String.valueOf(targetClsId));
		lineObj.put("nodeStartId", 1);
		lineObj.put("nodeEndId", 3);
		lines.put(lineObj);

		JSONArray nodes = new JSONArray();
		JSONObject sourceNodeObj = new JSONObject();
		sourceNodeObj.put("nodeClassId", sourceClsId);
		sourceNodeObj.put("pageNodeId", 1);
		sourceNodeObj.put("x", 400);
		sourceNodeObj.put("y", 100);
		JSONObject nodeObj1 = new JSONObject();
		nodeObj1.put("node", sourceNodeObj);
		nodes.put(nodeObj1);

		JSONObject targetNodeObj = new JSONObject();
		targetNodeObj.put("nodeClassId", targetClsId);
		targetNodeObj.put("pageNodeId", 2);
		targetNodeObj.put("x", 500);
		targetNodeObj.put("y", 300);
		JSONObject nodeObj2 = new JSONObject();
		nodeObj2.put("node", targetNodeObj);
		nodes.put(nodeObj2);

		JSONObject def = new JSONObject();
		if (hasEntrance){
			def.put("defType", 1);
			def.put("classId", sourceClsId);
			def.put("pageNodeId", 1);
			def.put("id", id);
		}else{
			def.put("defType", 2);
			def.put("id", id);
		}
		def.put("defName", updateDefName);
		JSONObject param = new JSONObject();
		param.put("lines", lines);
		param.put("nodes", nodes);
		param.put("def", def);
		return param;
	}
	//{"lines":[{"clsRltTypeId":"100000000001586","clsStartId":100000000001583,"clsEndId":100000000001584,"nodeStartId":1,"nodeEndId":3}],"nodes":[{"node":{"nodeClassId":100000000001583,"pageNodeId":1,"x":400,"y":100}},{"node":{"nodeClassId":100000000001584,"pageNodeId":3,"x":576,"y":291}}],"def":{"modifier":"管理员[admin]","id":100000000001538,"defName":"新建无源朋友圈规则","createTime":20180129162116,"validErrMsg":"","domainId":1,"dataStatus":1,"modifyTime":20180129162116,"useStatus":1,"defType":2,"creator":"管理员[admin]"}}

	//{"nodes":[{"node":{"x":400,"nodeClassId":100000000001593,"y":100,"pageNodeId":1}},{"node":{"x":500,"nodeClassId":100000000001594,"y":300,"pageNodeId":2}}],"def":{"defType":2,"defName":"修改后最大无源uuutru","id":0},"lines":[{"clsStartId":"100000000001593","nodeEndId":3,"nodeStartId":1,"clsRltTypeId":"100000000001596","clsEndId":"100000000001594"}]}
}
