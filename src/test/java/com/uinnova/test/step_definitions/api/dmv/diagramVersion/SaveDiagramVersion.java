package com.uinnova.test.step_definitions.api.dmv.diagramVersion;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.RestApi;

/**
 * 编写时间:2017-12-07
 * 编写人:sunsl
 * 功能介绍:DMV给视图添加历史版本
 */
public class SaveDiagramVersion extends RestApi{
   public JSONObject saveDiagramVersion(String ci3dPoint,JSONObject diagram,JSONArray diagramEles,String xml,String versionDesc,String versionNo){
	   String url =":1511/tarsier-vmdb/dmv/diagramVersion/saveDiagramVersion";
	    JSONObject param = new JSONObject();
		param.put("ci3dPoint", ci3dPoint);
		param.put("diagram", diagram);
		param.put("diagramEles", diagramEles);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("xml", xml);
		param.put("updateType", 1);
		param.put("versionNo", versionNo);
		param.put("versionDesc", versionDesc);
	   return doRest(url, param.toString(), "POST");
   }
   
   public JSONObject saveDiagramVersion(String ci3dPoint,JSONObject diagram,JSONArray diagramEles,String xml,String versionDesc,String upRelation,String versionNo){
	   String url =":1511/tarsier-vmdb/dmv/diagramVersion/saveDiagramVersion";
	    JSONObject param = new JSONObject();
	    JSONArray upRelations = new JSONArray();
	    upRelations.put(upRelation);
	    diagram.put("dataUpType", 2);
		param.put("ci3dPoint", ci3dPoint);
		param.put("diagram", diagram);
		param.put("diagramEles", diagramEles);
		param.put("thumbnail", Contants.DMV_INIT_THUMBNAIL);
		param.put("xml", xml);
		param.put("upRelations", upRelations);
		param.put("updateType", 2);
		param.put("versionDesc", versionDesc);
	   return doRest(url, param.toString(), "POST");
   }
}
