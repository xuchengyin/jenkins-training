package com.uinnova.test.step_definitions.testcase.base.obj.ci;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.api.base.ci.ImportCi;
import com.uinnova.test.step_definitions.api.base.ci.QueryPage;
import com.uinnova.test.step_definitions.api.base.ci.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryById;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_ci {

	String filePath;

	@When("^给\"(.*)\"添加如下数据:$")
	public void saveOrUpdateCi(String className,DataTable table){
		SaveOrUpdate  su= new SaveOrUpdate();
		int rows = table.raw().size();
		int j=0;
		for (int i=1;i<rows;i++){
			su.saveOrUpdate(className, table, i);
			j++;
		}
		QaUtil.report("======"+className+"的ci数据添加成功========");
		assertEquals(j, rows-1);
		try {
			Thread.sleep(6000);// 属性索引表同步数据5秒一次
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("^\"(.*)\"分类数据添加成功$")
	public void check_addCi(String className,DataTable table){
		QueryPage qp = new QueryPage();
		JSONObject ciData = qp.queryPage(className);
		JSONObject data = ciData.getJSONObject("data");
		assertTrue(data.getInt("totalRows")==table.raw().size()-1);

	}

	@When("^导出\"(.*)\"分类数据$")
	public void importSingleCi(String className){
		BigDecimal classId = (new CiClassUtil()).getCiClassId(className);
		filePath = (new ExportCi()).exportSingleCi(classId);
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^\"(.*)\"分类数据导出成功$")
	public void check_importSingleCi(String className){
		//判断数据库值与excel表一致
		Boolean result =(new CiUtil()).compSQLDataToExcel(className, filePath);
		assertTrue(result);
	}



	@When("删除\"(.*)\"分类下主键为\"(.*)\"的分类数据$")
	public void deleteCi(String className,String keyName){
		Boolean result = (new CiUtil()).deleteSingCi(className, keyName);
		assertTrue(result);
	}

	@Then("^\"(.*)\"分类下不存在主键为\"(.*)\"的分类数据$")
	public void check_deleteCi(String className,String keyName){
		JSONObject resultObj = (new QueryPage()).queryPage(className);
		int ciNum = resultObj.getJSONObject("data").getJSONArray("data").length();
		for(int i=0;i<ciNum;i++){
			JSONObject ci = (JSONObject) resultObj.getJSONObject("data").getJSONArray("data").get(i);
			if(ci.getJSONObject("ci").getString("ciCode").equals(keyName)){
				fail();
			}else{
				assertTrue(true);
			}
		}
	}


	@When("^下载\"(.*)\"分类数据模板$")
	public void downloadCiModel(String className){
		BigDecimal classId = (new CiClassUtil()).getCiClassId(className);
		filePath =(new ExportCi()).exportSingleCiClass(classId);
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("模板中内容与分类\"(.*)\"属性一致")
	public void check_downloadCiModel(String className){

		JSONArray arr = (new ExcelUtil()).readFromExcel(filePath, className);
		int attrNum = (new QueryById().queryById(className)).getJSONObject("data").getJSONArray("attrDefs").length();
		assertTrue(arr.getJSONObject(0).length()==attrNum);

	}


	@When("^将\"(.*)\"表中的数据导入\"(.*)\"分类数据$")
	public void importCiData(String fileName,String className){
		filePath  = Scenario_ci.class.getResource("/").getPath()+"testData/ci/"+fileName;
		JSONObject result = (new ImportCi()).importCi(className, filePath);
		assertTrue(result.toString().indexOf("ciDataImport")>0);
	}

	@Then("^\"(.*)\"表中的数据成功导入\"(.*)\"数据$")
	public void checkImportCi(String fileName,String className){
		filePath  = Scenario_ci.class.getResource("/").getPath()+"testData/ci/"+fileName;
//		JSONArray excelDatas = (new ExcelUtil()).readFromExcel_useTitleFeildAsKey(filePath, className);
		
		JSONArray excelDatas = (new ExcelUtil()).readFromExcel_useTitleFeildAsKey_import_ci(filePath, className);
		JSONArray apiDatas = new JSONArray();
		JSONArray datas = (new CiUtil()).getAllCiData(className);
		for(int i=0;i<datas.length();i++){
			JSONObject data =  datas.getJSONObject(i).getJSONObject("attrs");
			apiDatas.put(data);
		}

		//获取excel表头
		ArrayList<String> title = new ArrayList<String>();
		JSONArray attrDefs = (new QueryById()).queryById(className).getJSONObject("data").getJSONArray("attrDefs");
		for(int as=0;as<attrDefs.length();as++){
			String keyName = attrDefs.getJSONObject(as).getString("proName");
			title.add(keyName);
		}

		//获取CiCode
		String ciCodeField = (new QueryById()).queryById(className).getJSONObject("data").getJSONObject("fixMapping").getString("nmCiCode");

		//获取api数据的数据，循环与excel对比
		int rowFlag = 0;
		for(int i=0;i<apiDatas.length();i++){
			String apiCiCode = apiDatas.getJSONObject(i).getString(ciCodeField);
			//获取行号
			int line = 0;
			String excelCiCode = null;
			for(int j=1;j<excelDatas.length();j++){
				//判断apiCode与excelCode相等
				excelCiCode =  excelDatas.getJSONObject(j).getString(ciCodeField);
				if(excelCiCode.equals(apiCiCode)){
					line = j;
					break;
				}
			}
			QaUtil.report("======匹配excel表第"+line+"行=====apiCiCode【"+apiCiCode+"】=excel【"+excelCiCode+"】=====");
			//			 //对比列数据
			String keyName = null;
			String apiValue = null;
			String exclValue = null;
			int cols = excelDatas.getJSONObject(0).length();
			int colFlag = 0;
			if(line!=0){
				for(int col=0;col<cols;col++){
					keyName = title.get(col).toString();
					//判断参数为空
					if(apiDatas.getJSONObject(i).toString().indexOf(keyName)>0){
						apiValue = apiDatas.getJSONObject(i).getString(keyName);
						exclValue = excelDatas.getJSONObject(line).getString(keyName);
						if(apiValue.indexOf(exclValue)>=0){
							QaUtil.report("=====apiValue【"+apiValue+"】=exclValue【"+exclValue+"】相同=====");
							colFlag++;
						}else{
							QaUtil.report("=====apiValue【"+apiValue+"】=exclValue【"+exclValue+"】不相同=====");
						}
					}else{
						apiValue = null;
						exclValue = null;
						QaUtil.report("=====apiValue【"+apiValue+"】=exclValue【"+exclValue+"】为空=====");
						colFlag++;
						continue;
					}
				}
			}
			if(colFlag==cols){
				rowFlag++;
			}else{
				QaUtil.report(line+"行 **==不相等===***"+rowFlag);
			}
		}
		assertEquals(rowFlag, excelDatas.length()-1);
	}

	@When("^导出系统中的全部ci数据$")
	public void exportAllCi(){
		filePath = (new ExportCi()).exportAllCi();
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^ci分类数据全部导出成功$")
	public void checkExportAllCi(){
		try {
			Thread.sleep(3000);//导出的文件经常打不开，等待一会
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int Flag=0;
		JSONObject allClass = (new CiClassUtil()).getAllCiClass();
		int classNum = allClass.length();
		for (int i=0;i<classNum;i++){
			String className = allClass.getString(String.valueOf(i));

			Boolean result = (new CiUtil()).compSQLDataToExcel(className, filePath);	
			if(result){
				Flag++;
			}
		}
		if(Flag==classNum){
			assertTrue(true);
		}
	}




}
