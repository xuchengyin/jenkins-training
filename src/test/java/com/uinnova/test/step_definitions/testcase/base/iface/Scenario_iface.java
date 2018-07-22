package com.uinnova.test.step_definitions.testcase.base.iface;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.xi.iface.ActiveZabbix;
import com.uinnova.test.step_definitions.api.base.xi.iface.QueryList;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-08 编写人:sunsl 功能介绍:数据集成测试用例类
 */
public class Scenario_iface {
	JSONObject result;
	//String tplId;

	/* ================Scenario Outline: iface_增加数据源,搜索数据源======= */
	@When("^给模板名称为\"(.*)\"增加数据源为\"(.*)\"$")
	public void createIface(String tplName, String ifaceName) {
		ActiveZabbix az = new ActiveZabbix();
		result = az.activeZabbix(tplName, ifaceName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^系统中存在\"(.*)\"数据源$")
	public void checkCreateIface(String ifaceName){
		/**
		 * @author ldw
		 * 此功能和domain_id无关，所以去掉
		 * 
		 * */
		String sql = "SELECT ID FROM cc_iface WHERE IFACE_NAME ='" +ifaceName + "' AND DATA_STATUS = 1";
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertTrue(list.size() > 0);		
	}
	
	@When("^搜索名称包含\"(.*)\"数据源$")
	public void searchIface(String searchKey){
		QueryList ql = new QueryList();
		result = ql.queryList(searchKey);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^名称包含\"(.*)\"的数据源全部搜索出来$")
	public void checkSearchIface(String searchKey){
		String sql = "SELECT ID,IFACE_NAME FROM cc_iface WHERE IFACE_NAME LIKE '% " + searchKey+"%' AND DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id+" ORDER BY ID";
		ArrayList list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		
		if (list.size() == data.length()){
			for(int i = 0; i < list.size(); i ++){
			    HashMap ifaceHashMap = (HashMap)list.get(i);
			    JSONObject obj = (JSONObject)data.get(i);
			    String ifaceName = (String)ifaceHashMap.get("IFACE_NAME");
			    String ifaceNameDb = obj.getString("ifaceName");
			    if(ifaceName.equals(ifaceNameDb)){
			    	assertTrue(true);
			    }else{
			    	fail();
			    }
			}
		}
	}
	
	@When("^删除名称为\"(.*)\"数据源$")
	public void deleteIface(String ifaceName){
		String sql = "UPDATE cc_iface SET DATA_STATUS = 0  WHERE IFACE_NAME = '" + ifaceName +"' AND DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		JdbcUtil.executeUpdate(sql);		
	}
}
