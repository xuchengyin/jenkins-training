package com.uinnova.test.step_definitions.utils.itv;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.api.RestApi;

/*@autor:wjx
 * 功能介绍：ITV创建组合视图
 * 
 */

public class ItvView extends RestApi {
	public JSONObject saveOrUpdateDiagram(JSONObject creat) {
		String url = ":1511/tarsier-vmdb/dmv/diagram/saveOrUpdateDiagram";
		return doRest(url, creat.toString(), "POST");
	}

	/******************************** 新建 ************************************/
	public JSONObject newView(String viewName, String ciName) {
		JSONObject param = new JSONObject();
		String ci3dPoint = "{\"graphWidth\":1476,\"graphHeight\":803,\"nodes\":[],\"containers\":[],\"edges\":[]}";
		String xml = "<mxGraphModel grid=\"0\" gridSize=\"10\" guides=\"1\" tooltips=\"0\" connect=\"0\" arrows=\"1\" fold=\"1\" page=\"0\" pageScale=\"1\" pageWidth=\"827\" pageHeight=\"1169\" update-history-view=\"1\" uv-auto-layout=\"0\" upanel-width=\"800\" upanel-height=\"600\" upanel-enabled=\"0\"><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/></root></mxGraphModel>";
		String autoName = "true";
		JSONObject diagram = new JSONObject();

		diagram.put("dirId", 0);
		diagram.put("isOpen", 0);
		diagram.put("diagramDesc", "");
		diagram.put("name", viewName);
		diagram.put("status", 1);
		diagram.put("diagramType", 1);
		param.put("diagram", diagram);
		param.put("autoName", autoName);
		param.put("ci3dPoint", ci3dPoint);
		param.put("xml", xml);
		param.put("thumbnail", "");
		ItvView toParam = new ItvView();
		JSONObject data = toParam.saveOrUpdateDiagram(param);
		return toParam.selectId(data.get("data").toString(), ciName);
	}

	public JSONObject selectId(String dataid, String ciName) {
		String sql = "select * from vc_diagram WHERE ID = " + dataid
				+ " AND DIAGRAM_TYPE = 1 AND DATA_STATUS = 1 AND DOMAIN_ID = " + QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		ItvView toParam = new ItvView();
		return toParam.saveView(list, ciName);
	}

	/******************************** 添加CI ***********************************/
	public JSONObject saveView(ArrayList list, String ciName) {
		JSONObject param = new JSONObject();
		Properties prop = new Properties();
		HashMap map = new HashMap();
		String server = null;
		String port = "1512";
		try {
			InputStream in = new BufferedInputStream(com.uinnova.test.step_definitions.utils.common.QaUtil.class
					.getResourceAsStream("/" + Contants.CONFIGURE_FILENAME));
			prop.load(in);
			server = prop.getProperty("server");
		} catch (Exception e) {
			e.getMessage();
		}

		for (int i = 0; i < list.size(); i++) {
			map = (HashMap) list.get(i);
		}

		String serverUrl = server + ":" + port;

		CiAttr ciAttr = new CiAttr(ciName);

		String xml = "<mxGraphModel grid=\"1\" gridSize=\"10\" guides=\"1\" tooltips=\"0\" connect=\"1\" arrows=\"1\" fold=\"1\" page=\"0\" pageScale=\"1\" pageWidth=\"827\" pageHeight=\"1169\" update-history-view=\"1\" uv-auto-layout=\"0\" upanel-width=\"800\" upanel-height=\"600\" upanel-enabled=\"0\"><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><UserObject label=\""
				+ ciName + "\" size-ms=\"40,22\" size-mx=\"60,33\" size-ml=\"80,44\" data-id=\"ci_" + ciAttr.getID()
				+ "\" code=\"" + ciName + "\" id=\"2\"><mxCell style=\"html=1;image;image=" + serverUrl
				+ "/vmdb-sso/rsm/cli/read" + ciAttr.getClassIcon()
				+ "\" vertex=\"1\" parent=\"1\" utsize-ms=\"40,22\" utsize-mx=\"60,33\" utsize-ml=\"80,44\" utdata-id=\"ci_"
				+ ciAttr.getID() + "\" utcode=\"" + ciName
				+ "\"><mxGeometry x=\"560\" y=\"290\" width=\"60\" height=\"33\" as=\"geometry\"/></mxCell></UserObject></root></mxGraphModel>";
		String ci3dPoint = "{\"graphWidth\":963,\"graphHeight\":649,\"nodes\":[{\"img\":\"" + serverUrl
				+ "/vmdb-sso/rsm/cli/read" + ciAttr.getClassIcon() + "\",\"name\":\"" + ciName
				+ "\",\"id\":\"2\",\"width\":60,\"height\":33,\"x\":560,\"y\":290,\"ciId\":\"" + ciAttr.getID()
				+ "\",\"size-ms\":\"40,22\",\"size-mx\":\"60,33\",\"size-ml\":\"80,44\",\"data-id\":\"ci_"
				+ ciAttr.getID() + "\",\"code\":\"" + ciName + "\"}],\"containers\":[],\"edges\":[]}";
		String thumbnail = "data:image/png;base64,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";
		JSONObject diagram = new JSONObject();
		diagram.put("createTime", map.get("CREATE_TIME"));
		diagram.put("status", 1);
		diagram.put("diagramXml", map.get("DIAGRAM_XML"));
		diagram.put("icon1", serverUrl + "/vmdb-sso/rsm/cli/read" + map.get("ICON_1"));
		diagram.put("searchField", map.get("SEARCH_FIELD"));
		diagram.put("dirId", 0);
		diagram.put("isOpen", 0);
		diagram.put("creator", map.get("CREATOR").toString().replace("[\\[admin\\]]", ""));
		diagram.put("id", map.get("ID"));
		diagram.put("modifier", map.get("MODIFIER").toString().replace("[\\[admin\\]]", ""));
		diagram.put("diagramType", 1);
		diagram.put("diagramDesc", "");
		diagram.put("userId", 1);
		diagram.put("name", map.get("NAME"));
		diagram.put("domainId", map.get("DOMAIN_ID"));
		diagram.put("dataStatus", 1);
		diagram.put("ci3dPoint", map.get("CI_3D_POINT"));
		diagram.put("modifyTime", map.get("CREATE_TIME"));
		diagram.put("dataUpType", 1);
		diagram.put("diagramBgCss", "");
		diagram.put("diagramBgImg", "");

		JSONArray diagramEles = new JSONArray();
		JSONObject diagramEle = new JSONObject();
		diagramEle.put("eleType", 1);
		diagramEle.put("eleId", ciAttr.getID());
		diagramEles.put(diagramEle);

		param.put("xml", xml);
		param.put("diagram", diagram);
		param.put("diagramEles", diagramEles);
		param.put("ci3dPoint", ci3dPoint);
		param.put("thumbnail", thumbnail);

		ItvView toParam = new ItvView();
		return toParam.saveOrUpdateDiagram(param);
	}

}
