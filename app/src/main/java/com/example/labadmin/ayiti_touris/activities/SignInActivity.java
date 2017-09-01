package com.example.labadmin.ayiti_touris.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.labadmin.ayiti_touris.R;

public class SignInActivity extends AppCompatActivity {

    EditText edText;
    EditText edText1;
    Button but;
    Button but1;
    TextView text;
    TextView text1;
    ImageView Pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edText = (EditText) findViewById(R.id.etIdentif);
        edText1 = (EditText) findViewById(R.id.etPass);
        text = (TextView) findViewById(R.id.tvForgot);
        text1 = (TextView) findViewById(R.id.tvOr);
        Pic = (ImageView) findViewById(R.id.ivSignIn);
        but = (Button) findViewById(R.id.btnSignUp);
        but1 = (Button) findViewById(R.id.btnSignIn1);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
    public void onLogButton(View v) {
        Intent i = new Intent(SignInActivity.this, CreateAccountActivity.class);
        startActivity(i);
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
}
