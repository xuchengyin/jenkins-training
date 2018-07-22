package com.uinnova.test.step_definitions.utils.dmv;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ci.QueryList;
import com.uinnova.test.step_definitions.api.dmv.ciRlt.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.dmv.comb.SaveOrUpdateCombDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.CollectDiagramByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.MoveDirAndDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryDiagramInfoById;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryMyDirInfoPageByParentId;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDirByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDir;
import com.uinnova.test.step_definitions.api.dmv.diagramVersion.SaveDiagramVersion;
import com.uinnova.test.step_definitions.api.dmv.group.SaveOrUpdateGroupInfo;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * 编写时间:2017-12-5 编写人:sunsl 功能介绍:DMV我的工具类
 */
public class MyUtil {
	/**
	 * 根据文件夹ID取得文件夹信息
	 * 
	 * @param dirId
	 */
	public List getDirList(String dirName) {
		BigDecimal dirId = getDirIdByName(dirName);
		String sql = "SELECT CREATE_TIME,CREATOR,DATA_STATUS,"
				+ "DIR_LVL,DIR_NAME,DIR_PATH,DOMAIN_ID,ID,IS_LEAF,MODIFIER,MODIFY_TIME,PARENT_ID,USER_ID FROM vc_diagram_dir WHERE ID ="
				+ dirId + " AND USER_ID = "+QaUtil.user_id +" AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List dirList = JdbcUtil.executeQuery(sql);
		return dirList;
	}

	/**
	 * 搜索文件夹中的视图,文件夹,组合视图
	 * 
	 * @param dirId
	 * @param like
	 * @param type
	 */
	public JSONObject queryMyDirInfoPageByParentId(String dirName, String like, Integer type) {
		JSONObject result;
		QueryMyDirInfoPageByParentId qmdi = new QueryMyDirInfoPageByParentId();
		JSONObject cdt = new JSONObject();
		cdt.put("dirId", getDirIdByName(dirName));
		cdt.put("like", like);
		cdt.put("type", type);
		result = qmdi.queryMyDirInfoPageByParentId(cdt);
		return result;
	}

	/**
	 * 
	 */
	public BigDecimal getDirIdByName(String dirName) {
		String sql = "SELECT ID FROM vc_diagram_dir WHERE DIR_NAME = '" + dirName + "' AND DATA_STATUS = 1 and DOMAIN_ID = "+ QaUtil.domain_id;
		List dirList = JdbcUtil.executeQuery(sql);
		BigDecimal dirId = new BigDecimal(0);
		if (dirList != null && dirList.size() > 0) {
			HashMap dirHashMap = (HashMap) dirList.get(0);
			dirId = (BigDecimal) dirHashMap.get("ID");
		}
		return dirId;
	}

	/**
	 * @param diagramName
	 * @param dirName
	 * @return result
	 */
	public JSONObject moveDirAndDiagram(String diagramName, String dirName) {
		MoveDirAndDiagram md = new MoveDirAndDiagram();
		MyUtil myUtil = new MyUtil();
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONObject param = new JSONObject();
		JSONArray dirIds = new JSONArray();
		JSONArray diagramIds = new JSONArray();
		diagramIds.put(diagramUtil.getDiagramIdByName(diagramName));
		JSONObject result = md.moveDirAndDiagram(dirIds, diagramIds, dirName);
		return result;
	}

	/**
	 * @param dirName
	 * @return result
	 */
	public JSONObject deleteDir(String dirName) {
		RemoveDirByIds rd = new RemoveDirByIds();
		JSONArray dirIds = new JSONArray();
		JSONArray diagramIds = new JSONArray();
		dirIds.put(getDirIdByName(dirName));
		JSONObject result = rd.removeDirByIds(dirIds, diagramIds);
		return result;
	}

	/**
	 * @param srcDirName
	 * @param targetDirName
	 * @return result
	 */
	public JSONObject moveDir(String srcDirName, String targetDirName) {
		MyUtil myUtil = new MyUtil();
		SaveOrUpdateDir su = new SaveOrUpdateDir();
		JSONObject result = su.saveOrUpdateDir(targetDirName);
		//BigDecimal moveDirId = result.getBigDecimal("data");
		BigDecimal moveDirId = myUtil.getDirIdByName(srcDirName);
		MoveDirAndDiagram md = new MoveDirAndDiagram();
		JSONArray dirIds = new JSONArray();
		dirIds.put(moveDirId);
		JSONArray diagramIds = new JSONArray();
		//result = md.moveDirAndDiagram(dirIds, diagramIds, srcDirName);
		result = md.moveDirAndDiagram(dirIds, diagramIds, targetDirName);
		return result;
	}

	/**
	 * @param dirName
	 * @param searchKey
	 * @return result
	 */
	public JSONObject searchLikeDiagram(String dirName, String searchKey) {
		QueryMyDirInfoPageByParentId qmdi = new QueryMyDirInfoPageByParentId();
		JSONObject cdt = new JSONObject();
		cdt.put("dirId", getDirIdByName(dirName));
		cdt.put("like", searchKey);
		JSONObject result = qmdi.queryMyDirInfoPageByParentId(cdt);
		return result;
	}

	/**
	 * @param diagramName
	 * @param combName
	 * @Param dirName
	 * @return result
	 */
	public JSONObject createCombDiagram(String diagramName, String combName, String dirName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		JSONObject diagramInfo = new JSONObject();
		JSONArray combDiagrams = new JSONArray();
		JSONObject combDiagram = new JSONObject();
		JSONObject diagram = new JSONObject();
		combDiagram.put("diagramId", diagramUtil.getDiagramIdByName(diagramName));
		combDiagram.put("px", 1);
		combDiagram.put("py", 1);
		combDiagram.put("direct", 0);
		combDiagrams.put(combDiagram);
		diagramInfo.put("combDiagrams", combDiagrams);
		diagram.put("dirId", getDirIdByName(dirName));
		diagram.put("isOpen", 0);
		diagram.put("diagramDesc", "测试组合视图");
		diagram.put("status", 1);
		diagram.put("name", combName);
		diagram.put("diagramType", 2);
		diagram.put("combCols", 3);
		diagram.put("combRows", 3);
		diagramInfo.put("diagram", diagram);
		JSONArray tags = new JSONArray();
		diagramInfo.put("tags", tags);
		SaveOrUpdateCombDiagram su = new SaveOrUpdateCombDiagram();
		JSONObject result = su.saveOrUpdateCombDiagram(diagramInfo);
		return result;
	}

	/**
	 * @param combName
	 * @return result
	 */
	public JSONObject deleteComb(String combName) {
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal combId = diagramUtil.getCombIdByName(combName);
		JSONArray dirIds = new JSONArray();
		JSONArray diagramIds = new JSONArray();
		diagramIds.put(combId);
		RemoveDirByIds rd = new RemoveDirByIds();
		JSONObject result = rd.removeDirByIds(dirIds, diagramIds);
		return result;
	}

	/***
	 * @param diagramName
	 * @param versionDesc
	 * @return result
	 */
	public JSONObject saveDiagramVersion(String diagramName, String versionDesc,String versionNo) {
		QueryDiagramInfoById qd = new QueryDiagramInfoById();
		JSONObject result = qd.queryDiagramInfoById(diagramName, true);
		JSONObject data = result.getJSONObject("data");
		String ci3dPoint = data.getString("ci3dPoint");
		String xml = data.getString("xml");
		JSONObject diagram = data.getJSONObject("diagram");
		SaveDiagramVersion sd = new SaveDiagramVersion();
		JSONArray diagramEles = new JSONArray();
		result = sd.saveDiagramVersion(ci3dPoint, diagram, diagramEles, xml, versionDesc,versionNo);
		return result;
	}

	public JSONArray createDupGroup(List<String> groupNameList, String groupDesc) {
		JSONArray groupIdArray = new JSONArray();
		for (int i = 0; i < groupNameList.size(); i++) {
			String groupName = groupNameList.get(i);
			SaveOrUpdateGroupInfo su = new SaveOrUpdateGroupInfo();
			JSONObject result = su.saveOrUpdateGroupInfo(groupName, groupDesc);
			assertTrue(result.getBoolean("success"));
			String sql = "SELECT ID,GROUP_NAME,GROUP_DESC FROM vc_group WHERE GROUP_NAME = '" + groupName
					+ "' AND GROUP_DESC = '" + groupDesc + "' AND DATA_STATUS =1 and DOMAIN_ID = "+ QaUtil.domain_id;
			ArrayList list = JdbcUtil.executeQuery(sql);
			if (list.size() == 1) {
				HashMap groupMap = (HashMap) list.get(0);
				BigDecimal groupId = (BigDecimal) groupMap.get("ID");
				groupIdArray.put(groupId);
			}
		}
		return groupIdArray;
	}
	
	public JSONObject addDiagramRlt(String sourceCode,String targerCode,String rltClassName){
		SaveOrUpdate  saveOrUpdate = new SaveOrUpdate();
		JSONObject result = saveOrUpdate.saveOrUpdate(rltClassName, sourceCode, targerCode);
		return result;
	}
	
	public JSONObject collectDiagramByIds(List diagramNameList){
		JSONArray diagramIds = new JSONArray();
		DiagramUtil diagramUtil = new DiagramUtil();
		for(int i = 0; i < diagramNameList.size(); i++){
			String diangramName = (String)diagramNameList.get(i);
			BigDecimal diagramId = diagramUtil.getDiagramIdByName(diangramName);
			diagramIds.put(diagramId);
		}
		CollectDiagramByIds collectDiagramByIds = new CollectDiagramByIds();
		JSONObject result = collectDiagramByIds.collectDiagramByIds(diagramIds);
		return result;
	}
	
	public JSONObject queryList(List ciCodeList){
		  JSONObject result = new JSONObject();
		  QueryList queryList = new QueryList();
		   JSONArray ciQ = new JSONArray();
		   JSONArray ciCodes = new JSONArray();
		   ciQ.put("ATTR");
		   ciQ.put("CLASS");
		   for(int i = 0; i<ciCodeList.size();i++){
			   ciCodes.put(ciCodeList.get(i));
		   }
		   result = queryList.queryList(ciQ, ciCodes);
		   return result;
	}
	/***
	 * @param diagramName
	 * @param upRelation
	 * @return result
	 */
	public JSONObject saveDiagramVersion2(String diagramName, String upRelation) {
		QueryDiagramInfoById qd = new QueryDiagramInfoById();
		JSONObject result = qd.queryDiagramInfoById(diagramName, true);
		JSONObject data = result.getJSONObject("data");
		String ci3dPoint = data.getString("ci3dPoint");
		String xml = data.getString("xml");
		JSONObject diagram = data.getJSONObject("diagram");
		SaveDiagramVersion sd = new SaveDiagramVersion();
		JSONArray diagramEles = new JSONArray();
		result = sd.saveDiagramVersion(ci3dPoint, diagram, diagramEles, xml, "",upRelation,"");
		return result;
	}
}
