package com.uinnova.test.step_definitions.api.base.ciRlt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ExportCiRlt {

	/**
	 * 下载分类模板
	 * @param rltClsName
	 * @return
	 */
	public String exportCiRltMould(String rltClsName){
		String rltClassIds = String.valueOf((new RltClassUtil()).getRltClassId(rltClsName));
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/exportCiRlt?rltClassIds="+rltClassIds+"&hasData=0";
		Date now = new Date();
		SimpleDateFormat formate = new SimpleDateFormat("yyyymmdd-HHmmss");
		String filePath = ExportCiRlt.class.getResource("/").getPath()+"testData/download"+File.separator+"CIRealtaion"+formate.format(now)+"-"+rltClsName+".xls";
		Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
		if(result){
			return filePath; 
		}else{
			return null;
		}
	}

	/**
	 * 下载当前分类数据
	 * @param rltClsName
	 * @return
	 */
	public String exportCiRlt_currentCiRltCls(String rltClsName){
		String rltClassIds = String.valueOf((new RltClassUtil()).getRltClassId(rltClsName));
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/exportCiRlt?rltClassIds="+rltClassIds+"&hasData=1";
		Date now = new Date();
		SimpleDateFormat formate = new SimpleDateFormat("yyyymmdd-HHmmss");
		String filePath = ExportCiRlt.class.getResource("/").getPath()+"testData/download"+File.separator+"CIRealtaion-CurrentRlt"+formate.format(now)+"-"+rltClsName+".xls";
		Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
		if(result){
			return filePath; 
		}else{
			return null;
		}
	}

	/**
	 * 下载所有分类数据
	 * @return
	 */
	public String exportCiRlt_allCiRltCls(){
		String url = ":1511/tarsier-vmdb/cmv/ciRlt/exportCiRlt?hasData=1";
		Date now = new Date();
		SimpleDateFormat formate = new SimpleDateFormat("yyyymmdd-HHmmss");
		String filePath = ExportCiRlt.class.getResource("/").getPath()+"testData/download"+File.separator+"CIRealtaion-allRlt"+formate.format(now)+".xls";
		Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
		if(result){
			return filePath; 
		}else{
			return null;
		}
	}

}
