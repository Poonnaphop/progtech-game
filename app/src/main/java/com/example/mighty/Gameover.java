package com.example.mighty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

public class Gameover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView cause = (TextView)findViewById(R.id.gameovercause);
        SharedPreferences settings = getSharedPreferences("Game Over", MODE_PRIVATE) ;
        String cau = settings.getString("cause","!!die from strong RABBIT");
        try{
            Bundle extras = getIntent().getExtras();
            cau = extras.getString("cause");
        }finally {

            cause.setText(cau);
        }

    }
    public void goToGame(View view){
        Intent intent = new Intent(this,Gameplay.class);
        startActivity(intent);
    }
    public void goToHome(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}