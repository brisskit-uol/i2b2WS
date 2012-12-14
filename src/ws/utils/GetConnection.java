package ws.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class GetConnection {

		  /** Uses JNDI and Datasource (preferred style).   */
		  static public Connection getJNDIConnection(){
		    String DATASOURCE_CONTEXT = "java:comp/env/jdbc/blah";
		    
		    Connection result = null;
		    try {
		      Context initialContext = new InitialContext();
		      if ( initialContext == null){
		        log("JNDI problem. Cannot get InitialContext.");
		      }
		      DataSource datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
		      if (datasource != null) {
		        result = datasource.getConnection();
		      }
		      else {
		        log("Failed to lookup datasource.");
		      }
		    }
		    catch ( NamingException ex ) {
		      log("Cannot get connection: " + ex);
		    }
		    catch(SQLException ex){
		      log("Cannot get connection: " + ex);
		    }
		    return result;
		  }

		  /** Uses DriverManager.  */
		  static public Connection getSimpleConnectionSQL() {
		    //See your driver documentation for the proper format of this string :
		    String DB_CONN_STRING = "jdbc:mysql://localhost:3306/pm";
		    //Provided by your driver documentation. In this case, a MySql driver is used : 
		    String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
		    String USER_NAME = "root";
		    String PASSWORD = "root";
		    
		    Connection result = null;
		    try {
		       Class.forName(DRIVER_CLASS_NAME).newInstance();
		    }
		    catch (Exception ex){
		       log("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
		    }

		    try {
		      result = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
		    }
		    catch (SQLException e){
		       log( "Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
		    }
		    return result;
		  }
		  
		  /** Uses DriverManager.  */
		  static public Connection getSimpleConnectionMSSQL(String url) {
		    //See your driver documentation for the proper format of this string :
			//jdbc:sqlserver://localhost:1433;databaseName=EMPLOYEE;selectMethod=cursor
			  
		    //String DB_CONN_STRING = "jdbc:sqlserver://10.0.2.15:1433/databaseName=test;selectMethod=cursor";
		    //String DB_CONN_STRING = "jdbc:sqlserver://10.0.2.15:1433/test";
		    //Provided by your driver documentation. In this case, a MySql driver is used : 
		    //String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		    
		    //String DB_CONN_STRING = "jdbc:jtds:sqlserver://127.0.0.1:1433/test;ssl=off;useCursors=true";
		    //String DB_CONN_STRING = "jdbc:jtds:sqlserver://192.168.0.210:1433/test;ssl=off;useCursors=true";
		    
		    String DB_CONN_STRING = "jdbc:jtds:sqlserver://" + url + ":1433/test;ssl=off;useCursors=true";
		    
		    String DRIVER_CLASS_NAME = "net.sourceforge.jtds.jdbc.Driver";
		    
		    
		    
		    //String USER_NAME = "test";
		    //String PASSWORD = "test";
		    String USER_NAME = "sa";
		    String PASSWORD = "root";
		    
		    Connection result = null;
		    try {
		       Class.forName(DRIVER_CLASS_NAME).newInstance();
		    }
		    catch (Exception ex){
		       log("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
		    }

		    try {
		      result = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
		    }
		    catch (SQLException e){
		       log( "Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
		    }
		    return result;
		  }  
		  
		  
		  
		  
		  
		  private static void log(Object aObject){
			    System.out.println(aObject);
			  }
		}
