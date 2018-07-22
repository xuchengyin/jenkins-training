package com.uinnova.test.step_definitions.api.cmv.ciClass;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/*
 * 下载可视化建模模板相关信息[ZIP格式压缩包]
 * 
 * */
public class ExportClassDiagram extends RestApi{

	public String exportClassDiagram(){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/exportClassDiagram";
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportClassDiagram.class.getResource("/").getPath()+"testData/download/"+"CMV-ExportClassDiagram-"+format.format(now)+".zip";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}
