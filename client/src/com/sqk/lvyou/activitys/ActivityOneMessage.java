package com.sqk.lvyou.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sqk.lvyou.R;
import com.sqk.lvyou.activitys.ActivityMain.MessageHandler;
import com.sqk.lvyou.tools.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityOneMessage extends Activity {
	private String userId;
	private String ownerId;
	
	private ListView mList;
	private SimpleAdapter simpleAdapter;
	
	public static messageHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_onemessage);
		handler = new messageHandler();
		
		mList = (ListView) findViewById(R.id.one_listView);
		
		Intent it = super.getIntent();
		this.userId = it.getStringExtra("u_id");
		this.ownerId = it.getStringExtra("owner_id");
		
		Tools.getOneMessage(ownerId);
	}
	
	public class messageHandler extends Handler {
		public messageHandler() {
			super();
		}

		public messageHandler(Looper l) {
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

			simpleAdapter = new SimpleAdapter(ActivityOneMessage.this, lists,
					R.layout.item_message, new String[] { "owner_name", "time",
							"text" }, new int[] { R.id.owner_name, R.id.time,
							R.id.text });
			mList.setAdapter(simpleAdapter);
			mList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					HashMap<String, Object> map = (HashMap<String, Object>) parent
							.getItemAtPosition(position);
					int mId = (Integer) map.get("message_id");
					String ownerN = (String) map.get("owner_name");
					String time = (String) map.get("time");
					String mText = (String) map.get("text");
					Intent it = new Intent(ActivityOneMessage.this,
							ActivityRevert.class);
					it.putExtra("mId", mId);
					it.putExtra("oName", ownerN);
					it.putExtra("time", time);
					it.putExtra("text", mText);
					it.putExtra("u_id", userId);
					ActivityOneMessage.this.startActivity(it);
				}
			});
		}
	}
}
