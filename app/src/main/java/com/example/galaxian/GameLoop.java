package com.example.galaxian;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private static final int FPS = 60;

    private SurfaceHolder surfaceHolder;

    private GameView gameView;

    private boolean isRunning;

    private Timer timer;


    public GameLoop(SurfaceHolder sur, GameView game, Timer timer) {
        surfaceHolder = sur;
        gameView = game;
        this.timer = timer;
    }

    public void run() {
        int TIME = 1000 / FPS;
        Canvas canvas = null;


        while(isRunning) {
            timer.setInitialTime();
            timer.updateLeftTime();
            timer.setMinutes();
            timer.setSeconds();

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    gameView.update(canvas);
                    gameView.draw(canvas);
                    if(timer.checkTime()) {
                        this.setRunning(false);
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                if(canvas!=null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            try {
                Thread.sleep(TIME);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            timer.updateTimeElapsed();
        }
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }
}
