package com.example.android.miwok.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * This singleton factory class allows the media player creation and management
 * in the Miwok app.
 */
public class MiwokMediaPlayerFactory {
    private static final String LOG_TAG = "MiwokMediaPlayerFactory";
    private static MiwokMediaPlayerFactory instance;
    private static MediaPlayer mediaPlayer;

    /**
     * private constructor.
     */
    private MiwokMediaPlayerFactory() {}

    /**
     * static method to get the singleton class instance.
     * @return singleton class instance.
     */
    public static MiwokMediaPlayerFactory getInstance() {
        if (instance == null) {
            synchronized (MiwokMediaPlayerFactory.class) {
                if (instance == null) {
                    instance = new MiwokMediaPlayerFactory();
                }
            }
        }

        return instance;
    }

    /**
     * This method creates a new media player object.
     * @param context context that requested the media player creation.
     * @param resid resource id for the audio file to be played.
     */
    public synchronized void create(Context context, int resid) {
        if (hasMediaPlayer()) {
            /*
             * release any previous left behind media player object. This may
             * happen if the user didn't let the last audio file play until
             * the end and thus the media player object wasn't released.
             */
            releaseMediaPlayer();
        }

        mediaPlayer = MediaPlayer.create(context, resid);
        if (mediaPlayer == null) {
            Log.e(LOG_TAG, "Failed to create media player");
            return;
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                /* release the current media player object after playback is complete */
                releaseMediaPlayer();
            }
        });
    }

    /**
     * This method checks if a media player object is available.
     * @return boolean if a media player object is available.
     */
    public synchronized boolean hasMediaPlayer() {
        return mediaPlayer != null;
    }

    /**
     * This method returns the existing media player object.
     * @return MediaPlayer media player object.
     */
    public synchronized MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * This method releases the media player object.
     */
    public synchronized void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            /* abandon audio focus */
            MiwokAudioManagerFactory.getInstance().abandonAudioFocusRequest();
        }
    }
}
