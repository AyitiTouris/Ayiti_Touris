package com.example.labadmin.ayiti_touris.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.labadmin.ayiti_touris.R;
import com.example.labadmin.ayiti_touris.models.Hotel;

public class DetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NAME = "com.herprogramacion.toolbarapp.name";
    private static final String EXTRA_DRAWABLE = "com.herprogramacion.toolbarapp.drawable";

    private static final String LOG_TXT = "DetailsActivity";


    ViewPager mViewPager;

    CustomPagerAdapter mCustomPagerAdapter;
    int[] mResources = {
            R.drawable.oasis_one,
            R.drawable.oasis_two,
            R.drawable.oasis_three,
            R.drawable.oasis_four,
            R.drawable.oasis_five,

    };



    public static void createInstance(Activity activity, Hotel title) {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);
    }

    /**
     * build an Intent for the context and the activities
     * details.
     *
     * @param context Contexto donde se inicia
     * @param hotel    Identificador de la chica
     * @return Intent user list
     */
    public static Intent getLaunchIntent(Context context, Hotel hotel) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_NAME, hotel.getName());
        intent.putExtra(EXTRA_DRAWABLE, hotel.getIdDrawable());
        return intent;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
        FloatingActionButton locat = (FloatingActionButton) findViewById(R.id.location);

        locat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callMap();

                Log.i(LOG_TXT,"Onclick Call");
            }
        });

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSnackBar("Just add to favorites");
                    }
                }
        );

        mCustomPagerAdapter = new CustomPagerAdapter(this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);


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

    void callMap()
    {
        Intent i = new Intent(DetailsActivity.this, MapsActivity.class);
        startActivity(i);

    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }



}
