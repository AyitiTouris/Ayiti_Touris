package com.example.labadmin.ayiti_touris.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.labadmin.ayiti_touris.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by labadmin on 8/31/17.
 */

class CreateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        //Display the up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spiSexe);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Sexe");
        categories.add("M");
        categories.add("F");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            if(item.equals("Sexe")){
            }
        }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Respond to the action bar's Up/Home button
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onLogButton(View v) {
        if(spinner.getSelectedItem().toString()!="Sexe"){
            Intent i = new Intent(CreateAccountActivity.this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "Sous peu, vous recevrez un message relatif à votre demande", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Select sex", Toast.LENGTH_SHORT).show();
        }
    }
}



