package com.uinnova.test.step_definitions.api.cmv.monitor.performance;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportPerformanceTpl extends RestApi{

	public String exportPerformanceTpl(String ciCode){
		String url = ":1511/tarsier-vmdb/cmv/monitor/performance/exportPerformanceTpl?ciCode="+ciCode;
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filePath = ExportCi.class.getResource("/").getPath()+"testData/download/"+"CMV-ExportPerformanceTpl-"+format.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url,filePath)){
			return filePath;
		}else{
			return null;
		}
	}
	
}
