package com.example.tobi.uilayouts;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    public String[] spinnerEntries;
    int counter = 0;
    boolean checked = false;
    Button btnStartProgress;
    int currentProgress = 0;
    private Handler handler = new Handler();
    ProgressBar progressBar;
    TextView textSwitch;
    TextView itemSelected;
    View colourBox;
    SeekBar seekBar;
    Handler seekBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        this.spinnerEntries = new String[]{"one", "two", "three"};
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerEntries);
        s.setAdapter(adapter);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    colourBox = findViewById(R.id.ColourBox);
                    if(checkedRadioButton == findViewById(R.id.redRB))
                    {
                        seekBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (seekBar != null) {
                                    seekBar.setProgress(0);
                                    seekBar.refreshDrawableState();
                                }
                            }
                        });
                    }
                    else if(checkedRadioButton == findViewById(R.id.greenRB))
                    {
                        seekBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (seekBar != null) {
                                    seekBar.setProgress(33);
                                    seekBar.refreshDrawableState();
                                }
                            }
                        });
                    }
                    else if(checkedRadioButton == findViewById(R.id.blueRB))
                    {
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

        btnStartProgress = (Button) findViewById(R.id.progressButton);
        btnStartProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addListenerOnButton();
            }
        });
        Switch switcher = (Switch)findViewById(R.id.switch1);
        switcher.setChecked(true);
        textSwitch = (TextView)findViewById(R.id.switchText);
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    textSwitch.setText(getString(R.string.switchOn));
                }
                else{
                    textSwitch.setText(getString(R.string.switchOff));
                }

            }
        });
        itemSelected = (TextView)findViewById(R.id.selectedItem);
        final Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                itemSelected.setText(getString(R.string.selectedItem) + dropdown.getSelectedItem().toString()) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

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

    }

    public void addListenerOnButton() {


        new Thread(new Runnable() {
            public void run() {
                while (currentProgress < 100){
                    currentProgress += 1;
                    // Update the progress bar and display the
                    //current value in the text view

                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(currentProgress);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    //Counter
    public void counter(View view)
    {
        counter++;
        TextView myTextView = (TextView)findViewById(R.id.counter);
        myTextView.setText(String.valueOf(counter));
    }

    //Checker
    public void checker (View view)
    {
        checked = !checked;
        TextView myTextView = (TextView)findViewById(R.id.checkerText);
        if(checked)
        {
            myTextView.setText("Checked");
        }
        else
        {
            myTextView.setText("Unchecked");
        }
    }
}
