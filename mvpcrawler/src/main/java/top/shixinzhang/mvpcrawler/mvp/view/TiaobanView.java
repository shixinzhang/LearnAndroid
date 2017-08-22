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

package top.shixinzhang.mvpcrawler.mvp.view;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Arrays;
import java.util.List;

import top.shixinzhang.mvpcrawler.entity.SupplierInfoBean;
import top.shixinzhang.mvpcrawler.helper.Config;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.utils.NodeUtils;

/**
 * Description:
 * <br> 跳板网 v2.1.2_20161221.apk
 * <p>
 * <br> Created by shixinzhang on 17/8/22.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class TiaobanView implements CrawlerContract.View {
    private final String TAG = this.getClass().getSimpleName();

    private List<String> mWhiteList = Arrays.asList(
            Config.Tiaoban.CLASS_NAME_MAIN, Config.Tiaoban.CLASS_NAME_BRAND_LIST,
            Config.Tiaoban.CLASS_NAME_Models, Config.Tiaoban.CLASS_NAME_DETAIL,
            Config.Tiaoban.CLASS_NAME_NUMBER, Config.CLASS_NAME_DIALOG);

    @Override
    public String getAppName() {
        return Config.APP_NAME_TIAO_BAN;
    }

    @Override
    public String getAppPackageName() {
        return Config.Tiaoban.PKG_TIAO_BAN;
    }

    @Override
    public boolean isCorePage(final String className) {
        return mWhiteList.contains(className);
    }

    @Override
    public boolean isMainTab(final String className) {
        return Config.Tiaoban.CLASS_NAME_MAIN.equals(className);
    }

    @Override
    public boolean isBrandListPage(final AccessibilityNodeInfo rootNode, final String className) {
        return NodeUtils.hasText(rootNode, "平行进口");
    }

    @Override
    public boolean isSeriesPage(final String className) {
        return false;
    }

    @Override
    public boolean isModelsPage(final String className) {
        return Config.Tiaoban.CLASS_NAME_Models.equals(className);
    }

    @Override
    public boolean isSourceTypePage(final String className) {
        return false;
    }

    @Override
    public boolean isDetailPage(final String className) {
        return Config.Tiaoban.CLASS_NAME_DETAIL.equals(className);
    }

    @Override
    public boolean isNumberPage(final String className) {
        return Config.Tiaoban.CLASS_NAME_NUMBER.equals(className);
    }

    @Override
    public boolean openBrandList(@NonNull final AccessibilityNodeInfo rootNode) {
        boolean result = NodeUtils.actionText(rootNode, "车源");
        return result;
    }

    @Override
    public boolean openNumberPage(@NonNull final AccessibilityNodeInfo rootNode) {
        return false;
    }

    @Override
    public AccessibilityNodeInfo getBrandListNode(@NonNull final AccessibilityNodeInfo rootNode) {


        return null;
    }

    @Override
    public AccessibilityNodeInfo getBrandNodeFromItem(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        return null;
    }

    @Override
    public AccessibilityNodeInfo getSeriesListNode(final AccessibilityNodeInfo rootNode) {
        return null;
    }

    @Override
    public AccessibilityNodeInfo getSeriesNodeFromItem(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        return null;
    }

    @Override
    public AccessibilityNodeInfo getModelListNode(@NonNull final AccessibilityNodeInfo rootNode) {
        return null;
    }

    @Override
    public String getModelIdentity(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        return null;
    }

    @Override
    public SupplierInfoBean getInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception {
        return null;
    }

    @Override
    public SupplierInfoBean getNumberInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception {
        return null;
    }

    @Override
    public boolean needExitSeriesList(final AccessibilityNodeInfo rootNode) {
        return false;
    }

    @Override
    public boolean needExitModelList(final AccessibilityNodeInfo rootNode) {
        return false;
    }

    @Override
    public boolean needExitDetail(final AccessibilityNodeInfo rootNode) {
        return false;
    }

    @Override
    public void resolveNotWorked(final AccessibilityNodeInfo rootNode, final int mode, final String currentClassName) {

    }

    @Override
    public void setPresenter(@NonNull final CrawlerContract.Presenter presenter) {

    }
}
