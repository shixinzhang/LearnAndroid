package top.shixinzhang.datacrawler.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * <br> Description: SharedPreferences 工具类
 * <p>
 * <br> Created by shixinzhang on 17/4/21.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SpUtil {

    private static final String DEFAULT_NAME = "shixinzhang_sp";
    private static final Object mLockObj = new Object();     // TODO: 17/4/21 改成读写锁，效率更好？

    private static String mName = DEFAULT_NAME;

    private SpUtil() {
    }

    public static void saveDataInDefault(Context context, String key, Object object) {
        saveData(context, mName, key, object);
    }

    /**
     * 保存数据
     *
     * @param context
     * @param spName
     * @param key
     * @param object
     */
    public static void saveData(Context context, String spName, String key, Object object) {
        if (object == null) {
            return;
        }
        synchronized (mLockObj) {
            SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            switch (object.getClass().getSimpleName()) {
                case "String":
                    editor.putString(key, (String) object);
                    break;
                case "Integer":
                    editor.putInt(key, (Integer) object);
                    break;
                case "Boolean":
                    editor.putBoolean(key, (Boolean) object);
                    break;
                case "Float":
                    editor.putFloat(key, (Float) object);
                    break;
                case "Long":
                    editor.putLong(key, (Long) object);
                    break;
                case "HashSet":
                case "Set":
                    editor.putStringSet(key, (Set<String>) object);
                    break;
            }
            editor.apply();
        }
    }

    @SuppressWarnings("unchecked")
    public static Object getDataFromDefault(Context context, String key, Object defaultValue) {
        return getData(context, mName, key, defaultValue);
    }

    /**
     * 查询数据
     *
     * @param context
     * @param spName
     * @param key
     * @param defaultValue
     */
    public static Object getData(Context context, String spName, String key, Object defaultValue) {
        if (defaultValue == null) {
            throw new IllegalArgumentException("Default value used for get data type, so it can't be null!");
        }
        synchronized (mLockObj) {
            SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
            Object result = null;

            switch (defaultValue.getClass().getSimpleName()) {
                case "String":
                    result = sp.getString(key, (String) defaultValue);
                    break;
                case "Integer":
                    result = sp.getInt(key, (Integer) defaultValue);
                    break;
                case "Boolean":
                    result = sp.getBoolean(key, (Boolean) defaultValue);
                    break;
                case "Float":
                    result = sp.getFloat(key, (Float) defaultValue);
                    break;
                case "Long":
                    result = sp.getLong(key, (Long) defaultValue);
                    break;
                case "HashSet":
                case "Set":
                    result = sp.getStringSet(key, (Set<String>) defaultValue);
                    break;
            }
            return result;
        }
    }

    public static void removeData(Context context, String key) {
        removeData(context, key);
    }

    /**
     * 删除指定 KEY 的数据
     *
     * @param context
     * @param spName
     * @param key
     */
    public static void removeData(Context context, String spName, String key) {
        synchronized (mLockObj) {
            SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.remove(key);
        }
    }

    /**
     * 清除数据
     *
     * @param context
     * @param spName
     */
    public static void clear(Context context, String spName) {
        synchronized (mLockObj) {
            SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
        }
    }
}
