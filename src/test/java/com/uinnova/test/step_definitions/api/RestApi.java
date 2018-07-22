package com.uinnova.test.step_definitions.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

/**
 * @author wsl
 * Api的基类
 *
 */
public class RestApi {
	/**
	 * @param url
	 * @param parameter
	 * @param method
	 * @return
	 * 调用tarsier接口， 主要用户判断接口返回是否成功
	 * 期望接口成功时使用
	 */
	public JSONObject doRest(String url, String parameter, String method){
		String result = getRestResult(url, parameter, method);
		JSONObject resultObj = (new AssertResult()).assertRes(result);
		if (!resultObj.has("success")){
			fail("接口返回值错误："+resultObj.toString());
		}
		assertEquals(resultObj.toString(),new Boolean("true"),resultObj.getBoolean("success"));
		return resultObj;
	}
	
	/**
	 * 期望接口失败时使用
	 * @param url
	 * @param parameter
	 * @param method
	 * @return
	 */
	public JSONObject doFailRest(String url, String parameter, String method, String keyWord){
		String result = getRestResult(url, parameter, method);
		JSONObject resultObj = (new AssertResult()).assertRes(result);
		QaUtil.report("\n doFailRest result:"+result+"\n"+" doFailRest kw:"+keyWord);		
		assertEquals(500, resultObj.getInt("code"));
		assertFalse(resultObj.getBoolean("success"));
		//assertEquals(keyWord, resultObj.getString("message"));
		assertTrue(resultObj.getString("message").indexOf(keyWord) != -1);
		return null;
	}
	
	/**
	 * 调用接口
	 * @param url
	 * @param parameter
	 * @param method
	 * @return
	 */
	private String getRestResult(String url, String parameter, String method){
		String result  = QaUtil.loadRest(url, parameter, method);
		return result;
	}

}
