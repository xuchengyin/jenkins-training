package com.uinnova.test.step_definitions.testcase.dmv.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDirByIds;
import com.uinnova.test.step_definitions.api.dmv.diagram.SaveOrUpdateDiagram;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 导入视图
 *
 */
public class Scenario_image_Import extends Scenario_image_Base {
	private JSONArray fileArr = new JSONArray();;//导入多个sheet的visio时使用

	private List<String> diagramNameList = new ArrayList<String>(); //用于记录一共新建了多少视图， 用户After方法清理数据

	@After("@delImportDiagram")
	public void delDiagram(){
		if (!diagramNameList.isEmpty()){
			for (int i=0; i<diagramNameList.size(); i++){
				String diagramName = diagramNameList.get(i);
				RemoveDirByIds rd = new RemoveDirByIds();
				JSONObject result = rd.removeDirByIds(diagramName);
				assertTrue(result.getBoolean("success"));
				diagramNameList.remove(diagramName);
				i--;
			}
		}
	}



	/********************************导入Visio视图**************************************/

	@When("^导入如下Visio视图:$")
	public void importVisioDiagram(DataTable  fileTable) throws DocumentException{
		for(int i=1; i<fileTable.raw().size(); i++){
			String fileName = fileTable.raw().get(i).get(1);
			if (fileName.isEmpty()){
				fail("导入文件名为空");
			}
			SaveOrUpdateDiagram saveOrUpdateDiagram = new SaveOrUpdateDiagram();
			JSONObject result = saveOrUpdateDiagram.saveOrUpdateDiagramImportVisio(fileName);
			assertTrue(result.getBoolean("success"));
			fileArr = result.getJSONArray("data");
			for (int l =0; l<fileArr.length(); l++){
				diagramNameList.add(fileArr.getString(l));
			}
		}
	}


	@Then("^成功导入如下Visio视图:$")
	public void checkImportVisioDiagram(DataTable  fileTable){
		for(int i=1; i<fileTable.raw().size(); i++){
			String fileName = fileTable.raw().get(i).get(1);
			if (fileName.isEmpty()){
				fail("导入文件名为空");
			}
			for (int l =0; l<fileArr.length(); l++){
				fileName = fileArr.getString(l);
				JSONObject result = getDiagramInfoByName(fileName);
				assertTrue(result.getBoolean("success"));
				assertEquals(result.getInt("code"), -1);
				JSONObject oldParam = result.getJSONObject("data");
				JSONArray diagramEles = new JSONArray();
				//检查diagramEles
				if(oldParam.has("diagramEles")){
					diagramEles = (JSONArray) oldParam.get("diagramEles");
				}
				//读取数据库
				String sql ="select id from vc_diagram where data_status=1 and status=1 and name='"+fileName+"' and domain_id="+QaUtil.domain_id;
				List resultList = JdbcUtil.executeQuery(sql);
				assertEquals(1,resultList.size());
			}
		}
	}


	/********************************导入xml视图***************************************/
	@When("^导入如下xml视图:$")
	public void importXMLDiagram(DataTable  fileTable) throws DocumentException{
		for(int i=1; i<fileTable.raw().size(); i++){
			String fileName = fileTable.raw().get(i).get(1);
			if (fileName.isEmpty()){
				fail("导入文件名为空");
			}
			SaveOrUpdateDiagram saveOrUpdateDiagram = new SaveOrUpdateDiagram();
			JSONObject result = saveOrUpdateDiagram.saveOrUpdateDiagramImportXml(fileName);
			assertTrue(result.getBoolean("success"));
			diagramNameList.add(fileName);
		}
	}

	@Then("^成功导入如下xml视图:$")
	public void checkImportXMLDiagram(DataTable  fileTable) throws DocumentException{
		checkImportResult(fileTable);
	}

	/********************************导入配置信息***************************************/
	@When("^导入如下配置信息视图:$")
	public void importConfigDiagram(DataTable  fileTable) throws DocumentException{
		for(int i=1; i<fileTable.raw().size(); i++){
			String fileName = fileTable.raw().get(i).get(1);
			if (fileName.isEmpty()){
				fail("导入文件名为空");
			}
			SaveOrUpdateDiagram saveOrUpdateDiagram = new SaveOrUpdateDiagram();
			JSONObject result = saveOrUpdateDiagram.saveOrUpdateDiagramImportConfig(fileName);
			assertTrue(result.getBoolean("success"));
			diagramNameList.add(fileName);
		}
	}

	@Then("^成功导入如下配置信息视图:$")
	public void checkImportConfigDiagram(DataTable  fileTable) throws DocumentException{
		checkImportResult(fileTable);
	}

	/********************************导入图片
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws InterruptedException ***************************************/
	@When("^导入如下图片视图:$")
	public void importPictureDiagram(DataTable fileTable) throws DocumentException, ClientProtocolException, IOException, InterruptedException{
		for(int i=1; i<fileTable.raw().size(); i++){
			String fileName = fileTable.raw().get(i).get(1);
			if (fileName.isEmpty()){
				fail("导入文件名为空");
			}
			SaveOrUpdateDiagram saveOrUpdateDiagram = new SaveOrUpdateDiagram();
			JSONObject result = saveOrUpdateDiagram.saveOrUpdateDiagramImportPicture(fileName);
			assertTrue(result.getBoolean("success"));
			diagramNameList.add(fileName);
		}
	}

	@Then("^成功导入如下图片视图:$")
	public void checkImportPictureDiagram(DataTable fileTable) throws DocumentException{
		checkImportResult(fileTable);
	}

	/**
	 * @param fileTable
	 * 校验导入视图是否存在
	 */
	private void checkImportResult(DataTable  fileTable){
		for(int i=1; i<fileTable.raw().size(); i++){
			String fileName = fileTable.raw().get(i).get(1);
			if (fileName.isEmpty()){
				fail("导入文件名为空");
			}
			JSONObject result = getDiagramInfoByName(fileName);
			assertTrue(result.getBoolean("success"));
			assertEquals(result.getInt("code"), -1);
			JSONObject oldParam = result.getJSONObject("data");
			JSONArray diagramEles = new JSONArray();
			//检查diagramEles
			if(oldParam.has("diagramEles")){
				diagramEles = (JSONArray) oldParam.get("diagramEles");
			}
			//读取文件导入文件

			//读取数据库
			String sql ="select id from vc_diagram where data_status=1 and status=1 and name='"+fileName+"' and domain_id="+QaUtil.domain_id;
			List l = JdbcUtil.executeQuery(sql);
			assertEquals(1,l.size());
		}
	}
}
