package com.example.galaxian;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;

import static android.graphics.Color.YELLOW;

public class PlayerShot {

    private int x;
    private int y;
    private int position;
    private Paint paint;
    private PlayerSpaceship player;
    private SoundPlayer sp;

    public PlayerShot(PlayerSpaceship player, Context context) {
        this.player = player;
        this.paint = new Paint();
        this.paint.setColor(YELLOW);
        this.position = player.spaceshipWidth()/2;
        this.x = player.getPoint().x;
        this.y = player.getPoint().y;
        this.sp = new SoundPlayer(context);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x+position-5, y-45, x+position+5, y, paint);
        if(this.x == player.getPoint().x && this.y == player.getPoint().y) {
            //this.sp.playPlayerShot();
        }
    }

    public void update() {
        this.y+=-10;
        if(this.y<0) {
            this.x = player.getPoint().x;
            this.y = player.getPoint().y;
        }
    }

    public Point getCoordinates() {
        return new Point(this.x, this.y);
    }

    public void enemyKilled() {
        this.x = player.getPoint().x;
        this.y = player.getPoint().y;
        this.sp.playEnemyKilled();
    }
}
