package com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportImportIgnoreExcel extends RestApi{

	public String exportImportIgnoreExcel(String fileName){
		String url = ":1511/tarsier-vmdb/cmv/cirltBatchExcelImport/exportImportIgnoreExcel?fileName="+fileName;
//		String filePath = ExportImportIgnoreExcel.class.getResource("/").getPath()+"/testData/download/cirltBatchExcelImport/exportImportIgnoreExcel/"+fileName;
		String filePath = ExportImportIgnoreExcel.class.getResource("/").getPath()+"/testData/download/"+fileName;
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}
