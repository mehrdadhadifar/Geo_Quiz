package com.example.myapplication.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.MyColor;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Setting;
import com.example.myapplication.utils.ParseString;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_CURRENT_INDEX = "BUNDLE_KEY_CURRENT_INDEX";
    public static final String BUNDLE_KEY_CURRENT_SCORE = "BUNDLE_KEY_CURRENT_SCORE";
    public static final String BUNDLE_KEY_CURRENT_ANSWERS = "BUNDLE_KEY_CURRENT_ANSWERS";
    public static final String IS_ANSWER_TRUE = "IS_ANSWER_TRUE";
    private static final String BUNDLE_KEY_CURRENT_CHEAT = "BUNDLE_KEY_CURRENT_CHEATED";
    public static final String BUNDLE_KEY_TIMEOUT = "BUNDLE_KEY_TIMEOUT";
    public static final String TAG = "MYAPPBugs";
    public static final int REQUEST_CODE_CHEAT_ACTIVITY = 1;
    public static final int REQUEST_CODE_SETTING = 0;
    public static final String EXTRA_SETTING_OBJECT = "EXTRA_SETTING_OBJECT";
    public static final String BUNDLE_KEY_SETTING = "BUNDLE_KEY_SETTING";
    private Handler mHandler = new Handler();
    final Timer mTimer = new Timer();
    private LinearLayout mQuestionsLayout;
    private LinearLayout mLinearLayoutMaster;
    private TextView mTextViewQuestion;
    private TextView mTextViewScore;
    private TextView mTextViewTimer;
    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonReset;
    private Button mButtonCheat;
    private Button mButtonSetting;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrevious;
    private ImageButton mImageButtonFirst;
    private ImageButton mImageButtonLast;
    private ArrayList<Question> mQuestionBank;
    private Setting mSetting = new Setting();
    private int mCurrentIndex = 0;
    private int mScore = 0;
    private boolean[] mAnswers;
    private boolean[] mCheated;
    private long mTimeOut;
    private long mRemainingSeconds;
    private String mInputQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mInputQuestions = intent.getStringExtra("ALL_QUESTION_STRING");
        mQuestionBank = new ArrayList<>();
        mQuestionBank = ParseString.getQuestionBank(mInputQuestions);
        mTimeOut = ParseString.getTimeOut();
        mRemainingSeconds = mTimeOut;
        checkingBundle(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();
        setClickListeners();
        updateQuestion();
        updateScore();
        setTimeOut();
        showTimer();
    }

    private void setBackgroundColor() {
        switch (mSetting.getBackgroundColor()) {
            case LIGHT_GREEN:
                mLinearLayoutMaster.setBackgroundColor(Color.parseColor("#90ee90"));
                break;
            case LIGHT_BLUE:
                mLinearLayoutMaster.setBackgroundColor(Color.parseColor("#add8e6"));
                break;
            case LIGHT_RED:
                mLinearLayoutMaster.setBackgroundColor(Color.parseColor("#ffcccb"));
                break;
            default:
                mLinearLayoutMaster.setBackgroundColor(Color.WHITE);
                break;
        }
    }

    private void findAllViews() {
        mButtonTrue = findViewById(R.id.button_true);
        mButtonFalse = findViewById(R.id.button_false);
        mImageButtonNext = findViewById(R.id.imageButton_next);
        mImageButtonPrevious = findViewById(R.id.imageButton_previous);
        mTextViewQuestion = findViewById(R.id.text_view_question);
        mTextViewScore = findViewById(R.id.text_view_score);
        mImageButtonFirst = findViewById(R.id.imageButton_first);
        mImageButtonLast = findViewById(R.id.imageButton_last);
        mQuestionsLayout = findViewById(R.id.questions_buttons_layout);
        mButtonReset = findViewById(R.id.button_reset);
        mButtonCheat = findViewById(R.id.button_cheat);
        mTextViewTimer = findViewById(R.id.textView_timer);
        mButtonSetting = findViewById(R.id.button_setting);
        mLinearLayoutMaster = findViewById(R.id.linear_Layout_master);
    }

    private void showTimer() {
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewTimer.setText(String.valueOf(mRemainingSeconds));
                        mRemainingSeconds -= 1;
                        if (mRemainingSeconds < 0)
                            mTextViewTimer.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 0, 1000);
    }

    private void setTimeOut() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mQuestionBank.size(); i++) {
                    mQuestionBank.get(i).setAnswered(true);
                    disableAnswerButtons();
                }
            }
        };
        if (mSetting.isEnableTimeout()) {
            mHandler.postDelayed(runnable, mRemainingSeconds * 1000);
        } else {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private void checkingBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX);
            mScore = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_SCORE);
            mRemainingSeconds = savedInstanceState.getLong(BUNDLE_KEY_TIMEOUT);
            mAnswers = savedInstanceState.getBooleanArray(BUNDLE_KEY_CURRENT_ANSWERS);
            mCheated = savedInstanceState.getBooleanArray(BUNDLE_KEY_CURRENT_CHEAT);
            mSetting = (Setting) savedInstanceState.getSerializable(BUNDLE_KEY_SETTING);
            for (int i = 0; i < mAnswers.length; i++) {
                mQuestionBank.get(i).setAnswered(mAnswers[i]);
                mQuestionBank.get(i).setCheated(mCheated[i]);
            }
        }
    }

    private void updateQuestion() {
//        Log.d(TAG, String.valueOf(mCurrentIndex));
//        Log.d(TAG, String.valueOf(mQuestionBank.size()));
//        Log.d(TAG, String.valueOf(mQuestionBank.get(mCurrentIndex).isAnswered()));
        Question currentQuestion = mQuestionBank.get(mCurrentIndex);
        mTextViewQuestion.setTextSize(mSetting.getTextSize());
        mTextViewQuestion.setText(currentQuestion.getText());
        if (currentQuestion.getMyColor() == MyColor.BLUE)
            mTextViewQuestion.setTextColor(Color.BLUE);
        else if (currentQuestion.getMyColor() == MyColor.GREEN)
            mTextViewQuestion.setTextColor(Color.GREEN);
        else if (currentQuestion.getMyColor() == MyColor.RED)
            mTextViewQuestion.setTextColor(Color.RED);
        else if (currentQuestion.getMyColor() == MyColor.BLACK)
            mTextViewQuestion.setTextColor(Color.BLACK);
        if (!currentQuestion.isCheat())
            mButtonCheat.setEnabled(false);
        else
            mButtonCheat.setEnabled(true);
        disableAnswerButtons();
        hideOrShowButtons();
        setBackgroundColor();
    }

    private void hideOrShowButtons() {
        if (mSetting.isHideButtons()) {
            mButtonCheat.setVisibility(View.INVISIBLE);
            mButtonFalse.setVisibility(View.INVISIBLE);
            mButtonTrue.setVisibility(View.INVISIBLE);
            mImageButtonNext.setVisibility(View.INVISIBLE);
            mImageButtonPrevious.setVisibility(View.INVISIBLE);
            mImageButtonFirst.setVisibility(View.INVISIBLE);
            mImageButtonLast.setVisibility(View.INVISIBLE);
        } else {
            mButtonCheat.setVisibility(View.VISIBLE);
            mButtonFalse.setVisibility(View.VISIBLE);
            mButtonTrue.setVisibility(View.VISIBLE);
            mImageButtonNext.setVisibility(View.VISIBLE);
            mImageButtonPrevious.setVisibility(View.VISIBLE);
            mImageButtonFirst.setVisibility(View.VISIBLE);
            mImageButtonLast.setVisibility(View.VISIBLE);
        }
    }

    private void updateScore() {
        if (mScore < 0)
            mTextViewScore.setText("Your Score:");
        else {
            mTextViewScore.setText("Your Score: " + String.valueOf(mScore));
        }
    }

    private void disableAnswerButtons() {
        mButtonTrue.setEnabled(!mQuestionBank.get(mCurrentIndex).isAnswered());
        mButtonFalse.setEnabled(!mQuestionBank.get(mCurrentIndex).isAnswered());
        for (int i = 0; i < mQuestionBank.size(); i++) {
            if (!mQuestionBank.get(i).isAnswered())
                return;
        }
        mButtonReset.setVisibility(View.VISIBLE);
        mQuestionsLayout.setVisibility(View.GONE);
        mTextViewTimer.setVisibility(View.INVISIBLE);
    }

    private void setClickListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mImageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % mQuestionBank.size();
                updateQuestion();
            }
        });

        mImageButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (--mCurrentIndex + mQuestionBank.size()) % mQuestionBank.size();
                updateQuestion();
            }
        });
        mTextViewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % mQuestionBank.size();
                updateQuestion();
            }
        });
        mImageButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                updateQuestion();
            }
        });
        mImageButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mQuestionBank.size() - 1;
                updateQuestion();
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonReset.setVisibility(View.INVISIBLE);
                mScore = 0;
                for (int i = 0; i < mQuestionBank.size(); i++) {
                    mQuestionBank.get(i).setAnswered(false);
                    mQuestionBank.get(i).setCheated(false);
                }
                mQuestionsLayout.setVisibility(View.VISIBLE);
                mTextViewTimer.setVisibility(View.VISIBLE);
                mRemainingSeconds = mTimeOut;
                mSetting = new Setting();
                updateQuestion();
                updateScore();
                setTimeOut();
            }
        });
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                intent.putExtra(IS_ANSWER_TRUE, mQuestionBank.get(mCurrentIndex).isRightAnswer());
//                startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT_ACTIVITY);
            }
        });
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, SettingActivity.class);
                intent.putExtra(EXTRA_SETTING_OBJECT, mSetting);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_CHEAT_ACTIVITY)
            mQuestionBank.get(mCurrentIndex).setCheated(data.getBooleanExtra(CheatActivity.EXTRA_IS_CHEATED, false));
        else if (requestCode == REQUEST_CODE_SETTING) {
            mSetting = (Setting) data.getSerializableExtra(EXTRA_SETTING_OBJECT);
            updateQuestion();
            if (!mSetting.isEnableTimeout()) {
                mTextViewTimer.setVisibility(View.INVISIBLE);
            } else {
                mTextViewTimer.setVisibility(View.VISIBLE);
            }
            setTimeOut();
        }
    }

    private void checkAnswer(boolean userPressed) {
        if (mQuestionBank.get(mCurrentIndex).isCheated())
            Toast.makeText(this, "Cheat is not good!!!", Toast.LENGTH_LONG).show();
        else {
            boolean isAnswerTrue = mQuestionBank.get(mCurrentIndex).isRightAnswer();
            if (userPressed == isAnswerTrue) {
                Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_LONG).show();
                mScore += mSetting.getPositiveScore();
                updateScore();
            } else {
                Toast.makeText(this, R.string.toast_incorrect, Toast.LENGTH_LONG).show();
                mScore -= mSetting.getNegativeScore();
                updateScore();
            }
        }
        mQuestionBank.get(mCurrentIndex).setAnswered(true);
        disableAnswerButtons();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mAnswers = new boolean[mQuestionBank.size()];
        mCheated = new boolean[mQuestionBank.size()];
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(BUNDLE_KEY_CURRENT_SCORE, mScore);
        outState.putLong(BUNDLE_KEY_TIMEOUT, mRemainingSeconds);
        for (int i = 0; i < mQuestionBank.size(); i++) {
            mAnswers[i] = mQuestionBank.get(i).isAnswered();
            mCheated[i] = mQuestionBank.get(i).isCheated();
        }
        outState.putBooleanArray(BUNDLE_KEY_CURRENT_ANSWERS, mAnswers);
        outState.putBooleanArray(BUNDLE_KEY_CURRENT_CHEAT, mCheated);
        outState.putSerializable(BUNDLE_KEY_SETTING, mSetting);
    }
}