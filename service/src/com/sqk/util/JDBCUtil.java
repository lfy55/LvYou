package com.sqk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 提供数据库连接的接口
 * @author SongQiankun 
 *
 */
public class JDBCUtil {
	private String url = "jdbc:mysql://localhost:3306/android?useUnicode=true&characterEncoding=utf8";
	public Connection connect = null;
	public Statement statement = null;
	public ResultSet rs = null;

	public JDBCUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载JDBC驱动
			connect = DriverManager.getConnection(url, "root", "123456");
			statement = connect.createStatement();
			System.out.println("Loading MySQL ....");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Loading MySQL Error!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Loading MySQL Error!");
		}
		System.out.println("Loading MySQL Success!");
	}

	public void exit() {
		try {
			connect.close();
			statement = null;
			rs = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
