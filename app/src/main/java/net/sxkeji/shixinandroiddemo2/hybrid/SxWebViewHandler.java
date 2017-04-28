package net.sxkeji.shixinandroiddemo2.hybrid;

import android.content.Context;
import android.net.Uri;

import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridEvent;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridFactory;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridHandlerResult;
import net.sxkeji.shixinandroiddemo2.util.GsonUtils;

/**
 * <br/> Description: 总的 WebView 处理类，错误回调，拦截，什么的，都在这里
 * <p>
 * <br/> Created by shixinzhang on 16/12/24.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxWebViewHandler {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private HybridFactory mHybridFactory;

    public SxWebViewHandler(Context context) {
        mContext = context;
        mHybridFactory = HybridFactory.getInstance();
    }

    public HybridHandlerResult handleUrl(String url) {
        return handleUri(Uri.parse(url));
    }

    private HybridHandlerResult handleUri(Uri uri) {
        HybridHandlerResult handlerResult = HybridHandlerResult.HANDLE_NOT;
        if (uri == null || uri.getScheme() == null || uri.getAuthority() == null) {
            return handlerResult;
        }
        //约定的 uri 格式: shixinzhang://ui/toast?params={"data":{"message":"hello_hybrid"}}
        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        String path = uri.getPath();
        String params = uri.getQueryParameter("params");
        String appointedUri = String.format("%s://%s%s", scheme, authority, path);
        HybridEvent.HybridEventData hybridEventData = (HybridEvent.HybridEventData) GsonUtils.jsonToBean(params, HybridEvent.HybridEventData.class);
        HybridEvent hybridEvent = new HybridEvent(mContext, hybridEventData);

        handlerResult = mHybridFactory.handle(appointedUri, hybridEvent);

        return handlerResult;
    }
}
