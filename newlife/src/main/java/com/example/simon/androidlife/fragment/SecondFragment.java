package com.example.simon.androidlife.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.simon.androidlife.R;

import top.shixinzhang.sxframework.utils.LogUtils;

/**
 * desc:
 *
 * @author simon
 * @since 24/09/2017.
 */

public class SecondFragment extends Fragment {
    public static String KEY_TITLE = "title";

    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private View mContainerView;
    private TextView mTextView;
    private Button mBtnSwitchChild;
    private FragmentManager mFragmentManager;

    private FirstFragment mFirstChildFragment;
    private SecondFragment mSecondChildFragment;
    private int clickCount;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mContainerView = mLayoutInflater.inflate(R.layout.fragment_second, container, false);
        return mContainerView;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {

        if (view == null) {
            return;
        }
        mTextView = (TextView) view.findViewById(R.id.tv_content);
        mBtnSwitchChild = (Button) view.findViewById(R.id.btn_switch_child);

        Bundle arguments = getArguments();
        if (arguments != null && mTextView != null && !TextUtils.isEmpty(arguments.getString(KEY_TITLE))) {
            mTextView.setText(arguments.getString(KEY_TITLE));
        }
        mBtnSwitchChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mFragmentManager == null){
                    mFragmentManager = getChildFragmentManager();
                }
                mFragmentManager.beginTransaction()
                        .replace(R.id.fl_child, getChildFragment())
                        .commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * 获取子 Fragment
     * @return
     */
    private Fragment getChildFragment() {
        Fragment result;
        if (clickCount++ % 2 == 0){
            if (mFirstChildFragment == null){
                mFirstChildFragment = new FirstFragment();
            }
            result = mFirstChildFragment;
        }else {
            if (mSecondChildFragment == null){
                mSecondChildFragment = new SecondFragment();
            }
            result = mSecondChildFragment;
        }
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d("onHiddenChanged: " + hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLayoutInflater = null;
        mActivity = null;
    }
}
