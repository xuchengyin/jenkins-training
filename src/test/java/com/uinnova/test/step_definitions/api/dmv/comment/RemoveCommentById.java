package com.uinnova.test.step_definitions.api.dmv.comment;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;

/**
 * @author wsl
 * 删除评论
 *
 */
public class RemoveCommentById extends RestApi{
	public JSONObject removeCommentById(String commentId){
		
		String url=":1511/tarsier-vmdb/dmv/comment/removeCommentById";
		return doRest(url, commentId, "POST");
	}
}
