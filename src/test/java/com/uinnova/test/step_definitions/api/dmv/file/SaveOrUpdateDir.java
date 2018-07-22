package com.uinnova.test.step_definitions.api.dmv.file;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

/**
 * 编写时间:2018-04-20
 * 编写人:sunsl
 * 功能介绍:新建或者重命名目录
 */
public class SaveOrUpdateDir extends RestApi{
    public JSONObject saveOrUpdateDir(String dirName,BigDecimal parentId){
    	String url = ":1511/tarsier-vmdb/dmv/file/saveOrUpdateDir";
    	JSONObject param = new JSONObject();
    	param.put("dirName", dirName);
    	param.put("parentId", parentId);
    	return doRest(url,param.toString(),"POST");
    }
    
    public JSONObject saveOrUpdateDirAgain(String dirName, String kw){
    	String url = ":1511/tarsier-vmdb/dmv/file/saveOrUpdateDir";
    	JSONObject param = new JSONObject();
    	QueryFileRootDir queryFileRootDir = new QueryFileRootDir();
  	    JSONObject result = queryFileRootDir.queryFileRootDir();
  	    JSONObject data = result.getJSONObject("data");
  	    BigDecimal parentId =data.getBigDecimal("id");
    	param.put("dirName", dirName);
    	param.put("parentId", parentId);
    	return doFailRest(url, param.toString(), "POST", kw);
    }
    
    public JSONObject saveOrUpdateDir(String oldDirName,String newDirName,BigDecimal parentId){
    	String url = ":1511/tarsier-vmdb/dmv/file/saveOrUpdateDir";
    	FormUtil formUtil = new FormUtil();
    	BigDecimal dirId = formUtil.getDirIdByName(oldDirName);
    	JSONObject param = new JSONObject();
    	param.put("id", dirId);
    	param.put("parentId", parentId);
    	param.put("dirName", newDirName);
    	return doRest(url,param.toString(),"POST");
    }
}
