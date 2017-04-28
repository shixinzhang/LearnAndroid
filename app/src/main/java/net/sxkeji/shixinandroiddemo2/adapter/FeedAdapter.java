package net.sxkeji.shixinandroiddemo2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.sxkeji.shixinandroiddemo2.R;

/**
 * Created by snowbean on 16-11-4.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    private static final String TAG = "FeedAdapter";

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        Picasso.with(holder.itemView.getContext())
                .load(getContentResId(position))
                .centerInside()
                .fit()
                .into(holder.mIvContent);

        holder.mTvNickname.setText("旧 header " + position);
    }

    private int getContentResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.taeyeon_one;
            case 1:
                return R.drawable.taeyeon_two;
            case 2:
                return R.drawable.taeyeon_three;
            case 3:
                return R.drawable.taeyeon_four;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        ImageView mIvContent;
        TextView mTvNickname;

        public FeedHolder(View itemView) {
            super(itemView);
            mIvContent = (ImageView) itemView.findViewById(R.id.iv_content);
            mTvNickname = (TextView) itemView.findViewById(R.id.tv_nickname);
        }

    }
}
