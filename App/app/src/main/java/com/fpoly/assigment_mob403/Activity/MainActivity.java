package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

import com.fpoly.assigment_mob403.Adapter.AdapterFagment;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.Fragment.ReadStoryFragment;
import com.fpoly.assigment_mob403.Fragment.UserFragment;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TAGHELLO";
    private ActivityMainBinding binding;
    private AdapterFagment adapter_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Init();
    }


    private void Init() {
        AddViewPager();
        AddTabLayout();
    }

    private void AddTabLayout() {
        new TabLayoutMediator(binding.actiMainTab, binding.actiMainViewPg2, (tab, position) -> {
            if(position == 0){
                tab.setIcon(R.drawable.auto_stories);
            }else
                tab.setIcon(R.drawable.account_circle);
        }).attach();
    }

    private void AddViewPager() {
        adapter_fragment = new AdapterFagment(this,
                new Fragment[]{ReadStoryFragment.newInstance(), UserFragment.newInstance()});
        binding.actiMainViewPg2.setAdapter(adapter_fragment);
    }
}