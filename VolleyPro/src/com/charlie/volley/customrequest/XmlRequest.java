package com.charlie.volley.customrequest;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
/**
 * 自定义XmlRequest,模仿StringRequest
 * @author Charlie
 *
 */
public class XmlRequest extends Request<XmlPullParser>{
	private Listener<XmlPullParser> mListener;
	public XmlRequest(int method, String url, Listener<XmlPullParser> listener,ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mListener=listener;
	}
	public XmlRequest(String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
        this(Method.GET, url,listener,  errorListener);
    }

	@Override
	protected Response<XmlPullParser> parseNetworkResponse(
			NetworkResponse response) {
		
		 String parsed;
		 XmlPullParser xpp = null;
	        try {
	            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	           xpp=XmlPullParserFactory.newInstance().newPullParser();
	           xpp.setInput(new StringReader(parsed));
	           return Response.success(xpp, HttpHeaderParser.parseCacheHeaders(response)); 
	        } catch (Exception error) {
	            parsed = new String(response.data);
	            return Response.error(new ParseError(error));
	        }
	}

	@Override
	protected void deliverResponse(XmlPullParser response) {
		mListener.onResponse(response);
		
	}

}
