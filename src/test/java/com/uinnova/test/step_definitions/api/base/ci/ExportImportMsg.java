package com.uinnova.test.step_definitions.api.base.ci;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportImportMsg {
	
	/**
	 * @param fileName
	 * @return
	 */
	public String exportImportMsg(String fileName) {
		String url = ":1511/tarsier-vmdb/cmv/ci/exportImportMsg?fileName="+fileName;
		String filePath = ExportImportMsg.class.getResource("/").getPath()+"/testData/download/一键导入_分类"+fileName;
		Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
		if(result) {
			return filePath;
		}else{
			return null;
		}
	}

}
