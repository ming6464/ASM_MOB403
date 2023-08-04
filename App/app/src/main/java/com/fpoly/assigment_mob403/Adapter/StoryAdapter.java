package com.fpoly.assigment_mob403.Adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.assigment_mob403.DTO.Story;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>{

    private List<Story> list;
    private EventItemStory event;

    public StoryAdapter(EventItemStory event){
        this.event = event;
    }

    public void SetData(List<Story> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public interface EventItemStory{
        public void OnClickItem(String _id);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = list.get(position);
        holder.tv_name.setText(story.getName());
        holder.btn.setOnClickListener(v -> event.OnClickItem(story.get_id()));
        GeneralFunc.LoadImageFromLink(story.getBackground(),holder.img_avatar);
    }

    @Override
    public int getItemCount() {
        if(list != null) return list.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_avatar;
        private TextView tv_name;
        private Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.itemStory_img_avatar);
            tv_name = itemView.findViewById(R.id.itemStory_tv_name);
            btn = itemView.findViewById(R.id.itemStory_btn);
        }
    }

}
