package com.example.labadmin.ayiti_touris.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.labadmin.ayiti_touris.AdaptersOnline.AdapterEndroit;
import com.example.labadmin.ayiti_touris.ModelsOnline.BackendSettings;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.utils.Constante;
import com.example.labadmin.ayiti_touris.utils.NetWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    public android.app.ProgressDialog barProgressDialog, proDialog,ProgressDialog,loadweb;
    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    ProgressBar progressBar;
    String DEP_ID;

    private SwipeRefreshLayout swipeContainer;
    SearchView searchView;

    public static RestaurantsFragment newInstance() {
        return new RestaurantsFragment();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        DEP_ID  = this.getArguments().getString("dep");

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(R.color.orange,R.color.blue,R.color.green);
        //Methode ki ranpli listview
        fetchListeResto();

        // Methode pou rafrechi listview
        Refresh();
        Backendless.initApp(getActivity().getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        // Recuperer listview
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

    public void Refresh()
    {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ListEndroit.clear();
                        fetchListeResto();
                        swipeContainer.setRefreshing(false);
                    }
                }, 2500);
            }
        });
    }

    public void onRefresh() {
        ListEndroit.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ListEndroit.clear();
                fetchListeResto();
                //swipeContainer.setRefreshing(false);
            }
        }, 1000);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Rechercher");

        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public void fetchListeResto(){
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.NORD_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.OUEST_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.SUD_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.CENTRE_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.NIPPES_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.SUD_EST_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.NORD_EST_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.ARTIBONITE_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.GRAND_ANSE_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.RESTAURANT_ID + "' and lieux_x_dep='" + BackendSettings.NORD_OUEST_ID + "'");
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

    private void fetchSearchResto(String query) {
        boolean connexion= NetWork.checkConnexion(getActivity());
        if(!connexion)
        {

            Toast.makeText(getActivity(),"impossible de continuer, verifier la connexion internet",
                    Toast.LENGTH_LONG).show();

        }
        else

        {
            IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");

            DataQueryBuilder queryBuilder = DataQueryBuilder.create();

            queryBuilder.setWhereClause("nom like'%" + query + "%' and id_categorie='" + BackendSettings.RESTAURANT_ID + "'");
            queryBuilder.addSortBy("nom");

            endroitStorage.find(queryBuilder, new AsyncCallback<List<Map>>()

            {
                @Override
                public void handleResponse(List<Map> response) {

                    adapterendroit.addAll(Endroit.fromListMap(response));
                    adapterendroit.notifyDataSetChanged();
                    Log.d("DEBUG", lvendroit.toString());

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    Toast.makeText(getContext().getApplicationContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG",fault.getMessage());

                }
            });
        }

    }

    private void showProgress() {

        loadweb = new ProgressDialog(getActivity());
        loadweb.setMessage("Chargement ...");
        loadweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadweb.getWindow().setGravity(Gravity.CENTER);
        loadweb.show();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        if(query.isEmpty())
        {
            ListEndroit.clear();
            onRefresh();


        }
        else if (!query.isEmpty()){
            ListEndroit.clear();
            fetchSearchResto(query);
        }

        else {
            ListEndroit.clear();
            fetchListeResto();
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return false;
    }
}
