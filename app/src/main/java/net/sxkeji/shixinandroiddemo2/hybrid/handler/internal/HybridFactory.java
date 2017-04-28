package net.sxkeji.shixinandroiddemo2.hybrid.handler.internal;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * <br/> Description:
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

    public void registerHandlers(Class... classes) {
        for (Class handlerClass : classes) {
            registerHandler(handlerClass);
        }
    }

    public void registerHandler(Class clz) {
        HybridHandler annotation = (HybridHandler) clz.getAnnotation(HybridHandler.class);
        if (annotation == null) {
            return;
        }
        String scheme = annotation.scheme();
        String[] types = annotation.type();
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