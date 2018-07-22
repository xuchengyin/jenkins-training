package com.uinnova.test.step_definitions.api.base.sys.user;

import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2017-11-06 编写人:sunsl 功能介绍:用户管理下载模板类
 */
public class DownloadExcelTpl {

	/**
	 * @return
	 */
	public String downloadExcelTpl() {
		String url = ":1511/tarsier-vmdb/cmv/sys/user/downloadExcelTpl";
		String filePath = DownloadExcelTpl.class.getResource("/").getPath() + "testData/download/" + "用户信息模板.xls";
		if(QaUtil.downloadFile_urlWithoutFileName(url, filePath)){
			return filePath;
		}else{
			return null;
		}

	}
}
