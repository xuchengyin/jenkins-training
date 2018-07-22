package com.uinnova.test.step_definitions.utils.base;
import static org.junit.Assert.fail;
import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.dmv.ciRlt.QueryCiBetweenRlt;

public class SyncToNoahUtil {

	/**
	 * ci之间的关系同步
	 * @param sourceCiCode
	 * @param targetCiCode
	 * @return
	 */
	public boolean syncCiRltToNoah(String rltClassName, String sourceCiCode, String targetCiCode){
		//以下代码确认关系数据同步到noah
		boolean syncSuccessFlag = false;
		QueryCiBetweenRlt queryCiBetweenRlt = new QueryCiBetweenRlt();
		int count = 0;
		while(!syncSuccessFlag){
			JSONObject rltObj = queryCiBetweenRlt.queryCiBetweenRlt(rltClassName, sourceCiCode, targetCiCode);
			JSONArray arr = rltObj.getJSONArray("data");
			if (arr.length()>0){
				syncSuccessFlag = true;
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			if (count==20)
				break;
		}
		if (!syncSuccessFlag)
			fail("noah 未读取到ci:"+sourceCiCode+" 到："+targetCiCode+"之间的关系："+rltClassName);
		return syncSuccessFlag;
	}

}
