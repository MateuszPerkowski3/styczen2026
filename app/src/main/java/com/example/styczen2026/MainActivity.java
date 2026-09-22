package com.example.styczen2026;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Wewnętrzna klasa reprezentująca pytanie
    public static class Question {
        private String questionText;
        private String[] answers;
        private int correctAnswerIndex;
        private int imageResourceId;

        public Question(String questionText, String[] answers, int correctAnswerIndex, int imageResourceId) {
            this.questionText = questionText;
            this.answers = answers;
            this.correctAnswerIndex = correctAnswerIndex;
            this.imageResourceId = imageResourceId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String[] getAnswers() {
            return answers;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }
    }

    private ImageView ivQuestionImage;
    private TextView tvQuestionText;
    private RadioGroup rgAnswers;
    private RadioButton rbAnswer1, rbAnswer2, rbAnswer3;
    private Button btnNext;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int userScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupQuestions();
        displayCurrentQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNextButtonClick();
            }
        });
    }

    private void initializeViews() {
        ivQuestionImage = findViewById(R.id.ivQuestionImage);
        tvQuestionText = findViewById(R.id.tvQuestionText);
        rgAnswers = findViewById(R.id.rgAnswers);
        rbAnswer1 = findViewById(R.id.rbAnswer1);
        rbAnswer2 = findViewById(R.id.rbAnswer2);
        rbAnswer3 = findViewById(R.id.rbAnswer3);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setupQuestions() {
        questionList = new ArrayList<>();

        // Pytanie 1 – wartości począatkowe
        questionList.add(new Question(
                "Które to schronisko?",
                new String[]{"Na Rysiance.", "Na Wielkiej Raczy.", "Na Wielkiej Rycerzowej."},
                0,
                R.drawable.zad1
        ));

        // Pytanie 2
        questionList.add(new Question(
                "Jaki to szczyt górski?",
                new String[]{"Giewont.", "Kasprowy Wierch.", "Rysy."},
                2,
                R.drawable.zad2
        ));

        // Pytanie 3
        questionList.add(new Question(
                "W jakich górach leży ten obiekt?",
                new String[]{"Karkonosze.", "Beskidy.", "Tatry."},
                1,
                R.drawable.zad3
        ));
    }

    private void displayCurrentQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);

        ivQuestionImage.setImageResource(currentQuestion.getImageResourceId());
        tvQuestionText.setText(currentQuestion.getQuestionText());

        String[] answers = currentQuestion.getAnswers();
        rbAnswer1.setText(answers[0]);
        rbAnswer2.setText(answers[1]);
        rbAnswer3.setText(answers[2]);

        // Wyczyszczenie zaznaczeń pól radio
        rgAnswers.clearCheck();
    }

    private void handleNextButtonClick() {
        int selectedRadioButtonId = rgAnswers.getCheckedRadioButtonId();

        // Inkrementacja punktów, jeśli wybrano poprawną odpowiedź
        if (selectedRadioButtonId != -1) {
            View selectedRadioButton = rgAnswers.findViewById(selectedRadioButtonId);
            int selectedIndex = rgAnswers.indexOfChild(selectedRadioButton);

            Question currentQuestion = questionList.get(currentQuestionIndex);
            if (selectedIndex == currentQuestion.getCorrectAnswerIndex()) {
                userScore++;
            }
        }

        currentQuestionIndex++;

        // Przejście do kolejnego pytania lub powrót do pierwszego
        if (currentQuestionIndex < questionList.size()) {
            displayCurrentQuestion();
        } else {
            Toast.makeText(this, "Wynik: " + userScore + "/" + questionList.size(), Toast.LENGTH_SHORT).show();
            currentQuestionIndex = 0;
            userScore = 0;
            displayCurrentQuestion();
        }
    }
}