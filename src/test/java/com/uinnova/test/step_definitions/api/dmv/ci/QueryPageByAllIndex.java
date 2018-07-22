package com.uinnova.test.step_definitions.api.dmv.ci;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编辑时间:2017-12-26
 * 编辑人:sunsl
 * 功能介绍:
 */
public class QueryPageByAllIndex extends RestApi{
   public JSONObject queryPageByAllIndex(String words){
	   String url = ":1511/tarsier-vmdb/dmv/ci/queryPageByAllIndex";
	   JSONObject param = new JSONObject();
	   JSONObject cdt = new JSONObject();
	   JSONArray wordArray = new JSONArray();  
	    
	   param.put("pageNum", 1);
	   param.put("pageSize", 20);
	   String[] strs ={};
	   if(words.contains(",")){
		   strs=words.split(",");
	   }else{
		   if(words.matches("^[a-zA-Z]*")){
			   words = words.toUpperCase();
		   }
		   wordArray.put(words);
	   }
	   for(int i = 0; i <strs.length; i ++ ){
		   if(strs[i].matches("^[a-zA-Z]*")){
			   strs[i] = strs[i].toUpperCase();
		   }
		   wordArray.put(strs[i]);
	   }
	   cdt.put("words", wordArray);
	   cdt.put("isMore", 0);
	   param.put("cdt",cdt);
	   return doRest(url, param.toString(), "POST");
   }
   
}
