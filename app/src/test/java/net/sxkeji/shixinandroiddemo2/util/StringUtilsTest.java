package net.sxkeji.shixinandroiddemo2.util;

import org.junit.Test;

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
public class StringUtilsTest {

    @Test
    public void testIsRgbValue() throws Exception {
        System.out.println("#6688 is color " + StringUtils.isRgbValue("#6688"));
        System.out.println("#070A01 is color " + StringUtils.isRgbValue("#668899"));
        System.out.println("dd8899 is color " + StringUtils.isRgbValue("dd8899"));
        System.out.println("#yy8899 is color " + StringUtils.isRgbValue("#yy8899"));
        System.out.println("#FFFFFF is color " + StringUtils.isRgbValue("#FFFFFf"));

    }
}