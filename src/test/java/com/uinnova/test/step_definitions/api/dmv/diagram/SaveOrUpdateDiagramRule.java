package com.uinnova.test.step_definitions.api.dmv.diagram;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.CiRltUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * @author wsl
 * 保存和修改影响关系规则
 *[{
	"diagramId": 10076,
	"eleId": 10001,
	"eleType": 5
}]
 */
public class SaveOrUpdateDiagramRule extends RestApi{
	
	public JSONObject saveOrUpdateDiagramRule(String diagramName, String friendDefName){
		String url = ":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDiagramRule";
		JSONArray array = new JSONArray();
		JSONObject param = new JSONObject();
		DiagramUtil du = new DiagramUtil();
		param.put("diagramId", du.getDiagramIdByName(diagramName));
		CiRltUtil crlu = new CiRltUtil();
		param.put("eleId", crlu.getDefIdByDefName(friendDefName));
		param.put("eleType", 5);
		array.put(param);
		return doRest(url, array.toString(), "POST");
	}

}
