package com.uinnova.test.step_definitions.api.noah.rest;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
public class EventtomqImport extends RestApi{
	public JSONObject eventtomqImport(String param){
		   String url =":1532/rest/tarsier/rest/event/mq/import";
		   return doRest(url, param, "POST");
	   }

}
