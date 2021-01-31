package com.example.mem.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mem.R;
import com.example.mem.app.MyApplication;
import com.example.mem.entity.DB.YuyingInfoDB;
import com.example.mem.listen.OnItemClickListener;
import com.example.mem.listen.OnYunyingStepHandleItemClickListener;
import com.example.mem.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 显示运营步骤adapter
 */
public class ShowYuyingAdapter extends RecyclerView.Adapter<ShowYuyingAdapter.ViewHolder> {

    private List<YuyingInfoDB> mList;

    public ShowYuyingAdapter(List<YuyingInfoDB> listBeans) {
        this.mList = listBeans;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_yunying_item, parent, false);
        return new ViewHolder(view);
    }

    /*可以防止数据错乱*/
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YuyingInfoDB yuyingInfoDB = mList.get(position);
        holder.tvYunyingName.setText(yuyingInfoDB.getName());
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(view, position);
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        android.view.View View;
        public TextView tvYunyingName;
        ViewHolder(android.view.View itemView) {
            super(itemView);
            View = itemView;
            tvYunyingName = itemView.findViewById(R.id.tv_yunying_name);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener onItemClickListener;
    /*设置点击监听*/
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
