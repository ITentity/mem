package com.example.mem.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mem.R;
import com.example.mem.app.MyApplication;
import com.example.mem.entity.DB.YunyingStepDB;
import com.example.mem.entity.YunyingInfoBean;
import com.example.mem.listen.OnYunyingStepHandleItemClickListener;
import com.example.mem.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 新增运营步骤adapter
 */
public class ShowYuyingInfoAdapter extends RecyclerView.Adapter<ShowYuyingInfoAdapter.ViewHolder> {

    private List<YunyingStepDB> mList;

    public ShowYuyingInfoAdapter() {

    }

    public void setDatas(List<YunyingStepDB> listBeans) {
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
        YunyingStepDB yunyingInfoBean = mList.get(position);
        holder.etStep.setEnabled(false);
        holder.etStep.setText("步骤" + (position + 1) + "：" + yunyingInfoBean.getStepName());
        UIUtils.setGoodImgPathDefault(yunyingInfoBean.getImagePath(), holder.ivStepDesc);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        android.view.View View;
        public EditText etStep;
        public ImageView ivStepDesc;
        ViewHolder(android.view.View itemView) {
            super(itemView);
            View = itemView;
            etStep = itemView.findViewById(R.id.et_step_name);
            ivStepDesc = itemView.findViewById(R.id.iv_step_desc);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnYunyingStepHandleItemClickListener onYunyingStepHandleItemClickListener;
    /*设置点击监听*/
    public void setOnItemClickListener(OnYunyingStepHandleItemClickListener onYunyingStepHandleItemClickListener) {
        this.onYunyingStepHandleItemClickListener = onYunyingStepHandleItemClickListener;
    }

}
