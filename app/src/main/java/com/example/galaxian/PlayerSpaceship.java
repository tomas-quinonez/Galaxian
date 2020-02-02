package com.example.galaxian;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;

public class PlayerSpaceship {

    private Bitmap playerBitmap;
    private int speed;
    private Point point;
    private int count;

    public PlayerSpaceship(Resources res) {
        playerBitmap = BitmapFactory.decodeResource(res, R.drawable.player2);
        playerBitmap = Bitmap.createScaledBitmap(playerBitmap, 130, 130, false);
        this.speed = 0;
        this.point = new Point(290, 1100);
        this.count = 0;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerBitmap, point.x, point.y, null);
    }

    public void update(int canvasWidth) {
        point.x+=this.speed;
        if(point.x>canvasWidth-playerBitmap.getWidth()) { // se verifica que no se dibuje la nave fuera del ancho del canvas
            point.x=canvasWidth-playerBitmap.getWidth();
        }
        if(point.x<0){ // se verifica que no se dibuje la nave fuera del ancho del canvas
            point.x=0;
        }
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    public Point getPoint() {
        return this.point;
    }

    public int spaceshipWidth() {
        return this.playerBitmap.getWidth();
    }

    public void resetPosition() {
        this.point.set(290, 1100);
    }

    public Bitmap getPlayerBitmap() {
        return this.playerBitmap;
    }
}
