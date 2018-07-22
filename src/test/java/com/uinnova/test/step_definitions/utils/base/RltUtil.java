package com.uinnova.test.step_definitions.utils.base;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.uinnova.test.step_definitions.api.base.ciRlt.ExportImportMsg;
import com.uinnova.test.step_definitions.api.base.ciRlt.ImportCiRlt;
import com.uinnova.test.step_definitions.api.base.ciRlt.QueryPage;
import com.uinnova.test.step_definitions.api.base.ciRlt.RemoveByClassId;
import com.uinnova.test.step_definitions.api.base.ciRltClass.RemoveById;
import com.uinnova.test.step_definitions.api.base.ciRltClass.SaveOrUpdate;
import com.uinnova.test.step_definitions.utils.common.JdbcUtil;
import com.uinnova.test.step_definitions.utils.common.QaUtil;
import com.uinnova.test.step_definitions.utils.common.TxtUtil;

import cucumber.api.DataTable;

/**
 * 关系工具类
 * @author uinnova
 *
 */
public class RltUtil {

	/**
	 * @param rltClassName
	 * @param sourceCiClass
	 * @param targetCiClass
	 */
	public void initCiRltData(String []rltClassName, String fileName){
		//创建关系分类
		for (int i = 0; i < rltClassName.length; i++) {
			BigDecimal rltId =  (new RltClassUtil()).getRltClassId(rltClassName[i]);
			if(rltId.compareTo(new BigDecimal(0))!=0){
				JSONObject rc = (new RemoveByClassId()).removeByClassId(rltClassName[i]);
				if(rc.getBoolean("success")){
					QaUtil.report("====="+rltClassName[i]+"分类数据清除成功====");
					JSONObject ri = (new RemoveById()).removeById(rltClassName[i]);
					if(ri.getBoolean("success")){
						QaUtil.report("====="+rltClassName[i]+"关系分类清除成功====");
					}
				}
			}
		}


		//
		//		//创建关系
		for (int i = 0; i < rltClassName.length; i++) {
			JSONObject su = (new SaveOrUpdate()).saveOrUpdateWithoutAttr(rltClassName[i]);
		}

		//		rltId =  (new RltClassUtil()).getRltClassId(rltClassName);

		//		//创建分类之间的关系
		//		SaveOrUpdateCiClassRlt saveOrUpdateCiClassRlt = new SaveOrUpdateCiClassRlt();
		//		JSONObject ciClassRltResult = saveOrUpdateCiClassRlt.saveOrUpdateCiClassRlt(sourceCiClass, targetCiClass, rltClassName);
		//		assertTrue(ciClassRltResult.getBoolean("success"));
		//
		//		//创建ci关系数据
		//		BigDecimal appId = (new CiClassUtil()).getCiClassId(sourceCiClass);
		//		BigDecimal cluId = (new CiClassUtil()).getCiClassId(targetCiClass);
		//		String appSql = "SELECT ID FROM cc_ci c where CLASS_ID="+appId+" and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		//		String cluSql = "SELECT ID FROM cc_ci c where CLASS_ID="+cluId+" and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		//		ArrayList appList = JdbcUtil.executeQuery(appSql);
		//		ArrayList cluList = JdbcUtil.executeQuery(cluSql);
		//		int num = appList.size()<=cluList.size()? appList.size():cluList.size();
		//		int flag = 0;
		//		for(int i=0;i<num;i++){
		//			HashMap appMap = (HashMap)appList.get(i);
		//			HashMap cluMap = (HashMap)cluList.get(i);
		//			BigDecimal sourceCiId = new BigDecimal(appMap.get("ID").toString());
		//			BigDecimal targetCiId = new BigDecimal(cluMap.get("ID").toString());
		//			JSONObject result = (new com.uinnova.test.step_definitions.api.base.ciRlt.SaveOrUpdate()).saveOrUpdate(rltId, sourceCiId, targetCiId, new JSONObject());
		//			if(result.getBoolean("success")){
		//				flag++;
		//			}
		//		}
		//		assertEquals(flag, num);
		ExportImportMsg eim = new ExportImportMsg();
		TxtUtil tu = new TxtUtil();
		ImportCiRlt icr = new ImportCiRlt();
		//这里不用关心传入那个关系名，都会导入
		String resultObj = icr.importCiRlt(rltClassName[0], RltUtil.class.getResource("/").getPath() + "testData/rlt/"+fileName);
		JSONObject obj = new JSONObject(resultObj);
		String data = obj.getString("data");
		String filePath = eim.exportImportMsg(data);
		String result =  tu.readTxt(filePath);
		if ("importCiRltData.xls".compareToIgnoreCase(fileName) ==0){
			assertTrue(result.indexOf("分类[AppRlt]处理31条数据,添加31条,忽略0条,更新0条分类[特殊]处理10条数据,添加10条,忽略0条,更新0条") != -1);
		}

	}

	/**
	 * 获得分类与分类之间关系数据的ID
	 * @param rltClassName
	 * @param sourceCiClass
	 * @param targetCiClass
	 * @return
	 */
	public BigDecimal getCiClassRltId(String rltClassName,String sourceCiClass,String targetCiClass){
		BigDecimal clsRltId = new BigDecimal(0);
		BigDecimal rltId =  (new RltClassUtil()).getRltClassId(rltClassName);
		BigDecimal sourceId = (new CiClassUtil()).getCiClassId(sourceCiClass);
		BigDecimal targetId = (new CiClassUtil()).getCiClassId(targetCiClass);
		String sql = "SELECT ID FROM cc_ci_class_rlt  where CLASS_ID = "+rltId
				+" and SOURCE_CLASS_ID="+sourceId
				+" and TARGET_CLASS_ID="+targetId
				+" and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;
		ArrayList list = JdbcUtil.executeQuery(sql);
		Map map = (Map) list.get(0);
		clsRltId = (BigDecimal) map.get("ID");
		return clsRltId;
	}


	/**
	 * 获取单条关系数据
	 * @param rltClsName
	 * @param sourceCiClass
	 * @param sourceCiCode
	 * @param targetCiClass
	 * @param targetCiCode
	 * @return
	 */
	public JSONObject getRltData(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode, DataTable table){
		QueryPage queryPage = new QueryPage();
		JSONObject result = queryPage.queryPage(rltClsName);
		result = result.getJSONObject("data");
		assertTrue(result.getInt("totalRows")>=1);
		JSONArray data = result.getJSONArray("data");
		Boolean hasRlt = false;
		JSONObject RltDataResult = new JSONObject();
		RltClassUtil rcu = new RltClassUtil();
		for (int i=0; i<data.length(); i++){
			JSONObject tempObj = data.getJSONObject(i);
			JSONObject sourceCiInfo =  tempObj.getJSONObject("sourceCiInfo");
			JSONObject targetCiInfo =  tempObj.getJSONObject("targetCiInfo");
			String sourceCiCode2 = sourceCiInfo.getJSONObject("ci").getString("ciCode");
			String targetCiCode2 = targetCiInfo.getJSONObject("ci").getString("ciCode");
			if (sourceCiCode.compareToIgnoreCase(sourceCiCode2)==0 && targetCiCode.compareToIgnoreCase(targetCiCode2)==0){
				if (table==null){
					RltDataResult = tempObj;
					hasRlt = true;
					break;
				}
				JSONObject attrs =  null;
				if (tempObj.has("attrs"))
					attrs = tempObj.getJSONObject("attrs");
				if(attrs!=null){
					int rows = table.raw().size();
					int count =0;
					int comCount = rows-1;//是子code的比较
					for (int j=1;j<rows;j++){
						List<String> row = table.raw().get(j);
						String attr=row.get(0);
						if (attrs.has(attr)){
							if(rcu.rltAttrIsCode(rltClsName, attr)){

								if (attrs.getString(attr).compareToIgnoreCase(row.get(1))==0){
									count++;
								}
							}
							else
								comCount--;
							if (count==comCount)
							{
								RltDataResult = tempObj;
								hasRlt = true;
								break;
							}
						}
					}
				}
				if (hasRlt)
					break;
			}
		}
		assertTrue(hasRlt);
		return RltDataResult;
	}


	/**
	 * 是否存在关系数据
	 * @param rltClsName
	 * @param sourceCiClass
	 * @param sourceCiCode
	 * @param targetCiClass
	 * @param targetCiCode
	 * @return
	 */
	public boolean isExistsRltData(String rltClsName,String sourceCiClass,String sourceCiCode,String targetCiClass,String targetCiCode){
		QueryPage queryPage = new QueryPage();
		JSONObject result = queryPage.queryPage(rltClsName);
		result = result.getJSONObject("data");
		JSONArray data = result.getJSONArray("data");
		Boolean hasRlt = false;
		for (int i=0; i<data.length(); i++){
			JSONObject tempObj = data.getJSONObject(i);
			JSONObject sourceCiInfo =  tempObj.getJSONObject("sourceCiInfo");
			JSONObject targetCiInfo =  tempObj.getJSONObject("targetCiInfo");
			String sourceCiCode2 = sourceCiInfo.getJSONObject("ci").getString("ciCode");
			String targetCiCode2 = targetCiInfo.getJSONObject("ci").getString("ciCode");
			if (sourceCiCode.compareToIgnoreCase(sourceCiCode2)==0 && targetCiCode.compareToIgnoreCase(targetCiCode2)==0){
				hasRlt = true;
				break;
			}
		}
		return hasRlt;
	}

	public BigDecimal getciRltId(String sourceCiCode, String targetCiCode, String rltName){
		CiUtil cu = new CiUtil();
		BigDecimal sourceCiId = cu.getCiId(sourceCiCode);
		BigDecimal targetCiId = cu.getCiId(targetCiCode);
		BigDecimal rltId =  (new RltClassUtil()).getRltClassId(rltName);
		String sql = "SELECT ID FROM CC_CI_RLT  where CLASS_ID = "+rltId
				+" and SOURCE_CI_ID="+sourceCiId
				+" and TARGET_CI_ID="+targetCiId
				+" and DATA_STATUS=1 and DOMAIN_ID = "+ QaUtil.domain_id;

		ArrayList list = JdbcUtil.executeQuery(sql);
		Map map = (Map) list.get(0);
		return (BigDecimal) map.get("ID");
	}
}
