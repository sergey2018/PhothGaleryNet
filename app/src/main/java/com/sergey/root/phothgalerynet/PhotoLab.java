package com.sergey.root.phothgalerynet;

import java.util.ArrayList;

public class PhotoLab {
    private static PhotoLab mOutPhoto;
    private ArrayList<Photo>mPhotos; // список фотографий

    public static PhotoLab getInstance() {
        if(mOutPhoto == null){
            mOutPhoto = new PhotoLab();
        }
        return mOutPhoto;
    }
    public boolean getLoad(){
        return mPhotos.size()==0;
    }

    private PhotoLab() {
        mPhotos = new ArrayList<>();
    }
    public void addPhoto(ArrayList<Photo> photos){ // Добавление новых фотографий
        for (int i = 0; i<photos.size(); i++){
            mPhotos.add(photos.get(i));
        }
    }

    public Photo getPhoto(String id){ //Вывод фотографии по идентификатору
        for(int i= 0; i<mPhotos.size(); i++){
            if(mPhotos.get(i).getId().equals(id)){
                return mPhotos.get(i);
            }
        }
        return null;
    }

    public ArrayList<Photo> getPhotos() {
        return mPhotos;
    }
}
