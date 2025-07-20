package com.billing.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {
	public static Connection getConnection() {
		Connection con = null;
		try {
			// Load properties from config file
			InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties");
			Properties props = new Properties();
			props.load(input);
			
			String url = props.getProperty("db.url");
			String username = props.getProperty("db.username");
			String password = props.getProperty("db.password");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
			System.out.println("Database connected successfully");
			
		}catch(Exception e) {
			System.out.println("Database connection failed: "+e.getMessage());
		}
		return con;
	}
}