package com.rajendarreddyj.quiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rajendarreddyj.quiz.R;
import com.rajendarreddyj.quiz.dao.QuizDBHelper;
import com.rajendarreddyj.quiz.model.Question;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private List<Question> quesList;
    private int score = 0;
    private int qid = 0;
    private Question currentQ;
    private TextView txtQuestion;
    private RadioButton rda, rdb, rdc, rdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        QuizDBHelper db = new QuizDBHelper(this);
        quesList = db.getAllQuestions();
        currentQ = quesList.get(qid);
        txtQuestion = findViewById(R.id.textView1);
        rda = findViewById(R.id.radio0);
        rdb = findViewById(R.id.radio1);
        rdc = findViewById(R.id.radio2);
        rdd = findViewById(R.id.radio3);
        Button butNext = findViewById(R.id.button1);
        setQuestionView();
        butNext.setOnClickListener(v -> {
            RadioGroup grp = findViewById(R.id.radioGroup1);
            RadioButton answer = findViewById(grp.getCheckedRadioButtonId());
            Log.d("yourans", currentQ.getAnswer() + " " + answer.getText());
            if (currentQ.getAnswer().contentEquals(answer.getText())) {
                score++;
                Log.d("score", "Your score" + score);
            }
            if (qid < 5) {
                currentQ = quesList.get(qid);
                setQuestionView();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                Bundle b = new Bundle();
                b.putInt("score", score); //Your score
                intent.putExtras(b); //Put your score to your next Intent
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQ.getQuestion());
        rda.setText(currentQ.getOptionA());
        rdb.setText(currentQ.getOptionB());
        rdc.setText(currentQ.getOptionC());
        rdd.setText(currentQ.getOptionD());
        rda.setChecked(false);
        rdb.setChecked(false);
        rdc.setChecked(false);
        rdd.setChecked(false);
        qid++;
    }
}
