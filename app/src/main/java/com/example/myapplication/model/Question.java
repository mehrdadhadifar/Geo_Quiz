package com.example.myapplication.model;


import android.graphics.Color;

import java.io.Serializable;

public class Question implements Serializable {
    private String mText;
    private boolean mAnswered;
    private boolean mRightAnswer;
    private boolean mCheat;
    private MyColor mMyColor;

    public boolean isCheated() {
        return mCheated;
    }

    public void setCheated(boolean cheated) {
        mCheated = cheated;
    }

    private boolean mCheated;

    public String getText() {
        return mText;
    }

    public boolean isCheat() {
        return mCheat;
    }

    public MyColor getMyColor() {
        return mMyColor;
    }


    public boolean isRightAnswer() {
        return mRightAnswer;
    }

    public boolean isAnswered() {
        return mAnswered;
    }

    public void setAnswered(boolean answered) {
        this.mAnswered = answered;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.mRightAnswer = rightAnswer;
    }

    public Question(String text, boolean rightAnswer, boolean cheat, MyColor myColor) {
        mText = text;
        mRightAnswer = rightAnswer;
        mCheat = cheat;
        mMyColor = myColor;
    }
}

