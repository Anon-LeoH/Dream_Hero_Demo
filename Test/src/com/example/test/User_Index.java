package com.example.test;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.getData;
import com.example.test.getData.news;

public class User_Index extends Activity {

    private String uid;
    private String key;
    private Context t = this;
    ProgressDialog mpd;
    private TextView friReq;
    private TextView confReq;
    private Button friHandle;
    private Button confHandle;
    
    private Button myTsk;
    private Button index;
    private Button friend;
    private Button task;
    private Button game;
    
    private Boolean running ;
    
    private class refresh extends AsyncHttpResponseHandler {
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
                String info = getData.getStringInfo(argv);
                news tmp = getData.getNews(info);
				ArrayList<Map<String, String>> fList = tmp.flist;
				ArrayList<Map<String, String>> tList = tmp.tlist;
				ArrayList<Map<String, String>> cList = tmp.clist;
				friReq.setText("您有" + String.valueOf(fList.size()) + "条好友请求");
				confReq.setText("您有" + String.valueOf(cList.size()) + "条愿望确认请求");
				myTsk.setText("您有" + String.valueOf(tList.size()) + "个未完成任务");
				index.setText("主页(" + String.valueOf(fList.size() + cList.size()) + ")");
				index.setClickable(false);
    		}
    	}
    	
    	@Override
    	public void onFinish() {
    	}
    }
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_index_layout);
        running = true;
        Bundle mBundle = this.getIntent().getExtras();
        uid = mBundle.getString("uid");
        key = mBundle.getString("key");
        
        ((TextView)findViewById(R.id.title_uname)).setText(uid);
        
        friHandle = (Button)findViewById(R.id.index_conf_fri);
        confHandle = (Button)findViewById(R.id.index_conf_tsk);
        friReq = (TextView)findViewById(R.id.index_num_fri);
        confReq = (TextView)findViewById(R.id.index_num_task);
        friHandle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Index.this, Fri_Handle.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Index.this.finish();
			}
		});
        
        confHandle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Index.this, User_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Index.this.finish();
			}
		});
        
        index = (Button)findViewById(R.id.buttom_index);
        friend = (Button)findViewById(R.id.buttom_friend);
        task = (Button)findViewById(R.id.buttom_task);
        game = (Button)findViewById(R.id.buttom_game);
        myTsk = (Button)findViewById(R.id.title_unfin);
        game.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(t, "功能暂未开启", Toast.LENGTH_SHORT).show();
			}
		});
        
        friend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Index.this, User_Fri.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Index.this.finish();
			}
		});
        
        task.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Index.this, User_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Index.this.finish();
			}
		});
        
        myTsk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Index.this, User_Unfin.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Index.this.finish();
			}
		});

        networkHandler.getNews(uid, key, new refresh());
        index.setClickable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
