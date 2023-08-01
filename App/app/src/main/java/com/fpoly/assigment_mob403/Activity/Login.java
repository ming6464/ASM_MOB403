package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Result;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityLoginBinding;

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
    }

    public void ActionButtonLogin(View view) {
        String username = binding.actiLoginEdUserName.getText().toString().trim();
        String password = binding.actiLoginEdPass.getText().toString().trim();
        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Thông tin không được bỏ trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setPassword(password);
        user.setUsername(username);

        Call<Result> call =  ContainAPI.API().Login(user);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("Hello","123123123123123123");
                Toast.makeText(Login.this, response.body().getMes(), Toast.LENGTH_SHORT).show();
                if(response.body().isResult()){
                    ValuesSave.USER = response.body().getUser();
                    startActivity(new Intent(Login.this,MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }

    public void ActionButtonRegister(View view) {
    }

    public void ActionForgotPassword(View view) {
    }
}