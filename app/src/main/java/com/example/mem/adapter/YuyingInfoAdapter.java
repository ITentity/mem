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
import com.example.mem.entity.YunyingInfoBean;
import com.example.mem.listen.OnItemClickListener;
import com.example.mem.listen.OnYunyingStepHandleItemClickListener;
import com.example.mem.utils.ToastUtils;
import com.example.mem.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 开单适配器
 */
public class YuyingInfoAdapter extends RecyclerView.Adapter<YuyingInfoAdapter.ViewHolder> {

    private List<YunyingInfoBean> mList;

    public YuyingInfoAdapter() {

    }

    public void setDatas(List<YunyingInfoBean> listBeans) {
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
        YunyingInfoBean yunyingInfoBean = mList.get(position);
        if (position == mList.size() - 1) {
            holder.etStep.setEnabled(false);
            holder.etStep.setText("点击+新增步骤");
            holder.ivStepDesc.setImageDrawable(MyApplication.getInstance().getDrawable(R.mipmap.add_gray));
        } else  {
            holder.etStep.setEnabled(true);
            holder.etStep.setText("步骤" + (position + 1) + ":" + yunyingInfoBean.getStepName());
            UIUtils.setGoodImgPath(yunyingInfoBean.getImagePath(), holder.ivStepDesc);
        }
        holder.ivStepDesc.setOnClickListener(view -> {
            if (position == mList.size() - 1) { //新增步骤
                onYunyingStepHandleItemClickListener.addStep(view, position);
            } else {    // 新增图片
                onYunyingStepHandleItemClickListener.stepDescChange(view, position);
            }
        });

        holder.etStep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onYunyingStepHandleItemClickListener.stepNameChange(holder.etStep, position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
