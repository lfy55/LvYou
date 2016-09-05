package com.sqk.lvyou.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.text.StaticLayout;
import android.widget.Toast;

import com.sqk.lvyou.activitys.ActivityChangePass;
import com.sqk.lvyou.activitys.ActivityComment;
import com.sqk.lvyou.activitys.ActivityMain;
import com.sqk.lvyou.activitys.ActivityNear;
import com.sqk.lvyou.activitys.ActivityOneMessage;
import com.sqk.lvyou.activitys.ActivityOneTravel;
import com.sqk.lvyou.activitys.ActivityRevert;
import com.sqk.lvyou.activitys.ActivitySendMessage;
import com.sqk.lvyou.activitys.ActivitySendTravel;
import com.sqk.lvyou.activitys.ActivityTravel;
import com.sqk.lvyou.activitys.ActivityUserHome;
import com.sqk.lvyou.activitys.ActivityUserList;
import com.sqk.lvyou.activitys.ActivityUserM;

public class Tools {
	private static String back;
	private static boolean flag;

	// 获取所有旅游动态
	public static void getMessage(final String uId) {

		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/MessageServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uId));
					params.add(new BasicNameValuePair("operate", "1"));

					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getMessage得到的Json串：" + back);
					}
					// return flag;
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("message_id", jsonObject.getInt("message_id"));
						map.put("owner_id", jsonObject.getInt("owner_id"));
						map.put("owner_name",
								jsonObject.getString("owner_name"));
						map.put("time", jsonObject.getString("create_time"));
						map.put("text", jsonObject.getString("message_text"));
						System.out.println("map:" + map);
						list.add(map);
					}
					System.out.println("getMessage应该返回的List：" + list);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(list);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityMain.handler.sendMessage(msg);
			}
		}).start();
	}

	// 通过用户ID（邮箱）获取该用户的所有旅游动态
	public static void getOneMessage(final String uID) {
		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/OneMessageServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getOneMessage得到的Json串：" + back);
					}
					// return flag;
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("message_id", jsonObject.getInt("message_id"));
						map.put("owner_id", jsonObject.getInt("owner_id"));
						map.put("owner_name",
								jsonObject.getString("owner_name"));
						map.put("time", jsonObject.getString("create_time"));
						map.put("text", jsonObject.getString("message_text"));
						System.out.println("map:" + map);
						list.add(map);
					}
					System.out.println("getMessage应该返回的List：" + list);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(list);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityOneMessage.handler.sendMessage(msg);
			}
		}).start();
	}
	
	// 发送旅游动态
	public static void sendMessage(final String uID, final String content) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/sendMessage";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("content", content));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						flag = Boolean.parseBoolean(back);
						System.out.println("sendMessage得到的发送反馈：" + flag);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("sendMFlag", flag);
				msg.setData(bundle);
				ActivitySendMessage.sendMHandler.sendMessage(msg);
			}
		}).start();
	}

	// 通过messageID获取当前旅游动态的所有回复
	public static void getRevert(final int mId) {
		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/RevertServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					String mIDString = String.valueOf(mId);
					params.add(new BasicNameValuePair("m_id", mIDString));

					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getRevert得到的Json串：" + back);
					}
					// return flag;
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("owner_name", jsonObject.getString("owner_name"));
						map.put("time", jsonObject.getString("time"));
						map.put("text", jsonObject.getString("text"));
						System.out.println("map:" + map);
						list.add(map);
					}
					System.out.println("getRevert返回的List：" + list);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(list);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityRevert.rHandler.sendMessage(msg);
			}
		}).start();
	}
	
	// 发送回复
	public static void sendRevert(final String uID, final String content,final int mId) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/sendRevert";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					String mIdString = String.valueOf(mId);
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("content", content));
					params.add(new BasicNameValuePair("m_id", mIdString));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						flag = Boolean.parseBoolean(back);
						System.out.println("sendRevert得到的发送反馈：" + flag);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("sendRFlag", flag);
				msg.setData(bundle);
				ActivityRevert.sendHandler.sendMessage(msg);
			}
		}).start();
	}
	
	
	// 获取所有与用户ID（邮箱）相关的游记
	public static void getTravel(final String uId) {

		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/TravelServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uId));

					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getTravel得到的Json串：" + back);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("travel_id", jsonObject.getInt("travel_id"));
						map.put("owner_name",
								jsonObject.getString("owner_name"));
						map.put("time", jsonObject.getString("create_time"));
						map.put("title", jsonObject.getString("travel_title"));
						map.put("text", jsonObject.getString("travel_text"));
						System.out.println("map:" + map);
						list.add(map);
					}
					System.out.println("getMessage应该返回的List：" + list);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(list);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityTravel.travelHandler.sendMessage(msg);
			}
		}).start();
	}
	
	// 通过用户ID（邮箱）获取该用户的所有游记
	public static void getOneTravel(final String uID) {
		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/OneTravelServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getOneTravel得到的Json串：" + back);
					}
					// return flag;
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("travel_id", jsonObject.getInt("travel_id"));
						map.put("owner_name",
								jsonObject.getString("owner_name"));
						map.put("time", jsonObject.getString("create_time"));
						map.put("title", jsonObject.getString("travel_title"));
						map.put("text", jsonObject.getString("travel_text"));
						System.out.println("map:" + map);
						list.add(map);
					}
					System.out.println("getMessage应该返回的List：" + list);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(list);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityOneTravel.oneTHandler.sendMessage(msg);
			}
		}).start();
	}
	
	// 发送游记
	public static void sendTravel(final String uID, final String title, final String content) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/sendTravel";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("title", title));
					params.add(new BasicNameValuePair("content", content));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						flag = Boolean.parseBoolean(back);
						System.out.println("sendTravel得到的发送反馈：" + flag);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("sendTFlag", flag);
				msg.setData(bundle);
				ActivitySendTravel.sendTHandler.sendMessage(msg);
			}
		}).start();
	}
	
	// 通过TravelID获取当前游记的所有comment
	public static void getComment(final int tId) {
		final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/CommentServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					String tIDString = String.valueOf(tId);
					params.add(new BasicNameValuePair("t_id", tIDString));

					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getComment得到的Json串：" + back);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("owner_name", jsonObject.getString("owner_name"));
						map.put("time", jsonObject.getString("time"));
						map.put("text", jsonObject.getString("text"));
						System.out.println("map:" + map);
						list.add(map);
					}
					System.out.println("getComment返回的List：" + list);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(list);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityComment.commentHandler.sendMessage(msg);
			}
		}).start();
	}

	// 发送Comment
	public static void sendComment(final String uID, final String content,final int tId) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/sendComment";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					String tIdString = String.valueOf(tId);
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("content", content));
					params.add(new BasicNameValuePair("t_id", tIdString));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						flag = Boolean.parseBoolean(back);
						System.out.println("sendComment得到的发送反馈：" + flag);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("sendCFlag", flag);
				msg.setData(bundle);
				ActivityComment.sendCHandler.sendMessage(msg);
			}
		}).start();
	}
	
	// 通过用户ID（邮箱）获取当前用户所有信息
	public static void getMy(final String uID) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				String URL = "http://10.0.2.2:8080/Android/servlet/UserServlet";
				HttpPost request = new HttpPost(URL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("u_id", uID));
				params.add(new BasicNameValuePair("operate", "1"));
				try {
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getUser得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Message msg = new Message();
				Bundle bundle = new Bundle();
				try {
					JSONArray jsonArray = new JSONArray(back);
					JSONObject jsonObject = jsonArray.optJSONObject(0);
					bundle.putString("user_name",
							jsonObject.getString("user_name"));
					bundle.putString("user_email",
							jsonObject.getString("email"));
					bundle.putString("user_city", jsonObject.getString("City"));
					bundle.putString("user_sex", jsonObject.getString("sex"));
					bundle.putString("user_Bday", jsonObject.getString("Bday"));
					System.out.println("getUser应该返回的userBundle：" + bundle);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg.setData(bundle);
				ActivityUserHome.userHandler.sendMessage(msg);
			}
		}).start();
	}

	// 通过用户ID（邮箱）获取其他用户的信息
	public static void getUserM(final String uID) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				String URL = "http://10.0.2.2:8080/Android/servlet/UserServlet";
				HttpPost request = new HttpPost(URL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("u_id", uID));
				params.add(new BasicNameValuePair("operate", "1"));
				try {
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getUser得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Message msg = new Message();
				Bundle bundle = new Bundle();
				try {
					JSONArray jsonArray = new JSONArray(back);
					JSONObject jsonObject = jsonArray.optJSONObject(0);
					bundle.putInt("user_id", jsonObject.getInt("user_id"));
					bundle.putString("user_name",
							jsonObject.getString("user_name"));
					bundle.putString("user_email",
							jsonObject.getString("email"));
					bundle.putString("user_city", jsonObject.getString("City"));
					bundle.putString("user_sex", jsonObject.getString("sex"));
					bundle.putString("user_Bday", jsonObject.getString("Bday"));
					System.out.println("getUser应该返回的userBundle：" + bundle);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg.setData(bundle);
				ActivityUserM.userMHandler.sendMessage(msg);
			}
		}).start();
	}

	// 把GPS信息传到服务器
	public static void putServer(Location location, final String uID) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		final String latString = String.valueOf(latitude);
		final String longString = String.valueOf(longitude);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/sendGPS";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("latString", latString));
					params.add(new BasicNameValuePair("longString", longString));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	// 通过用户ID（邮箱）获取用户附近的用户
	public static void searchNearUser(final String uID) {
		final ArrayList<HashMap<String, Object>> nearUlist = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/searchNearServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过searchNearUser得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					System.out.println("进入JSON解析的json串：" + back);
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("user_id", jsonObject.getInt("getUser_id"));
						map.put("user_name",
								jsonObject.getString("getUser_name"));
						map.put("user_email", jsonObject.getString("email"));
						map.put("user_sex", jsonObject.getString("sex"));
						map.put("user_city", jsonObject.getString("city"));
						System.out.println("getMessage里面包含的map:" + map);
						nearUlist.add(map);
					}
					System.out.println("getMessage应该返回的List：" + nearUlist);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(nearUlist);
				System.out.println("放入Bundle中的List：" + bundleList);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityNear.nearHandler.sendMessage(msg);
			}
		}).start();
	}

	// 通过用户ID（邮箱）获取用户并可以操作
	public static void searchUserById(final String uID) {
		final ArrayList<HashMap<String, Object>> searchUlist = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				String URL = "http://10.0.2.2:8080/Android/servlet/UserServlet";
				HttpPost request = new HttpPost(URL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("u_id", uID));
				params.add(new BasicNameValuePair("operate", "1"));
				try {
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getUser得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("user_id", jsonObject.getInt("user_id"));
						map.put("user_name",
								jsonObject.getString("user_name"));
						map.put("user_email", jsonObject.getString("email"));
						map.put("user_sex", jsonObject.getString("sex"));
						map.put("user_city", jsonObject.getString("City"));
						searchUlist.add(map);
					}
					System.out.println("searchUserById应该返回的List：" + searchUlist);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(searchUlist);
				System.out.println("放入Bundle中的List：" + bundleList);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityNear.nearHandler.sendMessage(msg);
			}
		}).start();
	}
	
	// 通过两个用户的ID（邮箱）添加关注
	public static void addFollow(final String uID, final String otherUID) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/AddFollowServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("otherUser_id", otherUID));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过addFollow得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean flag = Boolean.parseBoolean(back);
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("addF_flag", flag);
				msg.setData(bundle);
				ActivityUserM.addHandler.sendMessage(msg);

			}
		}).start();
	}

	// 通过两个用户的ID（邮箱）取消关注
	public static void removeFollow(final String uID, final String otherUID) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/RemoveFollowServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("otherUser_id", otherUID));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过removeFollow得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				boolean flag = Boolean.parseBoolean(back);
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("removeF_flag", flag);
				msg.setData(bundle);
				ActivityUserM.removeHandler.sendMessage(msg);
			}
		}).start();
	}

	// 通过用户ID（邮箱）和ListType得到相应的用户列表
	public static void getUserList(final String uID, final int listType) {
		final String typeString = String.valueOf(listType);
		final ArrayList<HashMap<String, Object>> userlist = new ArrayList<HashMap<String, Object>>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/GetUserList";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("type", typeString));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过getUserList得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					System.out.println("进入JSON解析的json串：" + back);
					JSONArray jsonArray = new JSONArray(back);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("user_id", jsonObject.getInt("getUser_id"));
						map.put("user_name",
								jsonObject.getString("getUser_name"));
						map.put("user_email", jsonObject.getString("email"));
						map.put("user_sex", jsonObject.getString("sex"));
						map.put("user_city", jsonObject.getString("city"));
						System.out.println("getUserList里面包含的map:" + map);
						userlist.add(map);
					}
					System.out.println("getUserList应该返回的List：" + userlist);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayList bundleList = new ArrayList();
				bundleList.add(userlist);
				System.out.println("放入Bundle中的List：" + bundleList);

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("list", bundleList);
				msg.setData(bundle);
				ActivityUserList.userLHandler.sendMessage(msg);
			}
		}).start();
	}

	// 通过用户ID（邮箱）和新密码newPass对用户密码进行更改
	public static void changePass(final String uID, final String nPassString) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String URL = "http://10.0.2.2:8080/Android/servlet/ChangePassServlet";
					HttpPost request = new HttpPost(URL);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("u_id", uID));
					params.add(new BasicNameValuePair("newPass", nPassString));
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(request);
					if (httpResponse.getStatusLine().getStatusCode() != 404) {
						back = EntityUtils.toString(httpResponse.getEntity())
								.trim();
						System.out.println("通过changePass得到的Json串：" + back);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				boolean flag = Boolean.parseBoolean(back);
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("change_flag", flag);
				msg.setData(bundle);
				ActivityChangePass.cpHandler.sendMessage(msg);
			}
		}).start();
	}
}
