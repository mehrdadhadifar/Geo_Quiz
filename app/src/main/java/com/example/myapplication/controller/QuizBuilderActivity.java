package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.repository.QuestionRepository;
import com.example.myapplication.utils.ParseString;

public class QuizBuilderActivity extends AppCompatActivity {
    public static final String ALL_QUESTION_STRING = "ALL_QUESTION_STRING";
    public static final String QUESTION_REPOSITORY = "QUESTION_REPOSITORY";
    private Button mButtonStart;
    private EditText mEditTextQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize_builder);
        findAllViews();
        setClickListeners();

    }

    private void setClickListeners() {
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ParseString.checkString(mEditTextQuestions.getText().toString())) {
//                    Intent intent = new Intent(QuizBuilderActivity.this, QuizActivity.class);
                    Intent intent = new Intent(QuizBuilderActivity.this, QuizListActivity.class);
                    intent.putExtra(ALL_QUESTION_STRING, mEditTextQuestions.getText().toString());
                    QuestionRepository questionRepository = new QuestionRepository(mEditTextQuestions.getText().toString());
//                    intent.putExtra(QUESTION_REPOSITORY, questionRepository);
                    startActivity(intent);
                } else
                    Toast.makeText(QuizBuilderActivity.this, "Please enter correct input", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void findAllViews() {
        mButtonStart = findViewById(R.id.button_start);
        mEditTextQuestions = findViewById(R.id.editText_question);
    }
}