package com.uinnova.test.step_definitions.testcase.base.license;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.license.auth.QueryLicenseAuthInfo;
import com.uinnova.test.step_definitions.api.base.license.auth.RegisterLicense;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-08 编写人:sunsl 功能介绍:产品授权功能的测试用例类
 */
public class Scenario_license {
	// 客户识别码
	String clientCode, dbClientCode,authCodeExce;
	JSONObject result;
	// 授权许可证码
	String authCode = "659f2aecaa3fbddb1a134293f128050d5ac12ab0c6e1b0875dfa3e3b62e0fe11ef45e5dcc4a7f27fc09a2d0052eff3a126f2e5b9e4724edb3d77257464935c228f71c67fa17a95f73aab83f3b6d89b6e9540a99f6c9742169f1ccc81297396506882b19340a313695471ae6f767a92359e3deed8125eb7aad1fd7a7e039e7bffa16525b867cc9fad512180923c889d2a24261451cd2160230ac6cffdf1d3164e9bcc6cba145bfe5ca3b3b6fb7d67777d25d42ea3973628c16189e78eff301ed21411d35fd3ef1b91a77439edb2e87b7d8ebeb70049ce9e3e5b494ba9114e9b4c78dc509d4ac21e5ee8558fc907bf324e43ff11a4cb79c060a50e7b423a1d4f02";

	/* ================license_生成客户识别码===================== */
//	@When("^生成客户识别码$")
//	public void createClientCode() {
//		String sql = "SELECT CLIENT_CODE FROM cc_license_auth";
//		ArrayList list = JdbcUtil.executeQuery(sql);
//		HashMap clientCodeHashMap = (HashMap) list.get(0);
//		if(clientCodeHashMap.get("CLIENT_CODE") != null){
//		  clientCode = clientCodeHashMap.get("CLIENT_CODE").toString();
//		}else{
//			clientCode = null;
//		}
//		CreateClientCode cc = new CreateClientCode();
//		result = cc.createClientCode();
//		assertTrue(result.getBoolean("success"));
//	}
//
//	@Then("^客户识别码生成成功$")
//	public void checkCreateClientCode() {
//		dbClientCode = result.getString("data");
//		if (!dbClientCode.equals(clientCode)) {
//			assertTrue(true);
//		} else {
//			fail();
//		}
//	}

	/* ================license_注册授权许可证===================== */
	@When("^注册授权许可证$")
	public void registerLicense() {
		RegisterLicense rl = new RegisterLicense();
		result = rl.registerLicense(authCode);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^授权许可证注册成功$")
	public void checkRegisterLicense() {
		JSONObject data = result.getJSONObject("data");
		JSONObject auth = data.getJSONObject("auth");
		if (authCode.equals(auth.getString("authCode"))) {
			assertTrue(true);
				
		}else{
			fail();
		}
	}
	
	/*==========================Scenario Outline: license_异常注册授权许可证=================================*/
	@When("^异常注册授权许可证\"(.*)\"$")
	public void registerLicenseExce(String authCode){
        String filePath = Scenario_license.class.getResource("/").getPath() + "testData/license/" + authCode;
        authCode = (new TxtUtil()).readTxt(filePath);
		String sql = "SELECT AUTH_CODE FROM cc_license_auth";
		ArrayList list = JdbcUtil.executeQuery(sql);
		HashMap authCodeHashMap = (HashMap)list.get(0);
		authCodeExce = authCodeHashMap.get("AUTH_CODE").toString();
		RegisterLicense rl = new RegisterLicense();
		try{
		  result = rl.registerLicense(authCode);
		}catch(Exception e){
			e.getMessage();
			assertTrue(true);
		}		
	}
	
	@Then("^异常授权许可证\"(.*)\"注册失败$")
	public void checkRegisterLicenseExce(String authCode){
		String sql = "SELECT AUTH_CODE FROM cc_license_auth";
		ArrayList list = JdbcUtil.executeQuery(sql);
		HashMap authCodeHashMap = (HashMap)list.get(0);
		if (authCodeExce.equals(authCodeHashMap.get("AUTH_CODE").toString())){
		    assertTrue(true);
		}else{
			fail();
		}
	}
	
	/*================================== Scenario: license_使用时间有效验证=========================================*/
	@When("^使用时间有效验证$")
	public void checkUtTime() throws InterruptedException{
		QueryLicenseAuthInfo qlBefore = new QueryLicenseAuthInfo();
		result = qlBefore.queryLicenseAuthInfo();
		JSONObject dataBefore = result.getJSONObject("data");
		JSONObject authBefore = dataBefore.getJSONObject("auth");
		BigDecimal utTime = authBefore.getBigDecimal("utTime");
		Thread.sleep(60000);
		QueryLicenseAuthInfo qlAfter = new QueryLicenseAuthInfo();
		result = qlAfter.queryLicenseAuthInfo();
		JSONObject dataAfter = result.getJSONObject("data");
		JSONObject authAfter = dataAfter.getJSONObject("auth");
		if(authAfter.getBigDecimal("utTime").compareTo(utTime) == 1){
			assertTrue(true);
		}else{
			fail();
		}
	}
}
