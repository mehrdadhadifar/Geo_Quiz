package com.example.myapplication.model;

import java.io.Serializable;

public class Setting implements Serializable {
    private int mTextSize;
    private boolean mHideButtons;
    private MyColor mBackgroundColor;
    private int mPositiveScore;
    private int mNegativeScore;
    private boolean mEnableTimeout;

    public boolean isEnableTimeout() {
        return mEnableTimeout;
    }

    public void setEnableTimeout(boolean enableTimeout) {
        mEnableTimeout = enableTimeout;
    }

    public void setPositiveScore(int positiveScore) {
        mPositiveScore = positiveScore;
    }

    public void setNegativeScore(int negativeScore) {
        mNegativeScore = negativeScore;
    }

    public int getPositiveScore() {
        return mPositiveScore;
    }

    public int getNegativeScore() {
        return mNegativeScore;
    }

    public Setting() {
        mHideButtons = false;
        mTextSize = 26;
        mBackgroundColor = MyColor.WHITE;
        mPositiveScore = 1;
        mNegativeScore = 0;
        mEnableTimeout=true;
    }

    public void setBackgroundColor(MyColor backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    public MyColor getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setHideButtons(boolean hideButtons) {
        mHideButtons = hideButtons;
    }

    public boolean isHideButtons() {
        return mHideButtons;
    }
}
