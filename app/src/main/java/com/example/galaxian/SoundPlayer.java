package com.example.galaxian;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool soundPool;

    private int playerShotSound;

    private int enemyKilledSound;

    public SoundPlayer(Context context) {
        this.soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        playerShotSound = soundPool.load(context, R.raw.player_shot_sound, 1);

        enemyKilledSound = soundPool.load(context, R.raw.enemy_explosion, 1);
    }

    public void playPlayerShot() {
        soundPool.play(playerShotSound, 1, 1, 1, 0, 1);
    }

    public void playEnemyKilled() {
        soundPool.play(enemyKilledSound, 1, 1, 1, 0, 1);
    }
}
