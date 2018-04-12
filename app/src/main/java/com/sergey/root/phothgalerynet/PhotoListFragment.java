package com.sergey.root.phothgalerynet;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment {


    @BindView(R.id.photo_list)
    RecyclerView mPhotoList;
    Unbinder unbinder;

    public PhotoListFragment() {
        // Required empty public constructor
    }


    private void setAdapter(ArrayList<Data> datas){
        if(isAdded()){
            mPhotoList.setAdapter(new PhotoAdapter(datas));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPhotoList.setLayoutManager(new GridLayoutManager(getActivity(),3));
        setRetainInstance(true);
        new PhotoTask().execute();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class PhotoTask extends AsyncTask<Void,Void,ArrayList<Data>>{

        @Override
        protected ArrayList<Data> doInBackground(Void... voids) {
            return new GetImageInternet().getDatas();
        }

        @Override
        protected void onPostExecute(ArrayList<Data> data) {
            super.onPostExecute(data);
            if(data != null){
                setAdapter(data);
            }
        }
    }
}
