package com.uinnova.test.step_definitions.api.dmv.ciRlt;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.CommonUtil;

/**
 * 编写时间:2017-12-18
 * 编写人:wsl
 * 功能介绍:关系绘图，查询存在关系的关系和分类信息
 */
public class QueryUpAndDownRltAndClass extends RestApi{

    /**
     * @param ciCode   CI 名称
     * @param up  向上几级
     * @param down  向下几级
     * @return
     */
    public JSONObject queryUpAndDownRltAndClass(String ciCode, String up, String down){
    	if (ciCode.isEmpty())
    		return null;
    	CommonUtil cUtil = new CommonUtil();
    	if (!cUtil.isNumeric(up))
    		return null;
    	if (!cUtil.isNumeric(down))
    		return null;
    	
    	CiUtil ciU = new CiUtil();
    	BigDecimal ciId = ciU.getCiId(ciCode);
    	
    	String url = ":1511/tarsier-vmdb/dmv/ciRlt/queryUpAndDownRltAndClass";
		JSONObject param = new JSONObject();
		param.put("sCiId", String.valueOf(ciId));
		param.put("up", up);
		param.put("down", down);
    	return doRest(url, param.toString(), "POST");
    }
}
