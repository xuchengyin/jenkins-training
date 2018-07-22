package com.uinnova.test.step_definitions.utils.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.uinnova.test.contant.Contants;
import com.uinnova.test.step_definitions.api.base.ci.ExportCi;
import com.uinnova.test.step_definitions.api.base.integration.authority.GetCurUser;
import com.uinnova.test.step_definitions.utils.pmv.InfluxdbJDBC;

import cucumber.api.DataTable;
import cucumber.api.Scenario;


public class QaUtil {
	private static Boolean debugMode = true;
	private static Boolean hasPort = true;// 该环境是否有端口号
	public  static Boolean staticsApiTime = false; //是否统计每个api的耗时
	private static String serverHost = null;
	private static Scenario scenario;
	private static String token = null;
	private static Random rd = null;
	private static String userName = null;
	private static String password = null;
	private static String domainName = null;
	public static BigDecimal domain_id = new BigDecimal(0);
	public static BigDecimal user_id = new BigDecimal(0);


	public static void setScenario(Scenario sce) {
		scenario = sce;
	}

	public static void report(String str) {
		if (debugMode) {
			System.out.println("【" + Thread.currentThread().getStackTrace()[2].getFileName() + ":"
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName() + "】 " + str);
			System.out.println(" \r\n");
		}

	}

	public static void log(String logline) {
		if (debugMode) {
			System.out.println("【" + Thread.currentThread().getStackTrace()[2].getFileName() + ":"
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName() + "】 " + logline);
		}
	}

	public static String getServerUrl() {
		return serverHost;
	}
	public static boolean gethasPort() {
		return hasPort;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	private static String getUrl(String url){
		if (!hasPort){//去掉端口号
			if (url.startsWith(":"))
				url = url.substring(url.indexOf("/"));
		}
		url = serverHost + url;
		return url;
	}
	/**
	 * 适配https
	 * @param conn
	 * @return
	 */
	private static HttpURLConnection getHttpURLConnection(HttpURLConnection conn){
		SSLSocketFactory oldSocketFactory = null;
		HostnameVerifier oldHostnameVerifier = null;

		boolean useHttps = serverHost.toString().startsWith("https");
		if (useHttps) {
			HttpsURLConnection https = (HttpsURLConnection) conn;
			oldSocketFactory = trustAllHosts(https);
			oldHostnameVerifier = https.getHostnameVerifier();
			https.setHostnameVerifier(DO_NOT_VERIFY);
		}
		return conn;		
	}
	public static String getToken(String userName, String password) {
		String loginUrl = ":1512/vmdb-sso/user/oauth/login";
		Base64 base64 = new Base64();  
		String loginQuery = null;
		String resultStr = "";
		String tokenStr = "";

		loginUrl = getUrl(loginUrl);
		try{
			String encode_pwd =  base64.encodeToString(password.getBytes("utf-8"));
			loginQuery = "loginCode="+URLEncoder.encode(domainName+"|"+userName)+"&password="+URLEncoder.encode(encode_pwd)+"&captchaCode=";
			report("getToken " + loginUrl + " with " + loginQuery);
			URL restURL = new URL(loginUrl);

			HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
			conn=getHttpURLConnection(conn);
			//连接建立超时时间还有读取数据超时时间，单位毫秒
			conn.setConnectTimeout(6000);  
			conn.setReadTimeout(6000);  
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			PrintStream ps = new PrintStream(conn.getOutputStream());
			ps.print(loginQuery);
			ps.close();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while (null != (line = bReader.readLine())) {
				resultStr += line;
			}
			bReader.close();
			report("return " + resultStr);
			JSONObject resultJson = new JSONObject(resultStr);
			JSONObject dataJson = resultJson.getJSONObject("data");
			if (dataJson.has("token")){
				tokenStr = dataJson.getString("token");
				token = tokenStr;
				GetCurUser gu = new GetCurUser();
				JSONObject userObj = gu.getCurUser().getJSONObject("data");
				domain_id = userObj.getBigDecimal("domainId");
				user_id = userObj.getBigDecimal("id");
			}
		} catch (SocketTimeoutException e) {
			//report(e.getMessage());
			fail("连接超时："+loginUrl);
		}
		catch (IOException e) {
			//report(e.getMessage());
			fail("读取接口失败："+loginUrl);
		}catch (Exception e) {
			//report(e.getMessage());
			fail("获取token失败");
		}

		return tokenStr;
	}

	/**
	 * 覆盖java默认的证书验证
	 */
	private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[]{};
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	}};

	/**
	 * 设置不验证主机
	 */
	private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	/**
	 * 信任所有
	 * @param connection
	 * @return
	 */
	private static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
		SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			SSLSocketFactory newFactory = sc.getSocketFactory();
			connection.setSSLSocketFactory(newFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldFactory;
	}


	public static String loadRest(String url, String parameter, String method) {
		String[] parameters = { parameter };
		return loadRest(url, parameters, method);
	}

	public static String loadRest(String url, String[] parameters, String method) {
		String APIUrl = url;
		HttpURLConnection conn = null;
		if (token == null) {
			getToken(userName, password);
		}

		if (APIUrl.indexOf("http://") < 0) {
			APIUrl =getUrl(APIUrl);
		}

		report(method + " " + APIUrl);

		try {
			long startTime = System.currentTimeMillis();
			URL restURL = new URL(APIUrl);
			conn = (HttpURLConnection) restURL.openConnection();
			conn=getHttpURLConnection(conn);
			//连接建立超时时间还有读取数据超时时间，单位毫秒
			conn.setConnectTimeout(6000);  
			conn.setReadTimeout(6000);  
			conn.setRequestMethod(method);
			conn.setDoOutput(true);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("token", token);
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setRequestProperty("REQUEST_HEADER", "binary-http-client-header");//Pmv唯一性验证的时候没有这个返回405
			if (parameters.length > 0 && parameters[0] != null && parameters[0] != "") {
				PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
				for (int i = 0; i < parameters.length; i++) {
					if (parameters[0] != null && parameters[0] != "") {
						out.print(parameters[i]);
						report("param" + (i + 1) + parameters[i]);
					}
				}
				out.close();
			}
			BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line, resultStr = "";
			while (null != (line = bReader.readLine())) {
				resultStr += line;
			}
			bReader.close();
			if (QaUtil.staticsApiTime){
				long endTime = System.currentTimeMillis();
				ExcelUtil.writeExcel(url, endTime-startTime, QaUtil.class.getResource("/").getPath()+"/testData/api_time_result.xlsx");
			}
			report("return " + resultStr);
			return resultStr;
		} catch (SocketTimeoutException e) {
			fail("连接超时："+APIUrl);
			return e.getMessage();
		}catch (Exception e) {
			report(e.getMessage());
			return e.getMessage();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
	}

	public static void readProperty() throws Exception {
		Properties prop = new Properties();
		log("Read properties from configure:");
		try {
			InputStream in = new BufferedInputStream(
					com.uinnova.test.step_definitions.utils.common.QaUtil.class.getResourceAsStream("/"+Contants.CONFIGURE_FILENAME));
			prop.load(in);
			debugMode = new Boolean(prop.getProperty("debugMode"));
			hasPort = new Boolean(prop.getProperty("hasPort"));
			staticsApiTime = new Boolean(prop.getProperty("staticsApiTime"));
			serverHost = prop.getProperty("serverHost");
			userName = prop.getProperty("userName");
			password = prop.getProperty("password");
			domainName = prop.getProperty("domainName");
			JdbcUtil.db_type = prop.getProperty("ds.jdbc.vmdb.dstype");
			JdbcUtil.db_driver = prop.getProperty("ds.jdbc.vmdb.driver");
			JdbcUtil.db_url = prop.getProperty("ds.jdbc.vmdb.url");
			JdbcUtil.db_userName = prop.getProperty("ds.jdbc.vmdb.user");
			JdbcUtil.db_password = prop.getProperty("ds.jdbc.vmdb.passwd");
			InfluxdbJDBC.url = prop.getProperty("influxdb.url");
			InfluxdbJDBC.influx_db = prop.getProperty("influxdb.db");
			assertNotNull("读取debugMode参数值为空", debugMode);
			assertNotNull("读取debugMode参数值为空", debugMode);
			assertNotNull("读取staticsApiTime参数值为空", staticsApiTime);
			assertNotNull("读取hasPort参数值为空", hasPort);
			assertNotNull("读取server参数值为空", serverHost);
			assertNotNull("读取userName参数值为空", userName);
			assertNotNull("读取password参数值为空", password);
			assertNotNull("读取domainName参数值为空", domainName);
			assertNotNull("读取db_type参数值为空", JdbcUtil.db_type);
			assertNotNull("读取db_driver参数值为空", JdbcUtil.db_driver);
			assertNotNull("读取db_url参数值为空", JdbcUtil.db_url);
			assertNotNull("读取db_userName参数值为空", JdbcUtil.db_userName);
			assertNotNull("读取db_password参数值为空", JdbcUtil.db_password);
			in.close();
		} catch (Exception e) {
			log(e.getMessage());
		}

		log("debugMode=" + debugMode.toString());
		log("serverHost=" + serverHost);
	}

	@SuppressWarnings("finally")
	public static String uploadfiles(String actionUrl, Map<String, String> uploadFileMap, Map<String, String> textMap) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "----------------7e11311630464";

		DataOutputStream ds = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (token == null) {
			getToken(userName, password);
		}

		if (actionUrl.indexOf("http://") < 0) {
			actionUrl = getUrl(actionUrl);
		}

		try {
			// 统一资源
			URL url = new URL(actionUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection=getHttpURLConnection(httpURLConnection);
			//连接建立超时时间还有读取数据超时时间，单位毫秒
			httpURLConnection.setConnectTimeout(6000);  
			httpURLConnection.setReadTimeout(6000);  
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			httpURLConnection.setDoInput(true);
			// 设置是否向httpUrlConnection输出
			httpURLConnection.setDoOutput(true);
			// Post 请求不能使用缓存
			httpURLConnection.setUseCaches(false);
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码连接参数
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");

			// 设置请求内容类型
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			httpURLConnection.setRequestProperty("token", token);
			httpURLConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64; Trident/7.0; rv:11.0) like Gecko");
			httpURLConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			//			httpURLConnection.s

			// 设置DataOutputStream
			ds = new DataOutputStream(httpURLConnection.getOutputStream());

			if (uploadFileMap != null) {
				Iterator<Map.Entry<String, String>> iter_file = uploadFileMap.entrySet().iterator();
				while (iter_file.hasNext()) {

					Map.Entry<String, String> entry = iter_file.next();
					String uploadFilePath = entry.getKey();
					String fileType = entry.getValue();
					if (uploadFilePath == null) {
						continue;
					}
					report("start upload file...........");
					report("Upload FILE:" + uploadFilePath + " to " + actionUrl);
					ds.writeBytes(twoHyphens + boundary + end);
					log(twoHyphens + boundary + end);

					File file = new File(uploadFilePath);
					String filename = file.getName();
					ds.write(("Content-Disposition: form-data; " + "name=\"" + fileType + "\";filename=\""+ filename + "\"" + end).getBytes("utf-8"));
					//					ds.writeBytes("Content-Disposition: form-data; " + "name=\"" + fileType + "\";filename=\""+filename + "\"" + end);
					log("Content-Disposition: form-data; " + "name=\"" + fileType + "\";filename=\"" + filename + "\""+ end);
					ds.writeBytes(end);
					log(end);
					FileInputStream fStream = new FileInputStream(uploadFilePath);
					int bufferSize = 1024;
					byte[] buffer = new byte[bufferSize];
					int length = -1;
					while ((length = fStream.read(buffer)) != -1) {
						ds.write(buffer, 0, length);
					}
					ds.writeBytes(end);
					fStream.close();
				}
			}

			if (textMap != null) {
				Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; name=\"" + inputName + "\"" + end);
					ds.writeBytes(end);
					ds.writeBytes(inputValue);
					log(twoHyphens + boundary + end);
					log("Content-Disposition: form-data; name=\"" + inputName + "\"" + end);
					log(end);
					log(inputValue);
				}
			}
			ds.writeBytes(end + twoHyphens + boundary + twoHyphens + end);
			log(end + twoHyphens + boundary + twoHyphens + end);
			ds.flush();
			if (httpURLConnection.getResponseCode() >= 300) {
				resultBuffer.append(httpURLConnection.getResponseCode() + " ");
				resultBuffer.append(httpURLConnection.getResponseMessage());
				throw new Exception(
						"HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}
			if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() < 300) {
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
				reader = new BufferedReader(inputStreamReader);
				tempLine = null;
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
				}
			}
		} catch (SocketTimeoutException e) {
			fail("连接超时："+actionUrl);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ds != null) {
				try {
					ds.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			report("return: " + resultBuffer.toString());
			return resultBuffer.toString();
		}
	}

	public static String getUTF8StringFromGBKString(String gbkStr) {  
		try {  
			return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");  
		} catch (UnsupportedEncodingException e) {  
			throw new InternalError();  
		}  
	}  

	public static byte[] getUTF8BytesFromGBKString(String gbkStr) {  
		int n = gbkStr.length();  
		byte[] utfBytes = new byte[3 * n];  
		int k = 0;  
		for (int i = 0; i < n; i++) {  
			int m = gbkStr.charAt(i);  
			if (m < 128 && m >= 0) {  
				utfBytes[k++] = (byte) m;  
				continue;  
			}  
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
			utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
			utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
		}  
		if (k < utfBytes.length) {  
			byte[] tmp = new byte[k];  
			System.arraycopy(utfBytes, 0, tmp, 0, k);  
			return tmp;  
		}  
		return utfBytes;  
	}  

	/**
	 * 
	 * @param num
	 *            string个数
	 * @param sev
	 *            每个string长度
	 * @return List
	 * 
	 *         字符串组成为随机的字母或数字(也可能都是数字/字母)
	 */
	public static ArrayList getRandomLetterString(int num, int sev) {
		rd = new Random();
		ArrayList letter = new ArrayList(
				Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
						"s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
		ArrayList stringList = new ArrayList();
		for (int i = 0; i < num; i++) {
			String StringName = "";
			for (int j = 0; j < sev; j++) {
				StringName += letter.get(rd.nextInt(36));
			}
			stringList.add(StringName);
		}
		return stringList;
	}

	/**
	 * 
	 * @param urlPath
	 *            下载路径
	 * @param downloadDir
	 *            下载存放目录
	 * @return void
	 */
	@SuppressWarnings("finally")
	public static Boolean downloadFile(String urlPath, String downloadDir) {
		File file = null;
		BufferedInputStream bin = null;
		OutputStream out = null;
		Boolean ret = false;
		if (urlPath.indexOf("http://") < 0) {
			urlPath = serverHost + urlPath;
		}
		if (token == null) {
			getToken(userName, password);
		}
		report("download " + urlPath + " to " + downloadDir);
		try {
			// 统一资源
			URL url = new URL(urlPath);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection = getHttpURLConnection(httpURLConnection);
			httpURLConnection.setConnectTimeout(6000);  
			httpURLConnection.setReadTimeout(6000); 
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("GET");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.setRequestProperty("token", token);
			httpURLConnection.connect();
			// 文件大小
			int fileLength = httpURLConnection.getContentLength();
			// 文件名
			String filePathUrl = httpURLConnection.getURL().getFile();
			String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf('/') + 1);
			bin = new BufferedInputStream(httpURLConnection.getInputStream());
			String path = URLDecoder.decode(
					QaUtil.class.getResource(downloadDir).getPath() + File.separatorChar + fileFullName, "UTF-8");
			file = new File(path);
			out = new FileOutputStream(file);
			int size = 0;
			int len = 0;
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) {
				len += size;
				out.write(buf, 0, size);
			}
			bin.close();
			out.close();
			ret = true;

		} catch (SocketTimeoutException e) {
			fail("连接超时："+urlPath);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return ret;
		}

	}

	public static Boolean downloadFile_urlWithoutFileName(String urlPath,String filePath){
		File file = null;
		BufferedInputStream bin = null;
		Boolean ret = false;

		if (urlPath.indexOf("http://") < 0) {
			urlPath =getUrl(urlPath);
		}

		if (token == null) {
			getToken(userName, password);
		}

		report("download " + urlPath + " to " + filePath);

		try {
			// 统一资源
			URL url = new URL(urlPath);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection = getHttpURLConnection(httpURLConnection);
			httpURLConnection.setConnectTimeout(6000);  
			httpURLConnection.setReadTimeout(6000); 
			// 设定请求的方法，默认是GET
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setReadTimeout(6000*60);
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("token", token);
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();

			bin = new BufferedInputStream(httpURLConnection.getInputStream());

			DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));

			byte[] buf = new byte[1024];

			while (bin.read(buf)!=-1){
				out.write(buf);
			}

			out.flush();
			out.close();
			bin.close();
			ret = true;
		}catch (SocketTimeoutException e) {
			fail("连接超时："+urlPath);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	//从字符串数组中统计各字符串数量
	private static HashMap<String,Integer> getStringNum(ArrayList<String> s){
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		for(String st : s){
			int i=1;
			if(map.get(st)!=null){
				i = map.get(st)+1;
			}
			map.put(st, i);
		}
		return map;
	}

	//按照ruleNum分类，获取rule分组中item的数量
	public static HashMap<String, Integer> getNumMap(DataTable ruleTable, String colName) {
		List<Map<String, String>> ruleTableList = ruleTable.asMaps(String.class, String.class);
		ArrayList<String> ruleNumList = new ArrayList<String>();
		for (int i = 0; i < ruleTableList.size(); i++) {
			String ruleNum = ruleTableList.get(i).get(colName);
			// String ruleNum = ruleTableList.get(i).get("ruleNum");
			ruleNumList.add(ruleNum);
		}

		HashMap<String, Integer> ruleNumMap = getStringNum(ruleNumList);
		return ruleNumMap;
	}

	/*定义：获取各excel下各sheet的数量
	 * 
	 * */
	public static JSONObject getNumJSONObj(DataTable ruleTable, String excelName, int startRow, int tableExcelRows,String colName) {

		List<Map<String, String>> ruleTableList = ruleTable.asMaps(String.class, String.class);

		JSONObject excelObj = new JSONObject();
		int colNumFlag = 1;
		for (int i = startRow ; i < tableExcelRows; i++) {
			String name = ruleTableList.get(i).get(colName);
			if(!excelObj.has(name)) {
				colNumFlag = 1;
			}
			excelObj.put(name, colNumFlag);

			colNumFlag++;
		}
		return excelObj;
	}

	//按照cucumberTable,统计table中各excel对应的sheet及sheet对应的各行数。
	public static JSONObject getExcelSheetNum(DataTable table) {
		List<Map<String, String>> tableList = table.asMaps(String.class, String.class);
		HashMap<String, Integer> excelMap = QaUtil.getNumMap(table, "excelName");
		JSONObject excelSheetObj = new JSONObject();
		for(int i=0;i<tableList.size();i++) {
			String excelName = tableList.get(i).get("excelName");
			int excelRows = Integer.parseInt(excelMap.get(excelName).toString());
			JSONObject excelNameObj = QaUtil.getNumJSONObj(table,excelName,i,(i+excelRows),"sheetName");
			excelSheetObj.put(excelName, excelNameObj);
			i = i+(excelRows-1);
		}
		return excelSheetObj;
	}

	/**
	 * 退出登录
	 */
	private static void logOut(){
		String url = ":1512/vmdb-sso/user/oauth/logout";
		String beforeUrl = serverHost+":1511/tarsier-vmdb/vmdb/base/ci/ci.html?code=0201&token="+token;
		String result = QaUtil.loadRest(url+"?beforeUrl="+URLEncoder.encode(beforeUrl), "", "GET");
		token="";
	}


	/**
	 * @param domain
	 * @param userName
	 * @param password
	 * 切换用户
	 */
	public static void switchUser(String domain, String userName, String password) {
		logOut();
		domainName = domain;
		getToken(userName, password);	
	}

	public static void switchDefaultUser() throws Exception {
		logOut();
		readProperty();
		getToken(userName, password);	
	}

	/****************************************** CI导出全部的时候用*****************/
	/**
	 * CI导出全部的时候用， 用downloadFile_urlWithoutFileName 有时候导出的文件错误打不开。
	 * @param urlPath
	 * @param filePath
	 * @return
	 */
	public static  boolean downloadFile_urlWithoutFileName2(String urlPath,String filePath) {
		if (urlPath.indexOf("http://") < 0) {
			urlPath =getUrl(urlPath);
		}
		if (token == null) {
			getToken(userName, password);
		}
		report("download " + urlPath + " to " + filePath);
		try{
			URL httpurl=new URL(urlPath);
			HttpURLConnection httpConn=(HttpURLConnection)httpurl.openConnection();
			httpConn = getHttpURLConnection(httpConn);
			httpConn.setConnectTimeout(6000);  
			httpConn.setReadTimeout(6000); 
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod("GET");// 设置URL请求方法
			//可设置请求头
			httpConn.setRequestProperty("Content-Type", "application/octet-stream");
			httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpConn.setRequestProperty("Charset", "UTF-8");
			httpConn.setRequestProperty("token", token);
			//可设置请求头
			byte[] file =input2byte(httpConn.getInputStream());
			writeBytesToFile(file,filePath);
		}catch (SocketTimeoutException e) {
			fail("连接超时："+urlPath);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static  byte[] input2byte(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	public static  File writeBytesToFile(byte[] b, String outputFile) {
		File file = null;
		FileOutputStream os = null;
		try {
			file = new File(outputFile);
			os = new FileOutputStream(file);
			os.write(b);
		} catch (Exception var13) {
			var13.printStackTrace();
		} finally {
			try {
				if(os != null) {
					os.close();
				}
			} catch (IOException var12) {
				var12.printStackTrace();
			}

		}
		return file;
	}
	/****************************************** CI导出全部的时候用 end*****************/
}
