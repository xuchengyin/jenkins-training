package com.uinnova.test.step_definitions.testcase.base.ciExcelBatchImport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.base.ci.ExportImportMsg;
import com.uinnova.test.step_definitions.api.base.ci.QueryPage;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryAll;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryDefaultImage;
import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.base.ciExcelBatchImport.AsyncImportCiBySheetName;
import com.uinnova.test.step_definitions.api.base.ciExcelBatchImport.ImportCiBySheetNamesWithFieldMap;
import com.uinnova.test.step_definitions.api.base.ciExcelBatchImport.ImportExcel;
import com.uinnova.test.step_definitions.api.base.ciExcelBatchImport.ImportExcel2;
import com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport.GetSheetDataBySheetName;
import com.uinnova.test.step_definitions.api.base.progress.GetProgressInfoByKey;
import com.uinnova.test.step_definitions.utils.base.CiClassDirUtil;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_ciExcelBatchImport {

	JSONObject fileNames ;
	JSONObject result = null;
	JSONObject data = null;

	private static List<String> clClsList = new  ArrayList<String>();

	/**
	 * 删除导入时新建的分类
	 */
	@After("@CleanImportCiCls")
	public void cleanImportCiCls(){
		if (!clClsList.isEmpty()){
			for (int i=0; i<clClsList.size(); i++){
				String clsName = clClsList.get(i);
				BigDecimal clsId =(new CiClassUtil()).getCiClassId(clsName);
				if(clsId.compareTo(new BigDecimal(0))>0){
					Boolean result = (new CiClassUtil()).deleteCiClassAndCi(clsName);
					assertTrue(result);
				}
				clClsList.remove(clsName);
				i--;
			}
		}
	}

	@When("^上传分类导入所需excel表:\"(.*)\"$")
	public void importExcel(@Delimiter(",") List<String> excelNames) {
		int flag = 0;
		fileNames = new JSONObject();
		for(int i=0;i<excelNames.size();i++) {
			result = (new ImportExcel()).importExcel(excelNames.get(i));
			if(result.getBoolean("success")) {
				//将excel表名与 服务器存储文件名进行对应存储
				String fileName  = ((JSONObject)result.getJSONArray("data").getJSONObject(0)).getString("fileName");
				String name = excelNames.get(i).substring(0, excelNames.get(i).length()-4);
				fileNames.put(name, fileName);
				flag++;
			}

		}
		assertTrue(flag==excelNames.size());
	}

	@Then("^成功获取如下excel表中各sheet分类数据:$")
	public void getSheetData(DataTable table) {
		int flag = 0;
		List<Map<String,String>> mapList = table.asMaps(String.class, String.class);
		for(Map<String,String> map: mapList) {
			String excelName = map.get("excelName");
			String sheetName = map.get("sheetName");
			String fileNameServer = fileNames.getString(excelName);
			Boolean result = (new GetSheetDataBySheetName()).getSheetDataBySheetName(fileNameServer, sheetName).getBoolean("success");
			if(result) {
				flag++;
			}
		}
		assertEquals(mapList.size(), flag);
	}

	@And("^按照如下设置进行分类映射,并全量上传:")
	public void sheetNameFieldMap(DataTable mapTable) {
		result = (new ImportCiBySheetNamesWithFieldMap()).importCiBySheetNamesWithFieldMap(mapTable, fileNames);
		assertTrue(result.getBoolean("success"));

	}

	@Then("获取\"分类数据\"上传报告")
	public void getImportMsg() {
		Date date = new Date();
		String fileName =  result.getString("data");
		String  result = (new ExportImportMsg()).exportImportMsg(fileName);

	}

	@And("^如下分类数据通过\"一键导入\"全量上传成功:$")
	public void checkImportData(DataTable table) throws FileNotFoundException, IOException {
		//判断上传的分类数据与excel表数据相同
		int flag = 0;
		List<Map<String,String>> tableMap = table.asMaps(String.class, String.class);
		for(int i=0;i<tableMap.size();i++) {
			String excelName = tableMap.get(i).get("excelName");
			String sheetName = tableMap.get(i).get("sheetName");
			String clsName = tableMap.get(i).get("clsName");

			String dirUrl = Scenario_ciExcelBatchImport.class.getResource("/").getPath()+"/testData/ciExcelBatchImport/";
			Workbook wb = new HSSFWorkbook(new FileInputStream(dirUrl+excelName+".xls"));
			int excelrows = wb.getSheet(sheetName).getLastRowNum();
			int totalRows = (new QueryPage()).queryPage(clsName).getJSONObject("data").getInt("totalRows");
			if(excelrows==totalRows) {
				flag++;
			}
		}
		assertEquals(flag, tableMap.size());
	}

	@When("^删除如下分类及数据:\"(.*)\"$")
	public void deleteMoreCis(@Delimiter(",") List<String> ciNames) {
		int flag = 0;
		for(int i=0; i<ciNames.size();i++) {
			Boolean result = (new CiClassUtil()).deleteCiClassAndCi(ciNames.get(i));
			flag++;
		}
		assertTrue(flag==ciNames.size());
	}

	@Then("^如下分类数据删除成功:\"(.*)\"$")
	public void checkDelMoreCis(@Delimiter(",") List<String> ciNames) {
		int flag = 0;
		for(int i=0; i<ciNames.size();i++) {
			String sql = "SELECT * FROM cc_ci_class c where c.CLASS_CODE='" + ciNames.get(i)+ "' and c.DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
			//				String sql = "SELECT * FROM cc_ci_class c where c.CLASS_CODE like'" + startChar + "%" + endChar
			//						+ "' and c.DATA_STATUS=1";
			ArrayList list = JdbcUtil.executeQuery(sql);
			if(list.size() == 0) {
				flag++;
			}

		}
		assertTrue(flag==ciNames.size());
	}

	@And("^按照如下设置进行分类映射,并增量上传:$")
	public void checkUpdateData(DataTable table)  {
		//获取分类原数据
		data =  new JSONObject();
		List<Map<String,String>> map = table.asMaps(String.class, String.class);
		for(int i=0;i<map.size();i++) {
			String clsName = map.get(i).get("clsName");
			int totalRows = (new QueryPage()).queryPage(clsName).getJSONObject("data").getInt("totalRows");
			data.put(clsName, totalRows);
		}

		//		 int flag = 0;

		JSONObject result = (new ImportCiBySheetNamesWithFieldMap()).importCiBySheetNamesWithFieldMap(table, fileNames);
		//		 if(result.getBoolean("success")) {
		//			 for(int i=0;i<map.size();i++) {
		//				 String clsName = map.get(i).get("clsName");
		//				 String excelName = map.get(i).get("excelName");
		//				 String sheetName = map.get(i).get("sheetName");
		//				 
		//				 String fileName = Scenario_ciExcelBatchImport.class.getResource("/").getPath()+"/testData/ciExcelBatchImport/"+excelName+".xls";
		//				 Workbook wb = new HSSFWorkbook(new FileInputStream(fileName));
		//				 int excelRows = wb.getSheet(sheetName).getLastRowNum();
		//				 int totalRows = (new QueryPage()).queryPage(clsName).getJSONObject("data").getInt("totalRows");
		//				 int beforeRows = data.getInt(clsName);
		//				 if(excelRows==(totalRows-beforeRows)) {
		//					 flag++;
		//				 }
		//			 }
		////			 assertTrue(flag==map.size());
		//			 
		//		 }else {
		//			 fail();
		//		 }
		assertTrue(result.getBoolean("success"));
		//获取分类更新后数据
	}

	@Then("^如下分类数据通过\"一键导入\"增量上传成功:$")
	public void verifyAddUpdate(DataTable table) throws FileNotFoundException, IOException {
		List<Map<String,String>> map = table.asMaps(String.class, String.class);
		int flag = 0;
		for(int i=0;i<map.size();i++) {
			String clsName = map.get(i).get("clsName");
			String excelName = map.get(i).get("excelName");
			String sheetName = map.get(i).get("sheetName");

			String fileName = Scenario_ciExcelBatchImport.class.getResource("/").getPath()+"/testData/ciExcelBatchImport/"+excelName+".xls";
			Workbook wb = new HSSFWorkbook(new FileInputStream(fileName));
			int excelRows = wb.getSheet(sheetName).getLastRowNum();
			int totalRows = (new QueryPage()).queryPage(clsName).getJSONObject("data").getInt("totalRows");
			int beforeRows = data.getInt(clsName);
			if(excelRows==(totalRows-beforeRows)) {
				flag++;
			}
		}

	}


	/*******************3.2.0迭代改版*****************************/
	@When("^导入excel文档\"(.*)\"$")
	public void importExcel(String excelFileName){
		//上传文档
		ImportExcel2 importExcel = new ImportExcel2();
		JSONObject importResult = importExcel.importExcel2(excelFileName);
		assertEquals(new Boolean(true), importResult.get("success"));
		assertTrue(importResult.has("data"));
		JSONArray importExcelData = importResult.getJSONArray("data");
		assertNotNull(importExcelData);
		//创建分类
		QueryDefaultImage qDefaultImage = new QueryDefaultImage();
		JSONObject defaultImgResult = qDefaultImage.defaultImageUrl();
		JSONObject defaultImgData = defaultImgResult.getJSONObject("data");
		String defaultImageUrl = defaultImgData.getString("imgPath");

		for (int i=0; i<importExcelData.length(); i++){
			JSONObject excelObj = importExcelData.getJSONObject(i);
			String fileName = excelObj.getString("fileName");
			JSONArray sheetsArray = excelObj.getJSONArray("sheetTitles");
			for (int j=0; j<sheetsArray.length(); j++){
				JSONObject sheetObj = sheetsArray.getJSONObject(j);
				String sheetName  = sheetObj.getString("sheetName");
				JSONArray titles  = sheetObj.getJSONArray("titles");
				if(titles==null || titles.length()==0)//空excel表
					break;

				if ((new CiClassUtil()).getCiClassId(sheetName).compareTo(new BigDecimal(0))==0){//不存在分类则新建
					JSONObject ciClass = new JSONObject();
					JSONArray attrDefs = new JSONArray();
					JSONObject fixMapping = new JSONObject();
					ciClass.put("icon", defaultImageUrl);
					ciClass.put("classColor", "rgb(85, 168, 253)");
					ciClass.put("dirId", (new CiClassDirUtil()).getDirId(Contants.DEFAULT_CI_CLASS_DIR_NAME));
					ciClass.put("classCode", sheetName);
					ciClass.put("className", sheetName);
					ciClass.put("ciType", 1);
					ciClass.put("parentId", 0);
					ciClass.put("DISP_FIELD_IDS", "");
					ciClass.put("DISP_FIELD_NAMES", "");
					ciClass.put("classDesc", "");
					JSONObject attrDef = new JSONObject();
					attrDef.put("proName", titles.get(0));
					attrDef.put("orderNo", 1);
					attrDef.put("isCiDisp", 0);
					attrDef.put("isMajor", 1);
					attrDef.put("isRequired", 1);
					attrDef.put("proType", 3);
					attrDef.put("ciType", 1);
					attrDefs.put(attrDef);
					fixMapping.put("nmCiCode", titles.get(0));
					SaveOrUpdate saveOrUpdate = new SaveOrUpdate();
					saveOrUpdate.saveOrUpdate(attrDefs, ciClass, fixMapping);
					clClsList.add(sheetName);
				}

				//导入数据
				JSONObject ciClassInfo = new JSONObject();
				JSONArray newAttrDefs = new JSONArray();
				//读取分类信息
				QueryAll queryAll = new QueryAll();
				JSONObject allCls = queryAll.queryAll();
				assertTrue(allCls.has("data"));
				JSONArray allClsArr = allCls.getJSONArray("data");
				Boolean hasCls = false;
				for (int l =0; l<allClsArr.length(); l++){
					JSONObject clsObj = allClsArr.getJSONObject(l);
					JSONObject ciCls = clsObj.getJSONObject("ciClass");
					if (sheetName.compareToIgnoreCase(ciCls.getString("className"))==0){
						hasCls = true;
						ciClassInfo.put("ciClass", ciCls);
						ciClassInfo.put("fixMapping", clsObj.getJSONObject("fixMapping"));
						newAttrDefs = clsObj.getJSONArray("attrDefs");
						break;
					}
				}
				assertTrue(hasCls);
				TagRuleUtil tru = new TagRuleUtil();
				for (int t=0; t<titles.length(); t++){
					if (tru.getAttrId(sheetName, titles.getString(t)).compareTo(new BigDecimal(0))==0){//不存在属性时增加
						JSONObject attr = new JSONObject();
						attr.put("proName", titles.get(t));
						attr.put("orderNo", t+1);
						attr.put("isCiDisp", 0);
						attr.put("isMajor", 0);
						attr.put("isRequired", 0);
						attr.put("proType", 3);
						attr.put("defVal", "");
						attr.put("enumValues", "");
						newAttrDefs.put(attr);
					}
				}
				ciClassInfo.put("attrDefs", newAttrDefs);
				/*ImportCiBySheetName importCiBySheetName = new ImportCiBySheetName();
				importCiBySheetName.ImportCiBySheetName(fileName, sheetName, ciClassInfo);不用了， 修改成异步方式*/
				AsyncImportCiBySheetName asyncImportCiBySheetName = new AsyncImportCiBySheetName();
				asyncImportCiBySheetName.asyncImportCiBySheetName(fileName, sheetName, ciClassInfo);

				GetProgressInfoByKey getProgressInfoByKey =  new GetProgressInfoByKey();
				boolean completeFlag = false;
				while(!completeFlag){
					JSONObject progress = getProgressInfoByKey.getProgressInfoByKey(fileName+":"+sheetName);
					assertTrue(progress.has("data"));
					JSONObject progressData = progress.getJSONObject("data");
					assertTrue(progressData.has("percentage"));
					if (progressData.getInt("percentage")==100)
						completeFlag = true;
				}

			}
		}
	}

	@Then("^成功导入excel文档\"(.*)\"$")
	public void checkImportExcel(String fileName) throws IOException{
		TagRuleUtil tru = new TagRuleUtil();
		String filePath = Scenario_ciExcelBatchImport.class.getResource("/").getPath()+"/testData/ciExcelBatchImport/"+fileName;
		FileInputStream fi = new FileInputStream(filePath);
		Workbook wb = new HSSFWorkbook(fi);
		for (int i=0; i<wb.getNumberOfSheets(); i++){
			Sheet sheet = wb.getSheetAt(i);	
			String sheetName = sheet.getSheetName();
			//读取分类信息
			QueryAll queryAll = new QueryAll();
			JSONObject allCls = queryAll.queryAll();
			assertTrue(allCls.has("data"));
			JSONArray allClsArr = allCls.getJSONArray("data");
			Boolean hasCls = false;
			for (int l =0; l<allClsArr.length(); l++){
				JSONObject clsObj = allClsArr.getJSONObject(l);
				JSONObject ciCls = clsObj.getJSONObject("ciClass");
				if (sheetName.compareToIgnoreCase(ciCls.getString("className"))==0){
					hasCls = true;
					int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获取总列数
					int rowNum=sheet.getLastRowNum();//获取总行数
					JSONObject fixMapping = clsObj.getJSONObject("fixMapping");
					HSSFRow row0 = (HSSFRow) sheet.getRow(0); // 获得工作薄的第一行
					HSSFCell cell00 = row0.getCell(0);
					assertEquals(cell00.toString(), fixMapping.get("nmCiCode"));
					//比较属性
					JSONArray attrDefs = clsObj.getJSONArray("attrDefs");
					assertTrue(coloumNum<= attrDefs.length());

					for (int a=0; a<coloumNum; a++){
						HSSFCell cell = row0.getCell(a);
						assertTrue (tru.getAttrId(sheetName, cell.toString()).compareTo(new BigDecimal(0))>0);
					}

					//比较数据
					QueryPage queryPage = new QueryPage();
					JSONObject ciResult = queryPage.queryPage(sheetName);
					ciResult = ciResult.getJSONObject("data");
					JSONArray ciData = ciResult.getJSONArray("data");
					assertEquals(rowNum, ciData.length());
					assertEquals(rowNum, ciResult.getInt("totalRows"));

					for (int d =1; d<rowNum; d++){
						HSSFRow row = (HSSFRow) sheet.getRow(d); 
					}
					break;
				}
			}
			assertTrue(hasCls);

		}
	}

	@Then("^导入excel文档\"(.*)\"失败$")
	public void checkImportExcelFail(String fileName) throws IOException{
		String filePath = Scenario_ciExcelBatchImport.class.getResource("/").getPath()+"/testData/ciExcelBatchImport/"+fileName;
		FileInputStream fi = new FileInputStream(filePath);
		Workbook wb = new HSSFWorkbook(fi);
		for (int i=0; i<wb.getNumberOfSheets(); i++){
			Sheet sheet = wb.getSheetAt(i);	
			String sheetName = sheet.getSheetName();
			//读取分类信息
			QueryAll queryAll = new QueryAll();
			JSONObject allCls = queryAll.queryAll();
			assertTrue(allCls.has("data"));
			JSONArray allClsArr = allCls.getJSONArray("data");
			Boolean hasCls = false;
			for (int l =0; l<allClsArr.length(); l++){
				JSONObject clsObj = allClsArr.getJSONObject(l);
				JSONObject ciCls = clsObj.getJSONObject("ciClass");
				if (sheetName.compareToIgnoreCase(ciCls.getString("className"))==0){
					hasCls = true;
					break;
				}
			}
			assertFalse(hasCls);
		}
	}


	@When("^上传非excel文档\"(.*)\"失败$")
	public void importNotExcel(String fileName){
		ImportExcel2 importExcel = new ImportExcel2();
		JSONObject importResult = importExcel.importExcel2(fileName);
		assertEquals(new Boolean(false), importResult.get("success"));
		assertEquals(500, importResult.get("code"));
		assertEquals(" the message is empty! ", importResult.get("message"));
	}

}
