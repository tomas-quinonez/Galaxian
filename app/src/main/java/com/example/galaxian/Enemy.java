package com.example.galaxian;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.LinkedList;

public class Enemy {

    private Bitmap enemy;

    private boolean draw; // la var indica si el enemigo debe ser dibujado como parte del grupo de enemigos de la matriz

    private Point point; // contiene las coordenadas del enemigo en un momento dado

    private boolean flying; // var que indica si el enemigo está volando fuera del grupo de enemigos

    private int countUp, countDown, countLeft, countRight;

    private int flightDirection; // 0 indica que el enemigo debe volar a la izquierda. 1 indica a la derecha

    private LinkedList<Enemy> listaVolando;

    private EnemyShot enemyShot;

    public Enemy(Resources res, LinkedList<Enemy> listaVolando, PlayerSpaceship player, Health health) {
        this.enemy = BitmapFactory.decodeResource(res, R.drawable.enemy1);
        this.enemy = Bitmap.createScaledBitmap(enemy, 90, 90, false);
        this.draw = true;
        this.point = new Point();
        this.flying = false;
        this.listaVolando = listaVolando;
        this.countUp = 0;
        this.countDown = 0;
        this.countLeft = 0;
        this.countRight = 0;
        this.flightDirection = 0;
        this.enemyShot = new EnemyShot(this, player, health);
    }

    // metodo para dibujar el enemigo dentro del grupo de enemigos (en la matriz)
    public void draw(@org.jetbrains.annotations.NotNull Canvas canvas, int x, int y) {
        this.point.set(x, y);
        if(this.draw) {
            canvas.drawBitmap(this.enemy, point.x, point.y, null);
        }
    }

    public int getEnemyWidth() {
        return this.enemy.getWidth();
    }

    public int getEnemyHeight() {
        return this.enemy.getHeight();
    }

    public void keepDrawing(boolean draw) {
        this.draw = draw;
    }

    // metodo que verifica si el enemigo fue disparado por el jugador
    public boolean isShot(PlayerShot playerShot, Score score) {
        boolean shot = false;
        if(draw || flying) {
            int x = playerShot.getCoordinates().x;
            int y = playerShot.getCoordinates().y;
            if((x > point.x-60 && x < (point.x + enemy.getWidth()-55)) && (y > point.y && y < (point.y + enemy.getHeight()+15))) {
                this.keepDrawing(false);
                listaVolando.remove(this);
                this.flying = false;
                score.addPoints(10);
                playerShot.enemyKilled();
                shot = true;
            }
        }
        return shot;
    }

    public void fly() {
        this.flying = true;
    }

    // metodo que actualiza la posicion del enemigo (fuera de la matriz de enemigos)
    public void update() {
        if(point.y > 1000) {
            flying = false;
            draw = true;
        }else{
            if(this.flying) {
                if(this.flightDirection == 0) {
                    flyToLeft();
                }else{
                    flyToRight();
                }
                this.enemyShot.update();
            }
        }

    }

    public void flyToLeft() {
        if(countUp<50) {
            point.x -=2;
            point.y -=2;
            countUp++;
        }else{
            if(countLeft<20) {
                point.x -=6;
                countLeft++;
            }else{
                if(countDown<1500) {
                    point.x +=8;
                    point.y +=8;
                }else{
                    countUp=0;
                    countLeft=0;
                    countDown=0;
                }
            }
        }
    }

    public void flyToRight() {
        if(countUp<50) {
            point.x +=2;
            point.y -=2;
            countUp++;
        }else{
            if(countRight<20) {
                point.x +=6;
                countRight++;
            }else{
                if(countDown<1500) {
                    point.x -=8;
                    point.y +=8;
                }else{
                    countUp=0;
                    countRight=0;
                    countDown=0;
                }
            }
        }
    }

    public boolean isFlying() {
        return this.flying;
    }

    // metodo para dibujar al enemigo que está volando (fuera de la matriz de enemigos)
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.enemy, point.x, point.y, null);
        this.enemyShot.draw(canvas);
    }

    // metodo que indica que direccion debe volar el enemigo
    public void setFlightDirection(int direction) {
        this.flightDirection = direction;
    }

    public boolean continueDrawing() {
        return this.draw;
    }

    public Point getPoint() {
        return this.point;
    }
}
