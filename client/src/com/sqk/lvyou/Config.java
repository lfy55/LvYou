package com.sqk.lvyou;


public class Config {

	public static String KEY_TOKEN = null;
	public static String APP_ID = "com.sqk.LvYou";

	// 得到登录token的方法
	public static void putToken(String user) {
		KEY_TOKEN = user;
	}

	// 得到token值的方法
	public static String getToken() {
		return KEY_TOKEN;
	}
}
