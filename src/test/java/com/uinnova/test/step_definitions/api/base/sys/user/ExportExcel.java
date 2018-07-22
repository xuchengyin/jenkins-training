package com.uinnova.test.step_definitions.api.base.sys.user;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2017-11-06
 * 编写人:sunsl
 * 功能介绍:用户管理的导出类
 */
public class ExportExcel {
	/**
	 * @return
	 */
	public String exportExcel(){
		String url =":1511/tarsier-vmdb/cmv/sys/user/exportExcel?1=1";
		String filePath = ExportExcel.class.getResource("/").getPath() + "testData/download/" + "用户信息.xls";
		if (QaUtil.downloadFile_urlWithoutFileName(url, filePath)){
			return filePath; 
		}else{
			return null;
		}	   

	}
}
