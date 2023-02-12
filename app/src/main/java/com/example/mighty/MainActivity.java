package com.example.mighty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer bgm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bgm = MediaPlayer.create(this, R.raw.pekora_bgm);
        bgm.setVolume(0.03f,0.03f);
        try {
            if (bgm.isPlaying()) {
                bgm.stop();
            }
        }catch (Exception e){System.out.println(e+"\nat bgm isplaying");}
        bgm.start();
        bgm.setLooping(true);
        setContentView(R.layout.activity_main);

    }
    public void goToGame(View view){
        Intent intent = new Intent(this,Gameplay.class);
        bgm.stop();
        bgm.release();
        bgm=null;
        startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        bgm.stop();
        bgm.release();
        bgm=null;
        startActivity(intent);
    }
    public void goToCredit(View view) {
        Intent intent = new Intent(this,credit.class);
        bgm.stop();
        startActivity(intent);
    }
}