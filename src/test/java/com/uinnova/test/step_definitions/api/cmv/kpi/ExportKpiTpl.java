package com.uinnova.test.step_definitions.api.cmv.kpi;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2018-03-16
 * 编写人:sunsl
 * 功能介绍:导入下载模板功能
 */
public class ExportKpiTpl {
   public String exportKpiTpl(){
	   String url =":1511/tarsier-vmdb/cmv/kpi/exportKpiTpl";
	   Date now = new Date();
	   SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
	   String filePath = ExportKpiTpl.class.getResource("/").getPath() + "testData/download/指标模型数据-" + format.format(now) + ".xls";
	   Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
		if(result) {
			return filePath;
		}else{
			return null;
		}
   }
}
