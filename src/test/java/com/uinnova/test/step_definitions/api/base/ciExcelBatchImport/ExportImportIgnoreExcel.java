package com.uinnova.test.step_definitions.api.base.ciExcelBatchImport;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 导入分类处的导出功能
 * @author wsl
 *
 */
public class ExportImportIgnoreExcel {
	
	/**
	 * @return
	 */
	public String exportImportIgnoreExcel() {
		String url = ":1511/tarsier-vmdb/cmv/ciExcelBatchImport/exportImportIgnoreExcel";
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"/testData/download/ciExcelBatchImport/ciDataImport"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}
