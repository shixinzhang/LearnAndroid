package net.sxkeji.shixinandroiddemo2.adapter.rvbaseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 封装后的RecyclerAdapter
 * Created by zhangshixin on 5/13/2016.
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<T> mData;
    private int mLayoutId;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    /**
     * 移除数据
     * @param position
     */
    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置数据
     * @param data
     */
    public void setData(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 添加数据
     * @param position
     * @param item
     */
    public void addData(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public BaseQuickAdapter(Context context, List<T> data, int layoutId) {
        mContext = context;
        mData = data;
        mLayoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        return new BaseViewHolder(mContext, mView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (mData == null) {
            return;
        }
        convert(holder, mData.get(position));
        if (onRecyclerViewItemClickListener != null) {
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    protected abstract void convert(BaseViewHolder holder, T item);

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
}
