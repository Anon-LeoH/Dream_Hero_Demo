package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.getData;
import com.example.test.getData.news;

public class User_Task extends Activity {

    private String uid;
    private String key;
    private Context t = this;
    ProgressDialog mpd;
    
    // Add argv Starts Here
    
    private Button addTsk;
    private ListView tskList;
    
    private SimpleAdapter apt;
    private ArrayList<Map<String, String>> dataList = new  ArrayList<Map<String, String>>();
    private ArrayList<String> stackList;
    
    // Add argv Ends Here

    
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
				myTsk.setText("您有" + String.valueOf(tList.size()) + "个未完成任务");
				index.setText("主页(" + String.valueOf(fList.size() + cList.size()) + ")");
				index.setText("主页(" + String.valueOf(fList.size() + cList.size()) + ")");
    		}
    	}
    	
    	@Override
    	public void onFinish() {
    	}
    }
    
    // Add Handler Starts Here
    private class taskList extends AsyncHttpResponseHandler {
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
                ArrayList<JSONObject> info = getData.getObjectArrayInfo(argv);
                ArrayList<String> ids = new ArrayList<String>();
                try {
                	dataList.clear();
                	for (int i = 0; i < info.size(); i++) {
					    JSONObject tmp = info.get(i);
					    ids.add(tmp.getString("ID"));
						Map<String, String> tmp2 = new HashMap<String, String>();
						tmp2.put("title", tmp.getString("title"));
						tmp2.put("date", tmp.getString("date"));
						tmp2.put("owner", uid);
						dataList.add(tmp2);
                	}
					apt = new SimpleAdapter(t, dataList, R.layout.task_list_item, new String[] {"title", "owner", "date"}, new int[] {R.id.task_title, R.id.task_owner, R.id.task_date});
					stackList = ids;
					tskList.setAdapter(apt);
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
    
    
    private class removeTask extends AsyncHttpResponseHandler {
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
    			Toast.makeText(t, "删除成功", Toast.LENGTH_SHORT).show();
    			Intent intent = new Intent();
    			Bundle bundle = new Bundle();
    			bundle.putString("uid", uid);
				bundle.putString("key", key);
    			intent.putExtras(bundle);
    			intent.setClass(User_Task.this, User_Task.class);
    			startActivity(intent);
    			User_Task.this.finish();
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
        setContentView(R.layout.user_task_layout);
        running = true;
        Bundle mBundle = this.getIntent().getExtras();
        uid = mBundle.getString("uid");
        key = mBundle.getString("key");
        
        ((TextView)findViewById(R.id.title_uname)).setText(uid);
        
        // Add process Starts Here        
        tskList = (ListView)findViewById(R.id.task_list);
        addTsk = (Button)findViewById(R.id.btn_add_task);
        addTsk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Task.this, Edit_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Task.this.finish();
			}
		});
        
        tskList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String target = stackList.get(arg2);
				networkHandler.removeTask(uid, key, target, new removeTask());
				return true;
			}
		});
        
        tskList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Task.this, View_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				bundle.putString("target", stackList.get(arg2));
				intent.putExtras(bundle);
				startActivity(intent);
				User_Task.this.finish();
			}
		});
        
        // Add process Ends Here
        
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
        
        index.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Task.this, User_Index.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Task.this.finish();
			}
		});
        
        friend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Task.this, User_Fri.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Task.this.finish();
			}
		});
        
        myTsk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Task.this, User_Unfin.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Task.this.finish();
			}
		});

        networkHandler.getAllOwnTask(uid, key, new taskList());
        networkHandler.getNews(uid, key, new refresh());
        task.setClickable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
