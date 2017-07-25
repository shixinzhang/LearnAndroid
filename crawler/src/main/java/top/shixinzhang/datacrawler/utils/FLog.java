package top.shixinzhang.datacrawler.utils;

import android.text.TextUtils;


import java.io.File;

import top.shixinzhang.datacrawler.Config;

import static top.shixinzhang.datacrawler.utils.DateUtils.getYMdHms;

/**
 * com.yuntu.wxcrawler.common.utils
 * Author : erik
 * Email  : erik7@126.com
 * Date   : 17/2/24
 * Version 1.0
 * Description:
 * <p>
 * <p>
 * Copyright (c) 2017 www.yaomaiche.com
 * All Rights Reserved.
 */

public class FLog {

    public static void i(String log) {
        if (TextUtils.isEmpty(log))
            return;

        if (!log.endsWith("\n"))
            log = log.concat("\n");

        FileUtils.writeFile(getPath(), getYMdHms(System.currentTimeMillis()) + " " + log, true);
    }

    private static String getPath() {
        return Config.EXT_DIR + File.separator + "log.txt";
    }

    /**
     * 清除log文件
     */
    public static void clear() {
        FileUtils.delete(getPath());
    }
}
