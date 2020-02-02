package com.example.galaxian;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

public class Timer {

    private long timeLeftMilliseconds;

    private long timeElapsed;

    private long initialTime;

    private int minutes;

    private int seconds;

    private Paint paint;

    public Timer(Context context) {

        this.timeLeftMilliseconds = 121000;
        this.timeElapsed = 0;
        this.initialTime = 0;
        this.paint = new Paint();

        this.paint.setColor(Color.YELLOW);
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        this.paint.setTypeface(ResourcesCompat.getFont(context, R.font.eight_bit_operator_plus8_bold));
    }

    public void draw(Canvas canvas) {
        String minutesStr = String.valueOf(minutes);
        String secondsStr = String.valueOf(seconds);
        if(seconds<10) {
            secondsStr = "0"+secondsStr;
        }
        canvas.drawText(minutesStr+":"+secondsStr, canvas.getWidth()/2-paint.getTextSize(), 60, this.paint);
    }

    public void setInitialTime() {
        this.initialTime = System.currentTimeMillis();
    }

    public void updateLeftTime() {
        this.timeLeftMilliseconds-=this.timeElapsed;
    }

    public void setMinutes() {
        this.minutes= (int) timeLeftMilliseconds / 60000;
    }

    public void setSeconds() {
        this.seconds = (int) timeLeftMilliseconds % 60000 / 1000;
    }

    public boolean checkTime() {
        return (this.minutes == 0 && timeLeftMilliseconds<0);
    }

    public void updateTimeElapsed() {
        this.timeElapsed = System.currentTimeMillis()-initialTime;
    }
}
