package com.uinnova.test.step_definitions.utils.cmv;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 仪表盘工具类
 * @author wsl
 *
 */
public class DashboardUtil {
	
	/**
	 * 根据仪表盘名称获取仪表盘ID
	 * @param dashboardName
	 * @return
	 */
	public static BigDecimal getDashbordIDByName(String dashboardName){
		BigDecimal dashboardId = new BigDecimal(0);
		String sql="select ID from cc_ci_quality_dashboard   where   DASHBOARD_NAME = '"+dashboardName.trim()+"'"
				+" and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1";
		List l = JdbcUtil.executeQuery(sql);
		if (l!=null && l.size()==1){
			Map map = (Map) l.get(0);
			dashboardId =  (BigDecimal) map.get("ID");
		}
		return dashboardId;
	}
	
	
	/**
	 * 根据仪表盘名称和图标Title获取图标ID
	 * @param dashboardName
	 * @param chartTitle
	 * @return
	 */
	public static BigDecimal getChartIDByTitle(String dashboardName, String chartTitle){
		BigDecimal chartId = new BigDecimal(0);
		String sql ="select * from cc_ci_quality_chart where TITLE='"+chartTitle.trim()+"' and    DOMAIN_ID = "+QaUtil.domain_id+"  and   DATA_STATUS = 1"
				+ " and DASHBOARD_ID = (select ID from cc_ci_quality_dashboard where DASHBOARD_NAME='"+dashboardName.trim()+"'  and    DOMAIN_ID = "+QaUtil.domain_id+" and DATA_STATUS=1)";
		List l = JdbcUtil.executeQuery(sql);
		if (l!=null && l.size()==1){
			Map map = (Map) l.get(0);
			chartId =  (BigDecimal) map.get("ID");
		}
		return chartId;
	}

}
