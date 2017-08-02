package com.decheng.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private final static Logger logger = LoggerFactory.getLogger(Utils.class);

	static {
		Properties pro = new Properties();
		InputStream in = ClassLoader.class.getResourceAsStream("/db.properties");
		try {
			pro.load(in);
			url = pro.getProperty("url");
			username = pro.getProperty("user");
			password = pro.getProperty("password");
			driver = pro.getProperty("driver");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
	}

	public static Connection getConn() {
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}

		return conn;
	}

	public static void closeDb(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
