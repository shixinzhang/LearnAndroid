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
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Arrays;
import java.util.List;

import top.shixinzhang.mvpcrawler.Config;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.mvpcrawler.utils.NodeUtil;

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

    /**
     * 进入品牌列表页面
     *
     * @param rootNode
     * @return
     */
    @Override
    public boolean enterBrandList(@NonNull final AccessibilityNodeInfo rootNode) {
        boolean result = NodeUtil.actionText(rootNode, "车源/寻车");
        // TODO: 17/8/7 滑动一下 ?
        return result;
    }

    @Override
    public AccessibilityNodeInfo getBrandListNode(final AccessibilityNodeInfo rootNode) {
        return NodeUtil.findNode(rootNode, "com.maihaoche.bentley:id/search_list_brand");
    }

    @Override
    public AccessibilityNodeInfo getBrandNodeFromItem(@NonNull final AccessibilityNodeInfo itemNode) throws Exception{
        return itemNode.getChild(0).getChild(1);
    }
}
