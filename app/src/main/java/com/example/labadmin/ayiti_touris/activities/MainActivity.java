package com.example.labadmin.ayiti_touris.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.adapters.RecyclerViewAdapter;
import com.example.labadmin.ayiti_touris.utils.ListesEvent;

/*import com.Backendless.Backendless;*/

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {

    //Intent intent;
    String itemDep;
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new DataModel("OUEST", R.drawable.ouest, "#ffffffff","..."));
        arrayList.add(new DataModel("NORD", R.drawable.nord, "#ffffffff","..."));
        arrayList.add(new DataModel("SUD", R.drawable.sud, "#ffffffff","..."));
        arrayList.add(new DataModel("NORD-EST", R.drawable.nord_est, "#4BAA50","..."));
        arrayList.add(new DataModel("ARTIBONITE", R.drawable.artibonite, "#F94336","..."));
        arrayList.add(new DataModel("NIPPES", R.drawable.nippes, "#0A9B88","..."));
        arrayList.add(new DataModel("NORD-OUEST", R.drawable.nord_ouest, "#0A9B88","..."));
        arrayList.add(new DataModel("SUD-EST", R.drawable.sud_est, "#0A9B88","..."));
        arrayList.add(new DataModel("CENTRE", R.drawable.centre, "#0A9B88","..."));
        arrayList.add(new DataModel("GRAND-ANSE", R.drawable.grand_anse, "#0A9B88","..."));


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 300);
        recyclerView.setLayoutManager(layoutManager);


        /**
         Simple GridLayoutManager that spans two columns
         **/
        /*GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);*/
    }

  /*  private void SentDep(String itmd)

    {
        Bundle bundle = new Bundle();
        bundle.putString("departement", itmd);
        intent.putExtras(bundle);
    }*/

    @Override
    public void onItemClick(DataModel item) {
         String itemDepat=item.text;
        Intent intent = new Intent(MainActivity.this, ActivityFragmentListes.class);
        Bundle bundle = new Bundle();
        bundle.putString("departement", itemDepat);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}


