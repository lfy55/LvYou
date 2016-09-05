package com.sqk.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sqk.model.User;
import com.sqk.util.JDBCUtil;

/**
 * 实现与User相关的逻辑处理
 * 
 * @author SongQiankun
 * 
 */
public class UserDao {
	JDBCUtil jdbcUtil = new JDBCUtil();
	Connection connection = jdbcUtil.connect;
	Statement statement = jdbcUtil.statement;
	ResultSet rSet = jdbcUtil.rs;

	public boolean addUser(User user) {
		boolean flag = false;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("insert into User(User_name,Password,Email,Address,Sex,Brithday) values(?,?,?,?,?,?)");
			pStatement.setString(1, user.getUserName());
			pStatement.setString(2, user.getPassword());
			pStatement.setString(3, user.getEmail());
			pStatement.setString(4, user.getAddress());
			pStatement.setString(5, user.getSex());
			pStatement.setString(6, user.getBrithday());
			int num = pStatement.executeUpdate();
			if (num == 1) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("添加用户失败！");
		} finally {
			try {
				pStatement.close();
				jdbcUtil.exit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public User login(User user) {
		User loginUser=null;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("select * from user where Email=? and Password=?");
			pStatement.setString(1, user.getEmail());
			pStatement.setString(2, user.getPassword());
			rSet = pStatement.executeQuery();
			if (rSet.next()) {
				loginUser = new User();
				loginUser.setUserId(rSet.getInt("User_id"));
				loginUser.setUserName(rSet.getString("User_name"));
				loginUser.setPassword(rSet.getString("Password"));
				loginUser.setEmail(rSet.getString("Email"));
				loginUser.setAddress(rSet.getString("Address"));
				loginUser.setSex(rSet.getString("Sex"));
				loginUser.setBrithday(rSet.getDate("Brithday").toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("登录验证失败！");
		} finally {
			try {
				pStatement.close();
				jdbcUtil.exit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return loginUser;
	}
	
	public User searchByEmail(String email) {
		User user = null;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("select * from user where Email=?");
			pStatement.setString(1, email);
			rSet = pStatement.executeQuery();
			if (rSet.next()) {
				user = new User();
				user.setUserId(rSet.getInt("User_id"));
				user.setUserName(rSet.getString("User_name"));
				user.setPassword(rSet.getString("Password"));
				user.setEmail(rSet.getString("Email"));
				user.setAddress(rSet.getString("Address"));
				user.setSex(rSet.getString("Sex"));
				user.setBrithday(rSet.getDate("Brithday").toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("登录验证失败！");
		} finally {
			try {
				pStatement.close();
				//jdbcUtil.exit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}

	public User searchById(int userId) {
		User user = null;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("select * from user where User_id=?");
			pStatement.setInt(1, userId);
			rSet = pStatement.executeQuery();
			if (rSet.next()) {
				user = new User();
				user.setUserId(rSet.getInt("User_id"));
				user.setUserName(rSet.getString("User_name"));
				user.setPassword(rSet.getString("Password"));
				user.setEmail(rSet.getString("Email"));
				user.setAddress(rSet.getString("Address"));
				user.setSex(rSet.getString("Sex"));
				user.setBrithday(rSet.getDate("Brithday").toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("通过ID获取用户失败！");
		} finally {
			try {
				pStatement.close();
				//jdbcUtil.exit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}

	public boolean changePass(User user,String nPass) {
		boolean flag = false;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("update user set Password=? where User_id=?");
			pStatement.setString(1, nPass);
			pStatement.setInt(2, user.getUserId());
			int line = pStatement.executeUpdate();
			if(1 == line) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("修改密码失败！");
		} finally {
			try {
				pStatement.close();
				jdbcUtil.exit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
}
