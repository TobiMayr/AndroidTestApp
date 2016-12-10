package com.example.tobi.androidtestapp;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class UiActivity extends Fragment {

    View uiLayoutView;
    RadioGroup radioGroup;
    RadioButton checkedRadioButton;
    boolean rbIsChecked;
    Spinner progressBarSpinner;
    String[] spinnerEntries;
    TextView itemSelected;
    Button btnStartProgress;
    int currentProgress = 0;
    Handler progressHandler = new Handler();
    ProgressBar progressBar;
    TextView textSwitch;
    SeekBar seekBar;
    Handler seekBarHandler = new Handler();
    View colourBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        uiLayoutView = inflater.inflate(R.layout.activity_ui_layouts, container, false);

        //RadioButtons Red, Green, Blue
        radioGroup = (RadioGroup) uiLayoutView.findViewById(R.id.radio_group);
        colourBox = uiLayoutView.findViewById(R.id.ColourBox);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);

                rbIsChecked = checkedRadioButton.isChecked();
                if (rbIsChecked) {
                    if (checkedRadioButton == uiLayoutView.findViewById(R.id.redRB)) {
                        seekBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (seekBar != null) {
                                    seekBar.setProgress(0);
                                    seekBar.refreshDrawableState();
                                }
                            }
                        });
                    } else if (checkedRadioButton == uiLayoutView.findViewById(R.id.greenRB)) {
                        seekBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (seekBar != null) {
                                    seekBar.setProgress(33);
                                    seekBar.refreshDrawableState();
                                }
                            }
                        });
                    } else if (checkedRadioButton == uiLayoutView.findViewById(R.id.blueRB)) {
                        seekBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (seekBar != null) {
                                    seekBar.setProgress(66);
                                }
                            }
                        });
                    }
                }
            }
        });

        //Spinner/Dropdown
        itemSelected = (TextView) uiLayoutView.findViewById(R.id.selectedItem);
        progressBarSpinner = (Spinner) uiLayoutView.findViewById(R.id.spinner);
        progressBarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                itemSelected.setText(getString(R.string.selectedItem) + progressBarSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                itemSelected.setText("Nothing selected");
            }
        });

        //ProgressBar
        progressBar = (ProgressBar) uiLayoutView.findViewById(R.id.progressBar);
        this.spinnerEntries = new String[]{"one", "two", "three"};
        seekBar = (SeekBar) uiLayoutView.findViewById(R.id.seekBar);
        progressBarSpinner = (Spinner) uiLayoutView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerEntries);
        progressBarSpinner.setAdapter(adapter);

        btnStartProgress = (Button) uiLayoutView.findViewById(R.id.progressButton);
        btnStartProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressButtonListener();
            }
        });

        //Switcher
        Switch switcher = (Switch) uiLayoutView.findViewById(R.id.switch1);
        switcher.setChecked(true);
        textSwitch = (TextView) uiLayoutView.findViewById(R.id.switchText);
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    textSwitch.setText(getString(R.string.switchOn));
                } else {
                    textSwitch.setText(getString(R.string.switchOff));
                }

            }
        });

        //SeekBar changing colours
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                progress = progressValue;
                float[] hsvColor = {0, 1, 1};
                hsvColor[0] = 360f * progress / 100;
                colourBox.setBackgroundColor(Color.HSVToColor(hsvColor));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return uiLayoutView;
    }

    //ProgressBar Listener
    public void progressButtonListener() {
        new Thread(new Runnable() {
            public void run() {
                while (currentProgress < 100) {
                    currentProgress += 1;
                    // Update the progress bar and display the
                    //current value in the text view

                    progressHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(currentProgress);
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
