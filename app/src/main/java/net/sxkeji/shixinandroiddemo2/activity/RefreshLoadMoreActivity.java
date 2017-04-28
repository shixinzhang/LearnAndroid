package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.view.refreshloadmoreview.RefreshLoadMoreAdapter;
import net.sxkeji.shixinandroiddemo2.view.refreshloadmoreview.RefreshLoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <header>
 * Description:
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 * <p>
 * Create at: 11/15/2016
 * </p>
 * <p>
 * Update at: 11/15/2016
 * </p>
 * <p>
 * Related links: <a href="${link_address}">${linkName}</a>
 * </p>
 */
public class RefreshLoadMoreActivity extends BaseActivity {
    @BindView(R.id.refresh_load_more_view)
    RefreshLoadMoreRecyclerView mRefreshLoadMoreView;

    private ArrayList<String> mData;
    private RefreshLoadMoreAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_load_more);
        ButterKnife.bind(this);
        loadData();
        initViews();
    }

    @Override
    public void initViews() {
        mAdapter = new RefreshLoadMoreAdapter(this, mData);
        mLinearLayoutManager = new LinearLayoutManager(this);

        mRefreshLoadMoreView.setRecyclerAdapter(mAdapter);
        mRefreshLoadMoreView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void loadData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("first_item " + i);
        }
    }

    @Override
    public void addListeners() {

    }
}
