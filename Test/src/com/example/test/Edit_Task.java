package com.example.test;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.getData;

public class Edit_Task extends Activity {

    private String uid;
    private String key;
    private Context t = this;
    ProgressDialog mpd;
    
    // Add argv Starts Here
    
    private Button ret;
    private EditText title;
    private EditText content;
    private EditText date;
    private Button enter;
        
    // Add argv Ends Here
    
    // Add Handler Starts Here
    private class addTask extends AsyncHttpResponseHandler {
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
    			Toast.makeText(t, "新建成功", Toast.LENGTH_SHORT).show();
    			Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(Edit_Task.this, User_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				Edit_Task.this.finish();
    		}
    	}
    }
    
    // Add Handler Ends Here
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_layout);
        Bundle mBundle = this.getIntent().getExtras();
        uid = mBundle.getString("uid");
        key = mBundle.getString("key");
        
        ((TextView)findViewById(R.id.title_uname)).setText(uid);
        
        // Add process Starts Here
        
        ret = (Button)findViewById(R.id.edit_return);
        title = (EditText)findViewById(R.id.edit_title);
        date = (EditText)findViewById(R.id.edit_date);
        content = (EditText)findViewById(R.id.edit_content);
        enter = (Button)findViewById(R.id.btn_edit_enter);
        
        ret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(Edit_Task.this, User_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				Edit_Task.this.finish();
			}
		});
        
        enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tmp1 = title.getText().toString();
				String tmp2 = date.getText().toString();
				String tmp3 = content.getText().toString();
				if (tmp1.equals("") || tmp2.equals("") || tmp3.equals("")) {
					Toast.makeText(t, "数据不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				networkHandler.addTsk(uid, key, tmp1, tmp2, tmp3, new addTask());
			}
		});
        // Add process Ends Here
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
