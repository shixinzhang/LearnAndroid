package net.sxkeji.shixinandroiddemo2.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import net.sxkeji.shixinandroiddemo2.R;

/**
 * 搜索
 * Created by zhangshixin on 8/30/2016.
 */
public class SearchActivity extends AppCompatActivity {

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
