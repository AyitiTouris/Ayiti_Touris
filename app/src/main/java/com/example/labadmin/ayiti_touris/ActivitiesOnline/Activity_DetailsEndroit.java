package com.example.labadmin.ayiti_touris.ActivitiesOnline;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labadmin.ayiti_touris.MainActivity;
import com.example.labadmin.ayiti_touris.MapsActivity;
import com.example.labadmin.ayiti_touris.ModelsOnline.Endroit;
import com.example.labadmin.ayiti_touris.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_endroit);


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
                //Intent intent=new Intent(Activity_DetailsEndroit.this,Activity_ListeEndroit.class);
                //startActivity(intent);
            }
        });

        final Endroit endroit = (Endroit) getIntent().getSerializableExtra("Endroit");
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



        //recupere nom
        nomEndroit = (TextView)findViewById(R.id.tvnomEndroit);
        nomEndroit .setText(endroit.getNomEndroit());

        //recupere telephone
        telephoneEndroit=(TextView)findViewById(R.id.tvtelephoneEndroit);
        telephoneEndroit .setText(endroit.getTelephoneEndroit());

        //recupere adresse
        adresseEndroit = (TextView) findViewById(R.id.tvadresseEndroit);
        adresseEndroit.setText(endroit.getAdresseEndroit());

        //recupere email


        emailEndroit = (TextView) findViewById(R.id.tvemailEndroit);
        emailEndroit.setText(endroit.getEmailEndroit());

        //recupere sitewebe
        sitewebEndroit = (TextView) findViewById(R.id.tvsitewebEndroit);
        sitewebEndroit.setText(endroit.getSitewebEndroit());

        //recupere la Description
        descriptionEndroit = (TextView) findViewById(R.id.tvdescription);
        descriptionEndroit.setText(endroit.getInformationEndroit());


        //recupere la quantite d'etoile
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        ratingBar.setRating(endroit.getEtoile());

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
}
