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
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Arrays;
import java.util.List;

import top.shixinzhang.mvpcrawler.entity.SupplierInfoBean;
import top.shixinzhang.mvpcrawler.helper.Config;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.utils.NodeUtils;
import top.shixinzhang.utils.ShellUtils;

/**
 * Description:
 * <br> 易捷好车 v2.7.1
 * <p>
 * <br> Created by shixinzhang on 17/8/14.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class EjAutoView implements CrawlerContract.View {
    private final String TAG = this.getClass().getSimpleName();

    private CrawlerContract.Presenter mPresenter;
    private List<String> mWhiteList = Arrays.asList(
            Config.EjAuto.CLASS_NAME_MAIN, Config.EjAuto.CLASS_NAME_DETAIL,
            Config.EjAuto.CLASS_NAME_MODELS, Config.EjAuto.CLASS_NAME_SERIES,
            Config.EjAuto.DIALOG_NAME_CALL, Config.CLASS_NAME_LIST_VIEW,
            Config.CLASS_NAME_RECYCLER_VIEW, Config.CLASS_NAME_DIALOG);

    public static EjAutoView create() {
        return new EjAutoView();
    }

    @Override
    public void setPresenter(@NonNull final CrawlerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getAppName() {
        return Config.APP_NAME_YI_JIE_HAO_CHE;
    }

    @Override
    public String getAppPackageName() {
        return Config.EjAuto.PKG_EJ_AUTO;
    }

    @Override
    public boolean isCorePage(final String className) {
        return mWhiteList.contains(className);
    }

    @Override
    public boolean isMainTab(final String className) {
        return Config.EjAuto.CLASS_NAME_MAIN.equals(className);
    }

    @Override
    public boolean isBrandListPage(final AccessibilityNodeInfo rootNode, final String className) {
        return NodeUtils.hasText(rootNode, "品牌车型");
    }

    @Override
    public boolean isSeriesPage(final String className) {
        return Config.EjAuto.CLASS_NAME_SERIES.equals(className) || isListView(className);
    }

    @Override
    public boolean isModelsPage(final String className) {
        return Config.EjAuto.CLASS_NAME_MODELS.equals(className) || isListView(className);
    }

    @Override
    public boolean isSourceTypePage(final String className) {
        return Config.EjAuto.CLASS_NAME_SOURCE_TYPE.equals(className);
    }

    @Override
    public boolean isDetailPage(final String className) {
//        return Config.EjAuto.CLASS_NAME_DETAIL.equals(className) || isListView(className);
        return Config.EjAuto.CLASS_NAME_DETAIL.equals(className);
    }

    @Override
    public boolean isNumberPage(final String className) {
        return Config.EjAuto.DIALOG_NAME_CALL.equals(className);
    }

    private boolean isListView(final String className) {
        return Config.CLASS_NAME_LIST_VIEW.equals(className);
    }

    @Override
    public boolean openBrandList(@NonNull final AccessibilityNodeInfo rootNode) {
        boolean result = NodeUtils.actionText(rootNode, "车源行情");
//        if (!result){
//
//        }
        return result;
    }

    @Override
    public boolean openNumberPage(@NonNull final AccessibilityNodeInfo rootNode) {

        boolean clickResult = NodeUtils.clickNode(rootNode, "cn.ejauto.app:id/btn_to_phone");
        if (!clickResult) {
            clickResult = NodeUtils.clickNode(rootNode, "电话");
        }
        return clickResult;
    }

    @Override
    public AccessibilityNodeInfo getBrandListNode(@NonNull final AccessibilityNodeInfo rootNode) {
        AccessibilityNodeInfo parentNode = NodeUtils.findNode(rootNode, "cn.ejauto.app:id/lv_car_brand");
        return parentNode != null ? parentNode.getChild(0) : null;
    }

    @Override
    public AccessibilityNodeInfo getBrandNodeFromItem(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {

        AccessibilityNodeInfo node = NodeUtils.findNode(itemNode, "cn.ejauto.app:id/tv_car_brand");
        if (node != null) {
            return node;
        }
        if (itemNode.getChildCount() == 1) {
            return itemNode.getChild(0).getChild(1);
        } else if (itemNode.getChildCount() == 2) {
            return itemNode.getChild(1).getChild(1);
        }
        return null;
    }

    @Override
    public AccessibilityNodeInfo getSeriesListNode(final AccessibilityNodeInfo rootNode) {
        AccessibilityNodeInfo parentNode = NodeUtils.findNode(rootNode, "cn.ejauto.app:id/lv_car_series");
        return parentNode != null ? parentNode.getChild(0) : null;
    }

    @Override
    public AccessibilityNodeInfo getSeriesNodeFromItem(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        AccessibilityNodeInfo node = NodeUtils.findNode(itemNode, "cn.ejauto.app:id/tv_car_series");
        if (node != null) {
            return node;
        }
        if (itemNode.getChildCount() == 1) {
            return itemNode.getChild(0).getChild(0);
        } else if (itemNode.getChildCount() == 2) {
            return itemNode.getChild(1).getChild(0);
        }

        return null;
    }

    @Override
    public AccessibilityNodeInfo getModelListNode(@NonNull final AccessibilityNodeInfo rootNode) {
        return NodeUtils.findNode(rootNode, "cn.ejauto.app:id/lv_car_source_price_list");
    }

    @Override
    public String getModelIdentity(@NonNull final AccessibilityNodeInfo itemNode) throws Exception {
        if (itemNode == null || NodeUtils.hasText(itemNode, "发布寻车") || itemNode.getChildCount() < 2) {
            return null;
        }
        AccessibilityNodeInfo nameNode = itemNode.getChild(2).getChild(0);
        if (nameNode == null || nameNode.getText() == null) {
            Log.e(TAG, "车源型号名称节点 没找到！");
            return null;
        }
        StringBuilder identifyStr = new StringBuilder(nameNode.getText().toString());
        AccessibilityNodeInfo detailNode = itemNode.getChild(3);
        AccessibilityNodeInfo colorNode = itemNode.getChild(4);

        NodeUtils.safetyAppendString(identifyStr, detailNode);
        NodeUtils.safetyAppendString(identifyStr, colorNode);

        if (itemNode.getChildCount() > 7) {
            AccessibilityNodeInfo priceParentNode = itemNode.getChild(7);
            if (priceParentNode != null) {
                AccessibilityNodeInfo officialPriceNode = priceParentNode.getChild(0);   //官方价
                AccessibilityNodeInfo nakedPriceNode = priceParentNode.getChild(3);  //裸车价

                NodeUtils.safetyAppendString(identifyStr, officialPriceNode);
                NodeUtils.safetyAppendString(identifyStr, nakedPriceNode);
            }
        }

        return identifyStr.toString();
    }

    @Override
    public SupplierInfoBean getInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception {
        String carName = NodeUtils.getTextByNodeId(rootNode, "cn.ejauto.app:id/tv_car_category");//车辆名称
        String nakedPrice = NodeUtils.getTextByNodeId(rootNode, "cn.ejauto.app:id/tv_naked_car_price"); //裸车价
        String sourceId = NodeUtils.getTextByNodeId(rootNode, "cn.ejauto.app:id/tv_car_source_id");//车源ID

        String company = NodeUtils.getTextByNodeId(rootNode, "cn.ejauto.app:id/tv_company");
        String name = NodeUtils.getTextByNodeId(rootNode, "cn.ejauto.app:id/tv_user_name");//名称

        return new SupplierInfoBean(null, name, company, carName + nakedPrice + sourceId);
    }

    @Override
    public SupplierInfoBean getNumberInfo(@NonNull final AccessibilityNodeInfo rootNode) throws Exception {
//        AccessibilityNodeInfo node = NodeUtils.findNode(rootNode, "cn.ejauto.app:id/tv_title");
        String phone = NodeUtils.getTextByNodeId(rootNode, "cn.ejauto.app:id/tv_title");//电话
        return new SupplierInfoBean().setPhone(phone);
    }

    @Override
    public boolean needExitSeriesList(final AccessibilityNodeInfo rootNode) {
        return false;
    }

    @Override
    public boolean needExitModelList(final AccessibilityNodeInfo rootNode) {
//        return rootNode != null && rootNode.getChildCount() == 1;
        return false;
    }

    @Override
    public boolean needExitDetail(final AccessibilityNodeInfo rootNode) {
        return false;
    }

    @Override
    public void resolveNotWorked(final AccessibilityNodeInfo rootNode, final int mode, final String currentClassName) {
        Log.e(TAG, "resolveNotWorked: " + mode + " / " + currentClassName);

        if (mode == CrawlerContract.Model.MODE_SELECT_CAR_SERIES) { //应该在车系列表
            if (Config.EjAuto.CLASS_NAME_MODELS.equals(currentClassName)) {  //实际在车款列表
                ShellUtils.clickBack();
            }
        } else if (mode == CrawlerContract.Model.MODE_SELECT_CAR_MODEL) {       //应该在车款列表
            if (Config.EjAuto.CLASS_NAME_DETAIL.equals(currentClassName)) {  //实际在详情
                ShellUtils.clickBack();
            }
        } else if (mode == CrawlerContract.Model.MODE_GET_INFO) {     //应该在详情
            if (Config.EjAuto.CLASS_NAME_DETAIL.equals(currentClassName)) {  //实际也在详情，但就是不动
                if (openNumberPage(rootNode)) {
                    SystemClock.sleep(200);
                    ShellUtils.clickBack();
                }
            } else if (Config.EjAuto.CLASS_NAME_MODELS.equals(currentClassName)) {    //实际在车款列表页
                // TODO: 17/8/21 下拉通知栏
//                ShellUtils.execCmd("input tab 200 400");
            }
        } else if (mode == CrawlerContract.Model.MODE_GET_NUMBER) {
            if (Config.EjAuto.CLASS_NAME_DETAIL.equals(currentClassName)) {
                ShellUtils.clickBack();
            }
        }
    }

    @Override
    public void openNumberExtra(final AccessibilityNodeInfo rootNode, final String className) {

    }

}
