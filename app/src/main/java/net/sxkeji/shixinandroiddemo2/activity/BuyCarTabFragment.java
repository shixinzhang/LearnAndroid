package net.sxkeji.shixinandroiddemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.adapter.HotBrandAdapter;
import net.sxkeji.shixinandroiddemo2.bean.CarBrandBean;
import net.sxkeji.shixinandroiddemo2.util.GsonUtils;
import net.sxkeji.shixinandroiddemo2.view.sortlistview.SortFrameLayout;
import net.sxkeji.shixinandroiddemo2.view.sortlistview.SortModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 买车 Tab Native
 * Created by zhangshixin on 9/1/2016.
 */
public class BuyCarTabFragment extends Fragment implements SearchActivity.OnBackPressFragmentListener {
    private final String TAG = "BuyCarTabFragment";
    @BindView(R.id.sortframlayout)
    SortFrameLayout mSortframlayout;
    @BindView(R.id.iv_logo)
    ImageView mIvSelectBrandLogo;
    @BindView(R.id.tv_name)
    TextView mTvSelectBrandName;
    @BindView(R.id.recycler_car_series)
    RecyclerView mRecyclerCarSeries;
    @BindView(R.id.drawer_car_series)
    RelativeLayout mDrawerCarSeries;
    @BindView(R.id.drawlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_tab)
    TextView mTvTab;

    private Context mContext;
    private Activity mActivity;
    private HotBrandAdapter mHotBrandAdapter;
    private List<String> mHotBrandData = new ArrayList<>(Arrays.asList("http://img.yaomaiche.com/upload/image/original/201508/171340516914.png", "http://img.yaomaiche.com/upload/image/original/201510/222109576440.png",
            "http://img.yaomaiche.com/upload/image/original/201508/171341491701.png", "http://img.yaomaiche.com/upload/image/original/201508/201505145311.png",
            "http://img.yaomaiche.com/upload/image/original/201508/201503529815.png", "http://img.yaomaiche.com/upload/image/original/201508/171340516914.png", "http://img.yaomaiche.com/upload/image/original/201510/222109576440.png",
            "http://img.yaomaiche.com/upload/image/original/201508/171341491701.png", "http://img.yaomaiche.com/upload/image/original/201508/201505145311.png", "http://img.yaomaiche.com/upload/image/original/201508/201505145311.png"));
    private List<SortModel> mCarBrandList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_car, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        mActivity = getActivity();
        loadData();
        initViews();
        setListener();
    }

    private void loadData() {
        initLocalCarListData();
    }

    private void initViews() {
        initCarBrandList();
    }

    private void initCarBrandList() {
        //顶部的搜索和热门品牌
        View hotBrandHeaderView = View.inflate(mContext, R.layout.include_hot_brand, null);
        //点击进入搜索
        hotBrandHeaderView.findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchBrandActivity.class);
                startActivity(intent);
            }
        });
        //热门品牌
        RecyclerView recyclerHotBrand = (RecyclerView) hotBrandHeaderView.findViewById(R.id.recycler_hot_brand);

        mHotBrandAdapter = new HotBrandAdapter(mContext, mHotBrandData);
        recyclerHotBrand.setAdapter(mHotBrandAdapter);
        recyclerHotBrand.setLayoutManager(new GridLayoutManager(mContext, 5));

        if (mCarBrandList != null) {
            mSortframlayout.setData(hotBrandHeaderView, mCarBrandList);
            mSortframlayout.setRefresh(false);
        } else {
            Log.e("汽车品牌列表", "汽车列表为空");
        }
    }


    /**
     * 加载本地品牌数据
     */
    private void initLocalCarListData() {
        try {
            InputStream inputStream = mActivity.getAssets().open("allbrand.json.txt");
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            String localBrandJson = new String(bytes);

            //加载成功，解析
            if (!TextUtils.isEmpty(localBrandJson)) {
                CarBrandBean carBrandBean = (CarBrandBean) GsonUtils.jsonToBean(localBrandJson, CarBrandBean.class);
                mCarBrandList = carBrandBean.getCarBrandList();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("汽车品牌列表", "汽车列表加载失败" + e.getMessage());
        }
    }

    long lastTimeClick;
    private void setListener() {
        mTvTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = SystemClock.currentThreadTimeMillis();
                if (currentTime - lastTimeClick < 200) {
                    mSortframlayout.scrollToTop();
                }
                lastTimeClick = SystemClock.currentThreadTimeMillis();
            }
        });

        mSortframlayout.setOnItemClickListener(new SortFrameLayout.SortListOnItemClickListener() {
            @Override
            public void onItemClick(List<SortModel> sortModels, int position) {
                SortModel sortModel = sortModels.get(position);
                if (sortModel != null && !TextUtils.isEmpty(sortModel.getName())) {
                    mTvSelectBrandName.setText(sortModel.getName());
                    String logoUrl = sortModel.getLogoUrl();
                    if (!TextUtils.isEmpty(logoUrl)) {
                        Picasso.with(mContext).load(logoUrl)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(mIvSelectBrandLogo);
                    }
                }
                setDrawerLayout();
            }
        });
    }

    /**
     * 抽屉的展开与关闭
     **/
    private void setDrawerLayout() {
        if (mDrawerLayout.isDrawerOpen(mDrawerCarSeries)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.END);
        }
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED); //打开
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭滑动
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Activity 的返回键监听回调
     *
     * @return true if resolved the call back, else call superclass to resolve
     */
    @Override
    public boolean onBackPressed() {
//        if (mSearchResultList.getVisibility() == View.VISIBLE) {
//            mSearchResultList.setVisibility(View.GONE);
//            return true;
//        }
//        if (mLlSearchHistory.getVisibility() == View.VISIBLE) {
//            mLlSearchHistory.setVisibility(View.GONE);
//            return true;
//        }
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mDrawerCarSeries)) {
            mDrawerLayout.closeDrawers();
            return true;
        }
        return false;
    }
}
