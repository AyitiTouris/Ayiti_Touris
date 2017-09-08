package com.example.labadmin.ayiti_touris.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import com.example.labadmin.ayiti_touris.ActivitiesOnline.Activity_ListeHotel;

import com.example.labadmin.ayiti_touris.R;

import com.example.labadmin.ayiti_touris.utils.ListesClub;
import com.example.labadmin.ayiti_touris.utils.ListesEvent;
import com.example.labadmin.ayiti_touris.utils.ListesMonument;
import com.example.labadmin.ayiti_touris.utils.ListesPlage;
import com.example.labadmin.ayiti_touris.utils.ListesResto;

/*import com.Backendless.Backendless;*/

public class MainActivity extends AppCompatActivity {
    String Hotel="Hotel",Club="Club",Monument="Monument",Restaurant="Restaurant";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Backendless.initApp(this, APPLICATION_ID, API_KEY);*/

        ImageView btnliste =(ImageView)findViewById(R.id.ivhotels);


        btnliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Activity_ListeHotel.class);
                intent.putExtra("Hotel",Hotel);
                startActivity(intent);
            }
        });

        ImageView btnMonument =(ImageView)findViewById(R.id.ivmonuments);
        btnMonument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesMonument.class);
                startActivity(intent);
            }
        });

        ImageView btnRestaurant =(ImageView)findViewById(R.id.ivrestorants);
        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesResto.class);
                startActivity(intent);
            }
        });
        ImageView btnPlage =(ImageView)findViewById(R.id.ivplages);
        btnPlage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesPlage.class);
                startActivity(intent);
            }
        });
        ImageView btnEvenement =(ImageView)findViewById(R.id.ivevenements);
        btnEvenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesEvent.class);
                startActivity(intent);
            }
        });
        ImageView btnClub =(ImageView)findViewById(R.id.ivclubs);

         /*   btnClub.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ListesClub.class);
                    intent.putExtra("Club", Club);
                    startActivity(intent);
                }
            });*/





         /*   btnClub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Intent intent=new Intent(MainActivity.this,ListesClub.class);
                    Intent intent = new Intent(MainActivity.this, Activity_ListeHotel.class);
                    intent.putExtra("Club", Club);
                    startActivity(intent);
                }
            });*/
       // }

        btnClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesClub.class);
                startActivity(intent);
               // Intent intent=new Intent(MainActivity.this,LoginActivity.class);
               // startActivity(intent);
            }
        });


    }

   /* protected void checkNetworkConnectivity() {
        // TODO Auto-generated method stub
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi =
                connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile =
                connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( wifi.isAvailable() || mobile.isAvailable()){




        }

        else{
            Intent intent=new Intent(MainActivity.this,ListesHotel.class);
            //intent.putExtra("Hotel",Hotel);
            startActivity(intent);
            Toast.makeText(this, "No Network Available" , Toast.LENGTH_LONG).show();
        }
    }*/
}

//}
