package com.example.mem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mem.adapter.YuyingInfoAdapter;
import com.example.mem.app.MyApplication;
import com.example.mem.databinding.ActivityAddYunyingBinding;
import com.example.mem.databinding.ActivityMainBinding;
import com.example.mem.entity.DB.YunyingStepDB;
import com.example.mem.entity.DB.YuyingInfoDB;
import com.example.mem.entity.YunyingInfoBean;
import com.example.mem.fragment.FragmentShouye;
import com.example.mem.listen.OnYunyingStepHandleItemClickListener;
import com.example.mem.utils.CameraMenu;
import com.example.mem.utils.PhotoUtils;
import com.example.mem.utils.ToastUtils;
import com.example.mem.utils.UIUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class AddYunyingActivity extends AppCompatActivity implements OnYunyingStepHandleItemClickListener {
    private ActivityAddYunyingBinding binding;
    private YuyingInfoAdapter yuyingInfoAdapter;
    private List<YunyingInfoBean> yunyingInfoBeanList = new ArrayList<>();
    private int selectPicIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        binding = DataBindingUtil.setContentView(AddYunyingActivity.this, R.layout.activity_add_yunying);
        initView();
        initData();
        initViewListen();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        binding.head.tvTitle.setText("新增运营");
        binding.head.tvMenu.setText("保存");
        // 设置布局管理器
        binding.rvYunyingInfo.setLayoutManager(layoutManager);
        yuyingInfoAdapter = new YuyingInfoAdapter();
        yuyingInfoAdapter.setDatas(yunyingInfoBeanList);
        yuyingInfoAdapter.setOnItemClickListener(this);
        binding.rvYunyingInfo.setAdapter(yuyingInfoAdapter);
        yuyingInfoAdapter.notifyDataSetChanged();
    }

    private void initData() {

        // 初始化数据
        YunyingInfoBean yunyingInfoBean = new YunyingInfoBean();
        yunyingInfoBean.setStepName("");
        yunyingInfoBean.setImagePath("");
        yunyingInfoBeanList.add(yunyingInfoBean);
        yuyingInfoAdapter.setDatas(yunyingInfoBeanList);
        yuyingInfoAdapter.notifyDataSetChanged();
    }

    private void initViewListen() {
        // 返回
        binding.head.ivBack.setOnClickListener(view -> {
            finish();
        });

        // 保存
        binding.head.tvMenu.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etYunyingName.getText().toString())) {
                ToastUtils.show("请输入运营标题");
                return;
            }
            YuyingInfoDB yuyingInfoDB = new YuyingInfoDB();
            yuyingInfoDB.setName(binding.etYunyingName.getText().toString());
            for (int i = 0; i < yunyingInfoBeanList.size() - 1; i ++) {
                YunyingStepDB yunyingStepDB = new YunyingStepDB();
                yunyingStepDB.setStepName(yunyingInfoBeanList.get(i).getStepName());
                yunyingStepDB.setImagePath(yunyingInfoBeanList.get(i).getImagePath());
                yunyingStepDB.save();
                yuyingInfoDB.getSteps().add(yunyingStepDB);
            }
            yuyingInfoDB.save();
            ToastUtils.show("保存成功");
            finish();
        });
    }



    /***************************************图片上传相关start***********************************/
    private void selectPic() {
        initPermission();
    }

    //申请两个权限，拍照和文件读写
    //1、首先声明一个数组permissions，将需要的权限都放在里面
    String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码

    private void initPermission() {
        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0 && Build.VERSION.SDK_INT >= 23) {//有权限没有通过，需要申请
            requestPermissions(permissions, mRequestCode);
        } else {
            //选择头像
            CameraMenu cameraMenu = new CameraMenu(this);
            cameraMenu.showMenu();
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
            } else {
                //权限允许后，进行下一步操作。。。
                //选择头像
                CameraMenu cameraMenu = new CameraMenu(this);
                cameraMenu.showMenu();
            }

        }

    }
    /***************************************图片上传相关end***********************************/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CameraMenu.SYSTEM_CAMERA:// 普通拍照
                if (resultCode == RESULT_OK) {
                    String imgPath = MyApplication.getInstance().getCapturePath();
                    /*置空拍照路径*/
                    MyApplication.getInstance().setCapturePath("");
                    if (selectPicIndex > -1) {
                        yunyingInfoBeanList.get(selectPicIndex).setImagePath(imgPath);
                        yuyingInfoAdapter.setDatas(yunyingInfoBeanList);
                        yuyingInfoAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case CameraMenu.CHOOSE_PHOTO:  // 打开相机选择图片的返回
                if (resultCode == RESULT_OK && null != data) {
                    String imgPath = PhotoUtils.getPath(this, data.getData());
                    Log.e("图片地址", imgPath);
                    if (selectPicIndex > -1) {
                        yunyingInfoBeanList.get(selectPicIndex).setImagePath(imgPath);
                        yuyingInfoAdapter.setDatas(yunyingInfoBeanList);
                        yuyingInfoAdapter.notifyDataSetChanged();
                    }
                }
                break;

        }
    }

    @Override
    public void addStep(View view, int position) {
        // 新增步骤
        YunyingInfoBean yunyingInfoBean = new YunyingInfoBean();
        yunyingInfoBean.setStepName("");
        yunyingInfoBean.setImagePath("");
        yunyingInfoBeanList.add(yunyingInfoBeanList.size() - 1, yunyingInfoBean);
        binding.rvYunyingInfo.scrollToPosition(yunyingInfoBeanList.size() - 1);
        yuyingInfoAdapter.setDatas(yunyingInfoBeanList);
        yuyingInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void stepNameChange(View view, int position, String text) {
        yunyingInfoBeanList.get(position).setStepName(text);
    }

    @Override
    public void stepDescChange(View view, int position) {
        selectPicIndex = position;
        selectPic();
    }
}