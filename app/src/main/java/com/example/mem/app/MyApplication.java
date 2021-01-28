package com.example.mem.app;

import android.app.Application;

/**
 * Created by zhaozx on 2017/3/24.
 */

public class MyApplication extends Application {

    private static MyApplication app;
    /*记录拍照添加时，保存的路径*/
    private String capturePath;

    public static MyApplication getInstance() {
        if (app == null) {
            app = new MyApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public String getCapturePath() {
        return capturePath;
    }

    public void setCapturePath(String capturePath) {
        this.capturePath = capturePath;
    }
}