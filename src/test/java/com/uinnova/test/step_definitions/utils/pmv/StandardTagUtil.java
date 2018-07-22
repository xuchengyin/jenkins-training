package com.uinnova.test.step_definitions.utils.pmv;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class StandardTagUtil {
	/**
	 * 根据标准标签名称获取标准标签ID
	 * 
	 * @param stag
	 * @return
	 */
	public static BigDecimal getTagIDByName(String stag) {
		BigDecimal Id = new BigDecimal(0);
		String sql = "select ID from pc_tag_standard   where  NAME = '" + stag.trim() + "'and   DATA_STATUS = 1";
		List charMapList = JdbcUtil.executeQuery(sql);
		if (charMapList.size() > 0) {
			HashMap charMap = (HashMap) charMapList.get(0);
			Id = (BigDecimal) charMap.get("ID");
		}
		return Id;

	}

	/**
	 * 根据名指标名称获取ID
	 * 
	 * @param kpiclass
	 *            ,kpi
	 * @return
	 */
	public static BigDecimal getkpiIDByName(String kpiclass, String kpi) {
		BigDecimal Id = new BigDecimal(0);
		String sql = "select ID from pc_kpi   where  KPI = '" + kpi.trim() + "'And KPI_CLASS = '" + kpiclass.trim()
				+ "'";
		List charMapList = JdbcUtil.executeQuery(sql);
		if (charMapList.size() > 0) {
			HashMap charMap = (HashMap) charMapList.get(0);
			Id = (BigDecimal) charMap.get("ID");
		}
		return Id;
	}

	/**
	 * 查詢排序
	 * 
	 * @param objid
	 *            ,tagcode
	 * @return
	 */
	public static BigDecimal getOrderNum(String objid, String tagcode) {
		BigDecimal OrderNum = new BigDecimal(0);
		String sql = "select ORDER_NUM from pc_tag_show_conf   where  OBJ_ID = '" + objid.trim() + "'And TAG_CODE = '"
				+ tagcode.trim() + "'and   DATA_STATUS = 1";
		System.out.println(sql);

		List charMapList = JdbcUtil.executeQuery(sql);
		if (charMapList.size() > 0) {
			HashMap charMap = (HashMap) charMapList.get(0);
			OrderNum = (BigDecimal) charMap.get("ORDER_NUM");
		}
		return OrderNum;
	}
}
