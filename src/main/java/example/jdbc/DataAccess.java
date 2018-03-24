package example.jdbc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import oracle.jdbc.driver.OracleDriver;

public class DataAccess {
	private HashMap<String,String> configData;
	private Connection conn;
	private Statement st;
	private ResultSet rst;
	
	public DataAccess() {
		try{
			load_config();
			DriverManager.registerDriver(new OracleDriver());
			this.connect();
		}catch(Exception e){
			System.out.println(e+" in DataAccess()");
		}
	}
	// load database config to configData
	public void load_config(){
		try{ 
			// load config file for database into map
			configData = new HashMap<String,String>();
			FileReader file = new FileReader("src/main/resources/database_config");
			BufferedReader buffer = new BufferedReader(file);
			String s = buffer.readLine();
			while (s != null){
				configData.put(s.split("=")[0], s.split("=")[1]);
				s=buffer.readLine();
			}
			// close connection
			buffer.close();
			file.close();
			// config is not setup properly.
			if (configData.size() != 3){
				System.out.println("database config not setup");
			}
		}catch(Exception e){
			System.out.println(e + " in load_config");
		}	
	}
	//
	
	public void connect() throws SQLException{
		try{
			
			conn = DriverManager.getConnection(
					configData.get("connString"),
					configData.get("uname"), 
					configData.get("password"));
		}catch(Exception e){
			System.out.println(e+" in connect()");
		}
	}
	
	public void close(){
		try{
			if(st!=null && !st.isClosed()){
				st.close();
			}
			if(conn!=null && !conn.isClosed()){
				conn.close();
			}
		}catch(Exception e){
			System.out.println(e+" in close()");
		}
	}
	
	public boolean update(String query){
		try{
			st = conn.createStatement();
			int i = st.executeUpdate(query);
			return i > 0;
		}catch(SQLException sqe){
			System.out.println(sqe+" in update");
		}
		return false;
	}
	
	public ResultSet select(String query) throws SQLException{
		st = conn.createStatement();
		rst = st.executeQuery(query);
		return rst;
	}
}
