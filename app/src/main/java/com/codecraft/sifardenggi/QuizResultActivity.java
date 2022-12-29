package com.codecraft.sifardenggi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

public class QuizResultActivity extends AppCompatActivity {


    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riskresult);

        Bundle extras = getIntent().getExtras();
        if (extras != null) value = extras.getInt("result");

        Button exit = findViewById(R.id.exit);
        exit.setOnClickListener(v -> {
            finish();
        });

        TextView resultText = findViewById(R.id.result);
        TextView descText = findViewById(R.id.result_desc);
        String result = "";
        String desc = "";

        if (value < 4) {
            result = "Risiko Rendah";
            resultText.setTextColor(getResources().getColor(R.color.green));
            desc = "Berdasarkan jawapan anda, tiada tanda yang jelas anda sedang dijangkiti denggi pada ketika ini. Jika anda merasakan anda berkemungkinan akan sakit, sila dapatkan rawatan. Sila ingat bahawa ujian ini tidak menggantikan konsultasi perubatan tetapi hanya bertujuan untuk memantau wabak ini.";
        } else if (value < 6){
            result = "Risiko Sederhana";
            resultText.setTextColor(getResources().getColor(R.color.yellow));
            desc = "Berdasarkan jawapan anda, badan anda tidak sihat sepenuhnya. Terdapat kemungkinan yang kecil anda telah dijangkiti wabak denggi atau juga sakit yang lain. Kami mengesyorkan agar anda mendapatkan rawatan perubatan.";
        } else {
            result = "Risiko Tinggi";
            resultText.setTextColor(getResources().getColor(R.color.red));
            desc = "Berdasarkan jawapan anda, terdapat kemungkinan yang anda sedang dijangkiti wabak denggi. Kami mengesyorkan agar anda mendapatkan rawatan perubatan dengan kadar segera.";
        }

        resultText.setText(result);
        descText.setText(desc);
    }

}