package com.example.labadmin.ayiti_touris.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
//import android.widget.SearchView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.labadmin.ayiti_touris.AdaptersOnline.AdapterDepartement;
import com.example.labadmin.ayiti_touris.ModelsOnline.BackendSettings;
import com.example.labadmin.ayiti_touris.ModelsOnline.DepartementModel;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.utils.NetWork;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class MainActivity extends AppCompatActivity{

    //Intent intent;
    ArrayList<DepartementModel> ListDepartement;
    ListView lvDepartement;
    AdapterDepartement adapterDep;
    Toolbar toolbar;
    ProgressDialog loadweb;
    private MaterialSearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.blue,R.color.green);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        //searchView.setSuggestions(getResources().getStringArray(R.id.lvendroit);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // Toast.makeText(Activity_ListeHotel.this, "Query: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
              // ListDepartement.clear();
               // fetchSearchDepartement(query);
               // return true;
                if(query.isEmpty())
                {
                    ListDepartement.clear();
                    onRefresh();

                }
                else if (!query.isEmpty()){

                    ListDepartement.clear();
                    fetchSearchDepartement(query);
                }

                else {ListDepartement.clear();fetchListeDepartement();}

                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                ListDepartement.clear();
                onRefresh();
            }

            @Override
            public void onSearchViewClosed() {
                ListDepartement.clear();
                onRefresh();
            }
        });

        // Methode pour rafraichir ListView
        Refresh();

        //Methode pour lister Departement
        fetchListeDepartement();

        //Integration Backendless
        Backendless.initApp(getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        // Recupere listview
        lvDepartement = (ListView) findViewById(R.id.lvdepartement);
        ListDepartement = new ArrayList<>();
        adapterDep = new AdapterDepartement(this, ListDepartement);

        // Setup Adapter
        lvDepartement.setAdapter(adapterDep);
        lvDepartement.setTextFilterEnabled(true);

        lvDepartement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DepartementModel departement = ListDepartement.get(position);
        Intent intent = new Intent(MainActivity.this, ActivityFragmentListes.class);
        intent.putExtra("Departement", departement);
        startActivity(intent);
        //Toast.makeText(MainActivity.this, "dep"+dep, Toast.LENGTH_SHORT).show();
    }
    });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_dep, menu);
        //getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search_dep);
        //MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    // Metode swiper refresh listView

    public void Refresh()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ListDepartement.clear();
                        fetchListeDepartement();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
    }

    public void onRefresh() {
        ListDepartement.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListDepartement.clear();
                fetchListeDepartement();
                //swipeContainer.setRefreshing(false);
            }
        }, 1000);
    }

    public void fetchListeDepartement(){
        boolean connexion= NetWork.checkConnexion(this);
        if(!connexion)
        {

            Toast.makeText(this,"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {

            IDataStore<Map> endroitStorage = Backendless.Data.of("departement");
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.addSortBy("classement");

            //queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID +"'");
            showProgress();
            //progressBar.setVisibility(View.VISIBLE);

            endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

            {

                @Override
                public void handleResponse(List<Map> response) {
                    loadweb.dismiss();
                    //progressBar.setVisibility(View.GONE);
                    adapterDep.addAll(DepartementModel.fromListMap(response));
                    adapterDep.notifyDataSetChanged();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    // if (loadweb != null) {
                    //
                    //  }

                    Log.d("DEBUG", lvDepartement.toString());
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    public void fetchSearchDepartement(String query){
        boolean connexion= NetWork.checkConnexion(this);
        if(!connexion)
        {

            Toast.makeText(this,"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {

            IDataStore<Map> endroitStorage = Backendless.Data.of("departement");
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.addSortBy("nom_departement");

            //queryBuilder.setWhereClause("nom_departement='" + BackendSettings.HOTEL_ID +"'");
            queryBuilder.setWhereClause("nom_departement like'%" + query + "%'");
            //showProgress();
            //progressBar.setVisibility(View.VISIBLE);

            endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

            {

                @Override
                public void handleResponse(List<Map> response) {
                    //loadweb.dismiss();
                    //progressBar.setVisibility(View.GONE);
                    adapterDep.addAll(DepartementModel.fromListMap(response));
                    adapterDep.notifyDataSetChanged();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    // if (loadweb != null) {
                    //
                    //  }

                    Log.d("DEBUG", lvDepartement.toString());
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void showProgress() {

        loadweb = new ProgressDialog(MainActivity.this);
        loadweb.setMessage("Chargement Departements ...");
        loadweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadweb.getWindow().setGravity(Gravity.CENTER);
        loadweb.show();

    }
}


