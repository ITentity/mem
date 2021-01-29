package com.example.mem.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.mem.R;
import com.squareup.picasso.Picasso;

public class UIUtils {
    // 设置图片
    public static void setGoodImgPath(String path, ImageView productImg) {
        try {
            if (TextUtils.isEmpty(path)) {
                Picasso.with(productImg.getContext())
                        .load(R.mipmap.ic_add_show)
                        .placeholder(R.mipmap.ic_add_show)
                        .error(R.mipmap.ic_add_show)
                        .resize(100, 100)
                        .config(Bitmap.Config.RGB_565)
                        .into(productImg);
            } else {
                Picasso.with(productImg.getContext())
                        .load("file://" + path)
                        .placeholder(R.mipmap.ic_add_show)
                        .error(R.mipmap.ic_add_show)
                        .resize(100, 100)
                        .config(Bitmap.Config.RGB_565)
                        .into(productImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
