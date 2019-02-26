package com.example.labadmin.ayiti_touris.activities;

//import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;

public class ActivityFragmentListes extends AppCompatActivity{

    private BottomNavigationView mBottomNavigationView;
    String value, DEP_ID;
    Intent intent;
    DepartementModel departement;
    FloatingActionButton bt_floting_event;
    ArrayList<DepartementModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_listes);
        intent = getIntent();
        Bundle extras = intent.getExtras();
        value = extras.getString("departement");

        DEP_ID = intent.getStringExtra(value);

        departement = (DepartementModel) getIntent().getSerializableExtra("Endroit");

        list = new ArrayList<>();

        //Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //toolbar.setTitle("Hotels");

        setupBottomNavigation();

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



    private void setupBottomNavigation() {


        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.fragment_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_hotel:
                       // setTitle("Hotels");
                        loadHotelsFragment();
                        return true;
                    case R.id.navigation_club:
                        loadClubsFragment();
                       // setTitle("Clubs");
                        return true;
                    case R.id.navigation_restaurant:
                        loadRestaurantsFragment();
                        return true;

                    case R.id.navigation_monument:
                        loadRestaurantsFragment();
                        return true;

                    case R.id.navigation_plage:
                        loadRestaurantsFragment();
                        return true;
                }
                return false;
            }
        });
    }

    private void loadHotelsFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("dep", value);
        HotelsFragment fragment = HotelsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadClubsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", value);
        ClubsFragment fragment = ClubsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadRestaurantsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", value);
        RestaurantsFragment fragment = RestaurantsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadMonumentsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", value);
        MonumentsFragment fragment = MonumentsFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadPlagesFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("dep", value);
        PlagesFragment fragment = PlagesFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }


}
