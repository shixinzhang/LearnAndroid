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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

import io.reactivex.Observable;
import top.shixinzhang.mvpcrawler.entity.SupplierInfoBean;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;

/**
 * Description:
 * <br> 数据操作，保存各种数据，常量尽量别保持在这里，这样就持有了强引用，可以定义在接口负责另外一个配置类
 * <p>
 * <br> Created by shixinzhang on 17/8/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class CrawlerModel implements CrawlerContract.Model {

    private int mMode = MODE_START;

    public static CrawlerModel create() {
        return new CrawlerModel();
    }

    @NonNull
    @Override
    public Set<String> getClickedBrands() {
        return null;
    }

    @NonNull
    @Override
    public Set<String> getClickedSeries() {
        return null;
    }

    @NonNull
    @Override
    public Set<String> getClickedSourceTypes() {
        return null;
    }

    @NonNull
    @Override
    public Set<String> getClickedModels() {
        return null;
    }

    @Nullable
    @Override
    public String getCurrentCarName() {
        return null;
    }

    @Override
    public void setCurrentCarName() {

    }

    @Override
    public Observable<SupplierInfoBean> updateCurrentSupplierInfo(final SupplierInfoBean currentSupplier) {
        return null;
    }

    @Override
    public Set<SupplierInfoBean> addSupplier(final SupplierInfoBean supplierInfoBean) {
        return null;
    }

    @Override
    public int getMode() {
        return mMode;
    }

    @Override
    public void setMode(final int mode) {
        mMode = mode;
    }
}
