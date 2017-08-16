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
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import top.shixinzhang.mvpcrawler.helper.Config;
import top.shixinzhang.mvpcrawler.DataCrawlerService;
import top.shixinzhang.mvpcrawler.entity.SupplierInfoBean;
import top.shixinzhang.mvpcrawler.mvp.CrawlerContract;
import top.shixinzhang.utils.DateUtils;
import top.shixinzhang.utils.FileUtils;

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
    private final String TAG = this.getClass().getSimpleName();

    @EventMode
    private volatile int mMode = MODE_START;
    private int mLastSavePhoneSize; //上次保存时的电话数量
    private SupplierInfoBean mCurrentSupplier;
    private String mFileTimeStamp = DateUtils.getMMddhhmmss(System.currentTimeMillis());

    private Set<String> mClickedBrandsNameSet = new HashSet<>();
    private Set<String> mClickedSeriesNameSet = new HashSet<>();
    private Set<String> mClickedModelNameSet = new HashSet<>();
    private Set<SupplierInfoBean> mSupplierInfoSet = new HashSet<>();
    private Map<String, String> mPhoneMap = new HashMap<>();
    private String mLastIterateModelName;
    private String mLastIterateSeriesName;
    private String mCurrentClassName;
    private AccessibilityNodeInfo mRootNode;

    public static CrawlerModel create() {
        return new CrawlerModel();
    }

    @Override
    public int getLastSavePhoneSize() {
        return mLastSavePhoneSize;
    }

    @Override
    public void setLastSavePhoneSize(final int lastSavePhoneSize) {
        mLastSavePhoneSize = lastSavePhoneSize;
    }

    @NonNull
    @Override
    public Set<String> getClickedBrands() {
        return mClickedBrandsNameSet;
    }

    @NonNull
    @Override
    public Set<String> getClickedSeries() {
        return mClickedSeriesNameSet;
    }

    @NonNull
    @Override
    public Set<String> getClickedSourceTypes() {
        return null;
    }

    @NonNull
    @Override
    public Set<String> getClickedModels() {
        return mClickedModelNameSet;
    }

    @Nullable
    @Override
    public String getCurrentClassName() {
        return mCurrentClassName;
    }

    @Override
    public void setCurrentClassName(@NonNull final String currentClassName) {
        mCurrentClassName = currentClassName;
    }

    @Override
    public void setCurrentSupplier(@NonNull final SupplierInfoBean supplier) {
        mCurrentSupplier = supplier;
    }

    public AccessibilityNodeInfo getRootNode() {
        return mRootNode;
    }

    public void setRootNode(final AccessibilityNodeInfo rootNode) {
        mRootNode = rootNode;
    }

    @Override
    public SupplierInfoBean getCurrentSupplier() {
        return mCurrentSupplier;
    }

    @Override
    public Observable<SupplierInfoBean> updateCurrentSupplierInfo(final SupplierInfoBean currentSupplier) {
        return null;
    }

    @Override
    public void addSupplier(final SupplierInfoBean supplierInfoBean) {
        getSupplierInfoSet().add(supplierInfoBean);
    }

    @NonNull
    @Override
    public Set<SupplierInfoBean> getSupplierInfoSet() {
        return mSupplierInfoSet;
    }

    @Override
    public void setSupplierInfoSet(@NonNull final Set<SupplierInfoBean> supplierInfoSet) {
        mSupplierInfoSet = supplierInfoSet;
    }

    @Override
    public void addClickedBrands(@NonNull final String carBrandName) {
        getClickedBrands().add(carBrandName);
    }

    @Override
    public void addClickedSeries(@NonNull final String carSeriesName) {
        getClickedSeries().add(carSeriesName);
    }

    @Override
    public void addClickedModel(final String modelIdentityStr) {
        getClickedModels().add(modelIdentityStr);
    }

    @Override
    public void setLastModelName(final String modelIdentityStr) {
        mLastIterateModelName = modelIdentityStr;
    }

    @Override
    public String getLastModelName() {
        return mLastIterateModelName;
    }

    @Override
    public String getLastSeriesName() {
        return mLastIterateSeriesName;
    }

    @Override
    public void setLastSeriesName(final String carSeriesName) {
        mLastIterateSeriesName = carSeriesName;
    }

    @EventMode
    @Override
    public int getMode() {
        return mMode;
    }

    @Override
    public synchronized void setMode(@EventMode final int mode) {
        mMode = mode;
    }

    @Override
    public void saveInfo() {
        Collection<String> values = getPhoneMap().values();
        final HashSet<String> phoneSet = new HashSet<>(values);

        if (getLastSavePhoneSize() != phoneSet.size()) {
            Log.d(TAG, phoneSet.size() + " , " + phoneSet.toString());
            setLastSavePhoneSize(phoneSet.size());
            final String path = String.format("%s%s_%s_%s.txt", getDirectoryPath(), "去重", mFileTimeStamp, "number");
            final String fullInfoPath = String.format("%s%s_%s_%s.txt", getDirectoryPath(), "未去重", mFileTimeStamp, "完整信息");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileUtils.writeFile(path, phoneSet.size() + "\n" + phoneSet.toString(), false);
                    //数据格式为 JSON
                    FileUtils.writeFile(fullInfoPath, getSupplierInfoSet().size() + "\n" + getJsonResult(getSupplierInfoSet()), false);
                }
            }).start();


        }

    }

    private String getJsonResult(final Set<SupplierInfoBean> supplierInfoSet) {
        if (supplierInfoSet == null) {
            return "";
        }
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        for (SupplierInfoBean supplierInfoBean : supplierInfoSet) {
            jsonObject = new JSONObject();
            try {
                jsonObject.put("phone", supplierInfoBean.getPhone());
                jsonObject.put("name", supplierInfoBean.getName());
                jsonObject.put("company", supplierInfoBean.getCompany());
                jsonObject.put("publishInfo", supplierInfoBean.getPublishInfo());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return jsonArray.toString();
    }

    @NonNull
    @Override
    public Map<String, String> getPhoneMap() {
        return mPhoneMap;
    }

    public String getDirectoryPath() {
        return Config.EXT_DIR + File.separator + DataCrawlerService.getCurrentApp() + File.separator;
    }
}
