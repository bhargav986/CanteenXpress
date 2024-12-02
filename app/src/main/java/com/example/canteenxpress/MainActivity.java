package com.example.canteenxpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextInputEditText firstname, lastname, email, password, confirmPassword;
    TextView loginLink;
    Button signUpBtn;
    ImageView google, facebook, instagram;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginLink = findViewById(R.id.loginLink);
        signUpBtn = findViewById(R.id.signUpBtn);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getfirstname = firstname.getText().toString();
                String getlastname = lastname.getText().toString();
                String getemail = email.getText().toString();
                String getpassword = password.getText().toString();
                String getconfimPassword = confirmPassword.getText().toString();

                if (getfirstname.isEmpty() || getlastname.isEmpty() || getpassword.isEmpty() || getconfimPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fill up details!!", Toast.LENGTH_SHORT).show();
                } else {
                    PerformAuth();
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("FIRSTNAME", getfirstname);
                    editor.putString("LASTNAME", getlastname);
                    editor.putString("EMAIL", getemail);
                    editor.apply();
                }
            }
        });
    }

    private void PerformAuth() {
        String getemail = email.getText().toString();
        String getpassword = password.getText().toString();
        String getconfimPassword = confirmPassword.getText().toString();

        if (!getemail.matches(emailPattern)) {
            email.setError("Enter valid email");
        } else if (getpassword.length() < 6) {
            password.setError("Enter valid passsword");
        } else if (!getpassword.matches(getconfimPassword)) {
            confirmPassword.setError("Password does not matches");
        } else {
            progressDialog.setMessage("Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(getemail, getpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Registration Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}