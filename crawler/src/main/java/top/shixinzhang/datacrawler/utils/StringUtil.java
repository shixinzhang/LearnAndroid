package top.shixinzhang.datacrawler.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/5/4.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class StringUtil {
    public static String getNumberFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("").trim();
    }
}
