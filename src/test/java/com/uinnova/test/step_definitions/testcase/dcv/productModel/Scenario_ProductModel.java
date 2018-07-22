package com.uinnova.test.step_definitions.testcase.dcv.productModel;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dcv.dcModelItem.ExportMapping;
import com.uinnova.test.step_definitions.api.dcv.dcModelItem.ExportPort;
import com.uinnova.test.step_definitions.api.dcv.dcModelItem.QueryMappingPage;
import com.uinnova.test.step_definitions.api.dcv.dcModelItem.QueryProductPage;
import com.uinnova.test.step_definitions.api.dcv.dcModelItem.UploadMapping;
import com.uinnova.test.step_definitions.api.dcv.dcModelItem.UploadPort;
import com.uinnova.test.step_definitions.api.dcv.dcModelItem.UploadZip;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间: 2018-1-26 编写人:wjx
 * 全局资源管理：资源上传、模型库管理
 */

public class Scenario_ProductModel {
	JSONObject result;
	String filePath;

	@When("^上传名称为\"(.*)\"产品库文件$")
	public void upload(String fileName) {
		UploadZip uploadZip = new UploadZip();
		result = uploadZip.uploadProduct(fileName);
		assertTrue(result.getBoolean("success"));
		QaUtil.report("=========上传产品库完成===========");
	}

	@Then("^成功上传名称为\"(.*)\"产品库文件$")
	public void checkUploadZip(String fileName) {
			String sql = "SELECT ID FROM `dc_file` where FILE_NAME = '" + fileName + "' AND "
					+ "DATA_STATUS = 1 and DOMAIN_ID ="+ QaUtil.domain_id;
			ArrayList<?> list = JdbcUtil.executeQuery(sql);
			assertTrue(list.size() > 0);
	}

	@When("^上传名称为\"(.*)\"错误产品库文件$")
	public void uploadError(String fileError) {
		UploadZip uploadZipError = new UploadZip();
		result = uploadZipError.uploadProduct(fileError);
		assertTrue(!result.getBoolean("success"));
	}

	@Then("^上传名称为\"(.*)\"产品库文件失败$")
	public void checkUploadZipEoor(String fileError) {
			String sql = "SELECT ID FROM `dc_file` where FILE_NAME = '" + fileError + "' AND "
					+ "DATA_STATUS = 1 and DOMAIN_ID ="+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertTrue(list.size() == 0);
	}

	@When("^下载端口映射文件$")
	public String exportProdutPort() {
		filePath = (new ExportPort()).downloadPort();
		File file = new File(filePath);
		assertTrue(file.exists());
		QaUtil.report("=========下载端口映射文件===========");
		return filePath;
	}
	
	@Then("^成功下载端口映射文件$")
	public void checkProductPort (){
		String sql = "select USER_MODEL FROM `dc_model_item_mp` "
				+ "where MP_TYPE = 2 and DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
		ArrayList list  = JdbcUtil.executeQuery(sql);

		try {
			Charset gbk = Charset.forName("gbk");
			//InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),"UTF-8");
			InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),gbk);
			BufferedReader br = new BufferedReader(read);
			String a = null;
			String b = null;
			while ((a = br.readLine()) != null) {
				b = a;
			}
//			JSONObject k = new JSONObject(b);
//			for(int i = 0;i < list.size();i++){
//				HashMap map = (HashMap)list.get(i);
//				String usermodel = (String) map.get("USER_MODEL");
//				assertTrue(k.has(usermodel));

			for(int i = 0;i < list.size();i++){
				HashMap map = (HashMap)list.get(i);
				String userModel = (String) map.get("USER_MODEL");
				assertTrue(b.indexOf(userModel)!=-1);
				}			
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		String updatePort = "UPDATE `dc_model_item_mp` set DATA_STATUS = 0 where"
				+ " MP_TYPE = 2 and DATA_STATUS = 1 AND DOMAIN_ID =1"+QaUtil.domain_id;
		JdbcUtil.executeUpdate(updatePort);
		
		File file = new File(filePath);
		file.delete();
	}
		


	@When("^上传名称为\"(.*)\"端口映射文件$")
	public void upProPort(String fileName) {
		UploadPort upPort = new UploadPort();
		result = upPort.uploadProductPort(fileName);
		assertTrue(result.getBoolean("success"));
		QaUtil.report("=========上传端口映射文件===========");
	}

	@Then("^成功上传端口映射文件\"(.*)\"$")
	public void checkUploadPort(String fileName) {
		String filePath = UploadZip.class.getResource("/").getPath() + 
				"testData/dcv/productModel/" + fileName;
		
		String sql = "select USER_MODEL FROM dc_model_item_mp WHERE "
				+ "DATA_STATUS = 1 AND MP_TYPE = 2 AND DOMAIN_ID = "+QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		try {
			Charset gbk = Charset.forName("gbk");
			//InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),"UTF-8");
			InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),gbk);
			BufferedReader br = new BufferedReader(read);
			String a = null;
			String b = null;
			while ((a = br.readLine()) != null) {
				b = a;
			}
			for(int i = 0;i < list.size();i++){
				HashMap map = (HashMap)list.get(i);
				String userModel = (String) map.get("USER_MODEL");
				assertTrue(b.indexOf(userModel)!=-1);
				}			
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
	}

	@When("^模型库页面搜索名称为\"(.*)\"的模型$")
	public JSONObject seachModelName(String modelName) {
		result = new QueryProductPage().searchModel(1, modelName);
		assertTrue(result.getBoolean("success"));
		return result;
	}
	
	@Then("^显示搜索结果\"(.*)\"$")
	public void checkSeachResult(String moName){
		String sql = "select count(1) from (select STD_NAME from DC_MODEL_ITEM "
				+ "where STD_NAME like '%"+moName+"%' and DOMAIN_ID = "+QaUtil.domain_id+" and "
						+ "DATA_STATUS = 1 order by ID ) as alias_for_selectcount";
		ArrayList list = JdbcUtil.executeQuery(sql);
		HashMap map = (HashMap)list.get(0);
		long usermodel = (long) map.get("count(1)");
		long n = result.getJSONObject("data").getLong("totalRows");
		assertTrue(usermodel == n);		
	}
	
	@When("^模型库页面点击下一页$")
	public JSONObject productNextPage(){
		QueryProductPage nextPa = new QueryProductPage();
		result = nextPa.searchModel(2, null);
		assertTrue(result.getBoolean("success"));
		return result;
	}
	
	@Then("^正确显示下一页内容$")
	public void checkPageNum(){
		int n = result.getJSONObject("data").getInt("pageNum");
		assertTrue(n == 2);
	}
	
	@When("^模型库页面跳至\"(.*)\"页$")
	public JSONObject productSkipPage(int skipNum){
		QueryProductPage nextPa = new QueryProductPage();
		result = nextPa.searchModel(skipNum, null);
		assertTrue(result.getBoolean("success"));
		return result;
			}
	
	@Then("^显示跳转页\"(.*)\"的模型$")
	public void skipPage(int skipNum){
		int n = result.getJSONObject("data").getInt("pageNum");
		assertTrue(n == skipNum);
		
	}
		
	@When("^下载模型映射文件$")
	public void downloadMoMapping(){
		filePath = (new ExportMapping().downloadProductMp());
		File file = new File(filePath);
		assertTrue(file.exists());
		QaUtil.report("=========下载模型映射文件映射文件===========");
	}
	
	@Then("^成功下载模型映射文件$")
	public void checkDownMoMapping(){
		String sql = "select USER_MODEL FROM `dc_model_item_mp` "
				+ "where MP_TYPE = 1 and DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
		ArrayList list  = JdbcUtil.executeQuery(sql);

		try {
			Charset gbk = Charset.forName("gbk");
			//InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),"UTF-8");
			InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),gbk);

			
			BufferedReader br = new BufferedReader(read);
			String a = null;
			String b = null;
			while ((a = br.readLine()) != null) {
				b = a;
			}
			for(int i = 0;i < list.size();i++){
				HashMap map = (HashMap)list.get(i);
				String userModel = (String) map.get("USER_MODEL");
				assertTrue(b.indexOf(userModel)!=-1);
//			JSONObject k = new JSONObject(b);
//			for(int i = 0;i < list.size();i++){
//				HashMap map = (HashMap)list.get(i);
//				String usermodel = (String) map.get("USER_MODEL");
//				assertTrue(k.has(usermodel));
				}			
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		String updatePort = "UPDATE `dc_model_item_mp` set DATA_STATUS = 0 where"
				+ " MP_TYPE = 1 and DATA_STATUS = 1 AND DOMAIN_ID =1"+QaUtil.domain_id;
		JdbcUtil.executeUpdate(updatePort);
		
		File file = new File(filePath);
		file.delete();
	}
	
	@When("^上传名称为\"(.*)\"模型映射文件$")
	public void upModelMapping(String mappingFile){
		UploadMapping modelMapping = new UploadMapping();
		result = modelMapping.uploadProductMp(mappingFile);
		assertTrue(result.getBoolean("success"));
		QaUtil.report("=========上传模型映射文件完成===========");
	}
		
	@Then("^成功上传模型映射文件\"(.*)\"$")
	public void checkUpMoMapping(String fileName){
		String filePath = UploadZip.class.getResource("/").getPath() + 
				"testData/dcv/productModel/" + fileName;
		String sql = "select USER_MODEL FROM dc_model_item_mp WHERE "
				+ "DATA_STATUS = 1 AND MP_TYPE = 1 AND DOMAIN_ID = "+QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		try {
			Charset gbk = Charset.forName("gbk");
			//InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),"UTF-8");
			InputStreamReader read = new InputStreamReader(new FileInputStream(new File(filePath)),gbk);
			BufferedReader br = new BufferedReader(read);
			String a = null;
			String b = null;
			while ((a = br.readLine()) != null) {
				b = a;
			}
			for(int i = 0;i < list.size();i++){
				HashMap map = (HashMap)list.get(i);
				String userModel = (String) map.get("USER_MODEL");
				assertTrue(b.indexOf(userModel)!=-1);
				}			
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
	}
	
	@When("^模型映射页面搜索名称为\"(.*)\"的模型$")
	public JSONObject searchModelMa(String moMpName){
		result = new QueryMappingPage().searchModel(1, moMpName);
		assertTrue(result.getBoolean("success"));
		return result;
	}
	
	
	@Then("^模型映射界面搜索\"(.*)\"结果正确$")
	public void checkSearchModel(String moMpName){
		String sql = "select count(1) from (select USER_MODEL from DC_MODEL_ITEM_MP "
				+ "where MP_TYPE =1 and DC_ID =0 and USER_MODEL "
				+ "like '%"+moMpName+"%' and DOMAIN_ID ="+QaUtil.domain_id+" and "
						+ "DATA_STATUS =1 order by ID) as alias_for_selectcount";
		ArrayList list = JdbcUtil.executeQuery(sql);
		HashMap map = (HashMap)list.get(0);
		long userModel = (long) map.get("count(1)");
		long n = result.getJSONObject("data").getLong("totalRows");
		assertTrue(userModel == n);
	}
	
	@When("^模型映射界面点击下一页$")
	public JSONObject mappingNext(){
		QueryMappingPage maNext = new QueryMappingPage();
		result = maNext.searchModel(2, null);
		assertTrue(result.getBoolean("success"));
		return result;
	}
	
	@Then("^显示下一页模型映射内容$")
	public void checkMaNext(){
		int n = result.getJSONObject("data").getInt("pageNum");
		assertTrue(n == 2);	
	}
	
	@When("模型映射页面跳至\"(.*)\"页$")
	public JSONObject mappingSkip(int skipNum){
		QueryMappingPage nextPa = new QueryMappingPage();
		result = nextPa.searchModel(skipNum, null);
		assertTrue(result.getBoolean("success"));
		return result;
	}

	@Then("^显示跳转页\"(.*)\"模型映射内容$")
	public void checkMappingSkip(int skipNum){
		int n = result.getJSONObject("data").getInt("pageNum");
		assertTrue(skipNum == n);
	}
	
	
}

