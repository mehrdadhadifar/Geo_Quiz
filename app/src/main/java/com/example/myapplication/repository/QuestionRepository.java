package com.example.myapplication.repository;

import com.example.myapplication.model.Question;
import com.example.myapplication.utils.ParseString;

import java.io.Serializable;
import java.util.List;

public class QuestionRepository implements Serializable {
    private List<Question> mQuestionList;

    public QuestionRepository(String input) {
        mQuestionList = ParseString.getQuestionBank(input);
    }

    public List<Question> getQuestionList() {
        return mQuestionList;
    }
}
