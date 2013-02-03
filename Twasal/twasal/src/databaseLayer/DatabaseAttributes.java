package databaseLayer;

public class DatabaseAttributes {

	private String driver;
	private String host;
	private String database;
	private String username;
	private String password;
	
	public DatabaseAttributes(String driver, String host, String database,
			String username, String password) {
		this.driver = driver;
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	
	public String getDriver() {
		return driver;
	}
	public String getHost() {
		return host;
	}
	public String getDatabase() {
		return database;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
