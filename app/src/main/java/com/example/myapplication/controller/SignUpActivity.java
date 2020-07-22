package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonSignUp;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findAllViews();
        setOnClickListeners();
        checkingIntent();
    }

    private void checkingIntent() {
        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.EXTRA_USER_OBJECT);
        if (mUser != null) {
            mEditTextUserName.setText(mUser.getUserName());
            mEditTextPassword.setText(mUser.getPassword());
        }
    }

    private void setOnClickListeners() {
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditTextUserName.getText().toString().equals("") && !mEditTextPassword.getText().toString().equals("")) {
                    mUser = new User(mEditTextUserName.getText().toString(), mEditTextPassword.getText().toString());
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.putExtra(LoginActivity.EXTRA_USER_OBJECT, mUser);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Please fill both boxes!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findAllViews() {
        mEditTextUserName = findViewById(R.id.editText_username_sign_up);
        mEditTextPassword = findViewById(R.id.editText_password_sign_up);
        mButtonSignUp = findViewById(R.id.button_sign_up);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Back button is locked click on Sign up!", Toast.LENGTH_LONG).show();
    }
}