package com.uinnova.test.step_definitions.api.dcv.dataCenter;

import com.uinnova.test.step_definitions.utils.base.CiUtil;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;


public class UploadVisio {
    /**
     * @param dataCenterid
     * @param fileName
     * @return
     */
    public JSONObject uploadVisio(String dataCenterid, String fileName){
        BigDecimal data = (new CiUtil()).getCiId(dataCenterid);
        String url=":1511/tarsier-vmdb/dcv/dcFile/uploadVisio/" + data;
        HashMap<String,String> uploadFileMap = new HashMap<String,String>();
        String filePath = UploadMax.class.getResource("/").getPath()+"/testData/dcv/upload/"+fileName;
        uploadFileMap.put(filePath, "file");
        String result = QaUtil.uploadfiles(url, uploadFileMap, null);
        AssertResult as = new AssertResult();
        return as.assertRes(result);
    }
    //	public static void main(String[] args) {
    //		BigDecimal data = new BigDecimal(123);
    //		String url=":1511/tarsier-vmdb/dcv/dcFile/uploadMax/" + data;
    //	}
}
