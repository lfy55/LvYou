package com.sqk.lvyou.activitys;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sqk.lvyou.Config;
import com.sqk.lvyou.R;

public class ActivityLogin extends Activity {
	private String url = "http://10.0.2.2:8080/Android/servlet/LoginServlet";
	MyHandler myHandler;
	private EditText userEmail;
	private EditText passWord;
	private Button login;
	private Button regist;
	private Boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_login);
		myHandler = new MyHandler();
		this.userEmail = (EditText) super.findViewById(R.id.email);
		this.passWord = (EditText) super.findViewById(R.id.password);
		this.login = (Button) super.findViewById(R.id.login);
		this.regist = (Button) super.findViewById(R.id.regist);
		this.login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String email = userEmail.getText().toString();
				String pass = passWord.getText().toString();
				Log.d("useCheck", email + " " + pass + " " + flag);
				check(email, pass);
			}
		});
		this.regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityLogin.this, ActivityRegist.class);
				ActivityLogin.this.startActivity(it);
			}
		});
	}

	public void check(final String userEmail , final String pass) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d("checkstart", "Login check()方法线程开始");
				NameValuePair emailPair = new BasicNameValuePair("email", userEmail);
				NameValuePair passPair = new BasicNameValuePair("password", pass);
				List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(emailPair);
				pairList.add(passPair);
				try {
					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList,HTTP.UTF_8);
					HttpPost httpPost = new HttpPost(url);
					httpPost.setEntity(requestHttpEntity);//把请求体内容加入请求中
					HttpClient httpClient = new DefaultHttpClient();//创建客户端对象来发送请求
					HttpResponse response = httpClient.execute(httpPost);//发送请求
					
					boolean getFlag = false;
					if(null != response) {
						HttpEntity httpEntity = response.getEntity();
						try {
							InputStream inputStream = httpEntity.getContent();
				            BufferedReader reader = new BufferedReader(new InputStreamReader(
				                    inputStream));
				            String line;
				            while(null != (line = reader.readLine())) {
				            	getFlag = Boolean.parseBoolean(line);
				            }
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					
					Message msg=new Message();
					Bundle bundle = new Bundle();
					bundle.putBoolean("flag", getFlag);
					msg.setData(bundle);
					ActivityLogin.this.myHandler.sendMessage(msg);
					Log.d("checkend", "Login check()方法线程结束");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	class MyHandler extends Handler {
		public MyHandler() {
			super();
		}
		public MyHandler(Looper l) {
			super(l);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b=msg.getData();
			flag = b.getBoolean("flag");
			Log.d("LoginHandlerflag", ""+flag);
			if (true == flag) {
				Config.putToken(userEmail.getText().toString());
				Intent it = new Intent(ActivityLogin.this,
						ActivityMain.class);
				it.putExtra("u_id", userEmail.getText().toString());
				ActivityLogin.this.startActivity(it);
				Toast.makeText(ActivityLogin.this, "登录成功！", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ActivityLogin.this, "用户名或者密码错误！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
