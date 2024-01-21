package com.example.arabicdialectidentificationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    TextInputLayout mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, forgotTextLink;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        initializeWidgets();

        mLoginBtn.setOnClickListener(v -> {

            // Get Email and Password entered by user
            String email = mEmail.getEditText().getText().toString().trim();
            String password = mPassword.getEditText().getText().toString().trim();

            // Validate Inputs
            boolean status = validateFields(email, password);

            if(status == true)
            {
                // Show progress bar
                ProgressDialog pd = new ProgressDialog(LoginPage.this);
                pd.setMessage("Signing in...");
                pd.show();

                fAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(task -> {

                            if(task.isSuccessful() == true)
                            {
                                pd.dismiss();

                                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                pd.dismiss();

                                Toast.makeText(LoginPage.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mCreateBtn.setOnClickListener(v -> {

            Intent intent = new Intent(LoginPage.this, SignUpPage.class);
            startActivity(intent);
        });

        forgotTextLink.setOnClickListener(v -> {

            final EditText resetMail = new EditText(v.getContext());

            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password?");
            passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
            passwordResetDialog.setView(resetMail);

            // Yes Button
            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {

                ProgressDialog pd = new ProgressDialog(LoginPage.this);
                pd.setMessage("Sending....");
                pd.show();

                // extract the email and send reset link
                String mail = resetMail.getText().toString();

                // Sending reset link.
                fAuth.sendPasswordResetEmail(mail)
                        .addOnCompleteListener(task -> {

                                    if(task.isSuccessful() == true)
                                    {
                                        pd.dismiss();
                                        Toast.makeText(LoginPage.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        Toast.makeText(LoginPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
            });

            // No Button
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                // close the dialog
            });

            passwordResetDialog.create().show();

        });
    }

    private boolean validateFields(String email, String password) {

        if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required.");
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

        return true;
    }


    void initializeWidgets()
    {
        fAuth = FirebaseAuth.getInstance();

        // Initialize Elements
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login);
        mCreateBtn = findViewById(R.id.signup);
        forgotTextLink = findViewById(R.id.forget_password);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(intent);
        }
    }
}