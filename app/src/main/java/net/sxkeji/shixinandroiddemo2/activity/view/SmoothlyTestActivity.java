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

package net.sxkeji.shixinandroiddemo2.activity.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseQuickAdapter;
import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseViewHolder;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/7/7.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class SmoothlyTestActivity extends BaseActivity {
    @BindView(R.id.recycler_normal)
    RecyclerView mRecyclerNormal;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_recycler);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        mRecyclerNormal.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerNormal.setAdapter(new NormalRecyclerAdapter(this, createTestData()));
    }

    private List<String> createTestData() {
        List urlList = new ArrayList(1_000);
        for (int i = 0; i < 1_000; i++) {
            urlList.add("http://img.yaomaiche.com/upload/image/original/201707/011354175470.jpg");
        }
        return urlList;
    }

    @Override
    public void loadData() {

    }

    public class NormalRecyclerAdapter extends BaseQuickAdapter<String>{

        public NormalRecyclerAdapter(final Context context, final List<String> data) {
            super(context, data, R.layout.item_test_recycler);
        }

        @Override
        protected void convert(final BaseViewHolder holder, final String item) {
            holder.setImageUrl(R.id.iv_car, item);
        }
    }
}
