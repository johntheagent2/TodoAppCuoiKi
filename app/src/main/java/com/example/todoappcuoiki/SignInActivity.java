package com.example.todoappcuoiki;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private LinearLayout layoutSignUp;
    private EditText emailEnter, passwordEnter;
    private Button btnSignIn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUI();
        initListener();
    }

    private void initListener() {
        layoutSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        btnSignIn.setOnClickListener(view -> onClickSignIn());
    }

    private void onClickSignIn() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String email = emailEnter.getText().toString().trim();
        String password = passwordEnter.getText().toString().trim();

        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initUI(){
        progressDialog = new ProgressDialog(this);

        layoutSignUp = findViewById(R.id.layout_sign_up);
        emailEnter = findViewById(R.id.email_enter);
        passwordEnter = findViewById(R.id.password_enter);
        btnSignIn = findViewById(R.id.btn_signin);
    }
}