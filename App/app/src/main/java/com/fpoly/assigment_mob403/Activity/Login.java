package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Result;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityLoginBinding;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        if(ValuesSave.FirtOpen){
            binding.actiLoginTvWellcomeScreen.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.actiLoginTvWellcomeScreen.setVisibility(View.INVISIBLE);
                    ValuesSave.FirtOpen = false;
                }
            },1800);

        }else{
            binding.actiLoginTvWellcomeScreen.setVisibility(View.INVISIBLE);
        }

        //LogIn("giamin","121");

    }

    public void ActionButtonLogin(View view) {

        String username = binding.actiLoginEdUserName.getText().toString().trim();
        String password = binding.actiLoginEdPass.getText().toString().trim();
        LogIn(username,password);
    }


    private void LogIn(String userName,String pass){
        try {
            HandleShow(true);
            String username = userName.trim();
            String password = pass.trim();
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Thông tin không được bỏ trống !", Toast.LENGTH_SHORT).show();
                HandleShow(false);
                return;
            }

            User user = new User();
            user.setPassword(password);
            user.setUsername(username);

            Call<Result> call =  ContainAPI.API().Login(user);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Toast.makeText(Login.this, response.body().getMes(), Toast.LENGTH_SHORT).show();
                    if(response.body().isResult()){
                        ValuesSave.USER = response.body().getUser();
                        startActivity(new Intent(Login.this,MainActivity.class));
                    }
                    HandleShow(false);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    HandleShow(false);
                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            HandleShow(false);
        }
    }

    private void HandleShow(boolean isShow){
        if(isShow){
            binding.actiLoginPgLoad.setVisibility(View.VISIBLE);
        }else{
            binding.actiLoginPgLoad.setVisibility(View.INVISIBLE);
        }
    }

    public void ActionButtonRegister(View view) {
        startActivity(new Intent(this, Register.class));
    }

    public void ActionForgotPassword(View view) {
        Toast.makeText(this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
    }
}