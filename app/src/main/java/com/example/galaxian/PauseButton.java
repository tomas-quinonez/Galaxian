package com.example.galaxian;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class PauseButton {

    private Bitmap button;

    private int canvasWidth;

    private boolean paused;

    private Resources res;

    public PauseButton(Resources res) {
        this.button = BitmapFactory.decodeResource(res, R.drawable.pause_button);
        this.button = Bitmap.createScaledBitmap(this.button, 100,100, false);

        this.res = res;

        this.paused = false;
    }

    public void draw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvas.drawBitmap(button, canvas.getWidth()-110, 0, null);
    }

    public boolean touchedButton(float x, float y) {
        boolean touched = false;
        if((x > canvasWidth-110) && (y>0 && y<110)) {
            touched = true;

            if(!this.paused) {
                this.paused = true;
            }else{
                this.paused = false;
            }
        }

        return touched;
    }

    public boolean isPaused() {
        return this.paused;
    }
}
