package com.uinnova.test.step_definitions.api.dmv.file;

import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2018-04-20
 * 编写人: sunsl
 * 功能介绍:DMV表格通过文件路径下载文件
 */
public class GetResourceByPath {
   public String getResourceByPath(String fileName){
	   String url = ":1511/tarsier-vmdb/dmv/file/getResourceByPath";
	   String filePath = GetResourceByPath.class.getResource("/").getPath()+"/testData/download/"+fileName;
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
   }
}
