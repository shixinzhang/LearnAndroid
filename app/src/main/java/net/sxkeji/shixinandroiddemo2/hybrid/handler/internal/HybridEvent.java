package net.sxkeji.shixinandroiddemo2.hybrid.handler.internal;

import android.content.Context;

import java.util.HashMap;

/**
 * <br/> Description: 消息事件模型
 * <p>
 * <br/> Created by shixinzhang on 16/12/24.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class HybridEvent {
    private Context mEventContext;
    private HybridEventData mEventData;

    public HybridEvent(Context eventContext, HybridEventData eventData) {
        mEventContext = eventContext;
        mEventData = eventData;
    }

    public HybridEventData getEventData() {
        return mEventData;
    }

    public void setEventData(HybridEventData eventData) {
        mEventData = eventData;
    }

    public Context getEventContext() {
        return mEventContext;
    }

    public void setEventContext(Context eventContext) {
        mEventContext = eventContext;
    }

    public static class HybridEventData{
        // url 中的 param 都是键值对
        private HashMap data;

        public HashMap getData() {
            return data;
        }

        public void setData(HashMap data) {
            this.data = data;
        }
    }
}
