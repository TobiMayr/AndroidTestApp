package com.example.tobi.androidtestapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShowPicture extends android.app.Fragment {

    View showPictureView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        showPictureView = inflater.inflate(R.layout.activity_show_picture, container, false);
        return showPictureView;
    }
}
