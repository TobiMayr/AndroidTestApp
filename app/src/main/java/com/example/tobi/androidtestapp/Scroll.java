package com.example.tobi.androidtestapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tobi on 11.12.16.
 */

public class Scroll extends android.app.Fragment{

    private TextView ScrollListTextView;
    View scrollActivityView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scrollActivityView = inflater.inflate(R.layout.activity_scroll, container, false);

        ScrollListTextView = (TextView) scrollActivityView.findViewById(R.id.tv_toy_names);
        String[] randomItems = getResources().getStringArray(R.array.some_list);
        for (String item : randomItems) {
            ScrollListTextView.append(item + "\n\n\n");
        }
        return scrollActivityView;
    }
}
