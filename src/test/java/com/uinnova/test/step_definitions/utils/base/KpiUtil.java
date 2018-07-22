package com.uinnova.test.step_definitions.utils.base;

import java.util.ArrayList;
import java.util.HashMap;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
/**
 * 编写时间:2017-11-02
 * 编写人:sunsl
 * 功能介绍:指标模型的工具类
 */
public class KpiUtil {
	/**
	 * @param kpiCode
	 * @return
	 */
	public ArrayList getKpi(String kpiCode){
		String kpiSql = "Select ID from cc_kpi where KPI_CODE = '" + kpiCode + "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList kpiList = JdbcUtil.executeQuery(kpiSql);
		return kpiList;
	}
	
	
	public String getKpiIdByKpiCode(String kpiCode){
		String kpiSql = "Select ID from cc_kpi where KPI_CODE = '" + kpiCode + "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList kpiList = JdbcUtil.executeQuery(kpiSql);
		HashMap hm = (HashMap) kpiList.get(0);
		return hm.get("ID").toString();
	}
	
	public String getKpiTplIdByKpiTplName(String kpiTplName){
		String kpiSql = "Select ID from cc_kpi_tpl where TPL_NAME = '" + kpiTplName + "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList kpiList = JdbcUtil.executeQuery(kpiSql);
		HashMap hm = (HashMap) kpiList.get(0);
		return hm.get("ID").toString();
	}
	
}
