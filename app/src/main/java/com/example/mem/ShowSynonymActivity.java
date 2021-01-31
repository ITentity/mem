package com.example.mem;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mem.adapter.ShowYuyingInfoAdapter;
import com.example.mem.adapter.SynonymInfoAdapter;
import com.example.mem.databinding.ActivityShowSynonymBinding;
import com.example.mem.entity.DB.SynonymDB;
import com.example.mem.entity.DB.SynonymMainDB;
import com.example.mem.entity.DB.YunyingStepDB;
import com.example.mem.entity.DB.YuyingInfoDB;
import com.example.mem.listen.OnItemClickListener;
import com.gyf.barlibrary.ImmersionBar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ShowSynonymActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityShowSynonymBinding binding;
    private SynonymInfoAdapter synonymInfoAdapter;
    private List<String> synonymList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        binding = DataBindingUtil.setContentView(ShowSynonymActivity.this, R.layout.activity_show_synonym);
        initView();
        initData();
        initViewListen();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        binding.head.tvTitle.setText("近义词组信息");
        binding.head.tvMenu.setText("");
        // 设置布局管理器
        binding.rvYunyingInfo.setLayoutManager(layoutManager);
        synonymInfoAdapter = new SynonymInfoAdapter(synonymList);
        binding.rvYunyingInfo.setAdapter(synonymInfoAdapter);
        synonymInfoAdapter.notifyDataSetChanged();
    }

    private void initData() {
        int id = getIntent().getIntExtra("synonym_main_id", 0);
        SynonymMainDB synonymMainDB = LitePal.find(SynonymMainDB.class, id, true);
        synonymList.add(synonymMainDB.getMianName());
        for (SynonymDB synonymDB : synonymMainDB.getSynonymDBList()) {
            synonymList.add(synonymDB.getSynonymName());
        }
        synonymInfoAdapter.notifyDataSetChanged();
    }

    private void initViewListen() {
        // 返回
        binding.head.ivBack.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}