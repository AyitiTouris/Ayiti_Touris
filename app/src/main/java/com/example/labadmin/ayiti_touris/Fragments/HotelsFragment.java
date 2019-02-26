package com.example.labadmin.ayiti_touris.Fragments;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import static android.support.v4.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    // public static final String AplicationID = "2C703DEF-BB5B-08D7-FFDA-6C2620273000";
    // public static final String SecretKey = "DCF8496C-EF06-1583-FF19-15FBA9ACB000";
    public android.app.ProgressDialog barProgressDialog, proDialog,ProgressDialog,loadweb;
    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    ProgressBar progressbar;
    String DEP_ID;
    Intent intent;
    private Bundle bundle;

    private SwipeRefreshLayout swipeContainer;
    // private SearchAnimationToolbar toolbar;
    SearchView searchView;
    private TextView searchText;

    public static HotelsFragment newInstance() {
        return new HotelsFragment();
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotels, container, false);

        // rekiperasyon non depatman yo
        DEP_ID  = this.getArguments().getString("dep");

        // ajouter Swiper a la listview
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(R.color.orange,R.color.blue,R.color.green);

        // apelle methode ki ranpli listview a nan fragment an
        fetchListeHotel();

        /**
         * Methode ki rafrechi listview a
         */
        Refresh();

        Backendless.initApp(getActivity().getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

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
                        fetchListeHotel();
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
                fetchListeHotel();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {


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
                queryBuilder.addSortBy("nom");
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

            IDataStore<Map> endroitStorage = Backendless.Data.of("lieu_touristique");
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause("nom like'%" + query + "%' and id_categorie='" + BackendSettings.HOTEL_ID + "'");
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



    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    private void showProgress() {

        loadweb = new ProgressDialog(getActivity());
        loadweb.setMessage("Chargement ...");
        loadweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadweb.getWindow().setGravity(Gravity.CENTER);
        loadweb.show();

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getActivity().getApplicationContext(), "onQueryTextSubmit" + query, Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.isEmpty())
        {
            ListEndroit.clear();
            onRefresh();

        }
        else if (!newText.isEmpty()){
            ListEndroit.clear();
            fetchSearchHotel(newText);
        }

        else {ListEndroit.clear();fetchListeHotel();}

        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getActivity().getApplicationContext(), "onMenuItemActionExpand" + item, Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {

        Toast.makeText(getActivity().getApplicationContext(), "onMenuItemActionCollapse" + item, Toast.LENGTH_SHORT).show();

        return true;
    }

}
