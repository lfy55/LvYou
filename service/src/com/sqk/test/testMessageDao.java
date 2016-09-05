package com.sqk.test;

import java.util.List;

import org.json.JSONStringer;
import org.junit.Test;

import com.sqk.dao.MessageDao;
import com.sqk.model.Message;
import com.sqk.model.User;

public class testMessageDao {

	@Test
	public void testSearchMain() {
		User user = new User();
		user.setUserId(1);
		MessageDao messageDao = new MessageDao();
		JSONStringer json = messageDao.searchMain(user);
		System.out.println(json.toString());
	}

}
