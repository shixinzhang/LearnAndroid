package net.sxkeji.shixinandroiddemo2.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: Hybrid 练习1,熟悉 Native 与 H5 的基本交互
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 9/19/2016
 */
public class HybridDemo1Activity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.btn_call_js)
    Button mBtnCallJs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid_demo1);
        ButterKnife.bind(this);
        initViews();
        addListeners();
    }

    @Override
    public void initViews() {
        WebSettings settings = mWebView.getSettings();
        //允许 WebView 执行 Js
        settings.setJavaScriptEnabled(true);
        // Js 可以调用 JsBridge 类中的方法，
        // Js 中跳转的 href 为 “javascript:zsx.makeCall(123)”
        // 其中 "zsx" 为我们给在 addJavascriptInterface 方法中起的名字
        // "makeCall(123)" 为我们的 JsBridge 中的方法
        mWebView.addJavascriptInterface(new JsBridge(), "zsx");
        mWebView.loadUrl("file:///android_asset/demo.html");
    }

    @Override
    public void addListeners() {
        mBtnCallJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过 WebView.loadUrl 方法调用 Js 函数
                //参数为 "javascript: $ Js 的 function 名加参数$"
                mWebView.loadUrl("javascript:show('shixinzhang is so cute')");
            }
        });
    }

    @Override
    public void loadData() {

    }

    /**
     * 自定义要被 Js 调用的类
     */
    public class JsBridge {
        /**
         * 在非 UI 线程回调,
         *
         * @param number
         */
        @JavascriptInterface
        public void makeCall(String number) {
            Intent view = new Intent();
            view.setAction(Intent.ACTION_DIAL);
            view.setData(Uri.parse("tel:" + number));
            startActivity(view);
        }
    }
}
