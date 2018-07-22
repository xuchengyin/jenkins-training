package com.uinnova.test.step_definitions.api.dcv.asset;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

public class Search extends RestApi{
    public BigDecimal jsonId;
    public String term;
    public BigDecimal sc;
    public HashMap<String, String> strMap;
    
	public JSONObject search(BigDecimal jsonId, String term, BigDecimal sc, HashMap<String, String> strMap){
		this.jsonId = jsonId;
		this.term = term;
		this.sc = sc;
		this.strMap = strMap;
        String url = ":1511/tarsier-vmdb/dcv/asset/search";
        JSONObject parameter = new JSONObject();
        
        parameter.put("jsonId", jsonId);
        parameter.put("term", term);
        parameter.put("strMap", strMap);
        parameter.put("sc", sc);
        parameter.put("pageSize", 20);
        parameter.put("pageNum", 1);
        return doRest(url, parameter.toString(), "POST");
    }
}
