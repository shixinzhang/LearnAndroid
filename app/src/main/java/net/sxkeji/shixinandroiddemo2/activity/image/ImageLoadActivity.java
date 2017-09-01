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

package net.sxkeji.shixinandroiddemo2.activity.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import net.sxkeji.shixinandroiddemo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.shixinzhang.sxframework.common.base.BaseActivity;
import top.shixinzhang.sxframework.imageload.glide.Glide;

/**
 * Description:
 * <br> 测试图片加载库 Glide 和 Picasso
 * <p>
 * <br> Created by shixinzhang on 17/9/1.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class ImageLoadActivity extends BaseActivity {

    @BindView(R.id.iv_picasso)
    ImageView mIvPicasso;
    @BindView(R.id.iv_glide)
    ImageView mIvGlide;

    private final String IMAGE_URL = "http://img.daimg.com/uploads/allimg/170719/1-1FG9231920.jpg";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        ButterKnife.bind(this);

        Glide.with(this)
                .load(IMAGE_URL)
                .into(mIvGlide);
    }
}
