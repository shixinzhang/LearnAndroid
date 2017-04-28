package net.sxkeji.shixinandroiddemo2.adapter;

import android.content.Context;

import net.sxkeji.shixinandroiddemo2.adapter.rvbaseadapter.BaseQuickAdapter;
import net.sxkeji.shixinandroiddemo2.adapter.rvbaseadapter.BaseViewHolder;
import net.sxkeji.shixinandroiddemo2.bean.ActivityBean;
import net.sxkeji.shixinandroiddemo2.R;

import java.util.List;

/**
 * 首页 Activity 列表的 Adapter
 * Created by zhangshixin on 8/30/2016.
 */
public class ActivityListAdapter extends BaseQuickAdapter<ActivityBean> {
    public ActivityListAdapter(Context context, List<ActivityBean> data) {
        super(context, data, R.layout.item_activity_name);
    }

    @Override
    protected void convert(BaseViewHolder holder, ActivityBean item) {
        holder.setText(R.id.tv_name,item.getName());
    }
}
