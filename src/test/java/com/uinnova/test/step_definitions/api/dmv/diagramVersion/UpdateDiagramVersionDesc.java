package com.uinnova.test.step_definitions.api.dmv.diagramVersion;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.dmv.DiagramUtil;

/**
 * 编写时间:2018-03-07
 * 编写人:sunsl
 * 功能介绍:新建视图版本的更新版本类
 */
public class UpdateDiagramVersionDesc extends RestApi{
    public JSONObject updateDiagramVersionDesc(String diagramName,String versionDesc,String updateDesc,String updateVersionNo){
    	String url = ":1511/tarsier-vmdb/dmv/diagramVersion/updateDiagramVersionDesc";
    	DiagramUtil diagramUtil = new DiagramUtil();
    	BigDecimal versionId = diagramUtil.getVersionIdByVersionDesc(diagramName, versionDesc);
    	JSONObject param = new JSONObject();
    	param.put("id", versionId);
    	param.put("versionDesc", updateDesc);
    	param.put("versionNo", updateVersionNo);
    	return doRest(url,param.toString(),"POST");
    }
}
