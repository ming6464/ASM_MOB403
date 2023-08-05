package com.fpoly.assigment_mob403.Activity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStory extends AppCompatActivity implements MessAdapter.EventMess {
    private ActivityDetailStoryBinding binding;

    private List<Comment> commentList;
    private MessAdapter messAdapter;

    private Story story;

    private boolean isEditMes;

    private int curPosCmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.actiDetailStoryLayoutNormal.setVisibility(View.VISIBLE);
        binding.actiDetailStoryLayoutMess.setVisibility(View.INVISIBLE);
        binding.actiDetailStoryLayoutImages.setVisibility(View.INVISIBLE);

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
        messAdapter = new MessAdapter(this);
        messAdapter.SetData(commentList);
        binding.actiDetailStoryRcMess.setLayoutManager(new LinearLayoutManager(this));
        binding.actiDetailStoryRcMess.setAdapter(messAdapter);

    }

    private void AddAction(){
        binding.actiDetaiStoryBtnBack.setOnClickListener(v -> finish());
        binding.actiDetailStoryBtnReadStory.setOnClickListener(v -> ActionOnclickReadStory());
        binding.actiDetailStoryBtnMess.setOnClickListener(v -> ActionOnClickMess());
        binding.actiDetaiStoryBtnBackNormal.setOnClickListener(v -> ActionOnClickBackNormal());
        binding.actiDetailStoryBtnSend.setOnClickListener(v -> ActionOnClickSend());
        binding.actiDetailStoryBtnBackNormal1.setOnClickListener(v -> ActionOnClickBackNormal1());
        binding.actiDetailStoryBtnCloseEditMess.setOnClickListener(v -> {


        });
    }

    private void HandleEditMess(boolean isShow){
        isEditMes = isShow;
        if(isShow){
            binding.actiDetailStoryBtnCloseEditMess.setVisibility(View.VISIBLE);
        }else{
            binding.actiDetailStoryBtnCloseEditMess.setVisibility(View.INVISIBLE);
        }

    }

    private void ActionOnClickBackNormal1() {
        binding.actiDetailStoryLayoutImages.setVisibility(View.INVISIBLE);
        binding.actiDetailStoryLayoutNormal.setVisibility(View.VISIBLE);
    }

    private void ActionOnClickSend() {
        HandleShow(true);
        String text = binding.actiDetailStoryEdComment.getText().toString();
        if(text.length() == 0) return;
        Comment comment = new Comment();
        comment.setStoryID(ValuesSave.CURRENT_ID_STORY);
        comment.setUserID(ValuesSave.USER.get_id());
        comment.setContent(text);

        Call<Comment> call;
        if(isEditMes){
            Comment comment1 = new Comment();
            Comment curCmt = commentList.get(curPosCmt);
            String text1 = binding.actiDetailStoryEdComment.getText().toString().trim();
            if(text1 == null || text1.trim().isEmpty()){
                HandleEditMess(false);
                return;
            }
            comment1.setContent(text1);
            comment1.setStoryID(curCmt.getStoryID());
            comment1.setUserID(curCmt.getUserID());
            comment1.setTime(Instant.now().toEpochMilli());
            call = ContainAPI.COMMENT().UpdateElement(curCmt.get_id(),comment1);
        }else{
            call = ContainAPI.COMMENT().CreateElement(comment);
        }


        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.body() == null) return;
                if(isEditMes){
                    Comment cmt = response.body();
                    cmt.setContent(binding.actiDetailStoryEdComment.getText().toString());
                    commentList.set(curPosCmt,cmt);
                    messAdapter.notifyItemChanged(curPosCmt);
                    HandleEditMess(false);
                }else{
                    commentList.add(response.body());
                    messAdapter.notifyItemInserted(commentList.size() - 1);
                }
                binding.actiDetailStoryEdComment.setText("");
                HandleShow(false);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                HandleEditMess(false);
                HandleShow(false);
            }
        });
    }

    private void ActionOnclickReadStory(){
        binding.actiDetailStoryLayoutImages.setVisibility(View.VISIBLE);
        binding.actiDetailStoryLayoutNormal.setVisibility(View.INVISIBLE);
    }

    private void ActionOnClickMess(){
        HandleEditMess(false);
        binding.actiDetailStoryLayoutMess.setVisibility(View.VISIBLE);
        binding.actiDetailStoryLayoutNormal.setVisibility(View.INVISIBLE);
        binding.actiDetailStoryEdComment.requestFocus();
        LoadMess();
    }

    private void ActionOnClickBackNormal(){
        HandleEditMess(false);
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

    @Override
    public void HoldMess(int pos) {

        Log.d("TAG", "onHover: show1");


        Comment comment = commentList.get(pos);

        Runnable action1 = () -> {
            curPosCmt = pos;
            HandleEditMess(true);
            binding.actiDetailStoryEdComment.setText(comment.getContent());
            binding.actiDetailStoryEdComment.requestFocus();
        };
        Runnable act2 = () -> {

            Runnable act_1 = () -> {
                HandleShow(true);
                ContainAPI.COMMENT().DeleteElement(comment.get_id()).enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        commentList.remove(pos);
                        messAdapter.notifyItemRemoved(pos);
                        HandleShow(false);
                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        HandleShow(false);
                    }
                });

            };

            GeneralFunc.ShowTwoOptionDsialog(DetailStory.this,"Xoá",act_1,"Huỷ",null,"Xoá bình luận","Xác nhận xoá");
        };
        Runnable act3 = () -> {};


        GeneralFunc.ShowThreeOptionsDialog(this,"","","Sửa",action1,"Xoá",act2,"Huỷ",act3);
    }
}