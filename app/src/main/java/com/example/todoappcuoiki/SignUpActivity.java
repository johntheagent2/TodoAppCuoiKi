package com.example.todoappcuoiki;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailCreate, passCreate;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUI();
        initListener();
    }

    private void initListener() {

        btnSignUp.setOnClickListener(view -> onCLickSignUp());

    }

    private void onCLickSignUp() {
        String email = emailCreate.getText().toString().trim();
        String password = passCreate.getText().toString().trim();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    } else{
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initUI(){
        emailCreate = findViewById(R.id.email_create);
        passCreate = findViewById(R.id.password_create);
        btnSignUp = findViewById(R.id.btn_signup);
        progressDialog = new ProgressDialog(this);
    }
}