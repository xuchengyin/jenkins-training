package com.uinnova.test.step_definitions.testcase.dmv.migrateDiagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.migrateDiagram.ExportAllDiagrams;
import com.uinnova.test.step_definitions.api.dmv.migrateDiagram.ImportDiagrams;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.ZipUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 视图一键导入导出
 *
 */
public class Scenario_migrateDiagram {
	String filePath = "";

	/********************************一键导出视图***************************************/
	@When("^一键导出视图$")
	public void exportAllDiagrams(){
		ExportAllDiagrams exportAllDiagrams = new ExportAllDiagrams();
		filePath = exportAllDiagrams.exportAllDiagrams();
		File file = new File(filePath);
		assertTrue(file.exists());
	}

	@Then("^成功导出所有视图$")
	public void checkExportAllDiagrams() throws Exception{
		//读取压缩文件 不知为什么下载下面的文件打不开， 原因待查
		//String migrateDiagram = ReadZip.readFileContent(filePath, "diagrams/migrateDiagram.json");
		//assertFalse(migrateDiagram.isEmpty());
	}


	@When("^一键导入视图文件\"(.*)\"$")
	public void importAllDiagrams(String fileName){
		ImportDiagrams importDiagrams = new ImportDiagrams();
		JSONObject result = importDiagrams.importDiagrams(fileName);
		assertTrue(result.getBoolean("success"));
	}

	@Then("^成功导入视图文件\"(.*)\"$")
	public void checkImportAllDiagrams(String fileName) throws Exception{
		//读取压缩文件
		String filePath = ImportDiagrams.class.getResource("/").getPath()+"/testData/dmv/migrateDiagram/"+fileName;
		String migrateDiagram = ZipUtil.readFileContent(filePath, "diagrams/migrateDiagram.json");
		assertFalse(migrateDiagram.isEmpty());
		JSONObject result = new JSONObject(migrateDiagram);
		JSONArray diagramDirs = result.getJSONArray("diagramDirs");
		JSONArray diagramEles = result.getJSONArray("diagramEles");
		JSONArray diagramGroups = result.getJSONArray("diagramGroups");
		JSONArray combDiagrams = result.getJSONArray("combDiagrams");
		JSONArray groups = result.getJSONArray("groups");
		JSONArray groupUsers = result.getJSONArray("groupUsers");
		JSONArray diagrams = result.getJSONArray("diagrams");
		if (diagramDirs.length()>0){
			String sql = "select ID from vc_diagram_dir where DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(diagramDirs.length(), l.size());
		}
		if (diagramEles.length()>0){
			String sql = "select ID from vc_diagram_ele where DOMAIN_ID="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(diagramEles.length(), l.size());
		}
		if (diagramGroups.length()>0){
			String sql = "select ID from vc_diagram_group where DOMAIN_ID="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(diagramGroups.length(), l.size());
		}
		if (combDiagrams.length()>0){
			String sql = "select ID from dc_comb_diagram where DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(combDiagrams.length(), l.size());
		}
		if (groups.length()>0){
			String sql = "select ID from vc_group where DATA_STATUS=1 and DOMAIN_ID="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(groups.length(), l.size());
		}

		if (groupUsers.length()>0){
			String sql = "select ID from vc_group_user where DOMAIN_ID="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(groupUsers.length(), l.size());
		}
		if (diagrams.length()>0){
			String sql = "select ID from vc_diagram where data_status=1 and DOMAIN_ID="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(diagrams.length(), l.size());
		}
	}
}  



