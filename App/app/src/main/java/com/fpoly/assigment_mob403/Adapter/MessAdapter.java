package com.fpoly.assigment_mob403.Adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.Comment;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.GeneralFunc;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.ViewHolder>{

    private List<Comment> list;

    private EventMess event;

    public MessAdapter(EventMess eventMess){
        event = eventMess;
    }

    public void SetData(List<Comment> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess,parent,false));
    }

    public interface EventMess{
        public void HoldMess(int pos);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment cm = list.get(position);
        String userId = cm.getUserID();
        Log.d("cTAG", userId);
        Call<User> call = ContainAPI.USER().GetElement(cm.getUserID());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                holder.btn_option1.setVisibility(View.GONE);
                holder.layout1.setVisibility(View.GONE);
                holder.layout.setVisibility(View.GONE);
                if(userId.equals(ValuesSave.USER.get_id())){
                    holder.tv_comment1.setText(cm.getContent());
                    holder.layout1.setVisibility(View.VISIBLE);
                    holder.tv_name1.setText(response.body().getUsername());
                    GeneralFunc.LoadImageFromLink(response.body().getAvatar(),holder.img_avatar1);
                    holder.btn_option1.setOnClickListener( v -> event.HoldMess(holder.getAdapterPosition()));
                    holder.btn_option1.setVisibility(View.VISIBLE);

                }else{
                    holder.layout.setVisibility(View.VISIBLE);
                    holder.tv_name.setText(response.body().getUsername());
                    holder.tv_comment.setText(cm.getContent());
                    GeneralFunc.LoadImageFromLink(response.body().getAvatar(),holder.img_avatar);
                }
                holder.layoutMes.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null) return list.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tv_comment;
        private ImageView img_avatar;

        private ConstraintLayout layout,layout1,layoutMes;

        private TextView tv_name1;
        private TextView tv_comment1;
        private ImageView img_avatar1;

        private Button btn_option1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comment = itemView.findViewById(R.id.itemMes_tv_comment);
            tv_name = itemView.findViewById(R.id.itemMes_tv_name);
            img_avatar = itemView.findViewById(R.id.itemMes_img_avatar);
            layout = itemView.findViewById(R.id.itemMes_layout);
            layout1 = itemView.findViewById(R.id.itemMes_layout_1);
            tv_name1 = itemView.findViewById(R.id.itemMes_tv_name1);
            tv_comment1 = itemView.findViewById(R.id.itemMes_tv_comment1);
            img_avatar1 = itemView.findViewById(R.id.itemMes_img_avatar1);
            layoutMes = itemView.findViewById(R.id.itemMes_layout_mes);
            btn_option1 = itemView.findViewById(R.id.itemMes_btn_option1);
        }
    }

}
