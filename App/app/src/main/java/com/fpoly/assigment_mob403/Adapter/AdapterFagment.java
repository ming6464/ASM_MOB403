package com.fpoly.assigment_mob403.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterFagment extends FragmentStateAdapter {
    private Fragment[] listFragment;
    public AdapterFagment(@NonNull FragmentActivity fragmentActivity, Fragment[] listFragment) {
        super(fragmentActivity);
        this.listFragment = listFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment[position];
    }

    @Override
    public int getItemCount() {
        return listFragment.length;
    }
}
