package com.uinnova.test.step_definitions.api.cmv.progress;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class GetProgressInfoByKey extends RestApi{

	/**
	 * @author ldw
	 * @param 自动化构建传参 : autosaveRlt
	 * @return 
	 * {
		  "data" : {
		    "resultData" : [ //保存结果，数组,数组下标为0表示保存数量,1表示更新数量
		      0, //新增0个
		      4   //更新3个
		    ],
		    "percentage" : 0, //这里可忽略
		    "status" : -1,  //此状态可作为进程执行是否完毕的依据，0 准备或者理解为正在执行， -1执行完毕
		    "totalRecords" : 0, //这里可忽略
		    "processedRecords" : 0, //这里可忽略
		    "key" : "autosaveRlt" //查询进程的key值
		  },
		  "code" : -1,
		  "success" : true
		}
	 * */
	public JSONObject getProgressInfoByKey(String key){
		String url = ":1511/tarsier-vmdb/cmv/progress/getProgressInfoByKey";
		return doRest(url, key, "POST");
	}
}
