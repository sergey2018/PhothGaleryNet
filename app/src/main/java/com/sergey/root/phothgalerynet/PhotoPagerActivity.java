package com.sergey.root.phothgalerynet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoPagerActivity extends AppCompatActivity {

    public static final String EXTRA_ID="extra_id";
    @BindView(R.id.photo_pager)
    ViewPager mPhotoPager;
    private ArrayList<Photo> mPhotos; //Cписок картинок

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pager);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra(EXTRA_ID);
        mPhotos = PhotoLab.getInstance().getPhotos();
        FragmentManager manager = getSupportFragmentManager();
        mPhotoPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                Photo photo = mPhotos.get(position);
                return DetalFragment.newInstance(photo.getId());
            }

            @Override
            public int getCount() {
                return mPhotos.size();
            }
        });
        for(int i = 0; i<mPhotos.size(); i++){
            if(mPhotos.get(i).getId().equals(id)){
                mPhotoPager.setCurrentItem(i);
                break;
            }
        }


    }


    public static Intent newIntent(Context context, String id){
        Intent intent = new Intent(context,PhotoPagerActivity.class);
        intent.putExtra(EXTRA_ID,id);
        return intent;
    }
}
