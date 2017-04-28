package net.sxkeji.shixinandroiddemo2.view.sortlistview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.adapter.lvbaseadapter.BaseAdapterHelper;
import net.sxkeji.shixinandroiddemo2.adapter.lvbaseadapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 按首字母排序的车品牌列表
 * Created by renbingwu on 2015/10/10.
 * Updated by shixinzhang on 2016/09/01
 */
public class SortFrameLayout extends FrameLayout {
    private SideBar sidrbar;
    private TextView dialog;
    QuickAdapter<SortModel> sortAdapter;
    private ListView lvBrand;
    private List<SortModel> sortModels;
    private CharacterParser characterParser;
    PinyinComparator pinyinComparator;
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean enAble = false;


    private View topView;
    private int size = 12;
    private SortListOnItemClickListener itenClickListener;

    public SortFrameLayout(Context context) {
        super(context);
        initView();
    }

    public SortFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SortFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.sortlistview, this);
        lvBrand = (ListView) view.findViewById(R.id.listview_brand);
        sidrbar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.tv_dialog);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);


        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        size = (int) (size * dm.density);
    }


    public void setRefresh(boolean enAble) {

        if (null != swipeRefreshLayout) {
            swipeRefreshLayout.setEnabled(enAble);
        }

    }

    private void initData() {
//        swipeRefreshLayout.setEnabled(false);


        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        // 根据a-z进行排序源数据
//        Collections.sort(sortModels, pinyinComparator);
        sidrbar.setTextView(dialog);
        sidrbar.setTextSize(size);
        List<String> letters = getLetter();
        if (topView != null) {
            letters.add(0, "#");
        }
        if (topView != null) {
            lvBrand.addHeaderView(topView);
        }
        sidrbar.setTextB(letters.toArray(new String[letters.size()]));
        sortAdapter = new QuickAdapter<SortModel>(getContext(), R.layout.sortlist_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, SortModel item) {
                //根据position获取分类的首字母的Char ascii值
                int section = item.getFirstLetters().charAt(0);
                TextView tvLetter = helper.getView(R.id.catalog);
                ImageView line = helper.getView(R.id.iv_bottomLine);
                //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
                if (helper.getPosition() == getPositionForSection(section, sortModels)) {
                    tvLetter.setVisibility(View.VISIBLE);
                    line.setVisibility(View.GONE);
                    tvLetter.setText(item.getFirstLetters());
                } else {
                    line.setVisibility(View.VISIBLE);
                    tvLetter.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.logoUrl)) {
                    helper.setImageUrl(R.id.iv_sort, item.logoUrl);
                } else {
                    //// TODO: 7/11/2016 修改默认图片
                    ((ImageView) helper.getView(R.id.iv_sort)).setImageResource(R.mipmap.ic_launcher);
                }
                helper.setText(R.id.iv_sortname, item.getName());
                if (item.getName().equals("其他")) {
                    ((ImageView) helper.getView(R.id.iv_sort)).setVisibility(View.GONE);
                } else {
                    ((ImageView) helper.getView(R.id.iv_sort)).setVisibility(View.VISIBLE);
                }
            }
        };
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = getPositionForSection(s.charAt(0), sortModels);
                if (topView == null) {
                    lvBrand.setSelection(position);
                } else {
                    if (position != -1) {
                        lvBrand.setSelection(position + 1);
                    } else {
                        lvBrand.setSelection(0);
                    }
                }
            }
        });
        lvBrand.setAdapter(sortAdapter);
        sortAdapter.addAll(sortModels);
        lvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (itenClickListener != null) {
                    if (topView != null) {
                        position = position - 1;
                    }
                    itenClickListener.onItemClick(sortModels, position);
                }
            }
        });

    }

    public void setOnItemClickListener(SortListOnItemClickListener itenClickListener) {
        this.itenClickListener = itenClickListener;
    }

    public void setData(View topView, List<SortModel> sortModels) {
        this.topView = topView;
        this.sortModels = sortModels;
        if (sortModels == null || sortModels.size() <= 0) {
            Toast.makeText(getContext(), "请填充数据", Toast.LENGTH_SHORT).show();
            return;
        }
        initData();
    }

    public void setSidrbarSize(int size) {
        this.size = size;
    }

    private List<String> getLetter() {
        List<String> letters = new ArrayList<String>();
        for (SortModel sormodle : sortModels) {
            String letter = sormodle.getFirstLetters();
            if (!letters.contains(letter)) {
                letters.add(letter);
            }
        }
        return letters;
    }

    /**
     * 滑到顶部
     */
    public void scrollToTop(){
        lvBrand.setSelection(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section, List<SortModel> list) {
        for (int i = 0; i < list.size(); i++) {
            String sortStr = list.get(i).getFirstLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    public interface SortListOnItemClickListener {
        void onItemClick(List<SortModel> sortModels, int position);
    }
}
