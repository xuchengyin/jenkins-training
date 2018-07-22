package com.uinnova.test.step_definitions.testcase.dmv.dynamicClass;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ciClass.QueryDynamicClassAndCiTree;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 编写时间:2018-04-27
 * 编写人:sunsl
 * 功能介绍:DMV查询树状图
 */
public class Scenario_dynamicClass {
	private JSONObject result;
    /*==================Scenario: DynamicClass_树状图_查询===============*/
	@When("^查询树状图$")
	public void queryDynamicClassAndCiTree(){
		QueryDynamicClassAndCiTree queryDynamicClassAndCiTree = new QueryDynamicClassAndCiTree();
		result = queryDynamicClassAndCiTree.queryDynamicClassAndCiTree();
	}
	
	@Then("^树状图正确被查出$")
	public void checkQueryDynamicClassAndCiTree(){
		String sql ="SELECT ID,TAG_NAME, DIR_ID, TAG_TYPE, DEF_DESC, IS_VALID, INV_REASON, BUILD_STATUS, DOMAIN_ID, DATA_STATUS, CREATOR, MODIFIER, CREATE_TIME, MODIFY_TIME FROM CC_CI_TAG_DEF where TAG_NAME = " +QaUtil.domain_id +" AND TAG_TYPE = 61  AND  DOMAIN_ID = "+QaUtil.domain_id + " and DATA_STATUS = 1  order by TAG_NAME ";
		List list = JdbcUtil.executeQuery(sql);
		JSONArray data = result.getJSONArray("data");
		int sum = 0;
		for(int i = 0; i < data.length(); i++){
			JSONObject obj = data.getJSONObject(i);
			JSONArray children = obj.getJSONArray("children");
			for(int j = 0; j < children.length(); j++){
				JSONObject chObj = (JSONObject)children.get(j);
				if(chObj.getInt("type")==1){
					JSONArray chld = chObj.getJSONArray("children");
					for(int h = 0; h <chld.length(); h++){
						JSONObject chiObj = (JSONObject)chld.get(h);
						if(chiObj.getInt("type") ==1){
							JSONArray hhObj = chiObj.getJSONArray("children");
							for(int k = 0; k< hhObj.length(); k ++){
								JSONObject kkObj = (JSONObject)hhObj.get(k);
								JSONArray kkchildren = kkObj.getJSONArray("children");
								if(kkObj.getInt("type")==1){
									for(int m = 0; m< kkchildren.length(); m ++){
										JSONObject mmObj = (JSONObject)kkchildren.get(m);
										if(mmObj.getInt("type")==2){
										   sum = sum + 1;
										   continue;
									
									    }else{
									    	JSONArray bbchildren = mmObj.getJSONArray("children");
									    	for(int b = 0; b<bbchildren.length();b++){
									    		JSONObject bbObj = (JSONObject)bbchildren.get(b);
									    		if(bbObj.getInt("type")==2){
									    			sum = sum +1;
									    			continue;
									    		}else{
									    			JSONArray ccchildren = bbObj.getJSONArray("children");
									    			for(int c =0; c<ccchildren.length();c++){
									    				JSONObject ccObj = (JSONObject)ccchildren.get(b);
											    		if(ccObj.getInt("type")==2){
											    			sum = sum +1;
											    			continue;
									    			   }else{
//									    				  
									    			   }
									    		  }
									    	}
									    }
								     }
							}
						}else{
								sum = sum + 1;
								continue;
								}
							}
						}else{
							sum = sum + 1;
							continue;
						}
					}
				}else{
					sum = sum + 1;
					continue;
				}
			}
		}
		
		assertEquals(list.size(), sum);
	}
}
