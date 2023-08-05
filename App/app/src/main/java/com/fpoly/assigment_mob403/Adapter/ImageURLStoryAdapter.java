package com.fpoly.assigment_mob403.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.assigment_mob403.R;

import java.util.ArrayList;
import java.util.List;

public class ImageURLStoryAdapter extends RecyclerView.Adapter<ImageURLStoryAdapter.MyViewHolder>{


    private List<String> urls;
    private EventImageURLStory event;


    public ImageURLStoryAdapter(EventImageURLStory event){
        this.event = event;
    }

    public interface EventImageURLStory{
        void Delete(int pos);
    }

    public void SetData(List<String> urls){
        this.urls = urls;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_story,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ed_url.setText(urls.get(holder.getAdapterPosition()));
        holder.imgbtn_delete.setOnClickListener(v -> event.Delete(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {

        if(urls == null) return 0;

        return urls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private EditText ed_url;
        private ImageButton imgbtn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ed_url = itemView.findViewById(R.id.itemImageStory_ed_imageLink);
            imgbtn_delete = itemView.findViewById(R.id.itemImageStory_imgbtn_delete);
        }
    }

}
