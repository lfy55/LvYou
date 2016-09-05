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

public class ActivitySendTravel extends Activity {
	private String userId;
	public static SendTHandler sendTHandler;
	
	private EditText title;
	private EditText content;
	private Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_sendtravel);
		sendTHandler = new SendTHandler();
		
		Intent it = super.getIntent();
		this.userId = it.getStringExtra("u_id");
		
		title = (EditText) findViewById(R.id.sendTitle);
		content = (EditText) findViewById(R.id.sendContent);
		submit = (Button) findViewById(R.id.submit);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tString = title.getText().toString();
				String cString = content.getText().toString();
				if (tString == null || tString.trim().equals("")) {
					title.setError("游记标题不允许为空");
					return;
				}
				if (cString == null || cString.trim().equals("")) {
					content.setError("游记内容不允许为空");
					return;
				}
				Tools.sendTravel(userId, tString, cString);
			}
		});
	}
	
	public class SendTHandler extends Handler {
		public SendTHandler() {
			super();
		}
		public SendTHandler(Looper l) {
			super(l);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b=msg.getData();
			boolean flag = b.getBoolean("sendTFlag");
			Log.d("sendTflag", ""+flag);
			if (true == flag) {
				Intent it = new Intent(ActivitySendTravel.this,
						ActivityTravel.class);
				it.putExtra("u_id", userId);
				ActivitySendTravel.this.startActivity(it);
				Toast.makeText(ActivitySendTravel.this, "发送成功！", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ActivitySendTravel.this, "发送失败！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
