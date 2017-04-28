package net.sxkeji.shixinandroiddemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ismaeltoe.FlowLayout;

import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.adapter.SearchResultAdapter;
import net.sxkeji.shixinandroiddemo2.adapter.rvbaseadapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索品牌或车型
 * Created by zhangshixin on 9/6/2016.
 */
public class SearchBrandActivity extends Activity implements TextWatcher{
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.flow_recent_search)
    FlowLayout mFlowRecentSearch;
    @BindView(R.id.flow_hot_car)
    FlowLayout mFlowHotCar;
    @BindView(R.id.ll_search_history)
    LinearLayout mLlSearchHistory;
    @BindView(R.id.rv_search_result)
    RecyclerView mRvSearchResult;

    private SearchResultAdapter mSearchResultAdapter;
    private
    List<String> mSearchResultData = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "b", "c", "d", "b", "c", "d", "b", "c", "d", "b", "c", "d", "b", "c", "d"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_brand);
        ButterKnife.bind(this);

        initSearchData();
        initViews();
        addListener();
    }

    private void addListener() {
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    dismissInputSoft();
                    return true;
                }
                return false;
            }
        });
    }

    private void initViews() {
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissInputSoft();
                finish();
            }
        });
        mEtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEtSearch.addTextChangedListener(this);

        mSearchResultAdapter = new SearchResultAdapter(this, mSearchResultData);
        mSearchResultAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = mSearchResultAdapter.getData().get(position);
                Toast.makeText(SearchBrandActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        mRvSearchResult.setAdapter(mSearchResultAdapter);
    }

    private void dismissInputSoft() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();
            if (windowToken != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) currentFocus.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    private void initSearchData() {
        for (int i = 0; i < 7; i++) {
            final TextView tvRecentSearch = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_item, null);
            if (i % 2 == 0) {
                tvRecentSearch.setText("标致40" + i );
            }else {
                tvRecentSearch.setText("标致408标致408标致40"+i);
            }
            tvRecentSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SearchBrandActivity.this, tvRecentSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            mFlowRecentSearch.addView(tvRecentSearch);
        }
        for (int i = 0; i < 10; i++) {
            final TextView tvHotCar = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_item, null);
            if (i == 3 || i == 4 || i == 6){
                tvHotCar.setText("标致408标致408标致40"+i);
            }else {
                tvHotCar.setText("标致400" + i );
            }
            tvHotCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SearchBrandActivity.this, tvHotCar.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            mFlowHotCar.addView(tvHotCar);
        }
    }

    /**
     * 监听输入框输入的内容，实时搜索
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            mRvSearchResult.setVisibility(View.GONE);
        } else {
            // TODO: 8/30/2016 请求搜索结果，填充数据
            mSearchResultData.add(s.toString());
            mSearchResultAdapter.setData(mSearchResultData);
            mRvSearchResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

}
