package com.uinnova.test.step_definitions.testcase.dmv.comment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.comment.QueryCommentInfoList;
import com.uinnova.test.step_definitions.api.dmv.comment.RemoveCommentById;
import com.uinnova.test.step_definitions.api.dmv.comment.SaveOrUpdateComment;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author wsl
 * 视图评论场景
 *
 */
public class Scenario_comment {
	private String commentId;
	private JSONArray commentInfoList;
	
	@When("给名称为\"(.*)\"的视图新建评论\"(.*)\"")
	public void saveOrUpdateComment(String diagramName, String commentDesc){
		SaveOrUpdateComment saveOrUpdateComment = new SaveOrUpdateComment();
		JSONObject result = saveOrUpdateComment.saveOrUpdateComment(diagramName, commentDesc);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		commentId = String.valueOf(result.getBigDecimal("data"));
	} 

	@Then("成功给名称为\"(.*)\"的视图新建评论")
	public void checkSaveOrUpdateComment(String diagramName){
		String sql = "select ID from vc_comment where id=" + commentId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertTrue(list.size()==1);
	}

	@When("查询名称为\"(.*)\"的视图评论")
	public void queryCommentInfoList(String diagramName){
		QueryCommentInfoList queryCommentInfoList = new QueryCommentInfoList();
		JSONObject result = queryCommentInfoList.queryCommentInfoList(diagramName);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
		commentInfoList = result.getJSONArray("data");
	}

	@Then("成功查询名称为\"(.*)\"的视图评论")
	public void checkQueryCommentInfoList(String diagramName){
		BigDecimal diagramId = getDiagramIdByName(diagramName);
		String sql = "select * from vc_diagram where data_status=1 and status=1 and id = " + diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id + " AND USER_ID ="+QaUtil.user_id;
		ArrayList diagramList = JdbcUtil.executeQuery(sql);		
		assertEquals(1, diagramList.size());
		//比较评论
		String commentSql ="select * from vc_comment  where DIAGRAM_ID ="+ diagramId +" AND USER_ID = " + QaUtil.user_id + " and DOMAIN_ID = "+ QaUtil.domain_id+" order by id desc ";
		ArrayList commentList = JdbcUtil.executeQuery(commentSql);	
		HashMap map = new HashMap();
		for (int k=0; k<commentList.size(); k++){
			HashMap commentMap = (HashMap) commentList.get(k);
			map.put(commentMap.get("ID").toString(), commentMap);
		}
		
		
		JSONArray commentArray =commentInfoList.getJSONObject(0).getJSONArray("comments");
		assertEquals(commentArray.length(), commentList.size());
		for (int i=0; i<commentList.size(); i++){
			JSONObject commentObj = commentArray.getJSONObject(i);
			String commentId = commentObj.getBigDecimal("id").toString();
			assertTrue(map.containsKey(commentId));
			HashMap commentMap = (HashMap) map.get(commentId);//(HashMap) commentList.get(i);
			assertEquals(commentObj.getBigDecimal("id"), commentMap.get("ID"));
			assertEquals(commentObj.getBigDecimal("userId"), commentMap.get("USER_ID"));
			assertEquals(commentObj.getBigDecimal("domainId"), commentMap.get("DOMAIN_ID"));
			assertEquals(commentObj.getBigDecimal("diagramId"), commentMap.get("DIAGRAM_ID"));
			assertEquals(commentObj.getString("userCode"),commentMap.get("USER_CODE"));
			assertEquals(commentObj.getString("userName"),commentMap.get("USER_NAME"));
			assertEquals(commentObj.getString("comDesc"),commentMap.get("COM_DESC"));
			assertEquals(commentObj.getBigDecimal("createTime"),commentMap.get("CREATE_TIME"));
			assertEquals(commentObj.getBigDecimal("comTime"),commentMap.get("COM_TIME"));
			assertEquals(commentObj.getBigDecimal("modifyTime"),commentMap.get("MODIFY_TIME"));
		}
		//比较评论人员信息
		String opsSql ="select * from sys_op  where DATA_STATUS=1 "
				+" and id in (select DISTINCT(user_id) from vc_comment  where DIAGRAM_ID ="+ diagramId +" and DOMAIN_ID = "+ QaUtil.domain_id+")"
				+" order by id ";
		ArrayList opsList = JdbcUtil.executeQuery(opsSql);	
		JSONArray opsArray =commentInfoList.getJSONObject(0).getJSONArray("ops");
		assertEquals(opsArray.length(), opsList.size());
		for (int i=0; i<opsList.size(); i++){
			JSONObject opObj = opsArray.getJSONObject(i);
			HashMap opMap = (HashMap) opsList.get(i);
			assertEquals(opObj.getBigDecimal("id"), opMap.get("ID"));
			if (opMap.get("LAST_LOGIN_LOG_ID")!=null){
				assertEquals(opObj.getBigDecimal("lastLoginLogId"), opMap.get("LAST_LOGIN_LOG_ID"));
			}	
			assertEquals(opObj.getBigDecimal("superUserFlag"), opMap.get("SUPER_USER_FLAG"));
			assertEquals(opObj.getBigDecimal("userDomainId"), opMap.get("USER_DOMAIN_ID"));
			assertEquals(opObj.getBigDecimal("status"), opMap.get("STATUS"));
			assertEquals(opObj.getBigDecimal("opKind"), opMap.get("OP_KIND"));
			assertEquals(opObj.getBigDecimal("lockFlag"), opMap.get("LOCK_FLAG"));
			assertEquals(opObj.getBigDecimal("domainId"), opMap.get("DOMAIN_ID"));
			assertEquals(opObj.getBigDecimal("dataStatus"), opMap.get("DATA_STATUS"));
			assertEquals(opObj.getBigDecimal("isUpdatePwd"), opMap.get("IS_UPDATE_PWD"));

			//assertEquals(opObj.getString("loginPasswd"),opMap.get("LOGIN_PASSWD"));
			assertEquals(opObj.getString("emailAdress"),opMap.get("EMAIL_ADRESS"));
			assertEquals(opObj.getString("opCode"),opMap.get("OP_CODE"));
			if (opMap.get("LOGIN_AUTH_CODE")!=null){
				assertEquals(opObj.getString("loginAuthCode"),opMap.get("LOGIN_AUTH_CODE"));
			}
			assertEquals(opObj.getString("opName"),opMap.get("OP_NAME"));
			if(opMap.get("SHORT_NAME")==null){
			    
			}else{
				assertEquals(opObj.getString("shortName"),opMap.get("SHORT_NAME"));
			}
			if(opMap.get("NOTES") == null){
			
			}else{
				assertEquals(opObj.getString("notes"),opMap.get("NOTES"));
			}
			if(opMap.get("MOBILE_NO") == null){
				
			}else{
				assertEquals(opObj.getString("mobileNo"),opMap.get("MOBILE_NO"));
			}
			
			assertEquals(opObj.getString("loginCode"),opMap.get("LOGIN_CODE"));

			assertEquals(opObj.getBigDecimal("createTime"),opMap.get("CREATE_TIME"));
			if (opMap.get("LAST_LOGIN_TIME")!=null){
				assertEquals(opObj.getBigDecimal("lastLoginTime"),opMap.get("LAST_LOGIN_TIME"));
			}
			assertEquals(opObj.getString("modifier"),opMap.get("MODIFIER"));
			assertEquals(opObj.getBigDecimal("modifyTime"),opMap.get("MODIFY_TIME"));
		}

	}


	@When("删除视图名称为\"(.*)\"的评论")
	public void removeCommentById(String diagramName){
		//diagramId = getDiagramIdByName(diagramName);
		RemoveCommentById removeCommentById = new RemoveCommentById();
		JSONObject result = removeCommentById.removeCommentById(commentId);
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);
	}

	@Then("成功删除视图名称为\"(.*)\"的评论")
	public void checkRemoveCommentById(String diagramName){
		//diagramId = getDiagramIdByName(diagramName);
		String sql = "select ID from vc_comment where id=" + commentId +" and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		assertTrue(list.size()==0);
	}

	private BigDecimal getDiagramIdByName(String diagramName){
		DiagramUtil diagramUtil = new DiagramUtil();
		BigDecimal diagramId = diagramUtil.getDiagramIdByName(diagramName);
		return diagramId;
	}


}
