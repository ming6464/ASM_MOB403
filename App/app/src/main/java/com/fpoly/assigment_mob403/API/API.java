package com.fpoly.assigment_mob403.API;

import com.fpoly.assigment_mob403.DTO.Result;
import com.fpoly.assigment_mob403.DTO.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    public static final String Router = "api";

    @POST("/api/login")
    Call<Result> Login(@Body User user);

    @POST(Router + "/register")
    Call<Result> Register(@Body User user);
}
