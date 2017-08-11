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

package top.shixinzhang.mvpcrawler.mvp.model;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Description:
 * <br> 页面状态信息
 * <p>
 * <br> Created by shixinzhang on 17/8/11.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class PageStateModel {
    private int mode;
    private String className;
    private AccessibilityNodeInfo rootNode;

    public int getMode() {
        return mode;
    }

    public PageStateModel setMode(final int mode) {
        this.mode = mode;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public PageStateModel setClassName(final String className) {
        this.className = className;
        return this;
    }

    public AccessibilityNodeInfo getRootNode() {
        return rootNode;
    }

    public PageStateModel setRootNode(final AccessibilityNodeInfo rootNode) {
        this.rootNode = rootNode;
        return this;
    }
}
