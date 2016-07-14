package com.charlie.volley.application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

public class CHApplication extends Application {
    public static RequestQueue volleyQueue;
    @Override
    public void onCreate() {
        super.onCreate();

        /* Volley配置 */
        // 建立Volley的Http请求队列
    	/**
    	 * 创建请求消息队列对象
    	 * 注意这里拿到的RequestQueue是一个请求队列对象，
    	 * 它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求。
    	 * RequestQueue内部的设计就是非常合适高并发的，因此我们不必为每一次HTTP请求都创建一个RequestQueue对象，这是非常浪费资源的，
    	 * 基本上在每一个需要和网络交互的Activity中创建一个RequestQueue对象就足够了
    	 */
        volleyQueue = Volley.newRequestQueue(getApplicationContext());
    }

    // 开放Volley的HTTP请求队列接口
    public static RequestQueue getRequestQueue() {
        return volleyQueue;
    }
}
