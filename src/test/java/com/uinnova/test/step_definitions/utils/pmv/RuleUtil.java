package com.uinnova.test.step_definitions.utils.pmv;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class RuleUtil {

	/**
	 * 查詢策略count
	 * @param metric
	 * @return
	 */
	public long RuleCount(String metric) {
		long RuleCount = 0;
		String sql = "select COUNT(1) as RCOUNT from pc_rule WHERE METRIC = '" + metric.trim()
				+ "'and   DATA_STATUS = 1";
		List charMapList = JdbcUtil.executeQuery(sql);
		if (charMapList.size() > 0) {
			HashMap charMap = (HashMap) charMapList.get(0);
			RuleCount = (long) charMap.get("RCOUNT");
		}
		return RuleCount;

	}
}
