package com.example.mighty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class credit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

    }
    public void goTocredit2(View view){
        Intent intent = new Intent(this,credit2.class);
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}