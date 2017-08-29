package net.sxkeji.shixinandroiddemo2.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;

import net.sxkeji.shixinandroiddemo2.R;

import top.shixinzhang.sxframework.common.base.BaseActivity;
import top.shixinzhang.sxframework.statistic.ubt.PageEventInfo;

/**
 * 搜索
 * Created by zhangshixin on 8/30/2016.
 */
@PageEventInfo(pageName = "search", pageId = "search-id")
public class SearchActivity extends BaseActivity {

    private BuyCarTabFragment mBuyCarTabFragment;
    private OnBackPressFragmentListener mOnBackPressFragmentListener;
    private ShixinHandler mHandler;

    private class ShixinHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setPageEventParam("search-param");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        mBuyCarTabFragment = new BuyCarTabFragment();
        mOnBackPressFragmentListener = mBuyCarTabFragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mBuyCarTabFragment).commit();


        @SuppressLint("Typo")
        String logn;    //means login


        logn = "login";

        if (TextUtils.isEmpty(logn)){

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mOnBackPressFragmentListener != null && mOnBackPressFragmentListener.onBackPressed()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回键监听器，用于向 Fragment 传递返回事件
     */
    public interface OnBackPressFragmentListener {
        boolean onBackPressed();
    }
}
