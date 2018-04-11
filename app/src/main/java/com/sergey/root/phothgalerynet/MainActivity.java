package com.sergey.root.phothgalerynet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class MainActivity extends AppCompatActivity {

    public abstract Fragment NewFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.activity_content);
        if(fragment == null){
            fragment = NewFragment();
            manager.beginTransaction().add(R.id.activity_content,fragment).commit();
        }
    }
}
