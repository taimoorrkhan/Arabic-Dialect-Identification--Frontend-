package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class FeedbackPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("Feedback");
        setSupportActionBar(toolbar);
    }
}