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

public class ActivityChangePass extends Activity {
	private String userId;

	private EditText newPass;
	private EditText rNewPass;
	private Button submit;
	
	public static CPHandler cpHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_changepass);
		cpHandler = new CPHandler();

		Intent getIntent = getIntent();
		userId = getIntent.getStringExtra("u_id");

		newPass = (EditText) findViewById(R.id.changePass);
		rNewPass = (EditText) findViewById(R.id.rChangePass);
		submit = (Button) findViewById(R.id.changeSubmit);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean changeFlag = true;
				String nPass = newPass.getText().toString();
				String rNPass = rNewPass.getText().toString();
				if (nPass == null || nPass.trim().equals("")) {
					newPass.setError("密码不允许为空");
					changeFlag = false;
				}
				if (!rNPass.equals(nPass)) {
					rNewPass.setError("两次输入密码不一致");
					changeFlag = false;
				}

				if (false == changeFlag) {
					return;
				}

				Tools.changePass(userId, nPass);
			}
		});
	}

	public class CPHandler extends Handler {
		public CPHandler() {
			super();
		}

		public CPHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			Boolean changeFlag = b.getBoolean("change_flag");
			Log.d("changePasswordFlag:", "" + changeFlag);
			if (true == changeFlag) {
				Intent it = new Intent(ActivityChangePass.this,
						ActivityLogin.class);
				ActivityChangePass.this.startActivity(it);
				Toast.makeText(ActivityChangePass.this, "修改密码成功！",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ActivityChangePass.this, "修改密码失败！",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
