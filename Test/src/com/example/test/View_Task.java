package com.example.test;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.getData;

public class View_Task extends Activity {

    private String uid;
    private String key;
    private String tid;
    private Context t = this;
    ProgressDialog mpd;
    
    // Add argv Starts Here
    
    private Button ret;
    private TextView title;
    private TextView owner;
    private TextView date;
    private TextView content;
    private Button option;
    private TextView state;
        
    // Add argv Ends Here
    
    // Add Handler Starts Here
    private class confirmTask extends AsyncHttpResponseHandler {
    	@Override
		public void onStart() {
    	}
    	
    	@Override
    	public void onSuccess(String argv) {
    		Boolean err = getData.error(argv);
    		if (err == null) {
    			Toast.makeText(t, "数据解析失败", Toast.LENGTH_SHORT).show();
    		} else if (err) {
    			Toast.makeText(t, getData.getStringInfo(argv), Toast.LENGTH_SHORT).show();
    		} else {
    			Toast.makeText(t, "确认成功", Toast.LENGTH_SHORT).show();
    		}
    	}
    }
    
    private class taskInfo extends AsyncHttpResponseHandler {
    	@Override
		public void onStart() {
    	}
    	
    	@Override
    	public void onSuccess(String argv) {
    		Toast.makeText(t, argv, Toast.LENGTH_LONG).show();
    		Boolean err = getData.error(argv);
    		if (err == null) {
    			Toast.makeText(t, "数据解析失败", Toast.LENGTH_SHORT).show();
    		} else if (err) {
    			Toast.makeText(t, getData.getStringInfo(argv), Toast.LENGTH_SHORT).show();
    		} else {
    			JSONObject tmp = getData.getObjectInfo(argv);
    			try {
					title.setText(tmp.getString("title"));
					date.setText(tmp.getString("date"));
					owner.setText(tmp.getString("owner"));
					content.setText(tmp.getString("content"));
					option.setText("确认完成");
					if (tmp.getString("ownFin").equals("1")) {
						state.setText("已成功");
						option.setClickable(false);
					} else if (tmp.getString("tarFin").equals("1")) {
						state.setText("待确认");
						if (uid.equals(tmp.getString("owner"))) option.setClickable(true);
						else option.setClickable(true);
					} else {
						state.setText("待完成");
						option.setClickable(true);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		}
    	}
    	
    	@Override
    	public void onFinish() {
    	}
    }
    // Add Handler Ends Here
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_task_layout);
        Bundle mBundle = this.getIntent().getExtras();
        uid = mBundle.getString("uid");
        key = mBundle.getString("key");
        tid = mBundle.getString("target");
        
        ((TextView)findViewById(R.id.title_uname)).setText(uid);
        
        // Add process Starts Here
        
        ret = (Button)findViewById(R.id.view_return);
        title = (TextView)findViewById(R.id.view_title);
        date = (TextView)findViewById(R.id.view_date);
        owner = (TextView)findViewById(R.id.view_uname);
        content = (TextView)findViewById(R.id.view_content);
        state = (TextView)findViewById(R.id.view_state);
        option = (Button)findViewById(R.id.btn_view_op);
        
        networkHandler.taskInfo(uid, key, tid, new taskInfo());
        
        ret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(View_Task.this, User_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				View_Task.this.finish();
			}
		});
        
        option.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				networkHandler.confirmTask(uid, key, tid, new confirmTask());
			}
		});
        option.setClickable(false);
        // Add process Ends Here
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
