package com.sqk.model;

import java.sql.Date;

/**
 * User实体类
 * 
 * @author SongQiankun
 */
public class User {
	private int userId;
	private String userName;
	private String password;
	private String Email;
	private String address;
	private String sex;
	private String brithday;

	public User() {
	}

	public User(int id, String name, String pass, String mail, String address,
			String sex, String day) {
		this.userId = id;
		this.userName = name;
		this.password = pass;
		this.Email = mail;
		this.address = address;
		this.sex = sex;
		this.brithday = day;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", password=" + password + ", Email=" + Email + ", address="
				+ address + ", sex=" + sex + ", brithday=" + brithday + "]";
	}
	
}
