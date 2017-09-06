package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private Button TrueButton;
    private Button FalseButton;
    private Button NextButton;
    private Button CheatButton;
    private TextView QuestionTextView;

    private TrueFalse[] QuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.questton_americas, true),
            new TrueFalse(R.string.question_asia, true),
    };
    private int CurentIndex = 0;

    private boolean mIsCheater;

    @Override
    protected void onActivityResult(int requestCode, int rsultCode, Intent data){
        if (data == null){
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    private void updateQuestion(){
        int question = QuestionBank[CurentIndex].getQuestion();
        QuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = QuestionBank[CurentIndex].isTrueQuestion();

        int messageResId = 0;

        if (mIsCheater){
            messageResId = R.string.judgment_toast;
        }else {

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuestionTextView = (TextView) findViewById(R.id.question_text_view);


        TrueButton = (Button) findViewById(R.id.true_button);
        FalseButton = (Button) findViewById(R.id.false_button);

        TrueButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        FalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);            }
        });

        NextButton = (Button) findViewById(R.id.next_button);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurentIndex = (CurentIndex + 1) % QuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });
        CheatButton = (Button) findViewById(R.id.cheat_button);
        CheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = QuestionBank[CurentIndex].isTrueQuestion();
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(intent, 0);
            }
        });

        updateQuestion();
    }

}
