package databaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnection {

	private DatabaseAttributes db;
	private Connection con;
	
	public DbConnection(DatabaseAttributes db_att) {
		db = db_att;		
		init();
	}
	
	private void init(){
		try {
		    // Load the Driver class.
		    Class.forName(db.getDriver());
		    // If you are using any other database then load the right driver here.
		 
		    //Create the connection using the static getConnection method
		    con = DriverManager.getConnection (db.getHost()+db.getDatabase(), db.getUsername(), db.getPassword());
		        
		    System.out.println("connection done...");
		}
		catch (SQLException e) {
		    e.printStackTrace();
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public boolean excute(String query){
		Statement sta;
		try {
			sta = con.createStatement();
			return sta.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet excuteQuery (String query){
		Statement sta;
		try {
			sta = con.createStatement();
			return sta.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
