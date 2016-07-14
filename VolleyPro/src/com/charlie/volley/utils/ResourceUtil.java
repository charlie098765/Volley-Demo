package com.charlie.volley.utils;


import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.util.TypedValue;
/**
 * 加载资源的工具栏
 * @author Charlie
 *
 */
public  class ResourceUtil {
	/**
	 * 代替R.layout读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getLayoutId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "layout",
				paramContext.getPackageName());
	}
	/**
	 * 代替R.string读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getStringId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "string",
				paramContext.getPackageName());
	}
	/**
	 * 代替R.drawable读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getDrawableId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString,
				"drawable", paramContext.getPackageName());
	}
	/**
	 * 代替R.style读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getStyleId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "style",
				paramContext.getPackageName());
	}

	/**
	 * 代替R.id读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static final int getId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "id",
				paramContext.getPackageName());
	}		
	/**
	 * 代替R.color读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getColorId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "color",
				paramContext.getPackageName());
	}
	/**
	 * 代替R.attr读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getAttrId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "attr",
				paramContext.getPackageName());
	}
	/**
	 * 代替R.dimen读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getDimenId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "dimen",
				paramContext.getPackageName());
	}
	/**
	 * 代替R.anim读取
	 * @param paramContext
	 * @param paramString
	 * @return
	 */
	public static int getAnimId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "anim",
				paramContext.getPackageName());
	}
	
	
	/**对于context.getResources().getIdentifier无法获取的数据,或者数组资源反射值*/
	private static Object getResourceId(Context context,String name, String type) {
	
		String className = context.getPackageName() +".R";
	
		try {
		
			Class<?> cls = Class.forName(className);
		
			for (Class<?> childClass : cls.getClasses()) {
		
			String simple = childClass.getSimpleName();
		
			if (simple.equals(type)) {
			
				for (Field field : childClass.getFields()) {
				
					String fieldName = field.getName();
				
					if (fieldName.equals(name)) {
				
						System.out.println(fieldName);
					
						return field.get(null);
				
					}
				
				}
			
			}
		
			}
		
		} catch (Exception e) {
	
			e.printStackTrace();
	
		}
		return null;

	}
	/**
	 * context.getResources().getIdentifier无法获取到styleable的数据
	 * 
	 */
	public static int getStyleableId(Context context, String name) {

		return ((Integer)getResourceId(context, name,"styleable")).intValue();

	}

	/**
	 * 获取styleable的ID号数组
	 * @param context
	 * @param name
	 * @return
	 */
	public static int[] getStyleableArray(Context context,String name) {

		return (int[])getResourceId(context, name,"styleable");

	}
	/**
	 * 读取Theme里面的资源
	 * @param context
	 * @param attrId
	 * @param defaultColor
	 * @return
	 */
	public static int getColorFromTheme(Context context, String attrId, int defaultColor) {
		return ResourceUtil.getColorFromTheme(context, 
			ResourceUtil.getAttrId(context,attrId),Color.WHITE);
	}
	
	/**
	 * 读取Theme里面的资源
	 * @param context
	 * @param attrId
	 * @param defaultColor
	 * @return
	 */
	public static int getColorFromTheme(Context context, int attrId, int defaultColor) {
		final TypedValue sTypedValue = new TypedValue();
		synchronized (sTypedValue) {
            final TypedValue value = sTypedValue;
            final Theme theme = context.getTheme();
            if (theme != null) {
                theme.resolveAttribute(attrId, value, true);
                if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
                    return value.data;
                }
            }

            return defaultColor;
        }
    }

}
