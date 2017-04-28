package net.sxkeji.shixinandroiddemo2.hybrid;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridFactory;
import net.sxkeji.shixinandroiddemo2.hybrid.handler.internal.HybridHandlerResult;

/**
 * <br/> Description: WebViewClient，加载 H5 需要的处理器，处理页面跳转
 * <p>
 * <br/> Created by shixinzhang on 16/12/23.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxWebViewClient extends WebViewClient {
    private final String TAG = this.getClass().getSimpleName();
    private SxWebViewHandler mWebViewHandler;
    private WebView mWebView;

    private SxWebViewClient() {
    }

    public SxWebViewClient(WebView webView) {
        this.mWebView = webView;
        mWebViewHandler = new SxWebViewHandler(mWebView.getContext());
    }

    /**
     * 页面加载开始，可以做加载动画
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        showDebugLog("onPageStarted : " + url);
    }

    /**
     * 页面加载结束
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        showDebugLog("onPageFinished : " + url);
    }

    /**
     * HTTPS
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//        super.onReceivedSslError(view, handler, error);
        handler.proceed();  //
    }

    /**
     * 错误处理
     *
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        showDebugLog("onReceivedError : " + request.toString() + " , " + error.toString());
    }

    /**
     * 拦截、跳转的主要实现处
     * 约定 Scheme（router），或者在 Url 中加参数 param=XXXXX，通过解析 param，调用不同的处理器（工厂方式模式）
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        showDebugLog("shouldOverrideUrlLoading : " + url);
        //处理
        HybridHandlerResult handlerResult = mWebViewHandler.handleUrl(url);
        if (handlerResult == HybridHandlerResult.HANDLE_DOING || handlerResult == HybridHandlerResult.HANDLE_DONE){
            return true;    //拦截，不加载
        }

        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        showDebugLog("shouldOverrideKeyEvent : " + event.getKeyCode());

        return super.shouldOverrideKeyEvent(view, event);
    }

    private void showDebugLog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(TAG, msg);
    }
}
