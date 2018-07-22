package com.uinnova.test.step_definitions.api.dmv.file;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

/**
 * 编写时间:2018-04-20
 * 编写人:sunsl
 * 功能介绍:通过文件id删除文件，可以删除多个
 */
public class RemoveFileByIds extends RestApi{
   public JSONObject removeFileByIds(List fileNameList){
	   String url = ":1511/tarsier-vmdb/dmv/file/removeFileByIds";
	   FormUtil formUtil = new FormUtil();
	   JSONArray fileNames = new JSONArray();
	   for(int i =0; i < fileNameList.size(); i++){
		   String fileName = (String)fileNameList.get(i);
		   BigDecimal fileId = formUtil.getFormIdByFormName(fileName);
		   fileNames.put(fileId);
	   }
	   return doRest(url,fileNames.toString(),"POST");
   }
	
}
