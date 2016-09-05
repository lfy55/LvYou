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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sqk.lvyou.R;
import com.sqk.lvyou.tools.Tools;

public class ActivityRevert extends Activity {
	private String userId;
	private int messageId;
	private String ownerName;
	private String time;
	private String message;
	public static RHandler rHandler;
	public static SendHandler sendHandler;

	private SimpleAdapter simpleAdapter;

	private TextView ownerNText;
	private TextView timeText;
	private TextView messageText;
	private ListView revertList;
	private EditText revertText;
	private Button sendRButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_revert);

		rHandler = new RHandler();
		sendHandler = new SendHandler();

		Intent getIntent = getIntent();
		userId = getIntent.getStringExtra("u_id");
		messageId = getIntent.getIntExtra("mId", 0);
		ownerName = getIntent.getStringExtra("oName");
		time = getIntent.getStringExtra("time");
		message = getIntent.getStringExtra("text");

		ownerNText = (TextView) findViewById(R.id.owner_name);
		timeText = (TextView) findViewById(R.id.time);
		messageText = (TextView) findViewById(R.id.text);
		revertList = (ListView) findViewById(R.id.revert_listView);
		revertText = (EditText) findViewById(R.id.sendRevert);
		sendRButton = (Button) findViewById(R.id.sRButton);

		ownerNText.setText(ownerName);
		timeText.setText(time);
		messageText.setText(message);

		Tools.getRevert(messageId);

		sendRButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = revertText.getText().toString();
				if (content == null || content.trim().equals("")) {
					revertText.setError("回复不允许为空");
					return;
				}
				Tools.sendRevert(userId, content,messageId);
			}
		});
	}

	public class RHandler extends Handler {
		public RHandler() {
			super();
		}

		public RHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle getRevertBundle = msg.getData();
			ArrayList list = getRevertBundle.getParcelableArrayList("list");
			List<HashMap<String, Object>> getRevertList = (List<HashMap<String, Object>>) list
					.get(0);
			System.out.println("getRevert最终得到的List：" + getRevertList);
			simpleAdapter = new SimpleAdapter(ActivityRevert.this,
					getRevertList, R.layout.item_revert, new String[] {
							"owner_name", "time", "text" }, new int[] {
							R.id.owner_name, R.id.time, R.id.text });
			revertList.setAdapter(simpleAdapter);
		}
	}

	public class SendHandler extends Handler {
		public SendHandler() {
			super();
		}

		public SendHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b=msg.getData();
			boolean flag = b.getBoolean("sendRFlag");
			Log.d("sendRevertflag", ""+flag);
			if (true == flag) {
				Toast.makeText(ActivityRevert.this, "发送成功！", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ActivityRevert.this, "发送失败！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
