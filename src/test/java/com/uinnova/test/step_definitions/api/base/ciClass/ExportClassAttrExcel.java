package com.uinnova.test.step_definitions.api.base.ciClass;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.uinnova.test.step_definitions.utils.common.QaUtil;


public class ExportClassAttrExcel {

	/**
	 * @return
	 */
	public String exportClassAttrExcel(){
		String url = ":1511/tarsier-vmdb/cmv/ciClass/exportClassAttrExcel";
		Date now = new Date();
		SimpleDateFormat formate = new SimpleDateFormat("yyyymmdd-HHmmss");
		String filePath = ExportClassAttrExcel.class.getResource("/").getPath()+"testData/download"+File.separator+"分类属性模板-"+formate.format(now)+".xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url, filePath)){
			return filePath;
		}else{
			return null;
		}

	}
}
