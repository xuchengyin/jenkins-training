package com.uinnova.test.step_definitions.api.dmv.group;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/**
 * 编写时间:2017-11-21
 * 编写人:sunsl
 * 功能介绍:添加小组
 */
public class AddImage {
   public JSONObject addImage(String filePath){
	   String url=":1511/tarsier-vmdb/dmv/group/addImage";
	   
	   Map<String,String> uploadFileMap = new HashMap<String,String>();
	   uploadFileMap.put(filePath, "groupImage");
	   String result = QaUtil.uploadfiles(url, uploadFileMap, null);
	   AssertResult as = new AssertResult();
	   return as.assertRes(result);
   }
}
