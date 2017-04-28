package net.sxkeji.shixinandroiddemo2.weex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexAnimationFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexBoxFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexImageFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexListFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexModalFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexPickerFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexRefreshLoadMoreFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexScrollerFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexSliderFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexStreamFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexSwitchFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexTextareaFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexVideoFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexWebSocketFragment;
import net.sxkeji.shixinandroiddemo2.weex.fragment.WeexWebViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <br/> Description:
 * 生成 js 文件：weex compile dir_path\*.we out_dir_path -w
 * <p>
 * <br/> Created by shixinzhang on 17/3/23.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class WeexActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(android.R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weex1);
        ButterKnife.bind(this);

        initViews();
    }

    @Override
    public void initViews() {

        setSupportActionBar(mToolBar);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 15;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                    default:
                        return new WeexImageFragment();
                    case 1:
                        return new WeexBoxFragment();
                    case 2:
                        return new WeexSliderFragment();
                    case 3:
                        return new WeexSwitchFragment();
                    case 4:
                        return new WeexTextareaFragment();
                    case 5:
                        return new WeexVideoFragment();
                    case 6:
                        return new WeexWebViewFragment();
                    case 7:
                        return new WeexListFragment();
                    case 8:
                        return new WeexRefreshLoadMoreFragment();
                    case 9:
                        return new WeexScrollerFragment();
                    case 10:
                        return new WeexAnimationFragment();
                    case 11:
                        return new WeexPickerFragment();
                    case 12:
                        return new WeexWebSocketFragment();
                    case 13:
                        return new WeexModalFragment();
                    case 14:
                        return new WeexStreamFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                    default:
                        return getString(R.string.title_image);
                    case 1:
                        return getString(R.string.title_simple);
                    case 2:
                        return getString(R.string.title_slider);
                    case 3:
                        return getString(R.string.title_switch);
                    case 4:
                        return getString(R.string.title_textarea);
                    case 5:
                        return getString(R.string.title_video);
                    case 6:
                        return getString(R.string.title_webview);
                    case 7:
                        return getString(R.string.title_list);
                    case 8:
                        return getString(R.string.title_load_more);
                    case 9:
                        return getString(R.string.title_scroller);
                    case 10:
                        return getString(R.string.title_animation);
                    case 11:
                        return getString(R.string.title_picker);
                    case 12:
                        return getString(R.string.title_websocket);
                    case 13:
                        return getString(R.string.title_modal);
                    case 14:
                        return getString(R.string.title_stream);
                }
            }
        });
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void addListeners() {

    }
}
