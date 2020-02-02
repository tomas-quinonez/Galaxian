package com.example.galaxian;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

public class Score {

    private int score;
    private Paint paint;

    public Score(Context context) {
        this.score = 0;
        this.paint = new Paint();
        this.paint.setColor(Color.YELLOW);
        this.paint.setAntiAlias(true);
        this.paint.setTextSize(55);
        this.paint.setTypeface(ResourcesCompat.getFont(context, R.font.eight_bit_operator_plus8_bold));
    }

    public void draw(Canvas canvas) {
        canvas.drawText(""+this.score, 15, 60, paint);
    }

    // se suman puntos en caso de matar a un enemigo
    public void addPoints(int points) {
        this.score+=points;
    }

    public int getScore() {
        return this.score;
    }
}
