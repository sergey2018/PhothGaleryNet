package com.sergey.root.phothgalerynet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "ID";
    @BindView(R.id.detals_image)
    ImageView mDetalsImage;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private Photo mPhoto;


    public DetalFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetalFragment newInstance(String id) {
        DetalFragment fragment = new DetalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String id = getArguments().getString(ARG_ID);
            mPhoto = PhotoLab.getInstance().getPhoto(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detal, container, false);
        unbinder = ButterKnife.bind(this, view);
        Picasso.get().load(mPhoto.getUrl()).fit().centerInside().into(mDetalsImage);
        getActivity().setTitle(mPhoto.getCaption());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
