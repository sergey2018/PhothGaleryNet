package com.sergey.root.phothgalerynet;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class DetalActivity extends MainActivity {

    public static final String EXTRA_URL="extra_url";
    public static Intent newIntent(Context context, String url){
        Intent intent = new Intent(context,DetalActivity.class);
        intent.putExtra(EXTRA_URL,url);
        return intent;
    }
    @Override
    public Fragment NewFragment() {
        String url = getIntent().getStringExtra(EXTRA_URL);
        return DetalFragment.newInstance(url);
    }
}
