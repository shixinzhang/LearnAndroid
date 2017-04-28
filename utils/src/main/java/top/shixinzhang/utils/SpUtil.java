package top.shixinzhang.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * <br> Description: SharedPreference 工具类
 * <p>
 * <br> Created by shixinzhang on 17/4/21.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SpUtil {
    private SpUtil() {
    }

    private static final String DEFAULT_NAME = "shixinzhang_sp";

    // TODO: 17/4/21 改成读写锁，效率更好？
    private static final Object mLockObj = new Object();
    private static String mName = DEFAULT_NAME;

    private static void setName(String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("SharedPreferences cannot be null!");
        }

    }

    public static <T> void saveData(Context context, String key, T object) {
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
    public static <T> void saveData(Context context, String spName, String key, T object) {
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
            }
            editor.apply();
        }
    }

    public static <T> T getData(Context context, String key, T defaultValue) {
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
    public static <T> T getData(Context context, String spName, String key, T defaultValue) {
        synchronized (mLockObj) {
            SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);

            switch (defaultValue.getClass().getSimpleName()) {
                case "String":
                    sp.getString(key, (String) defaultValue);
                    break;
                case "Integer":
                    sp.getInt(key, (Integer) defaultValue);
                    break;
                case "Boolean":
                    sp.getBoolean(key, (Boolean) defaultValue);
                    break;
                case "Float":
                    sp.getFloat(key, (Float) defaultValue);
                    break;
                case "Long":
                    sp.getLong(key, (Long) defaultValue);
                    break;
            }
            return null;
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
