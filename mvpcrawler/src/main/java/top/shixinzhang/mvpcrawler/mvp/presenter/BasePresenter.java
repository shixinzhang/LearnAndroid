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

import top.shixinzhang.mvpcrawler.DataCrawlerService;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.utils.ShellUtils;

/**
 * Description:
 * <br> 封装一些 Presenter 共同的操作、属性
 * <p>
 * <br> Created by shixinzhang on 17/8/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public abstract class BasePresenter implements CrawlerContract.Presenter {
    private CrawlerContract.View mView;
    private CrawlerContract.Model mModel;

    public BasePresenter(@NonNull final CrawlerContract.View view, @NonNull final CrawlerContract.Model model) {
        mView = view;
        mModel = model;
    }

    public CrawlerContract.View getView() {
        return mView;
    }

    public BasePresenter setView(final CrawlerContract.View view) {
        mView = view;
        return this;
    }

    public CrawlerContract.Model getModel() {
        return mModel;
    }

    public BasePresenter setModel(final CrawlerContract.Model model) {
        mModel = model;
        return this;
    }

    @Override
    public void onDetachView() {
        setView(null);
    }

    /**
     * 上滑
     */
    protected void swipeUp() {
        ShellUtils.execCmd(DataCrawlerService.sSwipeCmd);
    }

    /**
     * 退出
     */
    protected void clickBack() {
        ShellUtils.execCmd("input keyevent 4");
    }
}
