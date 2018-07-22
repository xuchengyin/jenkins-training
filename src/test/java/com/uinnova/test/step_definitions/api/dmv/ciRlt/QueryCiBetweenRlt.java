package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;

/**
 * 编写时间:2017-12-08
 * 编写人:sunsl
 * 功能介绍:DMV我的查看视图Ci关系类
 */
public class QueryCiBetweenRlt extends RestApi{
	public JSONObject queryCiBetweenRlt(String  rltClassName,String sourceCode,String targerCode){
		String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryCiBetweenRlt";
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		JSONObject param = new JSONObject();
		JSONArray ids = new JSONArray();
		ids.put(rltClassId);
		CiUtil ciUtil = new CiUtil();
		BigDecimal sourcID = ciUtil.getCiId(sourceCode);
		BigDecimal targerID = ciUtil.getCiId(targerCode);
		ids.put(sourcID);
		ids.put(targerID);
		param.put("ids", ids);
		return doRest(url, param.toString(), "POST");
	}


	/**
	 * @param rltClassId
	 * @param sourceCode
	 * @param targerId
	 * @return
	 * 查询画面上所有分类的关系 因为调用的地方直接都是id，所以此处使用id参数
	 */
	public JSONObject queryMultiCiBetweenRlt(String[] ciCodeIds){
		JSONArray ids = new JSONArray();
		for (int i=0; i<ciCodeIds.length; i++){
			ids.put(ciCodeIds[i]);
		}
		return queryCiBetweenRlt(ids);
	}


	/**
	 * @param ciCodes
	 * @return
	 * 
	 */
	public JSONObject queryMultiCiBetweenRlt(String ciCode1, String ciCode2){
		JSONArray ids = new JSONArray();
		CiUtil ciUtil = new CiUtil();
		ids.put(String.valueOf(ciUtil.getCiId(ciCode1)));
		ids.put(String.valueOf(ciUtil.getCiId(ciCode2)));
		return queryCiBetweenRlt(ids);
	}
	
	/**
	 * 调用接口
	 * @param ids
	 * @return
	 */
	private JSONObject queryCiBetweenRlt(JSONArray ids){
		String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryCiBetweenRlt";
		JSONObject param = new JSONObject();
		param.put("ids", ids);
		JSONArray rltQs = new JSONArray();
		rltQs.put("CI_RLt_ATTR");
		param.put("ciRltQs", rltQs);
		return doRest(url, param.toString(), "POST");
	} 
}
