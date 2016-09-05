package com.sqk.lvyou.activitys;

import com.sqk.lvyou.R;
import com.sqk.lvyou.tools.Tools;

import android.R.bool;
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
import android.widget.Toast;

public class ActivityUserM extends Activity {
	private String userId;
	public static UserMHandler userMHandler;
	public static AddHandler addHandler;
	public static RemoveHandler removeHandler;

	private String getUserE;

	private TextView myName;
	private TextView myEmail;
	private TextView myCity;
	private TextView mySex;
	private TextView myDay;
	private Button toMList;
	private Button toTList;
	private Button toFansList;
	private Button toFollowList;
	private Button addFollow;
	private Button removeFollow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_message);
		userMHandler = new UserMHandler();
		addHandler = new AddHandler();
		removeHandler = new RemoveHandler();

		Intent getIntent = getIntent();
		getUserE = getIntent.getStringExtra("get_user_email");
		userId = getIntent.getStringExtra("u_id");

		myName = (TextView) findViewById(R.id.userName);
		myEmail = (TextView) findViewById(R.id.userEmail);
		myCity = (TextView) findViewById(R.id.userCity);
		mySex = (TextView) findViewById(R.id.userSex);
		myDay = (TextView) findViewById(R.id.userBDay);
		toMList = (Button) findViewById(R.id.toOneList);
		toTList = (Button) findViewById(R.id.toOneTList);
		toFansList = (Button) findViewById(R.id.toFansList);
		toFollowList = (Button) findViewById(R.id.toFollowList);
		addFollow = (Button) findViewById(R.id.addFollow);
		removeFollow = (Button) findViewById(R.id.removeFollow);

		Tools.getUserM(getUserE);

		addFollow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tools.addFollow(userId, getUserE);
			}
		});
		removeFollow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tools.removeFollow(userId, getUserE);
			}
		});
		toMList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserM.this,
						ActivityOneMessage.class);
				it.putExtra("u_id", userId);
				it.putExtra("owner_id", getUserE);
				startActivity(it);
			}
		});
		toTList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserM.this,
						ActivityOneTravel.class);
				it.putExtra("u_id", userId);
				it.putExtra("owner_id", getUserE);
				startActivity(it);
			}
		});
		toFansList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserM.this,
						ActivityUserList.class);
				it.putExtra("u_id", userId);
				it.putExtra("about_userId", getUserE);
				it.putExtra("listType", 1);
				startActivity(it);
			}
		});
		toFollowList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityUserM.this,
						ActivityUserList.class);
				it.putExtra("u_id", userId);
				it.putExtra("about_userId", getUserE);
				it.putExtra("listType", 0);
				startActivity(it);
			}
		});
	}

	public class UserMHandler extends Handler {
		public UserMHandler() {
			super();
		}

		public UserMHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			myName.setText("用户姓名：" + bundle.getString("user_name"));
			myEmail.setText("用户邮箱：" + bundle.getString("user_email"));
			myCity.setText("用户城市：" + bundle.getString("user_city"));
			mySex.setText("用户性别：" + bundle.getString("user_sex"));
			myDay.setText("用户生日：" + bundle.getString("user_Bday"));
		}
	}

	public class AddHandler extends Handler {
		public AddHandler() {
			super();
		}

		public AddHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			boolean flag = bundle.getBoolean("addF_flag");
			if (true == flag) {
				Toast.makeText(ActivityUserM.this, "添加关注成功", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(ActivityUserM.this, "添加关注失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public class RemoveHandler extends Handler {
		public RemoveHandler() {
			super();
		}

		public RemoveHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			boolean flag = bundle.getBoolean("removeF_flag");
			if (true == flag) {
				Toast.makeText(ActivityUserM.this, "删除关注成功", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(ActivityUserM.this, "删除关注失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
