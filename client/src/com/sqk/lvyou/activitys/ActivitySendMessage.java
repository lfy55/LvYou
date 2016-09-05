package com.sqk.lvyou.activitys;

import com.sqk.lvyou.R;
import com.sqk.lvyou.tools.Tools;

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
import android.widget.Toast;

public class ActivitySendMessage extends Activity {

	private String userId;

	private EditText content;
	private Button submit;

	public static SendMHandler sendMHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_sendmessage);
		sendMHandler = new SendMHandler();

		Intent it = super.getIntent();
		this.userId = it.getStringExtra("u_id");

		content = (EditText) findViewById(R.id.sendContent);
		submit = (Button) findViewById(R.id.submit);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String conString = content.getText().toString();
				if (conString == null || conString.trim().equals("")) {
					content.setError("动态内容不允许为空");
					return;
				}
				Tools.sendMessage(userId, content.getText().toString());
			}
		});
	}

	public class SendMHandler extends Handler {
		public SendMHandler() {
			super();
		}

		public SendMHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			boolean flag = b.getBoolean("sendMFlag");
			Log.d("sendMflag", "" + flag);
			if (true == flag) {
				Intent it = new Intent(ActivitySendMessage.this,
						ActivityMain.class);
				it.putExtra("u_id", userId);
				ActivitySendMessage.this.startActivity(it);
				Toast.makeText(ActivitySendMessage.this, "发送成功！",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ActivitySendMessage.this, "发送失败！",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
