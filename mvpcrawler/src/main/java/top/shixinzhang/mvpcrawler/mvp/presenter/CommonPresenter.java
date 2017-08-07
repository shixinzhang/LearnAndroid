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

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import top.shixinzhang.mvpcrawler.Config;
import top.shixinzhang.mvpcrawler.DataCrawlerService;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.mvpcrawler.utils.NodeUtil;
import top.shixinzhang.mvpcrawler.utils.ShellUtil;

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

public class CommonPresenter extends BasePresenter implements CrawlerContract.Presenter {
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

        int mode = getModel().getMode();
        switch (mode) {
            case CrawlerContract.Model.MODE_START:
                prepare(className);
                openBrandList(rootNode, className);
                break;
            case CrawlerContract.Model.MODE_SELECT_BRAND:   //选择品牌
                iterateBrands(rootNode, className);
                break;
            case CrawlerContract.Model.MODE_SELECT_CAR_SERIES:   //选择车系
                iterateSeries(className);
                break;
            case CrawlerContract.Model.MODE_SELECT_SOURCE_TYPE:   //选择来源
                selectSourceType(className);
                break;
            case CrawlerContract.Model.MODE_SELECT_CAR_MODEL:   //选择车款
                iterateModels(className);
                break;
            case CrawlerContract.Model.MODE_GET_INFO:
                getInfo(className);
                break;
        }

    }

    @Override
    public void prepare(final String className) {

    }

    @Override
    public void startApp() {
        switch (getView().getAppName()) {
            case Config.APP_NAME_SELL_NICE_CAR:

                break;
        }
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
        if (getView().enterBrandList(rootNode)) {
            getModel().setMode(CrawlerContract.Model.MODE_SELECT_BRAND);
        }
    }

    @Override
    public void iterateBrands(final AccessibilityNodeInfo rootNode, final String className) {
        if (!getView().isBrandListPage(className)) {
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
                        NodeUtil.clickNode(brandNode);
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }

            if (checkIndex >= recyclerViewCount) {      //下滑
                ShellUtil.execShellCmd(DataCrawlerService.sSwipeCmd);
//                saveNumber();
            }
        } else {
            Log.d(TAG, "没找到品牌 ListView 节点");
        }
    }

    @Override
    public void iterateSeries(final String className) {

    }

    @Override
    public void selectSourceType(final String className) {

    }

    @Override
    public void iterateModels(final String className) {

    }

    @Override
    public void enterDetail() {

    }

    @Override
    public void getInfo(final String className) {

    }

}
