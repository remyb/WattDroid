package edu.hawaii.wattdroid;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Plays sound effects for intro screen.
 * 
 */
public class Music {

    /* Mediaplayer instance */
    private static MediaPlayer mp = null;

    /**
     * Plays a song.
     * 
     * @param context
     * @param resource
     */
    public static void play(Context context, int resource) {
	stop(context);
	mp = MediaPlayer.create(context, resource);
	mp.setLooping(true);
	mp.start();
    }

    /**
     * Stops a song.
     * 
     * @param context
     */
    public static void stop(Context context) {
	if (mp != null) {
	    mp.stop();
	    mp.release();
	    mp = null;
	}
    }
}