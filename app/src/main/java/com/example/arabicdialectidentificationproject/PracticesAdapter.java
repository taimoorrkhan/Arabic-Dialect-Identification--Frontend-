package com.example.arabicdialectidentificationproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PracticesAdapter extends RecyclerView.Adapter<PracticesAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<ExerciseModel> mExercise;
    private Context mContext;
    private String dialect;
    private int lesson;

    // Pass in the contact array into the constructor
    public PracticesAdapter(List<ExerciseModel> exercise, Context mContext, String dialect, int lesson) {
        mExercise = exercise;
        this.mContext = mContext;
        this.dialect = dialect;
        this.lesson = lesson;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.dialect, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        ExerciseModel exercise = mExercise.get(position);

        holder.text.setText(exercise.getExercise_text());
        holder.exercise.setOnClickListener(v -> {
            Log.d("ADITAG",exercise.getExercise_text());
            Intent intent = new Intent(mContext.getApplicationContext(), PracticePage.class);
            intent.putExtra("string",exercise.getExercise_text());
            intent.putExtra("dialect",dialect);
            intent.putExtra("lesson",lesson);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mExercise.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView text;
        public CardView exercise;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            text = itemView.findViewById(R.id.exercise_text);
            exercise = itemView.findViewById(R.id.exercise);
        }
    }
}