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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityTravel extends Activity {
	private String userId;
	
	private Button refresh;
	private Button sendT;
	private ListView listview;
	private SimpleAdapter simpleAdapter;
	
	public static TravelHandler travelHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_travel);
		travelHandler = new TravelHandler();
		Intent it = super.getIntent();
		this.userId = it.getStringExtra("u_id");
		
		refresh = (Button) findViewById(R.id.refresh);
		sendT = (Button) findViewById(R.id.writer);
		listview = (ListView) findViewById(R.id.travel_listView);
		
		Tools.getTravel(userId);
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tools.getTravel(userId);
			}
		});
		sendT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ActivityTravel.this,
						ActivitySendTravel.class);
				i.putExtra("u_id", userId);
				startActivity(i);
			}
		});
	}
	
	public class TravelHandler extends Handler {
		public TravelHandler() {
			super();
		}

		public TravelHandler(Looper l) {
			super(l);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle getBundle = msg.getData();
			ArrayList list = getBundle.getParcelableArrayList("list");
			List<HashMap<String, Object>> travellist = (List<HashMap<String, Object>>) list
					.get(0);
			System.out.println("最终得到的list：" + travellist);

			simpleAdapter = new SimpleAdapter(ActivityTravel.this, travellist,
					R.layout.item_message, new String[] { "owner_name", "time",
							"title" }, new int[] { R.id.owner_name, R.id.time,
							R.id.text });
			listview.setAdapter(simpleAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

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
					Intent it = new Intent(ActivityTravel.this,
							ActivityComment.class);
					it.putExtra("tId", tId);
					it.putExtra("oName", ownerN);
					it.putExtra("time", time);
					it.putExtra("title", title);
					it.putExtra("text", text);
					it.putExtra("u_id", userId);
					ActivityTravel.this.startActivity(it);
				}
			});
		}
	}
}
