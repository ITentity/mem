package com.example.mem.app;

import android.app.Application;

/**
 * Created by zhaozx on 2017/3/24.
 */

public class MyApplication extends Application {

    private static MyApplication app;


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
}