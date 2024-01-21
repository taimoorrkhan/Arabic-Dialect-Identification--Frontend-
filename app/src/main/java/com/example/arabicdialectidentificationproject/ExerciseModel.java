package com.example.arabicdialectidentificationproject;

public class ExerciseModel {

    String exercise_text;

    ExerciseModel(String exercise_text)
    {
        this.exercise_text = exercise_text;
    }


    ExerciseModel(){}

    void setExercise_text(String exercise_text)
    {
        this.exercise_text = exercise_text;
    }

    String getExercise_text()
    {
        return this.exercise_text;
    }
}
