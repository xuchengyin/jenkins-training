package com.uinnova.test.step_definitions.testcase.pmv.kpiValueRlt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.noah.rest.PushPerfData;
import cucumber.api.java.en.When;

/**
 * @author kyn NOAH 推送性能数据
 */
public class Scenario_performace {

	@When("推送性能数据\"(.*)\"")
	public static void pushPerfData(String perfdate) {
		PushPerfData pushPerfData = new PushPerfData();
		JSONObject obj = new JSONObject(perfdate);
		List<JSONObject> pbs = new ArrayList<JSONObject>();
		obj.put("timestamp", System.currentTimeMillis());
		pbs.add(obj);
		org.json.JSONObject result = pushPerfData.pushPerfData(pbs.toString());
		assertTrue(result.getBoolean("success"));
		assertEquals(result.getInt("code"), -1);

	}

}