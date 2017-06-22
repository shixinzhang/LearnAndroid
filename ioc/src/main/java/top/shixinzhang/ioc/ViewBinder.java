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

package top.shixinzhang.ioc;

import android.app.Activity;

/**
 * Description:
 * <br>
 * <p>
 * <br> Created by shixinzhang on 17/6/22.
 * <p>
 * <br> Email: shixinzhang2016@gmail.com
 * <p>
 * <br> https://about.me/shixinzhang
 */

public class ViewBinder {
    private static final String SUFFIX = "$$ViewInjector";

    public static void bind(Activity activity) {
        bind(activity, activity);
    }

    /**
     * 1.寻找对应的代理类
     * 2.调用接口提供的绑定方法
     *
     * @param host
     * @param root
     */
    @SuppressWarnings("unchecked")
    private static void bind(final Object host, final Object root) {
        if (host == null || root == null) {
            return;
        }

        Class<?> aClass = host.getClass();
        String proxyClassFullName = aClass.getName() + SUFFIX;

        try {
            Class<?> proxyClass = Class.forName(proxyClassFullName);
            ViewInjector viewInjector = (ViewInjector) proxyClass.newInstance();
            if (viewInjector != null) {
                viewInjector.inject(host, root);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
