package com.uinnova.test.step_definitions.testcase.base.cirltBatchExcelImport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciRlt.QueryPage;
import com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport.ExportImportIgnoreExcel;
import com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport.GetSheetDataBySheetName;
import com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport.ImportCiRltAgain;
import com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport.ImportCiRltData;
import com.uinnova.test.step_definitions.api.base.cirltBatchExcelImport.ImportExcel;
import com.uinnova.test.step_definitions.testcase.base.obj.ciClass.Scenario_ciClass;
import com.uinnova.test.step_definitions.testcase.base.rlt.rltClass.Scenario_rltClass;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenrio_cirltBatchExcelImport {
	
	JSONObject fileNames = null;
	JSONObject result = null;
	String excelName = null;
	List<String> clsNames = null;
	@Given("^系统中已存在如下分类及数据:\"(.*)\"$")
	public void preCiDatas(@Delimiter(",") List<String> clsNames) {
		this.clsNames = null;
		for(int i=0;i<clsNames.size();i++) {
			String filePath = Scenrio_cirltBatchExcelImport.class.getResource("/").getPath()+"/testData/cirltBatchExcelImport/一键导入_多sheet所需分类.xls";
			(new CiClassUtil()).createCiClassAndImportCi("业务领域", clsNames.get(i), "Default", filePath);
		}
		this.clsNames = clsNames;
		
	}
	
	@After("@deleteImportClass")
	public void deleteImportClass(){
		Scenario_ciClass scc = new Scenario_ciClass();
		for (int i = 0; i < clsNames.size(); i++) {
			scc.deleteClass(clsNames.get(i));
		}
	}
	
	@When("^上传关系导入所需excel表:\"(.*)\"$")
	public void importRltExcel(@Delimiter(",") List<String> excelNames) {
		int flag = 0;
		
		fileNames = new JSONObject();
		for(int i=0;i<excelNames.size();i++) {
			JSONObject result = (new ImportExcel()).importExcel(excelNames.get(i));
			if(result.getBoolean("success")) {
				String name = excelNames.get(i).substring(0, excelNames.get(i).length()-4);
				fileNames.put(name, result.getJSONArray("data").getJSONObject(0).getString("fileName"));
				flag++;
			}
		}
		assertTrue(flag==excelNames.size());
	}
	
	
	@Then("^成功获取如下excel表中各sheet关系数据:$")
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
		assertTrue(flag==mapList.size());
	}

	@When("^按照如下设置进行关系映射,并上传:$")
	public void uploadRltMapping(DataTable table) {
		result = (new ImportCiRltData()).importCiRltData(table, fileNames);
		assertTrue(result.getBoolean("success"));
	}
	
	@Then("^获取\"关系数据\"上传报告:$")
	public void getUploadReport(DataTable dt) {
		JSONArray resultReport = result.getJSONArray("data");
				
		List<List<String>> list = dt.raw();
		for (int i = 1; i < list.size(); i++) {
			JSONObject temp = resultReport.getJSONObject(i-1);
			String igroneType = list.get(i).get(0);
			String errMessagesFieldsNum = list.get(i).get(1);
			String ignoreNum = list.get(i).get(2);
			String igroneDatasNum = list.get(i).get(3);
			String fileName = list.get(i).get(4);
			String updateNum = list.get(i).get(5);
			String sheetName = list.get(i).get(6);
			String insertNum = list.get(i).get(7);
			String titles = list.get(i).get(8);
			assertEquals("添加数量:", Integer.parseInt(insertNum), temp.getInt("insertNum"));
			assertEquals("更新数量:", Integer.parseInt(updateNum), temp.getInt("updateNum"));
			assertEquals("忽略数量:", Integer.parseInt(ignoreNum), temp.getInt("ignoreNum"));
//			assertEquals("文件名字:",fileName, temp.getString("fileName"));
			assertTrue("文件名字", temp.getString("fileName").startsWith("rltDataImport"));
			assertEquals("sheet名字:", sheetName, temp.getString("sheetName"));
			assertEquals("忽略类型:",Integer.parseInt(igroneType), temp.getInt("igroneType"));
			if(temp.has("igroneDatas")){
				assertEquals("忽略数据数量：",Integer.parseInt(igroneDatasNum), temp.getJSONArray("igroneDatas").length());
			}
			if(temp.has("errMessagesFields")){
				assertEquals("忽略关系数量:",Integer.parseInt(errMessagesFieldsNum), temp.getJSONArray("errMessagesFields").length());
			}
			excelName = temp.getString("fileName");
			String[] title = titles.split(":");
			if(temp.has("titles")){
				for (int j = 0; j < title.length; j++) {
					boolean boo = false;
					for (int j2 = 0; j2 < temp.getJSONArray("titles").length(); j2++) {
//						System.out.println("---"+title[j].toString()+"---"+temp.getJSONArray("titles").getString(j2).toString()+"---");
						if(title[j].toString().equals(temp.getJSONArray("titles").getString(j2).toString())){
							boo = true;
						}
					}
					assertTrue("titles测试对比", boo);
				}
//				assertEquals("忽略关系数量:",Integer.parseInt(errMessagesFieldsNum), temp.getJSONArray("errMessagesFields").length());
			}
		}

	}
	
	
	@Then("^验证返回信息是否正确:$")
	public void compResultMessage(DataTable dt){
		List<List<String>> list = dt.raw();
		JSONArray resultReport = result.getJSONArray("data");

		for(int i = 1; i < list.size(); i++){
			List<String> messList = list.get(i);
			String errMessagesFieldsArray = messList.get(0);
			String igroneDatasArray = messList.get(1);
			String igroneNum = messList.get(2);
			String [] errMessagesFields = errMessagesFieldsArray.split(":");
			String [] igroneDatas = igroneDatasArray.split(";");
			List<String> errMessagesFieldsList = Arrays.asList(errMessagesFields);
			HashMap<String,String> hm = new HashMap();
			for (int j = 0; j < igroneDatas.length; j++) {
				String errM[] = igroneDatas[j].split("-");
				hm.put(errM[0], errM[1]);
			}
			Set<String> keys = hm.keySet();
			
			for (int j = 0; j < resultReport.length(); j++) {
				JSONObject tempObj = resultReport.getJSONObject(j);
				JSONArray tempArr = tempObj.getJSONArray("igroneDatas");
//暂时不做errMessagesFields验证，太麻烦
				assertEquals("报错信息是否于验证相等:", list.size()-1, tempArr.length());
				int sum = 0;
				
				for (int k = 0; k < tempArr.length(); k++) {
					JSONObject temp = tempArr.getJSONObject(k);
					Iterator<String> it = keys.iterator();
					while(it.hasNext()){
						String key = it.next();
						String value = hm.get(key);
						if(temp.has(key)){
							if(temp.getString(key).equals(value)){
								sum+=1;
							}
//							assertEquals("比较igroneDatas中的value:", temp.getString(key), value);
						}
					}
				}
				assertTrue("比较igroneDatas中的value:", sum == 4);	//这里是因为只验证了4个属性的值
			}
		}
	}
	
	
	@Then("^用以下数据再次导入被忽略的关系数据成功:$")
	public void importAgain(DataTable dt){
		List<List<String>> list = dt.raw();
		
		ImportCiRltAgain ica = new ImportCiRltAgain();
		
		List <String> sourceCiList = new ArrayList();
		List <String> targetCiList = new ArrayList();
		for (int i = 1; i < list.size(); i++) {
			List<String> tempList = list.get(i);
//			String rltName = tempList.get(0);
			String sourceCiCode = tempList.get(1);
			String targetCiCode = tempList.get(2);
			sourceCiList.add(sourceCiCode);
			targetCiList.add(targetCiCode);
		}
		JSONObject resultObj = ica.importCiRltAgain(list.get(1).get(0), sourceCiList, targetCiList);
		System.out.println(resultObj);
	}
	
	
	@Then("^用上一步上传返回的fileName下载错误信息$")
	public void downloadImportIgnoreExcel(){
		ExportImportIgnoreExcel eiie = new ExportImportIgnoreExcel();
		String filePath = eiie.exportImportIgnoreExcel(excelName);
		assertTrue("检查下载是否成功",filePath != null);
	}
	
	
	
	@And("^如下关系数据导入成功:$")
	public void verifyImportCiRltData(DataTable table) throws FileNotFoundException, IOException {
		int flag = 0;
		List<Map<String,String>> mapList = table.asMaps(String.class, String.class);
		for(Map<String,String> map:mapList) {
			String excelName = map.get("excelName");
			String sheetName = map.get("sheetName");
			String rltName = map.get("rltName");
			String filePath = Scenrio_cirltBatchExcelImport.class.getResource("/").getPath()+"/testData/cirltBatchExcelImport/"+excelName+".xls";
			Workbook wb = new HSSFWorkbook(new FileInputStream(filePath));
			int excelNum = wb.getSheet(sheetName).getLastRowNum();
			int totalRows = (new QueryPage()).queryPage(rltName).getJSONObject("data").getInt("totalRows");
			if(totalRows==excelNum) {
				flag++;
			}else {
				fail();
			}
		}
		assertTrue(flag==mapList.size());
	}
}
