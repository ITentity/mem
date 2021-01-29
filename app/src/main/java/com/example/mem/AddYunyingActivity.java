package com.example.mem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mem.app.MyApplication;
import com.example.mem.databinding.ActivityAddYunyingBinding;
import com.example.mem.databinding.ActivityMainBinding;
import com.example.mem.fragment.FragmentShouye;
import com.example.mem.utils.CameraMenu;
import com.example.mem.utils.PhotoUtils;
import com.example.mem.utils.ToastUtils;
import com.example.mem.utils.UIUtils;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class AddYunyingActivity extends AppCompatActivity {
    private ActivityAddYunyingBinding binding;
    private SparseArray<Fragment> fragments = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        binding = DataBindingUtil.setContentView(AddYunyingActivity.this, R.layout.activity_add_yunying);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        binding.ivSelect.setOnClickListener(v -> {
            selectPic();
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
                    UIUtils.setGoodImgPath(imgPath, binding.ivSelect);
                }
                break;
            case CameraMenu.CHOOSE_PHOTO:  // 打开相机选择图片的返回
                if (resultCode == RESULT_OK && null != data) {
                    String imgPath = PhotoUtils.getPath(this, data.getData());
                    ToastUtils.show(imgPath);
                    UIUtils.setGoodImgPath(imgPath, binding.ivSelect);
                }
                break;

        }
    }
}