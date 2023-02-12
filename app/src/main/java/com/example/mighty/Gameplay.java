package com.example.mighty;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class Gameplay extends AppCompatActivity{
    private GameView gameView ;
    private Handler handler = new Handler();
    private  final static long Intervel = 100;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{//***
        super.onCreate(savedInstanceState);
////
        gameView = new GameView(this);
        setContentView(gameView) ;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {

                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        gameView.invalidate();
                    }
                });
            }
        },0,Intervel);

/////
    }catch(Exception e){System.out.println(e);}//***
    }
    public void goToEnd(View view){
        Intent intent = new Intent(this, Gameover.class);
        startActivity(intent);
    }

    public void goToPass(View view){
        Intent intent = new Intent(this, Gamepass.class);
        startActivity(intent);
    }

}