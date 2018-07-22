package com.uinnova.test.step_definitions.api.dmv.linked;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-01-08
 * 编写人:sunsl
 * 功能介绍:生成链路类
 */
public class SaveOrUpdateBatch extends RestApi{
   public JSONObject saveOrUpdateBatch(String diagramName ,String linkedName,String linkedColor,String ciCodes){
	   String url = ":1511/tarsier-vmdb/dmv/linked/saveOrUpdateBatch";
	   DiagramUtil diagramUtil = new DiagramUtil();
	   BigDecimal sourceId = diagramUtil.getDiagramIdByName(diagramName);
	   JSONArray array = new JSONArray();
	   JSONObject param = new JSONObject();
	   param.put("sourceId", sourceId);
	   param.put("linkedName", linkedName);
	   param.put("linkedColor", linkedColor);
	   param.put("ciCodes", ciCodes);
	   array.put(param);
	   return doRest(url, array.toString(), "POST");
   }
}
