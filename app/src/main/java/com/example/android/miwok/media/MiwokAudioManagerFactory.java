package com.example.android.miwok.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.util.Log;

/**
 * This singleton factory class allows the audio manager creation and management in the
 * Miwok app.
 */
public class MiwokAudioManagerFactory {
    private static final String LOG_TAG = "MiwokAudioManager";
    private static final int MIWOK_AUDIO_FOCUS_REQUEST_FAILED = -1;
    private static MiwokAudioManagerFactory instance;

    private final AudioFocusRequest audioFocusRequest;

    private AudioManager audioManager;

    /*
     * define a static OnAudioFocusChangeListener since all callers require the
     * same behavior on audio focus change.
     */
    private static AudioManager.OnAudioFocusChangeListener audioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            /*
                             * The app only requests AUDIOFOCUS_GAIN as the focus type
                             * (see ausio focus request being used on create() method).
                             * Thus we only handle this AUDIOFOCUS_GAIN* value in this
                             * switch-case block and start playing the audio when we
                             * get this audio focus type.
                             */
                            MiwokMediaPlayerFactory.getInstance().getMediaPlayer().start();
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                            /*
                             * Audio focus lost permanently:
                             * Release media player
                             */
                            if (MiwokMediaPlayerFactory.getInstance().hasMediaPlayer()) {
                                MiwokMediaPlayerFactory.getInstance().releaseMediaPlayer();
                            }

                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            /*
                             * Audio focus lost temporarily:
                             * Pause media player and reset audio to the start.
                             */
                            if (MiwokMediaPlayerFactory.getInstance().hasMediaPlayer()) {
                                MiwokMediaPlayerFactory.getInstance().getMediaPlayer().pause();
                                MiwokMediaPlayerFactory.getInstance().getMediaPlayer().seekTo(0);
                            }
                        default:
                            Log.e(LOG_TAG, "Unhandled Audio Focus type");
                    }
                }
            };

    /**
     * private constructor.
     */
    private MiwokAudioManagerFactory() {
        /*
         * audioFocusRequest in our case remains same for all contexts/activities. So we just
         * initialize it once in the constructor and all audio manager objects use the same
         * audio focus request object.
         */
        final AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        /* create an audio focus request for uninterrupted audio focus over a short duration */
        audioFocusRequest = new AudioFocusRequest.Builder(
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build();
    }

    /**
     * static method to get the singleton class instance.
     *
     * @return singleton class instance.
     */
    public static MiwokAudioManagerFactory getInstance() {
        if (instance == null) {
            synchronized (MiwokAudioManagerFactory.class) {
                if (instance == null) {
                    instance = new MiwokAudioManagerFactory();
                }
            }
        }

        return instance;
    }

    /**
     * This method creates a new audio manager object.
     *
     * @param context context that requested the media player creation.
     */
    public synchronized void create(Context context) {
        if (audioManager != null) {
            Log.w(LOG_TAG, "Audio manager already exists");
            return;
        }

        /* create a new audio manager */
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * This method requests audio focus on the audio manager object.
     *
     * @return int integer indicating if the audio focus was acquired.
     */
    public synchronized int requestAudioFocus() {
        if (audioManager == null) {
            Log.e(LOG_TAG, "Invalid request to request audio focus");
            return MIWOK_AUDIO_FOCUS_REQUEST_FAILED;
        }
        return audioManager.requestAudioFocus(audioFocusRequest);
    }

    /**
     * This method abandons audio focus on the audio manager object.
     *
     * @return int integer indicating if the audio focus was released.
     */
    public synchronized int abandonAudioFocusRequest() {
        if (audioManager == null) {
            Log.e(LOG_TAG, "Invalid request to abandon audio focus");
            return MIWOK_AUDIO_FOCUS_REQUEST_FAILED;
        }

        return audioManager.abandonAudioFocusRequest(audioFocusRequest);
    }

    public synchronized void cleanup() {
        /* reset the audio manager object */
        audioManager = null;
    }
}
