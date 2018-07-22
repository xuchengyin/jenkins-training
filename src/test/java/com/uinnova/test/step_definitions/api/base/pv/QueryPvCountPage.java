package com.uinnova.test.step_definitions.api.base.pv;

import java.util.ArrayList;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 分页查询系统流量统计信息
 * @author wsl
 * 2018-3-16
 */
public class QueryPvCountPage extends RestApi{
	
	/**
	 * @param startModifyTime   格式：yyyyMMddHHmmss
	 * @param endModifyTime
	 * @param targetName
	 * @param targetModuCode
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public JSONObject queryPvCountPage(Long startModifyTime, Long endModifyTime,  String targetName, ArrayList targetModuCode, int pageNum, int pageSize){
		String url =":1511/tarsier-vmdb/cmv/pv/queryPvCountPage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
//		cdt.put("startModifyTime", startModifyTime);
//		cdt.put("endModifyTime", endModifyTime);
		cdt.put("targetName", targetName);
		cdt.put("targetModuCodes", targetModuCode);
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		param.put("orders", "");//"排序字段默认是按照模块CODE排序"
		param.put("cdt", cdt);
		return this.doRest(url, param.toString(), "POST");
	}
}
