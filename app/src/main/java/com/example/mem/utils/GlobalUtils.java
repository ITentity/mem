package com.example.mem.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.mem.app.MyApplication;

/**
 * Created by zhaozx on 2021/2/1.
 */
public class GlobalUtils {

    /**
     * 打开软键盘
     *
     * @param view
     */
    public static void showSoftInput(View view) {
        InputMethodManager methodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (methodManager != null) {
            view.requestFocus();
            methodManager.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param view
     */
    public static void hideSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * 获取屏幕真实高度
     */
    public static int getRealSizeHeight() {
        WindowManager windowManager = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        return mRealSizeHeight;
    }


    /**
     * 获取屏幕真实宽
     */
    public static int getRealSizeWidth() {
        WindowManager windowManager = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeWidth;//手机屏幕真实高度
        mRealSizeWidth = outPoint.x;
        return mRealSizeWidth;
    }
}
