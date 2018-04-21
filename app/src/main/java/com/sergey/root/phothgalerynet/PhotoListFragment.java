package com.sergey.root.phothgalerynet;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



/**r
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment {


    @BindView(R.id.photo_list)
    RecyclerView mPhotoList;
    int page = 1; //номер страницы
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private PhotoAdapter mAdapter;
    Unbinder unbinder;
    private GridAutofitLayoutManager mGrid;

    public PhotoListFragment() {
        // Required empty public constructor
    }


    private void setAdapter(final ArrayList<Photo> photos){
        if(isAdded()){
            if(mAdapter == null){

                mAdapter = new PhotoAdapter(photos);
                mPhotoList.setAdapter(mAdapter);
            }
            else {
                mAdapter.addPhoto(photos);
                mAdapter.notifyDataSetChanged();
            }
            mPhotoList.addOnItemTouchListener(new ClickListener(getActivity(), new ClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) { // Обработка нажатия
                    String id = photos.get(position).getId();
                    Intent intent = PhotoPagerActivity.newIntent(getActivity(),id);
                    startActivity(intent);
                }
            }));
            mPhotoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) { // обработка прокрутки
                    super.onScrolled(recyclerView, dx, dy);
                    if(dy > 0){
                        visibleItemCount = mGrid.getChildCount();
                        totalItemCount = mGrid.getItemCount();
                        pastVisiblesItems = mGrid.findFirstCompletelyVisibleItemPosition();
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            //Toast.makeText(getActivity(),"Test",Toast.LENGTH_LONG).show();
                            page++;
                            new PhotoTask().execute(page);
                        }
                    }
                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mGrid = new GridAutofitLayoutManager(getActivity(),150);
        mPhotoList.setLayoutManager(mGrid);
        mPhotoList.addItemDecoration(new GridSpacesItemDecoration(3,false));
        if(PhotoLab.getInstance().getLoad()){
            new PhotoTask().execute(page);
        }
        else {
            setAdapter(PhotoLab.getInstance().getPhotos());
        }

       // setRetainInstance(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class PhotoTask extends AsyncTask<Integer,Void,ArrayList<Photo>>{

        @Override
        protected ArrayList<Photo> doInBackground(Integer... voids) {
            return new GetImageInternet().getDatas(voids[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Photo> data) {
            super.onPostExecute(data);
            if(data != null){
                PhotoLab.getInstance().addPhoto(data);
                setAdapter(data);
            }
        }
    }
}
