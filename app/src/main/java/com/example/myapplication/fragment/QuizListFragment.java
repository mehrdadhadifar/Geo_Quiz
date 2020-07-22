package com.example.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.QuizBuilderActivity;
import com.example.myapplication.model.Question;
import com.example.myapplication.repository.QuestionRepository;

import java.util.List;


public class QuizListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private QuestionRepository mRepository;


    public QuizListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getActivity().getIntent();
        mRepository=(QuestionRepository)intent.getSerializableExtra(QuizBuilderActivity.QUESTION_REPOSITORY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initUI();

        return view;
    }

    private void initUI() {
        List<Question> questionList = mRepository.getQuestionList();
        QuestionAdapter adapter = new QuestionAdapter(questionList);
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_questions);
    }

    private class QuestionHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewQuestion;
        private TextView mTextViewColor;
        private CheckBox mCheckBoxAnswer;
        private CheckBox mCheckBoxCheat;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewQuestion = itemView.findViewById(R.id.list_row_question_title);
            mTextViewColor = itemView.findViewById(R.id.list_row_color);
            mCheckBoxAnswer = itemView.findViewById(R.id.list_row_answer);
            mCheckBoxCheat = itemView.findViewById(R.id.list_row_cheat);
        }


        public void bindQuestion(Question question) {
            mTextViewQuestion.setText(question.getText());
            mTextViewColor.setText(question.getMyColor().toString());
            mCheckBoxAnswer.setChecked(question.isRightAnswer());
            mCheckBoxCheat.setChecked(question.isCheat());
            mCheckBoxCheat.setEnabled(false);
            mCheckBoxAnswer.setEnabled(false);
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

        private List<Question> mQuestions;

        public QuestionAdapter(List<Question> questions) {
            mQuestions = questions;
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_questions, parent, false);
            QuestionHolder questionHolder = new QuestionHolder(view);
            return questionHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            Question question = mQuestions.get(position);
            holder.bindQuestion(question);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }
    }
}