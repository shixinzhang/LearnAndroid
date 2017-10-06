package com.example.simon.androidlife;

import android.content.Context;

import java.util.List;

import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseQuickAdapter;
import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseViewHolder;

/**
 * desc: RecyclerView 的适配器
 *
 * @author simon
 * @since 24/09/2017.
 */

public class TitleRecyclerAdapter extends BaseQuickAdapter<String> {
    public TitleRecyclerAdapter(Context context, List<String> data) {
        super(context, data, R.layout.item_recycler_title);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        holder.setText(R.id.tv_title, s);
    }
}
