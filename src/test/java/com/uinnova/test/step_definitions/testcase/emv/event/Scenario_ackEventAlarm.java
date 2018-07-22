package com.uinnova.test.step_definitions.testcase.emv.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.emv.event.AckEventAlarm;
import com.uinnova.test.step_definitions.api.emv.group.Validate;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-04-10
 * 编写人:yll
 * 功能介绍:确认单条告警
 * 备注：确认单条告警前，先判断当前用户是否有确认权限，若有权限，则再次进行判断该用户是否用户对该事件源的告警拥有确认权限（admin用户只要admin角色里面有确认或关闭权限， 不用管是否在团队里面）。
 */

public class Scenario_ackEventAlarm {
	
	/*@Given("^当用户所在角色管理中没有确认权限$")
	public void checkLoginUserAuthority(){
		
	}
	@When("^确认如下告警$")
		/*public void validateAuth(String multi, String opObj,DataTable table){
		Validate validate = new Validate();
		int type =1;
		if ("故障".compareToIgnoreCase(opObj)==0)
			type = 2;
		boolean multiFalg = true;
		if ("单条".compareToIgnoreCase(multi)==0)
			multiFalg = false;
		JSONObject result = validate.validate(type, multiFalg, table);
		assertTrue(result.getJSONObject("data").getBoolean("result"));
		
	}
	@Then("^确认失败，提示:因权限不足，确认失败$")*/
	
	@Given("^当前用户所在角色管理中有确认操作权限，且当前用户为admin用户:$")
	public void checkLoginUserAuthority(){
		
	}
	@When("^确认如下告警,确认信息为：\"(.*)\":$")
	public void validate(int type,  boolean multi, DataTable table){
		Validate validate = new Validate();
		JSONObject resultAck= validate.validate(type,multi,table);
	}
	public void ackEventAlarm(String ackInfo, DataTable table){		
		AckEventAlarm ackEventAlarm = new AckEventAlarm();
		JSONObject resultAck= ackEventAlarm.ackEventAlarm(ackInfo,table);
	}
	@Then("^该告警成功被确认，状态变为已确认:$")
	public void queryackEventAlarm(DataTable table){
		for (int i =1; i<table.raw().size(); i++){
			List<String> row = table.raw().get(i);
			String sql="SELECT status FROM mon_eap_event_memory where SOURCECINAME = '" +row.get(0)+ "' AND SOURCEALERTKEY = '" +row.get(1)+ "' ";
			ArrayList list = JdbcUtil.executeQuery(sql);
			int flag =0;
			for (int j =0 ; j<list.size(); j++){
				Map map = (Map) list.get(j);
				int status = (int) map.get("status");
				if (status==1)
					flag++;
			}	
			assertEquals(1, flag);
		}
	}
}
