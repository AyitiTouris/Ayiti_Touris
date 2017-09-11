package com.example.labadmin.ayiti_touris.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.example.labadmin.ayiti_touris.R;

public class ListesEvent extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    String currentUrl;
    WebView webvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes_event);

        setToolbar();
        if (getSupportActionBar() != null) // button option
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mProgressDialog = setupProgressDialog();
        Progress();
        webvalue = (WebView)findViewById(R.id.web_value);

        webvalue.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        webvalue.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressDialog.dismiss();
            }
        });

        webvalue.loadUrl("https://www.instagram.com/partyinginhaiti/");

    }


    // TODO: check if using ProgressBar is better
    public void Progress() {
        mProgressDialog =  new ProgressDialog(ListesEvent.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Chargement");
        mProgressDialog.show();

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


        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }
}
