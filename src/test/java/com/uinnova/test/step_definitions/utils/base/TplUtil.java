package com.uinnova.test.step_definitions.utils.base;

import java.util.ArrayList;
import java.util.HashMap;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2017-12-18
 * 编写人:sunsl
 * 功能介绍:指标模板的工具类
 */
public class TplUtil {
	/**
	 * 根据指标模板名称获取指标模板ID
	 * @param tplName
	 * @return
	 */
	public String getKpiTplId(String tplName){
		String sql = "SELECT ID FROM cc_kpi_tpl WHERE TPL_NAME = '" + tplName + "' AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		String kpiTplId = ((HashMap)list.get(0)).get("ID").toString();
		return kpiTplId;
	}
}
