package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.getData;
import com.example.test.getData.news;

public class User_Fri extends Activity {

    private String uid;
    private String key;
    private Context t = this;
    ProgressDialog mpd;
    
    // Add argv Starts Here
    
    private Button addFri;
    private ListView friList;
    private EditText addName;
    
    private SimpleAdapter apt;
    private ArrayList<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
    private ArrayList<String> stackList = new ArrayList<String>();
    
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
    		}
    	}
    	
    	@Override
    	public void onFinish() {
    	}
    }
    
    // Add Handler Starts Here
    private class friendList extends AsyncHttpResponseHandler {
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
                JSONObject info = getData.getObjectInfo(argv);
                try {
                	JSONArray tmp1 = info.getJSONArray("fri");
                	ArrayList<String> fList = new ArrayList<String>();
                	for (int i = 0; i < tmp1.length(); i++) {
        				String tmp2 = tmp1.opt(i).toString(); 
        				fList.add(tmp2);
        			}					
					JSONObject tmp3 = info.getJSONObject("friName");
					ArrayList<String> fnList = new ArrayList<String>();
					for (int i = 0; i < tmp1.length(); i++) {
        				String tmp4 = tmp3.getString(tmp1.opt(i).toString()); 
        				fnList.add(tmp4);
        			}
					dataList.clear();
					for (int i = 0; i < fList.size(); i++) {
						Map<String, String> tmp5 = new HashMap<String, String>();
						tmp5.put("uid", fList.get(i));
						tmp5.put("name", fnList.get(i) + "( id : " + fList.get(i) + " )");
						dataList.add(tmp5);
					}
					apt = new SimpleAdapter(t, dataList, R.layout.friend_list_item, new String[] {"name"}, new int[] {R.id.friend_name});
					stackList = fList;
					friList.setAdapter(apt);
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
    
    private class addFriend extends AsyncHttpResponseHandler {
    	@Override
		public void onStart() {
    		mpd = ProgressDialog.show(t, "Requesting", "Please Wait...");
    	}
    	
    	@Override
    	public void onSuccess(String argv) {
    		Boolean err = getData.error(argv);
    		if (err == null) {
    			Toast.makeText(t, "数据解析失败", Toast.LENGTH_SHORT).show();
    		} else if (err) {
    			Toast.makeText(t, getData.getStringInfo(argv), Toast.LENGTH_SHORT).show();
    		} else {
                String rlt = getData.getStringInfo(argv);
                addName.setText("");
                if (rlt.equals("suc")) {
                	Toast.makeText(t, "成功添加好友", Toast.LENGTH_SHORT).show();
                } else {
                	Toast.makeText(t, "成功成为好友", Toast.LENGTH_SHORT).show();
                	networkHandler.userInfo(uid, key, uid, new friendList());
                }
    		}
    	}
    	
    	@Override
    	public void onFinish() {
    		mpd.dismiss();
    	}
    }
    
    private class removeFriend extends AsyncHttpResponseHandler {
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
    			networkHandler.userInfo(uid, key, uid, new friendList());
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
        setContentView(R.layout.user_friend_layout);
        running = true;
        Bundle mBundle = this.getIntent().getExtras();
        uid = mBundle.getString("uid");
        key = mBundle.getString("key");
        
        ((TextView)findViewById(R.id.title_uname)).setText(uid);
        
        // Add process Starts Here        
        friList = (ListView)findViewById(R.id.friend_list);
        addFri = (Button)findViewById(R.id.btn_add_friend);
        addName = (EditText)findViewById(R.id.add_name);
        addFri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = addName.getText().toString();
				if (name.equals("")) {
					Toast.makeText(t, "用户名不得为空", Toast.LENGTH_SHORT).show();
				} else {
					networkHandler.addFriend(uid, key, name, new addFriend());
				}
			}
		});
        
        friList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String target = stackList.get(arg2);
				networkHandler.removeFriend(uid, key, target, new removeFriend());
				return true;
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
				intent.setClass(User_Fri.this, User_Index.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Fri.this.finish();
			}
		});
        
        task.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Fri.this, User_Task.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Fri.this.finish();
			}
		});
        
        myTsk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(User_Fri.this, User_Unfin.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				User_Fri.this.finish();
			}
		});

        networkHandler.getNews(uid, key, new refresh());
        networkHandler.userInfo(uid, key, uid, new friendList());
        friend.setClickable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
