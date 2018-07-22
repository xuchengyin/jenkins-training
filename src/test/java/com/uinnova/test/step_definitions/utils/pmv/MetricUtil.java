package com.uinnova.test.step_definitions.utils.pmv;

import java.util.List;

/**
 * Metrics工具类
 * 
 * @author KYN
 *
 */

public class MetricUtil {
	/**
	 * 根据kpiclass,metric获得指标值
	 * 
	 * @param kpiclass,metric
	 * @return list类型数据
	 */

	public List getMetricValue(String kpiclass, String metric) {
		InfluxdbJDBC influxDB = new InfluxdbJDBC();
		// 查询数据
		String sql = "select mean(value) as value from " + metric + " where kpiclass='" + kpiclass + "'";
		List result = influxDB.InfluxQuery(sql,"pmvdb");
		return result;
	}

	/**
	 * 根据metric删除measurement
	 * 
	 * @param metric
	 * @return
	 */
	public String deleteMetricValue(String metric) {
		InfluxdbJDBC influxDB = new InfluxdbJDBC();
		// 删除数据
		String sql = "drop  measurement "+"\""+metric+"\"";
		
		String result = influxDB.deleteMeasurementData(sql);
		return result;
	}

	/**
	 * 查询所有measurement
	 * 
	 * @param
	 * @return list类型数据
	 */
	public List searchMeasurement() {
		InfluxdbJDBC influxDB = new InfluxdbJDBC();
		// 查询measurements
		String sql = "SHOW MEASUREMENTS";
		List result = influxDB.InfluxQuery(sql,"pmvdb");
		return result;
	}
	/**
	 * 查询measurement是否存在
	 * 
	 * @param
	 * @return list类型数据
	 */
	public List checkMeasurement(String metric) {
		InfluxdbJDBC influxDB = new InfluxdbJDBC();
		// 查询measurements
		String sql = "SHOW MEASUREMENTS WITH MEASUREMENT ="+"\""+metric+"\"";		
		List result = influxDB.InfluxQuery(sql,"pmvdb");
		return result;
	}
	/**
	 * 根据metric查询所有指标信息
	 * 
	 * @param metric
	 * @return list类型数据
	 */
	public List checkMetrics(String metric) {
		InfluxdbJDBC influxDB = new InfluxdbJDBC();
		// 查询measurements
		String sql = "select * from " + metric + "";
		List result = influxDB.InfluxQuery(sql,"pmvdb");
		return result;
	}
}
