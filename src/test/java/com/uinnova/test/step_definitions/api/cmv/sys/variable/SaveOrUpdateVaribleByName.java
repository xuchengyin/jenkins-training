package com.uinnova.test.step_definitions.api.cmv.sys.variable;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class SaveOrUpdateVaribleByName extends RestApi{
	
	public JSONObject saveOrUpdateVaribleByName(String varName, String varValue, String varDesc){
//		{
//			varName:SystemVariableNamed,//变量名
//			VarValue:String,//变量值（图标的url）
//			VarDesc:String,//变量描述
//			
//		}
		JSONObject param = new JSONObject();
		param.put("varName", varName);
		param.put("VarValue", varValue);
		param.put("VarDesc", varDesc);
		String url = ":1511/tarsier-vmdb/cmv/sys/variable/saveOrUpdateVaribleByName";
		return doRest(url, param.toString(), "POST");
	}
	
	
	public JSONObject saveOrUpdateVaribleByNameFailed(String varName, String varValue, String varDesc, String kw){
		JSONObject param = new JSONObject();
		param.put("varName", varName);
		param.put("VarValue", varValue);
		param.put("VarDesc", varDesc);
		String url = ":1511/tarsier-vmdb/cmv/sys/variable/saveOrUpdateVaribleByName";
		return doFailRest(url, param.toString(), "POST", kw);
	}
}
