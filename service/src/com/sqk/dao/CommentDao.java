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

public class CommentDao {
	JDBCUtil jdbcUtil = new JDBCUtil();
	Connection connection = jdbcUtil.connect;
	Statement statement = jdbcUtil.statement;
	ResultSet rSet = jdbcUtil.rs;
	
	public JSONStringer getCommentByTid(int tId) {
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			pStatement = connection
					.prepareStatement("select user.User_name,comment.time,comment.text from comment,user where comment.Owner_id=user.User_id and comment.To_travel=?");
			pStatement.setInt(1, tId);
			rSet = pStatement.executeQuery();
			try {
				jsonString.array();
				while (rSet.next()) {
					jsonString.object().key("owner_name")
							.value(rSet.getString("User_name")).key("time")
							.value(rSet.getTimestamp("time"))
							.key("text").value(rSet.getString("text"))
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
	
	public boolean sendComment(User user, String content, int tId) {
		boolean flag = false;
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("insert into comment(Owner_id,To_travel,text) values(?,?,?)");
			pStatement.setInt(1, user.getUserId());
			pStatement.setInt(2, tId);
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
