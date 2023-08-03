package com.fpoly.assigment_mob403.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fpoly.assigment_mob403.Activity.DetailStory;
import com.fpoly.assigment_mob403.Adapter.StoryAdapter;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.databinding.FragmentReadStoryBinding;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadStoryFragment extends Fragment implements StoryAdapter.EventItemStory {

    public static final String KEYBUNDLE = "STORY";
    private List<Story> storyList;

    private List<Story> curStoryList;


    private StoryAdapter itemStoryAdapter;

    private Handler handler;

    private String TAG ="TAGReadStory";

    private FragmentReadStoryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReadStoryBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    private void HandleShow(boolean isShow){
        if(isShow){
            binding.fragReadStoryPgLoad.setVisibility(View.VISIBLE);
        }else{
            binding.fragReadStoryPgLoad.setVisibility(View.INVISIBLE);

        }
    }

    public static ReadStoryFragment newInstance() {
        ReadStoryFragment fragment = new ReadStoryFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HandleRecycleStory();
        AddAction();
    }

    private void AddAction(){
        binding.fragReadStoryEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String namePath = charSequence.toString().toLowerCase();
                Log.d(TAG, "namePath: " + namePath + "/ length " + namePath.length());
                if(namePath.length() == 0){
                    curStoryList = storyList;
                    itemStoryAdapter.SetData(curStoryList);
                    return;
                }

                List<Story> stories = new ArrayList<>();

                for(Story st : storyList){
                    String name = st.getName().toLowerCase();
                    if(name.contains(namePath)){
                        stories.add(st);
                    }else{
                        String normalizedText = removeDiacritics(name);
                        String normalizedZ = removeDiacritics(namePath);

                        if(normalizedText.contains(normalizedZ)){
                            stories.add(st);
                        }
                    }
                }
                curStoryList = stories;
                itemStoryAdapter.SetData(curStoryList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void HandleRecycleStory(){
        curStoryList = new ArrayList<>();
        storyList = new ArrayList<>();
        itemStoryAdapter = new StoryAdapter(this);
        itemStoryAdapter.SetData(curStoryList);
        binding.fragReadStoryRcStory.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.fragReadStoryRcStory.setAdapter(itemStoryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        HandleShow(true);
        handler = new Handler();
        LoadList();
    }

    private void LoadList(){

        try {
            HandleShow(true);
            Call<List<Story>> call = ContainAPI.STORY().GetAll();
            call.enqueue(new Callback<List<Story>>() {
                @Override
                public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                    storyList = response.body();
                    curStoryList = storyList;
                    itemStoryAdapter.SetData(curStoryList);
                    HandleShow(false);
                }

                @Override
                public void onFailure(Call<List<Story>> call, Throwable t) {
                    HandleShow(false);
                    Toast.makeText(getActivity(), "Load không thành công !", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            HandleShow(false);
        }


    }

    @Override
    public void OnClickItem(String _id) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getActivity(), DetailStory.class);
        intent.putExtra(KEYBUNDLE,_id);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    private static String removeDiacritics(String input) {
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
    }
}