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

package top.shixinzhang.datacrawler.utils;

import android.text.TextUtils;

import top.shixinzhang.datacrawler.Config;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/7/25.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class PageUtil {
    /**
     * 是否为首页
     * @param clazz
     * @return
     */
    public static boolean isMainTab(String clazz){
        return TextUtils.equals(clazz, Config.CT_MAIN_TAB);
    }

    /**
     * 是否为车系页面
     * @param className
     * @return
     */
    public static boolean isSeriesTab(final String className) {
        return TextUtils.equals(className, Config.CT_SELECT_CAR_SERIES);
    }

    /**
     * 是否为车型页面
     * @param className
     * @return
     */
    public static boolean isModelTab(final String className) {
        return TextUtils.equals(className, Config.CT_SELECT_CAR_BRAND);
    }

    /**
     * 是否为车详情页面
     * @param className
     * @return
     */
    public static boolean isDetailTab(final String className) {
        return TextUtils.equals(className, Config.CT_SELECT_CAR_DETAIL);
    }
}
