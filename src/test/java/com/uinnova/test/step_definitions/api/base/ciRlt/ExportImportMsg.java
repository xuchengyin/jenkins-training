package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportImportMsg {
	public String exportImportMsg(String fileName){
		String url = ":1511/tarsier-vmdb/cmv/cirltBatchExcelImport/exportImportMsg?fileName="+fileName;
		String filePath = ExportImportMsg.class.getResource("/").getPath()+"testData/download/"+fileName;
		Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
		if(result){
			return filePath; 
		}else{
			return null;
		}
	}
}
