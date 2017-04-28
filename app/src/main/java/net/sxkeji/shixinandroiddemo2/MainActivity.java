package net.sxkeji.shixinandroiddemo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.sxkeji.shixinandroiddemo2.activity.ANRTestActivity;
import net.sxkeji.shixinandroiddemo2.activity.AmapLocationActivity;
import net.sxkeji.shixinandroiddemo2.activity.AnnotationTestActivity;
import net.sxkeji.shixinandroiddemo2.activity.AsyncTaskActivity;
import net.sxkeji.shixinandroiddemo2.activity.ChangeThemeActivity;
import net.sxkeji.shixinandroiddemo2.activity.DIYView1Activity;
import net.sxkeji.shixinandroiddemo2.activity.DownloadTestActivity;
import net.sxkeji.shixinandroiddemo2.activity.FocusInTouchModeActivity;
import net.sxkeji.shixinandroiddemo2.activity.HybridDemo1Activity;
import net.sxkeji.shixinandroiddemo2.activity.OaLoginActivity;
import net.sxkeji.shixinandroiddemo2.activity.RealmTestActivity;
import net.sxkeji.shixinandroiddemo2.activity.RefreshLoadMoreActivity;
import net.sxkeji.shixinandroiddemo2.activity.SearchActivity;
import net.sxkeji.shixinandroiddemo2.activity.ServiceTestActivity;
import net.sxkeji.shixinandroiddemo2.activity.SuspensionHeaderActivity;
import net.sxkeji.shixinandroiddemo2.adapter.ActivityListAdapter;
import net.sxkeji.shixinandroiddemo2.adapter.rvbaseadapter.BaseQuickAdapter;
import net.sxkeji.shixinandroiddemo2.bean.ActivityBean;
import net.sxkeji.shixinandroiddemo2.hybrid.SxWebViewActivity;
import net.sxkeji.shixinandroiddemo2.utils.SystemUtil;
import net.sxkeji.shixinandroiddemo2.weex.WeexActivity;
import net.sxkeji.shixinandroiddemo2.weex.WeexYmcActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 吸取之前那个 demo 的问题，不乱引入第三方，记住！
 */
public class MainActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    //    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ActivityListAdapter mActivityListAdapter;
    private static List<ActivityBean> mActivityNameList = new LinkedList<>();
    private final String INSTANCE_STATE_TEST = "instance_test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadData();
        initViews();

        if (!SystemUtil.checkIfOpenAccessibility(this)) {
            new AlertDialog
                    .Builder(this)
                    .setTitle("提示")
                    .setMessage("是否前往开启辅助辅助")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SystemUtil.jumpToSettingAccessibility(MainActivity.this);
                        }
                    })
                    .setNegativeButton("否", null)
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData() {
        registerActivityList(
                new ActivityBean("搜索", SearchActivity.class),
                new ActivityBean(getString(R.string.repeat_service), ServiceTestActivity.class),
                new ActivityBean("夜间模式", ChangeThemeActivity.class),
                new ActivityBean("Hybrid 练习1", HybridDemo1Activity.class),
                new ActivityBean(getString(R.string.diy_demo1), DIYView1Activity.class),
                new ActivityBean(getString(R.string.focusable_in_touch), FocusInTouchModeActivity.class),
                new ActivityBean(getString(R.string.alpha_header_recyclerview), SuspensionHeaderActivity.class),
                new ActivityBean(getString(R.string.refresh_load_more), RefreshLoadMoreActivity.class),
                new ActivityBean(getString(R.string.annotation), AnnotationTestActivity.class),
                new ActivityBean(getString(R.string.some_test), RealmTestActivity.class),
                new ActivityBean(getString(R.string.some_test), OaLoginActivity.class),
                new ActivityBean(getString(R.string.location), AmapLocationActivity.class),
                new ActivityBean(getString(R.string.hybrid), SxWebViewActivity.class),
                new ActivityBean(getString(R.string.async_task), AsyncTaskActivity.class),
                new ActivityBean(getString(R.string.weex1), WeexActivity.class),
                new ActivityBean(getString(R.string.weex_ymc), WeexYmcActivity.class),
                new ActivityBean(getString(R.string.anr_test), ANRTestActivity.class),
                new ActivityBean(getString(R.string.download_test), DownloadTestActivity.class));
    }

    public static void registerActivityList(ActivityBean... activityBeans) {
        if (activityBeans != null) {
            Collections.addAll(mActivityNameList, activityBeans);
        }
    }

    @Override
    public void initViews() {
        mActivityListAdapter = new ActivityListAdapter(this, mActivityNameList);
        mActivityListAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Class<?> claz = mActivityListAdapter.getData().get(position).getClaz();
                Intent intent = new Intent(MainActivity.this, claz);
                if (mActivityNameList != null) {
                    ActivityBean activityBean = mActivityNameList.get(position);
                    if (getString(R.string.hybrid).equals(activityBean.getName())) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    }
                }
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mActivityListAdapter);
    }


    @Override
    public void addListeners() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        showInfoLog("onSaveInstanceState");
        outState.putString(INSTANCE_STATE_TEST, "shixinzhang");
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String saveState = savedInstanceState.getString(INSTANCE_STATE_TEST);
        showInfoLog("onRestoreInstanceState" + saveState);
    }
}
