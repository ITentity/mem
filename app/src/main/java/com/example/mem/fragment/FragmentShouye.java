package com.example.mem.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mem.R;
import com.example.mem.adapter.YuyingInfoAdapter;
import com.example.mem.databinding.FragmentShouyeBinding;
import com.example.mem.listen.OnItemClickListener;
import com.example.mem.utils.ToastUtils;
import com.example.mem.utils.WrapContentLinearLayoutManager;

import java.util.List;


public class FragmentShouye extends Fragment implements OnItemClickListener {
    private FragmentShouyeBinding binding;
    protected View rootView;
    private YuyingInfoAdapter yuyingInfoAdapter;
    private List<String> datas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (rootView == null) {
                assert inflater != null;
                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shouye, container, false);
                rootView = binding.getRoot();
                initView();
                initData();
                initViewListen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void initView() {
        /*产品列表*/
        binding.rvShopList.setLayoutManager(new WrapContentLinearLayoutManager(FragmentShouye.this.getActivity()));
        yuyingInfoAdapter = new YuyingInfoAdapter(datas);
        binding.rvShopList.setAdapter(yuyingInfoAdapter);
        yuyingInfoAdapter.setOnItemClickListener(this);

        // 设置下拉刷新的样式
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.qq_blue);
        binding.swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
    }

    private void initData() {

    }

    private void initViewListen (){
        /*搜索输入监听*/
        binding.head.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    binding.head.tvSearch.setVisibility(View.GONE);
                } else {
                    binding.head.tvSearch.setVisibility(View.VISIBLE);
                }
            }
        });

        /*处理扫描结果*/
        binding.head.editSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                /*回车搜索*/
                String search = binding.head.editSearch.getText().toString();
                if (TextUtils.isEmpty(search)) {
                    ToastUtils.show("请输入数据");
                    return false;
                }

                return true;
            }
            return true;
        });

        /*搜索监听*/
        binding.head.tvSearch.setOnClickListener(view -> {

        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
