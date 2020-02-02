package com.example.galaxian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import static java.security.AccessController.getContext;

public class Health implements GameObject{

    private Context gameContext;

    private Bitmap heart;

    private Bitmap greyHeart;

    private int redLives;

    private int greyLives;

    private Score score;

    public Health(Resources res, Context context, Score score) {
        this.gameContext = context;

        this.score = score;

        this.heart = BitmapFactory.decodeResource(res, R.drawable.hearts);
        this.heart = Bitmap.createScaledBitmap(heart, 50, 50, false);


        this.greyHeart = BitmapFactory.decodeResource(res, R.drawable.heart_grey);
        this.greyHeart = Bitmap.createScaledBitmap(greyHeart, 50, 50, false);

        this.redLives = 3;
        this.greyLives = 0;

    }

    public void draw(Canvas canvas) {
        int i;
        int space = 125;
        for(i = 0; i<redLives; i++) {
            canvas.drawBitmap(heart, space, 17, null);
            space+=60;
        }

        space = 245;
        for(i = 0; i<greyLives; i++) {
            canvas.drawBitmap(greyHeart, space, 17, null);
            space-=60;
        }
    }

    public void update() {
        if(this.redLives == 0) {
            Intent intent = new Intent(gameContext, GameOverActivity.class);
            intent.putExtra("finalScore", this.score.getScore());
            gameContext.startActivity(intent);
            ((Activity) gameContext).overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out); // transicion personalizada
        }
    }

    public void killPlayer() {
        this.redLives--;
        this.greyLives++;
    }
}
