package com.uinnova.test.step_definitions.api.cmv.monitor.performance;

import java.util.HashMap;
import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

public class ImportPerformances extends RestApi{
/**
 * 
 * 
 * 
 * @param excelPath
 * @param ciCods  多个ciCode用英文逗号分开
 * @return
 */
	public String importPerformances(String excelPath,String ciCode){
		String url = ":1511/tarsier-vmdb/cmv/monitor/performance/importPerformances";
		HashMap fileMap = new HashMap();
		HashMap textMap = new HashMap();
		textMap.put("ciCode", ciCode);
		fileMap.put(excelPath, "file");
		String result = QaUtil.uploadfiles(url, fileMap, textMap);
		return result;
	}
}
