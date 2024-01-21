package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PracticesPage extends AppCompatActivity {

    ArrayList<ExerciseModel> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practices_page);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("All Practices");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String dialect = intent.getStringExtra("dialect");
        int lesson = intent.getIntExtra("lesson",1);

        int i_start = 0;

        if(lesson==1)
        {
            i_start=0;
        }
        else if(lesson==2)
        {
            i_start=5;
        }
        else if (lesson==3)
        {
            i_start=10;
        }

        exercises = new ArrayList<>();

        switch (dialect) {
            case "EGY":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.egy_exercises[i]));
                }
                break;
            case "NOR":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.nor_exercises[i]));
                }
                break;
            case "LAV":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.lav_exercises[i]));
                }
                break;
            case "GLF":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.glf_exercises[i]));
                }
                break;
            case "MSA":
                for (int i=i_start; i<i_start+5; i++) {
                    exercises.add(new ExerciseModel(ELTexts.msa_exercises[i]));
                }
                break;
        }

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = findViewById(R.id.rxExercises);

        // Initialize contacts
        // Create adapter passing in the sample user data
        PracticesAdapter adapter = new PracticesAdapter(exercises, this, dialect, lesson);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }
}