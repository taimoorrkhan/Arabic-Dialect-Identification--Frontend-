package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    CardView record, file, practice, aboutus, feedback;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final String[] PERMISSIONS = {
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Homepage");

        record = findViewById(R.id.record);
        file = findViewById(R.id.file);
        practice = findViewById(R.id.practice);
        aboutus = findViewById(R.id.aboutus);
        feedback = findViewById(R.id.feedback);

        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_RECORD_AUDIO_PERMISSION);
        } else {
            initializeListeners();
        }
    }

    private void initializeListeners() {
        record.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Record.class)));
        file.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FilePicker.class)));
        practice.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PracticeDialects.class)));
        aboutus.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutUsPage.class)));
        feedback.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FeedbackPage.class)));
    }

    private boolean hasPermissions() {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeListeners();
              //  Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginPage.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
