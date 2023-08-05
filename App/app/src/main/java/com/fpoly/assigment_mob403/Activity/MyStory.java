package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fpoly.assigment_mob403.Adapter.StoryAdapter;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.databinding.ActivityMyStoryBinding;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStory extends AppCompatActivity implements StoryAdapter.EventItemStory {

    List<Story> curStoryList;
    List<Story> storyList;

    public static final String KEYBUNDLE = "KEYMYSTORY";
    private StoryAdapter itemStoryAdapter;
    private ActivityMyStoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemStoryAdapter = new StoryAdapter(this);
        binding.actiMyStoryRcList.setAdapter(itemStoryAdapter);
        binding.actiMyStoryRcList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        AddAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.actiMyStoryEdSearch.setText("");
        ReLoadData();
    }

    private void ReLoadData() {
        HandleLoad(true);

        curStoryList = new ArrayList<>();
        storyList = new ArrayList<>();

        Call<List<Story>> call = ContainAPI.STORY().GetAll();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                storyList = response.body();
                curStoryList = response.body();
                itemStoryAdapter.SetData(curStoryList,true);
                HandleLoad(false);
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                HandleLoad(false);
            }
        });

    }

    private void AddAction() {
        binding.actiMyStoryBtnBack.setOnClickListener(v -> finish());
        SearchAction();
        binding.actiMyStoryBtnAddStory.setOnClickListener(v -> ActionAddStoryButton());
    }

    private void ActionAddStoryButton() {
        startActivity(new Intent(this, EditStory.class));
    }

    private void SearchAction() {
        binding.actiMyStoryEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String namePath = charSequence.toString().toLowerCase();
                LoadByName(namePath);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void LoadByName(String namePath){
        if(namePath.length() == 0){
            curStoryList = storyList;
            itemStoryAdapter.SetData(curStoryList,true);
            return;
        }

        List<Story> stories = new ArrayList<>();

        for(Story st : storyList){
            String name = st.getName().toLowerCase();
            if(name.contains(namePath)){
                stories.add(st);
            }else{
                String normalizedText = GeneralFunc.removeDiacritics(name);
                String normalizedZ = GeneralFunc.removeDiacritics(namePath);

                if(normalizedText.contains(normalizedZ)){
                    stories.add(st);
                }
            }
        }
        curStoryList = stories;
        itemStoryAdapter.SetData(curStoryList,true);
    }

    private void HandleLoad(boolean isShow){
        if(isShow){
            binding.actiMyStoryPgLoad.setVisibility(View.VISIBLE);
        }else binding.actiMyStoryPgLoad.setVisibility(View.INVISIBLE);
    }

    @Override
    public void OnClickItem(String _id) {
        Intent intent = new Intent(this, EditStory.class);
        intent.putExtra(KEYBUNDLE,_id);
        startActivity(intent);
    }

    @Override
    public void DeleteItem(String _id) {
        Runnable runnable = () -> {
            HandleLoad(true);
            ContainAPI.STORY().DeleteElement(_id).enqueue(new Callback<Story>() {
                @Override
                public void onResponse(Call<Story> call, Response<Story> response) {
                    Toast.makeText(MyStory.this, "Xoá thành công", Toast.LENGTH_SHORT).show();

                    HandleLoad(false);

                    storyList.removeIf(story -> story.get_id().equals(_id));

                    curStoryList.removeIf(v -> v.get_id().equals(_id));

                    itemStoryAdapter.SetData(curStoryList,true);
                }

                @Override
                public void onFailure(Call<Story> call, Throwable t) {
                    Log.d("Hello", t.toString());
                    Toast.makeText(MyStory.this, "Xoá thất bại onFailure", Toast.LENGTH_SHORT).show();
                    HandleLoad(false);
                }
            });
        };
        GeneralFunc.ShowTwoOptionDsialog(this,"Yes",runnable,"No",() -> {},"Xoá truyện","Xác nhận xoá");
    }
}