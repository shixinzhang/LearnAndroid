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

package net.sxkeji.shixinandroiddemo2.hybrid.handler;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridEvent;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridHandler;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridHandlerResult;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridMethodHandler;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * <br/> Description:
 * <p>
 * 做两件事：1.注册 2.调用处理
 * <p>
 * <br/> Created by shixinzhang on 16/12/24.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class HybridFactory {
    private final String TAG = this.getClass().getSimpleName();

    //反射方法的键值对，键 是约定的 scheme，通过约定的 scheme，调用指定的方法
    private HashMap<String, Method> mHandlerMap;
    private static HybridFactory mInstance;

    private HybridFactory() {
        mHandlerMap = new HashMap<>();
    }

    public static HybridFactory getInstance() {
        if (mInstance == null) {
            mInstance = new HybridFactory();
        }
        return mInstance;
    }

    public void clear() {
        if (mHandlerMap != null) {
            mHandlerMap.clear();
        }
    }

    @SafeVarargs
    public final void registerHandlers(Class<BaseHandler>... classes) {
        for (Class<BaseHandler> handlerClass : classes) {
            registerHandler(handlerClass);
        }
    }

    private void registerHandler(@NonNull Class<BaseHandler> clz) {
        HybridHandler annotation = clz.getAnnotation(HybridHandler.class);
        if (annotation == null) {
            return;
        }
        String scheme = annotation.scheme();
        String[] types = annotation.authority();
        String methodPath;
        for (String type : types) {
            Method[] methods = clz.getMethods();
            if (methods == null) {
                break;
            }
            for (Method method : methods) {
                HybridMethodHandler methodAnnotation = method.getAnnotation(HybridMethodHandler.class);
                if (methodAnnotation == null) { //每个类有很多从 Object 继承的方法，没有注解的方法都跳过
                    continue;
                }
                methodPath = String.format("%s://%s%s", scheme, type, methodAnnotation.path());
                Log.d(TAG, "method path : " + methodPath);
                mHandlerMap.put(methodPath, method);
            }
        }
    }

    public HybridHandlerResult handle(String uri, HybridEvent hybridEvent) {
        HybridHandlerResult handlerResult = HybridHandlerResult.HANDLE_NOT;
        if (mHandlerMap != null && !TextUtils.isEmpty(uri)) {
            Method method = mHandlerMap.get(uri);
            if (method != null) {
                Class<?> aClass = method.getDeclaringClass();
                Object newInstance;
                try {
                    newInstance = aClass.newInstance();
                    handlerResult = (HybridHandlerResult)
                            (hybridEvent == null
                                    ? method.invoke(newInstance)
                                    : method.invoke(newInstance, hybridEvent));
                } catch (Exception e) {
                    return HybridHandlerResult.HANDLE_NOT;
                }
            }
        }
        return handlerResult;
    }
}