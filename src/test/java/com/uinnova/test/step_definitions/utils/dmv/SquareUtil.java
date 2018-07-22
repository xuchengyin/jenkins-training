package com.uinnova.test.step_definitions.utils.dmv;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.diagram.QueryOpenDiagramCountByTag;
import com.uinnova.test.step_definitions.api.dmv.diagram.QueryOpenDiagramCountByUser;

/**
 * 编写时间:2017-12-19
 * 编写人:sunsl
 * 功能介绍:DMV广场的工具类
 */
public class SquareUtil {
   public BigDecimal getUserId(){
	   QueryOpenDiagramCountByUser qodc = new QueryOpenDiagramCountByUser();
	   JSONObject result = qodc.queryOpenDiagramCountByUser();
		JSONArray data = result.getJSONArray("data");
		BigDecimal userId = new BigDecimal(0);
		if (data.length() >= 1) {
			JSONObject diagramObj = (JSONObject) data.get(0);
			userId = diagramObj.getBigDecimal("userId");
		}
		return userId;
   }
   
   public BigDecimal getTagId(){
	   QueryOpenDiagramCountByTag qodc = new QueryOpenDiagramCountByTag();
	   JSONObject result = qodc.queryOpenDiagramCountByTag();
		JSONArray data = result.getJSONArray("data");
		BigDecimal tagId =new BigDecimal(0);
		if (data.length() >= 1) {
			
			JSONObject obj = (JSONObject) data.get(0);
			JSONArray tagDiagramCounts = obj.getJSONArray("tagDiagramCounts");
			if(tagDiagramCounts.length()>=1){
				JSONObject tagObj = (JSONObject)tagDiagramCounts.get(0);
			    tagId = tagObj.getBigDecimal("tagId");
			}
		}
		return tagId;
   }
}
