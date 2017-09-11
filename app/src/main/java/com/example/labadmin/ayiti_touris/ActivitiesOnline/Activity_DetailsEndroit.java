package com.example.labadmin.ayiti_touris.ActivitiesOnline;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.labadmin.ayiti_touris.ModelsOnline.BackendSettings;
import com.example.labadmin.ayiti_touris.activities. MainActivity;
import com.example.labadmin.ayiti_touris.activities. MapsActivity;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.ActivitiesOnline.LoginActivity;
import com.example.labadmin.ayiti_touris.R;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.labadmin.ayiti_touris.ActivitiesOnline.LoginActivity.idUser;

public class Activity_DetailsEndroit extends AppCompatActivity {

    TextView nomEndroit;
    TextView adresseEndroit;
    TextView telephoneEndroit;
    ImageView image1Endroit;
    TextView etoile;
    TextView emailEndroit;
    TextView descriptionEndroit;
    TextView sitewebEndroit;
    FloatingActionButton btnmap,btnfavorite;


    ArrayList<Endroit> list;
    //ListView lvLieu;

    RatingBar ratingBar;
    String image;
    int viewImage;
    //String nom;
    Endroit endroit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_endroit);
       // setTitle();
         //nom= endroit.getNomEndroit();
        //Toast.makeText(Activity_DetailsEndroit.this, ""+nom, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        // toolbar.setNavigationIcon(R.drawable.your_drawable_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what do you want here
                    onBackPressed();
                           }
        });


        endroit = (Endroit) getIntent().getSerializableExtra("Endroit");

       // setTitle("Details hotel");
// find bouton
        // btnretour =(Button)findViewById(R.id.btnretour);
        // btntrailer=(Button)findViewById(R.id.btntrailer);
        //retrieve all fields and set their value

        // gvImage = (GridView) findViewById(R.id.gvImage);
        list = new ArrayList<>();
       // adapterimage = new imageAdapter(this, list);

        //  gvImage.setAdapter(adapterimage);
        // Recupere boutton
        btnmap=(FloatingActionButton)findViewById(R.id.location);
        btnfavorite=(FloatingActionButton)findViewById(R.id.fabFavorite);
        //btnfavorite.setBackgroundResource(R.drawable.ic_favorit_default);


        //recupere nom
        nomEndroit = (TextView)findViewById(R.id.tvnomEndroit);
        nomEndroit .setText(endroit.getNomEndroit());

        CollapsingToolbarLayout collapser =
                (CollapsingToolbarLayout) findViewById(R.id.collapser);
        collapser.setTitle(endroit.getNomEndroit());

        //recupere telephone
        telephoneEndroit=(TextView)findViewById(R.id.tvtelephoneEndroit);
        telephoneEndroit .setText(endroit.getTelephoneEndroit());

        //recupere adresse

        adresseEndroit = (TextView) findViewById(R.id.tvadresseEndroit);
        adresseEndroit.setText(endroit.getAdresseEndroit());

        //recupere email


        emailEndroit = (TextView) findViewById(R.id.tvemailEndroit);

        if(endroit.getEmailEndroit()!=null) {
            emailEndroit.setText(endroit.getEmailEndroit());
        }else {emailEndroit.setVisibility(View.GONE);}

        //recupere sitewebe
        sitewebEndroit = (TextView) findViewById(R.id.tvsitewebEndroit);
        sitewebEndroit.setText(endroit.getSitewebEndroit());

        //recupere la Description
        descriptionEndroit = (TextView) findViewById(R.id.tvdescription);
        descriptionEndroit.setText(endroit.getInformationEndroit());

      // String id= LoginActivity.idUser;

 //nom=endroit.getNomEndroit();
        //recupere la quantite d'etoile
        String etoile;
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        etoile=  endroit.getEtoile();

        if(etoile!=null) {
            ratingBar.setRating(Float.parseFloat(etoile));
        }else {ratingBar.setVisibility(View.GONE);}

        final String Longitude=(endroit.getLongitudeEndroit());
        final String Latitude=(endroit.getLaltitudeEndroit());

        //Toast.makeText(Activity_DetailsEndroit.this, ""+Longitude, Toast.LENGTH_SHORT).show();

        //recupere image
        image1Endroit = (ImageView)findViewById(R.id.ivimage1_paralax);
        //Toast.makeText(Activity_DetailsEndroit.this, ""+viewImage, Toast.LENGTH_SHORT).show();
        viewImage=1;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        if(viewImage==1){
                            image= endroit.getImage1Endroit();
                          //  Toast.makeText(Activity_DetailsEndroit.this, "1", Toast.LENGTH_SHORT).show();
                            Picasso.with(Activity_DetailsEndroit.this).load(image)
                                    // .transform(new RoundedCornersTransformation(20, 20))
                                    .placeholder(R.drawable.placeholder2)
                                    .into(image1Endroit);
                            viewImage++;
                        }else if(viewImage==2){
                            image= endroit.getImage2Endroit();
                           // Toast.makeText(Activity_DetailsEndroit.this, "2", Toast.LENGTH_SHORT).show();
                            Picasso.with(Activity_DetailsEndroit.this).load(image)
                                    // .transform(new RoundedCornersTransformation(20, 20))
                                    .placeholder(R.drawable.placeholder2)
                                    .into(image1Endroit);
                            viewImage++;
                        }else if(viewImage==3){
                            //Toast.makeText(Activity_DetailsEndroit.this, "3", Toast.LENGTH_SHORT).show();
                            image= endroit.getImage3Endroit();
                            Picasso.with(Activity_DetailsEndroit.this).load(image)
                                    // .transform(new RoundedCornersTransformation(20, 20))
                                    .placeholder(R.drawable.placeholder2)
                                    .into(image1Endroit);
                            viewImage++;
                        }else if(viewImage==4){
                            image= endroit.getImage4Endroit();
                          //  Toast.makeText(Activity_DetailsEndroit.this, "4", Toast.LENGTH_SHORT).show();
                            Picasso.with(Activity_DetailsEndroit.this).load(image)
                                    // .transform(new RoundedCornersTransformation(20, 20))
                                     .placeholder(R.drawable.placeholder2)
                                    .into(image1Endroit);
                            viewImage++;
                        }else if(viewImage==5){
                            image= endroit.getImage5Endroit();
                          //  Toast.makeText(Activity_DetailsEndroit.this, "5", Toast.LENGTH_SHORT).show();
                            Picasso.with(Activity_DetailsEndroit.this).load(image)
                                    // .transform(new RoundedCornersTransformation(20, 20))
                                    .placeholder(R.drawable.placeholder2)
                                    .into(image1Endroit);
                            viewImage=1;
                        }else{
                            image= endroit.getImage1Endroit();
                           // Toast.makeText(Activity_DetailsEndroit.this, " "+viewImage, Toast.LENGTH_SHORT).show();
                            Picasso.with(Activity_DetailsEndroit.this).load(image)
                                    // .transform(new RoundedCornersTransformation(20, 20))
                                    .placeholder(R.drawable.placeholder2)
                                    .into(image1Endroit);
                            viewImage=1;
                        }

                    }
                });
            }
        }, 5000, 5000);

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Activity_DetailsEndroit.this, MapsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("Longitude",Longitude);
                extras.putString("Latitude",Latitude);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        btnfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                saveFavorit();
                btnfavorite.setEnabled(false);
               // Toast.makeText(Activity_DetailsEndroit.this, ""+endroit.getId_lieu_touristique(), Toast.LENGTH_SHORT).show();
            }
        });


      /*
        nomHotel.setText(hotel.getNomHotel());String si= hotel.getImage();

        String s[]=  si.split(";");
        for(int i=0; i<s.length; i++){
            System.out.println(i+": "+s[i]);
        }*/


        /*IDataStore<Map> personneStorage = Backendless.Data.of( "lieu_touristique" );
        IDataStore<Map> imageStorage = Backendless.Data.of( "images" );
        //DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        personneStorage.find(new AsyncCallback<List<Map>>() {

            @Override
            public void handleResponse(List<Map> response) {

                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                //recette = (recettes.fromListMap(response));
                adapterimage.addAll(Hotel.fromListMap(response));
                adapterimage.notifyDataSetChanged();
                Log.d("DEBUG",gvImage.toString());


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(getApplicationContext(), fault.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });*/

        /*tvReleaseDate = ButterKnife.findById(this, R.id.release_date);
        tvReleaseDate.setText("Release date: " + movie.getReleaseDate());

        tvSynopsis = ButterKnife.findById(this, R.id.synopsis);
        tvSynopsis.setText(movie.getOverview());*/

    }

    public void saveFavorit() {

        Backendless.initApp(getApplicationContext(), BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        HashMap testObject = new HashMap<>();
        testObject.put("nom", endroit.getNomEndroit());
        testObject.put("adresse", endroit.getAdresseEndroit());
        testObject.put("etoile", endroit.getEtoile());
        testObject.put("telephone", endroit.getTelephoneEndroit());
        testObject.put("id_lieu_touristique", endroit.getId_lieu_touristique());
        testObject.put("id_user", LoginActivity.idUser);
        testObject.put("image1", endroit.getImage1Endroit());

        if (LoginActivity.idUser != null) {
            Backendless.Data.of("favorite").save(testObject, new AsyncCallback<Map>() {
                @Override
                public void handleResponse(Map response) {

                    Toast.makeText(Activity_DetailsEndroit.this, "Favorit", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("MYAPP", "Server reported an error " + fault.getMessage());
                    Toast.makeText(getApplicationContext(), "You clicked on YES" +fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    else {
            Toast.makeText(Activity_DetailsEndroit.this, "vous devez connecter, ou creer un compte", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_DetailsEndroit.this);
            alertDialog.setTitle("Connexion");
            alertDialog.setMessage("Vous devez etre connecter");
            //alertDialog.setIcon(R.drawable.ic_launcher);

            alertDialog.setNegativeButton("NON",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,  int which) {
                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            btnfavorite.setEnabled(true);
                            dialog.dismiss();
                           // tv.setText("No Button clicked");
                        }
                    });
            alertDialog.setPositiveButton("OUI",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Intent intent=new Intent(Activity_DetailsEndroit.this,LoginActivity.class);
                            startActivity(intent);
                            btnfavorite.setEnabled(true);
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            //tv.setText("Yes Button clicked");
                        }
                    });

            alertDialog.show();

        }

    }


    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                //showSnackBar("Votre preference");
                return true;
            case R.id.action_add:
                //showSnackBar("");
                return true;

            case R.id.action_share:
               // shareImage(endroit.getImage1Endroit())
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                //showSnackBar("Favorite");
                Intent intent=new Intent(Activity_DetailsEndroit.this,Activity_ListeFavorite.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
