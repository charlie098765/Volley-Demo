package com.charlie.volley.utils;

import android.util.Log;

/**
 * 日志工具类
 * @author Charlie
 *
 */
public class CHLog {

	public static boolean DEBUG = false; 
	
	private static final String DEFAULT_TAG = "charlie_volley_log";
	
	public static void i(String msg ){
		
		if (DEBUG) {
			Log.i(DEFAULT_TAG, msg);
		}
		
	}
	public static void i(String tag ,String msg ){
		
		if (DEBUG) {
			Log.i(tag, msg);
		}
		
	}
	public static void i(String tag ,String msg,Throwable tr ){
		
		if (DEBUG) {
			Log.i(tag, msg,tr);
		}
		
	}
	public static void d(String tag ,String msg ){
		
		if (DEBUG) {
			Log.i(tag, msg);
		}
		
	}
	
	
}
