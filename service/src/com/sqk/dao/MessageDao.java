package com.sqk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import com.sqk.model.Message;
import com.sqk.model.User;
import com.sqk.util.JDBCUtil;

public class MessageDao {
	JDBCUtil jdbcUtil = new JDBCUtil();
	Connection connection = jdbcUtil.connect;
	Statement statement = jdbcUtil.statement;
	ResultSet rSet = jdbcUtil.rs;

	// 查找所有的旅游动态
	public JSONStringer searchMain(User user) {
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			// pStatement = connection
			// .prepareStatement("select b.* from (select message.message_id,message.owner_id,user.user_name,message.create_time,message.message_text,message.good_num from user,message where message.owner_id in(select friend_id from friend where my_id=?) and message.owner_id = user.user_id union select message.message_id,message.owner_id,user.user_name,message.create_time,message.message_text,message.good_num from user,message where message.owner_id = user.user_id and message.owner_id=?) b order by b.create_time desc;");
			pStatement = connection
					.prepareStatement("select b.* from (select message.message_id,message.owner_id,user.user_name,message.create_time,message.message_text from user,message where message.owner_id = user.user_id) b order by b.create_time desc;");
			// pStatement.setInt(1, user.getUserId());
			// pStatement.setInt(2, user.getUserId());
			rSet = pStatement.executeQuery();
			try {
				jsonString.array();
				while (rSet.next()) {
					jsonString.object().key("message_id")
							.value(rSet.getInt("message_id")).key("owner_id")
							.value(rSet.getString("owner_id"))
							.key("owner_name")
							.value(rSet.getString("user_name"))
							.key("create_time")
							.value(rSet.getTimestamp("create_time"))
							.key("message_text")
							.value(rSet.getString("message_text"))
							.endObject();
				}
				jsonString.endArray();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询失败！");
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

	// 通过用户获取该用户的所有动态
	public JSONStringer searchOneself(User user) {
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			pStatement = connection
					.prepareStatement("select b.* from (select message.message_id,message.owner_id,user.user_name,message.create_time,message.message_text from user,message where message.owner_id = user.user_id and message.owner_id=?) b order by b.create_time desc;");
			pStatement.setInt(1, user.getUserId());
			rSet = pStatement.executeQuery();
			try {
				jsonString.array();
				while (rSet.next()) {
					jsonString.object().key("message_id")
							.value(rSet.getInt("message_id")).key("owner_id")
							.value(rSet.getString("owner_id"))
							.key("owner_name")
							.value(rSet.getString("user_name"))
							.key("create_time")
							.value(rSet.getTimestamp("create_time"))
							.key("message_text")
							.value(rSet.getString("message_text"))
							.endObject();
				}
				jsonString.endArray();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询失败！");
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

	public boolean sendMessage(User user, String content) {
		boolean flag = false;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("insert into message(Owner_id,Message_text) values(?,?)");
			pStatement.setInt(1, user.getUserId());
			pStatement.setString(2, content);
			int line = pStatement.executeUpdate();
			if (1 == line) {
				flag = true;
			}
		} catch (SQLException e) {
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

		return flag;
	}
}
