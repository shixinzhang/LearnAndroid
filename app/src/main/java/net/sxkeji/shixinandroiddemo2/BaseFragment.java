package net.sxkeji.shixinandroiddemo2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/24.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class BaseFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showInfoLog("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showInfoLog("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showInfoLog("onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showInfoLog("onActivityCreated");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        showInfoLog("onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        showInfoLog("onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        showInfoLog("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        showInfoLog("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        showInfoLog("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        showInfoLog("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showInfoLog("onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showInfoLog("onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showInfoLog("onDetach");
    }


    protected void showErrorLog(String msg) {
        showErrorLog(TAG, msg);
    }

    protected void showErrorLog(String tag, String msg) {
        Log.e(tag, msg);
    }


    protected void showInfoLog(String msg) {
        showInfoLog(TAG, msg);
    }

    protected void showInfoLog(String tag, String msg) {
        Log.i(tag, msg);
    }
}
