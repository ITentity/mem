package com.example.mem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mem.R;
import com.example.mem.listen.OnItemClickListener;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 开单适配器
 */
public class YuyingInfoAdapter extends RecyclerView.Adapter<YuyingInfoAdapter.ViewHolder> {

    private List<String> mList;




    public YuyingInfoAdapter(List<String> listBeans) {
        this.mList = listBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_new_goods_item, parent, false);
        return new ViewHolder(view);
    }

    /*可以防止数据错乱*/
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String product = mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        android.view.View View;


        ViewHolder(android.view.View itemView) {
            super(itemView);
            View = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener onItemClickListener;
    /*设置点击监听*/
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
