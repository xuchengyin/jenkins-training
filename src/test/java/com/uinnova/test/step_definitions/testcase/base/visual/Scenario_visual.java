package com.uinnova.test.step_definitions.testcase.base.visual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciClass.GetClassTree;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryCiClassDiagramInfoList;
import com.uinnova.test.step_definitions.api.base.ciClass.QueryCiClassRltList;
import com.uinnova.test.step_definitions.api.base.ciClass.RemoveCiClassRltInfo;
import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdateCiClassDiagramInfo;
import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdateCiClassRlt;
import com.uinnova.test.step_definitions.api.base.kpi.tpl.QueryKpiTplByClassIds;
import com.uinnova.test.step_definitions.api.base.kpi.tpl.QueryKpiTplInfoPage;
import com.uinnova.test.step_definitions.api.base.kpi.tpl.SaveClassKpiTplItem;
import com.uinnova.test.step_definitions.api.base.search.ci.SearchFullClassGroup;
import com.uinnova.test.step_definitions.api.base.visualmodeling.QueryImpactPathList;
import com.uinnova.test.step_definitions.api.base.visualmodeling.SaveOrUpdateImpactPath;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.ClassRltUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 
 * 可视化建模
 * 2017-12-04
 * */
public class Scenario_visual {

	private int totalRltCount =0 ;
	private BigDecimal ciClassRltId;

	private BigDecimal classDiagramID; 
	private JSONObject classDiagram;
	private String svgPath;
	private String xmlPath;

	private JSONArray fullClassGrouparray;


	@When("^查询可视化建模$")
	public void queryCiClassDiagramInfoList(){
		QueryCiClassDiagramInfoList queryCiClassDiagramInfoList = new QueryCiClassDiagramInfoList();
		JSONObject result = queryCiClassDiagramInfoList.queryCiClassDiagramInfoList();
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		JSONArray array = result.getJSONArray("data");
		if (array!=null && array.length()>0){
			JSONObject dataObject = array.getJSONObject(0);
			//String svg  = dataObject.getString("svg");
			String xml= dataObject.getString("xml");
			classDiagram = dataObject.getJSONObject("classDiagram");
			classDiagramID = classDiagram.getBigDecimal("id");
			//svgPath = classDiagram.getString("svgPath");
			xmlPath = classDiagram.getString("xmlPath");
		}
	}

	@Then("^成功查询可视化建模$")
	public void checkQueryCiClassDiagramInfoList(){
		String sql = "select ID, DIAGRAM_TYPE, XML_PATH, SVG_PATH from cc_ci_class_diagram where DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		if (list!=null && list.size()>0){
			assertEquals(list.size(), 1);
			HashMap tempObject = (HashMap) list.get(0);
			assertEquals(classDiagramID, tempObject.get("ID"));
			//assertEquals(svgPath, tempObject.get("SVG_PATH").toString());
			assertEquals(xmlPath, tempObject.get("XML_PATH").toString());
		}
	}


	@When("^保存可视化建模$")
	public void saveOrUpdateCiClassDiagramInfo(){
		SaveOrUpdateCiClassDiagramInfo saveOrUpdateCiClassDiagramInfo = new SaveOrUpdateCiClassDiagramInfo();
		JSONObject result = saveOrUpdateCiClassDiagramInfo.saveOrUpdateCiClassDiagramInfo();
	}


	@When("^分组查询对象分类$")
	public void searchFullClassGroup(){
		SearchFullClassGroup searchFullClassGroup = new SearchFullClassGroup();
		JSONObject result = searchFullClassGroup.searchFullClassGroup();
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		fullClassGrouparray = result.getJSONArray("data");
		assertTrue(fullClassGrouparray.length()>0);
	}

	@Then("^成功分组查询对象分类$")
	public void checkSearchFullClassGroup(){
		String sql = "select ID, b.VAL, DIR_ID, IS_LEAF,CI_TYPE,CLASS_LVL,PARENT_ID, CLASS_CODE,CLASS_STD_CODE, CLASS_COLOR,CLASS_NAME, CLASS_PATH,MODIFY_TIME,CLASS_DESC, ICON,CREATOR, CREATE_TIME,MODIFIER from cc_ci_class a," 
				+" (select CLASS_ID, count(1) VAL from CC_CI  where  DATA_STATUS = 1  and DOMAIN_ID="+QaUtil.domain_id+" group by CLASS_ID) b "
				+"where DATA_STATUS=1 and CI_TYPE=1 and a.ID=b.CLASS_ID and DOMAIN_ID="+QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		//assertEquals(list.size() ,fullClassGrouparray.length());//不存在ci的class没有查询到， 需要改进
		Map map = new HashMap();
		for (int i=0; i<list.size(); i++){
			HashMap temp =  (HashMap) list.get(i);
			String classID = temp.get("ID").toString();
			map.put(classID, temp);
		}

		for(int j=0; j<fullClassGrouparray.length(); j++){
			JSONObject temp = fullClassGrouparray.getJSONObject(j);
			JSONObject tempCLs = temp.getJSONObject("cls");
			String classId = String.valueOf(tempCLs.getBigDecimal("id"));
			if (map.containsKey(classId)){//比较
				Map tempMap = (Map) map.get(classId);
				assertEquals(temp.getBigDecimal("ciCount"),new BigDecimal(tempMap.get("VAL").toString()));
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
				assertTrue(temp.getInt("ciCount")==0);
			}

		}
	}

	@Given("^分类\"(.*)\"到分类\"(.*)\"存在关系\"(.*)\"$")
	public void givenCiClassRlt(String sourceClassName, String targetClassName, String rltClassName){
		BigDecimal classRltId = (new ClassRltUtil()).getClassRltId(sourceClassName, targetClassName, rltClassName);
		if (classRltId.compareTo(new BigDecimal(0))<=0){
			SaveOrUpdateCiClassRlt saveOrUpdateCiClassRlt = new SaveOrUpdateCiClassRlt();
			JSONObject result = saveOrUpdateCiClassRlt.saveOrUpdateCiClassRlt(sourceClassName, targetClassName, rltClassName); 
			assertTrue(result.getBoolean("success"));
			ciClassRltId = result.getBigDecimal("data");
			assertTrue(ciClassRltId.compareTo(new BigDecimal(0))>0);
		}
	}

	@When("^新建分类\"(.*)\"到分类\"(.*)\"的关系\"(.*)\"$")
	public void createCiClassRlt(String sourceClassName, String targetClassName, String rltClassName){
		SaveOrUpdateCiClassRlt saveOrUpdateCiClassRlt = new SaveOrUpdateCiClassRlt();
		JSONObject result = saveOrUpdateCiClassRlt.saveOrUpdateCiClassRlt(sourceClassName, targetClassName, rltClassName); 
		assertTrue(result.getBoolean("success"));
		ciClassRltId = result.getBigDecimal("data");
		assertTrue(ciClassRltId.compareTo(new BigDecimal(0))>0);
	}

	@When("^新建失败场景分类\"(.*)\"到分类\"(.*)\"的关系\"(.*)\"$")
	public void createCiClassRltFail(String sourceClassName, String targetClassName, String rltClassName, String kw){
		SaveOrUpdateCiClassRlt saveOrUpdateCiClassRlt = new SaveOrUpdateCiClassRlt();
		JSONObject result = saveOrUpdateCiClassRlt.saveOrUpdateCiClassRltFail(sourceClassName, targetClassName, rltClassName, kw); 
		assertEquals(null, result);
	}

	@Then("^成功新建分类\"(.*)\"到分类\"(.*)\"的关系\"(.*)\"$")
	public void checkAddCiClassRlt(String sourceClassName, String targetClassName, String rltClassName) {
		BigDecimal classRltId = (new ClassRltUtil()).getClassRltId(sourceClassName, targetClassName, rltClassName);
		assertTrue(classRltId.compareTo(new BigDecimal(0))>0);
	}


	@When("^删除分类\"(.*)\"到分类\"(.*)\"的关系\"(.*)\"$")
	public void removeCiClassRltById(String sourceClassName, String targetClassName, String rltClassName) {
		ClassRltUtil clsRltUtil = new ClassRltUtil();
		BigDecimal ciClassRltId = clsRltUtil.getClassRltId(sourceClassName, targetClassName, rltClassName);
		RemoveCiClassRltInfo removeCiClassRltInfo = new RemoveCiClassRltInfo();
		removeCiClassRltInfo.removeCiClassRltInfo(String.valueOf(ciClassRltId)); 
	}


	@Then("^分类关系分类\"(.*)\"到分类\"(.*)\"的关系\"(.*)\"删除成功$")
	public void checkRemoveCiClassRltById(String sourceClassName, String targetClassName, String rltClassName) {
		CiClassUtil ciClassUtil = new CiClassUtil();
		BigDecimal sourceClassId = ciClassUtil.getCiClassId(sourceClassName);
		BigDecimal targetClassId = ciClassUtil.getCiClassId(targetClassName);
		RltClassUtil rltClassUtil = new RltClassUtil();
		BigDecimal rltClassId = rltClassUtil.getRltClassId(rltClassName);
		String sql = "select CLASS_ID, SOURCE_CLASS_ID, TARGET_CLASS_ID from cc_ci_class_rlt where CLASS_ID=" + rltClassId + " and SOURCE_CLASS_ID =" + sourceClassId
				+ " and TARGET_CLASS_ID=" + targetClassId +" and DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id;
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);
		assertEquals(list.size() ,0);
	}

	@When("^查询所有分类关系$")
	public void queryCiClassRlt(){
		QueryCiClassRltList queryCiClassRltList = new QueryCiClassRltList();
		JSONObject result = queryCiClassRltList.queryCiClassRltList(); 
		JSONArray array = result.getJSONArray("data");
		totalRltCount = array.length();
	}


	@Then("^所有分类关系查询正确$")
	public void checkQueryCiClassRlt() {
		String sql = "select ID from cc_ci_class_rlt where  DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id
				+" and CLASS_ID>0  and  SOURCE_CLASS_ID>0  and TARGET_CLASS_ID>0 ";
		ArrayList list = (ArrayList) JdbcUtil.executeQuery(sql);

		if(list.isEmpty()){
			assertEquals(totalRltCount, 0);
		}
		assertEquals(totalRltCount, list.size());
	}


	/******************************CI分类搜索******************************/
	JSONArray searchCiClsDirResult = new JSONArray();
	@When("^按照关键字\"(.*)\"进行CI分类搜索$")
	public void searchCiClass(String keyName){
		GetClassTree getClassTree = new GetClassTree();
		JSONObject searchCiClsObj = getClassTree.getClassTree(keyName);
		searchCiClsDirResult = searchCiClsObj.getJSONArray("data");
	}

	@Then("^成功按照关键字\"(.*)\"进行CI分类搜索$")
	public void checkSearchCiClass(String keyName){
		String sql ="select ID, CLASS_CODE, CLASS_NAME, CLASS_STD_CODE, DIR_ID, CI_TYPE,    CLASS_KIND, PARENT_ID, CLASS_LVL, CLASS_PATH, ORDER_NO, IS_LEAF,    ICON, CLASS_DESC, CLASS_COLOR, DISP_FIELD_IDS, DISP_FIELD_NAMES, COST_TYPE,    LINE_TYPE, LINE_BORDER, LINE_COLOR, LINE_DIRECT, LINE_ANIME, LINE_DISP_TYPE,    LINE_LABEL_ALIGN, DOMAIN_ID, DATA_STATUS, CREATOR, MODIFIER, CREATE_TIME,    MODIFY_TIME     from CC_CI_CLASS     where   CLASS_NAME like '%"+keyName.trim().toUpperCase()+"%' and    CI_TYPE = 1 and DOMAIN_ID = "+QaUtil.domain_id+" and DATA_STATUS = 1 order by  CLASS_NAME  ";
		List resultList = JdbcUtil.executeQuery(sql);
		int classCount =0;
		for (int i=0; i<searchCiClsDirResult.length(); i++){
			JSONObject temp = (JSONObject) searchCiClsDirResult.get(i);
			if (temp.has("cls"))
				classCount+= temp.getJSONArray("cls").length();
		}
		assertEquals("按照关键字进行ci分类搜索， 关键字为："+keyName,classCount, resultList.size());
	}

	/******************************给CI分类挂载指标模板**************************************/
	@When("^给CI分类\"(.*)\"挂载指标模板\"(.*)\"$")
	public void saveClassKpiTplItem(String ciClassName, String tplName){
		SaveClassKpiTplItem saveClassKpiTplItem = new SaveClassKpiTplItem();
		saveClassKpiTplItem.saveClassKpiTplItem(ciClassName, tplName);
	}

	@Then("^成功给CI分类\"(.*)\"挂载指标模板\"(.*)\"$")
	public void checkSaveClassKpiTplItem(String ciClassName, String tplName){
		String sql ="select ID, TPL_ID, RLT_OBJ_TYPE, RLT_OBJ_ID, RLT_OBJ_NAME,KPI_USE_TYPE, DOMAIN_ID, DATA_STATUS, CREATE_TIME, MODIFY_TIME from CC_KPI_TPL_ITEM where data_status=1 and domain_id="+QaUtil.domain_id
				+" and RLT_OBJ_NAME='"+ciClassName.trim()+"' "
				+" and TPL_ID = (select Id from cc_kpi_tpl where tpl_name='"+tplName+"' and data_status=1 and domain_id="+QaUtil.domain_id+" )";
		List resultList = JdbcUtil.executeQuery(sql);
		assertEquals(1, resultList.size());
		Map map = (Map) resultList.get(0);

		QueryKpiTplInfoPage qkip = new QueryKpiTplInfoPage();
		JSONObject result = qkip.queryKpiTplInfoPage(tplName);
		JSONArray arr = result.getJSONObject("data").getJSONArray("data");
		for (int i=0; i<arr.length(); i++){
			JSONObject tempObj = arr.getJSONObject(i);
			JSONObject kpiTplObj = tempObj.getJSONObject("kpiTpl");
			if (tplName.compareToIgnoreCase(kpiTplObj.getString("tplName"))==0){
				JSONArray tplItemsarr = tempObj.getJSONArray("tplItems");
				for (int j = 0; j<tplItemsarr.length(); j++){
					JSONObject tplItemsObj = tplItemsarr.getJSONObject(j);
					if (ciClassName.compareToIgnoreCase(tplItemsObj.getString("rltObjName"))==0){
						assertEquals(tplItemsObj.getBigDecimal("id"),map.get("ID"));
						assertEquals(tplItemsObj.getBigDecimal("tplId"),map.get("TPL_ID"));
						assertEquals(tplItemsObj.getBigDecimal("domainId"),map.get("DOMAIN_ID"));
						assertEquals(tplItemsObj.getBigDecimal("dataStatus"),map.get("DATA_STATUS"));
						assertEquals(tplItemsObj.getBigDecimal("rltObjId"),map.get("RLT_OBJ_ID"));
						assertEquals(tplItemsObj.getBigDecimal("rltObjType"),map.get("RLT_OBJ_TYPE"));
						assertEquals(tplItemsObj.getString("rltObjName"),map.get("RLT_OBJ_NAME"));
						assertEquals(tplItemsObj.getBigDecimal("createTime"),map.get("CREATE_TIME"));
						assertEquals(tplItemsObj.getBigDecimal("modifyTime"),map.get("MODIFY_TIME"));
						break;
					}
				}
				break;
			}
		}

	}

	/******************************给CI分类挂载指标模板**************************************/
	JSONArray kpiTplByClassIds = new JSONArray();
	@When("^查看CI分类\"(.*)\"挂载指标模板信息$")
	public void queryKpiTplByClassIds(String ciClassName){
		QueryKpiTplByClassIds queryKpiTplByClassIds = new QueryKpiTplByClassIds();
		JSONArray arr = new JSONArray();
		arr.put(ciClassName);
		kpiTplByClassIds = queryKpiTplByClassIds.queryKpiTplByClassIds(arr).getJSONArray("data");
	}

	@Then("^CI分类\"(.*)\"挂载指标模板信息中包含\"(.*)\"$")
	public void checkQueryKpiTplByClassIds(String ciClassName, String tplName){
		boolean hasTpl = false;
		for (int i =0; i<kpiTplByClassIds.length(); i++){
			JSONObject tempObj = kpiTplByClassIds.getJSONObject(i);
			JSONObject ciClass = tempObj.getJSONObject("ciClass");
			if (ciClassName.compareToIgnoreCase(ciClass.getString("classCode"))==0){
				JSONArray kpiTpls = tempObj.getJSONArray("kpiTplInfos");
				for (int j=0; j<kpiTpls.length(); j++){
					JSONObject kpiTplsObj = (JSONObject) kpiTpls.get(j);
					if (tplName.compareToIgnoreCase(kpiTplsObj.getJSONObject("kpiTpl").getString("tplName"))==0){
						hasTpl = true;
						break;
					}
				}
				break;
			}
		}
		assertTrue(hasTpl);
	}


	@When("^保存可视化建模中影响分析关系模型热点分类为\"(.*)\"，分类关系如下:$")
	public void saveOrUpdateImpactPath(String focusClassName, DataTable table){
		SaveOrUpdateImpactPath suip = new SaveOrUpdateImpactPath();
		suip.saveOrUpdateImpactPath(focusClassName, table);
	}

	@Then("^成功保存可视化建模中影响分析关系模型热点分类为\"(.*)\"，分类关系如下:$")
	public void checkSaveOrUpdateImpactPath(String focusClassName, DataTable table){
		QueryImpactPathList  qipl = new QueryImpactPathList();
		JSONObject result = qipl.queryImpactPathList(focusClassName);
		assertTrue(result.has("data"));
		JSONArray data = result.getJSONArray("data");
		assertTrue(data.length()>0);
		JSONArray ciClassRltIds  = new JSONArray();
		RltUtil ru = new RltUtil();
		for (int i =1; i<table.raw().size(); i++){
			List<String> row = table.raw().get(i);
			boolean flag = false;
			for (int d=0; d<data.length(); d++){
				JSONObject temp = data.getJSONObject(d);
				BigDecimal rltId =  (new RltClassUtil()).getRltClassId(row.get(3));
				BigDecimal sourceId = (new CiClassUtil()).getCiClassId(row.get(1));
				BigDecimal targetId = (new CiClassUtil()).getCiClassId(row.get(2));
				if (rltId.compareTo(temp.getBigDecimal("rltClassId"))==0 &&
						sourceId.compareTo(temp.getBigDecimal("sourceClassId"))==0 &&
						targetId.compareTo(temp.getBigDecimal("targetClassId"))==0 ){
					flag = true;
					break;
				}
			}
			assertTrue(flag);
		}

	}
}
