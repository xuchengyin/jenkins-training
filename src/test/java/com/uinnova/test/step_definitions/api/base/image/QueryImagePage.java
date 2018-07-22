package com.uinnova.test.step_definitions.api.base.image;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-10-31
 * 编写人:sunsl
 * 功能介绍:图标管理搜索功能
 */
public class QueryImagePage extends RestApi{
	/**
	 * @param searchKey
	 * @return
	 */
	public JSONObject queryImagePage(String searchKey){
		String url=":1511/tarsier-vmdb/cmv/image/queryImagePage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("imgName", searchKey);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 60);
		param.put("orders", "DIR_ID,ORDER_NO,IMG_NAME");
		return doRest(url, param.toString(), "POST");
	}
	
	/**
	 * @author ldw
	 * 2018/6/7 修改  分页查询2DImage数据  唯一改变是仅返回2D图标
	 * 
	 * */
	
	public JSONObject query2DImagePage(String searchKey){
		String url=":1511/tarsier-vmdb/cmv/image/query2DImagePage";
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("imgName", searchKey);
		param.put("cdt", cdt);
		param.put("pageNum", 1);
		param.put("pageSize", 60);
		param.put("orders", "DIR_ID,ORDER_NO,IMG_NAME");
		return doRest(url, param.toString(), "POST");
	}
}
