package com.uinnova.test.step_definitions.testcase.base.sys.loginlog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.base.sys.loginlog.QuerySysLoginCountPage;
import com.uinnova.test.step_definitions.api.base.sys.loginlog.QuerySysLoginLogPage;
import com.uinnova.test.step_definitions.utils.common.DateUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 登录日志
 * @author wsl
 * 2018-3-15
 */
public class Scenario_loginlog {
	Map<String, JSONObject> loginLogMap = new HashMap<String, JSONObject>();
	Map<String, JSONObject> loginCountMap = new HashMap<String, JSONObject>();
	JSONObject allLoginLog = new JSONObject();
	JSONObject allLoginCount = new JSONObject();
	String startLoginTime="";
	String endLoginTime="";

	@When("^按照如下关键字搜索登录日志$")
	public void searchLoginLog(DataTable table){
		if(table!=null){
			int rows = table.raw().size();
			QuerySysLoginLogPage querySysLoginLogPage = new QuerySysLoginLogPage();
			startLoginTime = DateUtil.getPreviousDay();
			endLoginTime = DateUtil.getToday();		
			for (int i=1;i<rows;i++){
				List<String> row = table.raw().get(i);
				String searchKey =  row.get(1);
				JSONObject result = querySysLoginLogPage.querySysLoginLogPage(searchKey, startLoginTime, endLoginTime, 1,30);
				loginLogMap.put(searchKey, result);
			}
		}
	}

	@Then("^按照如下关键字搜索登录日志成功$")
	public void checkSearchLoginLog(DataTable table){
		if(table!=null){
			int rows = table.raw().size();
			for (int i=1;i<rows;i++){
				List<String> row = table.raw().get(i);
				String searchKey =  row.get(1);
				JSONObject tempObj = loginLogMap.get(searchKey).getJSONObject("data");
				String sql ="";
				if (Contants.DB_TYPE_MYSQL.compareToIgnoreCase(JdbcUtil.db_type)==0){
					sql="select ID, USER_ID, USER_CODE, USER_NAME, SESSION_ID, LOGIN_TIME, LOGOUT_TIME, REMARK, DOMAIN_ID, USER_DOMAIN_ID, DATA_STATUS, CREATE_TIME,    MODIFY_TIME     "
							+" from SYS_LOGIN_LOG     where    DOMAIN_ID = "+QaUtil.domain_id;
					if (!searchKey.isEmpty()){
						sql+=	" and   USER_ID in (  select ID from SYS_OP  where  SUPER_USER_FLAG = 0    and    DOMAIN_ID = "+ QaUtil.domain_id+"               and    USER_DOMAIN_ID = "+ QaUtil.domain_id           
								+" and      ((OP_CODE like '%"+searchKey.trim()+"%') or (OP_NAME like '%"+searchKey.trim()+"%') or (LOGIN_CODE like '%"+searchKey.trim()+"%'))   "         
								+" )  ";
					}
					if (!startLoginTime.isEmpty())
						sql+=" and LOGIN_TIME >="+Long.valueOf(startLoginTime);
					if (!endLoginTime.isEmpty())
						sql+=" and LOGIN_TIME <="+Long.valueOf(endLoginTime);
					sql+= " and    USER_DOMAIN_ID = "+ QaUtil.domain_id;

					sql+=" order by LOGIN_TIME DESC limit 0,30 ";
				}

				else if (Contants.DB_TYPE_ORACLE.compareToIgnoreCase(JdbcUtil.db_type)==0){
					sql = "select ID, USER_ID, USER_CODE, USER_NAME, SESSION_ID, LOGIN_TIME, LOGOUT_TIME, REMARK, DOMAIN_ID, USER_DOMAIN_ID, DATA_STATUS, CREATE_TIME, MODIFY_TIME "
							+" from (   select        ID, USER_ID, USER_CODE, USER_NAME, SESSION_ID, LOGIN_TIME,    LOGOUT_TIME, REMARK, DOMAIN_ID, USER_DOMAIN_ID, DATA_STATUS, CREATE_TIME,    MODIFY_TIME, ROW_NUMBER() OVER(order by LOGIN_TIME DESC) alias_for_rownum  "
							+" from SYS_LOGIN_LOG  where DOMAIN_ID = "+QaUtil.domain_id ;
					if (!searchKey.isEmpty()){
						sql+=	" and   USER_ID in (  select ID from SYS_OP  where  SUPER_USER_FLAG = 0    and    DOMAIN_ID = "+ QaUtil.domain_id+"              and    USER_DOMAIN_ID = "+ QaUtil.domain_id           
								+" and      ((OP_CODE like '%"+searchKey.trim()+"%') or (OP_NAME like '%"+searchKey.trim()+"%') or (LOGIN_CODE like '%"+searchKey.trim()+"%'))   "         
								+"  )  ";
						
					}
					if (!startLoginTime.isEmpty())
						sql+=" and LOGIN_TIME >="+Long.valueOf(startLoginTime);
					if (!endLoginTime.isEmpty())
						sql+=" and LOGIN_TIME <="+Long.valueOf(endLoginTime);
					sql+=" and    USER_DOMAIN_ID = "+ QaUtil.domain_id+"                order by LOGIN_TIME DESC) where alias_for_rownum<31 and alias_for_rownum>=1";
				}

				
									
				ArrayList l = JdbcUtil.executeQuery(sql);
				assertEquals("关键字'"+searchKey+"'查询结果:" , l.size(), tempObj.getJSONArray("data").length());
			}
		}
	}

	@When("^按照如下关键字统计登录次数$")
	public void searchLoginCount(DataTable table){
		if(table!=null){
			int rows = table.raw().size();
			QuerySysLoginCountPage querySysLoginCountPage = new QuerySysLoginCountPage();
			startLoginTime = DateUtil.getPreviousDay();
			endLoginTime = DateUtil.getToday();	
			for (int i=1;i<rows;i++){
				List<String> row = table.raw().get(i);
				String searchKey =  row.get(1);
				JSONObject result = querySysLoginCountPage.querySysLoginCountPage(searchKey,startLoginTime, endLoginTime,1,30);
				loginCountMap.put(searchKey, result);
			}
		}

	}
	@Then("^按照如下关键字统计登录次数成功$")
	public void checkSearchLoginCount(DataTable table){
		if(table!=null){
			int rows = table.raw().size();
			for (int i=1;i<rows;i++){
				List<String> row = table.raw().get(i);
				String searchKey =  row.get(1);
				JSONObject tempObj = loginCountMap.get(searchKey).getJSONObject("data");
				String sql ="select USER_CODE, count(1) as LOGIN_COUNT  "
						+" from SYS_LOGIN_LOG     where    DOMAIN_ID = "+QaUtil.domain_id;
				if (!searchKey.isEmpty()){
					sql+=	" and ( USER_NAME like '%"+searchKey+"%' ";
					sql+=	" or  USER_CODE like '%"+searchKey+"%' )";
				}
					
				if (!startLoginTime.isEmpty())
					sql+=" and LOGIN_TIME >="+Long.valueOf(startLoginTime);
				if (!endLoginTime.isEmpty())
					sql+=" and LOGIN_TIME <="+Long.valueOf(endLoginTime);
				sql+= " and    USER_DOMAIN_ID = "+ QaUtil.domain_id+"     group by USER_CODE";
				if (Contants.DB_TYPE_MYSQL.compareToIgnoreCase(JdbcUtil.db_type)==0)
					sql+=" order by USER_CODE limit 0,30 ";
				/*else
					sql+=" and rownum<30 order by USER_NAME";*/

				ArrayList l = JdbcUtil.executeQuery(sql);
				assertTrue("按关键字统计登录次数出错， 关键字为： "+searchKey,l.size()>0);
				JSONArray dataArr = tempObj.getJSONArray("data");
				BigDecimal count =new BigDecimal(0);
				for(int r=0; r<dataArr.length(); r++){
					JSONObject obj = dataArr.getJSONObject(r);
					count=count.add(obj.getBigDecimal("loginCount"));
				}
				BigDecimal dbCount = new BigDecimal(0);
				for (int d=0; d<l.size(); d++){
					Map map = (Map) l.get(d);
					dbCount = dbCount.add(new BigDecimal(map.get("LOGIN_COUNT").toString()));
				}
				assertEquals("关键字'"+searchKey+"'统计结果:" , dbCount, count);

			}
		}
	}



	@When("^查询全部登录日志$")
	public void searchAllLoginlog(){
		QuerySysLoginLogPage querySysLoginLogPage = new QuerySysLoginLogPage();
		allLoginLog = querySysLoginLogPage.querySysLoginLogPage("", "", "", 1,30);
	}

	@Then("^查询全部登录日志成功$")
	public void checkSearchAllLoginlog(){
		JSONObject tempObj = allLoginLog.getJSONObject("data");
		String sql ="select ID, USER_ID, USER_CODE, USER_NAME, SESSION_ID, LOGIN_TIME, LOGOUT_TIME, REMARK, DOMAIN_ID, USER_DOMAIN_ID, DATA_STATUS, CREATE_TIME,    MODIFY_TIME     "
				+" from SYS_LOGIN_LOG     where    DOMAIN_ID = "+QaUtil.domain_id;
		sql+= " and    USER_DOMAIN_ID = "+ QaUtil.domain_id;//order by ID limit 0,30 ";
		ArrayList l = JdbcUtil.executeQuery(sql);
		assertEquals(l.size(), tempObj.get("totalRows"));
	}

	@When("^统计全部登录次数$")
	public void searchAllLoginCount(){
		QuerySysLoginCountPage querySysLoginCountPage = new QuerySysLoginCountPage();
		allLoginCount = querySysLoginCountPage.querySysLoginCountPage("","", "",1,30);
	}

	@Then("^统计全部登录次数成功$")
	public void checkSearchAllLoginCount(){
		JSONObject tempObj = allLoginCount.getJSONObject("data");
		String sql ="select USER_CODE, count(1) as LOGIN_COUNT "
				+" from SYS_LOGIN_LOG     where    DOMAIN_ID = "+QaUtil.domain_id;
		sql+= " and DATA_STATUS =1 and    USER_DOMAIN_ID = "+QaUtil.domain_id
				+"     group by USER_CODE ";//order by USER_NAME limit 0,30 ";
		ArrayList l = JdbcUtil.executeQuery(sql);
		assertEquals(l.size(), tempObj.get("totalRows"));
	}
}
