package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.comb.SaveOrUpdateCombDiagram;

/**
 * 编写时间:2018-01-09
 * 编写人:sunsl
 * 功能介绍:组合视图工具类
 */
public class CombUtil {
  public JSONObject createComb(List<String>diagramNameList,String name,String diagramDesc,String combRows,String combCols){
	  JSONObject diagram = new JSONObject();
	  JSONObject diagramInfo = new JSONObject();
	  JSONArray tags = new JSONArray();
	  JSONArray combDiagrams = new JSONArray();
	  DiagramUtil diagramUtil = new DiagramUtil();
	  for(int i = 0; i < diagramNameList.size();i ++){
		  JSONObject combDiagram = new JSONObject();
		  String diagramName = diagramNameList.get(i);
		  BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		  combDiagram.put("diagramId", diagramId);
		  combDiagram.put("px", 1);
		  combDiagram.put("py", i +1);
		  combDiagram.put("direct", 0);
		  combDiagrams.put(combDiagram);
	  }
	  diagram.put("dirId", 0);
	  diagram.put("isOpen", 0);
	  diagram.put("diagramDesc", diagramDesc);
	  diagram.put("status", 1);
	  diagram.put("name", name);
	  diagram.put("diagramType", 2);
	  diagram.put("combRows", combRows);
	  diagram.put("combCols", combCols);
	  diagramInfo.put("diagram", diagram);
	  diagramInfo.put("tags", tags);
	  diagramInfo.put("combDiagrams", combDiagrams);
	  SaveOrUpdateCombDiagram saveOrUpdateCombDiagram = new SaveOrUpdateCombDiagram();
	  JSONObject result = saveOrUpdateCombDiagram.saveOrUpdateCombDiagram(diagramInfo);
	  return result;
  }
  
  public JSONObject createComb(List<String>diagramNameList,String name,String diagramDesc,String combRows,String combCols,String ciCode){
	  JSONObject diagram = new JSONObject();
	  JSONObject diagramInfo = new JSONObject();
	  JSONArray tags = new JSONArray();
	  JSONArray combDiagrams = new JSONArray();
	  DiagramUtil diagramUtil = new DiagramUtil();
	  for(int i = 0; i < diagramNameList.size();i ++){
		  JSONObject combDiagram = new JSONObject();
		  String diagramName = diagramNameList.get(i);
		  BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		  combDiagram.put("diagramId", diagramId);
		  combDiagram.put("px", 1);
		  combDiagram.put("py", i +1);
		  combDiagram.put("direct", 0);
		  combDiagrams.put(combDiagram);
	  }
	  diagram.put("dirId", 0);
	  diagram.put("isOpen", 0);
	  diagram.put("diagramDesc", diagramDesc);
	  diagram.put("appRltCiCode", ciCode);
	  diagram.put("status", 1);
	  diagram.put("name", name);
	  diagram.put("diagramType", 2);
	  diagram.put("combRows", combRows);
	  diagram.put("combCols", combCols);
	  diagramInfo.put("diagram", diagram);
	  diagramInfo.put("tags", tags);
	  diagramInfo.put("combDiagrams", combDiagrams);
	  SaveOrUpdateCombDiagram saveOrUpdateCombDiagram = new SaveOrUpdateCombDiagram();
	  JSONObject result = saveOrUpdateCombDiagram.saveOrUpdateCombDiagram(diagramInfo);
	  return result;
  }
}
