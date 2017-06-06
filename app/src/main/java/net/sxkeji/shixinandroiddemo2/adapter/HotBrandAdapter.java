package net.sxkeji.shixinandroiddemo2.adapter;

import android.content.Context;

import net.sxkeji.shixinandroiddemo2.R;

import java.util.List;

import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseQuickAdapter;
import top.shixinzhang.sxframework.views.adapter.rvbaseadapter.BaseViewHolder;

/**
 * 热门品牌的 adapter
 * Created by zhangshixin on 8/30/2016.
 */
public class HotBrandAdapter extends BaseQuickAdapter<String> {
    public HotBrandAdapter(Context context, List<String> data) {
        super(context, data, R.layout.item_hot_brand);
    }

    @Override
    protected void convert(BaseViewHolder holder, String url) {
        holder.setImageUrl(R.id.iv_hot_brand, url);
    }
}
