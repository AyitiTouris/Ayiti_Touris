package com.example.labadmin.ayiti_touris.ActivitiesOnline;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.labadmin.ayiti_touris.MainActivity;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.listeners.EndlessScrollListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_ListeEndroit extends AppCompatActivity {

    public static final String AplicationID = "2C703DEF-BB5B-08D7-FFDA-6C2620273000";
    public static final String SecretKey = "DCF8496C-EF06-1583-FF19-15FBA9ACB000";

    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    String Hotel = "Hotel", Club = "Club", Monument = "Monument", Restaurant = "Restaurant";

    private MaterialSearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_endroit_online);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //search

       // toolbar.setNavigationIcon(R.drawable.your_drawable_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what do you want here

                Intent intent=new Intent(Activity_ListeEndroit.this,MainActivity.class);
                startActivity(intent);
            }
        });

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        //searchView.setSuggestions(getResources().getStringArray(R.id.lvendroit);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
                        //.show();

                Toast.makeText(Activity_ListeEndroit.this, "Query: " + query, Toast.LENGTH_SHORT).show();
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



        Backendless.initApp(getApplicationContext(), AplicationID, SecretKey);

        lvendroit = (ListView) findViewById(R.id.lvendroit);
        ListEndroit = new ArrayList<>();
        adapterendroit = new AdapterEndroit(this, ListEndroit);

        lvendroit.setAdapter(adapterendroit);
        lvendroit.setTextFilterEnabled(true);

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
        IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
        //IDataStore<Map> imageStorage = Backendless.Data.of( "images" );
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();

        Intent intent = getIntent();

        //  final String value = intent.getStringExtra("key");

        if (Hotel.equals(intent.getStringExtra("Hotel"))) {

            queryBuilder.setWhereClause("id_categorie='F48DD318-B096-F8A2-FF95-47284708F700'");

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

        } else if (Club.equals(intent.getStringExtra("Club"))) {

            queryBuilder.setWhereClause("id_categorie='B60B980E-D5F7-3554-FF31-22A25ACC3000'");

            endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

            {


                @Override
                public void handleResponse(List<Map> response) {

                    adapterendroit.addAll(Endroit.fromListMap(response));
                    adapterendroit.notifyDataSetChanged();
                    Log.d("DEBUG", lvendroit.toString());
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            //Methode non filtrer
          /* endroitStorage.find(new AsyncCallback<List<Map>>() {

               @Override
               public void handleResponse(List<Map> response) {

                   Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                   //recette = (recettes.fromListMap(response));
                   adapterendroit.addAll(Endroit.fromListMap(response));
                   adapterendroit.notifyDataSetChanged();
                   Log.d("DEBUG", lvendroit.toString());


               }

               @Override
               public void handleFault(BackendlessFault fault) {

                   Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

               }
           });*/
        } else if (Monument.equals(intent.getStringExtra("Monument"))) {


            queryBuilder.setWhereClause("id_categorie='8BA8DFEE-08FB-3128-FFDF-69E1F8EFEF00'");

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


        } else {
           // Toast.makeText(getApplicationContext(), "ou pa nan otel", Toast.LENGTH_SHORT).show();
        }

        lvendroit.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view is an instance of MovieView
                //Expose details of movie (ratings (out of 10), popularity, and synopsis
                //ratings using RatingBar

                Endroit endroit = ListEndroit.get(position);

                Intent intent = new Intent(Activity_ListeEndroit.this, Activity_DetailsEndroit.class);
                intent.putExtra("Endroit", endroit);
                startActivity(intent);
            }
        });

    }




    private void fetchEndroit(String query) {

       // lvrecette = (ListView) findViewById(R.id.lvrecette);
        //listRecette = new ArrayList<>();
       // adapterRecette = new ListviewrecetteAdapter(this, listRecette);

       /// lvrecette.setAdapter(adapterRecette);


        //StringBuilder whereClause = new StringBuilder();
        //whereClause.append( "categories[nom_categorie]" );
        //whereClause.append( ".objectId='" ).append( "1D075E85-8468-6319-FF44-4831E11AB400"  );


        //  String whereClause = "categorie=Viandes";
        IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        // queryBuilder.setWhereClause( whereClause.toString());

        queryBuilder.setWhereClause("nom like'" + query + "%'");

        endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

        {
            @Override
            public void handleResponse(List<Map> response) {

                adapterendroit.addAll(Endroit.fromListMap(response));
                adapterendroit.notifyDataSetChanged();
                Log.d("DEBUG", lvendroit.toString());
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }



    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
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
    }



    private void setUpClickListener() {

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
