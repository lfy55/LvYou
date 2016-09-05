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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityUserList extends Activity {

	private String userId;
	private String aboutUserId;
	private int listType;

	private TextView topText;
	private ListView userList;
	private SimpleAdapter simpleAdapter;

	public static UserLHandler userLHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_list);
		userLHandler = new UserLHandler();

		Intent getIntent = getIntent();
		userId = getIntent.getStringExtra("u_id");
		aboutUserId = getIntent.getStringExtra("about_userId");
		listType = getIntent.getIntExtra("listType", 2);

		topText = (TextView) findViewById(R.id.list_top);
		userList = (ListView) findViewById(R.id.userList);

		if (0 == listType) {
			topText.setText("关注");
		} else if (1 == listType) {
			topText.setText("粉丝");
		} else {
			topText.setText("程序出错");
		}
		
		Tools.getUserList(aboutUserId, listType);
	}

	public class UserLHandler extends Handler {
		public UserLHandler() {
			super();
		}

		public UserLHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle getNearBundle = msg.getData();
			ArrayList list = getNearBundle.getParcelableArrayList("list");
			List<HashMap<String, Object>> getUserList = (List<HashMap<String, Object>>) list
					.get(0);
			System.out.println("userListActivity最终得到的用户List：" + getUserList);

			simpleAdapter = new SimpleAdapter(ActivityUserList.this, getUserList,
					R.layout.item_nearuser, new String[] { "user_name",
							"user_email" }, new int[] { R.id.nuser_name,
							R.id.nuser_email });
			userList.setAdapter(simpleAdapter);
			userList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					HashMap<String, Object> map = (HashMap<String, Object>) parent
							.getItemAtPosition(position);
					String getUserEmail = (String) map.get("user_email");
					Intent it = new Intent(ActivityUserList.this,
							ActivityUserM.class);
					it.putExtra("get_user_email", getUserEmail);
					it.putExtra("u_id", userId);
					ActivityUserList.this.startActivity(it);
				}
			});
		}
	}

}
