package com.charlie.volley.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.charlie.volley.application.CHApplication;
import com.charlie.volley.bean.City;
import com.charlie.volley.core.Result;
import com.charlie.volley.customrequest.GsonRequest;
import com.charlie.volley.customrequest.XmlRequest;
import com.charlie.volley.interfaces.XmlObject;
import com.charlie.volley.interfaces.ResponseCallBackListener;
/**
 * Volley 网络请求工具类
 * @author Charlie
 *
 */
public  class RequestUtils<T> {
	private final static String TAG="RequestUtils_Log";
	
	
	/**
	 * 获取Gson对象
	 * @param context
	 * @param url
	 * @param klass
	 * @param listener
	 */
	public static void getGsonResult(Context context,String url, Class<? extends Object> klass,final ResponseCallBackListener listener){
		GsonRequest<Object>  gr=new GsonRequest<Object>(klass, url, new Listener<Object>() {
			@Override
			public void onResponse(Object response) {
					listener.OnResponseCallBack(new Result().setState(Constants.STATUS_SUCCESS).setTag(response));	
			}
		}, getErrorListener(listener));
		
		addToQueue(gr);
	
	}	
	
	/** 
	 * 获取xml
	 * @param context
	 * @param url
	 * @param klass
	 * @param listener
	 */
	public static void getXmlResult(Context context,String url,final Class<?  extends XmlObject> klass,final ResponseCallBackListener listener){
		
		Listener<XmlPullParser> xmlListener=new Listener<XmlPullParser>(){

			@Override
			public void onResponse(XmlPullParser response) {
				List<XmlObject> list=new ArrayList<XmlObject>();
				try {
					int eventType=response.getEventType();
					while(eventType!=XmlPullParser.END_DOCUMENT){
						switch (eventType) {
						case XmlPullParser.START_TAG:
							String nodeName=response.getName();
							XmlObject xmlObj = klass.newInstance();
							xmlObj.setValueToXmlObject(response, list, nodeName);
							break;
						default:
							break;
						}
						eventType=response.next();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
				
				Log.i(TAG,"xml list-------->"+list.toString());
				listener.OnResponseCallBack(new Result().setState(Constants.STATUS_SUCCESS).setTag(list.toString()));
			}
			
		};
		
		XmlRequest xr=new XmlRequest(url,xmlListener, getErrorListener(listener));
		addToQueue(xr);
	}	
	
	/**
	 * 获取图片，NetworkImageView方式加载网络图片
	 * @param context
	 * @param url
	 * @param view
	 * @param nImageView
	 * @param defaultImageResId
	 * @param errorImageResId
	 * @param maxWidth
	 * @param maxHeight
	 * @param listener
	 */
	public static void getNetworkImageViewResult(Context context,String url,NetworkImageView networkImageView,int defaultImageResId,int errorImageResId,int maxWidth,int maxHeight,final ResponseCallBackListener listener){
		BitmapCache cache=  BitmapCache.getInstance(context);
		ImageLoader imgLoader=new ImageLoader(CHApplication.getRequestQueue(),cache);
		networkImageView.setDefaultImageResId(defaultImageResId);
		networkImageView.setErrorImageResId(errorImageResId);
		networkImageView.setImageUrl(url, imgLoader);
	}	
	

	/**
	 * ImageLoader方式加载网络图片
	 * @param context
	 * @param url
	 * @param view
	 * @param defaultImageResId
	 * @param errorImageResId
	 * @param maxWidth
	 * @param maxHeight
	 * @param listener
	 */
	public static void getImageLoaderResult(Context context,String url,ImageView view,int defaultImageResId,int errorImageResId,int maxWidth,int maxHeight,final ResponseCallBackListener listener){

		BitmapCache cache=  BitmapCache.getInstance(context);
		ImageLoader imgLoader=new ImageLoader(CHApplication.getRequestQueue(),cache);
		ImageListener imgListener=ImageLoader.getImageListener(view, defaultImageResId, errorImageResId);
		imgLoader.get(url, imgListener, maxWidth, maxHeight);
	}
	/**
	 * 加载图片
	 * @param context
	 * @param url
	 * @param maxWidth
	 * @param maxHeight
	 * @param listener
	 */
	public static void getImageResult(Context context,String url,int maxWidth,int maxHeight,final ResponseCallBackListener listener){
		Response.Listener<Bitmap> rListener=new  Response.Listener<Bitmap>(){

			@Override
			public void onResponse(Bitmap response) {
				listener.OnResponseCallBack(new Result().setState(Constants.STATUS_SUCCESS).setTag(response));
			}
			
		};
		/**
		 * 第三第四个参数:分别用于指定允许图片最大的宽度和高度，
		 * 如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，
		 * 指定成0的话就表示不管图片有多大，都不会进行压缩。
		 * 第五个参数:用于指定图片的颜色属性，Bitmap.Config下的几个常量都可以在这里使用，
		 * 其中ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小，
		 * 而RGB_565则表示每个图片像素占据2个字节大小。
		 * 第六个参数:是图片请求失败的回调，
		 * 这里我们当请求失败时在ImageView中显示一张默认图片
		 */
		ImageRequest ir=new ImageRequest(url, rListener, maxWidth, maxHeight,Config.ARGB_8888, getErrorListener(listener));
	
		addToQueue(ir);
	}
		
		/**
	 * 请求获取json对象
	 * @param context
	 * @param url
	 * @param map
	 * @param listener
	 */
	public static void getJsonResult(Context context,String url,final ResponseCallBackListener listener){
		
		 JsonObjectRequest	joRequest = new JsonObjectRequest(Method.POST,url, null, new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					Log.i(TAG, response.toString());
					listener.OnResponseCallBack(new Result().setState(Constants.STATUS_SUCCESS).setTag(response));
					
				}
			},new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Log.i(TAG, "network error:"+error==null?null:error.getMessage(),error);
					listener.OnResponseCallBack(new Result().setTag(error));
					
				}
			});
			addToQueue(joRequest);

		
		
	}

	private static void addToQueue(@SuppressWarnings("rawtypes") Request request) {
		CHApplication.getRequestQueue().add(request);
		CHApplication.getRequestQueue().start();
	}
	

	
	/**
	 * post方式发送请求,获取String对象
	 * @param context
	 * @param url
	 * @param map
	 * @param listener
	 */
	public static void postStringResult(Context context,String url,final Map<String,String> map,final ResponseCallBackListener listener){
		StringRequest sq=new StringRequest(Method.POST, url,getListener(listener), getErrorListener(listener)){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
		        return map; 
			}
		};
		addToQueue(sq);
		
	}
	
	
	/**
	 * 获取请求的字符串,get请求
	 * @param context
	 * @param url
	 * @param listener
	 */
    public static void getStringResult(Context context,String url,final ResponseCallBackListener listener){
    	
    	/**
    	 * 发出一条HTTP请求，我们还需要创建一个StringRequest对象
    	 * 这里new出了一个StringRequest对象，StringRequest的构造函数需要传入三个参数:
    	 * 第一个参数就是目标服务器的URL地址，
    	 * 第二个参数是服务器响应成功的回调，
    	 * 第三个参数是服务器响应失败的回调。
    	 * 
    	 * 其中，目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打印出服务器返回的内容，
    	 * 在响应失败的回调里打印出失败的详细信息。
    	 */
    	StringRequest sq=new StringRequest(url,getListener(listener),getErrorListener(listener));
    	
    	addToQueue(sq);
    	
    }
    
    
    
	private static ErrorListener getErrorListener(
			final ResponseCallBackListener listener) {
		return new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i(TAG, "network error:"+error==null?null:error.getMessage(),error);
				listener.OnResponseCallBack(new Result().setState(Constants.STATUS_ERROR).setTag(error));
			}
		};
	}
	private static Listener<String> getListener(
			final ResponseCallBackListener listener) {
		return new Response.Listener<String>(){ 

			@Override
			public void onResponse(String response) {
				Log.i(TAG, response);
				listener.OnResponseCallBack(new Result().setState(Constants.STATUS_SUCCESS).setMessage(response));
			}
		};
	}
	
}
