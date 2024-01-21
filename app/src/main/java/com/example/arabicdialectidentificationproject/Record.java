package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Record extends AppCompatActivity {

    private final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Arabic Dialect Identification/uservoice.mp4";
    private Button start, stop;
    private MediaRecorder mediaRecorder;
    private TextView pred, score;
    private ProgressDialog dialog;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("Recording Page");
        setSupportActionBar(toolbar);
        start = findViewById(R.id.start_btn);
        stop = findViewById(R.id.stop_btn);
        pred = findViewById(R.id.pred);
        score = findViewById(R.id.score);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, 0);
        }

        start.setOnClickListener(v -> {
            if (!isRecording) {
                startRecording();
            }
        });

        stop.setOnClickListener(v -> {
            if (isRecording) {
                stopRecording();
            }
        });
    }

    private void startRecording() {
        try {
            initMediaRecorder();
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Recording failed to start", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            isRecording = false;
            Toast.makeText(getApplicationContext(), "Recording Stopped.", Toast.LENGTH_SHORT).show();
            dialog = ProgressDialog.show(Record.this, "Predicting",
                    "Predicting Dialect, please wait...", true);
            prediction();
        } catch (RuntimeException stopException) {
            stopException.printStackTrace();
        }
    }

    private void prediction() {
        java.io.File file = new java.io.File(FILE_PATH);

        Thread thread = new Thread(() -> {
            String response;
            try {
                Thread.sleep(3000);
                response = post(FilePicker.url, file);
                String[] result = response.split(",");
                runOnUiThread(() -> {
                    pred.setText("Prediction: " + result[0]);
                    score.setText("Dialect Matching: " + result[1]);
                    dialog.cancel();
                });
            } catch (Exception e) {
                dialog.cancel();
                e.printStackTrace();
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

    private void initMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(FILE_PATH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initMediaRecorder();
        } else {
            Toast.makeText(this, "Audio recording permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
