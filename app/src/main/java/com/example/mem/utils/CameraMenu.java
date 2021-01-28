package com.example.mem.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mem.R;
import com.example.mem.app.MyApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加图片菜单
 * Created by liuyongfeng on 2017/5/26.
 */

public class CameraMenu {

    public static final int SYSTEM_CAMERA = 0x00;
    public static final int CHOOSE_PHOTO = 0x01;

    @BindView(R.id.tv_camera)
    TextView tvCamera;
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    private Activity mActivity;
    private Dialog dialog;

    public CameraMenu(Activity activity) {
        this.mActivity = activity;
    }

    public void showMenu() {
        dialog = new Dialog(mActivity, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_picture, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        if (window != null) {
            //设置dialog在屏幕位置
            window.setGravity(Gravity.BOTTOM);
            //获得window窗口的属性
            WindowManager.LayoutParams lp = window.getAttributes();
            //设置窗口宽度为充满全屏
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //设置窗口高度为包裹内容
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //将设置好的属性set回去
            window.setAttributes(lp);
            dialog.show();
        }
        ButterKnife.bind(this, view);

        /*拍照*/
        tvCamera.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                systemCamera();
            }
        });
        /*图像*/
        tvPhoto.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                openAlbum();
            }
        });
        /*取消*/
        tvClear.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 启动系统相机拍照
     */
    @SuppressLint("SimpleDateFormat")
    private void systemCamera() {
        String savePath = "";
        // 判断是否挂载了SD卡
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            // 存放照片的文件夹
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DECERP/";

            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(mActivity, "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show();
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = timeStamp + ".jpg";// 照片命名
        File outputImage = new File(savePath, fileName);

        String photoPath = savePath + fileName;// 该照片的绝对路径
        MyApplication.getInstance().setCapturePath(photoPath);

        Uri imageUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果是7.0android系统
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, outputImage.getAbsolutePath());
            imageUri = mActivity.getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mActivity.startActivityForResult(intent, SYSTEM_CAMERA);
    }

    /**
     * 打开系统相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

}
