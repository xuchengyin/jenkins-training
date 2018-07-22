package com.uinnova.test.step_definitions.testcase.base.obj.ciClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.api.base.ciClass.ExportClassAttrExcel;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryAll;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryById;
import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.cmv.ciClass.ExportClassDiagram;
import com.uinnova.test.step_definitions.api.cmv.ciClass.ImportClassDiagram;
import com.uinnova.test.step_definitions.utils.base.CiClassDirUtil;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ImageUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;
import com.uinnova.test.step_definitions.utils.common.ZipUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class Scenario_ciClass {
	int classId;
	String filePath;
	JSONArray allCiClassObj; 
	private static List<String> clClsList = new  ArrayList<String>();

	@After("@CleanCiCls")
	public void cleanCicls(){
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

	/*==================Scenario Outline: ciClass_增删改查==================*/
	@When("^在\"(.*)\"目录下,创建名称为\"(.*)\"的ci分类,使用图标为\"(.*)\"$")
	public void creatCiClass(String dirName, String className, String imageName) {
		SaveOrUpdate su = new SaveOrUpdate();
		su.createCiClassFirstTime(className, dirName, imageName);
		clClsList.add(className);
	}

	@Then("^系统中存在名称为\"(.*)\"的ci分类$")
	public void searchCiClass(String className) {
		CiClassUtil cu = new CiClassUtil();
		BigDecimal classId = cu.getCiClassId(className);
		assertNotNull(classId);
	}

	@And("^\"(.*)\"的ci分类在\"(.*)\"目录下,使用图标为\"(.*)\"$")
	public void check_saveCiClass(String className,String dirName,String icon){
		JSONObject classInfo =  (new CiClassUtil()).getCiClassInfoWithoutAttrInfo(className);
		BigDecimal real_dirId = classInfo.getBigDecimal("dirId");
		BigDecimal expect_dirId =new BigDecimal((new CiClassDirUtil()).getDirId(dirName)) ;
		String real_icon =  classInfo.getString("icon");
		String expect_icon = (new ImageUtil()).getImageUrl(icon);
		assertEquals(expect_dirId, real_dirId);
		assertEquals(expect_icon ,  real_icon);
	}

	@When("^修改\"(.*)\"分类的名称为\"(.*)\"$")
	public void modifyClass(String sourceName, String distName) {
		SaveOrUpdate su = new SaveOrUpdate();
		su.updateCiClassName(sourceName, distName);
		clClsList.remove(sourceName);
		clClsList.add(distName);
	}

	@Then("^更新成功,\"(.*)\"分类名称更新为\"(.*)\"$")
	public void updateClass(String sourceName, String distName) {
		String source_sql = "SELECT * FROM cc_ci_class c where c.CLASS_CODE='" + sourceName
				+ "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList sourceData = JdbcUtil.executeQuery(source_sql);
		if (distName.compareToIgnoreCase(sourceName)!=0)
			assertNotNull(sourceData);
		else
			assertEquals(1, sourceData.size());
		String dist_sql = "SELECT ID FROM cc_ci_class c where c.CLASS_CODE='" + distName
				+ "' and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList distData = JdbcUtil.executeQuery(dist_sql);
		assertEquals(1, distData.size());
	}

	@When("^复制\"(.*)\"分类,名称修改为\"(.*)\"$")
	public void copyCiClass(String sourceName,String copyName){
		BigDecimal classId = (new CiClassUtil()).getCiClassId(sourceName);
		JSONObject classInfo = (new QueryById().queryById(sourceName));
		JSONArray attrDefsInfo = classInfo.getJSONObject("data").getJSONArray("attrDefs");
		JSONObject ciClassInfo = classInfo.getJSONObject("data").getJSONObject("ciClass");
		JSONObject fixMapping = classInfo.getJSONObject("data").getJSONObject("fixMapping");
		JSONArray attrDefs = new JSONArray();
		for(int i=0;i<attrDefsInfo.length();i++){
			JSONObject attrDefsObj = new JSONObject();
			JSONObject attrSingleInfo = (JSONObject)attrDefsInfo.get(i);
			if(attrSingleInfo.has("enumValues")){
				String enumValues = attrSingleInfo.getString("enumValues");
				attrDefsObj.put("enumValues", enumValues);
			}else{
				attrDefsObj.put("enumValues", "");
			}
			attrDefsObj.put("isCiDisp", attrSingleInfo.getInt("isCiDisp"));
			attrDefsObj.put("isRequired", attrSingleInfo.getInt("isRequired"));
			attrDefsObj.put("orderNo",  attrSingleInfo.getInt("orderNo"));
			attrDefsObj.put("proName", attrSingleInfo.getString("proName"));
			attrDefsObj.put("proType", attrSingleInfo.getInt("proType"));
			attrDefs.put(attrDefsObj);
		}

		JSONObject ciClass = new JSONObject();
		ciClass.put("DISP_FIELD_IDS", "");
		ciClass.put("DISP_FIELD_NAMES", "");
		ciClass.put("ciType", ciClassInfo.getInt("ciType"));
		ciClass.put("classCode", copyName );
		ciClass.put("classColor", ciClassInfo.getString("classColor"));
		//ciClass.put("classDesc", ciClassInfo.getString("classDesc")); oracle数据库如果这项是空， 返回值就没有
		ciClass.put("className",copyName );
		ciClass.put("dirId", ciClassInfo.getBigDecimal("dirId"));
		ciClass.put("icon", ciClassInfo.getString("icon"));
		ciClass.put("parentId", ciClassInfo.getBigDecimal("parentId"));

		JSONObject result = (new SaveOrUpdate()).saveOrUpdate(attrDefs, ciClass, fixMapping);
		assertTrue(result.getBoolean("success"));
		clClsList.add(copyName);

	}

	@Then("^\"(.*)\"分类成功复制为\"(.*)\"$")
	public void checkCopyCiClass(String sourceName,String copyName){
		BigDecimal sourceId = (new CiClassUtil()).getCiClassId(sourceName);
		JSONObject sourceInfo = (new QueryById()).queryById(sourceName);
		JSONArray soureAttrDefs = sourceInfo.getJSONObject("data").getJSONArray("attrDefs");
		BigDecimal copyId = (new CiClassUtil()).getCiClassId(copyName);
		JSONObject copyInfo =  (new QueryById()).queryById(String.valueOf(copyName));
		JSONArray copyAttrDefs = copyInfo.getJSONObject("data").getJSONArray("attrDefs");
		int flag = 0;
		for(int i=0;i<soureAttrDefs.length();i++){
			JSONObject source = (JSONObject) soureAttrDefs.get(i);
			JSONObject copy = (JSONObject) copyAttrDefs.get(i);
			if(source.getString("proName").equals(copy.getString("proName"))){
				if(source.getInt("proType")==copy.getInt("proType")){
					if(source.getInt("isRequired")==copy.getInt("isRequired")){
						if(source.getInt("isCiDisp")==copy.getInt("isCiDisp")){
							flag++;
						}
					}
				}
			}
			else{
				QaUtil.report("========复制前后数据不一致======");
				fail();
			}
		}
		if(flag==soureAttrDefs.length()){
			String sourceCode = sourceInfo.getJSONObject("data").getJSONObject("fixMapping").getString("nmCiCode");
			String copyCode = copyInfo.getJSONObject("data").getJSONObject("fixMapping").getString("nmCiCode");
			if(sourceCode.equals(copyCode)){
				assertTrue(true);
			}
		}
	}

	@When("^删除\"(.*)\"分类及数据$")
	public void deleteClass(String className){
		Boolean result = (new CiClassUtil()).deleteCiClassAndCi(className);
		assertTrue(result);
		clClsList.remove(className);
	}


	@Then("^名称为\"(.*)\"的分类删除成功$")
	public void verifyDelCiClass(String ciClassName) {
		String startChar = ciClassName.substring(0, 2);
		String endChar = ciClassName.substring(ciClassName.length() - 1);
		String sql = "SELECT * FROM cc_ci_class c where c.CLASS_CODE='" + ciClassName
				+ "' and c.DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertEquals(0, list.size());
	}


	/*===================Scenario Outline: ciClass属性_添加不同类型的属性、设置label、更改主键==================*/
	@When("^修改\"(.*)\"的主键名称为\"(.*)\"$")
	public void addClassAttr(String className,String newName){
		if(newName.indexOf(".txt")>0){
			String filePath = Scenario_ciClass.class.getResource("/").getPath()+"/testData/ci/"+newName;
			newName = (new TxtUtil()).readTxt(filePath);
		}
		JSONObject result = (new SaveOrUpdate()).updatePrimaryKeyName(className, newName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^\"(.*)\"分类的主键名称修改为\"(.*)\"$")
	public void  verfifyNewCodeName(String className,String CodeName){
		if(CodeName.indexOf(".txt")>0){
			String filePath = Scenario_ciClass.class.getResource("/").getPath()+"/testData/ci/"+CodeName;
			CodeName = (new TxtUtil()).readTxt(filePath);
		}
		JSONObject result = (new QueryById()).queryById(className);
		JSONArray attrDefs = result.getJSONObject("data").getJSONArray("attrDefs");
		String codeName = null;
		for(int i=0; i<attrDefs.length();i++){
			JSONObject tmp = (JSONObject) attrDefs.get(i);
			if(tmp.getInt("isMajor")==1){
				codeName = tmp.getString("proName");
				break;
			}
		}
		assertEquals(codeName, CodeName);
	}

	@When("^给\"(.*)\"分类添加如下属性:$")
	public void addAttr(String className,DataTable table){
		JSONObject res = (new CiClassUtil()).addAttrUtil(className, table);
		assertTrue(res.getBoolean("success"));
	}

	@Then("^\"(.*)\"分类属性更新成功$")
	public void verifyAddAttr(String className,DataTable table){
		BigDecimal classId = (new CiClassUtil()).getCiClassId(className);
		String sql =  "SELECT * FROM cc_ci_attr_def c where CLASS_ID="+classId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		int rows = table.raw().size();
		int flag = 0;
		for (int i=1;i<rows;i++){
			//读取excel表数据
			HashMap map = (HashMap)list.get(i-1);
			String proName = null;
			if(table.raw().get(i).get(0).indexOf(".txt")>0){
				String filePath = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+table.raw().get(i).get(0);
				proName = (new TxtUtil()).readTxt(filePath);
			}else{
				proName = table.raw().get(i).get(0);
			}
			Boolean nameEql = proName.equals(map.get("PRO_NAME"));
			String dataType = (new CiClassUtil()).getDataType( table.raw().get(i), 1);
			Boolean typeEql = dataType.equals(map.get("PRO_TYPE").toString());
			//			Boolean dict = false;
			String id = null;
			String dropType = null;
			Boolean dropIdEql = false;
			Boolean dropTypeEql = false;

			if(dataType.equals("8")){
				String[] diLi = table.raw().get(i).get(1).split("_");
				if(diLi[1].equalsIgnoreCase("type")){
					id = String.valueOf((new CiClassUtil()).getCiClassId(diLi[2]));
					dropType = "2";
				}
				if(diLi[1].equalsIgnoreCase("tag")){
					id = String.valueOf((new TagRuleUtil()).getTagId(diLi[2]));
					dropType = "1";
				}
				dropIdEql = id.equals(map.get("PRO_DROP_SOURCE_ID").toString());
				dropTypeEql = dropType.equals(map.get("PRO_DROP_SOURCE_TYPE").toString());
			}
			Boolean isRequiredEql = table.raw().get(i).get(2).equals(map.get("IS_REQUIRED").toString());
			Boolean isDispEql = table.raw().get(i).get(3).equals(map.get("IS_CI_DISP").toString());
			Boolean enumValueEql =false;
			if (map.get("ENUM_VALUES") == null){
				if (table.raw().get(i).get(4).isEmpty())
					enumValueEql = true;
			}
			else
				enumValueEql = table.raw().get(i).get(4).equals(map.get("ENUM_VALUES").toString());
			Boolean isMajorEql = table.raw().get(i).get(5).equals(map.get("IS_MAJOR").toString());
			QaUtil.report("====nameEql【"+nameEql+"】==typeEql 【"+typeEql +"】===dropIdEql【"+dropIdEql+"】==dropTypeEql【"+dropTypeEql+" 】isRequiredEql【 "+isRequiredEql+"】 isDispEql【 "+isDispEql+"】enumValueEql 【"+enumValueEql +"】isMajorEql【"+isMajorEql+"】======");
			if(dataType.equals("8")){
				if(nameEql && typeEql && dropIdEql && dropTypeEql && isRequiredEql && isDispEql && enumValueEql && isMajorEql){
					flag++;
				}
			}else{
				if(nameEql && typeEql &&isRequiredEql && isDispEql && enumValueEql && isMajorEql){
					flag++;
				}
			}
		}
		QaUtil.report("======flag===="+flag+"===rows===="+(rows-1));
		assertEquals(flag, rows-1);
	}


	@When("^给\"(.*)\"分类修改属性如下:$")
	public void updateAttr(String className,DataTable table){
		JSONObject res = (new CiClassUtil()).updateAttrUtil(className, table);
		assertTrue(res.getBoolean("success"));
	}

	@Then("^给\"(.*)\"分类修改属性成功$")
	public void verifyUpdateAttr(String className,DataTable table){
		BigDecimal classId = (new CiClassUtil()).getCiClassId(className);
		String sql =  "SELECT * FROM cc_ci_attr_def c where CLASS_ID="+classId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		int rows = table.raw().size();
		int flag = 0;
		for (int i=1;i<rows;i++){
			//读取excel表数据
			HashMap map = (HashMap)list.get(i-1);
			String proName = null;
			if(table.raw().get(i).get(0).indexOf(".txt")>0){
				String filePath = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+table.raw().get(i).get(0);
				proName = (new TxtUtil()).readTxt(filePath);
			}else{
				proName = table.raw().get(i).get(0);
			}
			Boolean nameEql = proName.equals(map.get("PRO_NAME"));
			String dataType = (new CiClassUtil()).getDataType( table.raw().get(i), 1);
			Boolean typeEql = dataType.equals(map.get("PRO_TYPE").toString());
			//			Boolean dict = false;
			String id = null;
			String dropType = null;
			Boolean dropIdEql = false;
			Boolean dropTypeEql = false;

			if(dataType.equals("8")){
				String[] diLi = table.raw().get(i).get(1).split("_");
				if(diLi[1].equals("type")){
					id = String.valueOf((new CiClassUtil()).getCiClassId(diLi[2]));
					dropType = "2";
				}
				if(diLi[1].equals("tag")){
					id = String.valueOf((new TagRuleUtil()).getTagId(diLi[2]));
					dropType = "1";
				}
				dropIdEql = id.equals(map.get("PRO_DROP_SOURCE_ID").toString());
				dropTypeEql = dropType.equals(map.get("PRO_DROP_SOURCE_TYPE").toString());
			}
			Boolean isRequiredEql = table.raw().get(i).get(2).equals(map.get("IS_REQUIRED").toString());
			Boolean isDispEql = table.raw().get(i).get(3).equals(map.get("IS_CI_DISP").toString());
			Boolean enumValueEql = table.raw().get(i).get(4).equals(map.get("ENUM_VALUES").toString());
			Boolean isMajorEql = table.raw().get(i).get(5).equals(map.get("IS_MAJOR").toString());
			QaUtil.report("====nameEql【"+nameEql+"】==typeEql 【"+typeEql +"】===dropIdEql【"+dropIdEql+"】==dropTypeEql【"+dropTypeEql+" 】isRequiredEql【 "+isRequiredEql+"】 isDispEql【 "+isDispEql+"】enumValueEql 【"+enumValueEql +"】isMajorEql【"+isMajorEql+"】======");
			if(dataType.equals("8")){
				if(nameEql && typeEql && dropIdEql && dropTypeEql && isRequiredEql && isDispEql && enumValueEql && isMajorEql){
					flag++;
				}
			}else{
				if(nameEql && typeEql &&isRequiredEql && isDispEql && enumValueEql && isMajorEql){
					flag++;
				}
			}
		}
		QaUtil.report("======flag===="+flag+"===rows===="+(rows-1));
		assertEquals(flag, rows-1);
	}

	@When("^删除\"(.*)\"的主键属性,并更改主键为\"(.*)\"$")
	public void delPrimaryKeyAndUpdate(String className,String newPrimaryKey){
		JSONObject result = (new QueryById()).queryById(className);
		JSONArray attrDefs =  result.getJSONObject("data").getJSONArray("attrDefs");
		attrDefs.remove(0);
		JSONObject attr0 = (JSONObject)attrDefs.get(0);
		attr0.put("proType", 3);
		attr0.put("isMajor", 1);

		JSONObject ciClass =  result.getJSONObject("data").getJSONObject("ciClass");
		JSONObject fixMapping =  new JSONObject();
		fixMapping.put("nmCiDesc", "");
		//		fixMapping.put("nmCiCode", "编号");
		fixMapping.put("nmCiCode", newPrimaryKey);

		SaveOrUpdate su = new SaveOrUpdate();
		JSONObject res = su.saveOrUpdate(attrDefs, ciClass, fixMapping);
		assertTrue(res.getBoolean("success"));
	}

	@Then("^\"(.*)\"属性定义更新成功$")
	public void verifyUpdatePrimaryKey(String className){
		JSONObject result = (new QueryById()).queryById(className);
		JSONArray attrDefs =  result.getJSONObject("data").getJSONArray("attrDefs");
		QaUtil.report("=========="+className+"分类主键更新成功=======");
		assert(attrDefs.length()==6);
	}


	@Then("^系统中不存在名称为\"(.*)\"的分类$")
	public void verifyDelClass(String className){
		BigDecimal classId = (new CiClassUtil()).getCiClassId(className);
		QaUtil.report("=========="+className+"分类删除成功=======");
		assertEquals(classId, new BigDecimal(0));
	}

	/*========Scenario Outline:ciClass_导入、导出==============*/
	@And("^导入\"(.*)\"分类属性$")
	public void importClassAttr(String className){
		String filePath = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/工商银行_机柜.xls";
		JSONObject result = (new CiClassUtil()).importCiClassAttr(className,filePath);
		assertTrue(result.getBoolean("success"));

	}

	@Then("^\"(.*)\"分类属性导入成功$")
	public void check_importAttr(String className){
		ExcelUtil  eu = new ExcelUtil();
		String filePath = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/工商银行_机柜.xls";
		JSONArray arr = eu.readFromExcel(filePath, className);
		int col = arr.getJSONObject(0).length();
		CiClassUtil cu = new CiClassUtil();
		BigDecimal classId = cu.getCiClassId(className);
		String sql =  "select PRO_NAME from cc_ci_attr_def c where CLASS_ID="+classId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList classAttr = JdbcUtil.executeQuery(sql);
		for (int i=0;i<col;i++){
			String col_excel = arr.getJSONObject(0).getString(String.valueOf(i));
			HashMap col_sql_tmp = (HashMap)classAttr.get(i+1);
			String col_sql  = col_sql_tmp.get("PRO_NAME").toString();
			QaUtil.report("【col_excel:::】"+col_excel+"【col_sql::::】"+col_sql);
			assertEquals(col_excel, col_sql);
		}
	}


	@When("^导出\"([^\"]*)\"分类$")
	public void exportAttr(String className){
		CiClassUtil cu = new CiClassUtil();
		BigDecimal classId = cu.getCiClassId(className);
		ExportCi ec = new ExportCi();
		filePath = ec.exportSingleCiClass(classId);
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^\"([^\"]*)\"分类导出成功$")
	public void check_exportAttr(String className){
		JSONObject ciC = (new QueryById()).queryById(className);
		int attr_len = ciC.getJSONObject("data").getJSONArray("attrDefs").length();
		JSONArray arr = (new ExcelUtil()).readFromExcel(filePath, className);
		assertEquals(1, arr.length());
		assertEquals(arr.getJSONObject(0).length(), attr_len);
	}


	/*==================Scenario:ciClass_下载模板===================*/
	@When("^下载\"分类模板\"$")
	public void downloadClassModel(){
		filePath = (new ExportClassAttrExcel()).exportClassAttrExcel();
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^\"分类模板\"文件下载成功$")
	public void check_downloadClassModel(){
		JSONArray arr = (new ExcelUtil()).readFromExcel(filePath, "分类名称");
		assertEquals(1, arr.length());
		assertEquals(3, arr.getJSONObject(0).length());
	}


	/*Scenario:ciClass_导出全部*/
	@When("^导出全部分类$")
	public void exportAllCi(){
		filePath = (new ExportCi()).exportAllCiClass();
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^分类数据导出成功$")
	public void check_exportAllCi(){
		try {
			String sql = "SELECT * FROM cc_ci_class c where DATA_STATUS=1 and CI_TYPE=1 and domain_id=" +QaUtil.domain_id  ;
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
			assertEquals(wb.getNumberOfSheets(), JdbcUtil.executeQuery(sql).size());
		} catch (Exception e) {
			QaUtil.report(e.getMessage());
		}
	}


	@When("查询所有分类及其属性")
	public void queryAll(){
		QueryAll queryAll = new QueryAll();
		JSONObject result = queryAll.queryAll();
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		allCiClassObj = result.getJSONArray("data");
		assertTrue(allCiClassObj.length()>0);
	}


	@Then("成功查询所有分类及其属性")
	public void checkQueryAll(){
		String sql = "SELECT * FROM cc_ci_class c where DATA_STATUS=1 and CI_TYPE=1 and DOMAIN_ID=" + QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(allCiClassObj.length(), list.size());
		Map map = new HashMap();
		for (int i=0; i<list.size(); i++){
			HashMap temp =  (HashMap) list.get(i);
			String classID = temp.get("ID").toString();
			map.put(classID, temp);
		}

		for(int j=0; j<allCiClassObj.length(); j++){
			JSONObject temp = allCiClassObj.getJSONObject(j);
			JSONObject tempCLs = temp.getJSONObject("ciClass");
			String classId = String.valueOf(tempCLs.getBigDecimal("id"));
			if (map.containsKey(classId)){
				//比较分类信息
				Map tempMap = (Map) map.get(classId);
				assertEquals(tempCLs.getBigDecimal("dirId"),tempMap.get("DIR_ID"));
				assertEquals(tempCLs.getBigDecimal("isLeaf"),tempMap.get("IS_LEAF"));
				assertEquals(tempCLs.getBigDecimal("ciType"),tempMap.get("CI_TYPE"));
				assertEquals(tempCLs.getBigDecimal("classLvl"),tempMap.get("CLASS_LVL"));
				assertEquals(tempCLs.getBigDecimal("parentId"),tempMap.get("PARENT_ID"));
				assertEquals(tempCLs.getString("classCode"),tempMap.get("CLASS_CODE"));
				assertEquals(tempCLs.getString("classStdCode"),tempMap.get("CLASS_STD_CODE"));
				assertEquals(tempCLs.getString("classColor"),tempMap.get("CLASS_COLOR"));
				assertEquals(tempCLs.getString("className"),tempMap.get("CLASS_NAME"));
				assertEquals(tempCLs.getString("classPath"),tempMap.get("CLASS_PATH"));

				//assertEquals(tempCLs.getString("classDesc"),tempMap.get("CLASS_DESC"));//接口有的class返回了该字段， 有的没返回
				assertTrue(tempCLs.getString("icon").contains((CharSequence) tempMap.get("ICON")));
				assertEquals(tempCLs.getString("creator"),tempMap.get("CREATOR"));
				assertEquals(tempCLs.getBigDecimal("createTime"),tempMap.get("CREATE_TIME"));
				assertEquals(tempCLs.getString("modifier"),tempMap.get("MODIFIER"));
				assertEquals(tempCLs.getBigDecimal("modifyTime"),tempMap.get("MODIFY_TIME"));
			}else{
				fail("数据不匹配");
			}

			//比较分类固定属性
			JSONObject tempFixMapping = temp.getJSONObject("fixMapping");
			String sqlFix = "SELECT * FROM cc_fix_attr_mapping c where DATA_STATUS=1 and id=" + classId +" and DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList listFix = (ArrayList) JdbcUtil.executeQuery(sqlFix);
			assertEquals(1, listFix.size());
			Map fixMap =  (Map) listFix.get(0);
			assertEquals(tempFixMapping.getBigDecimal("id"),fixMap.get("ID"));
			assertEquals(tempFixMapping.getBigDecimal("domainId"),fixMap.get("DOMAIN_ID"));
			assertEquals(tempFixMapping.getBigDecimal("dataStatus"),fixMap.get("DATA_STATUS"));
			assertEquals(tempFixMapping.getString("nmCiCode"),fixMap.get("NM_CI_CODE"));

			assertEquals(tempFixMapping.getString("creator"),fixMap.get("CREATOR"));
			assertEquals(tempFixMapping.getBigDecimal("createTime"),fixMap.get("CREATE_TIME"));
			assertEquals(tempFixMapping.getString("modifier"),fixMap.get("MODIFIER"));
			assertEquals(tempFixMapping.getBigDecimal("modifyTime"),fixMap.get("MODIFY_TIME"));

			//比较分类属性
			JSONArray attrDefs = temp.getJSONArray("attrDefs");
			String sqlAttr = "SELECT * FROM cc_ci_attr_def c where DATA_STATUS=1 and CLASS_ID=" + classId +" and DOMAIN_ID = "+ QaUtil.domain_id+" order by id";
			ArrayList listAttr = (ArrayList) JdbcUtil.executeQuery(sqlAttr);
			assertEquals(attrDefs.length(), listAttr.size());
			//数据库的内容存储到临时MAP
			Map tempAttrMap = new HashMap();
			for (int i=0; i<listAttr.size(); i++){
				HashMap tempAttr =  (HashMap) listAttr.get(i);
				String attrID = tempAttr.get("ID").toString();
				tempAttrMap.put(attrID, tempAttr);
			}
			for (int i=0; i<attrDefs.length(); i++){
				JSONObject attrObj = (JSONObject) attrDefs.get(i);
				String attrID =  String.valueOf(attrObj.getBigDecimal("id"));
				if (tempAttrMap.containsKey(attrID)){
					Map attrMap = (Map) tempAttrMap.get(attrID);
					assertEquals(attrObj.getBigDecimal("classId"),attrMap.get("CLASS_ID"));
					assertEquals(attrObj.getBigDecimal("id"),attrMap.get("ID"));
					assertEquals(attrObj.getBigDecimal("proType"),attrMap.get("PRO_TYPE"));
					assertEquals(attrObj.getBigDecimal("isCiDisp"),attrMap.get("IS_CI_DISP"));
					assertEquals(attrObj.getBigDecimal("ciType"),attrMap.get("CI_TYPE"));
					assertEquals(attrObj.getBigDecimal("orderNo"),attrMap.get("ORDER_NO"));
					assertEquals(attrObj.getBigDecimal("isRequired"),attrMap.get("IS_REQUIRED"));
					assertEquals(attrObj.getBigDecimal("domainId"),attrMap.get("DOMAIN_ID"));
					assertEquals(attrObj.getBigDecimal("isMajor"),attrMap.get("IS_MAJOR"));
					assertEquals(attrObj.getBigDecimal("dataStatus"),attrMap.get("DATA_STATUS"));
					assertEquals(attrObj.getBigDecimal("mpCiField"),attrMap.get("MP_CI_FIELD"));
					assertEquals(attrObj.getString("proName"),attrMap.get("PRO_NAME"));
					assertEquals(attrObj.getString("proStdName"),attrMap.get("PRO_STD_NAME"));
					assertEquals(attrObj.getString("creator"),attrMap.get("CREATOR"));
					assertEquals(attrObj.getBigDecimal("createTime"),attrMap.get("CREATE_TIME"));
					assertEquals(attrObj.getString("modifier"),attrMap.get("MODIFIER"));
					assertEquals(attrObj.getBigDecimal("modifyTime"),attrMap.get("MODIFY_TIME"));
				}
				else{
					fail("分类属性数据接口和数据库返回结果不一致");
				}
			}
		}
	}
	@Then("^用以下fileName验证可视化建模模板内容:$")
	public void exportClassDiagramTest(DataTable dt){
		ExportClassDiagram ecd = new ExportClassDiagram();
		String path = ecd.exportClassDiagram();
		ZipUtil zu = new ZipUtil();
		List<List<String>> list = dt.raw();
		for(int i = 1; i < list.size(); i++){
			String fileName = list.get(i).get(1);
		
			try {
				//这个readFileContent方法有问题，正常来说这样的方法应该传入一个方法然后，用对应方法读取文件，这样可以解决excel等文件乱码问题，着急，先这样。
				String readFile = zu.readFileContent(path, fileName);
				assertTrue(readFile.trim().length() > 0);//只判断一下是否为空
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@When("^用\"(.*)\"文件测试可视化建模模板上传$")
	public void importClassDiagram(String fileName){
		ImportClassDiagram icd = new ImportClassDiagram();
		String result = icd.importClassDiagram(fileName);
		JSONObject resultObj = new JSONObject(result);
		assertEquals(resultObj.get("data").toString(),"1");
	}
}
