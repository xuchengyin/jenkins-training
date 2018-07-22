package com.uinnova.test.step_definitions.utils.pmv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;


/**
 * KPI工具类
 * @author KYN
 *
 */
public class KpiValueRltUtil {


	/**
	 * @param  
	 * @param ID
	 * @return
	 */
/*	public ArrayList<JSONObject> getAllKpiData(String kpiclass, String metric, String value){
		JSONObject obj = new JSONObject();
		ArrayList<JSONObject> allkpi = new ArrayList<JSONObject>();		
		String sql = "Select * from pc_kpi_value_rlt where KPI='" + metric + "' and KPI_CLASS ='" + kpiclass + "'and KPI_VALUE_STRS ='" + value + "' and   DATA_STATUS = 1";
		List KpiList = JdbcUtil.executeQuery(sql);
		if (KpiList.size() >0 ){
			HashMap kpiMap = (HashMap)KpiList.get(0);					
			obj.put("id", (BigDecimal) kpiMap.get("ID"));
			obj.put("createTime", kpiMap.get("CREATE_TIME"));
			obj.put("kpiClass", kpiMap.get("KPI_CLASS"));
			obj.put("kpi", kpiMap.get("KPI"));
			obj.put("abandon", kpiMap.get("ABANDON"));
			obj.put("dataStatus", kpiMap.get("DATA_STATUS"));
			obj.put("modifyTime", kpiMap.get("MODIFY_TIME"));
			obj.put("kpiValueRlt", kpiMap.get("KPI_VALUE_RLT"));
			obj.put("kpiValueStrs", kpiMap.get("KPI_VALUE_STRS"));
			allkpi.add(obj);
			
	         }
		return allkpi;
	}*/

	
	/**
	 * 根据kpiclass,metric获得charMapID
	 * @param kpiclass,metric
	 * @return Integer
	 */
	public BigDecimal getcharMapID(String kpiclass, String metric){
		BigDecimal id = new BigDecimal(0);
		String sql = "Select ID from pc_kpi_value_rlt where KPI='" + metric + "' and KPI_CLASS ='" + kpiclass+"'";
		List charMapList = JdbcUtil.executeQuery(sql);
		if (charMapList.size() >0 ){
			HashMap charMap = (HashMap)charMapList.get(0);
			id = (BigDecimal) charMap.get("ID");			
		}
		return id ;
	}

	


	
}