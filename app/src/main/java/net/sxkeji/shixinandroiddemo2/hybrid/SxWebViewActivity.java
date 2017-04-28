package net.sxkeji.shixinandroiddemo2.hybrid;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 16/12/23.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class SxWebViewActivity extends BaseActivity implements SxWebViewProxy.OnWebViewUIChangedListener {
    @BindView(R.id.load_progress)
    ProgressBar mLoadProgress;

    private SxWebViewFragment mWebViewFragment;
    private final String testUrl = "shixinzhang://ui/toast?params={\"data\":{\"message\":\"hello_hybrid\"}}";
    private final String myCSDNUrl = "http://m.blog.csdn.net/blog/index?username=u011240877";
    private final String myWebsiteUrl = "http://shixinzhang.top";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void initViews() {
        mWebViewFragment = new SxWebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SxWebViewFragment.URL, myCSDNUrl);
        mWebViewFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_webview, mWebViewFragment)
                .commit();
    }

    private void startWebView(String url) {
        mWebViewFragment.loadUrl(url);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void addListeners() {
    }

    @Override
    public void onTitleChanged(String title) {
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
    }

    @Override
    public void onProgressChanged(int progress) {
//        mLoadProgress.setProgress(progress);
//        if (progress > 0 && progress < mLoadProgress.getMax() && mLoadProgress.getVisibility() != View.VISIBLE) {
//            mLoadProgress.setVisibility(View.VISIBLE);
//        } else{
//            mLoadProgress.setProgress(0);
//            mLoadProgress.setVisibility(View.INVISIBLE);
//        }
        smoothProgressChanged(progress);
    }

    /**
     * 进度条更顺滑
     * @param aimProcess
     */
    public void smoothProgressChanged(final int aimProcess){
        final int currentProgress = mLoadProgress.getProgress();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentProgress, aimProcess);
        valueAnimator.setInterpolator(new DecelerateInterpolator(1));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                if (animatedFraction < 1.0) {
                    mLoadProgress.setProgress((int) (currentProgress + animatedFraction * (aimProcess - currentProgress)));
                }else {
                    mLoadProgress.setProgress(0);
                }
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_my_website:
                startWebView(testUrl);
                break;
        }
        return true;
    }

    //
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            if (mWebViewFragment != null && mWebViewFragment.canGoBack()){
//                mWebViewFragment.canGoBack();
//            }else {
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
