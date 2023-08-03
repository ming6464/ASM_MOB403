package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Result;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.databinding.ActivityRegisterBinding;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void HandleShow(boolean isShow){
        if(isShow){
            binding.actiRegisterPgLoad.setVisibility(View.VISIBLE);
        }else{
            binding.actiRegisterPgLoad.setVisibility(View.INVISIBLE);

        }
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    public void ActionButtonRegister(View view) {

        try {
            HandleShow(true);

            String fullName = binding.actiRegisterEdFullName.getText().toString().trim();
            String userName = binding.actiRegisterEdUserName.getText().toString().trim();
            String email = binding.actiRegisterEdEmail.getText().toString().trim();
            String avatar = binding.actiRegisterEdAvatar.getText().toString().trim();
            String password = binding.actiRegisterEdPass.getText().toString().trim();

            if(fullName.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Thông tin không được bỏ trống !", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!isValidEmail(email)){
                Toast.makeText(this, "Email sai định dạng !", Toast.LENGTH_SHORT).show();
            }

            if(avatar.isEmpty()){
                avatar = "https://st.nettruyenmax.com/data/comics/176/van-co-chi-ton.jpg";
            }

            User user = new User(userName,password,email,fullName,avatar);

            Call<Result>  call = ContainAPI.API().Register(user);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    HandleShow(false);
                    Result result = response.body();
                    Toast.makeText(Register.this, result.getMes(), Toast.LENGTH_SHORT).show();
                    if(result.isResult()){
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    HandleShow(false);

                    Toast.makeText(Register.this, "Lỗi Internet!", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            HandleShow(false);
        }
    }

    public void ActionButtonBack(View view) {
        finish();
    }
}