package com.uinnova.test.step_definitions.api.dcv.dataCenter;

import com.uinnova.test.step_definitions.api.RestApi;
import com.uinnova.test.step_definitions.utils.common.AssertResult;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import cucumber.deps.com.thoughtworks.xstream.mapper.Mapper;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ReceivePic extends RestApi {
    /**
     * @param fileName
     * @return
     */
    public JSONObject recrivePic(String fileName){
        Map<String, String> uploadFileMap = new HashMap<String, String>();
        String url = "1511/tarsier-vmdb/dcv/dataCenter/receivePic";
        String filePath = ReceivePic.class.getResource("/").getPath() + "testData/dcv/initData/" + fileName;
        uploadFileMap.put(filePath, "file");
        String result = QaUtil.uploadfiles(url, uploadFileMap, null);
        AssertResult as = new AssertResult();
        return as.assertRes(result);
    }
}
