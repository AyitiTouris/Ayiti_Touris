package com.example.labadmin.ayiti_touris.utils;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.adapters.AdapterMonuments;
import com.example.labadmin.ayiti_touris.models.Monuments;

public class ListesMonument extends AppCompatActivity {

    private RecyclerView recycler;
    private LinearLayoutManager lManager;
    private CollapsingToolbarLayout collapser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes_monument);

        setToolbar();// Toolbar

        AdapterMonuments adaptador = new AdapterMonuments(this, Monuments.randomList(5));

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);


        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Creer un nouveau adapteur

        recycler.setAdapter(adaptador);


    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        finish();
        switch (id) {
            case R.id.action_search:
                showSnackBar("Rechercher votre Monument...");
                return true;
            case R.id.action_settings:
                showSnackBar("Votre preference");
                return true;
            case R.id.action_account:
                showSnackBar("Votre Profile");
                return true;
            case R.id.action_about:
                showSnackBar("A Propos de Nous");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Proyecta una {@link Snackbar} con el string usado
     *
     * @param msg Mensaje
     */
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

}

