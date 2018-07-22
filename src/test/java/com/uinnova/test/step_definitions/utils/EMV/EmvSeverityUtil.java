package com.uinnova.test.step_definitions.utils.EMV;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

public class EmvSeverityUtil {
	public BigDecimal getIdByServerity(String severity){
		BigDecimal severity01 = new BigDecimal(severity);
		String sql = "Select ID from mon_sys_severity where  SEVERITY = " + severity01;
		ArrayList list = JdbcUtil.executeQuery(sql);
		BigDecimal id = new BigDecimal(0);
		if(list != null && list.size()>0){
			HashMap map = (HashMap)list.get(0);
			id = (BigDecimal)map.get("ID");
		}
		return id;
	}

}
