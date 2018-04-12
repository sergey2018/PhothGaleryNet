package com.sergey.root.phothgalerynet;

import android.support.v4.app.Fragment;

public class PhotoListActivity extends MainActivity {
    @Override
    public Fragment NewFragment() {
        return new PhotoListFragment();
    }
}
