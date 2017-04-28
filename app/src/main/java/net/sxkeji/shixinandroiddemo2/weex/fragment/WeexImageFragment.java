package net.sxkeji.shixinandroiddemo2.weex.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.weex.WeexBaseFragment;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/24.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class WeexImageFragment extends WeexBaseFragment {

    private FrameLayout mFrameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(getActivity(), R.layout.fragment_weex, null);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.fragment_container);
        mWXSDKInstance = new WXSDKInstance(getActivity());
        mWXSDKInstance.registerRenderListener(this);
        mWXSDKInstance.render("WXSample", WXFileUtils.loadAsset("weex/image.js", getContext()), null, null, WXRenderStrategy.APPEND_ASYNC);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return mFrameLayout;
    }

    @Override
    public void onWeexViewCreate(WXSDKInstance instance, View view) {
        mFrameLayout.addView(view);
    }

}
