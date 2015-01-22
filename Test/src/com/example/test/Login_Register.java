package com.example.test;

import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.getData;

public class Login_Register extends Activity {

    private SharedPreferences sp;
    private EditText uidEntry;
    private EditText pswEntry;
    private Button enter;
    private String uid;
    private String psw;
    private Button reg;
    private Context t = this;
    ProgressDialog mpd;
    final public class login extends AsyncHttpResponseHandler {    	
    	@Override
		public void onStart() {
    		mpd = ProgressDialog.show(t, "Requesting", "Please Wait...");
    	}
    	
    	@Override
    	public void onSuccess(String argv) {
    		Boolean err = getData.error(argv);
    		if (err == null) {
    			Toast.makeText(t, "数据解析失败", Toast.LENGTH_SHORT).show();
    			uidEntry.setText("");
    			pswEntry.setText("");
    		} else if (err) {
    			Toast.makeText(t, getData.getStringInfo(argv), Toast.LENGTH_SHORT).show();
    			uidEntry.setText("");
    			pswEntry.setText("");
    		} else {
    			Bundle b = new Bundle();
    			Editor e = sp.edit();
    			e.putBoolean("SAVED", true);
    			e.putString("uid", uid);
    			e.putString("psw", psw);
    			e.commit();
    			b.putString("key", getData.getStringInfo(argv));
    			b.putString("uid", uid);
    			Intent intent = new Intent();
    			intent.setClass(Login_Register.this, User_Index.class);
    			intent.putExtras(b);
    			startActivity(intent);
    			Login_Register.this.finish();
    		}
    	}
    	
    	@Override
    	public void onFinish() {
    		mpd.dismiss();
    	}
    }
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Bundle mBundle = this.getIntent().getExtras();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uidEntry = (EditText)findViewById(R.id.uid_login);
        pswEntry = (EditText)findViewById(R.id.psw_login);
        enter = (Button)findViewById(R.id.btn_login);
        reg = (Button)findViewById(R.id.btn_reg); 
        if (sp.getBoolean("SAVED", false)) {
        	uid = sp.getString("uid", "");
        	psw = sp.getString("psw", "");
        	login tmp = new login();
        	networkHandler.login(uid, psw, tmp);
        } if (mBundle != null) {
        	uid = mBundle.getString("uid");
        	psw = mBundle.getString("psw");
        	login tmp = new login();
        	networkHandler.login(uid, psw, tmp);
        }
        enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uid = uidEntry.getText().toString();
				psw = pswEntry.getText().toString();
				if (uid.equals("") || psw.equals("")) {
					Toast.makeText(t, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
				} else {
					login tmp = new login();
		        	networkHandler.login(uid, psw, tmp);
				}
			}
		});
        reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Login_Register.this, Register.class);
				startActivity(intent);
				Login_Register.this.finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
