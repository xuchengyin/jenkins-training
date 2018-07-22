package com.uinnova.test.step_definitions.api.dmv.file;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

/**
 * 编写时间:2018-04-20
 * 编写人:sunsl
 * 功能介绍: 上传文件，可以上传多个 
 */
public class UploadFiles {	
  public JSONObject uploadFiles(String filePath,String dirName){
	  String url = ":1511/tarsier-vmdb/dmv/file/uploadFiles";
	  FormUtil formUtil = new FormUtil();
	  Map<String,String> uploadFileMap = new HashMap<String,String>();
	  Map<String, String> textMap = new HashMap<String,String>();
	  uploadFileMap.put(filePath, "file");
	  textMap.put("dirId", formUtil.getDirIdByName(dirName).toString());
	  String result = QaUtil.uploadfiles(url, uploadFileMap, textMap);
	  AssertResult as = new AssertResult();
	  return as.assertRes(result);
  }
}
