package com.uinnova.test.step_definitions.testcase.base.obj.classDir;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciClass.GetClassTree;
import com.uinnova.test.step_definitions.api.base.ciClass.RemoveDirById;
import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdateDir;
import com.uinnova.test.step_definitions.testcase.base.obj.ciClass.Scenario_ciClass;
import com.uinnova.test.step_definitions.utils.base.CiClassDirUtil;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Scenario_classDir {
	String dirId, result;
	private static List<String> clClsDirList = new  ArrayList<String>();

	@After("@CleanCiClsDir")
	public void cleanCiclsDir(){
		if (!clClsDirList.isEmpty()){
			for (int i=0; i<clClsDirList.size(); i++){
				String dirName = clClsDirList.get(i);
				RemoveDirById ri = new RemoveDirById();
				JSONObject result = ri.removeDirByName(dirName);
				assertTrue(result.getBoolean("success"));
				clClsDirList.remove(dirName);
				i--;
			}
		}
	}

	@After("@cleanAll")
	public void cleanAll(){
		if (!clClsDirList.isEmpty()){
			Scenario_ciClass sc = new Scenario_ciClass();
			RemoveDirById ri = new RemoveDirById();
			sc.cleanCicls();
			for (int i=0; i<clClsDirList.size(); i++){
				String dirName = clClsDirList.get(i);
				JSONObject result = ri.removeDirByName(dirName);
				assertTrue(result.getBoolean("success"));
				clClsDirList.remove(dirName);
				i--;
			}
		}
	}

	@When("^创建名称为(.*)的CiClass目录$")
	public void createClassDir(String dirName) {
		SaveOrUpdateDir sd = new SaveOrUpdateDir();
		sd.createDir(dirName);
		clClsDirList.add(dirName);
	}

	@Then("^系统中存在名称(.*)的CiClass目录$")
	public void searchClassDir(String dirName) {
		CiClassDirUtil cu = new CiClassDirUtil();
		String id = cu.getDirId(dirName);
		assertNotEquals(null, id);
	}

	@And("^再次创建名称为(.*)的CiClass目录失败,kw=\"(.*)\"$")
	public void createClassDirAgain(String dirName, String kw) {
		SaveOrUpdateDir sd = new SaveOrUpdateDir();
		JSONObject result = sd.createDirFail(dirName, kw);
		assertEquals(null, result);
	}

	@When("^修改CiClass目录(.*)的名称为(.*)$")
	public void modifyClassDirName(String sourceName, String distName) {
		CiClassDirUtil cu = new CiClassDirUtil();
		dirId = cu.getDirId(sourceName);
		SaveOrUpdateDir sd = new SaveOrUpdateDir();
		sd.updateDir(distName, dirId);
		clClsDirList.remove(sourceName);
		clClsDirList.add(distName);
	}

	@Then("目录名称(.*)更新为(.*)$")
	public void checkModify(String oldName, String dirName) {
		GetClassTree gt = new GetClassTree();
		JSONObject result = gt.getClassTree("");
		assertTrue(result.toString().indexOf("\"dirName\":\"" + dirName + "\"") > 0);
		CiClassDirUtil cu = new CiClassDirUtil();
		assertEquals(cu.getDirId(dirName), dirId);
		if (oldName.compareTo(dirName)!=0)
			assertEquals(-1, result.toString().indexOf("\"dirName\":\"" + oldName + "\"") );
	}

	@When("删除名称为(.*)的CiClass目录$")
	public void deleteDir(String dirName) {
		RemoveDirById ri = new RemoveDirById();
		ri.removeDirByName(dirName);
		clClsDirList.remove(dirName);
	}

	@Then("系统中不存在名称为(.*)的CiClass目录$")
	public void notExistDir(String dirName) {
		GetClassTree gt = new GetClassTree();
		JSONObject result = gt.getClassTree("");
		assertEquals("系统中不存在名称为"+dirName+"的CiClass目录", -1, result.toString().indexOf("\"dirName\":\"" + dirName + "\""));
	}

	/**
	 * 失败的场景
	 * 目前接口没做长度校验。数据库最大长度40， 页面最大长度20
	 */
	@When("^创建名称为\"(.*)\"的CiClass目录失败,kw=\"(.*)\"$")
	public void createDirFail(String dirName, String kw) {
		SaveOrUpdateDir sd = new SaveOrUpdateDir();
		JSONObject result = sd.createDirFail(dirName, kw);
		assertEquals(null, result);
	}

}
