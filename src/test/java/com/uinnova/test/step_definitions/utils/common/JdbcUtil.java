package com.uinnova.test.step_definitions.utils.common;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class JdbcUtil {
	static Connection con = null;
	public static String db_type = "";
	public static String db_driver = "";
	public static String db_url = "";
	public static String db_userName = "";
	public static String db_password = "";


	/*	//原来只支持MySql的代码。 不用了
    public static String userName = null;
	public static String password = null;
	public static String mysqlPort = null;
	public static String getUrl() {
		Properties prop = new Properties();
		String server = null;
		try {
			InputStream in = new BufferedInputStream(
					com.uinnova.test.step_definitions.utils.common.QaUtil.class.getResourceAsStream("/"+Contants.CONFIGURE_FILENAME));
			prop.load(in);
			server = prop.getProperty("mysql");
			userName = prop.getProperty("mysql_userName");
			password = prop.getProperty("mysql_pwd");
			mysqlPort = prop.getProperty("mysql_port");

			in.close();
		} catch (Exception e) {
			QaUtil.log(e.getMessage());
		}
		String url = "jdbc:mysql://" + server + ":"+mysqlPort+"/db_vmdb?useUnicode=true&characterEncoding=UTF-8";
		return url;
	}

	public static Connection createMysqlConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(getUrl().toString(), userName, password);
		} catch (Exception e) {
			System.out.print("MYSQL ERROR:" + e.getMessage());
		}
		return con;
	}*/

	public static ArrayList executeQuery(String sql) {
		//Connection con = createMysqlConnection();
		Connection con = getConnection();
		Statement stmt;
		ArrayList list = new ArrayList();
		ResultSetMetaData mate = null;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			mate = rs.getMetaData();
			int columnNum = mate.getColumnCount();
			while (rs.next()) {
				HashMap map = new HashMap();
				for (int i = 1; i <= columnNum; i++) {
					map.put(mate.getColumnLabel(i), rs.getObject(i));
				}
				list.add(map);
			}
			con.close();
		} catch (SQLException e) {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return list;
	}

	public static void executeUpdate(String sql){
		//Connection con = createMysqlConnection();
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			if(db_type.equals("Oracle10G")){
				conn.commit();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 匹配不同的数据类型
	 * @return
	 */
	private static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(db_driver).newInstance();
			conn = DriverManager.getConnection(db_url, db_userName, db_password);
		} catch (Exception e) {
			e.printStackTrace();
			fail("数据库连接失败");
		} 	
		return conn;
	}
}
