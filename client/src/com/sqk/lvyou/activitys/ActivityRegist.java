package com.sqk.lvyou.activitys;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ActivityRegist extends Activity {
	Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	Pattern patternBrithDay = Pattern.compile("^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))$");
	
	private String url = "http://10.0.2.2:8080/Android/servlet/RegistServlet";
	private MyHandler myHandler;
	private EditText userEmail;
	private EditText password;
	private EditText rPassword;
	private EditText userName;
	private EditText address;
	private EditText sex;
	private EditText brithday;
	private Button regist;
	private Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_regist);
		myHandler = new MyHandler();
		this.userEmail = (EditText) super.findViewById(R.id.email);
		this.password = (EditText) super.findViewById(R.id.password);
		this.rPassword = (EditText) super.findViewById(R.id.rPassword);
		this.userName = (EditText) super.findViewById(R.id.userName);
		this.address = (EditText) super.findViewById(R.id.address);
		this.sex = (EditText) super.findViewById(R.id.sex);
		this.brithday = (EditText) super.findViewById(R.id.brithday);
		this.regist = (Button) super.findViewById(R.id.regist);
		this.login = (Button) super.findViewById(R.id.login);

		regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean eTFlag = true;
				String uEString = userEmail.getText().toString();
				String pwString = password.getText().toString();
				String rPwString = rPassword.getText().toString();
				String uNString = userName.getText().toString();
				String adString = address.getText().toString();
				String sexString = sex.getText().toString();
				String bdString = brithday.getText().toString();

				Matcher matcherEmail = patternEmail.matcher(uEString);
				Matcher matcherBrithDay = patternBrithDay.matcher(bdString);
				if (uEString == null || uEString.trim().equals("")) {
					userEmail.setError("邮箱不允许为空");
					eTFlag = false;
				}
				if(!matcherEmail.matches()) {
					userEmail.setError("邮箱格式不正确");
					eTFlag = false;
				}
				if (pwString == null || pwString.trim().equals("")) {
					password.setError("密码不允许为空");
					eTFlag = false;
				}
				if (!rPwString.equals(pwString)) {
					rPassword.setError("两次输入密码不一致");
					eTFlag = false;
				}
				if (uNString == null || uNString.trim().equals("")) {
					userName.setError("用户名不允许为空");
					eTFlag = false;
				}
				if (adString == null || adString.trim().equals("")) {
					address.setError("住址不允许为空");
					eTFlag = false;
				}
				if (sexString == null || sexString.trim().equals("")) {
					sex.setError("性别不允许为空");
					eTFlag = false;
				}
				if((!sexString.trim().equals("男"))&&(!sexString.trim().equals("女"))) {
					sex.setError("性别必须为男或者女");
					eTFlag = false;
				}
				if (bdString == null || bdString.trim().equals("")) {
					brithday.setError("生日不允许为空");
					eTFlag = false;
				}
				if(!matcherBrithDay.matches()) {
					brithday.setError("生日格式不正确");
					eTFlag = false;
				}

				Log.d("registMessage", uEString + " " + pwString + " "
						+ rPwString + " " + uNString + " " + adString + " "
						+ sexString + " " + bdString);

				if (!eTFlag) {
					return;
				}

				NameValuePair uEPair = new BasicNameValuePair("email", uEString);
				NameValuePair pwPair = new BasicNameValuePair("password", pwString);
				NameValuePair uNPair = new BasicNameValuePair("userName", uNString);
				NameValuePair adPair = new BasicNameValuePair("address", adString);
				NameValuePair sexPair = new BasicNameValuePair("sex",
						sexString);
				NameValuePair bdPair = new BasicNameValuePair("brithday", bdString);
				List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(uEPair);
				pairList.add(pwPair);
				pairList.add(uNPair);
				pairList.add(adPair);
				pairList.add(sexPair);
				pairList.add(bdPair);

				check(pairList);
			}

		});

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityRegist.this,
						ActivityLogin.class);
				startActivity(intent);
			}
		});
	}

	private void check(final List<NameValuePair> pairList) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.d("checkstart", "Regist check()方法线程开始");
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
					
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putBoolean("flag", getFlag);
					msg.setData(bundle);
					ActivityRegist.this.myHandler.sendMessage(msg);
					Log.d("checkend", "Regist check()方法线程结束");
				} catch (Exception e) {
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
			super.handleMessage(msg);
			Bundle b = msg.getData();
			Boolean registFlag = b.getBoolean("flag");
			Log.d("RegistBundleFlag", ""+registFlag);
			if (true == registFlag) {
				Config.putToken(userEmail.getText().toString());
				Intent it = new Intent(ActivityRegist.this,
						ActivityMain.class);
				it.putExtra("u_id", userEmail.getText().toString());
				ActivityRegist.this.startActivity(it);
				Toast.makeText(ActivityRegist.this, "注册成功！", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ActivityRegist.this, "注册失败！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
