package com.example.simon.androidlife.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simon.androidlife.R;

import top.shixinzhang.sxframework.utils.LogUtils;

/**
 * desc:
 *
 * @author simon
 * @since 24/09/2017.
 */

public class FirstFragment extends Fragment {
    public static String KEY_TITLE = "title";

    private Activity mActivity;

    private LayoutInflater mLayoutInflater;
    private View mContainerView;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mContainerView = mLayoutInflater.inflate(R.layout.fragment_first, container, false);
        if (mContainerView != null) {
            mTextView = (TextView) mContainerView.findViewById(R.id.tv_content);
        }
        return mContainerView;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle arguments = getArguments();
        if (arguments != null && mTextView != null && !TextUtils.isEmpty(arguments.getString(KEY_TITLE))) {
            mTextView.setText(arguments.getString(KEY_TITLE));
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = context;
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
