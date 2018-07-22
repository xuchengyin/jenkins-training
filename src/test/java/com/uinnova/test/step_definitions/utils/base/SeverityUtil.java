package com.uinnova.test.step_definitions.utils.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

/**
 * 编写时间:2018-01-19
 * 编写人:sunsl
 * 功能介绍:事件级别定义工具类
 */
public class SeverityUtil {
	/**
	 * @param severity
	 * @return
	 */
	public BigDecimal getIdByServerity(String severity){
		BigDecimal severityB = new BigDecimal(severity);
		String sql = "Select ID from mon_sys_severity where  SEVERITY = " + severityB;
		ArrayList list = JdbcUtil.executeQuery(sql);
		BigDecimal id = new BigDecimal(0);
		if(list != null && list.size()>0){
			HashMap map = (HashMap)list.get(0);
			id = (BigDecimal)map.get("ID");
		}
		return id;
	}
}
