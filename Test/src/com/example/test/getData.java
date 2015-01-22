package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class getData {
	public static class news {
		public ArrayList<Map<String, String>> flist;
		public ArrayList<Map<String, String>> tlist;
		public ArrayList<Map<String, String>> clist;
		
		public news(String s){
			flist = new ArrayList<Map<String,String>>();
			tlist = new ArrayList<Map<String,String>>();
			clist = new ArrayList<Map<String,String>>();
			String[] tmp = s.split("&&&");
			for (int i = 0; i < tmp.length / 3; i++) {
				Map<String, String> tmp3 = new HashMap<String, String>();
				tmp3.put("ID", tmp[i * 3]);
				tmp3.put("type", tmp[i * 3 + 1]);
				tmp3.put("target", tmp[i * 3 + 2]);
				if (tmp3.get("type").equals("fri")) flist.add(tmp3);
				else if (tmp3.get("type").equals("conf")) clist.add(tmp3);
				else tlist.add(tmp3);
			}
		}
	}
	
	public static news getNews(String s) {
		news tmp = new news(s);
		return tmp;
	}
    public static Boolean error(String dataString) {
    	try {
			JSONObject data = new JSONObject(dataString);
			if (data.getInt("errno") == 0) return true;
			else return false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
    }
    
    public static String getType(String dataString) {
    	try {
			JSONObject data = new JSONObject(dataString);
			return data.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String getStringInfo(String dataString) {
    	try {
			JSONObject data = new JSONObject(dataString);
			return data.getString("info");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String[] getStringArrayInfo(String dataString) {
    	try {
			JSONObject data = new JSONObject(dataString);
			return data.getString("info");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static JSONObject getObjectInfo(String dataString) {
    	try {
			JSONObject data = new JSONObject(dataString);
			return data.getJSONObject("info");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static ArrayList<JSONObject> getObjectArrayInfo(String dataString) {
    	try {
			JSONObject data = new JSONObject(dataString);
			JSONArray tmp = data.getJSONArray("info");
			ArrayList<JSONObject> rlt = new ArrayList<JSONObject>();
			for (int i = 0; i < tmp.length(); i++) {
				JSONObject tmp2 = (JSONObject)tmp.opt(i); 
				rlt.add(tmp2);
			}
			return rlt;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}
