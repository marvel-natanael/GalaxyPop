package com.example.tapgamev2;

import android.content.Context;
import android.media.MediaPlayer;
public class Music {
    public static MediaPlayer player;
    public static void SoundPlayer(Context ctx,int raw_id){
        player = MediaPlayer.create(ctx, raw_id);
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);

        //player.release();
        player.start();
    }

    public static boolean soundOn = true;
}
