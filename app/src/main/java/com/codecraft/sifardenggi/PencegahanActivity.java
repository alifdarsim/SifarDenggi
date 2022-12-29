package com.codecraft.sifardenggi;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PencegahanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencegahan);

        ImageView back = findViewById(R.id.back);
        back.bringToFront();
        back.setOnClickListener(v -> finish());

    }

}