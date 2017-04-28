package net.sxkeji.shixinandroiddemo2.view.refreshloadmoreview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sxkeji.shixinandroiddemo2.R;

import java.util.ArrayList;

/**
 * <header>
 * Description:
 * </header>
 * <p>
 * Author: shixinzhang
 * </p>
 * <p>
 * Create at: 11/15/2016
 * </p>
 * <p>
 * Update at: 11/15/2016
 * </p>
 * <p>
 * Related links: <a href="${link_address}">${linkName}</a>
 * </p>
 */
public class RefreshLoadMoreAdapter extends RecyclerView.Adapter<RefreshLoadMoreAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<String> mData;    //your data list here

    public RefreshLoadMoreAdapter(Context context) {
        this(context, new ArrayList<String>());
    }

    public RefreshLoadMoreAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        String itemData = getItem(position);
        holder.tvItem.setText(itemData);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String getItem(int position) {
        if (position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public RecyclerViewHolder(View view) {
            super(view);
            tvItem = (TextView) view.findViewById(R.id.tv_item);
        }
    }
}
