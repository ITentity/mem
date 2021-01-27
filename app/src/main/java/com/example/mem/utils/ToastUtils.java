package com.example.mem.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.mem.app.MyApplication;



/**
 * Created by Administrator on 2016/9/24.
 */
public class ToastUtils {
    static Context mContext = MyApplication.getInstance();

    private static Toast mToast;
    public static void show(String values) {

       // Toast.makeText(mContext, values, Toast.LENGTH_SHORT).show();
        runOnUIThread(() -> {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, values, Toast.LENGTH_SHORT);
            }
            try {
                Thread.sleep(200);
                mToast.setText(values);
                mToast.show();
            } catch (Exception e) {
                Log.i("ss", "showmereson: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public static void showLong(String values) {

        // Toast.makeText(mContext, values, Toast.LENGTH_SHORT).show();
        runOnUIThread(() -> {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, values, Toast.LENGTH_LONG);
            }
            try {
                mToast.setText(values);
                mToast.show();
            } catch (Exception e) {
                Log.i("ss", "showmereson: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    //5.0的提示框
    public static void showSn(Activity activity, String values){

        //去掉虚拟按键
        /*activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键栏
                | View.SYSTEM_UI_FLAG_IMMERSIVE //防止点击屏幕时,隐藏虚拟按键栏又弹了出来
        );*/
        runOnUIThread(() -> {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, values, Toast.LENGTH_SHORT);
            }
            mToast.setText(values);
            mToast.show();
        });
    }

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 判断当前线程是否是主线程
     *
     * @return true表示当前是在主线程中运行
     */
    public static boolean isUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void runOnUIThread(Runnable run) {
        if (isUIThread()) {
            run.run();
        } else {
            mHandler.post(run);
        }
    }
}
