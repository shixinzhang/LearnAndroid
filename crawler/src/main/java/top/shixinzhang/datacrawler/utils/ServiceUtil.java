package top.shixinzhang.datacrawler.utils;

import android.content.Context;
import android.nfc.Tag;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;


public class ServiceUtil {

    private final static String TAG = "ServiceUtil";

    /**
     * 判断AccessibilityService服务是否开启
     *
     * @param mContext
     * @return
     */
    public static boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = "top.shixinzhang.datacrawler/top.shixinzhang.datacrawler.DataCrawlerService";
        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.i(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, "Error finding setting, default accessibility to not found: "
                    + e);
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.i(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    Log.i(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.i(TAG, "We've found the correct setting - accessibility is switched on!");
                        accessibilityFound = true;
                    }
                }
            }
        } else {
            Log.i(TAG, "***ACCESSIBILIY IS DISABLED***");
        }

        return accessibilityFound;
    }

}
