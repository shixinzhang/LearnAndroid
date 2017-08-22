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
import android.view.accessibility.AccessibilityNodeInfo;

import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;

/**
 * Description:
 * <br> 跳板网的逻辑和其他不一样，需要单独写一个控制器
 * 单独写成本又太大，不如继承以后修改部分逻辑，能共用就共用
 * <p>
 * <br> Created by shixinzhang on 17/8/22.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class TiaobanPresenter extends CommonPresenter {
    private final String TAG = this.getClass().getSimpleName();

    public TiaobanPresenter(@NonNull final CrawlerContract.View view, @NonNull final CrawlerContract.Model model) {
        super(view, model);
    }

    /**
     * 修改逻辑
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
            getModel().setMode(CrawlerContract.Model.MODE_SELECT_CAR_MODEL);    //直接进入详情
            iterateBrands(rootNode, className);
        }
    }

}
