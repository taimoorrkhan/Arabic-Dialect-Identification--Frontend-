package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class PracticeExercises extends AppCompatActivity {

    private final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private final int SAMPLE_RATE = 44100;
    private final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_ENCODING);
    private final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Arabic Dialect Identification/file2.mp4";

    private boolean isRecording = false;
    private AudioRecord recorder = null;
    private Thread recordingThread = null;

    Button start, stop;
    MediaRecorder mediaRecorder;

    FileOutputStream outputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_exercises);

        mediaRecorder = new MediaRecorder();
        init();

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

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
        });
    }


    void init() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(FILE_PATH);
    }


    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String[] permissions = {android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public void startRecording() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
            return;
        }
        recorder = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_ENCODING, BUFFER_SIZE);
        recorder.startRecording();
            isRecording = true;
            recordingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    writeAudioDataToFile();
                }
            }, "AudioRecorder Thread");
            recordingThread.start();
        }

    private void writeAudioDataToFile() {

        byte[] buffer = new byte[BUFFER_SIZE];

        try {
            outputStream = new FileOutputStream(FILE_PATH);
        } catch (FileNotFoundException e) {
            Log.d("ADITAG", e.getMessage());
        }

        int read;

        while (isRecording) {
            read = recorder.read(buffer, 0, BUFFER_SIZE);
            if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                try {
                    outputStream.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (recorder != null) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
        }

        // Close the FileOutputStream object
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The required permissions have been granted, start recording
                startRecording();
            } else {
                // The user did not grant the required permissions, show an error message or take other appropriate action
                Toast.makeText(this, "Audio recording permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}