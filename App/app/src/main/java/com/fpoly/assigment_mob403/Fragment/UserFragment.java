package com.fpoly.assigment_mob403.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.assigment_mob403.Activity.EditUser;
import com.fpoly.assigment_mob403.Activity.Login;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {
    private FragmentUserBinding binding;
    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddAction();
    }

    private void AddAction() {
        binding.fragUserBtnEdit.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditUser.class)));
        binding.fragUserBtnLogOut.setOnClickListener(v -> LogOut());
    }

    private void LogOut() {
        startActivity(new Intent(getActivity(), Login.class));
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }

    private void LoadData(){
        GeneralFunc.LoadImageFromLink(ValuesSave.USER.getAvatar(),binding.fragUserImgAvatar);
        binding.fragUserTvName.setText(ValuesSave.USER.getFullName());
        binding.fragUserTvEmail.setText(ValuesSave.USER.getEmail());
    }
}