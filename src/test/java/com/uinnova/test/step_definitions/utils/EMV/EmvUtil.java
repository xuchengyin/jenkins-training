package com.uinnova.test.step_definitions.utils.EMV;

import java.util.ArrayList;
import java.util.Map;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

public class EmvUtil {

	public String  getSerial(String sourceCiName, String sourceAlertKey){
		String serial = "";
		String sql ="SELECT SERIAL FROM  mon_eap_event_memory WHERE SOURCECINAME = '"+sourceCiName
				+"' AND SOURCEALERTKEY='"+sourceAlertKey+"'  ";
		ArrayList l = JdbcUtil.executeQuery(sql);
		if (l!=null && l.size()>0){
			Map map = (Map) l.get(0);
			serial =  (String) map.get("SERIAL");
		}
		return serial;
	}

}
