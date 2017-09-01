package com.example.labadmin.ayiti_touris.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.models.Evenement;

public class DetailsEvenementActivity extends AppCompatActivity {

    private static final String EXTRA_NAME = "com.herprogramacion.toolbarapp.name";
    private static final String EXTRA_DRAWABLE = "com.herprogramacion.toolbarapp.drawable";


    public static void createInstance(Activity activity, Evenement title) {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);
    }

    /**
     * build an Intent for the context and the activities
     * details.
     *
     * @param context Contexto donde se inicia
     * @param evenement    Identificador de la chica
     * @return Intent user list
     */
   /* public static Intent getLaunchIntent(Context context, Hotel hotel) {
        Intent intent = new Intent(context, DetailsMonumentActivity.class);
        intent.putExtra(EXTRA_NAME, hotel.getName());
        intent.putExtra(EXTRA_DRAWABLE, hotel.getIdDrawable());
        return intent;
    }*/

    public static Intent getLaunchIntent(Context context, Evenement evenement) {
        Intent intent = new Intent(context, DetailsEvenementActivity.class);
        intent.putExtra(EXTRA_NAME, evenement.getName());
        intent.putExtra(EXTRA_DRAWABLE, evenement.getIdDrawable());
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_evenement);

        setToolbar();// Add action bar
        if (getSupportActionBar() != null) // button option
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String name = i.getStringExtra(EXTRA_NAME);
        int idDrawable = i.getIntExtra(EXTRA_DRAWABLE, -1);

        CollapsingToolbarLayout collapser =
                (CollapsingToolbarLayout) findViewById(R.id.collapser);
        collapser.setTitle(name); // Change title

        loadImageParallax(idDrawable);// Change image


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSnackBar("Just add to favorites");
                    }
                }
        );
    }

    private void setToolbar() {
        // Add Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        // using the glide
        Glide.with(this)
                .load(id)
                .centerCrop()
                .into(image);
    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                showSnackBar("Votre preference");
                return true;
            case R.id.action_add:
                showSnackBar("");
                return true;
            case R.id.action_favorite:
                showSnackBar("Favorite");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


}
