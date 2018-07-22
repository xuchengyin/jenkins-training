package com.uinnova.test.step_definitions.testcase.base.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.license.auth.QueryLicenseAuthInfo;
import com.uinnova.test.step_definitions.api.base.sys.auth.QueryRoleList;
import com.uinnova.test.step_definitions.api.base.sys.auth.SaveOrUpdateRole;
import com.uinnova.test.step_definitions.api.base.sys.user.DownloadExcelTpl;
import com.uinnova.test.step_definitions.api.base.sys.user.ExportExcel;
import com.uinnova.test.step_definitions.api.base.sys.user.ImportExcel;
import com.uinnova.test.step_definitions.api.base.sys.user.QueryInfoById;
import com.uinnova.test.step_definitions.api.base.sys.user.QueryInfoPage;
import com.uinnova.test.step_definitions.api.base.sys.user.RemoveOpById;
import com.uinnova.test.step_definitions.api.base.sys.user.ResetUserPasswdByAdmin;
import com.uinnova.test.step_definitions.api.base.sys.user.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.cmv.integration.GetCurUser;
import com.uinnova.test.step_definitions.api.cmv.sys.user.QueryById;
import com.uinnova.test.step_definitions.api.cmv.sys.user.UpdateCurUser;
import com.uinnova.test.step_definitions.utils.base.UserUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-11-05 编写人:sunsl 功能介绍:用户管理测试用例类
 */
public class Scenario_user {
	String filePath;
	JSONObject result;
	private List<String> userList = new ArrayList<String>(); //用于记录一共新建了多少小组， 用户After方法清理数据

	
	@After("@DelUser")
	public void delUser(){
		if (!userList.isEmpty()){
			for (int i=0; i<userList.size(); i++){
				String opCode = userList.get(i);
				RemoveOpById robi = new RemoveOpById();
				result = robi.removeOpById(opCode);
				assertTrue(true);
				userList.remove(opCode);
				i--;
			}
		}
	}

	@After("@switchDefaultUser")
	public void tearDown() throws Exception{
		QaUtil.switchDefaultUser();
	}
	/* ==================Scenario:User_新建用户、更改用户、重置密码、删除用户=================== */
	@Given("^角色管理里有三个角色$")
	public void checkRoleList() {
		QueryRoleList qr = new QueryRoleList();
		SaveOrUpdateRole su = new SaveOrUpdateRole();
		result = qr.queryRoleList();
		JSONArray data = result.getJSONArray("data");
		if (data.length() < 3) {
			result = su.saveOrUpdateRole("222","222", "444","");
			result = su.saveOrUpdateRole("333","333","555","");
		}
		assertTrue(result.getBoolean("success"));
	}

	@When("^创建登录名为\"(.*)\"，用户名为\"(.*)\"，所属组织为\"(.*)\"，初始密码为\"(.*)\"，电子邮箱为\"(.*)\"，联系方式为\"(.*)\"，描述为\"(.*)\"的用户$")
	public void createUser(String opCode, String opName, String custom1, String loginPasswd, String emailAdress,
			String mobileNo, String notes) {
		SaveOrUpdate su = new SaveOrUpdate();
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		if (opName.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opName;
			opName = (new TxtUtil()).readTxt(filePath);
		}
		result = su.saveOrUpdate(opCode, opName,custom1,loginPasswd,emailAdress,mobileNo,notes,0);
		assertTrue(result.getBoolean("success"));
		userList.add(opCode);
	}

	@Then("^系统中存在登录名为\"(.*)\"，用户名为\"(.*)\"，所属组织为\"(.*)\"，初始密码为\"(.*)\"，电子邮箱为\"(.*)\"，联系方式为\"(.*)\"，描述为\"(.*)\"的用户$")
	public void checkCreateUser(String opCode, String opName, String custom1, String loginPasswd, String emailAdress,
			String mobileNo, String notes) {				
		QueryInfoById qi = new QueryInfoById();
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		if (opName.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opName;
			opName = (new TxtUtil()).readTxt(filePath);
		}
		UserUtil userUtil = new UserUtil();
		result = qi.queryInfoById(userUtil.getUserId(opCode));
		JSONObject data = result.getJSONObject("data");
		JSONObject op = data.getJSONObject("op");
		assertEquals("校验登录名："+opCode,opCode, op.getString("opCode"));
		assertEquals("校验用户名："+opName, opName, op.getString("opName"));
		assertEquals("校验所属租住："+custom1,custom1, op.getString("custom1"));
		assertEquals("校验邮箱："+emailAdress,emailAdress, op.getString("emailAdress"));
		assertEquals("校验联系方式："+mobileNo,mobileNo, op.getString("mobileNo"));
		assertEquals("校验描述："+notes,notes, op.getString("notes"));
	}
	
	@And("^登录名为\"(.*)\"密码为\"(.*)\"的用户可以登录成功$")
	public void checkCreateUserLogin(String opCode, String loginPasswd) {				
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		String token = QaUtil.getToken(opCode, loginPasswd);
		assertFalse(token.isEmpty());
	}

	@When("^登录名为\"(.*)\"的用户修改用户名为\"(.*)\"，所属组织为\"(.*)\"，电子邮箱为\"(.*)\"，联系方式为\"(.*)\"，描述为\"(.*)\"的用户$")
	public void updateUser(String opCode,String updateOpName, String updateCustom1, String updateEmailAdress, String updateMobileNo,
			String updateNotes) {
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		SaveOrUpdate su = new SaveOrUpdate();
		result = su.saveOrUpdate(opCode, updateOpName,updateCustom1,"",updateEmailAdress,updateMobileNo,updateNotes,1);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^登录名为\"(.*)\"的用户的用户名为\"(.*)\"，所属组织为\"(.*)\"，电子邮箱为\"(.*)\"，联系方式为\"(.*)\"，描述为\"(.*)\"的用户修改成功$")
	public void checkUpdateUser(String opCode,String updateOpName, String updateCustom1, String updateEmailAdress,
			String updateMobileNo, String updateNotes) {
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		JSONArray roles = new JSONArray();
		UserUtil  userUtil = new UserUtil();
		QueryInfoById qi = new QueryInfoById();
		result = qi.queryInfoById(userUtil.getUserId(opCode));
		JSONObject data = result.getJSONObject("data");
		JSONObject op = data.getJSONObject("op");
		roles = data.getJSONArray("roles");
		if (updateOpName.equals(op.getString("opName")) && updateCustom1.equals(op.getString("custom1"))
				&& updateEmailAdress.equals(op.getString("emailAdress"))
				&& updateMobileNo.equals(op.getString("mobileNo")) && updateNotes.equals(op.getString("notes"))) {
			if (roles.length() == 1) {
				assertTrue(true);
			} else {
				fail();
			}

		} else {
			fail();
		}
	}

	@When("^登录名为\"(.*)\"的用户,修改密码为\"(.*)\"$")
	public void resetLoginPasswd(String opCode,String updateLoginPasswd) {
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		ResetUserPasswdByAdmin rupb = new ResetUserPasswdByAdmin();
		result = rupb.resetUserPasswdByAdmin(opCode, updateLoginPasswd);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^登录名为\"(.*)\"的用户 的原密码为\"(.*)\",密码修改为\"(.*)\"$")
	public void checkResetLoginPasswd(String opCode,String loginPasswd ,String updateLoginPasswd) {
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		UserUtil userUtil = new UserUtil();
		String sql = "SELECT LOGIN_PASSWD FROM sys_op WHERE ID = '" + userUtil.getUserId(opCode) + "' and DATA_STATUS=1 and USER_DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		HashMap userHashMap = (HashMap) list.get(0);
		String updatePasswd = userHashMap.get("LOGIN_PASSWD").toString();
		assertTrue (!loginPasswd.equals(updatePasswd));
	}

	@And("^登录名为\"(.*)\"的用户密码修改为\"(.*)\"后登录成功$")
	public void checkResetLoginPasswdLogin(String opCode,String updateLoginPasswd) throws Exception {
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		String token = QaUtil.getToken(opCode, updateLoginPasswd);
		assertFalse(token.isEmpty());
	}
	
	@When("^删除用户名为\"(.*)\"的用户$")
	public void deleteUser(String opCode) {
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		RemoveOpById robi = new RemoveOpById();
		result = robi.removeOpById(opCode);
		assertTrue(true);
		userList.remove(opCode);
	}

	@Then("^系统中不存在名称为\"(.*)\"的用户$")
	public void checkDeleteUser(String opCode) {
		if (opCode.indexOf(".") > 0) {
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + opCode;
			opCode = (new TxtUtil()).readTxt(filePath);
		}
		String sql = "SELECT ID FROM sys_op WHERE OP_CODE = '" + opCode + "' and DATA_STATUS=1 and USER_DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		if (list.size() == 0) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	/* ==================Scenario:User_下载模板=================== */
	@When("^下载\"用户模板\"$")
	public void downloadUserModel() {
		filePath = (new DownloadExcelTpl()).downloadExcelTpl();
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^\"用户模板\"文件下载成功$")
	public void checkDownloadUserModel() {
		JSONArray arr = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		assertEquals(1, arr.length());
		assertEquals(8, arr.getJSONObject(0).length());
	}

	/* ==================Scenario:User_导出================== */
	@When("^导出用户信息$")
	public void exportExcel() {
		// 导出用户信息
		ExportExcel exportExcel = new ExportExcel();
		filePath = exportExcel.exportExcel();
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^用户信息导出成功$")
	public void checkExportExcel() {
		// 导出Excel数据
		JSONArray array = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		// 查询数据库数据
		QueryInfoPage qi = new QueryInfoPage();
		String opName = "";
		result = qi.queryInfoPage(opName);
		JSONObject data = result.getJSONObject("data");
		JSONArray dataArray = data.getJSONArray("data");
		if (array.length() == dataArray.length()) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject excelObj = array.getJSONObject(i);
				JSONObject dbObj = dataArray.getJSONObject(i);
				JSONObject op = dbObj.getJSONObject("op");
				if (op.getString("opCode").equals(excelObj.getString("0"))
						&& op.getString("opName").equals(excelObj.getString("1"))
						&& op.getString("emailAdress").equals(excelObj.getString("4"))) {
					assertTrue(true);
				} else {
					fail();
				}
			}
		}
	}

	/* ==================Scenario:User_导入================== */
	@When("^导入用户信息\"(.*)\"$")
	public void importExcel(String fileName) {
		String filePath = null;
		if(QaUtil.domain_id.intValue() > 1){
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + "用户信息-1.xls";
		}else{
			filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + fileName;
		}
		
		ImportExcel ie = new ImportExcel();
		result = ie.importExcel(filePath);
		assertTrue(result.getBoolean("success"));
		
		JSONArray array = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		for (int i = 1; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.getJSONObject(i);
			String opCode = obj.getString("0");
			userList.add(opCode);
		}
	}

	@Then("^用户信息\"(.*)\"导入成功$")
	public void checkImportExcel(String fileName) {
		String filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + fileName;
		// 读取Excel的值去数据库检索
		JSONArray array = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		for (int i = 1; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.getJSONObject(i);
			String opCode = obj.getString("0");
			String sql = "SELECT ID FROM sys_op WHERE OP_CODE = '" + opCode + "' and DATA_STATUS = 1 and USER_DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList list = JdbcUtil.executeQuery(sql);
			HashMap opHashMap = (HashMap) list.get(0);
			assertTrue (list.size() > 0);
		}
	}
	
	@And("^用户信息\"(.*)\"中的用户导入后登录成功$")
	public void checkImportExcelUserLogin(String fileName) {
		String filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + fileName;
		// 读取Excel的值去登录
		JSONArray array = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		for (int i = 1; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.getJSONObject(i);
			String opCode = obj.getString("0");
			String loginPasswd = obj.getString("3");
			String token = QaUtil.getToken(opCode, loginPasswd);
			assertFalse(token.isEmpty());
		}
	}
	@When("^删除导入的用户信息\"(.*)\"$")
	public void deleteImportExcel(String fileName) {
		String filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + fileName;
		// 读取Excel的值去数据库检索
		JSONArray array = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		for (int i = 1; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.getJSONObject(i);
			String opCode = obj.getString("0");
			RemoveOpById robi = new RemoveOpById();
			result = robi.removeOpById(opCode);
			userList.remove(opCode);
		}
	}
 	@Then("^用户信息\"(.*)\"中的用户登录失败$")
 	public void checkDeleteImportExcel(String fileName) {
 		String filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/" + fileName;
		// 读取Excel的值去登录
		JSONArray array = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		for (int i = 1; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.getJSONObject(i);
			String opCode = obj.getString("0");
			String loginPasswd = obj.getString("3");
			String token = QaUtil.getToken(opCode, loginPasswd);
			assertTrue(token.isEmpty());
		}
	}
	
	/* ========================Scenario Outline: User_用户搜索================= */
	@And("^搜索登录名包含\"(.*)\"的用户$")
	public void searchUser(String searchKey) {
		QueryInfoPage qi = new QueryInfoPage();
		result = qi.queryInfoPage(searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^包含\"(.*)\"关键字的的用户全部搜索出来$")
	public void checkSearchUser(String searchKey) {
		String sql = "SELECT OP_CODE,OP_NAME,LOGIN_CODE FROM sys_op WHERE (OP_CODE LIKE '%" + searchKey
				+ "%' OR OP_NAME LIKE '%" + searchKey + "%' OR LOGIN_CODE LIKE '%" + searchKey
				+ "%' ) AND DATA_STATUS=1 AND USER_DOMAIN_ID = "+ QaUtil.domain_id +" ORDER BY LOGIN_CODE";

		ArrayList list = JdbcUtil.executeQuery(sql);
		JSONObject data = result.getJSONObject("data");
		JSONArray dataArray = data.getJSONArray("data");
		if (dataArray.length() == list.size()) {
			for (int i = 0; i < list.size(); i++) {
				JSONObject dataObj = (JSONObject) dataArray.get(i);
				JSONObject op = dataObj.getJSONObject("op");
				HashMap opHashMap = (HashMap) list.get(i);
				 
				if (opHashMap.get("OP_CODE").equals(op.getString("opCode"))
						&&(opHashMap.get("OP_NAME")).equals(op.getString("opName"))
					&& (opHashMap.get("LOGIN_CODE")).equals(op.getString("loginCode"))) {
                   assertTrue(true);
				}else{
					fail();
				}
			}
		}
	}

	/*============================Scenario: User_超出用户数导入=================*/
	@When("^超出用户数导入用户信息$")
	public void superImportExcel(){
		 filePath = Scenario_user.class.getResource("/").getPath() + "testData/user/超出用户数用户信息.xls" ;
		 QueryLicenseAuthInfo ql = new QueryLicenseAuthInfo();
		 result = ql.queryLicenseAuthInfo();
		 JSONObject data = result.getJSONObject("data");
		 JSONObject auth = data.getJSONObject("auth");
		 Integer authCustom1 = auth.getInt("authCustom1");
		 JSONArray arry = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		 ImportExcel ie = new ImportExcel();
		 if (authCustom1==(arry.length()-1)){
			 result = ie.importExcel(filePath);
			 assertTrue(!result.getBoolean("success"));
		 }else{
			 fail();
		 }

	}
	
	@Then("^超出用户数用户信息导入失败$")
	public void checkSuperImportExcel(){
		JSONArray array = (new ExcelUtil()).readFromExcel(filePath, "Sheet0");
		for (int i = 1; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.getJSONObject(i);
			String opCode = obj.getString("0");
			String sql = "SELECT ID FROM sys_op WHERE OP_CODE = '" + opCode + "' and DATA_STATUS = 1 and USER_DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList list = JdbcUtil.executeQuery(sql);
			if (list.size() == 0) {
				assertTrue(true);
			} else {
				fail();
			}
		}
	}

	@Then("^修改用户信息,用如下表单:$")
	public void updateCurUser(DataTable dt){
		List<List<String>> list = dt.raw();
		JSONObject obj = new JSONObject();
		for (int i = 1; i < list.size(); i++) {
			obj.put(list.get(i).get(0), list.get(i).get(1));
		}
		UpdateCurUser ucu = new UpdateCurUser();
		JSONObject result = ucu.updateCurUser(obj);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^验证修改，用如下表单:$")
	public void compUpdateCurUser(DataTable dt){
		List<List<String>> list = dt.raw();
		GetCurUser gcu = new GetCurUser();	
		QueryById qbi = new QueryById();
		JSONObject resultObj = qbi.queryById(gcu.getCurUser().getJSONObject("data").getBigDecimal("id").toString()).getJSONObject("data");
		for (int i = 1; i < list.size(); i++) {
			if(i != 3){
			   assertEquals(resultObj.get(list.get(i).get(0)).toString(),list.get(i).get(1));
			}
			
		}
	}
}
