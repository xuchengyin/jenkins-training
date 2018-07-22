package com.uinnova.test.step_definitions.testcase.base.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.image.AddImage;
import com.uinnova.test.step_definitions.api.base.image.ExportDirAndImage;
import com.uinnova.test.step_definitions.api.base.image.ImportDirAndImage;
import com.uinnova.test.step_definitions.api.base.image.ImportImage;
import com.uinnova.test.step_definitions.api.base.image.RemoveImageAndDirByDirId;
import com.uinnova.test.step_definitions.api.base.image.RemoveImageByIds;
import com.uinnova.test.step_definitions.api.base.image.SaveOrUpdateImageDir;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-10-27 
 * 编写人: sunsl 
 * 功能介绍:图标管理的测试用例类
 */
public class Scenario_imageDir {
	JSONObject result;
	String filePath;
	
	SaveOrUpdateImageDir saveOrUpdateImageDir = new SaveOrUpdateImageDir();
	ImageUtil imageUtil = new ImageUtil();
	
	private static List<String> imageDirList = new  ArrayList<String>();

	@After("@CleanImgDir")
	public void CleanImgDir(){
		if (!imageDirList.isEmpty()){
			for (int i=0; i<imageDirList.size(); i++){
				String dirName = imageDirList.get(i);
				RemoveImageAndDirByDirId rd = new RemoveImageAndDirByDirId();
				JSONObject result = rd.removeImageAndDirByDirId(dirName);
				assertTrue(result.getBoolean("success"));
				imageDirList.remove(dirName);
				i--;
			}
		}
	}

	@When("^创建名称为\"(.*)\"的ImageClass目录$")
	public void createImageDir(String dirName) {
		result = saveOrUpdateImageDir.createImageDir(dirName);
		assertTrue(result.getBoolean("success"));
		imageDirList.add(dirName);
	}

	@Then("^系统中存在名称\"(.*)\"的ImageClass目录$")
	public void searchImageDir(String dirName) {
		ArrayList list = imageUtil.getDBImageDir(dirName);
		assertEquals(1, list.size());
	}

	@When("^修改ImageClass目录(.*)的名称为(.*)$")
	public void modifyImageClassDirName(String sourceName, String distName) {
		result = saveOrUpdateImageDir.updateImageDir(distName, sourceName);
		assertTrue(result.getBoolean("success"));
		imageDirList.remove(sourceName);
		imageDirList.add(distName);
	}

	@Then("ImageClass目录名称从(.*)更新为(.*)$")
	public void checkImageModify(String dirName,String dirNameModify) {
		ArrayList list = imageUtil.getDBImageDir(dirNameModify);
		HashMap imageDirMap = (HashMap) list.get(0);
		String distId = imageDirMap.get("ID").toString();
		
		ArrayList listsrc = imageUtil.getDBImageDir(dirName);
		HashMap imagesrcDirMap = (HashMap) list.get(0);
		String dirId = imageDirMap.get("ID").toString();
		assertTrue(dirId.equals(distId));
	}

	@When("删除名称为\"(.*)\"的ImageClass目录$")
	public void deleteDir(String dirName) throws InterruptedException {
		Thread.sleep(3000);
		RemoveImageAndDirByDirId rd = new RemoveImageAndDirByDirId();
		JSONObject result = rd.removeImageAndDirByDirId(dirName);
		assertTrue(result.getBoolean("success"));
		imageDirList.remove(dirName);
	}

	@Then("系统中不存在名称为\"(.*)\"的ImageClass目录$")
	public void notExistDir(String dirName) {
		ArrayList list = imageUtil.getDBImageDir(dirName);
		assertTrue(list.size() == 0);
	}

	/* ==================Scenario: ImageClass_导入、导出================ */
	@When("^图标管理导入(.*)图片$")
	public void importImageClass(String imageDirName) {
		ArrayList list = imageUtil.getDBImageDir(imageDirName);//查询导入的图片目录是否存在， 不存在的话执行完用例要删除
		filePath = Scenario_imageDir.class.getResource("/").getPath() + "testData/image/" + imageDirName + ".zip";
		ImportDirAndImage im = new ImportDirAndImage();
		result = im.importDirAndImage(imageDirName, filePath);
		assertTrue(result.getBoolean("success"));
		if (list.size() ==0)//不存在的话执行完用例要删除
			imageDirList.add(imageDirName);
	}

	@Then("^(.*)图片导入成功$")
	public void checkImageClass(String dirName) throws InterruptedException {
		Thread.sleep(1000);
		List zipFileNameList = imageUtil.readzipFile(filePath,0);
		ArrayList list = imageUtil.getDBImageDir(dirName);
		HashMap imageHashMap = (HashMap) list.get(0);
		String dirId = imageHashMap.get("ID").toString();

		String sql = "select IMG_NAME from cc_image where DIR_ID = " + dirId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList imageList = JdbcUtil.executeQuery(sql);
		String tempZipFileName = "";
		for(int j = 0; j < zipFileNameList.size(); j ++ ){
			String zipFileName = (String) zipFileNameList.get(j);
			tempZipFileName = tempZipFileName + zipFileName;			
		}
		if (zipFileNameList.size() == imageList.size()) {
			for (int i = 0; i < imageList.size(); i++) {
				HashMap imageNameHashMap = (HashMap) imageList.get(i);
				String imageFileName = imageNameHashMap.get("IMG_NAME").toString();
				assertTrue(tempZipFileName.contains(imageFileName));
			}
		}
	}
	
	@When("^图标管理导入图片文件\"(.*)\"$")
	public void importImage(String imageDirName) throws InterruptedException {
		ArrayList list = imageUtil.getDBImageDir(imageDirName);//查询导入的图片目录是否存在， 不存在的话执行完用例要删除
		filePath = Scenario_imageDir.class.getResource("/").getPath() + "testData/image/" + imageDirName + ".zip";
		//Thread.sleep(1000);
		ImportImage im = new ImportImage();
		result = im.importImage(filePath);
		assertTrue(result.getBoolean("success"));
		//不存在的话执行完用例要删除
		if (list.size() ==0){
			imageDirList.add(imageDirName);
		}
	}



	@When("^导出(.*)图片$")
	public void exportImageClass(String dirName) {
		ExportDirAndImage exportDirAndImage = new ExportDirAndImage();
		filePath = exportDirAndImage.exportDirAndImage(dirName);
		assertTrue((new File(filePath)).exists());
	}

	@Then("^(.*)图片导出成功$")
	public void checkExportImageClass(String dirName) {
		List zipFileNameList = imageUtil.readzipFile(filePath,1);
		ArrayList list = imageUtil.getDBImageDir(dirName);
		HashMap imageHashMap = (HashMap) list.get(0);
		String dirId = imageHashMap.get("ID").toString();
		String sql = "select IMG_NAME from cc_image where DIR_ID = '" + dirId + "' and DOMAIN_ID = "+ QaUtil.domain_id+" order by CREATE_TIME";
		ArrayList imageList = JdbcUtil.executeQuery(sql);
		// 比较导出文件的名字和数据库文件名字
		if (imageList.size() == zipFileNameList.size()) {
			for (int i = 0; i < imageList.size(); i++) {
				HashMap imageNameHashMap = (HashMap) imageList.get(i);
				String imgName = imageNameHashMap.get("IMG_NAME").toString();
				String zipFileName = (String) zipFileNameList.get(i);
				assertTrue(imgName.equals(zipFileName));
			}
		}

	}
	/* ==================Scenario: ImageClass_重名检查================ */
	
	@When("^再次创建名称为\"(.*)\"的ImageClass目录，创建失败，kw=\"(.*)\"$")
	public void repeatCreateImageDir(String dirName,String kw) {
		result = saveOrUpdateImageDir.createImageDirAgain(dirName, kw);
		assertEquals(null, result);
	}
	
    /* ================Scenario: ImageClass_不同目录下添加相同的图标=========*/
	@When("^创建多个ImageClass目录:$")
	public void createImageDirDupli(DataTable dt){
		List <List<String>>  dirList = dt.raw();
		for(int i = 1 ; i < dirList.size(); i ++){
			result = saveOrUpdateImageDir.createImageDir(dirList.get(i).get(0));
			assertTrue(result.getBoolean("success"));
			imageDirList.add(dirList.get(i).get(0));
		}
	}
	
	@Then("^成功创建多个ImageClass目录:$")
	public void checkCreateImageDirDupli(DataTable dt){
		List <List<String>> dirList = dt.raw();
		for(int i = 1; i < dirList.size(); i++){
		    ArrayList list = imageUtil.getDBImageDir(dirList.get(i).get(0));
		    assertEquals(1, list.size());
		}
	}
	
	@When("^给多个目录添加\"(.*)\"图片:$")
	public void addImageDupli(String addImage,DataTable dt){
	    filePath = Scenario_image.class.getResource("/").getPath() + "testData/image/" + addImage + ".jpg";
		AddImage ai = new AddImage();
		List <List<String>> dirList = dt.raw();
		for(int i = 1; i < dirList.size(); i ++){
		   result = ai.addImage(dirList.get(i).get(0), filePath);
		   assertTrue(result.getBoolean("success"));
		}
	}
	
    @Then("^给多个目录添加\"(.*)\"图片成功:$")
    public void checkAddImageDupli(String addImage,DataTable dt){
    	List<List<String>> dirList = dt.raw();
    	for(int i = 1; i < dirList.size(); i ++){
    		List imageList = imageUtil.getDBImageDir(dirList.get(i).get(0));
    		HashMap imageHashMap = (HashMap) imageList.get(0);
    		String dirId = imageHashMap.get("ID").toString();
    		String sql = "Select ID,MODIFY_TIME from cc_image where DIR_ID = " + dirId + " and IMG_NAME ='" + addImage
    				+ "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
    		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
    		assertTrue(list.size() > 0);
    	}
    }
    
    @When("^删除多个ImageClass目录：$")
    public void deleteDirDupli(DataTable dt){
    	RemoveImageAndDirByDirId rd = new RemoveImageAndDirByDirId();
    	List<List<String>> dirList = dt.raw();
    	for(int i = 1; i < dirList.size();i++){
    	   JSONObject result = rd.removeImageAndDirByDirId(dirList.get(i).get(0));
    	   assertTrue(result.getBoolean("success"));
    	   imageDirList.remove(dirList.get(i).get(0));
    	}
    }
    
    @Then("^系统中不存在多个ImageClass目录:$")
    public void checkDeleteDirDupli(DataTable dt){
    	List<List<String>> dirList = dt.raw();
    	for(int i = 1; i < dirList.size(); i++){
    	   ArrayList list = imageUtil.getDBImageDir(dirList.get(i).get(0));
		   assertTrue(list.size() == 0);
    	}
    }
    
    @When("^给多个目录删除名称为\"(.*)\"的图片:$")
    public void removeImageByIdsDupli(String addImage,DataTable dt){
    	List<List<String>> dirList = dt.raw();
    	RemoveImageByIds ri = new RemoveImageByIds();
    	for(int i = 1; i < dirList.size(); i ++){
    		JSONObject result = ri.removeImageByIds(addImage,dirList.get(i).get(0));
    		assertTrue(result.getBoolean("success"));
    	}
    }
    
    @Then("^多个目录系统中不存在名称为\"(.*)\"的图片:$")
    public void checkRemoveImageByIdsDupli(String addImage,DataTable dt){
    	List<List<String>> dirList = dt.raw();
    	for(int i = 1; i< dirList.size(); i++){
    	   ArrayList list = imageUtil.getDBImageIdByDirName(addImage,dirList.get(i).get(0));
  		   assertTrue(list.size() == 0);
    	}
    }
	   
}
