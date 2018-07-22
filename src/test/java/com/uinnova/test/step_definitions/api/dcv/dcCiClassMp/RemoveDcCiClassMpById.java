package com.uinnova.test.step_definitions.api.dcv.dcCiClassMp;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class RemoveDcCiClassMpById extends RestApi{
	public JSONObject removeDcCiClassMpById(String classMpId){
		String url = "1511/tarsier-vmdb/dcv/dcCiClassMp/removeDcCiClassMpById";
		String parameter = classMpId;
		return doRest(url, parameter, "POST");
	}

}
