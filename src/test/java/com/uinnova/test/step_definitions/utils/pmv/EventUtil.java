package com.uinnova.test.step_definitions.utils.pmv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class EventUtil {
	/**
	 * 查詢告警count
	 * 
	 * @param metric
	 * @return
	 */
	public Double EventCount(String metric) {
		InfluxdbJDBC influxDB = new InfluxdbJDBC();
		Double EventCount = null ;
		String sql = "select COUNT(serverity) from pmv_event_memory where metric='" + metric+"'";
		List result = influxDB.InfluxQuery(sql,"pmvEvent");
		if (result==null) {
			EventCount=(double) 0;
		}else if(result.size()>0)
		 {
			HashMap charMap = (HashMap) result.get(0);
			EventCount = Double.valueOf((String) charMap.get("count"));
		}
		return EventCount;

	}

}
