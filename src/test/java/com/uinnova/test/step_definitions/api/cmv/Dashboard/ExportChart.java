package com.uinnova.test.step_definitions.api.cmv.Dashboard;

import com.uinnova.test.step_definitions.api.base.ci.ExportImportMsg;
import com.uinnova.test.step_definitions.utils.cmv.DashboardUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 导出仪表盘图标
 * @author wsl
 *
 */
public class ExportChart {
	
	/**
	 * @param dashboardName
	 * @param chartTitle
	 * @return
	 */
	public String exportChart(String dashboardName, String chartTitle){
		String chartId = String.valueOf(DashboardUtil.getChartIDByTitle(dashboardName, chartTitle));
		String url ="1511/tarsier-vmdb/cmv/Dashboard/exportChart?chartIds="+chartId;
		String filePath = ExportImportMsg.class.getResource("/").getPath()+"/testData/download/chart-"+chartTitle;
		Boolean result = QaUtil.downloadFile_urlWithoutFileName(url, filePath);
		if(result) {
			return filePath;
		}else{
			return null;
		}
	}

}
