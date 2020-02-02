package com.example.galaxian;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import static android.graphics.Color.RED;

public class EnemyShot {

    private int x;
    private int y;
    private int position;
    private Paint paint;
    private Enemy enemy;
    private PlayerSpaceship player;
    private Health health;

    public EnemyShot(Enemy enemy, PlayerSpaceship player, Health health) {
        this.enemy = enemy;
        this.player = player;
        this.health = health;
        this.paint = new Paint();
        this.paint.setColor(RED);
        this.position = enemy.getEnemyWidth()/2;
        this.x = enemy.getPoint().x;
        this.y = enemy.getPoint().y;
    }

    public void draw(Canvas canvas) {
        if(enemy.isFlying() && this.x!=0){
            canvas.drawRect(x+position-5, y+80, x+position+5, y+45+80, paint);
        }
    }

    public void update() {

        checkPlayer();

        this.y+=20;
        if(this.y>1200) {
            this.x = enemy.getPoint().x;
            this.y = enemy.getPoint().y;
        }
    }

    public Point getCoordinates() {
        return new Point(this.x, this.y);
    }

    public void checkPlayer() {
        if(this.x != 0) {
            if((x > player.getPoint().x-25 && x < (player.getPoint().x + player.getPlayerBitmap().getWidth()-55)) && ((y+80) > player.getPoint().y)) {
                player.resetPosition();
                health.killPlayer();
                this.x = 0;
                this.y = 1300;
            }
        }
    }
}
