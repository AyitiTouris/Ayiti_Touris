package com.example.labadmin.ayiti_touris.ActivitiesOnline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_ListeFavorite extends AppCompatActivity {

    ArrayList<Endroit> ListEndroit;
    ListView lvendroit;
    AdapterEndroit adapterendroit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listefavorite);


        Backendless.initApp(getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        lvendroit = (ListView) findViewById(R.id.lvendroit);
        ListEndroit = new ArrayList<>();
        adapterendroit = new AdapterEndroit(this, ListEndroit);

        lvendroit.setAdapter(adapterendroit);
        lvendroit.setTextFilterEnabled(true);

        IDataStore<Map> endroitStorage = Backendless.Data.of("favorite");
        //IDataStore<Map> imageStorage = Backendless.Data.of( "images" );
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();


        queryBuilder.setWhereClause("id_user='"+LoginActivity.idUser +"'");

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
