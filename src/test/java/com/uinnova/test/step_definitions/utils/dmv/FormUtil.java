package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2018-01-15
 * 编写人:sunsl
 * 功能介绍:DMV表格工具类
 */
public class FormUtil {
   public BigDecimal getFormIdByFormName(String fileName){
	   String sql = "Select ID from cc_excel where EXCEL_NAME='" + fileName +"' AND DATA_STATUS = 1 AND DOMAIN_ID="+ QaUtil.domain_id;
	   List list = JdbcUtil.executeQuery(sql);
	   BigDecimal id = new BigDecimal(0);
	   if(list !=null && list.size()>0){
		   HashMap map = (HashMap)list.get(0);
		   id = ((BigDecimal)map.get("ID"));
	   }
	   return id;
   }
   
	/**
	 * @param dirName
	 * @return
	 * 根据文件夹的名字得到文件夹ID
	 */
	public BigDecimal getDirIdByName(String dirName){
		String sql = "select ID from cc_general_dir where DATA_STATUS=1 and DIR_NAME = '"+dirName+"' and DOMAIN_ID = "+ QaUtil.domain_id;
		List idList = JdbcUtil.executeQuery(sql);
		if(dirName.equals("")){
			return new BigDecimal(7);
		}
		if(idList != null && idList.size()>0){
		  HashMap map = (HashMap) idList.get(0);
		  BigDecimal id = (BigDecimal) map.get("ID");
		  return  id;
		}else{
		  return null;
		}
	}
	
	/**
	 * @param dirName
	 * @return
	 * 根据文件夹的名字得到文件夹ID
	 */
	public BigDecimal getDirIdByName(String dirName,BigDecimal parentId){
		String sql = "select ID from cc_general_dir where DATA_STATUS=1 and DIR_NAME = '"+dirName+"' and PARENT_ID = "+ parentId + " and DOMAIN_ID = "+ QaUtil.domain_id;
		List idList = JdbcUtil.executeQuery(sql);
		if(idList != null && idList.size()>0){
		  HashMap map = (HashMap) idList.get(0);
		  BigDecimal id = (BigDecimal) map.get("ID");
		  return  id;
		}else{
		  return null;
		}
	}
}
