package com.uinnova.test.step_definitions.utils.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ci.QueryPage;
import com.uinnova.test.step_definitions.api.base.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.base.ci.RemoveById;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryById;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * CI工具类
 * @author uinnova
 *
 */
public class CiUtil {

	/**
	 * @param className
	 * @param keyName
	 * @return
	 */
	public Boolean deleteSingCi(String className,String keyName){
		JSONObject queryPageObj = (new QueryPage()).queryPage(className);
		int dataNum = queryPageObj.getJSONObject("data").getJSONArray("data").length();
		BigDecimal ciId = new BigDecimal(0);
		BigDecimal classId = new BigDecimal(0);
		for (int i=0;i<dataNum;i++){
			JSONObject ci = (JSONObject)queryPageObj.getJSONObject("data").getJSONArray("data").get(i);
			if(ci.getJSONObject("ci").getString("ciCode").equals(keyName)){
				ciId = ci.getJSONObject("ci").getBigDecimal("id");
			}
		}
		if(ciId.compareTo(new BigDecimal(0))!=0){
			JSONObject result = (new RemoveById()).removeById(ciId);
			if(result.getInt("data")==1){
				//				String result = (new RemoveById()).removeById(ciId);
				//				if((new JSONObject(result).getInt("data"))==1){
				return true;
			}else{
				QaUtil.report("=======主键为"+keyName+"的数据删除失败========");
				return false;
			}
		}else{
			QaUtil.report("=======主键为"+keyName+"的数据不存在========");
			return false;
		}

	}

	/**
	 * @param className
	 * @return
	 */
	public JSONArray getAllCiData(String className){
		JSONArray allCi = new JSONArray();
		JSONObject queryPageObj = (new QueryPage()).queryPage(className);
		int totalRows = queryPageObj.getJSONObject("data").getInt("totalRows");
		int totalPages = queryPageObj.getJSONObject("data").getInt("totalPages");
		for (int i=0;i<totalPages;i++){
			JSONObject  qi = (new QueryPageByIndex()).queryPageByIndex(className, i+1);
			JSONArray data = qi.getJSONObject("data").getJSONArray("data");
			int num = data.length();
			for(int j=0;j<num;j++){
				allCi.put(data.getJSONObject(j));
			}
		}
		return allCi;
	}

	/**
	 * @param className
	 * @return
	 */
	public JSONArray getCiDataFromSQL(String className){
		//获取ci下的所有数据
		JSONObject queryPageObj = (new QueryPage()).queryPage(className);
		int totalRows = queryPageObj.getJSONObject("data").getInt("totalRows");
		int totalPages = queryPageObj.getJSONObject("data").getInt("totalPages");
		//获取分类属性
		JSONObject resultObj = (new QueryById()).queryById(className);
		JSONArray attrDefs = resultObj.getJSONObject("data").getJSONArray("attrDefs");
		int attrNum = attrDefs.length();
		JSONArray sql_data = new JSONArray();
		//获取数据库数据
		for(int i=0;i<totalRows;i++){
			//获取所有数据的id
			JSONArray ci = getAllCiData(className);
			BigDecimal ciId = ((JSONObject) ci.get(i)).getJSONObject("ci").getBigDecimal("id");
			BigDecimal classId = ((JSONObject) ci.get(i)).getJSONObject("ci").getBigDecimal("classId");;
			JSONObject sql_obj = new JSONObject();
			//获取每条记录的属性值，放入jsonobj
			int SV = 0;
			for(int attr=0;attr<attrNum;attr++){
				//根据各属性
				int proType = ((JSONObject)attrDefs.get(attr)).getInt("proType");
				String  proName = ((JSONObject)attrDefs.get(attr)).getString("proName");

				String sql = null;
				if(proType==1){
					sql = "SELECT IV_0  D FROM cc_ci_int_attr_0 c where CLASS_ID="+classId +" and ID="+ciId+" and DOMAIN_ID = "+ QaUtil.domain_id;
				}
				if(proType==2){
					sql = "SELECT DV_0  D FROM cc_ci_doub_attr_0  c where CLASS_ID="+classId +" and ID="+ciId+" and DOMAIN_ID = "+ QaUtil.domain_id;
				}
				if(proType==3 || proType==6 ||proType==7||proType==8 ){
					sql = "SELECT SV_"+ SV +" D FROM cc_ci_short_attr_0 c where CLASS_ID="+classId +" and ID="+ciId + " and DOMAIN_ID = "+ QaUtil.domain_id;
					SV++;
				}
				if(proType==4){
					sql = "SELECT LV_0 D FROM cc_ci_long_attr_0 c where CLASS_ID="+classId +" and ID="+ciId +" and DOMAIN_ID = "+ QaUtil.domain_id;
				}
				if(proType==5){
					sql = "SELECT BV_0 D FROM cc_ci_big_attr_0  c where CLASS_ID="+classId +" and ID="+ciId +" and DOMAIN_ID = "+ QaUtil.domain_id;
				}

				ArrayList list = JdbcUtil.executeQuery(sql);
				HashMap map = (HashMap)list.get(0);
				if(map.toString().indexOf("null")>0){
					sql_obj.put(proName, "");
				}else{
					sql_obj.put(proName, map.get("D").toString());
				}
			}
			sql_data.put(sql_obj);
		}
		return sql_data;
	}


	/**
	 * @param className
	 * @param filePath
	 * @return
	 */
	public Boolean compSQLDataToExcel(String className,String filePath){
		//获取ci下的所有数据
		JSONObject queryPageObj = (new QueryPage()).queryPage(className);
		int totalRows = queryPageObj.getJSONObject("data").getInt("totalRows");
		//获取分类属性
		JSONObject resultObj = (new QueryById()).queryById(className);
		JSONArray attrDefs = resultObj.getJSONObject("data").getJSONArray("attrDefs");
		int attrNum = attrDefs.length();
		JSONArray sql_data = (new CiUtil()).getCiDataFromSQL(className);
		//获取excel表数据
		JSONArray excel_Data = (new ExcelUtil()).readFromExcel(filePath, className); 
		//对比excel表与数据库数据
		int rowFlag = 0;
		for(int i=1;i<=totalRows;i++){ 
			JSONObject exData = (JSONObject)excel_Data.get(i);
			int attrFlag = 0;
			for(int j=0;j<attrNum;j++){
				String  proName = ((JSONObject)attrDefs.get(j)).getString("proName");
				String sqlValue = ((JSONObject) sql_data.get(i-1)).getString(proName).toString();
				String excelValue = exData.getString(String.valueOf(j));
				if(excelValue.equals(sqlValue)){
					attrFlag++;
				}
				else
					QaUtil.report("====比较失败： 属性:"+proName+"的sql值为【"+sqlValue +"】====excel表的值为【"+excelValue+"】");
			}
			if(attrFlag==attrNum){
				rowFlag++;
			}
		}	
		if(rowFlag==totalRows){
			return true;
		}else{
			return false;
		}

	}
	/**
	 * 根据CICode获得CIID
	 * @param ciCode
	 * @return Integer
	 */
	public BigDecimal getCiId(String ciCode){
		BigDecimal id = new BigDecimal(0);
		String sql = "SELECT ID FROM cc_ci WHERE CI_CODE = '" + ciCode + "'  AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List ciList = JdbcUtil.executeQuery(sql);
		if (ciList.size() >0 ){
			HashMap ciHashMap = (HashMap)ciList.get(0);
			id = (BigDecimal) ciHashMap.get("ID");			
		}
		return id ;
	}

	/**
	 * @param ciCodeId
	 * @return
	 * 根据ID取Code
	 */
	public String getCiCodeById(BigDecimal ciCodeId){
		String ciCode = "";
		String sql = "SELECT CI_CODE FROM cc_ci WHERE ID =" + ciCodeId + "  AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List ciList = JdbcUtil.executeQuery(sql);
		if (ciList.size() >0 ){
			HashMap ciHashMap = (HashMap)ciList.get(0);
			ciCode =(String) ciHashMap.get("CI_CODE");
		}
		return ciCode ;
	}
}