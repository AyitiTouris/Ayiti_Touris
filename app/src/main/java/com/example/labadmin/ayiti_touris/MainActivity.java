package com.example.labadmin.ayiti_touris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.labadmin.ayiti_touris.utils.ListEvent;

/*import com.Backendless.Backendless;*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Backendless.initApp(this, APPLICATION_ID, API_KEY);*/

        ImageView btnliste =(ImageView)findViewById(R.id.ivhotels);
        btnliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesEndroit.class);
                startActivity(intent);
            }
        });

        ImageView btnMonument =(ImageView)findViewById(R.id.ivmonuments);
        btnMonument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesEndroit.class);
                startActivity(intent);
            }
        });

        ImageView btnRestaurant =(ImageView)findViewById(R.id.ivrestorants);
        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesEndroit.class);
                startActivity(intent);
            }
        });
        ImageView btnPlage =(ImageView)findViewById(R.id.ivplages);
        btnPlage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesEndroit.class);
                startActivity(intent);
            }
        });
        ImageView btnEvenement =(ImageView)findViewById(R.id.ivevenements);
        btnEvenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListEvent.class);
                startActivity(intent);
            }
        });
        ImageView btnClub =(ImageView)findViewById(R.id.ivmaps);
        btnClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListesEndroit.class);
                startActivity(intent);
            }
        });
    }


}
