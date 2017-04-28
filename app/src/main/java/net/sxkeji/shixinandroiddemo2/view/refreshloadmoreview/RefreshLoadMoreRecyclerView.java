package net.sxkeji.shixinandroiddemo2.view.refreshloadmoreview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.R;

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
public class RefreshLoadMoreRecyclerView extends FrameLayout {
    private final String TAG = "RefreshLoadMore";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_view)
    TextView mRefreshView;
    @BindView(R.id.load_more_view)
    TextView mLoadMoreView;

    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mRefreshHeight;

    public RefreshLoadMoreRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public RefreshLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshLoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_load_more, this);
        ButterKnife.bind(this, view);

        final int screenHeightPx = context.getResources().getDisplayMetrics().heightPixels;

        mRefreshView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRefreshView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.e(TAG, "下拉刷新的高度 " + mRefreshView.getHeight());
                mRefreshView.setY(-mRefreshView.getHeight()); //隐藏下拉刷新到顶部
            }
        });

//        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mRefreshView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                Log.e(TAG, "下拉刷新的高度 " + mRecyclerView.getHeight());
//                mRecyclerView.setY(-mRecyclerView.getHeight()); //隐藏下拉刷新到顶部
//            }
//        });

        mLoadMoreView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLoadMoreView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.e(TAG, "加载更多的高度 " + mLoadMoreView.getMeasuredHeight());
                mLoadMoreView.setY(screenHeightPx + mLoadMoreView.getHeight()); //隐藏加载更多到底部
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mRefreshHeight = mRefreshView.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled " + dx + " , " + dy);
//                View firstView = getLayoutManager().findViewByPosition(0);    //第一个 View
//                if (firstView == null) {
//                    return; //没有子布局
//                }
//                Log.e(TAG, "第 1  个 item 的顶部高度" + firstView.getTop());

                if (dy < 0 ) {
                    mRefreshView.setY(-mRefreshHeight - dy);
                }
//                if (firstView.getTop() < mRefreshHeight) {
//                    mTvHeader.setY(viewByPosition.getTop() - mHeaderHeight);
//                    Log.e(TAG, "mTvHeader.setY " + (viewByPosition.getTop() - mHeaderHeight));
//
//                } else {
//                    //还原
//                    mRefreshView.setY(0);
//                    Log.e(TAG, "mRefreshView.setY(0)");
//                }

                if (getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    Log.e(TAG, "lastVisibleItemPosition " + lastVisibleItemPosition);

                }
            }
        });
    }

    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {
        mRecyclerAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mRecyclerView.setLayoutManager(layoutManager);
    }


    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public TextView getRefreshView() {
        return mRefreshView;
    }

    public void setRefreshView(TextView refreshView) {
        mRefreshView = refreshView;
    }

    public TextView getLoadMoreView() {
        return mLoadMoreView;
    }

    public void setLoadMoreView(TextView loadMoreView) {
        mLoadMoreView = loadMoreView;
    }
}
