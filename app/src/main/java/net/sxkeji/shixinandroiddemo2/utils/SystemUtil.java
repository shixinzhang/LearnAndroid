package net.sxkeji.shixinandroiddemo2.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import net.sxkeji.shixinandroiddemo2.service.AutoClickService;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/31.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SystemUtil {

    public static boolean checkIfOpenAccessibility(Context context) {
        int enable = 0;
        final String service = context.getPackageName() + "/" + AutoClickService.class.getCanonicalName();
        try {
            enable = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        if (enable == 1) {
            String enabledSettingValues = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (!TextUtils.isEmpty(enabledSettingValues)) {
                simpleStringSplitter.setString(enabledSettingValues);
                while (simpleStringSplitter.hasNext()) {
                    String enabledValue = simpleStringSplitter.next();
                    if (enabledValue.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void jumpToSettingAccessibility(Context context) {
        Toast.makeText(context, "请点击应用名称开启辅助功能", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }
}
