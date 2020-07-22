package com.example.myapplication.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;


public class CheatActivity extends AppCompatActivity {
    public static final String BUNDLE_CHEAT_IS_CLICKED = "BUNDLE_CHEAT_IS_CLICKED";
    public static final String EXTRA_IS_CHEATED = "isCheated";
    private Button mCheatButton;
    private TextView mShowAnswer;
    private boolean mIsAnswerTrue;
    private Button mBackButton;
    private boolean mCheatButtonIsClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        setAllViews();
        Intent intent = getIntent();
        mIsAnswerTrue = intent.getBooleanExtra(QuizActivity.IS_ANSWER_TRUE, false);
        setClickListeners();
        checkingBundle(savedInstanceState);
    }

    private void checkingBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCheatButtonIsClicked = savedInstanceState.getBoolean(BUNDLE_CHEAT_IS_CLICKED);
            if (mCheatButtonIsClicked)
                mCheatButton.callOnClick();
        }
    }

    private void setClickListeners() {
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheatButtonIsClicked = true;
                if (mIsAnswerTrue)
                    mShowAnswer.setText(R.string.toast_correct);
                else
                    mShowAnswer.setText(R.string.toast_incorrect);
                mCheatButton.setEnabled(false);
                sendResult(true);
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sendResult(boolean isCheated) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEATED, isCheated);
        setResult(RESULT_OK, intent);
    }

    private void setAllViews() {
        mCheatButton = findViewById(R.id.button_accept_cheat);
        mShowAnswer = findViewById(R.id.textView_show_answer);
        mBackButton = findViewById(R.id.button_back);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_CHEAT_IS_CLICKED, mCheatButtonIsClicked);
    }
}
