package com.uinnova.test.step_definitions.api.dcv.dcCiClassMp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.uinnova.test.step_definitions.api.RestApi;

public class SaveOrUpdateDcCiMp extends RestApi{
	public JSONObject saveOrUpdateDcMp(){
		String url = "1511/tarsier-vmdb/dcv/dcCiClassMp/saveOrUpdateDcCiMp";
		JSONObject parameter = new JSONObject();
		JSONObject attr = new JSONObject();
		JSONArray attrMps = new JSONArray();
		JSONArray tagIds = new JSONArray();
		ArrayList list = new ArrayList();
		list.add("123");
		list.add("456");
		
		for(int i = 0; i < list.size(); i++){
			attr.put("dcvAttrId", list.get(i));
			attr.put("vmdbAttrId", list.get(i));
			attrMps.put(attr);
			attr = null;
			attr = new JSONObject();
		}
		
		parameter.put("name",111);
		parameter.put("mpId",15);
		parameter.put("classType",1);
		parameter.put("dcvClassId",15);
		parameter.put("vmdbClassId",15);
		parameter.put("tagIds",tagIds);
		parameter.put("attrMps", attrMps);
		return doRest(url, parameter.toString(), "POST");
	}

}
