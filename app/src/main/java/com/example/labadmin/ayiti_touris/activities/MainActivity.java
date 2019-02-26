package com.example.labadmin.ayiti_touris.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
import com.example.labadmin.ayiti_touris.adapters.RecyclerViewAdapter;
import com.example.labadmin.ayiti_touris.utils.ListesEvent;
import com.example.labadmin.ayiti_touris.utils.NetWork;

/*import com.Backendless.Backendless;*/

public class MainActivity extends AppCompatActivity  {

    //Intent intent;
    ArrayList<DepartementModel> ListDepartement;
    ListView lvDepartement;
    AdapterDepartement adapterDep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fetchListeHotel();
        Backendless.initApp(getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

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

    public void fetchListeHotel(){
        boolean connexion= NetWork.checkConnexion(this);
        if(!connexion)
        {

            Toast.makeText(this,"impossible de continuer, verifier la connexion internet", Toast.LENGTH_LONG).show();
        } else {

            IDataStore<Map> endroitStorage = Backendless.Data.of("departement");
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.addSortBy("classement");

            //queryBuilder.setWhereClause("id_categorie='" + BackendSettings.HOTEL_ID +"'");
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

}


