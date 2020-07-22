package com.example.myapplication.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.User;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_USER_OBJECT = "EXTRA_USER_OBJECT";
    public static final int REQUEST_CODE_SIGN_UP = 0;
    public static final int REQUEST_CODE_Quiz_Builder = 1;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findAllViews();
        setClickListeners();
    }

    private void setClickListeners() {
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = new User(mEditTextUserName.getText().toString(), mEditTextPassword.getText().toString());
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra(EXTRA_USER_OBJECT, mUser);
                startActivityForResult(intent, REQUEST_CODE_SIGN_UP);
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUser == null || !mEditTextUserName.getText().toString().equals(mUser.getUserName()) || !mEditTextPassword.getText().toString().equals(mUser.getPassword())) {
                    Toast.makeText(LoginActivity.this, "username or password is not correct", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, QuizBuilderActivity.class);
                    intent.putExtra(EXTRA_USER_OBJECT, mUser);
                    startActivityForResult(intent, REQUEST_CODE_Quiz_Builder);
                }
            }
        });
    }

    private void findAllViews() {
        mEditTextUserName = findViewById(R.id.editText_username_login);
        mEditTextPassword = findViewById(R.id.editText_password_login);
        mButtonLogin = findViewById(R.id.button_login);
        mButtonSignUp = findViewById(R.id.button_sign_up);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_SIGN_UP) {
            mUser = (User) data.getSerializableExtra(EXTRA_USER_OBJECT);
            if (mUser != null) {
                mEditTextUserName.setText(mUser.getUserName());
                mEditTextPassword.setText(mUser.getPassword());
            }
        }
    }
}