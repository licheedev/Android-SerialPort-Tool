package com.licheedev.serialtool.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class PrefHelper {

    private static PrefHelper sInstance;

    private SharedPreferences mPreferences;

    public static void initDefault(Context context) {
        sInstance = new PrefHelper(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public static PrefHelper getDefault() {
        return sInstance;
    }

    public static PrefHelper get(Context context, String name) {
        return new PrefHelper(context, name);
    }

    private PrefHelper(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    private PrefHelper(Context context, String name) {
        mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor edit() {
        return mPreferences.edit();
    }

    public SharedPreferences.Editor putInt(String key, int value) {
        return edit().putInt(key, value);
    }

    public void saveInt(String key, int value) {
        putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public SharedPreferences.Editor putFloat(String key, float value) {
        return edit().putFloat(key, value);
    }

    public void saveFloat(String key, float value) {
        putFloat(key, value).apply();
    }

    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return edit().putBoolean(key, value);
    }

    public void saveBoolean(String key, boolean value) {
        putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public SharedPreferences.Editor putLong(String key, long value) {
        return edit().putLong(key, value);
    }

    public void saveLong(String key, long value) {
        putLong(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public SharedPreferences.Editor putString(String key, String value) {
        return edit().putString(key, value);
    }

    public void saveString(String key, String value) {
        putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }
}
