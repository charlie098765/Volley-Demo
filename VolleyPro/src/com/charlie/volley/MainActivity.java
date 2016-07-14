package com.charlie.volley;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.NetworkImageView;
import com.charlie.volley.bean.City;
import com.charlie.volley.bean.UserAllInfo;
import com.charlie.volley.core.Result;
import com.charlie.volley.interfaces.ResponseCallBackListener;
import com.charlie.volley.utils.BitmapUtils;
import com.charlie.volley.utils.Constants;
import com.charlie.volley.utils.RequestUtils;
import com.charlie.volley.utils.ResourceUtil;
import com.example.testvolley.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * volley测试Activity
 * @author Charlie
 *
 */
public class MainActivity extends Activity {
	private final String TAG="MainActivity";
	private Context context;
	private TextView tv_city;
	private TextView tv_show;
	private ImageView iv_show;
	private NetworkImageView niv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getBaseContext();
        tv_show=(TextView) findViewById(ResourceUtil.getId(context, "tv_request_str"));
        tv_city=(TextView) findViewById(ResourceUtil.getId(context, "tv_city"));
        iv_show=(ImageView) findViewById(ResourceUtil.getId(context, "iv_request_img"));
        niv_show=(NetworkImageView) findViewById(ResourceUtil.getId(context, "niv_test"));

        
		

        
        String jsonUrl="http://localhost:8888/testByJSP.action";//自己的服务器测试工程，设置手动代理的时候可以访问
//       服务器获取的json字符串格式{"success":true,"user":{"password":"JSON","name":"JSONServlet","say":"Hello , i am a servlet !","id":"123"}}
        String url="http://www.baidu.com";
        String xmlUrl="http://flash.weather.com.cn/wmaps/xml/china.xml";
        String imgUrl="http://img5.duitang.com/uploads/item/201408/01/20140801210934_KNvFR.thumb.700_0.jpeg";
//        getXml(xmlUrl);
//        getImg(imgUrl);
//        getString(url);
//		getJson(jsonUrl);
		getGson(jsonUrl);
		
		
		
		
		
    }

	private void getGson(String jsonUrl) {
		RequestUtils.getGsonResult(context, jsonUrl, UserAllInfo.class, new ResponseCallBackListener() {
			
			@Override
			public void OnResponseCallBack(Result result) {
				if(result.getState()==Constants.STATUS_SUCCESS){
					UserAllInfo user=(UserAllInfo) result.getTag();
					tv_show.setText(user.getUser().toString());
				}
				
			}
		});
	}

	private void getXml(String xmlUrl) {
		RequestUtils.getXmlResult(context, xmlUrl,City.class, new ResponseCallBackListener() {
	
			@Override
			public void OnResponseCallBack(Result result) {
				if(result.getState()==Constants.STATUS_SUCCESS){
					tv_city.setText(result.getTag().toString());
				}else{
				tv_city.setText("error:"+result.getMessage());
				}
				
			}
		});
	}

	private void getString(String url) {
		//1.获取String get方式
        RequestUtils.getStringResult(context, url, new ResponseCallBackListener() {
			
			@Override
			public void OnResponseCallBack(Result result) {
				tv_show.setText(result.getMessage());
			}
		});
        
        //2.获取string post方式提交
       Map<String, String> map = new HashMap<String, String>();  
        map.put("params1", "value1");  
        map.put("params2", "value2");  
        RequestUtils.postStringResult(context, url,map, new ResponseCallBackListener() {
			
			@Override
			public void OnResponseCallBack(Result result) {
				tv_show.setText(result.getMessage());
			}
		});
	}

	private void getJson(String url) {
		// 3.获取json对象
		   RequestUtils.getJsonResult(context, url, new ResponseCallBackListener() {

	    @Override
		public void OnResponseCallBack(Result result) {
				try {
					if(result.getState()==Constants.STATUS_SUCCESS){
						JSONObject jo=new JSONObject(result.getMessage());
						String str = null;
						if(jo!=null){
							 str=jo.get("user").toString();
						}
						tv_show.setText("json--->"+str);
					}else{
						tv_show.setText("json--->"+result.getTag());
						
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
		}
		});
	}

	private void getImg(String imgUrl) {
		 //6.NetworkImageView方式加载图片
        RequestUtils.getNetworkImageViewResult(context, imgUrl, niv_show, R.drawable.ic_launcher, R.drawable.pink, 400, 0, null);
        //5.获取图片，用ImageLoader方式，更高效
        	
//        	RequestUtils.getImageLoaderResult(context, imgUrl, iv_show, R.drawable.ic_launcher, R.drawable.pink, 400, 0, null);
        
        //4.获取图片，用imgRequest 
        RequestUtils.getImageResult(context, imgUrl,600,600, new ResponseCallBackListener() {
			
			@Override
			public void OnResponseCallBack(Result result) {
				if(result.getState()==Constants.STATUS_SUCCESS){
					iv_show.setImageBitmap((Bitmap) result.getTag());
				}else{
					iv_show.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_launcher));
				}
				
			}
		});
	}

    
   
  
}
