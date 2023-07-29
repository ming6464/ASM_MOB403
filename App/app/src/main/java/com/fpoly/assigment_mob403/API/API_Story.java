package com.fpoly.assigment_mob403.API;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API_Story {
    public static final String Router = "api/story";

    @GET(Router + ContainAPI.GetByName)
    Call<List<Story>> GetElementByName(@Path("ten") String ten);

    @GET(Router + ContainAPI.GetAll)
    Call<List<Story>> GetAll();

    @GET(Router + ContainAPI.GetElement)
    Call<Story> GetElement(@Path("id") String id);

    @POST(Router + ContainAPI.CreateElement)
    Call<Story> CreateElement(@Body Story Story);

    @PUT(Router + ContainAPI.UpdateElement)
    Call<Story> UpdateElement(@Path("id") String id,@Body Story Story);

    @PUT(Router + ContainAPI.DeleteElement)
    Call<Story> DeleteElement(@Path("id") String id);

    @PUT(Router + ContainAPI.DeleteAll)
    Call<Story> DeleteAll();
}
