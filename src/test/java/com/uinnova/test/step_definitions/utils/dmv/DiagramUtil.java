package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author wsl
 * 视图工具类
 * 2017-12-11
 *
 */
public class DiagramUtil {

	/**
	 * @param diagramName
	 * @return
	 * 根据视图名字得到视图ID
	 */
	public BigDecimal getDiagramIdByName(String diagramName){
		
		String sql = "select ID from vc_diagram where DATA_STATUS=1 and STATUS=1 and USER_ID = "+QaUtil.user_id +" AND NAME = '"+diagramName+"' and DOMAIN_ID = "+ QaUtil.domain_id;
		List idList = JdbcUtil.executeQuery(sql);
		if (idList!=null && idList.size()>0){
			HashMap map = (HashMap) idList.get(0);
			BigDecimal id = (BigDecimal) map.get("ID");
			return  id;
		}
		return new BigDecimal(0);
	}

	/**
	 * @param diagramId
	 * @return
	 * 根据试图ID得到试图名称
	 */
	public String getDiagramNameById(BigDecimal diagramId){
		String sql = "select Name from vc_diagram where DATA_STATUS=1 and STATUS=1  and ID = "+diagramId+" and DOMAIN_ID = "+ QaUtil.domain_id;
		List idList = JdbcUtil.executeQuery(sql);
		if (idList!=null && idList.size()>0){
			HashMap map = (HashMap) idList.get(0);
			String name = (String) map.get("Name");
			return  name;
		}
		return "";
	}

	/**
	 * @param dirName
	 * @return
	 * 根据文件夹的名字得到文件夹ID
	 */
	public int getDirIdByName(String dirName){
		String sql = "select ID from vc_diagram_dir where DATA_STATUS=1 and DIR_NAME = '"+dirName+"' and DOMAIN_ID = "+ QaUtil.domain_id;
		List idList = JdbcUtil.executeQuery(sql);
		if (idList!=null && idList.size()>1){
			HashMap map = (HashMap) idList.get(0);
			BigDecimal id = (BigDecimal) map.get("ID");
			return  id.intValue();
		}
		return 0;
	}

	public BigDecimal getCombIdByName(String combName){
		String sql = "Select ID from vc_diagram where DIAGRAM_TYPE = 2 AND NAME = '" + combName +"' AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List combList = JdbcUtil.executeQuery(sql);
		BigDecimal combId = new BigDecimal(0);
		if(combList != null && combList.size()>0){
			HashMap combHashMap = (HashMap)combList.get(0);
			combId = ((BigDecimal)combHashMap.get("ID"));
		}
		return combId;
	}

	/**
	 * @param diagramEles
	 * @param ciCode
	 * @return
	 * diagram ele 中是否含有某个ciCode
	 */
	public boolean hasElement(JSONArray diagramEles, String ciCode){
		boolean hasFlag = false;
		if (diagramEles==null)
			return false;
		CiUtil ciUtil = new CiUtil();
		BigDecimal ciId = ciUtil.getCiId(ciCode);

		for (int i=0; i<diagramEles.length(); i++){
			JSONObject tmp = (JSONObject) diagramEles.get(i);
			if (tmp.getBigDecimal("eleId").compareTo(ciId)==0){
				hasFlag = true;
				break;
			}
		}
		return hasFlag;
	}
	
	/**
	 * @param diagramName
	 * @param versionDesc
	 * @return BigDecimal
	 */
	public BigDecimal getVersionIdByVersionDesc(String diagramName,String versionDesc){
    	BigDecimal diagramId = getDiagramIdByName(diagramName);
    	String sql = "SELECT ID FROM vc_diagram_version WhERE DIAGRAM_ID = " + diagramId + " AND VERSION_DESC = '" + versionDesc + "' AND STATUS = 1 AND DATA_STATUS = 1";
    	List list = JdbcUtil.executeQuery(sql);
    	BigDecimal versionId = new BigDecimal(0);
    	if(list.size()>0){
    		HashMap map = (HashMap)list.get(0);
    		versionId = (BigDecimal)map.get("ID");
    	}
    	return versionId;
	}		
}




