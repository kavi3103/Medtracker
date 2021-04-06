package com.example.medtracker;
import android.media.MediaPlayer;
import java.io.IOException;

public class AudioPlay {
    public static MediaPlayer mediaPlayer;
    public static void playAudio(String path){
        mediaPlayer = new MediaPlayer();
        if(!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
    }
    public static void stopAudio(){
        if(mediaPlayer != null && (mediaPlayer.isPlaying())){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
