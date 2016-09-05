package com.sqk.test;

import org.junit.Test;

import com.sqk.dao.UserDao;
import com.sqk.model.User;

public class testUserDao {
	
	@Test
	public void testAddUser() {
		UserDao userDao = new UserDao();
		User user = new User(0, "王倩", "123456", "wq@163.com", "北京", "女", "1995-03-05");
		boolean flag = userDao.addUser(user);
		System.out.println(flag);
	}
	
	@Test
	public void testLongin() {
		UserDao userDao = new UserDao();
		User user= new User();
		user.setEmail("sqk_55@163.com");
		user.setPassword("123456");
		User loginUser = userDao.login(user);
		boolean flag = false;
		if(null != loginUser) {
			flag = true;
		}
		System.out.println(flag);
	}
}
