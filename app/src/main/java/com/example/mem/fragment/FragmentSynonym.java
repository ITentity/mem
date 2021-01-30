package com.example.mem.fragment;


import android.content.Intent;
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

import com.example.mem.AddYunyingActivity;
import com.example.mem.R;
import com.example.mem.ShowYunyingActivity;
import com.example.mem.adapter.ShowYuyingAdapter;
import com.example.mem.databinding.FragmentShouyeBinding;
import com.example.mem.entity.DB.YuyingInfoDB;
import com.example.mem.listen.OnItemClickListener;
import com.example.mem.utils.ToastUtils;
import com.example.mem.utils.WrapContentLinearLayoutManager;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class FragmentSynonym extends Fragment implements OnItemClickListener {
    private FragmentShouyeBinding binding;
    protected View rootView;
    private ShowYuyingAdapter showYuyingAdapter;
    private List<YuyingInfoDB> yuyingInfoDBS = new ArrayList<>();

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

    @Override
    public void onResume() {
        super.onResume();
        // 后面增加ture为激进查询方法，可以查出关联表的数据
        List<YuyingInfoDB> yuyingInfoDBList = LitePal.findAll(YuyingInfoDB.class);
        yuyingInfoDBS.clear();
        yuyingInfoDBS.addAll(yuyingInfoDBList);
        handleUI();

    }

    private void handleUI() {
        showYuyingAdapter.notifyDataSetChanged();
        if (yuyingInfoDBS != null && yuyingInfoDBS.size() >0) {
            binding.rvShopList.setVisibility(View.VISIBLE);
            binding.ivNoData.setVisibility(View.GONE);
        } else {
            binding.rvShopList.setVisibility(View.GONE);
            binding.ivNoData.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        /*运营列表*/
        binding.rvShopList.setLayoutManager(new WrapContentLinearLayoutManager(FragmentSynonym.this.getActivity()));
        showYuyingAdapter = new ShowYuyingAdapter(yuyingInfoDBS);
        showYuyingAdapter.setOnItemClickListener(this);
        binding.rvShopList.setAdapter(showYuyingAdapter);
        showYuyingAdapter.notifyDataSetChanged();



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

        binding.fabAdd.setOnClickListener(view -> {
            startActivity(new Intent(this.getContext(), AddYunyingActivity.class));
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this.getContext(), ShowYunyingActivity.class);
        intent.putExtra("yunying_id", yuyingInfoDBS.get(position).getId());
        startActivity(intent);
    }
}
