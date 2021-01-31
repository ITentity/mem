package com.example.mem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mem.adapter.SynonymInfoAdapter;
import com.example.mem.adapter.YuyingInfoAdapter;
import com.example.mem.app.MyApplication;
import com.example.mem.databinding.ActivityAddSynonymBinding;
import com.example.mem.databinding.ActivityAddYunyingBinding;
import com.example.mem.entity.DB.SynonymDB;
import com.example.mem.entity.DB.SynonymMainDB;
import com.example.mem.entity.DB.YunyingStepDB;
import com.example.mem.entity.DB.YuyingInfoDB;
import com.example.mem.entity.YunyingInfoBean;
import com.example.mem.listen.OnItemClickListener;
import com.example.mem.listen.OnYunyingStepHandleItemClickListener;
import com.example.mem.utils.CameraMenu;
import com.example.mem.utils.PhotoUtils;
import com.example.mem.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class AddSynonymActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityAddSynonymBinding binding;
    private SynonymInfoAdapter synonymInfoAdapter;
    private List<String> synonymList = new ArrayList<>();
    private int selectPicIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        binding = DataBindingUtil.setContentView(AddSynonymActivity.this, R.layout.activity_add_synonym);
        initView();
        initData();
        initViewListen();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        binding.head.tvTitle.setText("新增近似词");
        binding.head.tvMenu.setText("保存");
        // 设置布局管理器
        binding.rvYunyingInfo.setLayoutManager(layoutManager);
        synonymInfoAdapter = new SynonymInfoAdapter(synonymList);
        synonymInfoAdapter.setOnItemClickListener(this);
        binding.rvYunyingInfo.setAdapter(synonymInfoAdapter);
        synonymInfoAdapter.notifyDataSetChanged();
    }

    private void initData() {

    }

    private void initViewListen() {
        // 返回
        binding.head.ivBack.setOnClickListener(view -> {
            finish();
        });

        // 保存
        binding.head.tvMenu.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etYunyingName.getText().toString())) {
                ToastUtils.show("请输入主词");
                return;
            }
            SynonymMainDB synonymMainDB = new SynonymMainDB();
            synonymMainDB.setMianName(binding.etYunyingName.getText().toString());
            for (String s : synonymList) {
                SynonymDB synonymDB = new SynonymDB();
                synonymDB.setSynonymName(s);
                synonymDB.save();
                synonymMainDB.getSynonymDBList().add(synonymDB);
            }
            synonymMainDB.save();
            ToastUtils.show("保存成功");
            finish();
        });

        binding.fabAdd.setOnClickListener(view ->  {
            final EditText editText = new EditText(AddSynonymActivity.this);
            AlertDialog mDialog = new AlertDialog.Builder(this).setPositiveButton("确定", null).setNegativeButton("取消", null).create();
            mDialog.setView(editText);
            mDialog.setTitle("输入近似词");
            mDialog.setCancelable(false);
            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positionButton=mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton=mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    positionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(editText.getText().toString())) {
                                ToastUtils.show("请输入内容");
                                return;
                            }
                            synonymList.add(editText.getText().toString());
                            synonymInfoAdapter.notifyDataSetChanged();
                            mDialog.dismiss();
                        }
                    });
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                }
            });
            mDialog.show();
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}