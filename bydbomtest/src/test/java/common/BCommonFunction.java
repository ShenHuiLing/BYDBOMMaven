package common;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date; 


import java.sql.*;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class BCommonFunction {
	private ObjectMapper mapper=new ObjectMapper();
	private Map<String, String> testData=new HashMap<String, String>();
	
	private final String EXCEL_XLS = "xls";  
    private final String EXCEL_XLSX = "xlsx";
    
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    private static Logger logger=LogManager.getLogger();
    
	public static void main(String[] args)
	{

		BCommonFunction cf=new BCommonFunction();
		String filePath=cf.getProjectPath();
		filePath=filePath + "\\test-output\\" + cf.getCurrentTime(); 
		System.out.println(filePath);
		System.out.println("123456".hashCode());
		/*
        logger.trace("我是trace");
        logger.info("我是info信息");
        logger.error("我是error");
        logger.fatal("我是fatal");

        logger.trace("退出程序.");
		

		//cf.readJasonFile(EnvJsonFile.TESTDATA);
		//cf.getProperty("approver");
		//Map<String, String> testData1=new HashMap<String, String>();
		//testData1.put("approver3","shenhl");
		//testData1.put("test55", "cccc");
		//cf.writeJasonFile(EnvJsonFile.TESTDATA, testData1);
		//cf.getProperty("integration");
		//cf.connectDB();
		cf.connectDB(EnvJsonFile.BASICFILE, "integration");
		ResultSet rs=cf.queryData("select * from cust.cust_material_info cmi order by cmi.cust_material_info_id desc ");
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			int columnCount;
			columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				for(int i=0;i<columnCount;i++) {
					System.out.print(rs.getString(i+1)+"\t");
				}
				System.out.println();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cf.closeDB();
		*/
	}
	
	
	public void readJasonFile(EnvJsonFile ejf) throws Exception {
		
		try {
			 	String jsonpath;
			 	String jsonname;
			  	jsonname=ejf.getDesc();
			  	jsonpath=this.getProjectPath() + jsonname;
			  	File file=new File(jsonpath);
			  	if(file.exists())
			  		testData=mapper.readValue(file, Map.class); 
			  	else
			  		System.out.println("file doesn't exist");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void writeJasonFile(EnvJsonFile ejf, Map<String, String> jsonData) throws Exception {
		try {
			String jsonpath;
		 	String jsonname;
		  	jsonname=ejf.getDesc();
			jsonpath=this.getProjectPath() + jsonname;
			File file=new File(jsonpath);
		  	if(file.exists()) {
		  		testData=mapper.readValue(file, Map.class);
		  		testData.putAll(jsonData);
		  		mapper.writeValue(new File(jsonpath), testData);
		  	}
		  	else {
		  		System.out.println("file doesn't exist, will create one to track the test data.");
		  		mapper.writeValue(new File(jsonpath), jsonData);
		  	}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//get the path of the project
	public String getProjectPath()
	{
		String courseFile="";
		try {
	        // 获取项目路径 C:\Users\alans\git\alantest
	        File directory = new File("");// 参数为空
	        courseFile = directory.getCanonicalPath();
	        
		}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		return courseFile;
	}
	
	//return the value basing on the name from json file
	public String getProperty(String name) {
		String value="";
		value=testData.get(name);
		System.out.println(name + ":" + value);
		return value;
	}
	
	//return the time stamp 
	public String getTimeStamp()
	{
		String timeStamp="";
		timeStamp=String.valueOf(System.currentTimeMillis() / 1000);
		return timeStamp;
	}
	
	/**
	 * 
	 * @return return current time by the format "yyyymmddhhmmss"
	 */
	public String getCurrentTime() {
		String currentTime="";
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		currentTime=dateFormat.format(date);
		return currentTime;
	}
    
    /**
     * read XML file
     * @param filePath, the XML file path
     * @return Document, contains the XML nodes
     */
    public Document readXML(String filePath) {
    	try {   
    		     File f = new File(filePath);   
    		     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
    		     DocumentBuilder builder = factory.newDocumentBuilder();   
    		     Document doc = builder.parse(f);   
    		     return doc;  
    		    } catch (Exception e) {   
    		     e.printStackTrace();   
    		     return null;
    		    }   
    }
    
    public void connectDB(EnvJsonFile ejf, String env) throws Exception{
    	this.readJasonFile(ejf);
    	String url="";
		String user="";
		String password="";
    	if(env.equals("integration")) {
    		url=this.getProperty("integrationDB");
    		user=this.getProperty("integrationDBUser");
			password=this.getProperty("integrationDBPWD");
    	}else if(env.equals("local")) {
    		url=this.getProperty("localDB");
    		user=this.getProperty("localDBUser");
			password=this.getProperty("localDBPWD");
    	}
			try {
				this.conn=DriverManager.getConnection(url, user, password);
				this.stmt=this.conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
			
    	
    }
    
    public void closeDB() {
    	try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public ResultSet queryData(String sql) {
    	try {
			this.rs=this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rs;
    }
    
    public int updateData(String sql) {
    	int result=0;
		try {
			result = this.stmt.executeUpdate(sql);
			this.conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    public void connectDB() {
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			String url="jdbc:oracle:thin:@192.168.1.61:1621:xe";
			String user="system";
			String password="admin123";
			Connection conn= DriverManager.getConnection(url,user,password);
			Statement stmt = conn.createStatement();
			String sql="select * from cust.cust_material_info cmi order by cmi.cust_material_info_id desc ";
			ResultSet rs = stmt.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount=rsmd.getColumnCount();
			while(rs.next()) {
				for(int i=0;i<columnCount;i++) {
					System.out.print(rs.getString(i+1)+"\t");
				}
				System.out.println();
			}
			
			sql="update CUST.CUST_MATERIAL_INFO\r\n" + 
					"set \r\n" + 
					"CF_STATUS='APPLY_COMPLETE',\r\n" + 
					"CF_NO='P'||(select to_char(sysdate,'yyyymmddhh24miss') from dual),\r\n" + 
					"MAT_NO=(select to_char(sysdate,'yyyymmddhh24miss') from dual),\r\n" + 
					"mat_desc='mat_desc' || (select to_char(sysdate,'yyyymmddhh24miss') from dual),\r\n" + 
					"mat_endesc='mat_endesc' || (select to_char(sysdate,'yyyymmddhh24miss') from dual),\r\n" + 
					"ACTIVE_STATUS='CURRENT'\r\n" + 
					"where cust_material_info_id='21'";
			
			System.out.println(stmt.executeUpdate(sql));
			
			conn.commit();
			
			//System.out.println(stmt.execute("commit"));
			
			conn.close();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}


