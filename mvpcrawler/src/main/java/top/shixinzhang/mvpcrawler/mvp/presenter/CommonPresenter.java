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

package top.shixinzhang.mvpcrawler.mvp.presenter;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Map;

import top.shixinzhang.mvpcrawler.DataCrawlerService;
import top.shixinzhang.mvpcrawler.entity.SupplierInfoBean;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.utils.ApplicationUtils;
import top.shixinzhang.utils.NodeUtils;

import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_GET_INFO;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_GET_NUMBER;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_SELECT_BRAND;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_SELECT_CAR_MODEL;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_SELECT_CAR_SERIES;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_SELECT_SOURCE_TYPE;
import static top.shixinzhang.mvpcrawler.mvp.CrawlerContract.Model.MODE_START;

/**
 * Description:
 * <br> 共用的 Presenter
 * <p>
 * <br> Created by shixinzhang on 17/8/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class CommonPresenter extends BasePresenter {
    private final String TAG = this.getClass().getSimpleName();

    public CommonPresenter(@NonNull final CrawlerContract.View view, @NonNull final CrawlerContract.Model model) {
        super(view, model);
    }


    @Override
    public void receiveEvent(final AccessibilityNodeInfo rootNode, final AccessibilityEvent event) {
        if (event == null || TextUtils.isEmpty(event.getClassName())) {
            return;
        }
        String className = event.getClassName().toString();
        if (!getView().isCorePage(className)) {
            return;
        }
        getModel().setCurrentClassName(className);
        getModel().setRootNode(rootNode);
        int mode = getModel().getMode();
        Log.d(TAG, "mode: " + mode);
        switch (mode) {
            case MODE_START:
                prepare(className);
                openBrandList(rootNode, className);
                break;
            case MODE_SELECT_BRAND:   //选择品牌
                iterateBrands(rootNode, className);
                break;
            case MODE_SELECT_CAR_SERIES:   //选择车系
                iterateSeries(rootNode, className);
                break;
            case MODE_SELECT_SOURCE_TYPE:   //选择来源
                selectSourceType(rootNode, className);
                break;
            case MODE_SELECT_CAR_MODEL:   //选择车款
                iterateModels(rootNode, className);
                break;
            case MODE_GET_INFO:   //详情页
                getInfo(rootNode, className);
                break;
            case MODE_GET_NUMBER:   //电话
                getNumber(rootNode, className);
                break;
        }

    }

    @Override
    public void prepare(final String className) {

    }

    @Override
    public void startApp() {
        ApplicationUtils.startApplication(DataCrawlerService.mContext, getView().getAppPackageName());
    }

    /**
     * 打开品牌列表页面
     *
     * @param rootNode
     * @param className
     */
    @Override
    public void openBrandList(@NonNull final AccessibilityNodeInfo rootNode, final String className) {
        if (!getView().isMainTab(className)) {
            return;
        }
        if (getView().openBrandList(rootNode)) {
            getModel().setMode(CrawlerContract.Model.MODE_SELECT_BRAND);
            iterateBrands(rootNode, className);
        }
    }

    @Override
    public void iterateBrands(final AccessibilityNodeInfo rootNode, final String className) {
        if (!getView().isBrandListPage(rootNode, className)) {
            Log.e(TAG, "不是品牌列表，" + className);
            return;
        }

        AccessibilityNodeInfo listNode = getView().getBrandListNode(rootNode);
        if (listNode != null) {
            int recyclerViewCount = listNode.getChildCount();
            int checkIndex = 1; //从第二个开始是品牌
            AccessibilityNodeInfo child;
            String carBrandName;
            AccessibilityNodeInfo brandNode;
            for (; checkIndex < recyclerViewCount; checkIndex++) {
                child = listNode.getChild(checkIndex);
                if (child == null || child.getChildCount() == 0) {
                    Log.d(TAG, "没找到第 " + checkIndex + " 个品牌");
                    continue;
                }
                try {
                    brandNode = getView().getBrandNodeFromItem(child);
                    carBrandName = brandNode.getText().toString();

                    if (!TextUtils.isEmpty(carBrandName) && !getModel().getClickedBrands().contains(carBrandName)) {
                        Log.d(TAG, "进入品牌 " + carBrandName);
                        getModel().addClickedBrands(carBrandName);
                        getModel().setMode(MODE_SELECT_CAR_SERIES);
                        NodeUtils.clickNode(brandNode);
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
//                    Log.e(TAG, e.getMessage());
                }
            }

            if (checkIndex >= recyclerViewCount) {      //下滑
                swipeUp();
//                saveNumber();
            }
        } else {
            Log.d(TAG, "没找到品牌 ListView 节点");
        }
    }


    @Override
    public void iterateSeries(final AccessibilityNodeInfo rootNode, final String className) {
        if (!getView().isSeriesPage(className)) {
            Log.e(TAG, "不是车系列表，" + className);
            return;
        }

        if (getView().needExitSeriesList(rootNode)) {
            getModel().setMode(MODE_SELECT_BRAND);
            clickBack();
            return;
        }

        AccessibilityNodeInfo listNode = getView().getSeriesListNode(rootNode);
        if (listNode != null) {
            int recyclerViewCount = listNode.getChildCount();
            int checkIndex = 0;
            AccessibilityNodeInfo child;
            String carSeriesName = null;
            int clickSeriesIndex;   //点击哪个车系
            AccessibilityNodeInfo seriesNode;
            for (; checkIndex < recyclerViewCount; checkIndex++) {
                child = listNode.getChild(checkIndex);
                if (child == null) {
                    Log.d(TAG, "车系列表，没找到第 " + checkIndex + " 个节点");
                    continue;
                }

                try {
                    seriesNode = getView().getSeriesNodeFromItem(child);
                    carSeriesName = seriesNode.getText().toString();
                    if (!TextUtils.isEmpty(carSeriesName) && !getModel().getClickedSeries().contains(carSeriesName)) {
                        Log.d(TAG, "选了车系：" + carSeriesName);
                        getModel().addClickedSeries(carSeriesName);
//                        mCurrentSeriesName = carSeriesName;
                        getModel().setMode(MODE_SELECT_SOURCE_TYPE);
                        NodeUtils.clickNode(seriesNode);
                        return;
                    }
                    // TODO: 17/8/8 如何判断遍历结束
                } catch (Exception e) {
                    e.printStackTrace();
//                    Log.e(TAG, e.getMessage());
                }
            }
            if (checkIndex >= recyclerViewCount) {
                if (!TextUtils.isEmpty(carSeriesName) && carSeriesName.equals(getModel().getLastSeriesName())) {
                    getModel().setMode(MODE_SELECT_BRAND);
                    clickBack();
                } else {
                    getModel().setLastSeriesName(carSeriesName);
                    swipeUp();
                }
//                swipeUp();
//                getModel().setMode(MODE_SELECT_CAR_SERIES);
//                saveNumber();
            }

        } else {
            Log.d(TAG, "没找到车系 ListView 节点");
        }
    }

    @Override
    public void selectSourceType(final AccessibilityNodeInfo rootNode, final String className) {
        if (getView().isModelsPage(className)) {
            getModel().setMode(MODE_SELECT_CAR_MODEL);
            return;
        }
        if (!getView().isSourceTypePage(className)) {
            Log.e(TAG, "不是来源页，" + className);
            getModel().setMode(MODE_SELECT_CAR_MODEL);
        }


    }

    @Override
    public void iterateModels(final AccessibilityNodeInfo rootNode, final String className) {
        if (!getView().isModelsPage(className)) {
            Log.e(TAG, "不是SKU列表，" + className);
            return;
        }

        if (getView().needExitModelList(rootNode)) {
            getModel().setMode(MODE_SELECT_CAR_SERIES);
            clickBack();
            return;
        }

        SystemClock.sleep(100);

        AccessibilityNodeInfo listNode = getView().getModelListNode(rootNode);
        if (listNode != null) {
            int recyclerViewCount = listNode.getChildCount();
            int checkIndex = 0;
            AccessibilityNodeInfo child;
            String modelIdentityStr = null;  //用来辨识是否点击过该车款的唯一标识

            for (; checkIndex < recyclerViewCount; checkIndex++) {
                child = listNode.getChild(checkIndex);
                try {
                    modelIdentityStr = getView().getModelIdentity(child);
                    if (!TextUtils.isEmpty(modelIdentityStr) && !getModel().getClickedModels().contains(modelIdentityStr)) {
                        getModel().addClickedModel(modelIdentityStr);
                        getModel().setMode(MODE_GET_INFO);
                        NodeUtils.clickNode(child);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (checkIndex >= recyclerViewCount) {
                getModel().saveInfo();

                //这次遍历的最后一个和上次的最后一个一样，即到达最低端
                if (!TextUtils.isEmpty(modelIdentityStr) && modelIdentityStr.equals(getModel().getLastModelName())) {
                    getModel().setMode(MODE_SELECT_CAR_SERIES);
                    clickBack();
                } else {
                    getModel().setLastModelName(modelIdentityStr);
                    swipeUp();
                }
            }


        } else {
            Log.d(TAG, "没找到车款列表的 RecyclerView 节点");
        }

    }

    @Override
    public void getInfo(final AccessibilityNodeInfo rootNode, final String className) {
        if (!getView().isDetailPage(className)) {
            Log.e(TAG, "不是详情页，" + className);
            return;
        }

        SupplierInfoBean supplierInfoBean = null;
        try {
            supplierInfoBean = getView().getInfo(rootNode);
            if (supplierInfoBean == null || TextUtils.isEmpty(supplierInfoBean.getPublishInfo())
                    || "nullnull".equals(supplierInfoBean.getPublishInfo())) {
                return;
            }
            Map<String, String> phoneMap = getModel().getPhoneMap();
            if (phoneMap.containsKey(supplierInfoBean.getPublishInfo()) &&
                    !"1".equals(phoneMap.get(supplierInfoBean.getPublishInfo()))) {  //点过了
                getModel().setMode(MODE_SELECT_CAR_MODEL);
                clickBack();
                return;
            }

            getModel().setCurrentSupplier(supplierInfoBean);        //保存当前的信息，为了下一步继续使用


            phoneMap.put(supplierInfoBean.getPublishInfo(), "1");
        } catch (Exception e) {
            e.printStackTrace();
            getModel().setMode(MODE_SELECT_CAR_MODEL);
            clickBack();
        }

        if (supplierInfoBean != null) {
            if (TextUtils.isEmpty(supplierInfoBean.getPhone())) {        //没拿到电话数据
                if (getView().openNumberPage(rootNode)) {   //打开电话页面
                    getModel().setMode(MODE_GET_NUMBER);
                }
            }
        }

    }

    @Override
    public void getNumber(final AccessibilityNodeInfo rootNode, final String className) {
        if (!getView().isNumberPage(className)) {
            Log.e(TAG, "不是电话弹出框，" + className);
            return;
        }

        try {
            SupplierInfoBean supplierInfoBean = getView().getNumberInfo(rootNode);
            if (supplierInfoBean != null) {
                getModel().getCurrentSupplier().clone(supplierInfoBean);
                getModel().addSupplier(getModel().getCurrentSupplier());
                getModel().getPhoneMap().put(getModel().getCurrentSupplier().getPublishInfo(),
                        getModel().getCurrentSupplier().getPhone());
                getModel().setMode(MODE_GET_INFO);
                clickBack();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "获取电话信息失败");
        }

    }


}
