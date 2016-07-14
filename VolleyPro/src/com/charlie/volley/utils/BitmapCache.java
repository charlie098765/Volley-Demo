package com.charlie.volley.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
/**
 * 图片缓存类
 * @author Charlie
 *
 */
public class BitmapCache implements ImageCache {  
	  
	private LruCache<String, Bitmap> mMemoryCache;
	@SuppressWarnings("unused")
	private BitmapCache(){
		
	}
	private static BitmapCache bc;
	public static BitmapCache getInstance(Context context){
		if(bc==null){
			bc=new BitmapCache(context);
		}
		return bc;
	}
    public BitmapCache(Context context) {  
    	  /*
    	   *  Get memory class of this device, exceeding this amount will throw an
    	   *   OutOfMemory exception.
    	   */
		  final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		 
		  // Use 1/8th of the available memory for this memory cache.
		  final int cacheSize = 1024 * 1024 * memClass / 8;
		 	 mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {  
            @Override  
            protected int sizeOf(String key, Bitmap bitmap) {  
//                return bitmap.getRowBytes() * bitmap.getHeight();  
            	 // The cache size will be measured in bytes rather than number of items.
		          return bitmap.getByteCount();
		          
            }  
        };  
    }  
  
    @Override  
    public Bitmap getBitmap(String url) {  
        return mMemoryCache.get(url);  
    }  
  
    @Override  
    public void putBitmap(String url, Bitmap bitmap) {  
    	if(getBitmap(url)!=null){
    		mMemoryCache.put(url, bitmap);  
    	}
    }  

}  