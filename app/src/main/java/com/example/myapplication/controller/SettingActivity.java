package com.example.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.MyColor;
import com.example.myapplication.model.Setting;

public class SettingActivity extends AppCompatActivity {
    private EditText mEditTextPositiveScore;
    private EditText mEditTextNegativeScore;
    private CheckBox mCheckBoxEnableTimeout;
    private RadioGroup mRadioGroupQuestionSize;
    private Setting mSetting;
    private Button mButtonSave;
    private Button mButtonDiscard;
    private CheckBox mCheckBoxQuizButtons;
    private Spinner mSpinnerBackgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findAllViews();
        setOnClickListeners();
        showPreviousSetting();
    }

    private void showPreviousSetting() {
        Intent intent = getIntent();
        mSetting = (Setting) intent.getSerializableExtra(QuizActivity.EXTRA_SETTING_OBJECT);
        //Showing question size
        switch (mSetting.getTextSize()) {
            case 14:
                mRadioGroupQuestionSize.check(R.id.radio_question_size_small);
                break;
            case 18:
                mRadioGroupQuestionSize.check(R.id.radio_question_size_medium);
                break;
            default:
                mRadioGroupQuestionSize.check(R.id.radio_question_size_large);
        }
        //buttons enable not not
        if (mSetting.isHideButtons())
            mCheckBoxQuizButtons.setChecked(mSetting.isHideButtons());
        //Checking background color
        if (mSetting.getBackgroundColor() == MyColor.LIGHT_RED)
            mSpinnerBackgroundColor.setSelection(0);
        else if (mSetting.getBackgroundColor() == MyColor.LIGHT_BLUE)
            mSpinnerBackgroundColor.setSelection(1);
        else if (mSetting.getBackgroundColor() == MyColor.LIGHT_GREEN)
            mSpinnerBackgroundColor.setSelection(2);
        else
            mSpinnerBackgroundColor.setSelection(3);
        //Showing scores
        mEditTextPositiveScore.setText(String.valueOf(mSetting.getPositiveScore()));
        mEditTextNegativeScore.setText(String.valueOf(mSetting.getNegativeScore()));
        //checking timeout
        if (mSetting.isEnableTimeout())
            mCheckBoxEnableTimeout.setChecked(true);
    }

    private void setOnClickListeners() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Setting questions size
                if (mRadioGroupQuestionSize.getCheckedRadioButtonId() == R.id.radio_question_size_small)
                    mSetting.setTextSize(14);
                else if (mRadioGroupQuestionSize.getCheckedRadioButtonId() == R.id.radio_question_size_medium)
                    mSetting.setTextSize(18);
                else
                    mSetting.setTextSize(26);
                //Enable and disable buttons
                if (mCheckBoxQuizButtons.isChecked())
                    mSetting.setHideButtons(true);
                else
                    mSetting.setHideButtons(false);
                //Setting background color
                switch (mSpinnerBackgroundColor.getSelectedItemPosition()) {
                    case 0:
                        mSetting.setBackgroundColor(MyColor.LIGHT_RED);
                        break;
                    case 1:
                        mSetting.setBackgroundColor(MyColor.LIGHT_BLUE);
                        break;
                    case 2:
                        mSetting.setBackgroundColor(MyColor.LIGHT_GREEN);
                        break;
                    default:
                        mSetting.setBackgroundColor(MyColor.WHITE);
                }
                //Setting scores
                mSetting.setPositiveScore(Integer.parseInt(String.valueOf(mEditTextPositiveScore.getText())));
                mSetting.setNegativeScore(Integer.parseInt(String.valueOf(mEditTextNegativeScore.getText())));
                //Setting timeout
                mSetting.setEnableTimeout(mCheckBoxEnableTimeout.isChecked());
//                Log.d("SETTING_LOG",String.valueOf(mCheckBoxQuizButtons.isChecked()));
                //Sending info
                Intent intent = new Intent(SettingActivity.this, QuizActivity.class);
                intent.putExtra(QuizActivity.EXTRA_SETTING_OBJECT, mSetting);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mButtonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findAllViews() {
        mRadioGroupQuestionSize = findViewById(R.id.radiogroup_question_size);
        mButtonSave = findViewById(R.id.button_save);
        mCheckBoxQuizButtons = findViewById(R.id.checkbox_quiz_buttons);
        mSpinnerBackgroundColor = findViewById(R.id.spinner_background_color);
        mEditTextNegativeScore = findViewById(R.id.editText_negative_score);
        mEditTextPositiveScore = findViewById(R.id.editText_positive_score);
        mCheckBoxEnableTimeout = findViewById(R.id.checkBox_enable_timeout);
        mButtonDiscard = findViewById(R.id.button_discard);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Back button is locked click on discard!", Toast.LENGTH_LONG).show();
    }
}