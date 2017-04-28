package net.sxkeji.shixinandroiddemo2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <header>
 * Description:
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 * <p>
 * Create at: 11/17/2016
 * </p>
 * <p>
 * Update at: 11/17/2016
 * </p>
 * <p>
 * Related links: <a href="${link_address}">${linkName}</a>
 * </p>
 */
public class StringUtils {

    public static boolean isRgbValue(String color){
//        Pattern pattern = Pattern.compile("^#(([0123456789abcdefABCDEF]{6})|([0123456789abcdefABCDEF]{8}))$");
        Pattern pattern = Pattern.compile("^#(([0123456789abcdefABCDEF]{6})|([0123456789abcdefABCDEF]{8}))$");
        Matcher matcher = pattern.matcher(color);
        return matcher.matches();
    }
}
