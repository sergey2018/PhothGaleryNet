package com.sergey.root.phothgalerynet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHoleder>{

    private ArrayList<Photo> mDats;

    public PhotoAdapter(ArrayList<Photo> dats) {
        mDats = dats;

    }

    @NonNull
    @Override
    public PhotoHoleder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images,null);
        return new PhotoHoleder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHoleder holder, int position) {
        Photo photo = mDats.get(position);
        holder.setPhoto(photo);
    }

    @Override
    public int getItemCount() {
        return mDats.size();
    }
    public void addPhoto(ArrayList<Photo> data){ //Добавление новых фото
        for(int i = 0; i<data.size(); i++){
            mDats.add(data.get(i));
        }

    }

    public class PhotoHoleder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public PhotoHoleder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
        public void setPhoto(Photo photo){
            Picasso.get().load(photo.getUrl()).into(mImageView);
        }
    }
}
