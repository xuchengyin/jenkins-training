package com.uinnova.test.step_definitions.api.cmv.monitor.severity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2018-01-19
 * 编写人:sunsl
 * 功能介绍:事件级别定义上传音频文件类
 */
public class UploadVoice {
   public JSONObject uploadVoice(String filePath){
	   String url = ":1511/tarsier-vmdb/cmv/monitor/severity/uploadVoice";
	   Map<String,String> uploadFileMap = new HashMap<String,String>();
	   uploadFileMap.put(filePath, "file");
	   String result = QaUtil.uploadfiles(url, uploadFileMap, null);
	   AssertResult as = new AssertResult();
	   return as.assertRes(result);
   }
}
