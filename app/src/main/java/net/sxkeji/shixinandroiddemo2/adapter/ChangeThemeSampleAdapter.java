package net.sxkeji.shixinandroiddemo2.adapter;

import android.content.Context;

import net.sxkeji.shixinandroiddemo2.R;
import net.sxkeji.shixinandroiddemo2.adapter.rvbaseadapter.BaseQuickAdapter;
import net.sxkeji.shixinandroiddemo2.adapter.rvbaseadapter.BaseViewHolder;

import java.util.List;

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
