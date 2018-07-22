package com.uinnova.test.step_definitions.utils.pmv;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;
import org.influxdb.InfluxDBFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * influxdb操作类
 */
public class InfluxdbJDBC {
	public static String username = "admin";// 用户名
	public static String password = "admin";// 密码
	public static String url = "";// 连接地址
	public static String influx_db = "";// 数据库

	private InfluxDB influxDB;

	public InfluxdbJDBC() {

	}

	/** 连接时序数据库；获得InfluxDB **/
	public InfluxDB influxDbBuild() {
		if (influxDB == null) {
			influxDB = InfluxDBFactory.connect(url, username, password);
		}
		return influxDB;
	}

	/**
	 * 设置数据保存策略 defalut 策略名 /database 数据库名/ 30d 数据保存时限30天/ 1 副本个数为1/ 结尾DEFAULT 表示
	 * 设为默认的策略
	 */
	public void createRetentionPolicy() {
		String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
				"defalut", influx_db, "30d", 1);
		this.query(command,influx_db);
	}

	/**
	 * 创建数据库
	 */
	public void createDatabase() {
		influxDB.createDatabase(influx_db);
	}

	/**
	 * 删除数据库
	 * @param dbName
	 */
	public void deleteDatabase(String dbName) {
		influxDB.deleteDatabase(dbName);
	}

	/**
	 * 查询语句
	 * @param command
	 * @return
	 */
	public QueryResult query(String command,String influx_db) {
		influxDbBuild();
		return influxDB.query(new Query(command, influx_db));
	}
	/**
	 * 查询封装
	 * @param QuerySql
	 * @return List
	 */
	public ArrayList InfluxQuery(String QuerySql,String influx_db) {
		influxDbBuild();
		// 查询数据
		QueryResult queryResult = query(QuerySql, influx_db);

		ArrayList list = new ArrayList();
		List<QueryResult.Result> results = queryResult.getResults();		
		if (results != null && results.size() > 0) {
			for (QueryResult.Result result : results) {				
				List<Series> series = result.getSeries();
				if (series!=null) {				
				for (Series s : series) {
					List<String> columns = s.getColumns();// 字段名
					List<List<Object>> values = s.getValues();// 字段字集合
					for (List<Object> n : values) {
						Map<String, String> map = new HashMap<String, String>();
						for (int i = 0; i < columns.size(); i++) {
							map.put(columns.get(i), n.get(i) == null ? "" : n.get(i).toString());

						}
						list.add(map);

					}

				}}
				else{
					return null;
				}
			}
		}
		return list;
	}

	/**
	 * 删除语句
	 * @param command
	 * @return 返回错误信息
	 */
	public String deleteMeasurementData(String command) {
		influxDbBuild();
		QueryResult result = influxDB.query(new Query(command, influx_db));
		return result.getError();
	}

	/**
	 * 插入数据
	 * @param measurement
	 * a Point in a fluent manner
	 * @param tagsToAdd
	 * the Map of tags to add
	 * @param fields
	 * the fields to add
	 */
	public void insert(String measurement, Map<String, String> tagsToAdd, Map<String, Object> fields) {
		influxDbBuild();
		Point.Builder builder = Point.measurement(measurement).tag(tagsToAdd);
		if (fields != null && !fields.isEmpty()) {
			builder.fields(fields);
		}
		influxDB.write(influx_db, "", builder.build());
	}

}