package net.sxkeji.shixinandroiddemo2.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.adapter.FeedAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <header>
 * Description: 学习 RecyclerView 顶部悬浮条
 *      通过 LinearLayoutManager 获取指定位置的 Item View, 然后计算出这个 Item 顶部的高度，是否已经到了 悬浮条的位置，
 *      如果是，就更新旧悬浮条的位置。
 *      当旧悬浮条从顶部完全移出界面 或者 指定位置的 Item View 让出悬浮条的位置时，恢复悬浮条的位置，并且填充数据。
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 * <p>
 * Create at: 11/13/2016
 * </p>
 * <p>
 * Update at: 11/13/2016
 * </p>
 * <p>
 * Related links: <a href="http://www.jianshu.com/p/fe69a53502ab">相关博客</a>
 * </p>
 */
public class SuspensionHeaderActivity extends BaseActivity {
    private final String TAG = "AlphaHeader";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_header)
    TextView mTvHeader;
    private int mHeaderHeight;
    private int mCurrentPosition = 0;   //当前位置？
    private LinearLayoutManager mLinearLayoutManager;
    private FeedAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_header);
        ButterKnife.bind(this);
        initViews();
        Log.d(TAG,"onCreate");

        {
            {
                {
                    Log.d(TAG,"这是哪里1");
                }
                Log.d(TAG,"这是哪里2");
            }
            Log.d(TAG,"这是哪里3");
        }

        new AlertDialog.Builder(this)
                .setTitle("hello")
                .setMessage("I'm shixinzhang")
                .setIcon(R.mipmap.icon_ez)
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //...
                    }
                })
                .show();
    }

    @Override
    public void initViews() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FeedAdapter();
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mHeaderHeight = mTvHeader.getHeight();  //获取悬浮条高度
                Log.e(TAG, "mHeaderHeight " + mHeaderHeight);
                Log.e(TAG, "onScrollStateChanged " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled " + dx + " , " + dy);
                View viewByPosition = mLinearLayoutManager.findViewByPosition(mCurrentPosition + 1);    //下一个 View
                if (viewByPosition == null) {
                    return; //到最后一个了
                }
                Log.e(TAG, "第 " + (mCurrentPosition+1) + " 个 item 的顶部高度"+ viewByPosition.getTop());
                if (viewByPosition.getTop() < mHeaderHeight) {
                    //滑到悬浮条上面,悬浮条往上移动，被顶走
                    mTvHeader.setY(viewByPosition.getTop() - mHeaderHeight);
                    Log.e(TAG, "mTvHeader.setY " + (viewByPosition.getTop() - mHeaderHeight));

                } else {
                    //滑到悬浮条以外，还原
                    mTvHeader.setY(0);
                    Log.e(TAG, "mTvHeader.setY(0)");
                }

                if (mCurrentPosition != mLinearLayoutManager.findFirstVisibleItemPosition()) {   //当前可见的元素已经不是之前的了，更新
                    mCurrentPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                    mTvHeader.setY(0);

                    mTvHeader.setText("新 header " + mCurrentPosition);
                }

            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void addListeners() {

    }
}
