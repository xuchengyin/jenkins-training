package com.uinnova.test.step_definitions.api.dmv.migrateDiagram;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author wsl
 * 导出所有视图
 *
 */
public class ExportAllDiagrams {
	public String exportAllDiagrams(){
		String url =":1511/tarsier-vmdb/dmv/migrateDiagram/exportAllDiagrams";
		String filePath = ExportAllDiagrams.class.getResource("/").getPath() + "testData/download/diagrams.zip";
		if(QaUtil.downloadFile_urlWithoutFileName(url, filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}
