package com.example.mem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mem.adapter.ShowYuyingInfoAdapter;
import com.example.mem.adapter.YuyingInfoAdapter;
import com.example.mem.app.MyApplication;
import com.example.mem.databinding.ActivityAddYunyingBinding;
import com.example.mem.entity.DB.YunyingStepDB;
import com.example.mem.entity.DB.YuyingInfoDB;
import com.example.mem.entity.YunyingInfoBean;
import com.example.mem.listen.OnYunyingStepHandleItemClickListener;
import com.example.mem.utils.CameraMenu;
import com.example.mem.utils.PhotoUtils;
import com.example.mem.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ShowYunyingActivity extends AppCompatActivity {
    private ActivityAddYunyingBinding binding;
    private ShowYuyingInfoAdapter showYuyingInfoAdapter;
    private List<YunyingStepDB> yunyingStepDBList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        binding = DataBindingUtil.setContentView(ShowYunyingActivity.this, R.layout.activity_add_yunying);
        initView();
        initData();
        initViewListen();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        binding.head.tvTitle.setText("运营信息");
        binding.head.tvMenu.setText("");
        // 设置布局管理器
        binding.rvYunyingInfo.setLayoutManager(layoutManager);
        showYuyingInfoAdapter = new ShowYuyingInfoAdapter();
        showYuyingInfoAdapter.setDatas(yunyingStepDBList);
        binding.rvYunyingInfo.setAdapter(showYuyingInfoAdapter);
        showYuyingInfoAdapter.notifyDataSetChanged();
    }

    private void initData() {
        int id = getIntent().getIntExtra("yunying_id", 0);
        YuyingInfoDB yuyingInfoDB = LitePal.find(YuyingInfoDB.class, id, true);
        binding.etYunyingName.setText(yuyingInfoDB.getName());
        binding.etYunyingName.setEnabled(false);
        showYuyingInfoAdapter.setDatas(yuyingInfoDB.getSteps());
        showYuyingInfoAdapter.notifyDataSetChanged();
    }

    private void initViewListen() {
        // 返回
        binding.head.ivBack.setOnClickListener(view -> {
            finish();
        });
    }

}