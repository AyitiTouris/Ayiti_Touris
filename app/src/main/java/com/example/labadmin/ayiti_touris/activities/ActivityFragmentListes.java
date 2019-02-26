package com.example.labadmin.ayiti_touris.activities;

//import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.labadmin.ayiti_touris.Fragments.ClubsFragment;
import com.example.labadmin.ayiti_touris.Fragments.HotelsFragment;
import com.example.labadmin.ayiti_touris.Fragments.MonumentsFragment;
import com.example.labadmin.ayiti_touris.Fragments.PlagesFragment;
import com.example.labadmin.ayiti_touris.Fragments.RestaurantsFragment;
import com.example.labadmin.ayiti_touris.ModelsOnline.DepartementModel;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.utils.ListesEvent;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class ActivityFragmentListes extends AppCompatActivity{

    private BottomNavigationViewEx mBottomNavigationView;
    String value, DEP_ID;
    Intent intent;
    FloatingActionButton bt_floting_event;
    DepartementModel departement;
    ArrayList<DepartementModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_listes);
        intent = getIntent();
        Bundle extras = intent.getExtras();
        //value = extras.getString("departement");

       // DEP_ID = intent.getStringExtra(value);

        departement = (DepartementModel) getIntent().getSerializableExtra("Departement");

        list = new ArrayList<>();

        DEP_ID= departement.getNom_dep();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Hotels "+DEP_ID.toLowerCase());

        setupBottomNavigation();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what do you want here
                onBackPressed();

            }
        });

        if (savedInstanceState == null) {

            loadHotelsFragment();
        }
        bt_floting_event=(FloatingActionButton) findViewById(R.id.bt_floting_event);
        bt_floting_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityFragmentListes.this, ListesEvent.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupBottomNavigation() {


        mBottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.fragment_navigation);
        mBottomNavigationView.enableAnimation(false);
        mBottomNavigationView.enableShiftingMode(false);
        mBottomNavigationView.enableItemShiftingMode(false);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_hotel:
                        setTitle("Hotels "+DEP_ID.toLowerCase());
                        loadHotelsFragment();
                        return true;
                    case R.id.navigation_club:
                        loadClubsFragment();
                        setTitle("Clubs "+DEP_ID.toLowerCase());
                        return true;
                    case R.id.navigation_restaurant:
                        loadRestaurantsFragment();
                        setTitle("Restaurants "+DEP_ID.toLowerCase());
                        return true;

                    case R.id.navigation_monument:
                        loadMonumentsFragment();
                        setTitle("Monuments "+DEP_ID.toLowerCase());
                        return true;

                    case R.id.navigation_plage:
                        loadPlagesFragment();
                        setTitle("Plages "+DEP_ID.toLowerCase());
                        return true;
                }
                return false;
            }
        });
    }

    private void loadHotelsFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("dep", DEP_ID);
        HotelsFragment fragment = HotelsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadClubsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", DEP_ID);
        ClubsFragment fragment = ClubsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadRestaurantsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", DEP_ID);
        RestaurantsFragment fragment = RestaurantsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadMonumentsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", DEP_ID);
        MonumentsFragment fragment = MonumentsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadPlagesFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", DEP_ID);
        PlagesFragment fragment = PlagesFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }


}

