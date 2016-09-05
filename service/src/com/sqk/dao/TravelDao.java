package com.sqk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONStringer;

import com.sqk.model.User;
import com.sqk.util.JDBCUtil;

public class TravelDao {
	JDBCUtil jdbcUtil = new JDBCUtil();
	Connection connection = jdbcUtil.connect;
	Statement statement = jdbcUtil.statement;
	ResultSet rSet = jdbcUtil.rs;

	// 获取与当前用户有关的所有游记
	public JSONStringer searchTravel(User user) {
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			pStatement = connection
					.prepareStatement("select b.* from (select travel.travel_id,user.user_name,travel.time,travel.title,travel.text from user,travel where travel.owner_id in(select friend_id from friend where my_id=?) and travel.owner_id = user.user_id union select travel.travel_id,user.user_name,travel.time,travel.title,travel.text from user,travel where travel.owner_id = user.user_id and travel.owner_id=?) b order by b.time desc;");
			pStatement.setInt(1, user.getUserId());
			pStatement.setInt(2, user.getUserId());
			rSet = pStatement.executeQuery();
			try {
				jsonString.array();
				while (rSet.next()) {
					jsonString.object().key("travel_id")
							.value(rSet.getInt("travel_id")).key("owner_name")
							.value(rSet.getString("user_name"))
							.key("create_time")
							.value(rSet.getTimestamp("time"))
							.key("travel_title").value(rSet.getString("title"))
							.key("travel_text").value(rSet.getString("text"))
							.endObject();
				}
				jsonString.endArray();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonString;
	}

	// 获取当前用户的所有游记
	public JSONStringer searchOneself(User user) {
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			pStatement = connection
					.prepareStatement("select b.* from (select travel.travel_id,user.user_name,travel.time,travel.title,travel.text from user,travel where travel.owner_id = user.user_id and travel.owner_id=?) b order by b.time desc;");
			pStatement.setInt(1, user.getUserId());
			rSet = pStatement.executeQuery();
			try {
				jsonString.array();
				while (rSet.next()) {
					jsonString.object().key("travel_id")
							.value(rSet.getInt("travel_id")).key("owner_name")
							.value(rSet.getString("user_name"))
							.key("create_time")
							.value(rSet.getTimestamp("time"))
							.key("travel_title").value(rSet.getString("title"))
							.key("travel_text").value(rSet.getString("text"))
							.endObject();
				}
				jsonString.endArray();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonString;
	}
	
	public boolean sendTravel(User user, String title,String content) {
		boolean flag = false;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("insert into travel(Owner_id,Title,Text) values(?,?,?)");
			pStatement.setInt(1, user.getUserId());
			pStatement.setString(2, title);
			pStatement.setString(3, content);
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
