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

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import org.w3c.dom.Node;

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
            Config.Tiaoban.CLASS_NAME_NUMBER, Config.CLASS_NAME_DIALOG,
            Config.CLASS_NAME_LIST_VIEW);


    public static TiaobanView create() {
        return new TiaobanView();
    }

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
        return false;
//        return NodeUtils.hasText(rootNode, "平行进口");
    }


    private boolean isListView(final String className) {
        return Config.CLASS_NAME_LIST_VIEW.equals(className);
    }

    @Override
    public boolean isSeriesPage(final String className) {
        return false;
    }

    @Override
    public boolean isModelsPage(final String className) {
        return Config.Tiaoban.CLASS_NAME_Models.equals(className) || isListView(className);
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
        boolean result = NodeUtils.clickNode(rootNode, "com.eage.tbw:id/sourdetail_relativelayout_company");
        return result;
    }

    @Override
    public AccessibilityNodeInfo getBrandListNode(@NonNull final AccessibilityNodeInfo rootNode) {
//        AccessibilityNodeInfo listNode = NodeUtils.findNode(rootNode, "com.eage.tbw:id/carsource_listview_carsource");
        return NodeUtils.findNode(rootNode, "com.eage.tbw:id/carsource_listview_carsource");
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
        return NodeUtils.findNode(rootNode, "com.eage.tbw:id/carsource_listview_carsource");
    }

    @Override
    public String getModelIdentity(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {

        AccessibilityNodeInfo nameNode = NodeUtils.findNode(itemNode, "com.eage.tbw:id/carsource_textview_title");
        if (nameNode == null || nameNode.getText() == null) {
            Log.e(TAG, "车源型号名称节点 没找到！");
            return null;
        }

        AccessibilityNodeInfo priceNode = NodeUtils.findNode(itemNode, "com.eage.tbw:id/carsource_textview_price");
        AccessibilityNodeInfo dateNode = NodeUtils.findNode(itemNode, "com.eage.tbw:id/carsource_textview_datetime");

        StringBuilder stringBuilder = new StringBuilder(nameNode.getText().toString());
        NodeUtils.safetyAppendString(stringBuilder, priceNode);
        NodeUtils.safetyAppendString(stringBuilder, dateNode);

        return stringBuilder.toString();
    }

    @Override
    public SupplierInfoBean getInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception {
        String name = NodeUtils.getTextByNodeId(rootNode, "com.eage.tbw:id/sourdetail_textview_name");
        String carName = NodeUtils.getTextByNodeId(rootNode, "com.eage.tbw:id/sourdetail_textview_cartitle");
        String price = NodeUtils.getTextByNodeId(rootNode, "com.eage.tbw:id/sourdetail_textview_carprice");
        String note = NodeUtils.getTextByNodeId(rootNode, "com.eage.tbw:id/sourdetail_textview_carnote");   //备注
        String company = NodeUtils.getTextByNodeId(rootNode, "com.eage.tbw:id/sourdetail_textview_company");

        return new SupplierInfoBean(null, name, company, carName + price + note);
    }

    @Override
    public SupplierInfoBean getNumberInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception {
        boolean clickDetailResult = NodeUtils.clickNode(rootNode, "com.eage.tbw:id/button3");   //点击 联系我们

        SystemClock.sleep(50);
        String phone = NodeUtils.getTextByNodeId(rootNode, "com.eage.tbw:id/textview2");
        if (TextUtils.isEmpty(phone)) {
            Log.i(TAG, "phone : " + phone);
        }
//
//        AccessibilityNodeInfo listViewNode = NodeUtils.findNode(rootNode, "com.eage.tbw:id/listview1");
//        if (listViewNode != null) {
//            int childCount = listViewNode.getChildCount();
//            Log.d(TAG, String.format("有 %d 个联系人信息", childCount));
//
//            if (childCount < 3) {
//                return new SupplierInfoBean();
//            }
//
//            AccessibilityNodeInfo child;
//            for (int i = 2; i < childCount; i++) {
//                child = listViewNode.getChild(i);
//                NodeUtils.findNode(child)
//            }
//        }

        return new SupplierInfoBean().setPhone(phone);
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
        Log.e(TAG, "resolveNotWorked: " + mode + " / " + currentClassName);
    }

    @Override
    public void setPresenter(@NonNull final CrawlerContract.Presenter presenter) {

    }
}
