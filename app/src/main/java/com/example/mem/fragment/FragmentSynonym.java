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

import com.example.mem.AddSynonymActivity;
import com.example.mem.AddYunyingActivity;
import com.example.mem.R;
import com.example.mem.ShowYunyingActivity;
import com.example.mem.adapter.ShowYuyingAdapter;
import com.example.mem.databinding.FragmentShouyeBinding;
import com.example.mem.databinding.FragmentSynonymBinding;
import com.example.mem.entity.DB.YuyingInfoDB;
import com.example.mem.listen.OnItemClickListener;
import com.example.mem.utils.ToastUtils;
import com.example.mem.utils.WrapContentLinearLayoutManager;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class FragmentSynonym extends Fragment implements OnItemClickListener {
    private FragmentSynonymBinding binding;
    protected View rootView;

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
                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_synonym, container, false);
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


    }



    private void initView() {

    }

    private void initData() {

    }

    private void initViewListen (){
        binding.fabAdd.setOnClickListener(view -> {
            startActivity(new Intent(this.getContext(), AddSynonymActivity.class));
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
