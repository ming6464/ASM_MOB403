package com.fpoly.assigment_mob403.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;

import java.util.List;

public class StoryContentAdapter extends RecyclerView.Adapter<StoryContentAdapter.ViewHolder>{

    List<String> listUrl;

    public void SetData(List<String> list){
        listUrl = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_content,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GeneralFunc.LoadImageFromLink(listUrl.get(position),holder.img_content);
    }

    @Override
    public int getItemCount() {

        if(listUrl != null) return listUrl.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_content = itemView.findViewById(R.id.itemStoryContent_img_content);
        }
    }
}
