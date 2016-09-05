package com.sqk.test;

import org.junit.Test;

import com.sqk.dao.GPSDao;
import com.sqk.model.User;

public class testGPSDao {
	
	@Test
	public void testSearchNear() {
		GPSDao gpsDao = new GPSDao();
		User user = new User();
		user.setUserId(1);
		gpsDao.searchNearUser(user);
	}

}
