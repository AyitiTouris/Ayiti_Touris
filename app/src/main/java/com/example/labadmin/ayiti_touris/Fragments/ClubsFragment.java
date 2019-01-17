package com.example.labadmin.ayiti_touris.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.example.labadmin.ayiti_touris.utils.Constante;
import com.example.labadmin.ayiti_touris.utils.NetWork;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubsFragment extends Fragment {
    // public static final String AplicationID = "2C703DEF-BB5B-08D7-FFDA-6C2620273000";
    // public static final String SecretKey = "DCF8496C-EF06-1583-FF19-15FBA9ACB000";
    public android.app.ProgressDialog barProgressDialog, proDialog,ProgressDialog,loadweb;
    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    ProgressBar progressBar;
    String DEP_ID;

    private SwipeRefreshLayout swipeContainer;
    private MaterialSearchView searchView;
    public static ClubsFragment newInstance() {
        // Required empty public constructor
        return new ClubsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_clubs, container, false);
        // Inflate the layout for this fragment
        DEP_ID  = this.getArguments().getString("dep");
        fetchListeClubs();
        Backendless.initApp(getActivity().getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        //lvendroit = (ListView) getActivity().findViewById(R.id.lvendroit);
        lvendroit = view.findViewById(R.id.lvendroit);
        ListEndroit = new ArrayList<>();
        adapterendroit = new AdapterEndroit( this.getActivity(),ListEndroit);
        // Setup Adapter
        lvendroit.setAdapter(adapterendroit);
        lvendroit.setTextFilterEnabled(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public void fetchListeClubs(){
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.NORD_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.OUEST_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.SUD_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.CENTRE_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.NIPPES_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.SUD_EST_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.NORD_EST_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.ARTIBONITE_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.GRAND_ANSE_ID + "'");
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

                queryBuilder.setWhereClause("id_categorie='" + BackendSettings.CLUB_ID + "' and lieux_x_dep='" + BackendSettings.NORD_OUEST_ID + "'");
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

    private void showProgress() {

        loadweb = new ProgressDialog(getActivity());
        loadweb.setMessage("Chargement ...");
        loadweb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadweb.getWindow().setGravity(Gravity.CENTER);
        loadweb.show();

    }
}
