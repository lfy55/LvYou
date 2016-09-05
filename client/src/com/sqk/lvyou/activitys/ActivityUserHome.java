package com.sqk.lvyou.activitys;

import com.sqk.lvyou.R;
import com.sqk.lvyou.tools.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityUserHome extends Activity {
	
	public static UserHandler userHandler;
	
	private String userId;
	private TextView myName;
	private TextView myEmail;
	private TextView myCity;
	private TextView mySex;
	private TextView myDay;
	private Button toChange;
	private Button toFansList;
	private Button toFollowList;
	private Button toOneMessage;
	private Button toOneTravel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_home);
		
		userHandler = new UserHandler();
		
		myName = (TextView) findViewById(R.id.myName);
		myEmail = (TextView) findViewById(R.id.myEmail);
		myCity = (TextView) findViewById(R.id.myCity);
		mySex = (TextView) findViewById(R.id.mySex);
		myDay = (TextView) findViewById(R.id.myBDay);
		
		toChange = (Button) findViewById(R.id.toChange);
		toFansList = (Button) findViewById(R.id.toFansList);
		toFollowList = (Button) findViewById(R.id.toFollowList);
		toOneMessage = (Button) findViewById(R.id.toOneList);
		toOneTravel = (Button) findViewById(R.id.toOneTList);
		
		Intent getIntent = getIntent();
		userId = getIntent.getStringExtra("u_id");
		Tools.getMy(userId);
		
		toChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserHome.this,ActivityChangePass.class);
				it.putExtra("u_id", userId);
				startActivity(it);
			}
		});
		toFansList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserHome.this,ActivityUserList.class);
				it.putExtra("u_id", userId);
				it.putExtra("about_userId", userId);
				it.putExtra("listType", 1);
				startActivity(it);
			}
		});
		toFollowList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserHome.this,ActivityUserList.class);
				it.putExtra("u_id", userId);
				it.putExtra("about_userId", userId);
				it.putExtra("listType", 0);
				startActivity(it);
			}
		});
		toOneMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserHome.this,ActivityOneMessage.class);
				it.putExtra("u_id", userId);
				it.putExtra("owner_id", userId);
				startActivity(it);
			}
		});
		toOneTravel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserHome.this,ActivityOneTravel.class);
				it.putExtra("u_id", userId);
				it.putExtra("owner_id", userId);
				startActivity(it);
			}
		});
		
	}
	
	public class UserHandler extends Handler {
		public UserHandler() {
			super();
		}

		public UserHandler(Looper l) {
			super(l);
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			myName.setText("用户姓名："+bundle.getString("user_name"));
			myEmail.setText("用户邮箱："+bundle.getString("user_email"));
			myCity.setText("用户城市："+bundle.getString("user_city"));
			mySex.setText("用户性别："+bundle.getString("user_sex"));
			myDay.setText("用户生日："+bundle.getString("user_Bday"));
		}
	}
}
