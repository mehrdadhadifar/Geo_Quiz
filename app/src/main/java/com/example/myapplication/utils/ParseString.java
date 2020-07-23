package com.example.myapplication.utils;

import android.util.Log;

import com.example.myapplication.controller.QuizActivity;
import com.example.myapplication.model.MyColor;
import com.example.myapplication.model.Question;

import java.util.ArrayList;
import java.util.List;

public class ParseString {
    private static long mTimeOut;
    private static ArrayList<Question> mQuestionBank = new ArrayList<>();
    private static List<String> set = new ArrayList<String>();


    public static ArrayList<Question> getQuestionBank(String input) {
        mQuestionBank = new ArrayList<>();
        fillingQuestions(input);
        return mQuestionBank;
    }

    public static long getTimeOut() {
        return mTimeOut;
    }

    private static void fillingQuestions(String inputQuestions) {
        mTimeOut = Integer.valueOf(inputQuestions.substring(inputQuestions.lastIndexOf('{') + 1, inputQuestions.lastIndexOf('}')));
        parseQuestions(inputQuestions.substring(0, inputQuestions.lastIndexOf("{")));
        for (String s : set
        ) {
            parseAndSetQuestions(s);
        }
    }

    private static void parseQuestions(String input) {
        String question = input.substring(input.indexOf('[') + 1, input.indexOf("]"));
        input = input.substring(input.indexOf("]") + 1);
        set.add(question);
        while (input.contains("]")) {
            parseQuestions(input);
            input = input.substring(input.indexOf("]") + 1);
//            Log.d(QuizActivity.TAG, input);
        }
    }

    private static void parseAndSetQuestions(String input) {
        String questionString = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
        input = input.substring(input.indexOf("}") + 1);
        String answerString = input.substring(input.indexOf("{") + 1, input.indexOf("}"));
        input = input.substring(input.indexOf("}") + 1);
        String cheatString = input.substring(input.indexOf("{") + 1, input.indexOf("}"));
        input = input.substring(input.indexOf("}") + 1);
        String colorString = input.substring(input.indexOf("{") + 1, input.indexOf("}"));
        boolean answer = answerString.equals("true");
        boolean cheat = cheatString.equals("true");
        MyColor color = MyColor.BLACK;
        if (colorString.equals("black"))
            color = MyColor.BLACK;
        else if (colorString.equals("blue"))
            color = MyColor.BLUE;
        else if (colorString.equals("red"))
            color = MyColor.RED;
        else if (colorString.equals("green"))
            color = MyColor.GREEN;
        mQuestionBank.add(new Question(questionString, answer, cheat, color));
    }

    public static boolean checkString(String input) {
        try {
            fillingQuestions(input);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

