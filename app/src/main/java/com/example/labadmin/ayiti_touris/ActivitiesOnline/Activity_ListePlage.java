package com.example.labadmin.ayiti_touris.ActivitiesOnline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_ListePlage extends AppCompatActivity {

    public android.app.ProgressDialog barProgressDialog, proDialog,ProgressDialog,loadweb;
    ArrayList<Endroit> ListEndroit;
    ListView lvclub;
    AdapterEndroit adapterendroit;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_plage_online);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Plages");

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
         * Methode populate data dans le listeView
         */
        fetchListePlage();

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
                Toast.makeText(Activity_ListePlage.this, "Query: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ListEndroit.clear();
                fetchEndroit(query);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        //fin search


        //Initialisation API

        Backendless.initApp(getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        lvclub = (ListView) findViewById(R.id.lvendroit);
        ListEndroit = new ArrayList<>();
        adapterendroit = new AdapterEndroit(this, ListEndroit);

        // Setup Adapter
        lvclub.setAdapter(adapterendroit);
        lvclub.setTextFilterEnabled(true);

       /* lvendroit.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Long sinceId = getOldestTweetId();
                client.getOlderHomeTimeline(new TwitterClient.TimelineResponseHandler() {
                    @Override
                    public void onSuccess(List<Tweet> tweets) {
                        adapterendroit.addAll(tweets.isEmpty() ? tweets : tweets.subList(1, tweets.size()));
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        logError(error);
                    }
                }, sinceId);
                return true;
            }
        });*/




        // }

        // evenement click sur un evenement de la liste

        lvclub.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Endroit endroit = ListEndroit.get(position);

                Intent intent = new Intent(Activity_ListePlage.this, Activity_DetailsEndroit.class);
                intent.putExtra("Endroit", endroit);
                startActivity(intent);
            }
        });

    }


    public void fetchListePlage(){

        IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
        //IDataStore<Map> imageStorage = Backendless.Data.of( "images" );
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();


        Intent intent = getIntent();

        //  final String value = intent.getStringExtra("key");

        queryBuilder.setWhereClause("id_categorie='"+BackendSettings.PLAGE_ID+"'");


        showProgress();

        endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

        {


            @Override
            public void handleResponse(List<Map> response) {

                adapterendroit.addAll(Endroit.fromListMap(response));
                adapterendroit.notifyDataSetChanged();
                // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                if (loadweb != null) {
                    loadweb.dismiss();
                }

                Log.d("DEBUG", lvclub.toString());
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void fetchEndroit(String query) {

        //  String whereClause = "categorie=Viandes";
        IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        // queryBuilder.setWhereClause( whereClause.toString());

        queryBuilder.setWhereClause("nom like'" + query + "%'");
        queryBuilder.addSortBy("nom");

        showProgress();

        endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

        {
            @Override
            public void handleResponse(List<Map> response) {

                adapterendroit.addAll(Endroit.fromListMap(response));
                adapterendroit.notifyDataSetChanged();
                if (loadweb!=null)
                {
                    loadweb.dismiss();
                }
                Log.d("DEBUG", lvclub.toString());
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    /**
     * Methode pour gerer les menu
     * @param menu
     * @return
     */

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
                Intent intent=new Intent(Activity_ListePlage.this,LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                //   showSnackBar("A Propos de Nous");
                return true;

            case R.id.action_favorite:
                Intent intentfavorite=new Intent(Activity_ListePlage.this,Activity_ListeFavorite.class);
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

        loadweb = new ProgressDialog(Activity_ListePlage.this);
        loadweb.setMessage("Chargement ...");
        loadweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadweb.getWindow().setGravity(Gravity.CENTER);
        loadweb.show();

    }



    }
