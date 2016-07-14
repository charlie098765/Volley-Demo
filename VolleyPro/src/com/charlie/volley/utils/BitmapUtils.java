package com.charlie.volley.utils;

import com.example.testvolley.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * 
 * @author Charlie
 *
 */
public class BitmapUtils {
	
	
	/**
	 * 将指定图片缩放到需要的宽和高
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * 应用场景iv_show.setImageBitmap( BitmapUtils. 
	    decodeSampledBitmapFromResource(getResources(), R.drawable.girl, 300, 300));  
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,  
	        int reqWidth, int reqHeight) {
	    //第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
	    final BitmapFactory.Options options = new BitmapFactory.Options();  
	    options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeResource(res, resId, options);  
	    // 调用上面定义的方法计算inSampleSize值  
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
	    // 使用获取到的inSampleSize值再次解析图片  
	    options.inJustDecodeBounds = false;  
	    return BitmapFactory.decodeResource(res, resId, options);  
	}  
	
	/**
	 * 计算图片缩放的比例
	 * blog:
	 * http://blog.csdn.net/guolin_blog/article/details/9316683
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,  
	        int reqWidth, int reqHeight) {  
	    // 源图片的高度和宽度  
	    final int height = options.outHeight;  
	    final int width = options.outWidth;  
	    int inSampleSize = 1;  
	    if (height > reqHeight || width > reqWidth) {  
	        // 计算出实际宽高和目标宽高的比率  
	        final int heightRatio = Math.round((float) height / (float) reqHeight);  
	        final int widthRatio = Math.round((float) width / (float) reqWidth);  
	        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高  
	        // 一定都会大于等于目标的宽和高。  
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;  
	    }  
	    return inSampleSize;  
	}  
	
}
