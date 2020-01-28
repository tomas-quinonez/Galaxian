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

    public EnemyShot(Enemy enemy) {
        this.enemy = enemy;
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
        this.y+=20;
        if(this.y>1200) {
            this.x = enemy.getPoint().x;
            this.y = enemy.getPoint().y;
        }
    }

    public Point getCoordinates() {
        return new Point(this.x, this.y);
    }

    public void enemyKilled() {
        this.x = enemy.getPoint().x;
        this.y = enemy.getPoint().y;
    }
}
