package com.uinnova.test.step_definitions.utils.base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.uinnova.test.step_definitions.api.base.image.QueryImagePage;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryDefaultImage;
import com.uinnova.test.step_definitions.api.base.image.QueryImagePage;
import com.uinnova.test.step_definitions.api.cmv.image.UpdateImageRlt;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 图标工具类
 * @author uinnova
 *
 */
public class ImageUtil {

	/**
	 * @param imageName
	 * @return
	 */
	public String getImageUrl(String imageName) {
		String imageUrl = null;
		if (imageName.equals("Default")) {
			QueryDefaultImage qi = new QueryDefaultImage();
			JSONObject result = qi.defaultImageUrl();
			JSONObject data = result.getJSONObject("data");
			imageUrl = data.getString("imgPath");
		} else {
			QueryImagePage qp = new QueryImagePage();
			JSONObject result = qp.queryImagePage(imageName);
			JSONArray data = result.getJSONObject("data").getJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = (JSONObject) data.get(i);
				String name = obj.getString("imgName");
				if (name.equals(imageName)) {
					imageUrl = obj.getString("imgPath");
					break;
				}
			}
		}
		return imageUrl;
	}

	/**
	 * 编写时间:2017-10-27
	 * 编写人: sunsl
	 */
	public ArrayList getDBImageDir(String dirName){
		String sql = "SELECT ID FROM cc_general_dir c where c.DIR_NAME = '" + dirName 
				+ "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList arraylist = JdbcUtil.executeQuery(sql);
		return arraylist;
	}
	/**
	 * 编写时间:2017-10-29
	 * 编写人:sunsl
	 * 功能介绍:读取zip文件
	 */
	public List readzipFile(String filePath,int i){
		String fileName = "";
		List fileNameList = new ArrayList();
		try {
			// 读取zip文件
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry ze;

			while ((ze = zin.getNextEntry()) != null) {
				if (ze.isDirectory()) {

				} else {
					if (i != 0){
						fileName = ze.getName().split("/")[1];
					}else{
						fileName = ze.getName();
					}			
					fileName = fileName.substring(0, fileName.indexOf("."));
					fileNameList.add(fileName);
				}
			}

		} catch (IOException ioe) {
			ioe.getMessage();
		}
		return fileNameList;
	}

	/**
	 * 编写时间:2017-10-30
	 * 编写人: sunsl
	 */
	public ArrayList getDBImage(String imageName){		
		String sql = "Select ID,ORDER_NO from cc_image where IMG_NAME ='" + imageName + "' and DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList arraylist = (ArrayList)JdbcUtil.executeQuery(sql);
		return arraylist;
	}

	/**
	 * 编写时间:2017-10-30
	 * 编写人: sunsl
	 */
	public ArrayList getDBImageIdByDirName(String imageName,String dirName){	
		List dirList = getDBImageDir(dirName);
		BigDecimal dirId = new BigDecimal(0);
		if(dirList.size()>0){
			HashMap map = (HashMap)dirList.get(0);
			dirId = (BigDecimal)map.get("ID");
		}
		String sql = "Select ID,ORDER_NO from cc_image where IMG_NAME ='" + imageName + "' AND DIR_ID = " + dirId + " and DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList arraylist = (ArrayList)JdbcUtil.executeQuery(sql);
		return arraylist;
	}
	/**
	 * @param imageName
	 * @param flag
	 * @param rltImageName
	 * @return
	 */
	public JSONObject updateImageRlt(String imageName,String dirName,String rltImageName){
		JSONObject result = new JSONObject();
		List list = getDBImage(imageName);
		BigDecimal id = new BigDecimal(0);
		BigDecimal rltImgId = new BigDecimal(0);
		if(list != null && list.size()>0){
			HashMap imageHashMap = (HashMap)list.get(0);
			id = (BigDecimal)imageHashMap.get("ID");
		}

		com.uinnova.test.step_definitions.api.cmv.image.QueryImagePage queryImagePage = new com.uinnova.test.step_definitions.api.cmv.image.QueryImagePage();
		List arrayList = getDBImageDir(dirName);
		HashMap map = (HashMap)arrayList.get(0);
		result = queryImagePage.queryImagePage((BigDecimal)map.get("ID"));
		JSONObject data = result.getJSONObject("data");
		JSONArray dataarray = data.getJSONArray("data");
		for(int i = 0; i < dataarray.length(); i ++){
			JSONObject obj = (JSONObject)dataarray.get(i);
			if(obj.getString("imgName").equals(rltImageName)){
				rltImgId = obj.getBigDecimal("id");
				break;
			}
		}
		UpdateImageRlt updateImageRlt = new UpdateImageRlt();
		result = updateImageRlt.updateImageRlt(rltImgId, id, dirName);
		return result;
	}
}
