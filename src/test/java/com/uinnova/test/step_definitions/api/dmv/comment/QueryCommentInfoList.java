package com.uinnova.test.step_definitions.api.dmv.comment;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * @author wsl
 * 查询视图的评论
 * 2017-12-11
 *
 */
public class QueryCommentInfoList extends RestApi{
	public JSONObject queryCommentInfoList(String diagramName){
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		JSONObject param = new JSONObject();
		JSONObject cdt = new JSONObject();
		cdt.put("diagramId", diagramId);
		param.put("cdt", cdt);
		String url=":1511/tarsier-vmdb/dmv/comment/queryCommentInfoList";
		return doRest(url, param.toString(), "POST");
	}

}
