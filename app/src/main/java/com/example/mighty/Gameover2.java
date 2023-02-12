package com.example.mighty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Gameover2 extends AppCompatActivity {
    private MediaPlayer sfx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over2);
        sfx = MediaPlayer.create(this, R.raw.pekora_bgm);
        sfx.setVolume(0.03f,0.03f);
        sfx.start();
        sfx.setLooping(true);
        TextView c = (TextView)findViewById(R.id.gameovercause);
        SharedPreferences settings = getSharedPreferences("Game Over", MODE_PRIVATE) ;
        String cau = settings.getString("cause","die from trap in nowhere");
        try{
            Bundle extras = getIntent().getExtras();
            cau = extras.getString("cause");
        }finally {

            c.setText(cau);
        }
    }
    public void goToGame(View view){
        sfx.release();sfx=null;
        Intent intent = new Intent(this,Gameplay.class);
        startActivity(intent);
    }
    public void goToHome(View view){
        sfx.release();sfx=null;
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}