package com.example.labadmin.ayiti_touris.ActivitiesOnline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.QueryOptions;
import com.example.labadmin.ayiti_touris.AdaptersOnline.AdapterEndroit;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.utils.NetWork;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.example.labadmin.ayiti_touris.ModelsOnline.BackendSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

public class Activity_ListeHotel extends AppCompatActivity {

   // public static final String AplicationID = "2C703DEF-BB5B-08D7-FFDA-6C2620273000";
   // public static final String SecretKey = "DCF8496C-EF06-1583-FF19-15FBA9ACB000";
   public ProgressDialog barProgressDialog, proDialog,ProgressDialog,loadweb;
    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeContainer;
    private MaterialSearchView searchView;

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_hotel_online);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            setTitle("Liste Hotel");
            toolbar.setTitleTextColor(android.graphics.Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //search

       // toolbar.setNavigationIcon(R.drawable.your_drawable_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what do you want here
                    onBackPressed();

            }
        });

          //  progressBar = (ProgressBar) findViewById(R.id.HeaderProgress);
            //progressBar.setVisibility(View.GONE);



        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        //searchView.setSuggestions(getResources().getStringArray(R.id.lvendroit);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Activity_ListeHotel.this, "Query: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ListEndroit.clear();
                fetchSearchHotel(query);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                ListEndroit.clear();
                fetchListeHotel();
            }

            @Override
            public void onSearchViewClosed() {
                ListEndroit.clear();
                fetchListeHotel();
            }
        });

        //fin search
            fetchListeHotel();
            Refresh();

            //Initialisation API

        Backendless.initApp(getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        lvendroit = (ListView) findViewById(R.id.lvendroit);
        ListEndroit = new ArrayList<>();
        adapterendroit = new AdapterEndroit(this, ListEndroit);

        // Setup Adapter
        lvendroit.setAdapter(adapterendroit);
        lvendroit.setTextFilterEnabled(true);


        lvendroit.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view is an instance of MovieView
                //Expose details of movie (ratings (out of 10), popularity, and synopsis
                //ratings using RatingBar

                Endroit endroit = ListEndroit.get(position);

                Intent intent = new Intent(Activity_ListeHotel.this, Activity_DetailsEndroit.class);
                intent.putExtra("Endroit", endroit);
                startActivity(intent);
            }
        });

    }

    /**
     * Methode pour lister les hotel
     */




    public void fetchListeHotel(){
        boolean connexion= NetWork.checkConnexion(this);
        if(!connexion)
        {

            Toast.makeText(this,"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {

            IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();

            Intent intent = getIntent();

            //  final String value = intent.getStringExtra("key");

            queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "'");
            showProgress();
            //progressBar.setVisibility(View.VISIBLE);

            endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

            {

                @Override
                public void handleResponse(List<Map> response) {
                    loadweb.dismiss();
                    //progressBar.setVisibility(View.GONE);
                    adapterendroit.addAll(Endroit.fromListMap(response));
                    adapterendroit.notifyDataSetChanged();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                   // if (loadweb != null) {
                    //
                  //  }

                    Log.d("DEBUG", lvendroit.toString());
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }



    private void fetchSearchHotel(String query) {
        boolean connexion= NetWork.checkConnexion(this);
        if(!connexion)
        {

            Toast.makeText(this,"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {
            //  String whereClause = "categorie=Viandes";
            IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");

            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            // queryBuilder.setWhereClause( whereClause.toString());

            queryBuilder.setWhereClause("nom like'" + query + "%'");
            queryBuilder.addSortBy("nom");

            //showProgress();

            endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

            {
                @Override
                public void handleResponse(List<Map> response) {

                    adapterendroit.addAll(Endroit.fromListMap(response));
                    adapterendroit.notifyDataSetChanged();
                    if (loadweb != null) {
                        loadweb.dismiss();
                    }
                    Log.d("DEBUG", lvendroit.toString());
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }



    public void Refresh()
    {

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                fetchListeHotel();
                //fetchHardcodedData();

                //signal refresh has finished:
                swipeContainer.setRefreshing(false);
            }


        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        //MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        finish();
        switch (id) {
             case R.id.action_settings:
               // showSnackBar("Votre preference");
                return true;
            case R.id.action_account:
            Intent intent=new Intent(Activity_ListeHotel.this,LoginActivity.class);
            startActivity(intent);
                return true;
            case R.id.action_about:
             //   showSnackBar("A Propos de Nous");
                return true;

            case R.id.action_favorite:
                Intent intentfavorite=new Intent(Activity_ListeHotel.this,Activity_ListeFavorite.class);
                startActivity(intentfavorite);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    private void showProgress() {

        loadweb = new ProgressDialog(Activity_ListeHotel.this);
        loadweb.setMessage("Chargement ...");
        loadweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadweb.getWindow().setGravity(Gravity.CENTER);
        loadweb.show();

    }


   }
