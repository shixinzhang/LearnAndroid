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

package top.shixinzhang.hook_sample.dynamicload;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;

import top.shixinzhang.hook_sample.R;
import top.shixinzhang.utils.reflect.ReflectUtils;

/**
 * Description:
 * <br> 动态布局 的容器，当
 * <p>
 * 学习自： http://www.jianshu.com/p/195eb1d8d0de
 * <p>
 * <br> Created by shixinzhang on 17/8/29.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class DynamicViewGroup extends RelativeLayout implements IDynamicView {
    private String mViewUUID;   //标识当前 View 的唯一 ID

    public DynamicViewGroup(final Context context) {
        super(context);
        init();
    }

    public DynamicViewGroup(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DynamicViewGroup, 0, 0);
        mViewUUID = attributes.getString(R.styleable.DynamicViewGroup_uuid);
        attributes.recycle();

        init();
    }

    private void init() {

    }


    @Override
    public void updateView() {

    }

    @Override
    public void replaceResource(@NonNull String path) {
        // TODO: 17/8/29 这里的 context 是谁 ？ AppCompatActivity？
        Context context = getContext();

        Resources resources = context.getResources();
//        AssetManager assetManager = resources.getAssets();
        try {
            AssetManager newAssetManagerObj = AssetManager.class.newInstance();
            Method addAssetPathMethod = newAssetManagerObj.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(newAssetManagerObj, path);
            Resources newResource = new Resources(newAssetManagerObj, resources.getDisplayMetrics(), resources.getConfiguration());
            //替换
            ReflectUtils.onObject(context).set("mResource", newResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
