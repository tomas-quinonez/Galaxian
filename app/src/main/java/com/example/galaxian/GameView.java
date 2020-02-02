package com.example.galaxian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color.*;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap background;

    private GameLoop gameLoop;

    private PlayerSpaceship player;

    private PlayerShot playerShot;

    private Score score;

    private int canvasWidth;

    private EnemyGroup enemyGroup;

    private Timer timer;

    private PauseButton pauseButton;

    private Health health;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        setFocusable(true);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);

        timer = new Timer(context);

        score = new Score(context);

        health = new Health(getResources(), getContext(), score);

        player = new PlayerSpaceship(getResources());

        playerShot = new PlayerShot(player, context);

        pauseButton = new PauseButton(getResources());

        enemyGroup = new EnemyGroup(getResources(), player, health);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /*gameLoop = new GameLoop(holder, this, timer);
        gameLoop.setRunning(true);
        gameLoop.start();*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                gameLoop.setRunning(false);
                gameLoop.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvasWidth = canvas.getWidth();

        canvas.drawBitmap(background, 0,0, null);

        pauseButton.draw(canvas);

        timer.draw(canvas);

        health.draw(canvas);

        player.draw(canvas);

        playerShot.draw(canvas);

        score.draw(canvas);

        enemyGroup.draw(canvas);


    }

    public void update(Canvas canvas) {
        player.update(canvas.getWidth());

        playerShot.update();

        enemyGroup.update(canvas.getWidth(), playerShot, score);

        health.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // al tocar el boton de pausa se pasa a la activity PauseActivity para pausar el juego
        if(pauseButton.touchedButton(event.getX(), event.getY()) && pauseButton.isPaused()) {
            Intent intent = new Intent(getContext(), PauseActivity.class);
            getContext().startActivity(intent);
            ((Activity) getContext()).overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out); // transicion personalizada
        }


        // si se apoya el boton en la pantalla, la nave se mueve en la direccion correspondiente
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            if(event.getY()>300){
                if(event.getX()<canvasWidth/2) {
                    player.setSpeed(-20);
                }else{
                    player.setSpeed(20);
                }
            }
        }

        // si se levanta el dedo de la pantalla, la nave deja de moverse
        if(event.getAction()==MotionEvent.ACTION_UP) {
            player.setSpeed(0);
        }

        return true;
    }

    public void pause() {
        gameLoop.setRunning(false);
        try {
            gameLoop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        gameLoop = new GameLoop(getHolder(), this, timer);
        gameLoop.setRunning(true);
        gameLoop.start();
    }
}
