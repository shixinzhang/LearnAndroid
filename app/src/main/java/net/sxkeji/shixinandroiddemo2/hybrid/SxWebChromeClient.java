package net.sxkeji.shixinandroiddemo2.hybrid;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * <br/> Description: WebView 加载 H5 过程中的 UI 状态监听，比如进度、提示等
 * <p>
 * <br/> Created by shixinzhang on 16/12/23.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxWebChromeClient extends WebChromeClient {
    private final String TAG = this.getClass().getSimpleName();
    private SxWebViewProxy.OnWebViewUIChangedListener mWebViewUIChangedListener;

    public SxWebChromeClient(){

    }

    public SxWebViewProxy.OnWebViewUIChangedListener getWebViewUIChangedListener() {
        return mWebViewUIChangedListener;
    }

    public void setWebViewUIChangedListener(SxWebViewProxy.OnWebViewUIChangedListener webViewUIChangedListener) {
        mWebViewUIChangedListener = webViewUIChangedListener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        showDebugLog("onProgressChanged ：" + newProgress * 1000);
        if (mWebViewUIChangedListener != null){
            mWebViewUIChangedListener.onProgressChanged(newProgress * 1000);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        showDebugLog("onReceivedTitle : " + title);
        if (mWebViewUIChangedListener != null){
            mWebViewUIChangedListener.onTitleChanged(title);
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        showDebugLog("onJsAlert:" + message);
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        showDebugLog("onJsConfirm:" + message);
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        showDebugLog("onJsPrompt:" + message);
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    /**
     * 支持全屏
     * @param view
     * @param callback
     */
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
    }

    /**
     * 关闭全屏
     */
    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
        showDebugLog("onCloseWindow");
    }

    private void showDebugLog(String msg) {
        if (TextUtils.isEmpty(msg)){
            return;
        }
        Log.e(TAG, msg);
    }
}
