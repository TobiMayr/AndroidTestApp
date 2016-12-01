package com.example.tobi.uilayouts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class UiActivity extends AppCompatActivity {

    int counter = 0;
    boolean checked = false;
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

    //Nav Drawer
    private DrawerLayout myDrawerLayout;
    private ListView myDrawerList;
    private android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    private CharSequence myDrawerTitle;
    private CharSequence pageTitle;
    private String[] navItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_layouts);

        //Nav Drawer
        pageTitle = myDrawerTitle = getTitle();
        navItems = getResources().getStringArray(R.array.navItems_array);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        myDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        myDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        myDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, navItems));
        myDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(
                this,                  /* host Activity */
                myDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(pageTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(myDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        myDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        //RadioButtons Red, Green, Blue
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedRadioButton = (RadioButton) group.findViewById(checkedId);

                rbIsChecked = checkedRadioButton.isChecked();
                if (rbIsChecked) {
                    colourBox = findViewById(R.id.ColourBox);
                    if (checkedRadioButton == findViewById(R.id.redRB)) {
                        seekBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (seekBar != null) {
                                    seekBar.setProgress(0);
                                    seekBar.refreshDrawableState();
                                }
                            }
                        });
                    } else if (checkedRadioButton == findViewById(R.id.greenRB)) {
                        seekBarHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (seekBar != null) {
                                    seekBar.setProgress(33);
                                    seekBar.refreshDrawableState();
                                }
                            }
                        });
                    } else if (checkedRadioButton == findViewById(R.id.blueRB)) {
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
        itemSelected = (TextView) findViewById(R.id.selectedItem);
        progressBarSpinner = (Spinner) findViewById(R.id.spinner);
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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.spinnerEntries = new String[]{"one", "two", "three"};
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        progressBarSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerEntries);
        progressBarSpinner.setAdapter(adapter);

        btnStartProgress = (Button) findViewById(R.id.progressButton);
        btnStartProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressButtonListener();
            }
        });

        //Switcher
        Switch switcher = (Switch) findViewById(R.id.switch1);
        switcher.setChecked(true);
        textSwitch = (TextView) findViewById(R.id.switchText);
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

    }

    //Checker
    public void checker(View view) {
        checked = !checked;
        TextView myTextView = (TextView) findViewById(R.id.checkerText);
        if (checked) {
            myTextView.setText(R.string.checkOn);
        } else {
            myTextView.setText(R.string.checkOff);
        }
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


    //Counter
    public void counter(View view) {
        counter++;
        TextView myTextView = (TextView) findViewById(R.id.counter);
        myTextView.setText(String.valueOf(counter));
    }

    //Nav Drawer
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = myDrawerLayout.isDrawerOpen(myDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        myDrawerList.setItemChecked(position, true);
        setTitle(navItems[position]);
        myDrawerLayout.closeDrawer(myDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        pageTitle = title;
        //getActionBar().setTitle(pageTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_ui_layouts, container, false);
            return rootView;

/*            View rootView = inflater.inflate(R.layout.activity_ui_layouts, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.navItems_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;*/
        }
    }
}
