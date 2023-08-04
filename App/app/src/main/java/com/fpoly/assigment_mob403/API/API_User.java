package com.fpoly.assigment_mob403.API;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API_User {
    public static final String Router = "api/user";

    @GET(Router + ContainAPI.GetAll)
    Call<List<User>> GetAll();

    @GET(Router + ContainAPI.GetElement)
    Call<User> GetElement(@Path("id") String id);

    @POST(Router + ContainAPI.CreateElement)
    Call<User> CreateElement(@Body User user);

    @PUT(Router + ContainAPI.UpdateElement)
    Call<User> UpdateElement(@Path("id") String id, @Body User user);

    @PUT(Router + ContainAPI.DeleteElement)
    Call<User> DeleteElement(@Path("id") String id);

    @PUT(Router + ContainAPI.DeleteAll)
    Call<User> DeleteAll();
}
