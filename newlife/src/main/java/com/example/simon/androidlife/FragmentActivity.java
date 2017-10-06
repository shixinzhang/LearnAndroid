package com.example.simon.androidlife;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.simon.androidlife.fragment.FirstFragment;
import com.example.simon.androidlife.fragment.SecondFragment;
import com.example.simon.androidlife.fragment.ThirdFragment;

import java.util.Arrays;

import top.shixinzhang.sxframework.utils.AlertUtils;
import top.shixinzhang.sxframework.utils.DateUtils;
import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseQuickAdapter;

/**
 * desc:
 *
 * @author simon
 * @since 24/09/2017.
 */

public class FragmentActivity extends android.support.v4.app.FragmentActivity {

    private Fragment mFirstFragment, mSecondFragment, mThirdFragment;
    private RecyclerView mRecyclerView;
    private TitleRecyclerAdapter mAdapter;
    private FragmentManager mFragmentManager;

    private String[] mTitleArray = new String[]{"大鹅福利", "陕北羊肉汤", "米饭系列", "手工凉皮", "抗饿小点", "特色开胃粥", "营养套餐"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        initRecyclerView();

        //1.创建
        createFirstFragment(mTitleArray[0]);

        //2.添加
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_content, mFirstFragment);
        fragmentTransaction.commit();
//        fragmentTransaction.commitAllowingStateLoss();    //区别？

    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_title);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TitleRecyclerAdapter(this, Arrays.asList(mTitleArray));
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                switchFragment(i);
                AlertUtils.toastShort(FragmentActivity.this, mTitleArray[i]);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 切换 Fragment
     * @param position
     */
    private void switchFragment(int position) {
        Fragment fragment = null;
        switch (position % 3) {
            case 0:
                if (mFirstFragment == null) {
                    createFirstFragment(mTitleArray[position]);
                }
                fragment = mFirstFragment;
                break;
            case 1:
                if (mSecondFragment == null) {
                    createSecondFragment(mTitleArray[position]);
                }
                fragment = mSecondFragment;
                break;
            default:
            case 2:
                if (mThirdFragment == null) {
                    createThirdFragment(mTitleArray[position]);
                }
                fragment = mThirdFragment;
                break;
        }
        if (fragment != null && !fragment.isAdded()) {
            //3.替换
            mFragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commitAllowingStateLoss();
        }
    }

    private void createThirdFragment(String s) {
        mThirdFragment = new ThirdFragment();
        mThirdFragment.setArguments(getBundle(s));
    }

    private void createSecondFragment(String s) {
        mSecondFragment = new SecondFragment();
        mSecondFragment.setArguments(getBundle(s));
    }

    private void createFirstFragment(@NonNull String s) {
        mFirstFragment = new FirstFragment();
        mFirstFragment.setArguments(getBundle(s));
    }

    private Bundle getBundle(String s) {
        Bundle bundle = new Bundle();
        bundle.putString(FirstFragment.KEY_TITLE, s + "\nCreated at " + DateUtils.getCurrentTime());
        return bundle;
    }
}
