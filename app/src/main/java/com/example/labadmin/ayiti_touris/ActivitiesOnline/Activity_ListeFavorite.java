package com.example.labadmin.ayiti_touris.ActivitiesOnline;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.labadmin.ayiti_touris.AdaptersOnline.AdapterEndroit;
import com.example.labadmin.ayiti_touris.ModelsOnline.BackendSettings;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.utils.NetWork;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_ListeFavorite extends AppCompatActivity {

    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    private MaterialSearchView searchView;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listefavorite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Favorite");
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


        /**
         * setup search in tools bar
         *
         */

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        //searchView.setSuggestions(getResources().getStringArray(R.id.lvendroit);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(Activity_ListeClub.this, "Query: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ListEndroit.clear();
               fetchSearchFavorit(query);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                ListEndroit.clear();
                fetchListeFavorite();
                //
            }

            @Override
            public void onSearchViewClosed() {
                ListEndroit.clear();
                fetchListeFavorite();
                //ListEndroit.clear();
            }
        });

        //fin search
        fetchListeFavorite();
        Refresh();

        Backendless.initApp(getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        lvendroit = (ListView) findViewById(R.id.lvendroit);
        ListEndroit = new ArrayList<>();
        adapterendroit = new AdapterEndroit(this, ListEndroit);

        lvendroit.setAdapter(adapterendroit);
        lvendroit.setTextFilterEnabled(true);

        fetchListeFavorite();

    }


    public void fetchListeFavorite()
    {
        boolean connexion= NetWork.checkConnexion(this);
        if(!connexion)
        {

            Toast.makeText(this,"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {

            IDataStore<Map> endroitStorage = Backendless.Data.of("favorite");
            //IDataStore<Map> imageStorage = Backendless.Data.of( "images" );
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();


            queryBuilder.setWhereClause("id_user='" + LoginActivity.idUser + "'");

            endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

            {


                @Override
                public void handleResponse(List<Map> response) {

                    adapterendroit.addAll(Endroit.fromListMap(response));
                    adapterendroit.notifyDataSetChanged();
                    Log.d("DEBUG", lvendroit.toString());
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void fetchSearchFavorit(String query) {
        boolean connexion= NetWork.checkConnexion(this);
        if(!connexion)
        {

            Toast.makeText(this,"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {

            //  String whereClause = "categorie=Viandes";
            IDataStore<Map> endroitStorage = Backendless.Data.of("favorite");

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
                    // if (loadweb!=null)
                    // {
                    //     loadweb.dismiss();
                    //}
                    Log.d("DEBUG", lvendroit.toString());
                  //  Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

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
                ListEndroit.clear();
                fetchListeFavorite();
                swipeContainer.setRefreshing(false);
            }


        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
