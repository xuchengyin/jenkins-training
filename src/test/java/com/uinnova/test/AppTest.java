package com.uinnova.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.uinnova.test.step_definitions.api.base.ciClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.api.base.tagRule.RemoveDirById;
import com.uinnova.test.step_definitions.api.base.tagRule.SaveOrUpdateDefInfo;
import com.uinnova.test.step_definitions.api.dmv.diagram.RemoveDiagramByIds;
import com.uinnova.test.step_definitions.testcase.base.obj.ciClass.Scenario_ciClass;
import com.uinnova.test.step_definitions.utils.base.CiClassUtil;
import com.uinnova.test.step_definitions.utils.base.RltUtil;
import com.uinnova.test.step_definitions.utils.base.TagRuleUtil;
import com.uinnova.test.step_definitions.utils.common.ExcelUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;

import cucumber.api.CucumberOptions;
import cucumber.api.DataTable;
import cucumber.api.junit.Cucumber;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "json:target/result.json" }, features = {"classpath:features"})
//@CucumberOptions(plugin = { "pretty", "json:target/result.json" }, features = {"classpath:features"},tags={"@Smoke"})

/*------------------------------------------------BASE------------------------------------------*/
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/auth/Auth.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/auth/Auth.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/ciExcelBatchImport/importCisAndRlts.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/ciExcelBatchImport/importCisAndRlts.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/cirltBatchExcelImport/cirltBatchExcelImport.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/cirltBatchExcelImport/cirltBatchExcelImport.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/rlt/ciRlt.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/image/Image.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/image/Image.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/image/ImageDir.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/image/ImageDir.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/integration/iface.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/kpi/Kpi.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/kpi/Kpi.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/kpi/Tpl.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/license/License.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/license/License.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/monitor/Event.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/monitor/severity/Severity.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/navigationbar/Navigationbar.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/navigationbar/Navigationbar.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/obj/Ci.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/obj/Ci.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/obj/CiClass.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/obj/CiClass.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/obj/CiClassDir.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/obj/CiClassDir.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/rlt/ciRlt.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/rlt/rltClass.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/sys/Vframe.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/tagRule/tagRule.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/tagRule/tagRule.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/user/User.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target//result.json"},features={"classpath:features/BASE/user/User.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/visual/Visual.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/sys/Operationlog.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/sys/Updatelog.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/sys/Loginlog.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/BASE/pv/Pv.feature"})


/*------------------------------------------------CMV------------------------------------------*/
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/ciRltRule/CiRltRule.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/ciQualityRule/ciQualityRule.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/ciQualityRule/ciQualityRule.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/configureQuery/ConfigureQuery.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/configureQuery/CiDetails.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/Dashboard/Dashboard.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/CMV/dynamicClass/dynamicClass.feature"})

/*------------------------------------------------DMV------------------------------------------*/
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/migrateDiagram/MigrateDiagram.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/group/Group.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Import.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Import.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Comb.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Comb.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Template.feature"},tags={"@Debug"}) 
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Template.feature"}) 
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Container.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Img.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/image.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/image.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/comment/Comment.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/comment/Comment.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Canvas_Top_Menu.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Canvas_Top_Menu.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_QueryImg.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_QueryImg.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_CI.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_CI.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Canvas.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Canvas.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Line.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Line.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_RltClass.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_RltClass.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Share.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Share.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Preview.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/image/Image_Preview.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/diagram/My.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/diagram/My.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/theme/Theme.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/theme/Theme.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/theme/Theme_Kpi.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/group/Group.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/group/Group.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/diagram/Square.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/diagram/Square.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/count/Count.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/workbench/Workbench.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/wall/Wall.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/file/Dir.feature"},tags={"@Debug"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/dynamicClass/dynamicClass.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DMV/file/file.feature"})
/*------------------------------------------------DCV------------------------------------------*/
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DCV/"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DCV/productModel/UploadProduct.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/DCV/productModel/UploadProduct.feature"})


/*------------------------------------------------noah------------------------------------------*/
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/noah/Event.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/noah/Performance.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/noah/EventToMq.feature"})


/*------------------------------------------------EMV------------------------------------------*/
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/EMV/event/ackEventAlarm.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/EMV/group/"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/EMV/severity/EmvSeverity.feature"})



/*------------------------------------------------PMV------------------------------------------*/
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/PMV/kpiValueRlt/kpiValueRlt.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/PMV/kpiValueRlt/Performance.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/PMV/KpiView/KpiView.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/PMV/StandardTag/StandardTag.feature"})
//@CucumberOptions(plugin={"pretty","json:target/result.json"},features={"classpath:features/PMV/TagManagement/TagManagement.feature"})
public class AppTest {
	@BeforeClass
	public static void setUp() throws Exception {
		//读取配置信息
		QaUtil.readProperty();
		if (QaUtil.staticsApiTime){
			ExcelUtil.cleanExcel(AppTest.class.getResource("/").getPath()+"/testData/api_time_result.xlsx");//清空统计api时间文件
		}

		QaUtil.report("=============开始初始化ci数据!!!=============");

		String dirName = "业务领域";
		String ciClassName_app = "Application";
		String ciClassName_cluster = "Cluster";
		String ciClassName_spec = "s@&_-";
		

		String imageName = "Default";

		CiClassUtil cl = new CiClassUtil();
		if(cl.getCiClassId("Application").compareTo(new BigDecimal(0))!=0){
			// 删除Application和Cluster分类
			cl.deleteCiClassAndCi(ciClassName_app);

		}
		if (cl.getCiClassId("Cluster").compareTo(new BigDecimal(0))!=0) {
			cl.deleteCiClassAndCi(ciClassName_cluster);
		}
		if (cl.getCiClassId("s@&_-").compareTo(new BigDecimal(0))!=0) {
			cl.deleteCiClassAndCi(ciClassName_spec);
		}

		String filePath_app = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+ciClassName_app+".xls";
		String filePath_cluster = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+ciClassName_cluster+".xls";
		String filePath_spec = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+ciClassName_spec+".xls";

		cl.createCiClassAndImportCi(dirName,ciClassName_app,imageName,filePath_app);
		cl.createCiClassAndImportCi(dirName,ciClassName_cluster,imageName,filePath_cluster);
		cl.createCiClassAndImportCi(dirName,ciClassName_spec,imageName,filePath_spec);

		QaUtil.report("=============ci数据初始化完成!!!=============");

		String tagDirName = "testTag";
		String tagName_APP = "APP";
		String tagName_cls = "Clu";
		String tagName_spe1 = "@&_-~！@#￥%……&*（））——，";
		String tagName_spe2 = "【、；‘。、《》？’】234567是￥";

		if((new TagRuleUtil()).getTagId(tagName_APP).compareTo(new BigDecimal(0))!=0){
			(new RemoveDirById()).removeDirById(tagDirName);
		}



		List<List<String>> appList  =  Arrays.asList(Arrays.asList("commons","ruleNum","ciClsName", "ciFriendName","attrName","ruleOp","ruleVal"),
				Arrays.asList("app","1",ciClassName_app, ciClassName_app,"CI Code","like","%银%")); 
		DataTable appTable  = DataTable.create(appList);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagDirName, tagName_APP);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagName_APP, appTable);

		List<List<String>> cluList  =  Arrays.asList(Arrays.asList("commons","ruleNum","ciClsName", "ciFriendName","attrName","ruleOp","ruleVal"),
				Arrays.asList("cluster","1",ciClassName_cluster, ciClassName_cluster,"Name","like","%BJ%")); 
		DataTable cluTable  = DataTable.create(cluList);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagDirName, tagName_cls);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagName_cls, cluTable);



		List<List<String>> spec1List  =  Arrays.asList(Arrays.asList("commons","ruleNum","ciClsName", "ciFriendName","attrName","ruleOp","ruleVal"),
				Arrays.asList("cluster","1",ciClassName_spec, ciClassName_spec,"整数",">","1")); 
		DataTable spec1Table  = DataTable.create(spec1List);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagDirName, tagName_spe1);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagName_spe1, spec1Table);



		List<List<String>> spec2List  =  Arrays.asList(Arrays.asList("commons","ruleNum","ciClsName", "ciFriendName","attrName","ruleOp","ruleVal"),
				Arrays.asList("cluster","1",ciClassName_spec, ciClassName_spec,"CI Code","like","%i%")); 
		DataTable spec2Table  = DataTable.create(spec2List);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagDirName, tagName_spe2);
		(new SaveOrUpdateDefInfo()).saveDefInfo(tagName_spe2, spec2Table);

		QaUtil.report("=============tag数据初始化完成!!!=============");



		QaUtil.report("======初始化关系数据=====");
		String[] rltClassName = {"AppRlt","特殊"};
		(new RltUtil()).initCiRltData(rltClassName, "importCiRltData.xls");
//		(new RltUtil()).initCiRltData(speClassName,"Application","s@&_-");
		//Thread.sleep(5000);
		QaUtil.report("============***************初始化数据准备完成******************=========================");

		QaUtil.report("============***************清空DMV回收站 预防视图授权数不够的情况******************=========================");
		RemoveDiagramByIds removeDiagramByIds = new RemoveDiagramByIds();
		removeDiagramByIds.removeDiagramByIds();
		QaUtil.report("============***************清空DMV回收站结束******************=========================");
		
		initDMVData();
		initEMVData();

	}


	//	@AfterClass  //为了适应多租户
	//	public static void teanDown() throws Exception {
	//		String dirName = "业务领域";
	//		String ciClassName_app = "Application";
	//		String ciClassName_cluster = "Cluster";
	//		String ciClassName_spec = "s@&_-";
	//		String imageName = "Default";
	//		CiClassUtil cl = new CiClassUtil();
	//		if(cl.getCiClassId("Application").compareTo(new BigDecimal(0))!=0){
	//			// 删除Application和Cluster分类
	//			cl.deleteCiClassAndCi(ciClassName_app);
	//
	//		}
	//		if (cl.getCiClassId("Cluster").compareTo(new BigDecimal(0))!=0) {
	//			cl.deleteCiClassAndCi(ciClassName_cluster);
	//		}
	//		if (cl.getCiClassId("s@&_-").compareTo(new BigDecimal(0))!=0) {
	//			cl.deleteCiClassAndCi(ciClassName_spec);
	//		}
	//	}
	
	/**
	 * 初始化DMV数据
	 */
	private static void initDMVData()
	{
		String dirName = "业务领域";
		String ciClassName_loadBalance = "DMV专用-负载均衡";
		String ciClassName_logicServer = "DMV专用-逻辑服务器";
		String ciClassName_application = "DMV专用-应用";
		String imageName = "Default";
		CiClassUtil cl = new CiClassUtil();
		if(cl.getCiClassId(ciClassName_loadBalance).compareTo(new BigDecimal(0))!=0){			
			cl.deleteCiClassAndCi(ciClassName_loadBalance);
		}
		if (cl.getCiClassId(ciClassName_logicServer).compareTo(new BigDecimal(0))!=0) {
			cl.deleteCiClassAndCi(ciClassName_logicServer);
		}
		if (cl.getCiClassId(ciClassName_application).compareTo(new BigDecimal(0))!=0) {
			cl.deleteCiClassAndCi(ciClassName_application);
		}
		String filePath_loadBalance = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+ciClassName_loadBalance+".xls";
		String filePath_logicServer = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+ciClassName_logicServer+".xls";
		String filePath_application = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+ciClassName_application+".xls";
		cl.createCiClassAndImportCi(dirName,ciClassName_loadBalance,imageName,filePath_loadBalance);
		cl.createCiClassAndImportCi(dirName,ciClassName_logicServer,imageName,filePath_logicServer);
		cl.createCiClassAndImportCi(dirName,ciClassName_application,imageName,filePath_application);
		SaveOrUpdate su = new SaveOrUpdate();
		List<String> labelList = new ArrayList<String>();
		labelList.add("应用名称");
		su.updateClassLabel(ciClassName_application, labelList);
		
		labelList = new ArrayList<String>();
		labelList.add("IP地址");
		su.updateClassLabel(ciClassName_logicServer, labelList);
		
		String[] rltClassName = {"DMV专用-依赖"};
		(new RltUtil()).initCiRltData(rltClassName, "DMV专用-CIRelation-依赖.xls");
		
	}
	
	/**
	 * 初始化EMV数据
	 */
	private static void initEMVData()
	{
		String dirName = "业务领域";
		String ciClassName_emv = "EMV专用";		
		String imageName = "Default";
		CiClassUtil cl = new CiClassUtil();
		if(cl.getCiClassId("EMV专用").compareTo(new BigDecimal(0))!=0){			
			cl.deleteCiClassAndCi(ciClassName_emv);
		}	
		String filePath_emv = Scenario_ciClass.class.getResource("/").getPath()+"testData/ci/"+ciClassName_emv+".xls";		
		cl.createCiClassAndImportCi(dirName,ciClassName_emv,imageName,filePath_emv);
	}

}