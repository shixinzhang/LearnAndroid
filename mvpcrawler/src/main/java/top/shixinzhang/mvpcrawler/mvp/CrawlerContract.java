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

package top.shixinzhang.mvpcrawler.mvp;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Set;

import io.reactivex.Observable;
import top.shixinzhang.mvpcrawler.entity.SupplierInfoBean;
import top.shixinzhang.mvpcrawler.mvp.view.BaseView;

/**
 * Description:
 * <br> 方便复用的契约类
 * <p>
 * <br> Created by shixinzhang on 17/8/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public interface CrawlerContract {

    /**
     * 不参与业务，只负责数据的增删改查
     */
    interface Model {
        int MODE_STOP = -1; //停止
        int MODE_START = 0;
        public static final int MODE_SELECT_BRAND = 1;    //首页，选择品牌
        public final static int MODE_SELECT_CAR_SERIES = 2;   //选择车系
        public final static int MODE_SELECT_CAR_MODEL = 3;    //选择车款
        public final static int MODE_GET_INFO = 4;
        public final static int MODE_GET_NUMBER = 5;
        int MODE_SELECT_SOURCE_TYPE = 6;  //选择来源

        int getMode();

        void setMode(@IntRange(from = -1, to = 6) final int mode);

        @NonNull
        Set<String> getClickedBrands(); //点击过的品牌名

        @NonNull
        Set<String> getClickedSeries();

        @NonNull
        Set<String> getClickedSourceTypes(); //点击过的来源

        @NonNull
        Set<String> getClickedModels(); //点击过的品牌名

        @Nullable
        String getCurrentCarName(); //当前车名称

        void setCurrentCarName();

        Observable<SupplierInfoBean> updateCurrentSupplierInfo(SupplierInfoBean currentSupplier);   //更新当前供应商信息

        Set<SupplierInfoBean> addSupplier(SupplierInfoBean supplierInfoBean);   //添加供应商信息

        void addClickedBrands(@NonNull String carBrandName); //添加点击的品牌名称
    }

    /**
     * 具体的布局、节点、点击操作
     */
    interface View extends BaseView<Presenter> {
        String getAppName();    //获取 app 名称

        boolean isCorePage(String className);   //是否是主要页面列表其中之一

        boolean isMainTab(String className);    //是否在首页

        boolean isBrandListPage(String className);  //是否在品牌列表页面

        boolean enterBrandList(@NonNull final AccessibilityNodeInfo rootNode);   //打开有品牌列表的页面

        AccessibilityNodeInfo getBrandListNode(AccessibilityNodeInfo rootNode); //获取品牌列表 Node

        AccessibilityNodeInfo getBrandNodeFromItem(@NonNull AccessibilityNodeInfo itemNode) throws Exception; //获取 item 中的品牌节点

    }

    /**
     * 业务逻辑
     */
    interface Presenter {
        void prepare(final String className);

        void startApp();    //启动对应的 APP

        void openBrandList(final AccessibilityNodeInfo rootNode, final String className);   //打开车辆列表

        void iterateBrands(final AccessibilityNodeInfo rootNode, final String className);   //遍历品牌

        void iterateSeries(final String className);   //遍历车系

        void selectSourceType(final String className);  //选择车源类型：国产 or 进口

        void iterateModels(final String className);   //遍历车款

        void enterDetail(); //进入详情

        void getInfo(final String className); //获取信息

        void receiveEvent(AccessibilityNodeInfo rootNode, AccessibilityEvent event);
    }

}
