package com.example.mighty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Gamepass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamepass);
    }
    public void goToGame(View view){
        Intent intent = new Intent(this, Gameplay.class);
        startActivity(intent);}

    public void goToHome(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}