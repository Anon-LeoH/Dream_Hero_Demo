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
import android.widget.Toast;

import com.example.test.getData;

public class Register extends Activity {
	EditText uid;
	EditText psw;
	EditText name;
	Button enter;
	Button cancel;
    private Context t = this;
    ProgressDialog mpd;
    final public class register extends AsyncHttpResponseHandler {    	
    	@Override
		public void onStart() {
    		mpd = ProgressDialog.show(t, "Requesting", "Please Wait...");
    	}
    	
    	@Override
    	public void onSuccess(String argv) {
    		Toast.makeText(t, argv, Toast.LENGTH_SHORT).show();
    		Boolean err = getData.error(argv);
    		if (err == null) {
    			Toast.makeText(t, "数据解析失败", Toast.LENGTH_SHORT).show();    			
    		} else if (err) {
    			Toast.makeText(t, getData.getStringInfo(argv), Toast.LENGTH_SHORT).show();
    		} else {
                Bundle b = new Bundle();
                b.putString("uid", uid.getText().toString());
                b.putString("psw", psw.getText().toString());
                Intent intent = new Intent();
                intent.setClass(Register.this, Login_Register.class);
                intent.putExtras(b);
                startActivity(intent);
                Register.this.finish();
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
        setContentView(R.layout.register_layout);
        uid = (EditText)findViewById(R.id.reg_uid);
        psw = (EditText)findViewById(R.id.reg_psw);
        name= (EditText)findViewById(R.id.reg_uname);
        enter = (Button)findViewById(R.id.btn_reg_enter);
        cancel = (Button)findViewById(R.id.btn_reg_cancel);
        enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tmp1 = uid.getText().toString();
				String tmp2 = psw.getText().toString();
				String tmp3 = name.getText().toString();
				if (tmp1.equals("") || tmp2.equals("") || tmp3.equals("")) {
					Toast.makeText(t, "表项不能为空", Toast.LENGTH_SHORT).show();
				} else {
					register tmp = new register();
					networkHandler.register(tmp1, tmp2, tmp3, tmp);
				}					
			}
		});
        cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Register.this, Login_Register.class);
				startActivity(intent);
				Register.this.finish();
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
