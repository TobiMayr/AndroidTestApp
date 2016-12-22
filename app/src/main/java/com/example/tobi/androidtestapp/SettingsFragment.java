package com.example.tobi.androidtestapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tobi on 21.12.16.
 */

public class SettingsFragment extends android.app.Fragment{

    private View settingsFragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsFragmentView = inflater.inflate(R.layout.fragment_settings, container, false);

        return settingsFragmentView;
    }
}
