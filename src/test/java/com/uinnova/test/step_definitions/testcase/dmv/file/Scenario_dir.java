package com.uinnova.test.step_definitions.testcase.dmv.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;

import com.uinnova.test.step_definitions.api.dmv.file.QueryFileRootDir;
import com.uinnova.test.step_definitions.api.dmv.file.RemoveDirByIds;
import com.uinnova.test.step_definitions.api.dmv.file.SaveOrUpdateDir;
import com.uinnova.test.step_definitions.utils.dmv.FormUtil;

import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-04-20
 * 编写人:sunsl
 * 功能介绍:DMV_表格_测试用例
 */
public class Scenario_dir {
   /*============DMV_文件_新建文件夹,重命名文件夹,删除文件夹==========*/
   private JSONObject result;
   private List<String> formDirList = new ArrayList<String>(); //用于记录一共新建了多少文件夹， 用户After方法清理数据
   @After("@delFormDir")
	public void delDiagraDir(){
		if (!formDirList.isEmpty()){
			//for (int i=0; i<formDirList.size(); i++){
				//String formDirName = formDirList.get(i);
				RemoveDirByIds  removeDirByIds = new RemoveDirByIds();
				result = removeDirByIds.removeDirByIds(formDirList);
				assertTrue(result.getBoolean("success"));
				//formDirList.remove(formDirName);
				//i--;
			//}
		}
	}
   
   @When("^在表格中创建名称为\"(.*)\"的文件夹$")
   public void saveOrUpdateDir(String dirName){
	   SaveOrUpdateDir saveOrUpdateDir = new SaveOrUpdateDir();
	   QueryFileRootDir queryFileRootDir = new QueryFileRootDir();
	   result = queryFileRootDir.queryFileRootDir();
	   JSONObject data = result.getJSONObject("data");
	   BigDecimal parentId =data.getBigDecimal("id");
	   result = saveOrUpdateDir.saveOrUpdateDir(dirName,parentId);
	   assertTrue(result.getBoolean("success"));
	   formDirList.add(dirName);
   }
   
   @Then("^在表格中名称为\"(.*)\"的文件夹创建成功$")
   public void checkSaveOrUpdateDir(String dirName){
	   FormUtil formUtil = new FormUtil();
	   BigDecimal dirId = formUtil.getDirIdByName(dirName);
	   Assert.assertNotNull(dirId);
   }
   
   @And("^在表格中再次创建名称为\"(.*)\"的文件夹失败,kw=\"(.*)\"$")
   public void againCreateDir(String dirName, String kw){
	   SaveOrUpdateDir saveOrUpdateDir = new SaveOrUpdateDir();
	   result = saveOrUpdateDir.saveOrUpdateDirAgain(dirName, kw);
	   assertEquals(null,result);
   }
   
   @When("^在表格中文件夹名称为\"(.*)\"重命名为\"(.*)\"$")
   public void updateDirName(String dirName, String updateDirName){
	   SaveOrUpdateDir saveOrUpdateDir = new SaveOrUpdateDir();
	   QueryFileRootDir queryFileRootDir = new QueryFileRootDir();
	   result = queryFileRootDir.queryFileRootDir();
	   JSONObject data = result.getJSONObject("data");
	   BigDecimal parentId = data.getBigDecimal("id");
	   result = saveOrUpdateDir.saveOrUpdateDir(dirName, updateDirName,parentId);
	   assertTrue(result.getBoolean("success"));
	   formDirList.remove(dirName);
	   formDirList.add(updateDirName);
   }
   
   @Then("^在表格中文件夹名称成功重命名为\"(.*)\"$")
   public void checkUpdateDirName(String updateDirName){
	   FormUtil formUtil = new FormUtil();
	   BigDecimal dirId = formUtil.getDirIdByName(updateDirName);
	   Assert.assertNotNull(dirId);
   }
   
   @When("^在表格中删除文件夹\"(.*)\"$")
   public void removeDirByIds(String updateDirName){
	   List dirNameList = new ArrayList();
	   dirNameList.add(updateDirName);
	   RemoveDirByIds  removeDirByIds = new RemoveDirByIds();
	   result = removeDirByIds.removeDirByIds(dirNameList);
	   assertTrue(result.getBoolean("success"));
	   formDirList.remove(updateDirName);
   }
   
   @Then("^在表格中成功删除文件夹\"(.*)\"$")
   public void checkRemoveDirByIds(String updateDirName){
	   FormUtil formUtil = new FormUtil();
	   BigDecimal dirId = formUtil.getDirIdByName(updateDirName);
	   Assert.assertNull(dirId);
   }
   
   /*==============Scenario: DMV_文件_文件夹删除多个文件夹=================*/  
   @When("^在表格中创建多个名称为\"(.*)\"的文件夹$")
   public void saveOrUpdateDirDupli(@Delimiter(",")List<String> dirNameList){
	   SaveOrUpdateDir saveOrUpdateDir = new SaveOrUpdateDir();
	   QueryFileRootDir queryFileRootDir = new QueryFileRootDir();
	   result = queryFileRootDir.queryFileRootDir();
	   JSONObject data = result.getJSONObject("data");
	   BigDecimal parentId = data.getBigDecimal("id");
	   for(int i =0; i< dirNameList.size(); i ++){
		   String dirName = dirNameList.get(i);
		   result = saveOrUpdateDir.saveOrUpdateDir(dirName,parentId);
		   assertTrue(result.getBoolean("success"));
		   formDirList.add(dirName);
	   }
   }
   
   @Then("^在表格中多个文件夹为\"(.*)\"成功被创建$")
   public void checkSaveOrUpdateDirDupli(@Delimiter(",")List<String> dirNameList){
	   FormUtil formUtil = new FormUtil();
	   for(int i =0; i<dirNameList.size(); i++){
		   String dirName = dirNameList.get(i);
	       BigDecimal dirId = formUtil.getDirIdByName(dirName);
	       Assert.assertNotNull(dirId);
	   }
	  
   }
   
   @When("^在表格中删除多个文件夹\"(.*)\"$")
   public void removeDirByIdsDumpli(@Delimiter(",")List<String> dirNameList){
	   RemoveDirByIds removeDirByIds = new RemoveDirByIds();
	   result = removeDirByIds.removeDirByIds(dirNameList);
	   assertTrue(result.getBoolean("success"));
	   for(int i =0; i<dirNameList.size(); i++){
		   String dirName = dirNameList.get(i);
		   formDirList.remove(dirName);
	   }	  
   }
   
   @Then("^在表格中成功删除多个文件夹\"(.*)\"$")
   public void checkRemoveDirByIdsDumpli(@Delimiter(",")List<String> dirNameList){
       FormUtil formUtil = new FormUtil();
       for(int i = 0; i < dirNameList.size(); i ++){
    	   String dirName = dirNameList.get(i);
	       BigDecimal dirId = formUtil.getDirIdByName(dirName);
	       Assert.assertNull(dirId);
       }
   }
   
   /*===============Scenario: DMV_文件_文件夹内创建文件夹===============*/
   @When("^在文件夹\"(.*)\"内创建文件夹\"(.*)\"$")
   public void saveOrUpdateDir2(String srcDirName,String destDirName){
	   FormUtil formUtil = new FormUtil();
	   BigDecimal parentId = formUtil.getDirIdByName(srcDirName);
	   SaveOrUpdateDir  saveOrUpdateDir = new SaveOrUpdateDir();
	   result = saveOrUpdateDir.saveOrUpdateDir(destDirName,parentId);
	   assertTrue(result.getBoolean("success"));
	   formDirList.add(destDirName);
   }
   
   @Then("^在文件夹\"(.*)\"内成功创建文件夹\"(.*)\"$")
   public void checkSaveOrUpdateDir2(String srcDirName,String destDirName){
	   FormUtil formUtil = new FormUtil();
	   BigDecimal parentId = formUtil.getDirIdByName(srcDirName);
	   BigDecimal dirId = formUtil.getDirIdByName(destDirName, parentId);
	   Assert.assertNotNull(dirId);
   }
}
