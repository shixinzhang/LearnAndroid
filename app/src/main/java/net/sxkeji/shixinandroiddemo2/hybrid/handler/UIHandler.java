package net.sxkeji.shixinandroiddemo2.hybrid.handler;

import android.text.TextUtils;
import android.widget.Toast;

import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridEvent;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridHandler;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridHandlerResult;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridMethodHandler;

import java.util.HashMap;

/**
 * <br/> Description: H5 触发原生组件中的一种：UI 操作
 * <p>
 * <br/> Created by shixinzhang on 16/12/24.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

@HybridHandler(scheme = "shixinzhang", type = "ui")
public class UIHandler {

    @HybridMethodHandler(path = "/toast")
    public HybridHandlerResult showToast(HybridEvent hybridEvent) {
        if (hybridEvent != null && hybridEvent.getEventContext() != null && hybridEvent.getEventData() != null) {
            HashMap dataMap = hybridEvent.getEventData().getData();
            String message = (String) dataMap.get("message");
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(hybridEvent.getEventContext(), message, Toast.LENGTH_SHORT).show();
                return HybridHandlerResult.HANDLE_DONE;
            }
        }
        return HybridHandlerResult.HANDLE_NOT;

    }
}
