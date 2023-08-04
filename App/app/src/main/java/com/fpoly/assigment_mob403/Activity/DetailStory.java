package com.fpoly.assigment_mob403.Activity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fpoly.assigment_mob403.Adapter.MessAdapter;
import com.fpoly.assigment_mob403.Adapter.StoryContentAdapter;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Comment;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.Fragment.ReadStoryFragment;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityDetailStoryBinding;
import com.fpoly.assigment_mob403.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStory extends AppCompatActivity {
    private ActivityDetailStoryBinding binding;

    private List<Comment> commentList;
    private MessAdapter messAdapter;

    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HandleShow(true);

        String _id = getIntent().getStringExtra(ReadStoryFragment.KEYBUNDLE);

        Call<Story> call = ContainAPI.STORY().GetElement(_id);


        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                story = response.body();
                Init();
                HandleShow(false);
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {

            }
        });


        AddAction();
    }

    private void HandleShow(boolean isShow){
        if(isShow){
            binding.actiDetailStoryPgLoad.setVisibility(View.VISIBLE);
        }else{
            binding.actiDetailStoryPgLoad.setVisibility(View.INVISIBLE);

        }
    }

    private void Init(){
        ValuesSave.CURRENT_ID_STORY = story.get_id();
        GeneralFunc.LoadImageFromLink(story.getBackground(),binding.actiDetailStoryImgAvatar);
        binding.actiDetailStoryTvName.setText(story.getName());
        binding.actiDetailStoryTvAuthor.setText(story.getAuthor());
        binding.actiDetailStoryTvTimeRelase.setText(GeneralFunc.ConvertToStringDate(story.getTimeRelease()));
        binding.actiDetailStoryTvDescribe.setText(story.getDescribe());
        binding.actiDetailStoryRcImages.setLayoutManager(new LinearLayoutManager(this));
        StoryContentAdapter storyContentAdapter = new StoryContentAdapter();
        storyContentAdapter.SetData(story.getImages());
        binding.actiDetailStoryRcImages.setAdapter(storyContentAdapter);

        //mess
        commentList = new ArrayList<>();
        messAdapter = new MessAdapter();
        messAdapter.SetData(commentList);
        binding.actiDetailStoryRcMess.setLayoutManager(new LinearLayoutManager(this));
        binding.actiDetailStoryRcMess.setAdapter(messAdapter);

    }

    private void AddAction(){
        binding.actiDetaiStoryBtnBack.setOnClickListener(v -> finish());
        binding.actiDetailStoryBtnReadStory.setOnClickListener(v -> ActionOnclickReadStory());
        binding.actiDetailStoryBtnDescribe.setOnClickListener(v -> ActionOnClickDescribe());
        binding.actiDetailStoryBtnMess.setOnClickListener(v -> ActionOnClickMess());
        binding.actiDetaiStoryBtnBackToNormal.setOnClickListener(v -> ActionOnClickBackToNormal());
        binding.actiDetailStoryBtnSend.setOnClickListener(v -> ActionOnClickSend());
    }

    private void ActionOnClickSend() {
        String text = binding.actiDetailStoryEdComment.getText().toString();
        if(text.length() == 0) return;
        Comment comment = new Comment();
        comment.setStoryID(ValuesSave.CURRENT_ID_STORY);
        comment.setUserID(ValuesSave.USER.get_id());
        comment.setContent(text);
        Call<Comment> call = ContainAPI.COMMENT().CreateElement(comment);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.body() == null) return;
                binding.actiDetailStoryEdComment.setText("");
                commentList.add(response.body());
                messAdapter.notifyItemInserted(commentList.size() - 1);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

    private void ActionOnclickReadStory(){
        binding.actiDetailStoryRcImages.setVisibility(View.VISIBLE);
        binding.actiDetailStoryTvDescribe.setVisibility(View.INVISIBLE);
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnReadStory,"#D9D9D9");
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnDescribe,"#FFFFFF");
    }

    private void ActionOnClickDescribe(){
        binding.actiDetailStoryTvDescribe.setVisibility(View.VISIBLE);
        binding.actiDetailStoryRcImages.setVisibility(View.INVISIBLE);
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnDescribe,"#D9D9D9");
        GeneralFunc.ChangeColorButton(binding.actiDetailStoryBtnReadStory,"#FFFFFF");
    }

    private void ActionOnClickMess(){
        binding.actiDetailStoryLayoutMess.setVisibility(View.VISIBLE);
        binding.actiDetailStoryLayoutNormal.setVisibility(View.INVISIBLE);
        LoadMess();
    }

    private void ActionOnClickBackToNormal(){
        binding.actiDetailStoryLayoutMess.setVisibility(View.INVISIBLE);
        binding.actiDetailStoryLayoutNormal.setVisibility(View.VISIBLE);
    }

    private void LoadMess(){

        try {
            HandleShow(true);
            Call<List<Comment>> call = ContainAPI.COMMENT().GetElementByStoryID(ValuesSave.CURRENT_ID_STORY);
            call.enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    boolean check = false;

                    if(response.body() == null){
                        commentList = new ArrayList<>();
                        messAdapter.SetData(commentList);
                        return;
                    }

                    if(commentList.size() != response.body().size()) check = true;
                    else{

                    }

                    if(check){
                        commentList = response.body();
                        messAdapter.SetData(commentList);
                    }
                    HandleShow(false);

                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {
                    HandleShow(false);

                }
            });
        }catch (Exception e){
            HandleShow(false);
        }


    }
}