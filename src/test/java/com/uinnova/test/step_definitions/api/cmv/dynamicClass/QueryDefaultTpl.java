package com.uinnova.test.step_definitions.api.cmv.dynamicClass;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class QueryDefaultTpl extends RestApi{
//	返回结果格式：
//	CcDynamicClassTpl //查询结果
//	 {
//	 domainId : Long, //所属域[DOMAIN_ID]
//	 modifyTime : Long, //修改时间[MODIFY_TIME] 修改时间:yyyyMMddHHmmss
//	 dataStatus : Integer, //数据状态[DATA_STATUS] 数据状态:1-正常 0-删除
//	 creator : String, //创建人[CREATOR]
//	 createTime : Long, //创建时间[CREATE_TIME] 创建时间:yyyyMMddHHmmss
//	 tplName : String, //模板名称[TPL_NAME]
//	 status : Integer, //模版状态[STATUS] 模版状态: 1=有效 0=无效
//	 id : Long, //ID[ID]
//	 levelName6 : String, //分级名称_6[LEVEL_NAME_6]
//	 levelName5 : String, //分级名称_5[LEVEL_NAME_5]
//	 levelName4 : String, //分级名称_4[LEVEL_NAME_4]
//	 levelName3 : String, //分级名称_3[LEVEL_NAME_3]
//	 tplDesc : String, //模板描述[TPL_DESC]
//	 levelName2 : String, //分级名称_2[LEVEL_NAME_2]
//	 modifier : String, //修改人[MODIFIER]
//	 levelName1 : String //分级名称_1[LEVEL_NAME_1]
//	 } 
	
	/**
	 * @author ldw
	 * 2018/5/29  参数改为与SaveOrUpdateDefaultTpl一样，但是由于不读参数，所以此处先不修改
	 * 
	 * */
	public JSONObject queryDefaultTpl(){
		String url = ":1511/tarsier-vmdb/cmv/dynamicClass/queryDefaultTpl";
		return doRest(url, "", "POST");
	}
}
