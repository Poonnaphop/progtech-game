package com.example.mighty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GameView extends View{
    private MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.pekora_bgm);
    private MediaPlayer atk1 =MediaPlayer.create(getContext(),R.raw.oraa);
    private MediaPlayer atk2 =MediaPlayer.create(getContext(),R.raw.itme);
    private MediaPlayer atk3 =MediaPlayer.create(getContext(),R.raw.yes);
    private MediaPlayer[] atk = {atk1,atk2,atk3};
    private MediaPlayer nousagikill= MediaPlayer.create(getContext(),R.raw.pekoradespair07);
    private MediaPlayer bosskill=MediaPlayer.create(getContext(),R.raw.hurt);
    private MediaPlayer trap=MediaPlayer.create(getContext(),R.raw.urusanai);
    private MediaPlayer haha=MediaPlayer.create(getContext(),R.raw.haha);
    private boolean hasStart = false;
    private final Bitmap[] floor = new Bitmap[2];
    private final Bitmap[] emy = new Bitmap[5];
    private final Bitmap[] peko = new Bitmap[5];
    private final Bitmap[] boss = new Bitmap[4];
    private Bitmap backgroundImage;
    private Bitmap beforestart;
    private final Paint scorePaint = new Paint();
    private final Paint timer = new Paint();
    private final Paint pow = new Paint();
    private final Paint N = new Paint();
    int canvasWidth;
    static int canvasHeight;
    int score=0;
    int time;


    private boolean enablewall=true;//default true
    private boolean deadend=true;//default true
    public String cause;
    private Map m=new Map();

    //must change change emyCnt to amount of enemy BOSS NOT IN CLUDE
    private final int emyCnt= m.getEmypow().length;
    private final Char[] chaCoor=new Char[emyCnt+1+1];//peko =cha0, emy1=cha2
    //emy [no][x,y]

    //might change
    int pekopow=10;

    private final Object [][]coor= new Object[8][14];
    //do not change
    private final Integer []Xaxis={20,195,370,545,720,895,1070,1245,1620};
    private final Integer []Yaxis={120,295,470,645,820,995,1170,1345,1520,1695,1870,2045,2220};



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context) {
        super(context);
        game();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context,AttributeSet attrs) {
        super(context,attrs);
        game();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context,AttributeSet attrs,int defstyle) {
        super(context,attrs,defstyle);
        game();
    }
    public void game(){
        time=0;
        mp.setVolume(0.03f,0.03f);
        atk1.setVolume(2f,2f);
        atk2.setVolume(2f,2f);
        atk3.setVolume(2f,2f);
        bosskill.setVolume(0.2f,0.2f);
        nousagikill.setVolume(0.7f,0.7f);
        trap.setVolume(2f,2f);
        haha.setVolume(0.7f,0.7f);

        if(mp.isPlaying()){mp.stop();}
        mp.start();
        mp.setLooping(true);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        timer.setColor(Color.BLUE);
        timer.setTextSize(70);
        timer.setTypeface(Typeface.DEFAULT_BOLD);
        timer.setAntiAlias(true);

        pow.setColor(Color.BLACK);
        pow.setTextSize(60);
        pow.setTypeface(Typeface.DEFAULT_BOLD);
        pow.setAntiAlias(true);
///bg
        //backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
        //beforestart = BitmapFactory.decodeResource(getResources(),R.drawable.beforestart);

        floor[0] = BitmapFactory.decodeResource(getResources(),R.drawable.floor0);
        floor[1] = BitmapFactory.decodeResource(getResources(),R.drawable.floor1);

        emy[0]= BitmapFactory.decodeResource(getResources(),R.drawable.emy1);
        emy[1]= BitmapFactory.decodeResource(getResources(),R.drawable.emy2);
        emy[2]= BitmapFactory.decodeResource(getResources(),R.drawable.emy3);
        emy[3]= BitmapFactory.decodeResource(getResources(),R.drawable.emy4);
        emy[4]= BitmapFactory.decodeResource(getResources(),R.drawable.emy5);

        peko[0]= BitmapFactory.decodeResource(getResources(),R.drawable.pekowalk1);
        peko[1]= BitmapFactory.decodeResource(getResources(),R.drawable.pekowalk2);
        peko[2]= BitmapFactory.decodeResource(getResources(),R.drawable.pekowalk3);
        peko[3]= BitmapFactory.decodeResource(getResources(),R.drawable.pekowalk4);
        peko[4]= BitmapFactory.decodeResource(getResources(),R.drawable.pekowalk5);

        boss[0] = BitmapFactory.decodeResource(getResources(),R.drawable.boss1);
        boss[1] = BitmapFactory.decodeResource(getResources(),R.drawable.boss2);
        boss[2] = BitmapFactory.decodeResource(getResources(),R.drawable.boss3);
        boss[3] = BitmapFactory.decodeResource(getResources(),R.drawable.boss4);

        //build default

        chaCoor[chaCoor.length-1]=new Boss();
        for (Object[] objects : coor) {
            Arrays.fill(objects, 1);
        }

        //must delete
        for(int n=1;n<chaCoor.length-1;n++){//last is boss
            chaCoor[n]=new Emy();
        }
        /*
        //to be fill
        //fill enemy list from map
        for(int c=1;c<chaCoor.length-1;c++){
            chaCoor[c]=new Emy(temlistx[c],temlisty[c],temlistpow[c]);
        }
        chaCoor[chaCoor.length-1]=new Boss(boss x, boss y, boss pow);
        //to be fill
        //i need map ja
        for(int r=0;r<coor.length;r++){
            for(int c=0;c<coor[r].length;c++){
                coor[r][c]=temmap[r][c];

         */

        //must delete
        //ran emy location
        Integer[] xx = new Integer[emyCnt];
        xx=m.getEmyx();
        Integer[] yy = new Integer[emyCnt];
        yy=m.getEmyy();
        Integer[] pp = new Integer[emyCnt];
        pp=m.getEmypow();
        int allemypow=0;
        try{
            for(int t=0;t<m.getEmypow().length;t++){//last is boss
            chaCoor[t+1].setX(xx[t]);//x axis
            chaCoor[t+1].setY(yy[t]);//y axis
            chaCoor[t+1].setPow(ThreadLocalRandom.current().nextInt(5,11+1));
            allemypow+=chaCoor[t+1].getPow();
            }
        }catch (Exception e){System.out.println(e+"\nat ran Emy");}

        //must delete
        //set boss
        chaCoor[chaCoor.length-1].setX(7);
        chaCoor[chaCoor.length-1].setY(12);
        chaCoor[chaCoor.length-1].setPow((int)allemypow/2);

        chaCoor[0]=new Player(1,0,pekopow);//player coordinate
        coor[0][0]=chaCoor[0];
        //ran set tile
        try{
        for(int r=0;r<8;r++){
            for(int c=0;c<13;c++){
                coor[r][c]=m.getMap()[c][r];
            }
        }
        }catch (Exception e){System.out.println(e+"\n at ran set tile");}
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        time++;

        try {
            canvas.drawBitmap(backgroundImage,0,0,N) ;
        }catch (Exception e){
            System.out.println(e+"\nat draw bitmap bg image");
        }
        if(!hasStart){
            System.out.println("game does not start yet");
            try {
                canvas.drawBitmap(beforestart,0,0,N) ;
            }catch (Exception e){
                System.out.println(e+"\nat draw bitmap before start");
            }
        }
        else {//put things in map
            for (int n = 0; n < chaCoor.length; n++) {
                if (n == 0) {
                    coor[chaCoor[n].getX()][chaCoor[n].getY()] = chaCoor[0];
                }
                if (n != 0) {
                    coor[chaCoor[n].getX()][chaCoor[n].getY()] = chaCoor[n];
                }
            }

            //draw#2
            canvas.drawText(time / 100 / 60 + ":" + (time / 10) % 60, 20, 100, timer);
            canvas.drawText("rabbit killed : "+score, 700, 100, timer);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 13; j++) {
                    try {
                        if (coor[i][j] instanceof Integer) {
                            switch ((int) coor[i][j]) {
                                case 0:
                                    canvas.drawBitmap(floor[0], Xaxis[i], Yaxis[j], null);
                                    break;
                                case 1:
                                    canvas.drawBitmap(floor[1], Xaxis[i], Yaxis[j], null);
                                    break;
                            }
                        }else{
                        if (coor[i][j] instanceof Char) {
                            if (coor[i][j] instanceof Emy) {
                                Emy nousagi = (Emy) coor[i][j];
                                canvas.drawBitmap(floor[0], Xaxis[i], Yaxis[j], null);
                                if(nousagi.getPow()>0){
                                canvas.drawBitmap(floor[1], Xaxis[i], Yaxis[j], null);
                                canvas.drawBitmap(emy[time % 4], Xaxis[nousagi.getX()], Yaxis[nousagi.getY()], null);
                                canvas.drawText(String.valueOf(nousagi.getPow()), Xaxis[nousagi.getX()] + 80, Yaxis[nousagi.getY()] + 10, pow);
                                }else{
                                    coor[i][j]=chaCoor[0];
                                    Player pekora = (Player) coor[i][j];
                                    canvas.drawBitmap(floor[1], Xaxis[pekora.getX()], Yaxis[pekora.getY()], null);
                                    canvas.drawBitmap(peko[time % 4], Xaxis[pekora.getX()], Yaxis[pekora.getY()], null);
                                    canvas.drawText(String.valueOf(pekora.getPow()), Xaxis[pekora.getX()] + 80, Yaxis[pekora.getY()] + 10, pow);
                                }
                            }else{
                            if (coor[i][j] instanceof Player) {
                                Player pekora = (Player) coor[i][j];
                                canvas.drawBitmap(floor[1], Xaxis[pekora.getX()], Yaxis[pekora.getY()], null);
                                canvas.drawBitmap(peko[time % 4], Xaxis[pekora.getX()], Yaxis[pekora.getY()], null);
                                canvas.drawText(String.valueOf(pekora.getPow()), Xaxis[pekora.getX()] + 80, Yaxis[pekora.getY()] + 10, pow);
                            }else {
                            if (coor[i][j] instanceof Boss) {
                                Boss b = (Boss) coor[i][j];
                                canvas.drawBitmap(floor[1], Xaxis[i], Yaxis[j], null);
                                canvas.drawBitmap(boss[time % 3], Xaxis[b.getX()], Yaxis[b.getY()], null);
                                canvas.drawText(String.valueOf(b.getPow()), Xaxis[b.getX()] + 80, Yaxis[b.getY()] + 10, pow);
                            }}}
                        }else {
                            canvas.drawBitmap(floor[1], Xaxis[i], Yaxis[j], null);
                        }
                        }
                    } catch (Exception e) {
                        System.out.println(e+"\nat ondraw");
                    }

                }
            }
            //clear death mon
            for(int r=0;r<coor.length;r++){
                for(int c=0;c<coor[r].length;c++) {
                    if(coor[r][c] instanceof Emy){
                        Emy e1 = (Emy) coor[r][c];
                        if(e1.getPow()<=0){coor[r][c]=chaCoor[0];}
                    }
                }}

        }// dont go under this
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        System.out.println("\nJust clicked");
        canvasWidth = getWidth();
        canvasHeight = getHeight();
        int x=(int)event.getX();int y=(int)event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (y > 0&&y < canvasHeight / 3) {
                up();
            }
            if(y>=canvasHeight / 3 && y < canvasHeight*2/3){
                if(x>canvasWidth/2){right();}
                if(x<=canvasWidth/2){left();}
            }
            if(y>=canvasHeight*2/3 && y<canvasHeight){
                down();
            }
            hasStart = true;
        }
        return true;
    }

    public void down(){
        if(hasStart){
        try{
            Player p = (Player) chaCoor[0];
            if(coor[(chaCoor[0].getX())][chaCoor[0].getY()+1] instanceof Char){
                Char e1 = (Char) coor[(chaCoor[0].getX())][chaCoor[0].getY()+1];
                fight(e1);
            }
            if(chaCoor[0].getY()<12){
                if(chaCoor[0].getY()<coor[1].length-1){
                    if(enablewall&& ((int)coor[(chaCoor[0].getX())][chaCoor[0].getY()+1]==0)){return;}
                    chaCoor[0].setY(chaCoor[0].getY()+1);
                    coor[(chaCoor[0].getX())][chaCoor[0].getY()-1]=0;
                    coor[(chaCoor[0].getX())][chaCoor[0].getY()]=p;
                    System.out.println();
                    System.out.println("canup :"+ canUp());
                    System.out.println("candown :"+ canDown());
                    System.out.println("canleft :"+ canLeft());
                    System.out.println("canright :"+ canRight());
                    if(deadend&&!(canUp()|| canDown()|| canLeft()|| canRight())){
                        trap.start();
                        gotoGameOver("die from trap in nowhere");
                    }
                }}
        }catch (Exception e){
            System.out.println(e+"\nat dn met");
        }
        }
    }
    public void up(){
        if(hasStart){
        try{
            Player p = (Player) chaCoor[0];
            if(coor[(chaCoor[0].getX())][chaCoor[0].getY()-1] instanceof Char){
                Char e1 = (Char) coor[(chaCoor[0].getX())][chaCoor[0].getY()-1];
                fight(e1);
            }
            if(chaCoor[0].getY()>0){
                if(chaCoor[0].getY()<coor[1].length-1){
                    if(enablewall&& (int)coor[(chaCoor[0].getX())][chaCoor[0].getY()-1]==0){return;}
                    chaCoor[0].setY(chaCoor[0].getY()-1);
                    coor[(chaCoor[0].getX())][chaCoor[0].getY()+1]=0;
                    coor[(chaCoor[0].getX())][chaCoor[0].getY()]=p;
                    System.out.println();
                    System.out.println("canup :"+ canUp());
                    System.out.println("candown :"+ canDown());
                    System.out.println("canleft :"+ canLeft());
                    System.out.println("canright :"+ canRight());
                    if(deadend&&!(canUp()|| canDown()|| canLeft()|| canRight())){
                        trap.start();
                        gotoGameOver("die from trap in nowhere");
                    }
                }}
        }catch (Exception e){System.out.println(e+"\nat up met");}
        }
    }
    public void left(){
        if(hasStart) {
            try {
                Player p = (Player) chaCoor[0];
                if(coor[(chaCoor[0].getX())-1][chaCoor[0].getY()] instanceof Char) {
                    Char e1 = (Char) coor[(chaCoor[0].getX())-1][chaCoor[0].getY()];
                    fight(e1);
                }
                if (chaCoor[0].getX() >0) {
                    if (chaCoor[0].getX() < coor[1].length - 1) {
                        if(enablewall&&//apply to all move
                                !(coor[(chaCoor[0].getX())-1][chaCoor[0].getY()] instanceof Emy||
                                        coor[(chaCoor[0].getX())-1][chaCoor[0].getY()] instanceof Boss)&&
                                (int)coor[(chaCoor[0].getX())-1][chaCoor[0].getY()]==0){return;}
                        chaCoor[0].setX(chaCoor[0].getX() - 1);
                        coor[(chaCoor[0].getX() + 1)][chaCoor[0].getY()] = 0;
                        coor[(chaCoor[0].getX())][chaCoor[0].getY()] = p;
                        System.out.println();
                        System.out.println("canup :"+ canUp());
                        System.out.println("candown :"+ canDown());
                        System.out.println("canleft :"+ canLeft());
                        System.out.println("canright :"+ canRight());
                        if(deadend&&!(canUp()|| canDown()|| canLeft()|| canRight())){
                            trap.start();
                            gotoGameOver("die from trap in nowhere");
                        }
                    }
                }
            } catch (Exception e) {System.out.println(e+"\nat lt met");
            }
        }
    }
    public void right(){
        if(hasStart) {
            try {
                Player p = (Player) chaCoor[0];
                if(coor[(chaCoor[0].getX())+1][chaCoor[0].getY()] instanceof Char) {
                    Char e1 = (Char) coor[(chaCoor[0].getX())+1][chaCoor[0].getY()];
                    fight(e1);
                }
                if (chaCoor[0].getX() < 7 ) {
                    if (chaCoor[0].getX() < coor[1].length - 1) {
                        if(enablewall&& (int)coor[(chaCoor[0].getX())+1][chaCoor[0].getY()]==0){return;}
                        chaCoor[0].setX(chaCoor[0].getX() + 1);
                        coor[(chaCoor[0].getX() - 1)][chaCoor[0].getY()] = 0;
                        coor[(chaCoor[0].getX())][chaCoor[0].getY()] = p;
                        System.out.println();
                        System.out.println("canup :"+ canUp());
                        System.out.println("candown :"+ canDown());
                        System.out.println("canleft :"+ canLeft());
                        System.out.println("canright :"+ canRight());
                        if(deadend&&!(canUp()|| canDown()|| canLeft()|| canRight())){
                            trap.start();
                            cause="die from trap in nowhere";
                            gotoGameOver2("die from trap in nowhere");
                        }
                    }
                }
            } catch (Exception e) {System.out.println(e+"\nat rt met");
            }
        }
    }
    public boolean canUp(){
        try{
            Player p= (Player) chaCoor[0];
            return coor[p.getX()][p.getY() - 1] != (Object) 0;
        }catch (Exception e){
            System.out.println(e+"\nat canup");
            return false;
        }
    }
    public boolean canDown(){
        try{
            Player p= (Player) chaCoor[0];
            if(p.getY()==12){return false;}
            if(coor[p.getX()][p.getY()+1]!=(Object) 0){return  true;}
        }catch (Exception e){
            System.out.println(e+"\nat candn");
            return false;
        }
        return false;
    }
    public boolean canLeft(){
        try{
            Player p= (Player) chaCoor[0];
            if(p.getX()==0){return false;}
            if(coor[p.getX()-1][p.getY()]!=(Object) 0){return  true;}
        }catch (Exception e){
            System.out.println(e+"\nat canlf");
            return false;
        }
        return false;
    }
    public boolean canRight(){
        try{
            Player p= (Player) chaCoor[0];
            if(p.getX()==7){return false;}
            if(coor[p.getX()+1][p.getY()]!=(Object) 0){return  true;}
        }catch (Exception e){
            System.out.println(e+"\nat canrt");
            return false;
        }
        return false;
    }

    public void fight(Char e){
        Player p = (Player) chaCoor[0];
        if (e instanceof Boss){
            e =(Boss)e;
            if(p.getPow()>=e.getPow()){
                haha.start();
                gotoWin();
            }
            else if(p.getPow()<e.getPow()){
                cause="die from FLOWEY the flower!";
                bosskill.start();
                gotoGameOver("die from FLOWEY the flower!");}
        }
        else if(e instanceof Emy){
            e =(Emy)e;
            if(p.getPow()>=e.getPow()){
                p.addPow(e.getPow());
                score+=1;
                e.setPow(0);
                coor[e.getX()][e.getY()]=1;
                atk[score%3].start();
            }
            if(p.getPow()<e.getPow()){
                cause ="die from strong RABBIT!!";
                nousagikill.start();
                gotoGameOver("die from strong RABBIT!!");
            }
        }

    }
    public void gotoGameOver(String cause){
        mp.stop();
        Toast.makeText(getContext(), "You n***", Toast.LENGTH_SHORT).show() ;
        Intent gameOverIntent = new Intent(getContext(), Gameover.class) ;
        gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        gameOverIntent.putExtra("cause", cause);
        getContext().startActivity(gameOverIntent);
        killsound();

    }
    public void gotoGameOver2(String cause){
        mp.stop();
        Toast.makeText(getContext(), "You ***b", Toast.LENGTH_SHORT).show() ;
        Intent gameOverIntent = new Intent(getContext(), Gameover2.class) ;
        gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        gameOverIntent.putExtra("cause", cause);
        getContext().startActivity(gameOverIntent);
        killsound();
    }

    public void gotoWin(){
        mp.stop();

        Intent gameOverIntent = new Intent(getContext(), Gamepass.class) ;
        getContext().startActivity(gameOverIntent);

    }


    public abstract static class Char{
        private int coorX,coorY;
        private int pow;
        public Char(int x,int y,int pow){
            this.coorX=x;this.coorY=y;
            this.pow=pow;
        }
        public Char(){}
        public int getX(){return coorX;}
        public int getY(){return coorY;}
        public int getPow(){return pow;}
        public void addPow(int i){pow+=i;}
        public void setX(int x){coorX=x;}
        public void setY(int y){coorY=y;}
        public void setPow(int y){pow=y;}
    }

    public class Player extends Char{
        public Player(int x,int y,int pow){super(x, y, pow);}
        public Player(){super();}
    }
    public class Emy extends Char{
        public Emy(int x,int y,int pow){super(x, y, pow);}
        public Emy(){super();}
    }
    public class Boss extends Char{
        public Boss(int x,int y,int pow){super(x, y, pow);}
        public Boss(){super();}
    }
    public void killsound(){
        //m.end();
        /*
        mp.release();mp=null;
        atk1.release();atk1=null;
        atk2.release();atk2=null;
        atk3.release();atk3=null;
        nousagikill.release();;nousagikill=null;
        bosskill.release();bosskill=null;
        trap.release();trap=null;
         */
    }

}
