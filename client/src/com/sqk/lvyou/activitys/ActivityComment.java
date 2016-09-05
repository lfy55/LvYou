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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityComment extends Activity {
	private String userId;
	private int travelId;
	private String ownerName;
	private String time;
	private String title;
	private String text;
	public static CommentHandler commentHandler;
	public static SendCHandler sendCHandler;
	private SimpleAdapter simpleAdapter;
	
	private TextView ownerNText;
	private TextView timeText;
	private TextView titleText;
	private TextView textText;
	private ListView commentList;
	private EditText commentText;
	private Button sendCButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_comment);
		commentHandler = new CommentHandler();
		sendCHandler = new SendCHandler();
		
		Intent getIntent = getIntent();
		userId = getIntent.getStringExtra("u_id");
		travelId = getIntent.getIntExtra("tId", 0);
		ownerName = getIntent.getStringExtra("oName");
		time = getIntent.getStringExtra("time");
		title = getIntent.getStringExtra("title");
		text = getIntent.getStringExtra("text");
		
		ownerNText = (TextView) findViewById(R.id.owner_name);
		timeText = (TextView) findViewById(R.id.time);
		titleText = (TextView) findViewById(R.id.title);
		textText = (TextView) findViewById(R.id.text);
		commentList = (ListView) findViewById(R.id.comment_listView);
		commentText = (EditText) findViewById(R.id.sendComment);
		sendCButton = (Button) findViewById(R.id.sCButton);
		
		ownerNText.setText(ownerName);
		timeText.setText(time);
		titleText.setText(title);
		textText.setText(text);
		
		Tools.getComment(travelId);
		sendCButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = commentText.getText().toString();
				if (content == null || content.trim().equals("")) {
					commentText.setError("评论不允许为空");
					return;
				}
				Tools.sendComment(userId, content,travelId);
			}
		});
	}
	
	public class CommentHandler extends Handler {
		public CommentHandler() {
			super();
		}
		public CommentHandler(Looper l) {
			super(l);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle getRevertBundle = msg.getData();
			ArrayList list = getRevertBundle.getParcelableArrayList("list");
			List<HashMap<String, Object>> getCommentList = (List<HashMap<String, Object>>) list
					.get(0);
			System.out.println("getComment最终得到的List：" + getCommentList);
			simpleAdapter = new SimpleAdapter(ActivityComment.this,
					getCommentList, R.layout.item_revert, new String[] {
							"owner_name", "time", "text" }, new int[] {
							R.id.owner_name, R.id.time, R.id.text });
			commentList.setAdapter(simpleAdapter);
		}
	}
	
	public class SendCHandler extends Handler {
		public SendCHandler() {
			super();
		}
		public SendCHandler(Looper l) {
			super(l);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle b=msg.getData();
			boolean flag = b.getBoolean("sendCFlag");
			Log.d("sendCommentFlag", ""+flag);
			if (true == flag) {
				Toast.makeText(ActivityComment.this, "发送成功！", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(ActivityComment.this, "发送失败！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
