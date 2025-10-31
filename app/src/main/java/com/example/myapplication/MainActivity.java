package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textQuestion, textScore;
    private Button buttonA, buttonB, buttonC, buttonStart, buttonReset;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean quizStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // layout
        textQuestion = findViewById(R.id.textQuestion);
        textScore = findViewById(R.id.textScore);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonStart = findViewById(R.id.buttonStart);
        buttonReset = findViewById(R.id.buttonReset);


        setQuizVisibility(false);

        // lista pytan
        questions = new ArrayList<>();
        questions.add(new Question("Stolica Francji to:", "Berlin", "Paryż", "Rzym", "Paryż"));
        questions.add(new Question("2 + 2 = ?", "3", "4", "5", "4"));
        questions.add(new Question("Kolor nieba to:", "Niebieski", "Zielony", "Czerwony", "Niebieski"));
        questions.add(new Question("Ile dni ma tydzień?", "5", "6", "7", "7"));
        questions.add(new Question("Największy ocean to:", "Atlantycki", "Spokojny", "Arktyczny", "Spokojny"));
        questions.add(new Question("Stolica Polski to:", "Kraków", "Warszawa", "Gdańsk", "Warszawa"));

        // przycisk START
        buttonStart.setOnClickListener(v -> startQuiz());

        // przyciski odpowiedzi
        View.OnClickListener answerClickListener = v -> {
            Button clicked = (Button) v;
            checkAnswer(clicked.getText().toString());
        };
        buttonA.setOnClickListener(answerClickListener);
        buttonB.setOnClickListener(answerClickListener);
        buttonC.setOnClickListener(answerClickListener);

        // przycisk RESET
        buttonReset.setOnClickListener(v -> resetQuiz());
    }

    private void startQuiz() {
        quizStarted = true;
        score = 0;
        textScore.setText("Wynik: 0");
        Collections.shuffle(questions);
        currentQuestionIndex = 0;
        buttonStart.setVisibility(View.GONE);
        setQuizVisibility(true);
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < 5) {
            Question q = questions.get(currentQuestionIndex);
            textQuestion.setText(q.getQuestionText());
            buttonA.setText(q.getOptionA());
            buttonB.setText(q.getOptionB());
            buttonC.setText(q.getOptionC());
        } else {
            Toast.makeText(this, "Koniec quizu! Twój wynik: " + score + " / 5", Toast.LENGTH_LONG).show();
            setQuizVisibility(false);
            buttonStart.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer(String answer) {
        Question q = questions.get(currentQuestionIndex);
        if (answer.equals(q.getCorrectAnswer())) {
            score++;
            textScore.setText("Wynik: " + score);
        }
        currentQuestionIndex++;
        showNextQuestion();
    }

    private void resetQuiz() {
        score = 0;
        textScore.setText("Wynik: 0");
        currentQuestionIndex = 0;
        quizStarted = false;
        setQuizVisibility(false);
        buttonStart.setVisibility(View.VISIBLE);
    }

    private void setQuizVisibility(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        textQuestion.setVisibility(visibility);
        buttonA.setVisibility(visibility);
        buttonB.setVisibility(visibility);
        buttonC.setVisibility(visibility);
        buttonReset.setVisibility(visibility);
    }
}
