package com.uinnova.test.step_definitions.api.dmv.feedback;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-03-15
 * 编写人:sunsl
 * 功能介绍:用户反馈功能类
 */
public class SaveFeedback extends RestApi{
   public JSONObject saveFeedback(String param){
	   String url = ":1511/tarsier-vmdb/dmv/feedback/saveFeedback";
	   return doRest(url, param,"POST");
   }
}
