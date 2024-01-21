package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity {

    TextInputLayout mFullName,mEmail,mPassword,mPhone, mCPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setTitle("Sign Up");
        setSupportActionBar(toolbar);

        initializeWidgets();

        if(fAuth.getCurrentUser() != null) {
            Intent intent = new Intent(SignUpPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        mRegisterBtn.setOnClickListener(v -> {

            // Get user data
            final String email = mEmail.getEditText().getText().toString().trim();
            String password = mPassword.getEditText().getText().toString().trim();
            String c_password = mCPassword.getEditText().getText().toString().trim();
            final String fullName = mFullName.getEditText().getText().toString();
            final String phone = mPhone.getEditText().getText().toString();

            boolean status = validateFields(email,password,c_password,fullName,phone);

            if(status)
            {
                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(task -> {

                            if(task.isSuccessful())
                            {
                                FirebaseUser fuser = fAuth.getCurrentUser();

                                // Send verification email.
                                fuser.sendEmailVerification()
                                        .addOnSuccessListener(aVoid -> Toast.makeText(SignUpPage.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Log.d("SignUpTAG", "onFailure: Email not sent " + e.getMessage()));

                                Toast.makeText(SignUpPage.this, "User Created.", Toast.LENGTH_SHORT).show();

                                userID = fAuth.getCurrentUser().getUid();

                                // function
                                addUserData(fullName,email,phone);
                            }
                            else
                            {
                                Toast.makeText(SignUpPage.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

        mLoginBtn.setOnClickListener(v -> {

            Intent intent = new Intent(SignUpPage.this, LoginPage.class);
            startActivity(intent);
        });
    }

    private void addUserData(String fullName, String email, String phone) {

        // Create Object of users table.
        DocumentReference documentReference = fStore.collection("users").document(userID);

        // Create user class.
        UserModel userModel = new UserModel(fullName,email,"92"+phone);

        // Add this object to Firestore
        documentReference.set(userModel)
                .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "onSuccess: user Profile is created for "+ userID, Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "onFailure: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        // Move to homepage.
        Intent intent = new Intent(SignUpPage.this, MainActivity.class);
        startActivity(intent);
    }

    private void initializeWidgets() {

        mFullName   = findViewById(R.id.name);
        mEmail      = findViewById(R.id.email);
        mPassword   = findViewById(R.id.password);
        mCPassword   = findViewById(R.id.c_password);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.register);
        mLoginBtn   = findViewById(R.id.already_user);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    private boolean validateFields(String email,String password,String c_password,String fullName,String phone) {

        if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required.");
            return false;
        }

        matcher = pattern.matcher(email);

        if(matcher.matches() == false){
            mEmail.setError("Wrong email pattern.");
            return false;
        }

        if(TextUtils.isEmpty(phone)){
            mPassword.setError("Phone is Required.");
            return false;
        }


        if(phone.length()!=10 && phone.charAt(0)!=3){
            mPhone.setError("Wrong Phone number.");
            return false;
        }

        if(TextUtils.isEmpty(password)){
            mPassword.setError("Password is Required.");
            return false;
        }

        if(password.length() < 6){
            mPassword.setError("Password Must be >= 6 Characters");
            return false;
        }

        if(!TextUtils.equals(password,c_password)){
            mCPassword.setError("Password does not match.");
            return false;
        }

        return true;
    }

}