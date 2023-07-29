package com.fpoly.assigment_mob403.API;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API_Comment {
    public static final String Router = "api/comment";

    @GET(Router + ContainAPI.GetAll)
    Call<List<Comment>> GetAll();

    @GET(Router + ContainAPI.GetElement)
    Call<Comment> GetElement(@Path("id") String id);

    @GET(Router + "/getByStoryID/{id}")
    Call<List<Comment>> GetElementByStoryID(@Path("id") String id);

    @POST(Router + ContainAPI.CreateElement)
    Call<Comment> CreateElement(@Body Comment comment);

    @PUT(Router + ContainAPI.UpdateElement)
    Call<Comment> UpdateElement(@Path("id") String id);

    @PUT(Router + ContainAPI.DeleteElement)
    Call<Comment> DeleteElement(@Path("id") String id);

    @PUT(Router + ContainAPI.DeleteAll)
    Call<Comment> DeleteAll();

}
