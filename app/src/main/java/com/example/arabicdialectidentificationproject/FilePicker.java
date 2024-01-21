package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

public class FilePicker extends AppCompatActivity {
    Button choose;
    TextView pred, score;
    OkHttpClient client;
    static String url = " http://192.168.100.6:5000/predict";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("File Picker");
        setSupportActionBar(toolbar);

        client = new OkHttpClient();

        choose = findViewById(R.id.choose);
        pred = findViewById(R.id.pred);
        score = findViewById(R.id.score);

        choose.setOnClickListener(v -> {

            // Set up the intent to pick a file
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");

            int request_code = 1;

            // Start the file picker activity
            startActivityForResult(intent, request_code);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Uri uri = data.getData();

            dialog = ProgressDialog.show(FilePicker.this, "Predicting",
                    "Predicting Dialect, please wait...", true);

            // Do something with the selected file URI

            Toast.makeText(getApplicationContext(), uri.getPath(), Toast.LENGTH_LONG).show();

            //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "EGY000001.wav");

            String filename = uri.getPath().replace("document/primary:","");

            java.io.File file = new java.io.File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);

            Thread thread = new Thread(() -> {

                String response;
                try {
                    response = post(url, file);
                    String finalResponse = response;
                    dialog.cancel();

                    String[] result = finalResponse.split(",");


                    runOnUiThread(() -> {
                        pred.setText("Prediction: "+ result[0]);
                        score.setText("Dialect Matching: "+ result[1]);
                    });

                } catch (IOException e) {
                    dialog.cancel();
                    throw new RuntimeException(e);
                }
            });

            thread.start();
        }
    }

    public String post(String url, java.io.File audioFile) throws IOException {

        client = new OkHttpClient.Builder()
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

    public String get(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url).get().build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}