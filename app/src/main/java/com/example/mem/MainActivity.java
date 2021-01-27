package com.example.mem;

import android.os.Bundle;

import com.example.mem.databinding.ActivityMainBinding;
import com.example.mem.fragment.FragmentShouye;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.gyf.barlibrary.ImmersionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.SparseArray;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SparseArray<Fragment> fragments = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        fragments.put(R.id.bottomhome, new FragmentShouye());
        fragments.put(R.id.bottomorder, new FragmentShouye());
        fragments.put(R.id.bottomme, new FragmentShouye());

        binding.bottomBar.setOnTabSelectListener(tabId -> {
            Fragment targetFragment = fragments.get(tabId);
            setFragment(targetFragment);
        });
    }

    private void setFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded() && fragment.isVisible()) {
            return;
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.replace(R.id.content, fragment);
        }
        transaction.commit();
    }
}