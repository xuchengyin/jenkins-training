package com.uinnova.test.step_definitions.api.noah.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

import cucumber.api.DataTable;

/**
 * @author kyn
 * NOAH推送ci性能数据
 *
 */
public class PushPerfData extends RestApi{

	public JSONObject pushPerfData(String perfdate){
		
		String url = ":1532/rest/tarsier";
		return doRest(url,perfdate, "POST");

	}
	

}
