package com.charlie.volley.customrequest;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
/**
 * 自定义GsonRequst
 * @author Charlie
 *
 * @param <T>
 */
public class GsonRequest<T> extends Request<T>{
	private Class<? extends Object> clazz;
	private Listener<T> listener;
	private Gson gson;
	public GsonRequest(int method, Class<? extends Object> clazz,String url, Listener<T> listener,ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz=clazz;
		this.listener= listener;
		gson=new Gson();
		
	}
	public GsonRequest(Class<? extends Object> klass,String url, Listener<T> listener,ErrorListener errorListener) {
		this(Method.GET, klass,url, listener,errorListener);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		
		  String parsed;
	        try {
	            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	            return (Response<T>) Response.success(gson.fromJson(parsed, clazz), HttpHeaderParser.parseCacheHeaders(response));
	        } catch (UnsupportedEncodingException e) {
	            return Response.error(new ParseError(e));
	        }
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

}
