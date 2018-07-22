package com.uinnova.test.step_definitions.testcase.cmv.ciRltRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ci.QueryPage;
import com.uinnova.test.step_definitions.api.base.ci.QueryPageByIndex;
import com.uinnova.test.step_definitions.api.base.kpi.tpl.QueryKpiTplInfoByCiIds;
import com.uinnova.test.step_definitions.api.cmv.ciClass.QueryListHasCiCount;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.QueryFuzzyMatchPathInCis;
import com.uinnova.test.step_definitions.api.cmv.ciRlt.QueryReversalRcaByCiId;
import com.uinnova.test.step_definitions.api.cmv.ciRltRule.QueryFrientByCiIdAndPaths;
import com.uinnova.test.step_definitions.api.cmv.ciRltRule.QueryNoStartFrientByDef;
import com.uinnova.test.step_definitions.api.cmv.ciRltRule.QueryRltRuleDefListHasClass;
import com.uinnova.test.step_definitions.api.cmv.ciRltRule.RemoveDefById;
import com.uinnova.test.step_definitions.api.cmv.ciRltRule.SaveOrUpdateDefAndRule;
import com.uinnova.test.step_definitions.api.cmv.monitor.event.QueryEventSimple;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryFriendByDef;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.base.ClassRltUtil;
import com.uinnova.test.step_definitions.utils.base.RltClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltUtil;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.CiRltUtil;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 
 *         影响分析 2018-1-20
 */
public class Scenario_CiRltRule {
	
	private static List<String> ciRltRuleList = new ArrayList<String>();
	JSONObject result;
	JSONObject queryFrientPathsResult;
	JSONObject FuzzyMatchPath;
	JSONObject ReversalRca;
	@After("@delCiRltRule")
	public void delCiRltRule() {
		if (!ciRltRuleList.isEmpty()) {
			for (int i = 0; i < ciRltRuleList.size(); i++) {
				String defName = ciRltRuleList.get(i);
				RemoveDefById removeDefById = new RemoveDefById();
				JSONObject result = removeDefById.removeDefById(defName);
				assertTrue(result.getBoolean("success"));
				ciRltRuleList.remove(defName);
				i--;
			}
		}
	}

	/******************************** 新建朋友圈规则 ***************************************/
	@When("新建\"(.*)\"到\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"$")
	public void addDefAndRuleHasEntrance(String sourceClsName, String targetClsName, String rltClsName, String defName)
			throws DocumentException {
		SaveOrUpdateDefAndRule saveOrUpdateDefAndRule = new SaveOrUpdateDefAndRule();
		JSONObject result = saveOrUpdateDefAndRule.saveOrUpdateDefAndRuleHasEntrance(sourceClsName, targetClsName,
				rltClsName, defName);
		assertTrue(result.getBoolean("success"));
		ciRltRuleList.add(defName);
	}

	@Then("成功新建\"(.*)\"到\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"$")
	public void checkAddDefAndRuleHasEntrance(String sourceClsName, String targetClsName, String rltClsName,
			String defName) throws DocumentException {
		String sql = "select ID from CC_RLT_RULE_DEF where DATA_STATUS=1 and DOMAIN_ID =" + QaUtil.domain_id
				+ " and DEF_name ='" + defName + "'";
		List l = JdbcUtil.executeQuery(sql);
		assertEquals(1, l.size());
	}

	@When("新建\"(.*)\"和\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"$")
	public void addDefAndRuleNoEntrance(String sourceClsName, String targetClsName, String rltClsName, String defName)
			throws DocumentException {
		SaveOrUpdateDefAndRule saveOrUpdateDefAndRule = new SaveOrUpdateDefAndRule();
		JSONObject result = saveOrUpdateDefAndRule.saveOrUpdateDefAndRuleNoEntrance(sourceClsName, targetClsName,
				rltClsName, defName);
		assertTrue(result.getBoolean("success"));
		ciRltRuleList.add(defName);
	}

	@Then("成功新建\"(.*)\"和\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"$")
	public void checkAddDefAndRuleNoEntrance(String sourceClsName, String targetClsName, String rltClsName,
			String defName) throws DocumentException {
		String sql = "select ID from CC_RLT_RULE_DEF where DATA_STATUS=1 and DOMAIN_ID =" + QaUtil.domain_id
				+ " and DEF_name ='" + defName + "'";
		List l = JdbcUtil.executeQuery(sql);
		assertEquals(1, l.size());
	}

	@When("删除朋友圈规则\"(.*)\"$")
	public void deleteDefAndRule(String defName) throws DocumentException {
		RemoveDefById removeDefById = new RemoveDefById();
		JSONObject result = removeDefById.removeDefById(defName);
		assertTrue(result.getBoolean("success"));
		ciRltRuleList.remove(defName);
	}

	@Then("成功删除朋友圈规则\"(.*)\"$")
	public void checkDeleteDefAndRule(String defName) throws DocumentException {
		String sql = "select ID from CC_RLT_RULE_DEF where DATA_STATUS=1 and DOMAIN_ID =" + QaUtil.domain_id
				+ " and DEF_name ='" + defName + "'";
		List l = JdbcUtil.executeQuery(sql);
		assertEquals(0, l.size());
	}

	@When("^修改\"(.*)\"到\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"为修改后朋友圈规则\"(.*)\"$")
	public void updateDefAndRuleHasEntrance(String sourceClsName, String targetClsName, String rltClsName,
			String defName, String updateDefName) {
		SaveOrUpdateDefAndRule saveOrUpdateDefAndRule = new SaveOrUpdateDefAndRule();
		JSONObject result = saveOrUpdateDefAndRule.saveOrUpdateDefAndRuleHasEntrance(sourceClsName, targetClsName,
				rltClsName, defName, updateDefName);
		assertTrue(result.getBoolean("success"));
		ciRltRuleList.remove(defName);
		ciRltRuleList.add(updateDefName);
	}

	@Then("^成功修改\"(.*)\"到\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"为修改后朋友圈规则\"(.*)\"$")
	public void checkUpdateDefAndRuleHasEntrance(String sourceClsName, String targetClsName, String rltClsName,
			String defName, String updateDefName) {
		String sql = "select ID from CC_RLT_RULE_DEF where DATA_STATUS = 1  and DEF_TYPE = 1 and DOMAIN_ID ="
				+ QaUtil.domain_id + " and DEF_NAME = '" + updateDefName + "'";
		List list = JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());
	}

	/* ==============Scenario Outline: Image_画布左侧菜单_规则查询==================== */
	@When("^查询在朋友圈规则\"(.*)\"下的CiCode为\"(.*)\"的朋友圈$")
	public void queryFriendByDef(String friendName, String ciCode) throws InterruptedException {
		QueryFriendByDef queryFriendByDef = new QueryFriendByDef();
		Thread.sleep(3000);
		result = queryFriendByDef.queryFriendByDef(friendName, ciCode);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功查询在朋友圈规则\"(.*)\"下的CiCode为\"(.*)\"的朋友圈$")
	public void checkQueryFriendByDef(String friendName, String ciCode) {
		CiUtil ciUtil = new CiUtil();
		BigDecimal id = ciUtil.getCiId(ciCode);
		CiRltUtil crlu = new CiRltUtil();
		BigDecimal friendDefId = crlu.getDefIdByDefName(friendName);
		String sql = "SELECT SOURCE_CI_ID,SOURCE_CI_CODE, TARGET_CI_ID, TARGET_CI_CODE FROM cc_ci_rlt WHERE domain_id = "
				+ QaUtil.domain_id + " AND data_status = 1 AND source_ci_id IN "
				+ "(SELECT id FROM cc_ci WHERE domain_id = " + QaUtil.domain_id + " AND data_status = 1 AND id = " + id
				+ " AND class_id IN " + "(SELECT CLS_START_ID FROM CC_RLT_LINE WHERE domain_id = " + QaUtil.domain_id
				+ " AND data_status = 1 AND def_id = " + friendDefId + ")) "
				+ "AND target_class_id IN (SELECT CLS_END_ID FROM CC_RLT_LINE WHERE domain_id = "+ QaUtil.domain_id+" AND data_status = 1 AND def_id = "
				+ friendDefId + ")";

		List list = JdbcUtil.executeQuery(sql);
		HashMap map = new HashMap();
		if (list != null && list.size() > 0) {
			map = (HashMap) list.get(0);
		}
		JSONObject data = result.getJSONObject("data");
		JSONArray ciRlts = data.getJSONArray("ciRlts");
		String sourceCiCode = "";
		String targetCiCode = "";
		BigDecimal sourceCiId = new BigDecimal(0);
		BigDecimal targetCiId = new BigDecimal(0);
		if (ciRlts.length() > 0) {
			JSONObject obj = (JSONObject) ciRlts.get(0);
			JSONObject ciRlt = obj.getJSONObject("ciRlt");
			sourceCiCode = ciRlt.getString("sourceCiCode");
			targetCiCode = ciRlt.getString("targetCiCode");
			sourceCiId = ciRlt.getBigDecimal("sourceCiId");
			targetCiId = ciRlt.getBigDecimal("targetCiId");
		}
        if(map.get("SOURCE_CI_CODE") !=null && map.get("TARGET_CI_CODE")!= null 
        		&& map.get("SOURCE_CI_ID") !=null && map.get("TARGET_CI_ID")!=null){
		   assertEquals(map.get("SOURCE_CI_CODE"), sourceCiCode);
		   assertEquals(map.get("TARGET_CI_CODE"), targetCiCode);
		   assertEquals((BigDecimal) map.get("SOURCE_CI_ID"), sourceCiId);
		   assertEquals((BigDecimal) map.get("TARGET_CI_ID"), targetCiId);
		}
	}
	
    /* =====Scenario Outline: Image_画布左侧菜单_规则查询空特殊字符测试======= */
	@And("^查询在朋友圈规则\"(.*)\"下的CiCode为空,特殊字符\"(.*)\"的朋友圈,kw=\"(.*)\"$")
	public void queryFriendByDefNull(String friendName,String searchKey, String kw){
		QueryFriendByDef queryFriendByDef = new QueryFriendByDef();
		result = queryFriendByDef.queryFriendByDefNull(friendName, searchKey, kw);
		assertEquals(null,result);
	}
	
	/* =====Scenario Outline: CiRltRule_无源朋友圈视图规则增改删========= */
	@When("^修改\"(.*)\"和\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"为修改后朋友圈规则\"(.*)\"$")
	public void updateDefAndRuleNoEntrance(String sourceClsName, String targetClsName, String rltClsName,
			String defName, String updatefriendName) {
		SaveOrUpdateDefAndRule saveOrUpdateDefAndRule = new SaveOrUpdateDefAndRule();
		result = saveOrUpdateDefAndRule.saveOrUpdateDefAndRuleNoEntrance(sourceClsName, targetClsName, rltClsName,
				defName, updatefriendName);
		assertTrue(result.getBoolean("success"));
		ciRltRuleList.remove(defName);
		ciRltRuleList.add(updatefriendName);
	}

	@Then("^成功修改\"(.*)\"和\"(.*)\"关系为\"(.*)\"的朋友圈规则\"(.*)\"为修改后朋友圈规则\"(.*)\"$")
	public void checkUpdateDefAndRuleNoEntrance(String sourceClsName, String targetClsName, String rltClsName,
			String defName, String updatefriendName) {
		String sql = "select ID from CC_RLT_RULE_DEF where DATA_STATUS = 1 and DEF_TYPE = 2 and DOMAIN_ID ="
				+ QaUtil.domain_id + " and DEF_NAME = '" + updatefriendName + "'";
		List list = JdbcUtil.executeQuery(sql);
		assertEquals(1, list.size());
	}

	/* ===============Scenario Outline: CiRltRule_搜索朋友圈规则========= */
	@When("^搜索关键字为\"(.*)\"的朋友圈规则$")
	public void queryRltRuleDefListHasClass(String searchKey) {
		QueryRltRuleDefListHasClass queryRltRuleDefListHasClass = new QueryRltRuleDefListHasClass();
		result = queryRltRuleDefListHasClass.queryRltRuleDefListHasClass("%" + searchKey + "%");
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功搜索关键字为\"(.*)\"的朋友圈规则$")
	public void checkQueryRltRuleDefListHasClass(String searchKey) {
		String sql = "SELECT ID , DEF_TYPE,DEF_NAME From cc_rlt_rule_def Where DEF_NAME like '%" + searchKey
				+ "%' AND DATA_STATUS=1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		assertEquals(list.size(), data.length());
	}

	/* =========Scenario Outline: CiRltRule_搜索CI分类======== */
	@When("^搜索关键字为\"(.*)\"的CI分类$")
	public void queryListHasCiCount(String searchKey) {
		QueryListHasCiCount queryListHasCiCount = new QueryListHasCiCount();
		result = queryListHasCiCount.queryListHasCiCount(searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功搜索关键字为\"(.*)\"的CI分类$")
	public void checkQueryListHasCiCount(String searchKey) {
		String sql = "SELECT ID,CLASS_ID FROM cc_ci WHERE CLASS_ID in (SELECT ID From cc_ci_class WHERE CLASS_STD_CODE like '%"
				+ searchKey + "%' AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id
				+ ") AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			JSONObject obj = (JSONObject) data.get(i);
			assertEquals(list.size(), obj.getInt("ciCount"));
			JSONObject ciClass = obj.getJSONObject("ciClass");
			BigDecimal id = ciClass.getBigDecimal("id");
			HashMap map = (HashMap) list.get(i);
			assertEquals((BigDecimal) map.get("CLASS_ID"), id);
		}
	}

	/* ============Scenario Outline:CiRltRule_搜索CI========= */
	@When("^搜索CI分类为\"(.*)\"关键字为\"(.*)\"的CI$")
	public void queryPageByIndex_ciByKeyword(String className, String searchKey) {
		QueryPageByIndex queryPageByIndex = new QueryPageByIndex();
		result = queryPageByIndex.queryPageByIndex_ciByKeyword(className, searchKey);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功搜索CI分类为\"(.*)\"关键字为\"(.*)\"的CI$")
	public void checkQueryPageByIndex_ciByKeyword(String className, String searchKey) {
		CiClassUtil cc = new CiClassUtil();
		BigDecimal classId = cc.getCiClassId(className);
		String sql = "SELECT CI_CODE,ID, CLASS_ID FROM cc_ci Where CLASS_ID =" + classId + " AND CI_CODE like '%"
				+ searchKey + "%' AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		List list = JdbcUtil.executeQuery(sql);
		HashMap map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			HashMap dbMap = (HashMap) list.get(i);
			map.put((BigDecimal) dbMap.get("ID"), dbMap);
		}
		JSONArray data = result.getJSONObject("data").getJSONArray("data");
		assertEquals(list.size(), data.length());
		for (int i = 0; i < data.length(); i++) {
			JSONObject obj = (JSONObject) data.get(i);
			JSONObject ci = obj.getJSONObject("ci");
			HashMap ciMap = (HashMap) map.get(ci.getBigDecimal("id"));
			assertEquals(ciMap.get("CI_CODE"), ci.getString("ciCode"));
			assertEquals((BigDecimal) ciMap.get("CLASS_ID"), ci.getBigDecimal("classId"));
		}
	}

	/* ===========Scenario: CiRltRule_故障诊断========= */
	@When("^给CI为\"(.*)\"做故障诊断$")
	public void QueryEventSimple(@Delimiter(",") List<String> codeList) {
		QueryEventSimple queryEventSimple = new QueryEventSimple();
		result = queryEventSimple.queryEventSimple(codeList);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功为CI\"(.*)\"做故障诊断$")
	public void checkQueryEventSimple(@Delimiter(",") List<String> codeList) {
		JSONArray data = result.getJSONArray("data");
		for (int i = 0; i < codeList.size(); i++) {
			String sql = "SELECT CINAME From mon_eap_event_memory WHERE CINAME = '" + codeList.get(i) + "'";
			List list = JdbcUtil.executeQuery(sql);
			int count = 0;
			for (int j = 0; j < data.length(); j++) {
				JSONObject obj = (JSONObject) data.get(j);
				if (obj.getString("ciCode").equals(codeList.get(i))) {
					count = obj.getInt("count");
				}
			}
			assertEquals(list.size(), count);
		}
	}

	/* ===============Scenario: CiRltRule_无源朋友圈规则_CI关系======= */
	@When("^查询朋友圈规则\"(.*)\"的CI关系$")
	public void queryNoStartFrientByDef(String firendDefName) {
		QueryNoStartFrientByDef queryNoStartFrientByDef = new QueryNoStartFrientByDef();
		result = queryNoStartFrientByDef.queryNoStartFrientByDef(firendDefName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功查询朋友圈规则\"(.*)\"的CI关系$")
	public void checkQueryNoStartFrientByDef(String firendDefName) {
		JSONObject data = result.getJSONObject("data");
		//JSONArray ciClassInfos = data.getJSONArray("ciClassInfos");
		JSONArray friends = data.getJSONArray("friends");
		if (friends.length() > 0) {
			assertTrue(true);
		} else {
			fail();
		}
	}
	
	@When("^分类\"(.*)\"中的所有CI监控指标模板$")
	public void ciKpiTpl(String className){
		QueryKpiTplInfoByCiIds queryKpiTplInfoByCiIds = new QueryKpiTplInfoByCiIds();
		JSONArray ciCodes = new JSONArray();
		QueryPage queryPage = new QueryPage();
		JSONObject allClsCI = queryPage.queryPage(className);
		assertTrue(allClsCI.has("data"));
		allClsCI = allClsCI.getJSONObject("data");
		assertTrue(allClsCI.has("data"));
		JSONArray allClsCiData = allClsCI.getJSONArray("data");
		assertNotNull(allClsCiData);
		assertTrue(allClsCiData.length()>0);
		for (int i=0; i<allClsCiData.length(); i++){
			JSONObject ciObj = allClsCiData.getJSONObject(i);
			ciCodes.put(ciObj.getJSONObject("ci").getString("ciCode"));
			//queryFrientByDef
			JSONObject ciKpiTplresult = queryKpiTplInfoByCiIds.queryKpiTplInfoByCiCodes(ciCodes);
			assertTrue(ciKpiTplresult.has("data"));
			JSONArray kpiTplData = ciKpiTplresult.getJSONArray("data");
			assertNotNull(kpiTplData);
			assertTrue(kpiTplData.length()>0);
			for (int k =0; k<kpiTplData.length(); k++){
				JSONObject ciKpiTplObj = kpiTplData.getJSONObject(k);
				assertTrue(ciKpiTplObj.has("ci"));
				assertTrue(ciKpiTplObj.has("kpiTplInfos"));
				
			}
		}
		
	}
	
	@Then("^查询如下CI关系路径:$")
	public void queryFrientPaths(DataTable dt){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QueryFrientByCiIdAndPaths qfbciap = new QueryFrientByCiIdAndPaths();
		RltClassUtil rcu = new RltClassUtil();
		ClassRltUtil cru = new ClassRltUtil();
		CiClassUtil ccu = new CiClassUtil();
		RltUtil ru = new RltUtil();
		List<List<String>> list = dt.raw();
		String startCiCode = list.get(1).get(0);
		String endCiCode = list.get(1).get(1);
		//组装ciClassRltPaths/不用全部组装，api中组装，这里只需要把rltpath传过去就行
		JSONArray ciClassRltPaths = new JSONArray();
		for(int i = 1; i < list.size(); i++){
			ArrayList temp = new ArrayList();
			JSONObject ciClassRltIds = new JSONObject();
			String tempRltPath = list.get(i).get(2);
			String [] rltPathList = tempRltPath.split("=");
			for(int j = 0; j < rltPathList.length; j++){
				String sourceCiClassName;
				String targetCiClassName;
				String rltClassName;
//				for(int n = 0; n < rltPathList[j].length(); n++){
				String [] detail = rltPathList[j].split(":");
				rltClassName = detail[0];
				sourceCiClassName = detail[1];
				targetCiClassName = detail[2];
//				}
				temp.add(cru.getClassRltId(sourceCiClassName, targetCiClassName, rltClassName).toString());
			}
			ciClassRltIds.put("ciClassRltIds", temp);
			ciClassRltPaths.put(ciClassRltIds);
		}
		queryFrientPathsResult = qfbciap.queryFrientByCiIdAndPaths(startCiCode, endCiCode, ciClassRltPaths);
		assertTrue(queryFrientPathsResult.getJSONObject("data").getJSONArray("ciRltLines").length() > 0);
		assertTrue(queryFrientPathsResult.getBoolean("success"));
	}
	@Then("^验证CI关系路径查询结果:$")
	public void compRltPath(DataTable dt){
		ClassRltUtil cru = new ClassRltUtil();
		RltClassUtil rcu = new RltClassUtil();
		RltUtil ru = new RltUtil();
		CiClassUtil ccu = new CiClassUtil();
		CiUtil cu = new CiUtil();
//		|sourceClassName|targetClassName|sourceCiCode|targetCiCode|RltClassName|
//		|s@&_-|Application|six|400网关1|ciRltRule测试|
		JSONArray queryFrientPathsResultArray = queryFrientPathsResult.getJSONObject("data").getJSONArray("ciRltLines");
		List<List<String>> list = dt.raw();
		for(int i = 1; i < list.size(); i++){
			BigDecimal sourceClassId = ccu.getCiClassId(list.get(i).get(0));
			BigDecimal targetClassId = ccu.getCiClassId(list.get(i).get(1));
			String sourceCiCode = list.get(i).get(2);
			String targetCiCode = list.get(i).get(3);
			String RltClassName = list.get(i).get(4);
//			BigDecimal RltClassId = cru.getClassRltId(list.get(i).get(0), list.get(i).get(1), RltClassName);
			BigDecimal RltClassId = rcu.getRltClassId(RltClassName);
			boolean b = false;
			for(int j = 0; j <queryFrientPathsResultArray.length(); j++ ){
				JSONObject temp = queryFrientPathsResultArray.getJSONObject(j);
				if(temp.getBigDecimal("classId").equals(RltClassId) && temp.getBigDecimal("sourceClassId").equals(sourceClassId) && temp.getBigDecimal("targetClassId").equals(targetClassId) && temp.getString("sourceCiCode").equals(sourceCiCode) && temp.getString("targetCiCode").equals(targetCiCode)){
					b = true;
					break;
				}
			}
			assertTrue(b);
		}
	}
	
	@When("^用以下内容搜索网络寻路:$")
	public void queryFuzzyMatchPath(DataTable dt){
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<List<String>> list = dt.raw();
		List<String> param = list.get(1);
		QueryFuzzyMatchPathInCis qfmpic = new QueryFuzzyMatchPathInCis();
		FuzzyMatchPath = qfmpic.queryFuzzyMatchPathInCis(Arrays.asList(param.get(0).split(":")), Arrays.asList(param.get(1).split(":")), Arrays.asList(param.get(2).split(":")), Arrays.asList(param.get(3).split(":")), Integer.parseInt(param.get(4)));

		
	}
	
	@Then("^用以下内容验证网络寻路:$")
	public void compFuzzyMatchPath(DataTable dt){
		JSONArray array = FuzzyMatchPath.getJSONObject("data").getJSONArray("ciRltLines");
		RltUtil ru = new RltUtil();
		ClassRltUtil cru = new ClassRltUtil();
		RltClassUtil rcu = new RltClassUtil();
		
//		CiUtil cu = new CiUtil();
		CiClassUtil ccu = new CiClassUtil();
		List<List<String>> list = dt.raw();
		for(int i = 1; i < list.size(); i++){
			boolean boo = false;
			String srourceCiCode = list.get(i).get(0);
			String rltName = list.get(i).get(1);
			String targetCiCode = list.get(i).get(2);
//			BigDecimal rltId = cru.getClassRltId(rltName, ccu.getCiClassNameByCiCode(srourceCiCode), ccu.getCiClassNameByCiCode(targetCiCode));
			BigDecimal rltId = rcu.getRltClassId(rltName);
			for(int j = 0; j< array.length(); j++){
				JSONObject tempObj = array.getJSONObject(j);
				QaUtil.log("验证网络寻路log: "+rltId+"<---->"+tempObj.getBigDecimal("classId")+"---"+targetCiCode+"<---->"+tempObj.getString("targetCiCode")+"---"+srourceCiCode+"<---->"+tempObj.getString("sourceCiCode"));
				if(rltId.equals(tempObj.getBigDecimal("classId"))&&targetCiCode.equals(tempObj.getString("targetCiCode"))&&srourceCiCode.equals(tempObj.getString("sourceCiCode"))){
					boo = true;
					break;
				}
			}
			assertTrue(boo);
		}
		
	}
	
	@When("^查询ciCode为\"(.*)\",层级为\"(.*)\"的影响分析$")
	public void queryReversalRcaByCiCode(String ciCode, String depth){
		QueryReversalRcaByCiId qrrbci = new QueryReversalRcaByCiId();
		ReversalRca = qrrbci.queryReversalRcaByCiId(ciCode, depth);
	}
	
	@When("^用以下数据验证影响分析查询结果:$")
	public void compReversalRca(DataTable dt){
		List<List<String>> list = dt.raw();
		RltClassUtil rcu = new RltClassUtil();
		JSONArray ciRltLines = ReversalRca.getJSONObject("data").getJSONArray("ciRltLines");
		for(int i = 0; i < ciRltLines.length(); i++){
			JSONObject tempObj = ciRltLines.getJSONObject(i);
			boolean boo = false;
			for(int j = 1; j < list.size(); j++){
				String rltName = list.get(j).get(0);
				BigDecimal rltClassId = rcu.getRltClassId(rltName);
				String sourceCiCode = list.get(j).get(1);
				String targetCiCode = list.get(j).get(2);
				if(tempObj.getBigDecimal("classId").equals(rltClassId)&&tempObj.getString("sourceCiCode").equals(sourceCiCode)&&tempObj.getString("targetCiCode").equals(targetCiCode)){
					boo = true;
				}
			}
			assertTrue(boo);
		}
		
	}
}
