package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.image.QueryCommonImageInfoPage;
import com.uinnova.test.step_definitions.api.dmv.image.QueryCustomImagePage;
import com.uinnova.test.step_definitions.api.dmv.image.QueryImageDirList;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 查询全部图标目录， 全部常用图标，全部自定义图标单接口
 *
 */
public class Scenario_image_QueryImg {
	private JSONObject customImgInfo;  //自定义图标信息
	private JSONObject commonImgInfo;//常用图标信息
	private JSONArray imageDirInfo;//图标目录列表


	@When("^查询图标目录$")
	public void queryImageDirList(){
		QueryImageDirList queryImageDirList = new QueryImageDirList();
		JSONObject result = queryImageDirList.queryImageDirList();
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		imageDirInfo = result.getJSONArray("data");
		assertTrue(imageDirInfo.length()>0);
	}

	@Then("^成功查询图标目录$")
	public void checkQueryImageDirInfoList(){
		String sql = "select ID, DIR_NAME, DIR_TYPE, PARENT_ID, DIR_LVL, DIR_PATH, ORDER_NO, IS_LEAF, ICON, DIR_DESC, DIR_COLOR, CUSTOM_1, CUSTOM_2, CUSTOM_3, CUSTOM_4, CUSTOM_5, CUSTOM_6, DOMAIN_ID, DATA_STATUS, CREATOR, MODIFIER, CREATE_TIME, MODIFY_TIME "
				+" from CC_GENERAL_DIR where  DIR_TYPE = 1 and DOMAIN_ID = " +QaUtil.domain_id+ "  and DATA_STATUS = 1 "
				+" and DIR_NAME not like '%DCV%' and DIR_NAME not like '%3D%' "
				+" order by ID ";
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(imageDirInfo.length(), list.size());
		Map map = new HashMap();
		for (int i=0; i<list.size(); i++ ){
			HashMap temp = (HashMap) list.get(i);
			map.put(String.valueOf(temp.get("ID")), temp);
		}

		for (int j=0; j<imageDirInfo.length(); j++){
			JSONObject tempData = imageDirInfo.getJSONObject(j);
			JSONObject tempDir = tempData.getJSONObject("dir");
			String dirId= String.valueOf(tempDir.getBigDecimal("id"));
			assertTrue(map.containsKey(dirId));
			HashMap tempMap = (HashMap) map.get(dirId);

			String imageSql ="select count(*) as VAL FROM cc_image where dir_ID = "+dirId+" and DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList imageList = (ArrayList) JdbcUtil.executeQuery(imageSql);
			HashMap imageCountMap = (HashMap) imageList.get(0);
			assertEquals(tempData.getBigDecimal("imageCount"),new BigDecimal(imageCountMap.get("VAL").toString()));
			assertEquals(tempDir.getBigDecimal("dirType"),tempMap.get("DIR_TYPE"));
			assertEquals(tempDir.getBigDecimal("dirLvl"),tempMap.get("DIR_LVL"));
			assertEquals(tempDir.getBigDecimal("parentId"),tempMap.get("PARENT_ID"));
			assertEquals(tempDir.getBigDecimal("isLeaf"),tempMap.get("IS_LEAF"));

			assertEquals(tempDir.getString("dirPath"),tempMap.get("DIR_PATH"));
			assertEquals(tempDir.getString("dirName"),tempMap.get("DIR_NAME"));

			assertEquals(tempDir.getString("creator"),tempMap.get("CREATOR"));
			assertEquals(tempDir.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
			assertEquals(tempDir.getString("modifier"),tempMap.get("MODIFIER"));
			assertEquals(tempDir.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
		}
	}

	@When("^查询常用图标$")
	public void queryCommonImageInfoPage(){
		QueryCommonImageInfoPage queryCommonImageInfoPage = new QueryCommonImageInfoPage();
		JSONObject result = queryCommonImageInfoPage.queryCommonImageInfoPage("",1,1,1000);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		commonImgInfo = result.getJSONObject("data");
		assertNotNull(commonImgInfo);
	}

	@Then("^常用图标查询成功$")
	public void checkQueryCommonImageInfoPage(){
		String sql = "select ID, IMG_NAME, IMG_FULL_NAME, IMG_GROUP, DIR_ID, IMG_DESC, IMG_TYPE, IMG_THEME, IMG_PATH, IMG_RANGE, IMG_SIZE, UPOR_ID,UPOR_NAME, UP_TIME, RLT_IMG_ID_1, RLT_IMG_ID_2, RLT_IMG_ID_3, CUSTOM_1,    CUSTOM_2, CUSTOM_3, CUSTOM_4, CUSTOM_5, CUSTOM_6, SEARCH_FIELD,    ORDER_NO, DOMAIN_ID, CREATOR, MODIFIER, DATA_STATUS, CREATE_TIME,    MODIFY_TIME     from CC_IMAGE "
				+" where  IMG_GROUP in (1)  "
				+" and   DIR_ID = (select ID from CC_GENERAL_DIR where DIR_NAME like 'Common Icons'    and    DOMAIN_ID = " +QaUtil.domain_id+ " and    DATA_STATUS = 1)"//  order by   ID )     "
				+" and    DOMAIN_ID = " +QaUtil.domain_id+ "    and    DATA_STATUS = 1 order by DIR_ID,ORDER_NO,IMG_NAME "; //limit 0,1000 ";   此语句oralce数据库提示：java.sql.SQLException: ORA-00907: 缺失右括号 待修正

		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(commonImgInfo.getInt("totalRows"), list.size());
		JSONArray dataArr = commonImgInfo.getJSONArray("data");
		assertEquals(dataArr.length(), list.size());
		for (int i=0; i<dataArr.length(); i++){
			JSONObject imgObj = dataArr.getJSONObject(i).getJSONObject("image");
			HashMap tempMap = (HashMap) list.get(i);

			assertEquals(imgObj.getBigDecimal("id"),tempMap.get("ID"));
			assertEquals(imgObj.getBigDecimal("imgSize"),tempMap.get("IMG_SIZE"));
			assertEquals(imgObj.getBigDecimal("uporId"),tempMap.get("UPOR_ID"));
			assertEquals(imgObj.getBigDecimal("dirId"),tempMap.get("DIR_ID"));
			assertEquals(imgObj.getBigDecimal("imgGroup"),tempMap.get("IMG_GROUP"));
			assertEquals(imgObj.getBigDecimal("orderNo"),tempMap.get("ORDER_NO"));
			assertEquals(imgObj.getBigDecimal("domainId"),tempMap.get("DOMAIN_ID"));
			assertEquals(imgObj.getBigDecimal("dataStatus"),tempMap.get("DATA_STATUS"));
			assertTrue(imgObj.getString("imgPath").contains((CharSequence) tempMap.get("IMG_PATH")));
			assertEquals(imgObj.getString("imgName"),tempMap.get("IMG_NAME"));
			assertEquals(imgObj.getString("imgFullName"),tempMap.get("IMG_FULL_NAME"));
			assertEquals(imgObj.getString("searchField"),tempMap.get("SEARCH_FIELD"));
			assertEquals(imgObj.getString("uporName"),tempMap.get("UPOR_NAME"));
			assertEquals(imgObj.getBigDecimal("upTime"),tempMap.get("UP_TIME"));
			assertEquals(imgObj.getString("creator"),tempMap.get("CREATOR"));
			assertEquals(imgObj.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
			assertEquals(imgObj.getString("modifier"),tempMap.get("MODIFIER"));
			assertEquals(imgObj.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
		}
	}

	@When("^查询自定义图标$")
	public void queryCustomImagePage(){
		QueryCustomImagePage queryCustomImagePage = new QueryCustomImagePage();
		JSONObject result = queryCustomImagePage.queryCustomImagePage(1,10000);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		customImgInfo = result.getJSONObject("data");
		assertNotNull(customImgInfo);
	}

	@Then("^自定义图标查询成功$")
	public void checkQueryCustomImagePage(){
//		String sql = "select ID, IMG_NAME, IMG_FULL_NAME, IMG_GROUP, DIR_ID, IMG_DESC, IMG_TYPE, IMG_THEME, IMG_PATH, IMG_RANGE, IMG_SIZE, UPOR_ID,UPOR_NAME, UP_TIME, RLT_IMG_ID_1, RLT_IMG_ID_2, RLT_IMG_ID_3, CUSTOM_1,    CUSTOM_2, CUSTOM_3, CUSTOM_4, CUSTOM_5, CUSTOM_6, SEARCH_FIELD,    ORDER_NO, DOMAIN_ID, CREATOR, MODIFIER, DATA_STATUS, CREATE_TIME,    MODIFY_TIME     from CC_IMAGE "
//				+" where  IMG_GROUP in (1)  "
//				+" and   DIR_ID = (select ID from CC_GENERAL_DIR where DIR_NAME like '自定义形状'   and   DOMAIN_ID = " +QaUtil.domain_id+ " and    DATA_STATUS = 1  order by   ID )     "
//				+" and   DOMAIN_ID = " +QaUtil.domain_id+ " and  DATA_STATUS = 1 order by DIR_ID,ORDER_NO,IMG_NAME limit 0,1000 ";
		String sql = "select ID, IMG_NAME, IMG_FULL_NAME, IMG_GROUP, DIR_ID, IMG_DESC, IMG_TYPE, IMG_THEME, IMG_PATH, IMG_RANGE, IMG_SIZE, UPOR_ID,UPOR_NAME, UP_TIME, RLT_IMG_ID_1, RLT_IMG_ID_2, RLT_IMG_ID_3, CUSTOM_1,    CUSTOM_2, CUSTOM_3, CUSTOM_4, CUSTOM_5, CUSTOM_6, SEARCH_FIELD,    ORDER_NO, DOMAIN_ID, CREATOR, MODIFIER, DATA_STATUS, CREATE_TIME,    MODIFY_TIME     from CC_IMAGE "
		+" where  IMG_GROUP in (1)  "
		+" and   DIR_ID = (select ID from CC_GENERAL_DIR where DIR_NAME like '自定义形状'   and   DOMAIN_ID = " +QaUtil.domain_id+ " and    DATA_STATUS = 1)     "
		+" and   DOMAIN_ID = " +QaUtil.domain_id+ " and  DATA_STATUS = 1 order by DIR_ID,ORDER_NO,IMG_NAME ";

		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(customImgInfo.getInt("totalRows"), list.size());
		Map dbMap =new  HashMap<>();
		for(int l=0; l<list.size(); l++){
			HashMap tempMap = (HashMap) list.get(l);
			dbMap.put(tempMap.get("ID"), tempMap);
		}
		
		JSONArray dataArr = customImgInfo.getJSONArray("data");
		assertEquals(dataArr.length(), list.size());
		for (int i=0; i<dataArr.length(); i++){
			JSONObject imgObj = dataArr.getJSONObject(i).getJSONObject("image");
			HashMap tempMap = (HashMap) dbMap.get(imgObj.getBigDecimal("id"));//(HashMap) list.get(i);

			assertEquals(imgObj.getBigDecimal("id"),tempMap.get("ID"));
			assertEquals(imgObj.getBigDecimal("imgSize"),tempMap.get("IMG_SIZE"));
			assertEquals(imgObj.getBigDecimal("uporId"),tempMap.get("UPOR_ID"));
			assertEquals(imgObj.getBigDecimal("dirId"),tempMap.get("DIR_ID"));
			assertEquals(imgObj.getBigDecimal("imgGroup"),tempMap.get("IMG_GROUP"));
	//		assertEquals(imgObj.getBigDecimal("orderNo"),tempMap.get("ORDER_NO"));
			assertEquals(imgObj.getBigDecimal("domainId"),tempMap.get("DOMAIN_ID"));
			assertEquals(imgObj.getBigDecimal("dataStatus"),tempMap.get("DATA_STATUS"));
			assertTrue(imgObj.getString("imgPath").contains((CharSequence) tempMap.get("IMG_PATH")));
			assertEquals(imgObj.getString("imgName"),tempMap.get("IMG_NAME"));
		//	assertEquals(imgObj.getString("imgFullName"),tempMap.get("IMG_FULL_NAME"));
		//	assertEquals(imgObj.getString("searchField"),tempMap.get("SEARCH_FIELD"));
			assertEquals(imgObj.getString("uporName"),tempMap.get("UPOR_NAME"));
			assertEquals(imgObj.getBigDecimal("upTime"),tempMap.get("UP_TIME"));
			assertEquals(imgObj.getString("creator"),tempMap.get("CREATOR"));
			assertEquals(imgObj.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
			assertEquals(imgObj.getString("modifier"),tempMap.get("MODIFIER"));
			assertEquals(imgObj.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
		}
	}

}
