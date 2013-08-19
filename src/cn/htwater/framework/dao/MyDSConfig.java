package cn.htwater.framework.dao;

public class MyDSConfig {

	public static final String DRIVERCLASSNAME = "driverClassName";
	public static final String URL = "url";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	String dbName;
	String driverClassName;
	String url;
	String username;
	String password;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MyDSConfig(String dbName, String driverClassName, String url,
			String username, String password) {

		this.dbName = dbName;
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
	}
}
