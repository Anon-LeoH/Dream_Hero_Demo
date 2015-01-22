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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.getData;
import com.example.test.getData.news;

public class Fri_Handle extends Activity {

    private String uid;
    private String key;
    private Context t = this;
    ProgressDialog mpd;
    
    // Add argv Starts Here
    
    private ListView friList;
    private Button ret;
    private SimpleAdapter apt;
    private ArrayList<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
    private ArrayList<String> stackList;
    
    // Add argv Ends Here
    
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
                ArrayList<String> ids = new ArrayList<String>();
				ArrayList<Map<String, String>> fList = tmp.flist;
				ArrayList<Map<String, String>> tList = tmp.tlist;
				ArrayList<Map<String, String>> cList = tmp.clist;
				dataList.clear();
                for (int i = 0; i < fList.size(); i++) {
					ids.add(fList.get(i).get("target"));
					Map<String, String> tmp2 = new HashMap<String, String>();
					tmp2.put("id", fList.get(i).get("target"));
					dataList.add(tmp2);
                }
				apt = new SimpleAdapter(t, dataList, R.layout.friend_list_item, new String[] {"id"}, new int[] {R.id.friend_name});
				stackList = ids;
				friList.setAdapter(apt);
    		}
    	}
    	
    	@Override
    	public void onFinish() {
    	}
    }
    
    // Add Handler Starts Here
    private class confFri extends AsyncHttpResponseHandler {
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
    			networkHandler.getNews(uid, key, new refresh());
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
        setContentView(R.layout.friend_req_layout);
        running = true;
        Bundle mBundle = this.getIntent().getExtras();
        uid = mBundle.getString("uid");
        key = mBundle.getString("key");
        
        ((TextView)findViewById(R.id.title_uname)).setText(uid);
        
        // Add process Starts Here
        friList = (ListView)findViewById(R.id.freq_list);
        ret = (Button)findViewById(R.id.freq_return);
        ret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				running = false;
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(Fri_Handle.this, User_Fri.class);
				bundle.putString("uid", uid);
				bundle.putString("key", key);
				intent.putExtras(bundle);
				startActivity(intent);
				Fri_Handle.this.finish();
			}
		});
        
        friList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String target = stackList.get(arg2);
				networkHandler.addFriend(uid, key, target, new confFri());
			}
		});
        
        networkHandler.getNews(uid, key, new refresh());
        
        // Add process Ends Here
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
