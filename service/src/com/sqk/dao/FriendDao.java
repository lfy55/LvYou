package com.sqk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONStringer;

import com.sqk.model.User;
import com.sqk.util.JDBCUtil;

public class FriendDao {
	JDBCUtil jdbcUtil = new JDBCUtil();
	Connection connection = jdbcUtil.connect;
	Statement statement = jdbcUtil.statement;
	ResultSet rSet = jdbcUtil.rs;

	public boolean addFollow(User user, User otherUser) {
		boolean flag = false;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("insert into friend(My_id,Friend_id) values(?,?)");
			pStatement.setInt(1, user.getUserId());
			pStatement.setInt(2, otherUser.getUserId());
			int num = pStatement.executeUpdate();
			if (num == 1) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("添加关注失败！");
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

	public boolean removeFollow(User user, User otherUser) {
		boolean flag = false;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("delete from friend where My_id=? and Friend_id=?");
			pStatement.setInt(1, user.getUserId());
			pStatement.setInt(2, otherUser.getUserId());
			int num = pStatement.executeUpdate();
			if (num == 1) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("删除关注失败！");
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

	public JSONStringer getFollow(User user) {
		UserDao userDao = new UserDao();
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			pStatement = connection
					.prepareStatement("select * from friend where My_id=?");
			pStatement.setInt(1, user.getUserId());
			rSet = pStatement.executeQuery();
			System.out.println(rSet);

			jsonString.array();
			while (rSet.next()) {
				int followId = rSet.getInt("Friend_id");
				User getUser = userDao.searchById(followId);
				jsonString.object().key("getUser_id")
						.value(getUser.getUserId()).key("getUser_name")
						.value(getUser.getUserName()).key("email")
						.value(getUser.getEmail()).key("sex")
						.value(getUser.getSex()).key("city")
						.value(getUser.getAddress()).endObject();
			}
			jsonString.endArray();
			System.out.println("得到的关注列表json串:" + jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pStatement.close();
				jdbcUtil.exit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonString;
	}

	public JSONStringer getFans(User user) {
		UserDao userDao = new UserDao();
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			pStatement = connection
					.prepareStatement("select * from friend where Friend_id=?");
			pStatement.setInt(1, user.getUserId());
			rSet = pStatement.executeQuery();
			System.out.println(rSet);

			jsonString.array();
			while (rSet.next()) {
				int followId = rSet.getInt("My_id");
				User getUser = userDao.searchById(followId);
				jsonString.object().key("getUser_id")
						.value(getUser.getUserId()).key("getUser_name")
						.value(getUser.getUserName()).key("email")
						.value(getUser.getEmail()).key("sex")
						.value(getUser.getSex()).key("city")
						.value(getUser.getAddress()).endObject();
			}
			jsonString.endArray();
			System.out.println("得到的粉丝列表json串:" + jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pStatement.close();
				jdbcUtil.exit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonString;
	}

}
