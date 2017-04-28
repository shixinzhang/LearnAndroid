package net.sxkeji.shixinandroiddemo2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.BaseActivity;
import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.adapter.ChangeThemeSampleAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 切换夜间模式
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 9/18/2016
 */
public class ChangeThemeActivity extends BaseActivity {
    @BindView(R.id.tv_change_theme)
    TextView mTvChangeTheme;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private ChangeThemeSampleAdapter mAdapter;
    private List mList = Arrays.asList("shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute", "shixinzhang is so cute");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theme);
        ButterKnife.bind(this);
        initViews();

        writeCountryDataToServer();
    }

    private void writeCountryDataToServer() {
        String json ="";
        try {
            mTvChangeTheme.setText("读取中...");
            InputStream inputStream = getResources().getAssets().open("countryCode.json");
            int length = inputStream.available();   //获取文件的字节数
            byte[] buffer = new byte[length];   //创建byte数组
            inputStream.read(buffer);    //读取到 byte 数组中
            json = new String(buffer);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            mTvChangeTheme.setText("读取失败：" + e.getMessage());
        }

        mTvChangeTheme.setText("读取成功: " + json);

//        GsonUtils.jsonToBean();
    }

    @Override
    public void initViews() {
        mTvChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMySharedPreferences().edit().putBoolean(THEME_NAME, !isCurrentTheme()).apply();
                setDayNightTheme(!isCurrentTheme());
            }
        });
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new ChangeThemeSampleAdapter(this, mList);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void addListeners() {

    }

}
