package com.example.myapplication.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.fragment.QuizListFragment;

public class QuizListActivity extends SingleFragmentActivity {



    @Override
    public Fragment createFragment() {
        return new QuizListFragment();
    }

}