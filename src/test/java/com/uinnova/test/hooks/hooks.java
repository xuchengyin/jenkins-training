package com.uinnova.test.hooks;


import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.Scenario;
import cucumber.api.java.Before;

public class hooks {

	@Before
	public void keepScenario(Scenario sce) {
		QaUtil.setScenario(sce);
		QaUtil.report("Scenario:::" + sce.getName());
	}
}
