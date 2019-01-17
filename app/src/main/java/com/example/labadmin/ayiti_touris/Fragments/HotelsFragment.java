package com.example.labadmin.ayiti_touris.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.labadmin.ayiti_touris.ActivitiesOnline.Activity_DetailsEndroit;
import com.example.labadmin.ayiti_touris.ActivitiesOnline.Activity_ListeFavorite;
import com.example.labadmin.ayiti_touris.ActivitiesOnline.Activity_ListeHotel;
import com.example.labadmin.ayiti_touris.ActivitiesOnline.LoginActivity;
import com.example.labadmin.ayiti_touris.AdaptersOnline.AdapterEndroit;
import com.example.labadmin.ayiti_touris.ModelsOnline.BackendSettings;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.utils.Constante;
import com.example.labadmin.ayiti_touris.utils.NetWork;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelsFragment extends Fragment {

    // public static final String AplicationID = "2C703DEF-BB5B-08D7-FFDA-6C2620273000";
    // public static final String SecretKey = "DCF8496C-EF06-1583-FF19-15FBA9ACB000";
    public android.app.ProgressDialog barProgressDialog, proDialog,ProgressDialog,loadweb;
    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    ProgressBar progressBar;
    String DEP_ID;
    Intent intent;
    private Bundle bundle;

    private SwipeRefreshLayout swipeContainer;
    private MaterialSearchView searchView;
    public static HotelsFragment newInstance() {

        return new HotelsFragment();
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotels, container, false);
   // }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_hotels, container, false);*/
        // Inflate the layout for this fragment

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        //toolbar.setTitle("Hotels");
        //toolbar.setTitleTextColor(android.graphics.Color.WHITE);

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        DEP_ID  = this.getArguments().getString("dep");
        //Toast.makeText(getActivity(), myValue, Toast.LENGTH_SHORT).show();

       // apelle methode ki ranpli listview a nan fragment an
    fetchListeHotel();
       // Refresh();

        Backendless.initApp(getActivity().getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        //lvendroit = (ListView) getActivity().findViewById(R.id.lvendroit);
         lvendroit = view.findViewById(R.id.lvendroit);
        ListEndroit = new ArrayList<>();
        adapterendroit = new AdapterEndroit( this.getActivity(),ListEndroit);
        // Setup Adapter
        lvendroit.setAdapter(adapterendroit);
        lvendroit.setTextFilterEnabled(true);

        /**
         * Methode clicklistener du listview 'lvendroit'
         */
        lvendroit.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Endroit endroit = ListEndroit.get(position);
                Intent intent = new Intent(getActivity(), Activity_DetailsEndroit.class);
                intent.putExtra("Endroit", endroit);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    /**
     * emplemantasyon metod ==fetchListeHotel== qui ranpli listview 'lvendroit' nan fragment an
     */
    public void fetchListeHotel(){
        boolean connexion= NetWork.checkConnexion(getActivity());
        if(!connexion)
        {

            Toast.makeText(getActivity(),"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        }
        else {

            if(DEP_ID.trim().equals(Constante.NORD_DEP)) {
               // Toast.makeText(getActivity(),"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();

                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.NORD_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }//fin nord
            else if(DEP_ID.trim().equals(Constante.OUEST_DEP)) //ouest
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.OUEST_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin ouest

            else if(DEP_ID.trim().equals(Constante.SUD_DEP)) //sud
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.SUD_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin sud

            else if(DEP_ID.trim().equals(Constante.CENTRE_DEP))//centre
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.CENTRE_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin centre

            else if(DEP_ID.trim().equals(Constante.NIPPES_DEP)) // Nippes
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.NIPPES_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin Nippes
            else if(DEP_ID.trim().equals(Constante.SUD_EST_DEP)) //sud-est
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.SUD_EST_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin sud-est

            else if(DEP_ID.trim().equals(Constante.NORD_EST_DEP)) //nord-est
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.NORD_EST_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin nord-est

            else if(DEP_ID.trim().equals(Constante.ARTIBONITE_DEP)) // artibonite
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.ARTIBONITE_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin artibonite

            else if(DEP_ID.trim().equals(Constante.GRAND_ANSE_DEP))// grand-anse
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.GRAND_ANSE_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin grand-anse

            else if(DEP_ID.trim().equals(Constante.NORD_OUEST_DEP)) //nord-ouest
            {
                IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID + "' and lieux_x_dep='" + BackendSettings.NORD_OUEST_ID + "'");
                showProgress();
                endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {
                        loadweb.dismiss();
                        adapterendroit.addAll(Endroit.fromListMap(response));
                        adapterendroit.notifyDataSetChanged();
                        Log.d("DEBUG", lvendroit.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }// fin nord-ouest
        }


    }


    private void fetchSearchHotel(String query) {
        boolean connexion= NetWork.checkConnexion(getActivity());
        if(!connexion)
        {

            Toast.makeText(getActivity(),"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {
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
                    if (loadweb != null) {
                        loadweb.dismiss();
                    }
                    Log.d("DEBUG", lvendroit.toString());
                    Toast.makeText(getContext().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }



    public void Refresh()
    {

        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
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


    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.search_menu, menu);
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        //MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        getActivity().finish();
        switch (id) {
            case R.id.action_settings:
                // showSnackBar("Votre preference");
                return true;
            case R.id.action_account:
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                //   showSnackBar("A Propos de Nous");
                return true;

            case R.id.action_favorite:
                Intent intentfavorite=new Intent(getActivity(),Activity_ListeFavorite.class);
                startActivity(intentfavorite);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



        public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.getActivity().onBackPressed();
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

        loadweb = new ProgressDialog(getActivity());
        loadweb.setMessage("Chargement ...");
        loadweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadweb.getWindow().setGravity(Gravity.CENTER);
        loadweb.show();

    }
}
