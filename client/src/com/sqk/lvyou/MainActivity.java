package com.sqk.lvyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sqk.lvyou.activitys.ActivityLogin;
import com.sqk.lvyou.activitys.ActivityMain;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String token = Config.getToken();
		if (token != null) {
			Intent i = new Intent(MainActivity.this, ActivityMain.class);
			i.putExtra("u_id", token);
			startActivity(i);
		} else {
			Intent i = new Intent(this, ActivityLogin.class);
			startActivity(i);
		}

		finish();
	}
}
