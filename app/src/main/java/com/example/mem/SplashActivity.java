package com.example.mem;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mem.databinding.ActivitySplashBinding;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by zhaozx on 2021/1/27.
 */
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        binding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        // 动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_to_normal);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.imgWelcome.startAnimation(animation);
    }
}
