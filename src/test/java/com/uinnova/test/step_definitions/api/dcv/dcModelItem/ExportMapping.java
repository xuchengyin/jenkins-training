package com.uinnova.test.step_definitions.api.dcv.dcModelItem;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 *编写时间: 2018-2-27
 *编写人:wjx
 *功能介绍:下载模型映射
 */

public class ExportMapping {
	
	public String downloadProductMp(){
		String url = ":1511/tarsier-vmdb/dcv/dcModelItem/exportMapping";
		Date now = new Date();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-mm-dd_HH-mm-ss");
//		String filePath = ExportPort.class.getResource("/").getPath()+"testData/download"+File.separator+"product_lib_port"+formate.format(now)+".json";
		String filePath = ExportPort.class.getResource("/").getPath()+"testData/download"+"/modelMapping"+formate.format(now)+".json";
		if(QaUtil.downloadFile_urlWithoutFileName(url, filePath)){
			return filePath;
		}else{
			return null;
		}
	}
}

