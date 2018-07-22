package com.uinnova.test.step_definitions.api.dmv.feedback;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2018-03-16
 * 编写人:sunsl
 * 功能介绍:导出反馈功能
 */
public class ExportFeedback {
   public String exportFeedback(){
	   String url =":1511/tarsier-vmdb/dmv/feedback/exportFeedback";
	   String filePath = ExportFeedback.class.getResource("/").getPath() + "testData/download/" + "dmv反馈" + ".xls";
	   Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
	   if(result){
		   return filePath; 
	   }else{
		   return null;
	   }
   }
}
