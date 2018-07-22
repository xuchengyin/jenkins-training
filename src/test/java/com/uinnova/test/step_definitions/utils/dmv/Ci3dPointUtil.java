package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author wsl
 * 2017-12-22
 * 视图参数ci3DPoint操作类
 *
 */
public class Ci3dPointUtil {
	/**
	 * @param xml
	 * @param ciCode
	 * @return
	 * @throws DocumentException
	 * 是否存在某个CI
	 */
	public boolean hasCiCode(String ci3dPoint, String ciCode) throws DocumentException{
		boolean hasNode = false;
		JSONObject ci3dPointObj = new JSONObject(ci3dPoint);
		JSONArray nodes = ci3dPointObj.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					hasNode = true;
					break;
				}
			}
		}
		return hasNode;
	}
	
	/**
	 * @param ci3dPoint
	 * @return
	 */
	private int getCi3dPointId(String ci3dPoint){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		JSONArray containers=obj.getJSONArray("containers");
		int id=1;
		for (int i=0; i<nodes.length();i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			int tempId = tempObj.getInt("id");
			if (tempId>id)
				id =tempId;
		}
		for (int j=0; j<containers.length();j++){
			JSONObject tempObj = containers.getJSONObject(j);
			int tempId = tempObj.getInt("id");
			if (tempId>id)
				id =tempId;
		}
		return id+1;
	}

	/**
	 * @param ci3dPoint
	 * @param opObj
	 * @param ciCode
	 * @param ciId
	 * @param icon
	 * @param label
	 * @param utdataLabelValObj
	 * @param x
	 * @param y
	 * @return
	 * 增加CI是修改c3dpoint
	 */
	public String ci3dPointAddNode(String ci3dPoint, String opObj,String ciCode, String ciId,String icon,String label, List utdataLabelValObj, String x, String y){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		int id=getCi3dPointId(ci3dPoint);
		JSONObject newObj = new JSONObject();
		if ("ci".compareToIgnoreCase(opObj)==0){
			newObj.put("img", icon);
			newObj.put("name", ciCode);
			newObj.put("id", String.valueOf(id));
			newObj.put("width", 60);
			newObj.put("height", 60);
			newObj.put("x", Integer.valueOf(x));
			newObj.put("y", Integer.valueOf(y));
			newObj.put("ciId", ciId);
			newObj.put("size-ms", "40,40");
			newObj.put("size-mx", "60,60");
			newObj.put("size-ml", "80,80");
			List codeL = new ArrayList<>();
			codeL.add(ciCode);
			newObj.put("data-values", codeL);
			newObj.put("data-id",  "ci_"+ciId);
			List<String> labelList = new ArrayList<String>();
			labelList.add(label);
			newObj.put("data-label-list", labelList);
			JSONObject labelValObj = new JSONObject();
			labelValObj.put("label", ciCode);
			newObj.put("data-label-val",labelValObj);
			newObj.put("data-label-val-obj", utdataLabelValObj);
			newObj.put("code", ciCode);

		}

		/*	if ("img".compareToIgnoreCase(opObj)==0){
			newObj.put("img", icon);
			newObj.put("name", "");
			newObj.put("id", String.valueOf(id));
			newObj.put("width", 60);
			newObj.put("height", 60);
			newObj.put("x", Integer.valueOf(x));
			newObj.put("y", Integer.valueOf(y));
			newObj.put("imgName", "");
			newObj.put("imgPath", "");
			newObj.put("imgCode", "");
			newObj.put("image_node", "");
			newObj.put("imgFullName", "");
			newObj.put("size-ms", "40,40");
			newObj.put("size-mx", "60,60");
			newObj.put("size-ml", "80,80");
		}*/
		nodes.put(newObj);

		obj.put("nodes", nodes);
		return obj.toString();
	}
	public String ci3dPointAddEdges(String ci3dPoint, String clsName, String sourceCiCode, String targetCiCode){
		JSONObject obj = new JSONObject(ci3dPoint);
		String sourceId= "";
		String targetId= "";
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		boolean hasSrc = false;
		boolean hasTar = false;
		JSONArray nodes = obj.getJSONArray("nodes");
		for (int i=0; i<nodes.length(); i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (tempObj.has("code"))
			{
				if (sourceCiCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					sourceId = tempObj.getString("id");
					x1= tempObj.getInt("x");
					y1= tempObj.getInt("y");
					hasSrc = true;
				}
				if (targetCiCode.compareToIgnoreCase(tempObj.getString("code"))==0){
					targetId = tempObj.getString("id");
					x2= tempObj.getInt("x");
					x2= tempObj.getInt("y");
					hasTar = true;
				}
				if (hasSrc && hasTar)
					break;
			}
		}
		
		JSONArray edges=obj.getJSONArray("edges");
		int id=getCi3dPointId(ci3dPoint);
		JSONObject newObj = new JSONObject();
		newObj.put("sourceName", "");
		newObj.put("sourceId", sourceId);
		newObj.put("targetName", "");
		newObj.put("targetId", targetId);
		newObj.put("text", clsName);
		newObj.put("strokeWidth", 1);
		List pointList = new ArrayList<>();
		JSONObject temp1 = new JSONObject();
		temp1.put("x", x1);
		temp1.put("y", y1);
		pointList.add(temp1);
		JSONObject temp2 = new JSONObject();
		temp2.put("x", x2);
		temp2.put("y", y2);
		pointList.add(temp2);
		newObj.put("points", pointList);
		edges.put(newObj);

		obj.put("edges", edges);
		return obj.toString();
	}

	/**
	 * @param ci3dPoint
	 * @param opObj
	 * @param ciCode
	 * @param ciId
	 * @param icon
	 * @param label
	 * @param utdataLabelValObj
	 * @param x
	 * @param y
	 * @return
	 * CI解除CI后再创建CI时修改ci3dPoint
	 */
	public String ci3dPointciCreateCi(String ci3dPoint, String oldCiCode,String newCiCode, String ciId,String icon,String label, List ci3dPointLabelValObj, String x, String y){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		for(int i=0; i<nodes.length(); i++){
			JSONObject newObj = nodes.getJSONObject(i);
			if (oldCiCode.compareToIgnoreCase(newObj.getString("name"))==0){
				newObj.put("img", icon);
				newObj.put("name", newCiCode);
				newObj.put("ciId", ciId);
				newObj.put("data-id",  "ci_"+ciId);
				List codeL = new ArrayList<>();
				codeL.add(newCiCode);
				newObj.put("data-values", codeL);
				newObj.put("data-id",  "ci_"+ciId);
				List<String> labelList = new ArrayList<String>();
				labelList.add(label);
				newObj.put("data-label-list", labelList);

				JSONObject labelValObj = new JSONObject();
				labelValObj.put("label", newCiCode);
				newObj.put("data-label-val",labelValObj);
				newObj.put("data-label-val-obj", ci3dPointLabelValObj);
				newObj.put("code", newCiCode);
				nodes.put(newObj);
				break;
			}
		}

		obj.put("nodes", nodes);
		return obj.toString();
	}


	/**
	 * @param ci3dPoint
	 * @param ciCode
	 * @return
	 * CI添加至容器时ci3dPoint的修改
	 */
	public String ci3dPointAddContainer(String ci3dPoint, String ciCode){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		JSONArray containers=obj.getJSONArray("containers");
		int id=getCi3dPointId(ci3dPoint);

		int x = 0;
		int y = 0;
		for (int i=0; i<nodes.length();i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
				x = tempObj.getInt("x");
				y = tempObj.getInt("y");
				break;
			}
		}
		x-=40;
		y-=70;
		JSONObject newObj = new JSONObject();
		newObj.put("name", "Container");
		newObj.put("id", String.valueOf(id));
		newObj.put("width", 140);
		newObj.put("height", 170);
		newObj.put("x", x);
		newObj.put("y", y);
		newObj.put("level", 0);

		containers.put(newObj);

		obj.put("containers", containers);
		return obj.toString();
	}


	/**
	 * @param ci3dPoint
	 * @param ciCode
	 * @return
	 * ci增加钻取视图时ci3dPoint的修改
	 */
	public String ci3dPointCIAddDrillDown(String ci3dPoint, String ciCode, String drillDiagramName){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		for (int i=0; i<nodes.length();i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
				DiagramUtil diagramUtil = new DiagramUtil();
				BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);
				String view ="";
				if (tempObj.has("view"))
					view = tempObj.getString("view");
				if (view.isEmpty())
					view = String.valueOf(diagramId);
				else
					view+=","+String.valueOf(diagramId);
				tempObj.put("view", view);
				tempObj.put("direction", "down");
				JSONObject relationObj = new JSONObject();
				relationObj.put("viewId", view);
				relationObj.put("direction", "down");
				tempObj.put("relation",relationObj);
				nodes.put(i, tempObj);
				break;
			}
		}
		obj.put("nodes", nodes);
		return obj.toString();
	}


	/**
	 * @param ci3dPoint
	 * @param ciCode
	 * @param drillDiagramName
	 * @return
	 * 给ci取消钻取视图时修改ci3dpoint
	 */
	public String ci3dPointCIRemoveDrillDown(String ci3dPoint, String ciCode, String drillDiagramName){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		for (int i=0; i<nodes.length();i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
				DiagramUtil diagramUtil = new DiagramUtil();
				BigDecimal diagramId = diagramUtil.getDiagramIdByName(drillDiagramName);
				String view = tempObj.getString("view");
				String[] temp = view.split(",");
				String newView = "";
				for (int j=0; j<temp.length; j++){
					if (String.valueOf(diagramId).compareToIgnoreCase(temp[i])!=0){
						if (newView.isEmpty())
							newView = temp[i];
						else
							newView+=","+temp[i];
					}
				}
				tempObj.put("view", newView);
				tempObj.put("direction", "down");
				JSONObject relationObj = new JSONObject();
				relationObj.put("viewId", newView);
				relationObj.put("direction", "down");
				tempObj.put("relation",relationObj);
				nodes.put(i, tempObj);
				break;
			}
		}
		obj.put("nodes", nodes);
		return obj.toString();
	}

	/**
	 * @param ci3dPoint
	 * @param ciCode
	 * @param drillDiagramName
	 * @return
	 * CI解除CI时修改ci3dPoing
	 */
	public String ci3dPointCIGetRidOfCi(String ci3dPoint, String ciCode){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		for (int i=0; i<nodes.length();i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
				tempObj.remove("ciId");
				tempObj.remove("code");
				tempObj.remove("data-id");
				nodes.put(i, tempObj);
				break;
			}
		}
		obj.put("nodes", nodes);
		return obj.toString();
	}

	/**
	 * @param ci3dPoint
	 * @param ciCode
	 * @param dynamicCiId
	 * @return
	 * 加载动态节点时修改ci3dpoint
	 */
	public String ci3dPointciMountDynamicNode(String ci3dPoint, String ciCode, String dynamicCiId){
		JSONObject obj = new JSONObject(ci3dPoint);
		JSONArray nodes=obj.getJSONArray("nodes");
		for (int i=0; i<nodes.length();i++){
			JSONObject tempObj = nodes.getJSONObject(i);
			if (ciCode.compareToIgnoreCase(tempObj.getString("code"))==0){
				JSONObject tagsInfo = new JSONObject();
				tagsInfo.put("tagIds", new ArrayList<>());
				tagsInfo.put("classIds", new ArrayList<>());
				tagsInfo.put("words", new ArrayList<>());
				tempObj.put("tags-info", tagsInfo);
				tempObj.put("tags-params-info", new ArrayList<>());
				boolean hasChild = false;
				if (tempObj.has("tags-selection-cis")){
					JSONArray temp = tempObj.getJSONArray("tags-selection-cis");
					List cisList = new ArrayList<>();

					for (int k=0; k<temp.length(); k++){
						String s = temp.getString(i);
						cisList.add(s);
						if (s.compareToIgnoreCase(dynamicCiId)==0){
							hasChild =true;
							break;
						}
					}
					if (!hasChild){
						cisList.add(dynamicCiId);
						tempObj.put("tags-selection-cis", cisList);
					}

				}else{
					List cisList = new ArrayList<>();
					cisList.add(dynamicCiId);
					tempObj.put("tags-selection-cis",cisList);
				}

				if (tempObj.has("child-num")){
					if (!hasChild)
						tempObj.put("child-num", tempObj.getInt("child-num")+1);
				}
				else
					tempObj.put("child-num", 1);

				nodes.put(i, tempObj);
				break;
			}
		}
		obj.put("nodes", nodes);
		return obj.toString();
	}
}
