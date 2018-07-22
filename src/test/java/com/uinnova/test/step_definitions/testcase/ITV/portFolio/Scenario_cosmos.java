package com.uinnova.test.step_definitions.testcase.ITV.portFolio;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.text.View;

import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.relaxng.datatype.helpers.StreamingValidatorImpl;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.ITV.cirel.QueryOneUpAndDown;
import com.uinnova.test.step_definitions.api.ITV.dcModelItem.Query3DByCiCodesAndImgNames;
import com.uinnova.test.step_definitions.api.ITV.portFolio.Cosmos;
import com.uinnova.test.step_definitions.api.ITV.view.GetViewPointsForDcvByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDiagram;
import com.uinnova.test.step_definitions.api.dmv.diagram.ShareDiagram;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.CombUtil;
import com.uinnova.test.step_definitions.utils.itv.CiAttr;
import com.uinnova.test.step_definitions.utils.itv.ItvView;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_cosmos{
	
	private JSONObject result; 
	private JSONObject viewInfo;
	private String[] ciIDs;//存放视图CI的ID
	private ArrayList<String> imageName = new ArrayList<>(); //存放图标名称
	private ArrayList viewIds = new ArrayList(); //存放组合视图ID
	private ArrayList<String> diagramNameList = new ArrayList<String>(); // 用于记录一共新建了多少视图
	private ArrayList<String> ciNameList = new ArrayList<String>();  //存放视图中CI的名称

	@When("^组合视图为空进入逻辑宇宙$")
	public void getView() {
		Cosmos viewList = new Cosmos();
		result = viewList.getViewList();
		assertTrue(result.getBoolean("success"));
	}

	@Then("^没有组合视图$")
	public void checkViewNull() {
		String sql = "select NAME from VC_DIAGRAM where is_open=1 and"
				+ " DIAGRAM_TYPE = 2 and STATUS = 1 AND DATA_STATUS = 1 and DOMAIN_ID =" + QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		// 不存在组合视图
		assertTrue(list.size() == 0);

	}


	@When("^创建名称为\"(.*)\"组合视图并加入CI:$")
	public JSONObject createViews(String viNmae, DataTable viewLi) {
		ItvView view = new ItvView();
		for(int i = 1; i < viewLi.raw().size(); i++){
			String vieName  = viewLi.raw().get(i).get(1);
			String ciName = viewLi.raw().get(i).get(2);
			diagramNameList.add(vieName);
			ciNameList.add(ciName);
			JSONObject data = view.newView((viewLi.raw().get(i).get(1)).toString(), viewLi.raw().get(i).get(2).toString());
			BigDecimal viewid = data.getBigDecimal("data");
			viewIds.add(viewid.toString());
		}

		CombUtil creatView = new CombUtil();
		result = creatView.createComb(diagramNameList, viNmae, "", "3", "3");
		JSONObject bu = new ShareDiagram().shareDiagram(viNmae);
		assertTrue(result.getBoolean("success"));
		return result;
	}

	@Then("^显示新创建的组合视图$")
	public void checkView() {
		String sql = "select ID from VC_DIAGRAM where is_open=1 and"
				+ " DIAGRAM_TYPE = 2 and STATUS = 1 AND DATA_STATUS = 1 and DOMAIN_ID =" + QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		Cosmos ref = new Cosmos();
		JSONObject refreshIds = ref.getViewList();
		String newAdd = result.getBigDecimal("data").toString();
		if (result.getBigDecimal("data") != null) {
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap) list.get(i);
				BigDecimal viewName = (BigDecimal) map.get("ID");
				assertTrue((refreshIds.toString()).indexOf(viewName.toString()) != -1);
			}
		}
		assertTrue((refreshIds.toString()).indexOf(newAdd) != -1);
	}

	@When("^获取组合视图节点信息$")
	public JSONObject viewInformation() {
		GetViewPointsForDcvByIds viId = new GetViewPointsForDcvByIds();
		viewInfo = viId.getViewInformation(viewIds);

		return viewInfo;

	}

	@Then("^节点信息显示正确$")
	public void checkNode() {

		for (int i = 0; i < diagramNameList.size(); i++) {
			String map = diagramNameList.get(i);
			assertTrue(viewInfo.toString().indexOf(map) != -1);

			for (int j = 0; j < viewIds.size(); j++) {
				String id = viewIds.get(j).toString();
				assertTrue(viewInfo.toString().indexOf(id) != -1);
			}
		}

	}

	@When("^查看节点映射的3D模型:$")
	public JSONObject seeModel(DataTable imagesName) {
	
		Query3DByCiCodesAndImgNames images = new Query3DByCiCodesAndImgNames();
		for (int i = 1; i < imagesName.raw().size(); i++) {
			String imgName = imagesName.raw().get(i).get(1);
			System.out.println("***********sssaaa***" + imgName);
			if (imgName.isEmpty()) {
				fail("传入名称为空");
			}	
			imageName.add("Tarsier|"+imgName);
		}
		for(int i = 0; i < diagramNameList.size(); i++){
			diagramNameList.set(i, "'"+diagramNameList.get(i)+"'");
		}

		
		String[] nodeNames = ciNameList.toArray(new String[ciNameList.size()]);
		String[] imageNames = imageName.toArray(new String[imageName.size()]);
		
		return result  = images.getIconMapping(nodeNames, imageNames);
		
		
	}

	@Then("^图标映射模型正确$")
	public void checkMapping() {
		
		assertTrue(result.toString().indexOf("NetPoint.mesh") != -1);
		for(int i = 0; i < ciNameList.size(); i ++){
			CiAttr classImg = new CiAttr(ciNameList.get(i));
			assertTrue((result.toString()).indexOf(classImg.getClassIconImage()+".mesh") != -1);
		}		
		for(int i = 0; i < imageName.size(); i++){
			String imgName = imageName.get(i);
			boolean res = ((result.toString()).indexOf(imgName.replace("Tarsier|", "")+".mesh") != -1);
			
			
			/*判断图标是否有对应的模型文件
			 * 没有则使用默认模型
			 * 有则使用对应的模型
			 */
			if(res == false){
			assertTrue((result.toString()).indexOf(imgName+"\":\"categoryDefault.mesh") !=-1);
			}else {
				assertTrue((result.toString()).indexOf(imgName.replace("Tarsier|", "")+".mesh") != -1);
			}
		}
	}


	@When("^查看ITV组合视图关系连线$")
	public JSONObject viewLine() {
		ciIDs = new String[ciNameList.size()];
		int i = 0;
		while (i < ciNameList.size()) {
		CiAttr ci = new CiAttr(ciNameList.get(i));
		ciIDs[i] = ci.getID().toString();
		i++;
	}	
		result = new QueryOneUpAndDown().releation(ciIDs);
		assertTrue(result.getBoolean("success"));
		return result;


	}

	@Then("^正确显示关系数据$")
	public void checkRelData() {
		//将CI的ID取出放入字符串中使用sql查询
		String ciIds = null;
		for(int i = 0; i < ciIDs.length; i++){
			if(ciIds == null){
				ciIds = ciIDs[i];
			}else {
				ciIds = ciIds+","+ciIDs[i];
			}			
		}
		String sql = "SELECT CI_CODE FROM cc_ci_rlt WHERE SOURCE_CI_ID IN (" +ciIds+ ") AND DATA_STATUS = 1 AND DOMAIN_ID = "+QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		
		for (int i = 0; i < list.size(); i++) {
		HashMap rel = (HashMap) list.get(i);
		String relCode = (String) rel.get("CI_CODE");
		assertTrue(result.toString().indexOf(relCode) != -1);
		}

	}
}
