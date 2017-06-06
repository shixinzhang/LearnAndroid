package net.sxkeji.shixinandroiddemo2.adapter;

import android.content.Context;

import net.sxkeji.shixinandroiddemo2.R;

import java.util.List;

import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseQuickAdapter;
import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseViewHolder;

/**
 * description:
 * <br/>
 * author: shixinzhang
 * <br/>
 * data: 9/18/2016
 */
public class ChangeThemeSampleAdapter extends BaseQuickAdapter<String> {
    public ChangeThemeSampleAdapter(Context context, List<String> data) {
        super(context, data, R.layout.item_change_theme);
    }


    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
