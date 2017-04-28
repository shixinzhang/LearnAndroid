package net.sxkeji.shixinandroiddemo2.hybrid;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import net.sxkeji.shixinandroiddemo2.BuildConfig;
import net.sxkeji.shixinandroiddemo2.R;

/**
 * <br/> Description:
 * WebView 的容器 Fragment，负责管理 WebView 的：
 * 1.加载（加载动画、加载进度、加载内容）、
 * 2.拦截（某些页面需要跳转到 Native） ----  由 H5 主动触发逻辑，不管是跳转还是显示原生组件，弹个对话框啥的，都是一套机制
 * 3.错误监听（ 404，网络错误...）、
 * 4.替换为离线 H5 （加强体验）、
 * <p>
 * <p>
 * 待做：
 * CSS,JS 资源预加载
 * <p>
 * <br/> Created by shixinzhang on 16/12/23.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxWebViewFragment extends Fragment implements SxWebViewProxy.OnUrlReplaceListener {
    private final String TAG = this.getClass().getSimpleName();
    public static final String URL = "link_url";

    private SxWebViewProxy mWebViewProxy;
    private SxWebViewProxy.OnWebViewUIChangedListener mWebViewUIChangedListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SxWebViewProxy.OnWebViewUIChangedListener) {
            mWebViewUIChangedListener = (SxWebViewProxy.OnWebViewUIChangedListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_webview, null);
        addWebView(view);
        initWebViewSetting();
        loadUrl();
        return view;
    }

    private void loadUrl() {
        String linkUrl = getArguments().getString(URL);
        if (mWebViewProxy != null && !TextUtils.isEmpty(linkUrl)) {
            mWebViewProxy.loadUrl(linkUrl);
        } else {
            showDebugLog("load url failed!");
        }
    }

    /**
     * 外部调用，直接加载 url
     * @param url
     */
    public void loadUrl(String url){
        mWebViewProxy.loadUrl(url);
    }


    @SuppressWarnings("setJavaScriptEnabled")
    private void initWebViewSetting() {
        if (mWebViewProxy == null) {
            return;
        }
        WebSettings webSettings = mWebViewProxy.getSettings();
        webSettings.setJavaScriptEnabled(true);    //允许使用 JS

        boolean isDebug = BuildConfig.DEBUG;

        if (isDebug && Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

//        webSettings.setAllowFileAccess(false);
        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath("path");//设置当前Application缓存文件路径，Application Cache API能够开启需要指定Application具备写入权限的路径
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setSupportZoom(false);

        webSettings.setUserAgentString("your user agent");

        if (Build.VERSION.SDK_INT >= 21) {
            //设置当一个安全站点企图加载来自一个不安全站点资源时WebView的行为，
            // android.os.Build.VERSION_CODES.KITKAT 默认为 MIXED_CONTENT_ALWAYS_ALLOW，
            // android.os.Build.VERSION_CODES#LOLLIPOP 默认为 MIXED_CONTENT_NEVER_ALLOW
            // 取值其中之一：
            //  MIXED_CONTENT_NEVER_ALLOW、
            //  MIXED_CONTENT_ALWAYS_ALLOW、
            //  MIXED_CONTENT_COMPATIBILITY_MODE.
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        CookieSyncManager.createInstance(getContext());
        CookieManager.setAcceptFileSchemeCookies(true);
        CookieManager.getInstance().setAcceptCookie(true);
    }

    private void addWebView(FrameLayout view) {
        if (view != null) {
            mWebViewProxy = new SxWebViewProxy(getContext());
            mWebViewProxy.setUrlReplaceListener(this);
            SxWebChromeClient webChromeClient = new SxWebChromeClient();
            webChromeClient.setWebViewUIChangedListener(mWebViewUIChangedListener);
            mWebViewProxy.setWebChromeClient(webChromeClient);

            mWebViewProxy.setWebViewClient(new SxWebViewClient(mWebViewProxy));
            try {
                view.addView(mWebViewProxy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public SxWebViewProxy getWebView() {
        return mWebViewProxy;
    }

    public boolean canGoBack() {
        if (mWebViewProxy != null && mWebViewProxy.canGoBack()) {
            return true;
        }
        return false;
    }

    public void goBack() {
        if (mWebViewProxy != null) {
            mWebViewProxy.goBack();
        }
    }

    /**
     * 是否替换为离线 URL
     *
     * @param url
     * @return
     */
    @Override
    public String onUrlReplace(String url) {
        return url;
    }

    private void showDebugLog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(TAG, msg);
    }


}
