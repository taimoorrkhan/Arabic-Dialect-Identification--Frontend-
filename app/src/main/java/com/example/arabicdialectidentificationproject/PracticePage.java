package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PracticePage extends AppCompatActivity {

    ImageView start, pause, forward, rewind;
    TextView exercise;
    Handler handler = new Handler();
    Runnable runnable;
    SeekBar seekBar;
    JSONObject obj = new JSONObject();
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_page);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("Practice Page");
        setSupportActionBar(toolbar);

        getVoices();

        Intent intent = getIntent();
        String myString = intent.getStringExtra("string");

        String dialect = intent.getStringExtra("dialect");
        int lesson = intent.getIntExtra("lesson",1);

        start = findViewById(R.id.start_btn);
        pause = findViewById(R.id.pause_btn);
        forward = findViewById(R.id.forward);
        rewind = findViewById(R.id.rewind);
        seekBar = findViewById(R.id.seekbar);
        exercise = findViewById(R.id.exercise_text_1);

        exercise.setText(myString);

        int voice = getVoice(myString);

        mediaPlayer = MediaPlayer.create(this, voice);

        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };

        start.setOnClickListener(view -> {
            start.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            //seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(runnable,0);
        });

        pause.setOnClickListener(view -> {
            pause.setVisibility(View.GONE);
            start.setVisibility(View.VISIBLE);
            mediaPlayer.pause();
            handler.removeCallbacks(runnable);
        });


        forward.setOnClickListener(view -> {
            int current_postion = mediaPlayer.getCurrentPosition();
            int duration1 = mediaPlayer.getDuration();

            if(mediaPlayer.isPlaying() && duration1 != current_postion)
            {
                current_postion += 5000;
                mediaPlayer.seekTo(current_postion);
            }
        });

        rewind.setOnClickListener(view -> {
            int current_postion = mediaPlayer.getCurrentPosition();

            if(mediaPlayer.isPlaying() && current_postion > 5000)
            {
                current_postion -= 5000;
                mediaPlayer.seekTo(current_postion);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(mediaPlayer1 -> {
            pause.setVisibility(View.GONE);
            start.setVisibility(View.VISIBLE);
            mediaPlayer.seekTo(0);
        });
    }

    int getVoice(String text)
    {
        try
        {
             int voice = (int) obj.get(text);
             return voice;
        }
        catch (JSONException e)
        {
            return R.raw.v1;
        }
    }

    void getVoices()
    {
        try
        {
            obj.put(ELTexts.egy_exercises[0], R.raw.v1);
            obj.put(ELTexts.egy_exercises[1], R.raw.v2);
            obj.put(ELTexts.egy_exercises[2], R.raw.v3);
            obj.put(ELTexts.egy_exercises[3], R.raw.v4);
            obj.put(ELTexts.egy_exercises[4], R.raw.v5);
            obj.put(ELTexts.egy_exercises[5], R.raw.v6);
            obj.put(ELTexts.egy_exercises[6], R.raw.v7);
            obj.put(ELTexts.egy_exercises[7], R.raw.v8);
            obj.put(ELTexts.egy_exercises[8], R.raw.v9);
            obj.put(ELTexts.egy_exercises[9], R.raw.v10);
            obj.put(ELTexts.egy_exercises[10], R.raw.v11);
            obj.put(ELTexts.egy_exercises[11], R.raw.v12);
            obj.put(ELTexts.egy_exercises[12], R.raw.v13);
            obj.put(ELTexts.egy_exercises[13], R.raw.v14);
            obj.put(ELTexts.egy_exercises[14], R.raw.v15);

            obj.put(ELTexts.glf_exercises[0], R.raw.v16);
            obj.put(ELTexts.glf_exercises[1], R.raw.v17);
            obj.put(ELTexts.glf_exercises[2], R.raw.v18);
            obj.put(ELTexts.glf_exercises[3], R.raw.v19);
            obj.put(ELTexts.glf_exercises[4], R.raw.v20);
            obj.put(ELTexts.glf_exercises[5], R.raw.v21);
            obj.put(ELTexts.glf_exercises[6], R.raw.v22);
            obj.put(ELTexts.glf_exercises[7], R.raw.v23);
            obj.put(ELTexts.glf_exercises[8], R.raw.v24);
            obj.put(ELTexts.glf_exercises[9], R.raw.v25);
            obj.put(ELTexts.glf_exercises[10], R.raw.v26);
            obj.put(ELTexts.glf_exercises[11], R.raw.v27);
            obj.put(ELTexts.glf_exercises[12], R.raw.v28);
            obj.put(ELTexts.glf_exercises[13], R.raw.v29);
            obj.put(ELTexts.glf_exercises[14], R.raw.v30);

            obj.put(ELTexts.lav_exercises[0], R.raw.v31);
            obj.put(ELTexts.lav_exercises[1], R.raw.v32);
            obj.put(ELTexts.lav_exercises[2], R.raw.v33);
            obj.put(ELTexts.lav_exercises[3], R.raw.v34);
            obj.put(ELTexts.lav_exercises[4], R.raw.v35);
            obj.put(ELTexts.lav_exercises[5], R.raw.v36);
            obj.put(ELTexts.lav_exercises[6], R.raw.v37);
            obj.put(ELTexts.lav_exercises[7], R.raw.v38);
            obj.put(ELTexts.lav_exercises[8], R.raw.v39);
            obj.put(ELTexts.lav_exercises[9], R.raw.v40);
            obj.put(ELTexts.lav_exercises[10], R.raw.v41);
            obj.put(ELTexts.lav_exercises[11], R.raw.v42);
            obj.put(ELTexts.lav_exercises[12], R.raw.v43);
            obj.put(ELTexts.lav_exercises[13], R.raw.v44);
            obj.put(ELTexts.lav_exercises[14], R.raw.v45);

            obj.put(ELTexts.msa_exercises[0], R.raw.v46);
            obj.put(ELTexts.msa_exercises[1], R.raw.v47);
            obj.put(ELTexts.msa_exercises[2], R.raw.v48);
            obj.put(ELTexts.msa_exercises[3], R.raw.v49);
            obj.put(ELTexts.msa_exercises[4], R.raw.v50);
            obj.put(ELTexts.msa_exercises[5], R.raw.v51);
            obj.put(ELTexts.msa_exercises[6], R.raw.v52);
            obj.put(ELTexts.msa_exercises[7], R.raw.v53);
            obj.put(ELTexts.msa_exercises[8], R.raw.v54);
            obj.put(ELTexts.msa_exercises[9], R.raw.v55);
            obj.put(ELTexts.msa_exercises[10], R.raw.v56);
            obj.put(ELTexts.msa_exercises[11], R.raw.v57);
            obj.put(ELTexts.msa_exercises[12], R.raw.v58);
            obj.put(ELTexts.msa_exercises[13], R.raw.v59);
            obj.put(ELTexts.msa_exercises[14], R.raw.v60);

            obj.put(ELTexts.nor_exercises[0], R.raw.v61);
            obj.put(ELTexts.nor_exercises[1], R.raw.v62);
            obj.put(ELTexts.nor_exercises[2], R.raw.v63);
            obj.put(ELTexts.nor_exercises[3], R.raw.v64);
            obj.put(ELTexts.nor_exercises[4], R.raw.v65);
            obj.put(ELTexts.nor_exercises[5], R.raw.v66);
            obj.put(ELTexts.nor_exercises[6], R.raw.v67);
            obj.put(ELTexts.nor_exercises[7], R.raw.v68);
            obj.put(ELTexts.nor_exercises[8], R.raw.v69);
            obj.put(ELTexts.nor_exercises[9], R.raw.v70);
            obj.put(ELTexts.nor_exercises[10], R.raw.v71);
            obj.put(ELTexts.nor_exercises[11], R.raw.v72);
            obj.put(ELTexts.nor_exercises[12], R.raw.v73);
            obj.put(ELTexts.nor_exercises[13], R.raw.v74);
            obj.put(ELTexts.nor_exercises[14], R.raw.v75);
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mediaPlayer.stop();
        super.onPause();
    }
}