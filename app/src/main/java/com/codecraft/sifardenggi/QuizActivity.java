package com.codecraft.sifardenggi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    Integer currentQuestion = 1;
    String[] questionText;
    TextView questionCount;
    TextView question;
    ProgressBar progressBar;
    LinearLayout question_panel;
    ArrayList<Integer> answer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk);

        ImageView closeButton = findViewById(R.id.close);
        closeButton.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
            alertDialogBuilder.setTitle("Amaran");
            alertDialogBuilder.setMessage("Jika anda keluar sekarang, anda perlu menjawab kembali daripada soalan pertama. Teruskan?");
            alertDialogBuilder.setPositiveButton("Ya", (arg0, arg1) -> finish());
            alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> {});
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        questionCount = findViewById(R.id.question_count);
        question = findViewById(R.id.question);
        question_panel = findViewById(R.id.question_panel);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);
        progressBar = findViewById(R.id.progress_circular);

        questionText = new String[]{
            "Adakah anda menghidapi demam tinggi?",
            "Adakah anda mengalami sakit kepala?",
            "Adakah anda mengalami sakit otot atau sendi?",
            "Adakah anda mengalami muntah berterusan?",
            "Adakah anda mengalami cirit-birit?",
            "Adakah anda mengalami kehilangan selera makan?",
            "Adakah terdapat ruam pada kulit anda?",
            "Adakah anda tinggal di kawasan denggi berisiko tinggi?"
        };

        yesButton.setOnClickListener(v -> {
            buttonClick(1);
        });

        noButton.setOnClickListener(v -> {
            buttonClick(0);
        });

        toggleProgressBar();
    }

    private void toggleProgressBar(){
        final Handler handler = new Handler();
        question_panel.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> {
            progressBar.setVisibility(View.INVISIBLE);
            question_panel.setVisibility(View.VISIBLE);
        }, 350);
    }

    private void viewResult(){
        int sum = sum(answer);
        Intent i = new Intent(QuizActivity.this, QuizResultActivity.class);
        i.putExtra("result",sum);
        startActivity(i);
        finish();
    }

    private void buttonClick(Integer result){
        toggleProgressBar();

        if (currentQuestion == 8) {
            viewResult();
            return;
        }
        answer.add(result);
        question.setText(questionText[currentQuestion]);
        questionCount.setText("Soalan: " + (currentQuestion+1) + "/8");
        currentQuestion++;

    }

    private int sum(List<Integer> m){
        Integer sum = 0;
        for(int i = 0; i < m.size(); i++)
            sum += m.get(i);
        return sum;
    }

}