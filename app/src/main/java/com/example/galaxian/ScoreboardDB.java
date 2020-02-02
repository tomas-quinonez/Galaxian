package com.example.galaxian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "ScoreboardDB";

    private static final int version = 1;

    private static final String TABLE_SCORE = "CREATE TABLE SCOREBOARD(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME STRING NOT NULL, SCORE INTEGER )";


    public ScoreboardDB(@Nullable Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_SCORE");
        db.execSQL(TABLE_SCORE);
    }

    public void insertNewScore(String name, String score) {
        SQLiteDatabase db = getWritableDatabase();

        if(db!=null) {
            ContentValues values = new ContentValues();
            values.put("NAME", name);
            values.put("SCORE", score);
            db.insert("SCOREBOARD", null, values );
            db.close();
        }
    }

    public ArrayList<String> getScoreboard() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SCOREBOARD",null);
        ArrayList<String> scoresList = new ArrayList<String>();

        while(cursor.moveToNext()) {
            scoresList.add(cursor.getString(1)+"        "+cursor.getString(2));
        }

        return scoresList;
    }
}
