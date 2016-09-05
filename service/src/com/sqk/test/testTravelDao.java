package com.sqk.test;

import org.json.JSONStringer;
import org.junit.Test;

import com.sqk.dao.MessageDao;
import com.sqk.dao.TravelDao;
import com.sqk.model.User;

public class testTravelDao {
	
	@Test
	public void testSearch() {
		User user = new User();
		user.setUserId(1);
		TravelDao travelDao = new TravelDao();
		JSONStringer json = travelDao.searchTravel(user);
		System.out.println(json.toString());
	} 

}
