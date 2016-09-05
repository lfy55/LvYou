package com.sqk.lvyou.activitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sqk.lvyou.R;
import com.sqk.lvyou.tools.Tools;

public class ActivityNear extends Activity {

	private String userId;

	private EditText searchEmail;
	private Button sEmailButton;
	private ListView near_userList;
	private Button searchN;
	private Button sendGPS;
	private SimpleAdapter simpleAdapterNear;

	public static NearHandler nearHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_near);
		nearHandler = new NearHandler();

		searchEmail = (EditText) findViewById(R.id.searchEmail);
		sEmailButton = (Button) findViewById(R.id.sEButton);
		near_userList = (ListView) findViewById(R.id.near_userList);
		sendGPS = (Button) findViewById(R.id.sendGPS);
		searchN = (Button) findViewById(R.id.searchNear);

		Intent it = super.getIntent();
		this.userId = it.getStringExtra("u_id");

		sendGPS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Location location = registerGPS();
				if (null == location) {
					Toast.makeText(ActivityNear.this, "location为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Tools.putServer(location, userId);
			}
		});
		searchN.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tools.searchNearUser(userId);
			}
		});
		sEmailButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String sEmail = searchEmail.getText().toString();
				Tools.searchUserById(sEmail);
			}
		});
	}

	public class NearHandler extends Handler {
		public NearHandler() {
			super();
		}

		public NearHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle getNearBundle = msg.getData();
			ArrayList list = getNearBundle.getParcelableArrayList("list");
			List<HashMap<String, Object>> NUList = (List<HashMap<String, Object>>) list
					.get(0);
			System.out.println("nearActivity最终得到的用户List：" + NUList);

			simpleAdapterNear = new SimpleAdapter(ActivityNear.this, NUList,
					R.layout.item_nearuser, new String[] { "user_name",
							"user_email" }, new int[] { R.id.nuser_name,
							R.id.nuser_email });
			near_userList.setAdapter(simpleAdapterNear);
			near_userList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					HashMap<String, Object> map = (HashMap<String, Object>) parent
						       .getItemAtPosition(position);
					String nuserEmail = (String) map.get("user_email");
					Intent it = new Intent(ActivityNear.this,ActivityUserM.class);
					it.putExtra("get_user_email", nuserEmail);
					it.putExtra("u_id", userId);
					ActivityNear.this.startActivity(it);
				}
			});
		}
	}

	// 得到GPS信息Location的函数
	private Location registerGPS() {
		LocationManager locationManager = (LocationManager) getSystemService(
				Context.LOCATION_SERVICE);
		// 设置Criteria的信息
		Criteria criteria = new Criteria();
		// 经度要求
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 取得效果最好的criteria
		String provider = locationManager.getBestProvider(criteria, true);
		// 注册一个周期性的更新，1min更新一次
		locationManager.requestLocationUpdates(provider, 0, 1000 * 60,
				new getGpsLocationListner());
		// 得到坐标相关的信息
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			String gpsString = "经度：" + location.getLatitude() + "纬度："
					+ location.getLongitude();
			System.out.println(gpsString);
			Toast.makeText(ActivityNear.this, gpsString, Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(ActivityNear.this, "location为空", Toast.LENGTH_SHORT)
					.show();
		}
		return location;
	}

	// 监听GPS数据变化的函数
	private class getGpsLocationListner implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("latitude", String.valueOf(location.getLatitude()));
			map.put("longitude", String.valueOf(location.getLongitude()));

			System.out.println(map);

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

}
