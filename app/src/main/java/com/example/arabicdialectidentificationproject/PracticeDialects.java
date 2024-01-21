package com.example.arabicdialectidentificationproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class PracticeDialects extends AppCompatActivity {

    CardView egy, msa, nor, lav, glf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_dialects);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("Practice Dialects");
        setSupportActionBar(toolbar);

        egy = findViewById(R.id.egy);
        msa = findViewById(R.id.msa);
        nor = findViewById(R.id.nor);
        lav = findViewById(R.id.lav);
        glf = findViewById(R.id.glf);

        egy.setOnClickListener(v -> {
            Intent intent = new Intent(PracticeDialects.this, LessonsPracticePage.class);
            intent.putExtra("dialect", "EGY");
            startActivity(intent);
        });

        msa.setOnClickListener(v -> {
            Intent intent = new Intent(PracticeDialects.this, LessonsPracticePage.class);
            intent.putExtra("dialect", "MSA");
            startActivity(intent);
        });

        nor.setOnClickListener(v -> {
            Intent intent = new Intent(PracticeDialects.this, LessonsPracticePage.class);
            intent.putExtra("dialect", "NOR");
            startActivity(intent);
        });

        lav.setOnClickListener(v -> {
            Intent intent = new Intent(PracticeDialects.this, LessonsPracticePage.class);
            intent.putExtra("dialect", "LAV");
            startActivity(intent);
        });

        glf.setOnClickListener(v -> {
            Intent intent = new Intent(PracticeDialects.this, LessonsPracticePage.class);
            intent.putExtra("dialect", "GLF");
            startActivity(intent);
        });
    }
}