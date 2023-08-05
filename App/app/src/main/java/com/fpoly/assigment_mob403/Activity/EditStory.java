package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.assigment_mob403.Adapter.ImageURLStoryAdapter;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityEditStoryBinding;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStory extends AppCompatActivity implements ImageURLStoryAdapter.EventImageURLStory {
    private ActivityEditStoryBinding binding;
    private List<String> urls;
    private ImageURLStoryAdapter imageUrlAdapter;
    private String _id;

    private Story story;

    private boolean IsEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        _id = getIntent().getStringExtra(MyStory.KEYBUNDLE);

        if(_id != null){
            IsEdit = true;
            binding.actiEditStoryImgAvatar.setVisibility(View.VISIBLE);
        }

        AddAction();
        AddRc();
    }

    private void AddRc() {
        imageUrlAdapter = new ImageURLStoryAdapter(this);

        if(IsEdit){
            HandleLoad(true);
            Call<Story> call = ContainAPI.STORY().GetElement(_id);
            call.enqueue(new Callback<Story>() {
                @Override
                public void onResponse(Call<Story> call, Response<Story> response) {
                    story = response.body();
                    GeneralFunc.LoadImageFromLink(story.getBackground(),binding.actiEditStoryImgAvatar);
                    binding.actiEditStoryEdAvatar.setText(story.getBackground());
                    binding.actiEditStoryEdDescribe.setText(story.getDescribe());
                    binding.actiEditStoryEdNameStory.setText(story.getName());
                    urls = story.getImages();
                    imageUrlAdapter.SetData(urls);
                    binding.actiEditStoryRcListImage.setLayoutManager(new LinearLayoutManager(EditStory.this));
                    binding.actiEditStoryRcListImage.setAdapter(imageUrlAdapter);
                    HandleLoad(false);
                }

                @Override
                public void onFailure(Call<Story> call, Throwable t) {
                    HandleLoad(false);
                }
            });
        }else{
            story = new Story();
            urls = new ArrayList<>();
            imageUrlAdapter.SetData(urls);
            binding.actiEditStoryRcListImage.setLayoutManager(new LinearLayoutManager(EditStory.this));
            binding.actiEditStoryRcListImage.setAdapter(imageUrlAdapter);
        }
    }

    private void AddAction() {
        binding.actiEditStoryBtnBack.setOnClickListener(v -> finish());
        binding.actiEditStoryBtnSave.setOnClickListener(v -> ActionButtonSave());
        binding.actiEditStoryBtnAddImage.setOnClickListener(v -> ActionAddImage());
    }

    private void ActionAddImage() {
        urls.add("");
        imageUrlAdapter.notifyItemInserted(urls.size() - 1);
    }

    private void ActionButtonSave() {

        String name = binding.actiEditStoryEdNameStory.getText().toString();
        String background = binding.actiEditStoryEdAvatar.getText().toString();
        String des = binding.actiEditStoryEdDescribe.getText().toString();

        if(name.trim().isEmpty() || des.trim().isEmpty() || background.trim().isEmpty()) {
            Toast.makeText(this, "Thông tin bị thiếu !", Toast.LENGTH_SHORT).show();
            return;
        }

        HandleLoad(true);
        story.set_id(null);
        story.setBackground(background);
        story.setDescribe(des);
        story.setName(name);

        if(!IsEdit){
            story.setAuthor(ValuesSave.USER.getFullName());
            story.setTimeRelease(Instant.now().toEpochMilli());
        }

        List<String> Images = new ArrayList<>();

        for(int i = 0; i < urls.size(); i ++){
            View itemView = Objects.requireNonNull(binding.actiEditStoryRcListImage.findViewHolderForAdapterPosition(i)).itemView;
            EditText editText = itemView.findViewById(R.id.itemImageStory_ed_imageLink);
            String renderedUrl = editText.getText().toString();
            Images.add(renderedUrl);
        }
        story.setImages(Images);
        Call<Story> call;

        if(IsEdit){
            call = ContainAPI.STORY().UpdateElement(_id,story);
        }else{
            call = ContainAPI.STORY().CreateElement(story);
        }

        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {

                if(IsEdit){
                    Toast.makeText(EditStory.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(EditStory.this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();

                }

                finish();
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                Toast.makeText(EditStory.this, "Thất bại", Toast.LENGTH_SHORT).show();

                HandleLoad(false);
            }
        });
    }
    @Override
    public void Delete(int pos) {
        Toast.makeText(this, pos + "", Toast.LENGTH_SHORT).show();
        urls.remove(pos);
        imageUrlAdapter.notifyItemRemoved(pos);
    }

    private void HandleLoad(boolean isShow){

        if(isShow){
            binding.actiEditStoryPgLoad.setVisibility(View.VISIBLE);

        }else{
            binding.actiEditStoryPgLoad.setVisibility(View.INVISIBLE);
        }

    }
}