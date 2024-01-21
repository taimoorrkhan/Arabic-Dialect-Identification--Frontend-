package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    Button get_started, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        initializeWidgets();

        get_started.setOnClickListener(view -> {
            Intent intent = new Intent(FirstPage.this, LoginPage.class);
            startActivity(intent);
        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(FirstPage.this, SignUpPage.class);
            startActivity(intent);
        });
    }

    private void initializeWidgets()
    {
        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("Arabic Dialect Learning");
        setSupportActionBar(toolbar);

        get_started = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
    }
}