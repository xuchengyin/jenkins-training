package com.uinnova.test.step_definitions.testcase.base.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.image.AddImage;
import com.uinnova.test.step_definitions.api.base.image.QueryImagePage;
import com.uinnova.test.step_definitions.api.base.image.RemoveImageByIds;
import com.uinnova.test.step_definitions.api.base.image.UpdateImageById;
import com.uinnova.test.step_definitions.api.base.image.UpdateImageOrderNo;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2017-10-30 编写人:sunsl 功能介绍:图标管理的图片添加,更换,删除类
 */
public class Scenario_image {
	String filePath, id;
	String modifyTime;
	JSONObject result;
	JSONObject result2D;
	ImageUtil imageUtil = new ImageUtil();

	@When("^给\"(.*)\"目录添加\"(.*)\"图片$")
	public void addImage(String dirName, String addImage) {
		filePath = Scenario_image.class.getResource("/").getPath() + "testData/image/" + addImage + ".jpg";
		AddImage ai = new AddImage();
		result = ai.addImage(dirName, filePath);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^这个\"(.*)\"目录\"(.*)\"图片添加成功$")
	public void checkAddImage(String dirName,String addImage) {
		List imageList = imageUtil.getDBImageDir(dirName);
		HashMap imageHashMap = (HashMap) imageList.get(0);
		String dirId = imageHashMap.get("ID").toString();
		String sql = "Select ID,MODIFY_TIME from cc_image where DIR_ID = " + dirId + " and IMG_NAME ='" + addImage
				+ "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		HashMap addImageMap = (HashMap) list.get(0);
		modifyTime = addImageMap.get("MODIFY_TIME").toString();
		assertTrue(list.size() > 0);
	}

	@When("^将\"(.*)\"图片替换为\"(.*)\"图片$")
	public void updateImageById(String addImage, String updateImage) {		
		filePath = Scenario_image.class.getResource("/").getPath() + "testData/image/" + updateImage + ".jpg";
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		UpdateImageById ui = new UpdateImageById();
		result = ui.updateImageById(addImage, filePath);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^图片成功更新为\"(.*)\"$")
	public void checkUpdateImageById(String updateImage) {
		String sql = "Select MODIFY_TIME from cc_image where IMG_NAME='" + updateImage + "' and DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);

		HashMap updateImageMap = (HashMap) list.get(0);
		String updateModifyTime = updateImageMap.get("MODIFY_TIME").toString();
		if (!modifyTime.equals(updateModifyTime)) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	@When("^删除名称为\"(.*)\"的图片$")
	public void removeImageByIds(String updateImage) {
		RemoveImageByIds ri = new RemoveImageByIds();
		JSONObject result = ri.removeImageByIds(updateImage);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^系统中不存在名称为\"(.*)\"的图片$")
	public void checkRemoveImageByIds(String updateImage) {
		List list = imageUtil.getDBImage(updateImage);
		assertEquals(list.size(),0);
	}

	@And("^搜索名称包含(.*)的图片$")
	public void searchImage(String searchKey) {
		QueryImagePage qi = new QueryImagePage();
		result = qi.queryImagePage(searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^包含(.*)关键字的的图片全部搜索出来$")
	public void checkSearchImage(String searchKey) {
		int totalRows = result.getJSONObject("data").getInt("totalRows");
		String sql = "Select IMG_NAME from cc_image where IMG_NAME like '%" + searchKey + "%' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		if (list.size() == totalRows) {
			assertTrue(true);
		} else {
			fail();
		}
	}

	@When("^给\"(.*)\"目录添加\"(.*)\"多张图片$")
	public void addImage(String dirName, @Delimiter(",") List<String> addImageList) {
		AddImage ai = new AddImage();
		for (int i = 0; i < addImageList.size(); i++) {
			String addImage = addImageList.get(i);
			filePath = Scenario_image.class.getResource("/").getPath() + "testData/image/" + addImage + ".jpg";
			result = ai.addImage(dirName, filePath);
			assertTrue(result.getBoolean("success"));
		}
	}

	@When("^给这个\"(.*)\"目录 的图片\"(.*)\"移动位置$")
	public void moveImage(String dirName,String moveImage) {
		UpdateImageOrderNo ui = new UpdateImageOrderNo();
		result = ui.updateImageOrderNo(moveImage,dirName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("图片\"(.*)\"位置成功移动")
	public void checkMoveImage(String moveImage){
		List list = imageUtil.getDBImage(moveImage);
		HashMap moveImageMap = (HashMap) list.get(0);
		String oderNo = moveImageMap.get("ORDER_NO").toString();
		if (Integer.parseInt(oderNo)==2){
			assertTrue(true);
		}else{
			fail();
		}
	}

	@When("^删除名称为\"(.*)\"的多张图片$")
	public void removeImageByIds(@Delimiter(",") List<String> deleteImageList) {
		RemoveImageByIds ri = new RemoveImageByIds();
		JSONObject result = ri.removeImageByIds(deleteImageList);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^系统中不存在名称为\"(.*)\"的多张图片$")
	public void checkRemoveImageByIds(@Delimiter(",") List<String> deleteImageList) {
		for (int i = 0; i < deleteImageList.size(); i++) {
			String deleteImage = deleteImageList.get(i);
			List list = imageUtil.getDBImage(deleteImage);
			assertEquals(list.size(),0);
		}
	}

	/*============== Scenario Outline: Image_图片挂载DCV3D图标============*/
	@When("^给图片\"(.*)\"添加\"(.*)\"的\"(.*)\"的3D图标$")
	public void updateImageRlt(String imageName,String dirName,String rltImageName){
		ImageUtil imageUtil = new ImageUtil();
		result = imageUtil.updateImageRlt(imageName, dirName, rltImageName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功给图片\"(.*)\"添加\"(.*)\"的\"(.*)\"的3D图标$")
	public void checkUpdateImageRlt(String imageName,String dirName,String rltImageName){
		String sql = "";
		com.uinnova.test.step_definitions.api.cmv.image.QueryImagePage queryImagePage = new com.uinnova.test.step_definitions.api.cmv.image.QueryImagePage();
		BigDecimal actualRltImageId = new BigDecimal(0);
		List list ;
		HashMap imageHashMap = new HashMap();
		ImageUtil imageUtil = new ImageUtil();
		List arrayList = imageUtil.getDBImageDir(dirName);
		HashMap map = (HashMap)arrayList.get(0);
		result = queryImagePage.queryImagePage((BigDecimal)map.get("ID"));
		if(dirName.equals("Tarsier DCV-3D")){
			sql = "SELECT ID,RLT_IMG_ID_2 FROM cc_image WHERE IMG_NAME='" + imageName + "' AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
			list = JdbcUtil.executeQuery(sql);
			if (list !=null && list.size() > 0){
				imageHashMap = (HashMap)list.get(0);
				actualRltImageId = (BigDecimal)imageHashMap.get("RLT_IMG_ID_2");
			}
		}
		else{
			//result = queryImagePage.queryImagePage(5);
			sql = "SELECT ID,RLT_IMG_ID_1 FROM cc_image WHERE IMG_NAME='" + imageName + "' AND DATA_STATUS = 1 AND DOMAIN_ID =" + QaUtil.domain_id;
			list = JdbcUtil.executeQuery(sql);
			if (list !=null && list.size() > 0){
				imageHashMap = (HashMap)list.get(0);
				actualRltImageId = (BigDecimal)imageHashMap.get("RLT_IMG_ID_1");
			}
		}
		BigDecimal exceptRltImageId = new BigDecimal(0);
		JSONObject data = result.getJSONObject("data");
		JSONArray dataarray = data.getJSONArray("data");
		for(int i = 0; i< dataarray.length(); i ++){
			JSONObject obj = (JSONObject)dataarray.get(i);
			if(obj.getString("imgName").equals(rltImageName)){
				exceptRltImageId = obj.getBigDecimal("id");
				break;
			}
		}

		assertEquals(actualRltImageId,exceptRltImageId);
	}
	
	@When("^搜索名称为\"(.*)\"的3D图标$")
	public void search2DImageOnly(String searchKey) {
		QueryImagePage qi = new QueryImagePage();
		result2D = qi.query2DImagePage(searchKey);
		assertTrue(result2D.getBoolean("success"));
	}
	
	@Then("^返回结果不包含如下目录的图标:$")
	public void checkSearchImageByNames(DataTable imageDirList){
		JSONArray result2DArray = result2D.getJSONObject("data").getJSONArray("data");
		List<List<String>> list = imageDirList.raw();
		for (int i = 1; i < list.size(); i++) {
			String imageDirName = list.get(i).get(0);
			for (int j = 0; j < result2DArray.length(); j++) {
				JSONObject temp = result2DArray.getJSONObject(j);
				String imgFullName = temp.getString("imgFullName");
				assertFalse("判断结果中不包含3D目录下的图标 : " , imgFullName.startsWith(imageDirName));
			}
		}
	}
	
	@Then("^返回结果包含\"(.*)\"$")
	public void checkSearch2DImageByNames(String imageName){
		JSONArray result2DArray = result2D.getJSONObject("data").getJSONArray("data");
		boolean boo = false;
		for (int i = 0; i < result2DArray.length(); i++) {
			JSONObject temp = result2DArray.getJSONObject(i);
			String imgFullName = temp.getString("imgFullName");
			if(imgFullName.equals(imageName)){
				boo = true;
			}
		}
		assertTrue("判断结果中不包含2D图标 : ", boo);
	}

}
