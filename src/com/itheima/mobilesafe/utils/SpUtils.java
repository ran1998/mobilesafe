package com.itheima.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
	private static SharedPreferences sp = null;
	
	/**
	 * sharedPerference获取值
	 * @param ctx
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Context ctx, String key, String defaultValue) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
		}
		return sp.getString(key, defaultValue);
	}
	
	/**
	 * 添加string类型的值
	 * @param ctx
	 * @param key
	 * @param value
	 */
	public static void putString(Context ctx, String key, String value) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}
	
	/**
	 * 获取boolean类型的值
	 * @param ctx
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Context ctx, String key, boolean defaultValue) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defaultValue);
	}
	
	/**
	 * 添加boolean类型的值
	 * @param ctx
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context ctx, String key, boolean value) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 移除key值
	 * @param ctx
	 * @param key
	 */
	public static void remove(Context ctx, String key) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
		}
		sp.edit().remove(key).commit();
	}
}
