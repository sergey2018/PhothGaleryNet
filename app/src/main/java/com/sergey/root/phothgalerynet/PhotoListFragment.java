package com.sergey.root.phothgalerynet;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    int page = 1; //номер страницы
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private PhotoAdapter mAdapter;
    Unbinder unbinder;
    private GridLayoutManager mGrid;

    public PhotoListFragment() {
        // Required empty public constructor
    }


    private void setAdapter(final ArrayList<Data> datas){
        if(isAdded()){
            if(mAdapter == null){
                mAdapter = new PhotoAdapter(datas);
                mPhotoList.setAdapter(mAdapter);
            }
            else {
                mAdapter.addPhoto(datas);
                mAdapter.notifyDataSetChanged();
            }
            mPhotoList.addOnItemTouchListener(new ClickListener(getActivity(), new ClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String url = datas.get(position).getUrl();
                    Intent intent = DetalActivity.newIntent(getActivity(),url);
                    startActivity(intent);
                }
            }));
            mPhotoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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
        mGrid = new GridLayoutManager(getActivity(),3);
        mPhotoList.setLayoutManager(mGrid);
        setRetainInstance(true);
        new PhotoTask().execute(page);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class PhotoTask extends AsyncTask<Integer,Void,ArrayList<Data>>{

        @Override
        protected ArrayList<Data> doInBackground(Integer... voids) {
            return new GetImageInternet().getDatas(voids[0]);
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
