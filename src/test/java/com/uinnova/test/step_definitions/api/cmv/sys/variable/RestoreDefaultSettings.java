package com.uinnova.test.step_definitions.api.cmv.sys.variable;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2018-05-16
 * 编写人:sunsl
 * 功能介绍:还原图标功能接口
 */
public class RestoreDefaultSettings extends RestApi{
     public JSONObject restoreDefaultSettings(String varName,String varType,String varValue,String varDesc){
    	 String url =":1511/tarsier-vmdb/cmv/sys/variable/restoreDefaultSettings";
    	 JSONObject param = new JSONObject();
    	 param.put("varName", varName);
    	 param.put("varType", varType);
    	 param.put("varValue", varValue);
    	 param.put("varDesc", varDesc);
    	 return doRest(url,param.toString(),"POST");
     }
}
