package com.uinnova.test.step_definitions.api.dmv.comment;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * @author wsl
 * 保存评论
 *
 */
public class SaveOrUpdateComment extends RestApi{
	public JSONObject saveOrUpdateComment(String diagramName, String comDesc){
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		JSONObject param = new JSONObject();
		param.put("diagramId", diagramId);
		param.put("comDesc", comDesc);
		String url=":1511/tarsier-vmdb/dmv/comment/saveOrUpdateComment";
		return doRest(url, param.toString(), "POST");
	}
}
