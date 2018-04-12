package com.sergey.root.phothgalerynet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHoleder>{

    private ArrayList<Data> mDats;

    public PhotoAdapter(ArrayList<Data> dats) {
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
        Data data = mDats.get(position);
        holder.setPhoto(data);
    }

    @Override
    public int getItemCount() {
        return mDats.size();
    }

    public class PhotoHoleder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public PhotoHoleder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
        public void setPhoto(Data data){
            Picasso.get().load(data.getUrl()).into(mImageView);

        }
    }
}
