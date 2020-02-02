package com.example.galaxian;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.provider.SyncStateContract;

import java.util.LinkedList;
import java.util.Random;

public class EnemyGroup {

    private Enemy[][] group;

    private Point point;

    private int leftMostEnemyX;

    private int rightMostEnemyX;

    private boolean right;

    private int enemyWidth;

    private int enemyHeight;

    private Random random;

    private LinkedList<Enemy> listaVolando; // lista que contiene a todos los enemigos que están volando fuera del grupo de enemigos

    public EnemyGroup(Resources res, PlayerSpaceship player, Health health) {

        this.group = new Enemy[4][6];

        this.random = new Random();

        this.listaVolando = new LinkedList<>();

        for(int i=0; i<group.length; i++) { // se crean todas las instancias de enemigos
            for(int j=0; j<group[0].length; j++) {
                this.group[i][j] = new Enemy(res, this.listaVolando, player, health);

                if(j < group[0].length/2) {
                    this.group[i][j].setFlightDirection(0);
                }else{
                    this.group[i][j].setFlightDirection(1);
                }
            }

        }

        this.enemyWidth = this.group[0][0].getEnemyWidth();

        this.enemyHeight = this.group[0][0].getEnemyHeight();

        this.right = true;

        this.point = new Point(85, 110);
    }

    // se dibujan todos los enemigos en la matriz (excepto los que están volando fuera de ella)
    public void draw(Canvas canvas) {
        int x=point.x;
        int y=point.y;
        int i, j;
        for(i = 0; i<group.length; i++) {
            for(j = 0; j<group[0].length; j++) {

                if(this.group[i][j].continueDrawing()){
                    this.group[i][j].draw(canvas, x, y);
                }else{
                    if(this.group[i][j].isFlying()) {
                        this.group[i][j].draw(canvas);
                    }
                }

                if(i==0 && j==0) {
                    this.leftMostEnemyX = x; // se determina la posicion horizontal del enemigo de más a la izquierda
                }
                if(i==0 && j==group[0].length-1) {
                    this.rightMostEnemyX = x; // se determina la posicion horizontal del enemigo de más a la derecha
                }
                x+=enemyWidth;
            }
            y+=enemyHeight;
            x=point.x;
        }
    }

    public void update(int canvasWidth, PlayerShot playerShot, Score score) {
        this.checkEnemies(playerShot, score);

        if(right) {
            point.x+=2;
            if(this.rightMostEnemyX > canvasWidth-enemyWidth) {
                right = false;
            }
        }else{
            point.x-=2;
            if(this.leftMostEnemyX< 0) {
                right = true;
            }
        }

        selectEnemyToFly();

        for(Enemy enemy : listaVolando) {
            enemy.update();
        }
    }

    public void checkEnemies(PlayerShot playerShot, Score score) {
        for(int i = 0; i< this.group.length; i++) {
            for(int j = 0 ; j<this.group[0].length; j++) {
                this.group[i][j].isShot(playerShot, score);
            }
        }
    }

    // metodo que elige aleatoriamente un ememigo para que vuele fuera de la matriz y ataque al jugador
    public void selectEnemyToFly() {
        int posFila = random.nextInt(50);
        int posCol = random.nextInt(50);
        if(posFila < group.length && posCol < group[0].length && group[posFila][posCol].continueDrawing()) {
            listaVolando.add(group[posFila][posCol]);
            group[posFila][posCol].keepDrawing(false);
            group[posFila][posCol].fly();
        }
    }

}
