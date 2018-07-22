package com.uinnova.test.step_definitions.testcase.base.sys.operationlog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.cmv.sys.operationlog.QuerySysOperateLogPage;
import com.uinnova.test.step_definitions.utils.common.DateUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-01-25
 * 编写人:sunsl
 * 功能介绍:查询操作日志的测试用例类
 */
public class Scenario_operationlog {
	JSONObject result;
	String startSearchTime="";
	String endSearchTime="";
	/*======Scenario Outline: Operationlog_查询操作日志===*/
	@When("^根据查询条件起始时间\"(.*)\",终了时间\"(.*)\",操作用户\"(.*)\",操作描述\"(.*)\",模块选择\"(.*)\"来查询日志$")
	public void querySysOperateLogPage(String startLogTime,String endLogTime,String userName,String opDesc, @Delimiter(",") List<String>opNames){
		QuerySysOperateLogPage querySysOperateLogPage = new QuerySysOperateLogPage();
		startSearchTime = DateUtil.getPreviousDay();
		endSearchTime = DateUtil.getToday();
		//暂时只查询一天的数据
		result = querySysOperateLogPage.querySysOperateLogPage(startSearchTime, endSearchTime, userName, opDesc, opNames);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功根据查询条件起始时间\"(.*)\",终了时间\"(.*)\",操作用户\"(.*)\",操作描述\"(.*)\",模块选择\"(.*)\"来查询日志$")
	public void checkQuerySysOperateLogPage(String startLogTime,String endLogTime,String userName,String opDesc, @Delimiter(",") List<String>opNames){
		String sql = " select ID, LOG_TIME, USER_ID, USER_CODE, USER_NAME, OP_NAME, OP_PATH, OP_DESC, OP_STATUS, OP_PARAM, OP_RESULT, MVC_FULL_NAME,    MVC_MOD_NAME, DOMAIN_ID, USER_DOMAIN_ID, CREATE_TIME, MODIFY_TIME  "
				+" from SYS_OPERATE_LOG     where  USER_DOMAIN_ID = "+QaUtil.domain_id;
		
		if (!startSearchTime.isEmpty())
			sql+=" and LOG_TIME >="+Long.valueOf(startSearchTime);
		if (!endSearchTime.isEmpty())
			sql+=" and LOG_TIME <="+Long.valueOf(endSearchTime);
		
		if(!userName.isEmpty()){
			sql = sql +" and  USER_NAME like '"+ userName + "'";
		}
		if(!opDesc.isEmpty()){
			sql = sql + " and OP_DESC like '" + opDesc + "'";
		}
		String opNamestr = "";
		if (opNames != null && opNames.size()>0){
			for(int i = 0; i < opNames.size(); i ++){
				if(i == 0){
					opNamestr = "'" + opNames.get(i) +"'";			
				}else{
					opNamestr = opNamestr + "," +"'" + opNames.get(i) +"'";
				}
			}
		}
		if(!opNamestr.isEmpty()){
			sql = sql + " and OP_NAME in (" + opNamestr +")";
		}
		sql = sql + "  order by LOG_TIME DESC  ";
		List list = JdbcUtil.executeQuery(sql);
		assertEquals(list.size(),result.getJSONObject("data").getInt("totalRows"));
		Map<BigDecimal, HashMap> dbMap = new HashMap<BigDecimal, HashMap>();//接口和数据库返回的顺序不一致，所以先把数据库的记录暂存在map中，以备后面比较使用
		for (int l=0; l<list.size(); l++){
			HashMap map = (HashMap)list.get(l);
			dbMap.put((BigDecimal)map.get("ID"), map);
		}
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		for(int i = 0; i< data.length(); i++){
			JSONObject obj = (JSONObject)data.get(i);
			HashMap map = dbMap.get(obj.getBigDecimal("id"));//获取到数据库查询结果的响应记录
			assertEquals((BigDecimal)map.get("LOG_TIME"),obj.getBigDecimal("logTime"));
			assertEquals((String)map.get("USER_NAME"),obj.getString("userName"));
			assertEquals((String)map.get("OP_NAME"),obj.getString("opName"));
		}
	}


}
