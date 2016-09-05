package com.sqk.lvyou.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sqk.lvyou.R;
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

public class ActivityOneTravel extends Activity {
	private String userId;
	private String ownerId;
	public static OneTHandler oneTHandler;
	
	private ListView oTList;
	private SimpleAdapter simpleAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_onetravel);
		oneTHandler = new OneTHandler();
		
		oTList = (ListView) findViewById(R.id.oneT_listView);
		
		Intent it = super.getIntent();
		this.userId = it.getStringExtra("u_id");
		this.ownerId = it.getStringExtra("owner_id");
		
		Tools.getOneTravel(ownerId);
	}
	
	public class OneTHandler extends Handler {
		public OneTHandler() {
			super();
		}

		public OneTHandler(Looper l) {
			super(l);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle getBundle = msg.getData();
			ArrayList list = getBundle.getParcelableArrayList("list");
			List<HashMap<String, Object>> getTList = (List<HashMap<String, Object>>) list
					.get(0);
			System.out.println("最终得到的list：" + getTList);

			simpleAdapter = new SimpleAdapter(ActivityOneTravel.this, getTList,
					R.layout.item_message, new String[] { "owner_name", "time",
							"title" }, new int[] { R.id.owner_name, R.id.time,
							R.id.text });
			oTList.setAdapter(simpleAdapter);
			oTList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					HashMap<String, Object> map = (HashMap<String, Object>) parent
							.getItemAtPosition(position);
					int tId = (Integer) map.get("travel_id");
					String ownerN = (String) map.get("owner_name");
					String time = (String) map.get("time");
					String title = (String) map.get("title");
					String text = (String) map.get("text");
					Intent it = new Intent(ActivityOneTravel.this,
							ActivityComment.class);
					it.putExtra("tId", tId);
					it.putExtra("oName", ownerN);
					it.putExtra("time", time);
					it.putExtra("title", title);
					it.putExtra("text", text);
					it.putExtra("u_id", userId);
					ActivityOneTravel.this.startActivity(it);
				}
			});
		}
	}
}
