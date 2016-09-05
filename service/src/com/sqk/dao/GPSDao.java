package com.sqk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONStringer;

import com.sqk.model.User;
import com.sqk.util.JDBCUtil;

public class GPSDao {
	JDBCUtil jdbcUtil = new JDBCUtil();
	Connection connection = jdbcUtil.connect;
	Statement statement = jdbcUtil.statement;
	ResultSet rSet = jdbcUtil.rs;

	public String sendGPS(User user, double lat, double lon) {
		String result = "无操作";
		PreparedStatement pStatement = null;
		try {
			pStatement = connection
					.prepareStatement("select * from GPS where user_id=?");
			pStatement.setInt(1, user.getUserId());
			rSet = pStatement.executeQuery();
			int row = 0;
			while (rSet.next()) {
				row++;
			}
			System.out.println("GPS表中用户" + user.getUserId() + "的数据数量" + row);
			if (0 == row) {
				pStatement = connection
						.prepareStatement("insert into GPS(user_id,latitude,longitude) values(?,?,?)");
				pStatement.setInt(1, user.getUserId());
				pStatement.setDouble(2, lat);
				pStatement.setDouble(3, lon);
				int line = pStatement.executeUpdate();
				if (1 == line) {
					result = "插入一条数据";
				}
			} else if (1 == row) {
				pStatement = connection
						.prepareStatement("update GPS set latitude=?,longitude=? where user_id=?");
				pStatement.setDouble(1, lat);
				pStatement.setDouble(2, lon);
				pStatement.setInt(3, user.getUserId());
				int line = pStatement.executeUpdate();
				if (1 == line) {
					result = "修改一条数据";
				}
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
		return result;
	}

	public JSONStringer searchNearUser(User user) {
		UserDao userDao = new UserDao();
		double myLat = 0;
		double myLon = 0;
		PreparedStatement pStatement = null;
		JSONStringer jsonString = new JSONStringer();
		try {
			pStatement = connection
					.prepareStatement("select * from GPS where user_id=?");
			pStatement.setInt(1, user.getUserId());
			rSet = pStatement.executeQuery();
			while (rSet.next()) {
				myLat = rSet.getDouble("latitude");
				myLon = rSet.getDouble("longitude");
			}
			System.out.println("经度：" + myLat + "维度：" + myLon);
			pStatement = connection
					.prepareStatement("select * from GPS where user_id <> ?");
			pStatement.setInt(1, user.getUserId());
			rSet = pStatement.executeQuery();
			System.out.println(rSet);

			jsonString.array();
			while (rSet.next()) {
				double latdistance = rSet.getDouble("latitude") - myLat;
				double londistance = rSet.getDouble("longitude") - myLon;
				if (latdistance < 1 && latdistance > -1 && londistance < 1
						&& londistance > -1) {
					int uId = rSet.getInt("user_id");
					User getUser = userDao.searchById(uId);
					jsonString.object().key("getUser_id")
							.value(getUser.getUserId()).key("getUser_name")
							.value(getUser.getUserName()).key("email")
							.value(getUser.getEmail()).key("sex")
							.value(getUser.getSex()).key("city")
							.value(getUser.getAddress()).endObject();
				}
			}
			jsonString.endArray();
			System.out.println("得到的其他用户json串:" + jsonString);
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
