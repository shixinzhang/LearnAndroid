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
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Arrays;
import java.util.List;

import top.shixinzhang.mvpcrawler.Config;
import top.shixinzhang.mvpcrawler.entity.SupplierInfoBean;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.utils.NodeUtils;

/**
 * Description:
 * <br> 卖好车 v6.4.1
 * <p>
 * <br> Created by shixinzhang on 17/8/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class SellNiceCarView implements CrawlerContract.View {
    private final String TAG = this.getClass().getSimpleName();
    private List<String> mCoreClassNameList = Arrays.asList("");

    public static SellNiceCarView create() {
        return new SellNiceCarView();
    }

    @Override
    public void setPresenter(@NonNull final CrawlerContract.Presenter presenter) {

    }

    @Override
    public String getAppName() {
        return Config.APP_NAME_SELL_NICE_CAR;
    }

    @Override
    public boolean isCorePage(@NonNull final String className) {
        return mCoreClassNameList.contains(className);
    }

    @Override
    public boolean isMainTab(@NonNull final String className) {
        return className.equals(Config.CLASS_NAME_SNC_MAIN_TAB);
    }

    @Override
    public boolean isBrandListPage(final AccessibilityNodeInfo rootNode, final String className) {
        boolean result = NodeUtils.hasText(rootNode, "发布车源");
        return result;
    }

    @Override
    public boolean isSeriesPage(final String className) {
        return false;
    }

    @Override
    public boolean isModelsPage(final String className) {
        return false;
    }

    @Override
    public boolean isSourceTypePage(final String className) {
        return false;
    }

    @Override
    public boolean isDetailPage(final String className) {
        return false;
    }

    @Override
    public boolean isNumberPage(final String className) {
        return false;
    }

    /**
     * 进入品牌列表页面
     *
     * @param rootNode
     * @return
     */
    @Override
    public boolean openBrandList(@NonNull final AccessibilityNodeInfo rootNode) {
        boolean result = NodeUtils.actionText(rootNode, "车源/寻车");
//        com.maihaoche.bentley:id/main_bottom_source

        // TODO: 17/8/7 滑动一下 ?
        return result;
    }

    /**
     * 打开拨号页面
     * @param rootNode
     * @return
     */
    @Override
    public boolean openNumberPage(@NonNull final AccessibilityNodeInfo rootNode) {
        boolean clickResult = NodeUtils.clickNode(rootNode, "电话咨询");
        return clickResult;
    }

    @Override
    public AccessibilityNodeInfo getBrandListNode(@NonNull final AccessibilityNodeInfo rootNode) {
        return NodeUtils.findNode(rootNode, "com.maihaoche.bentley:id/search_list_brand");
    }

    @Override
    public AccessibilityNodeInfo getBrandNodeFromItem(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        return itemNode.getChild(0).getChild(1);
    }

    @Override
    public AccessibilityNodeInfo getSeriesListNode(final AccessibilityNodeInfo rootNode) {
        return NodeUtils.findNode(rootNode, "com.maihaoche.bentley:id/list");
    }

    @Override
    public AccessibilityNodeInfo getSeriesNodeFromItem(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        return itemNode.getChild(0);
    }

    @Override
    public AccessibilityNodeInfo getModelListNode(@NonNull final AccessibilityNodeInfo rootNode) {
        return NodeUtils.findNode(rootNode, "android:id/list");
    }

    @Override
    public String getModelIdentity(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        if (NodeUtils.hasText(itemNode, "秒车库") || NodeUtils.hasText(itemNode, "推荐车源")) {
            return null;
        }

        if (itemNode.getChildCount() < 3) {
            return null;
        }

        AccessibilityNodeInfo modelNameNode = itemNode.getChild(1).getChild(0);
        if (modelNameNode == null || modelNameNode.getText() == null) {
            Log.e(TAG, "车源型号名称节点 没找到！");
            return null;
        }
        StringBuilder identityStr = new StringBuilder(modelNameNode.getText().toString());
        AccessibilityNodeInfo priceNode = itemNode.getChild(1).getChild(1);
        AccessibilityNodeInfo colorNode = itemNode.getChild(3);
        AccessibilityNodeInfo companyNode = itemNode.getChild(5).getChild(0);

        NodeUtils.safetyAppendString(identityStr, priceNode);
        NodeUtils.safetyAppendString(identityStr, colorNode);
        NodeUtils.safetyAppendString(identityStr, companyNode);

        return identityStr.toString();
    }

    @Override
    public SupplierInfoBean getInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception{
        String carName = NodeUtils.getTextByNodeId(rootNode, "com.maihaoche.bentley:id/tv_name");//车辆名称
        String value = NodeUtils.getTextByNodeId(rootNode, "com.maihaoche.bentley:id/value");
        String company = NodeUtils.getTextByNodeId(rootNode, "com.maihaoche.bentley:id/sale_name");//公司

        return new SupplierInfoBean(null, null, company, carName + value);
    }

    @Override
    public SupplierInfoBean getNumberInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception {

        String name = NodeUtils.getTextByNodeId(rootNode, "com.maihaoche.bentley:id/tv_name");  //名称
        String phone = NodeUtils.getTextByNodeId(rootNode, "com.maihaoche.bentley:id/tv_number");

        return new SupplierInfoBean(phone, name, null, null);
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


}
