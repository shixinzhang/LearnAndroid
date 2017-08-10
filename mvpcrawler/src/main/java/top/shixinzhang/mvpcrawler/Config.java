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

package top.shixinzhang.mvpcrawler;

import android.os.Environment;

import java.io.File;

/**
 * Description:
 * <br> 配置信息
 * <p>
 * <br> Created by shixinzhang on 17/8/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class Config {

    public static final String FOLDER = "mvpcrawler" + File.separator;
    public static final String EXT_DIR = Environment.getExternalStorageDirectory() + File.separator + FOLDER;
    public static final String CRASH_LOG_PATH = EXT_DIR + File.separator + "crash" + File.separator + "crashlog.txt";
    // app 名称
    public final static String APP_NAME_SELL_NICE_CAR = "卖好车";

    //包名
    public static final String PKG_SELL_NICE_CAR = "com.maihaoche.bentley";

    //页面 className
    public static final String CLASS_NAME_SNC_MAIN_TAB =  PKG_SELL_NICE_CAR + ".activity.MainActivity";   //卖好车首页
    public static final String CLASS_NAME_SNC_SERIES = PKG_SELL_NICE_CAR + ".source.activity.SeriesActivity";   //车系列表
    public static final String CLASS_NAME_SNC_MODELS = PKG_SELL_NICE_CAR + ".source.activity.SkuListActivity";  //SKU 列表
    public static final String CLASS_NAME_SNC_DETAIL = PKG_SELL_NICE_CAR + ".source.activity.SkuDetailActivity";    //详情
    public static final String DIALOG_NAME_CALL = PKG_SELL_NICE_CAR + ".source.c.c";    //拨号对话框
    public final static String CLASS_NAME_RECYCLER_VIEW = "android.support.v7.widget.RecyclerView";   //List 改变
    public final static String CLASS_NAME_CALL_DIALOG = "android.support.v7.app.AlertDialog";  //弹出的对话框
}
