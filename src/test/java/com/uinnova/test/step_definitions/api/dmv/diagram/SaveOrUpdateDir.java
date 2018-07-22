package com.uinnova.test.step_definitions.api.dmv.diagram;

import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.MyUtil;

/**
 * 编写时间:2017-12-04
 * 编写人:sunsl
 * 功能介绍:DMV我的新建文件夹类
 */
public class SaveOrUpdateDir extends RestApi{
    public JSONObject saveOrUpdateDir(String dirName){
    	String url=":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDir";
    	JSONObject param = new JSONObject();
		param.put("dirName", dirName);
		param.put("parentId", 0);
    	return doRest(url, param.toString(), "POST");
    }
    public JSONObject saveOrUpdateDirAgain(String dirName, String kw){
    	String url=":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDir";
    	JSONObject param = new JSONObject();
		param.put("dirName", dirName);
		param.put("parentId", 0);
    	return doFailRest(url, param.toString(), "POST", kw);
    }
    
    public JSONObject saveOrUpdateDir(HashMap dirHashMap,String updateDirName,String dirName){
    	String url=":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDir";
    	MyUtil myUtil = new MyUtil();
    	JSONObject param = new JSONObject();
		param.put("createTime", dirHashMap.get("CREATE_TIME"));
		param.put("creator", dirHashMap.get("CREATOR"));
		param.put("dataStatus", dirHashMap.get("DATA_STATUS"));
		param.put("dirLvl", dirHashMap.get("DIR_LVL"));
		param.put("dirName", updateDirName);
		param.put("dirPath", dirHashMap.get("DIR_PATH"));
		param.put("domainId", dirHashMap.get("DOMAIN_ID"));
		param.put("id", myUtil.getDirIdByName(dirName));
		param.put("isLeaf", dirHashMap.get("IS_LEAF"));
		param.put("modifier", dirHashMap.get("MODIFIER"));
		param.put("modifyTime", dirHashMap.get("MODIFY_TIME"));
		param.put("parentId", dirHashMap.get("PARENT_ID"));
		param.put("userId", dirHashMap.get("USER_ID"));
        return doRest(url, param.toString(), "POST");
    }
}
