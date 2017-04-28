package net.sxkeji.shixinandroiddemo2.hybrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.Map;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/23.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxWebViewProxy extends WebView {

    /**
     * URL 替换监听器
     */
    public interface OnUrlReplaceListener {
        String onUrlReplace(String url);
    }

    /**
     * WebView UI 变化监听器
     */
    public interface OnWebViewUIChangedListener{
        void onTitleChanged(String title);
        void onProgressChanged(int progress);
    }

    private OnUrlReplaceListener mUrlReplaceListener;

    public SxWebViewProxy(Context context) {
        super(context);
        initView();
    }

    public SxWebViewProxy(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SxWebViewProxy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    }

    @Override
    public void loadUrl(String url) {
        if (mUrlReplaceListener != null) {
            url = mUrlReplaceListener.onUrlReplace(url);
        }
        super.loadUrl(url);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        if (mUrlReplaceListener != null) {
            url = mUrlReplaceListener.onUrlReplace(url);
        }
        super.loadUrl(url, additionalHttpHeaders);
    }

    public OnUrlReplaceListener getUrlReplaceListener() {
        return mUrlReplaceListener;
    }

    public void setUrlReplaceListener(OnUrlReplaceListener urlReplaceListener) {
        mUrlReplaceListener = urlReplaceListener;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (canGoBack()){
                goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
