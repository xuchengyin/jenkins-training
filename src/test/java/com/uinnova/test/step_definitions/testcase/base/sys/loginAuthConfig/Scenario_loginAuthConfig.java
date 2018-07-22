package com.uinnova.test.step_definitions.testcase.base.sys.loginAuthConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig.QueryEffective;
import com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig.QueryLdapInfoByCode;
import com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig.SaveLdapConfigInfo;
import com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig.SetEffective;
import com.uinnova.test.step_definitions.api.base.sys.loginAuthConfig.TestLdapConnection;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_loginAuthConfig {
	
	@When("^保存集成LDAP配置,用如下配置:$")
	public void saveLdapConfigInfo(DataTable dt){
		List<List<String>> list = dt.raw();
		JSONObject record = new JSONObject();
		JSONObject ldapProp = new JSONObject();
		SaveLdapConfigInfo slci = new SaveLdapConfigInfo();
		for(int i = 1; i < list.size(); i++){
			if(i < 3){
				record.put(list.get(i).get(0), list.get(i).get(1));
			}else{
				ldapProp.put(list.get(i).get(0), list.get(i).get(1));
			}
		}
		JSONObject result = slci.saveLdapConfigInfo(record, ldapProp);
		assertTrue(result.getBoolean("success"));
//		assertTrue(result.getBigDecimal("data").intValue() > 0);
	}
	
	@When("^保存集成LDAP配置,用如下配置,设置失败:$")
	public void saveLdapConfigInfoFailed(DataTable dt){
		List<List<String>> list = dt.raw();
		JSONObject record = new JSONObject();
		JSONObject ldapProp = new JSONObject();
		SaveLdapConfigInfo slci = new SaveLdapConfigInfo();
		for(int i = 1; i < list.size(); i++){
			if(i < 3){
				record.put(list.get(i).get(0), list.get(i).get(1));
			}else{
				ldapProp.put(list.get(i).get(0), list.get(i).get(1));
			}
		}
		JSONObject result = slci.saveLdapConfigInfoFailed(record, ldapProp, list.get(1).get(2));
//		assertTrue(result.getBoolean("success"));
//		assertTrue(result.getBigDecimal("data").intValue() > 0);
	}
	
	@Then("^查询集成LDAP配置,用如下配置:$")
	public void queryLdapInfoByCode(DataTable dt){
		List<List<String>> list = dt.raw();
		JSONObject record = new JSONObject();
		JSONObject ldapProp = new JSONObject();
		QueryLdapInfoByCode qlibc = new QueryLdapInfoByCode();
		for(int i = 1; i < list.size(); i++){
			if(i < 3){
				record.put(list.get(i).get(0), list.get(i).get(1));
			}else{
				ldapProp.put(list.get(i).get(0), list.get(i).get(1));
			}
		}
		JSONObject result = qlibc.queryLdapInfoByCode(record.getString("protoCode")).getJSONObject("data");
		for(int i = 1; i < list.size(); i++){
			if(i < 4){
				assertEquals(list.get(i).get(1),result.getJSONObject("record").get(list.get(i).get(0)).toString());
			}else{
				assertEquals(list.get(i).get(1),result.getJSONObject("ldapProp").get(list.get(i).get(0)).toString());
			}
		}
		
	}
	
	
	@When("^设置登录方式为\"(.*)\".$")
	public void setEffective(String effective){
		SetEffective se = new SetEffective();
		JSONObject result = se.setEffective(effective);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^登录方式设置为\"(.*)\"成功$")
	public void queryEffective(String effective){
		QueryEffective qe = new QueryEffective();
		JSONObject result = qe.queryEffective();
		assertEquals(result.getString("data"),effective);
	}
	
	@When("^设置登录方式为\"(.*)\"失败,keyword=\"(.*)\"$")
	public void setEffectiveFailed(String effective, String kw){
		SetEffective se = new SetEffective();
		JSONObject result = se.setEffectiveFailed(effective,kw);
//		assertFalse(result.getBoolean("success"));
	}
	
	@Then("^用一下参数设置ldap连接公司LDAP可通过:$")
	public void testLdapConnection(DataTable dt){
		TestLdapConnection tlc = new TestLdapConnection();
//		tlc.testLdapConnection(type, protoName, hostName, port, baseDn, loginUserDn, password, testUserName, testPassword)
		List<List<String>> list = dt.raw();
		for (int i = 1; i < list.size(); i++) {
			JSONObject result = tlc.testLdapConnection(list.get(i).get(0), 
					list.get(i).get(1), 
					list.get(i).get(2), 
					list.get(i).get(3), 
					list.get(i).get(4), 
					list.get(i).get(5), 
					list.get(i).get(6), 
					list.get(i).get(7), 
					list.get(i).get(8));
			assertTrue(result.getBoolean("data"));
		}
		
	}
	
	@Then("^用一下参数设置ldap连接公司LDAP失败:$")
	public void testLdapConnectionFailed(DataTable dt){
		TestLdapConnection tlc = new TestLdapConnection();
//		tlc.testLdapConnection(type, protoName, hostName, port, baseDn, loginUserDn, password, testUserName, testPassword)
		List<List<String>> list = dt.raw();
		for (int i = 1; i < list.size(); i++) {
			JSONObject result = tlc.testLdapConnection(list.get(i).get(0), 
					list.get(i).get(1), 
					list.get(i).get(2), 
					list.get(i).get(3), 
					list.get(i).get(4), 
					list.get(i).get(5), 
					list.get(i).get(6), 
					list.get(i).get(7), 
					list.get(i).get(8));
			assertEquals(result.getBoolean("data"),false);
		}
		
	}
	
	
}
