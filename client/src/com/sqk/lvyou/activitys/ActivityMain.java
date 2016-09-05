package com.sqk.lvyou.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.sqk.lvyou.R;
import com.sqk.lvyou.tools.Tools;

public class ActivityMain extends Activity {

	private String userId;

	private Button refresh;
	private Button sendM;
	private ListView listview;
	private SimpleAdapter simpleAdapter;
	private Button toUser;
	private Button toNear;
	private Button toTravel;

	public static MessageHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_main);
		handler = new MessageHandler();

		refresh = (Button) findViewById(R.id.refresh);
		sendM = (Button) findViewById(R.id.writer);
		listview = (ListView) findViewById(R.id.main_listView);
		toUser = (Button) findViewById(R.id.user);
		toNear = (Button) findViewById(R.id.near);
		toTravel = (Button) findViewById(R.id.travel);

		Intent it = super.getIntent();
		this.userId = it.getStringExtra("u_id");

		Tools.getMessage(userId);
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tools.getMessage(userId);
			}
		});
		sendM.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ActivityMain.this,
						ActivitySendMessage.class);
				i.putExtra("u_id", userId);
				startActivity(i);
			}
		});

		toUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ActivityMain.this, ActivityUserHome.class);
				i.putExtra("u_id", userId);
				startActivity(i);
			}
		});

		toNear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ActivityMain.this, ActivityNear.class);
				i.putExtra("u_id", userId);
				startActivity(i);
			}
		});
		toTravel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ActivityMain.this, ActivityTravel.class);
				i.putExtra("u_id", userId);
				startActivity(i);
			}
		});
	}

	public class MessageHandler extends Handler {
		public MessageHandler() {
			super();
		}

		public MessageHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle getBundle = msg.getData();
			ArrayList list = getBundle.getParcelableArrayList("list");
			List<HashMap<String, Object>> lists = (List<HashMap<String, Object>>) list
					.get(0);
			System.out.println("最终得到的list：" + lists);

			simpleAdapter = new SimpleAdapter(ActivityMain.this, lists,
					R.layout.item_message, new String[] { "owner_name", "time",
							"text" }, new int[] { R.id.owner_name, R.id.time,
							R.id.text });
			listview.setAdapter(simpleAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					HashMap<String, Object> map = (HashMap<String, Object>) parent
							.getItemAtPosition(position);
					int mId = (Integer) map.get("message_id");
					String ownerN = (String) map.get("owner_name");
					String time = (String) map.get("time");
					String mText = (String) map.get("text");
					Intent it = new Intent(ActivityMain.this,
							ActivityRevert.class);
					it.putExtra("mId", mId);
					it.putExtra("oName", ownerN);
					it.putExtra("time", time);
					it.putExtra("text", mText);
					it.putExtra("u_id", userId);
					ActivityMain.this.startActivity(it);
				}
			});
		}
	}
}
