package com.example.test;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class networkHandler {
	private static final String BASE_URL = "http://172.18.187.94:7777/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	    client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	    client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
	    return BASE_URL + relativeUrl;
	}
	
	// Three POST method
	
    public static void login(String uid, String psw, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("psw", psw);
        post("login", argv, cb);
    }
    
    public static void register(String uid, String psw, String name, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("psw", psw);
        argv.put("name", name);
        post("reg", argv, cb);
    }
    
    public static void addTsk(String uid, String key, String title, String date, String content, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("title", title);
        argv.put("date", date);
        argv.put("content", content);
        post("addTsk", argv, cb);
    }
    
    // X GET method
    
    public static void getNews(String uid, String key, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        get("news", argv, cb);
    }
    
    public static void userInfo(String uid, String key, String target, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("target", target);
        get("uinfo", argv, cb);
    }
    
    public static void taskInfo(String uid, String key, String target, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("target", target);
        get("tinfo", argv, cb);
    }
    
    public static void addFriend(String uid, String key, String target, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("target", target);
        get("addFri", argv, cb);
    }
    
    public static void confirmTask(String uid, String key, String target, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("target", target);
        get("confTsk", argv, cb);
    }
    
    public static void removeTask(String uid, String key, String target, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("target", target);
        get("remvTsk", argv, cb);
    }
    
    public static void removeFriend(String uid, String key, String target, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("target", target);
        get("remvFri", argv, cb);
    }
    
    public static void getAllOwnTask(String uid, String key, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("type", "my");
        get("allt", argv, cb);
    }
    
    public static void getAllUndoTask(String uid, String key, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        argv.put("type", "undo");
        get("allt", argv, cb);
    }
    
    public static void logout(String uid, String key, AsyncHttpResponseHandler cb) {
        RequestParams argv = new RequestParams();
        argv.put("uid", uid);
        argv.put("key", key);
        get("logout", argv, cb);
    }
}
