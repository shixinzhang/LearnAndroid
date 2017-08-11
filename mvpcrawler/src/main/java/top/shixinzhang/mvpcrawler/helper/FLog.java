/*
 * Copyright (c) 2017. shixinzhang (shixinzhang2016@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.shixinzhang.mvpcrawler.helper;

import android.text.TextUtils;


import java.io.File;

import top.shixinzhang.utils.DateUtils;
import top.shixinzhang.utils.FileUtils;


public class FLog {

    public static void i(String log) {
        i(log, getPath());
    }

    public static void i(String log, String path) {
        if (TextUtils.isEmpty(log))
            return;

        if (!log.endsWith("\n"))
            log = log.concat("\n");

        FileUtils.writeFile(path, DateUtils.getYMdHms(System.currentTimeMillis()) + " " + log, true);
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
