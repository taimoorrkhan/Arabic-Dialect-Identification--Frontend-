package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LessonPage extends AppCompatActivity {

    private final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Arabic Dialect Identification/uservoice.mp4";
    Button start, stop;
    MediaRecorder mediaRecorder;
    TextView exercise, pred, score;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("Lesson Page");
        setSupportActionBar(toolbar);

        mediaRecorder = new MediaRecorder();

        Intent intent = getIntent();
        String myString = intent.getStringExtra("string");

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(LessonPage.this, new String[]{android.Manifest.permission.RECORD_AUDIO}, 0);

        } else {

            init();

        }

        start = findViewById(R.id.start_btn);
        stop = findViewById(R.id.stop_btn);
        exercise = findViewById(R.id.exercise_text_1);
        pred = findViewById(R.id.pred);
        score = findViewById(R.id.score);

        exercise.setText(myString);

        start.setOnClickListener(v -> {
            Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();

            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mediaRecorder.start();


        });

        stop.setOnClickListener(v -> {
            mediaRecorder.stop();
            mediaRecorder.release();
            Toast.makeText(getApplicationContext(), "Recording Stopped.", Toast.LENGTH_SHORT).show();
            dialog = ProgressDialog.show(LessonPage.this, "Predicting",
                    "Predicting Dialect, please wait...", true);
            prediction();
        });
    }

    void prediction()
    {
        java.io.File file = new java.io.File(FILE_PATH);

        Thread thread = new Thread(() -> {

            String response;
            try {
                Thread.sleep(3000);
                response = post(FilePicker.url, file);
                String finalResponse = response;
                String[] result = finalResponse.split(",");
                //runOnUiThread(() -> );

                runOnUiThread(() -> {
                    pred.setText("Prediction: "+ result[0]);
                    score.setText("Dialect Matching: "+ result[1]);
                    dialog.cancel();
                });

            } catch (Exception e) {
                dialog.cancel();
                throw new RuntimeException(e);
            }
        });

        thread.start();
    }

    public String post(String url, java.io.File audioFile) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody body = RequestBody.create(mediaType, audioFile);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("", audioFile.getName(), body)
                .build();

        Request request = new Request.Builder()
                .url(url).post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    void init() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(FILE_PATH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The required permissions have been granted, start recording
                Toast.makeText(getApplicationContext(),  "Mic permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                // The user did not grant the required permissions, show an error message or take other appropriate action
                Toast.makeText(this, "Audio recording permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}