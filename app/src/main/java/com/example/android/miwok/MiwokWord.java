package com.example.android.miwok;

import androidx.annotation.NonNull;

/**
 * This class represents a Miwok work in this app. This contains the default
 * translation (e.g. English translation corresponding to the user's language)
 * and the Miwok translation
 */
public class MiwokWord {
    private static final int NO_IMAGE_PROVIDED = 0;
    private final int imageResourceId;
    private final String defaultTranslation;
    private final String miwokTranslation;
    private final int soundResourceId;

    /**
     * constructor
     * @param defaultTranslation word in default language
     * @param miwokTranslation word in Miwok language
     */
    public MiwokWord(String defaultTranslation, String miwokTranslation, int soundResourceId) {
        this.imageResourceId = NO_IMAGE_PROVIDED;
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.soundResourceId = soundResourceId;
    }

    /**
     * constructor
     * @param imageResourceId image resource id that must be shown for the word
     * @param defaultTranslation word in default language
     * @param miwokTranslation word in Miwok language
     */
    public MiwokWord(int imageResourceId, String defaultTranslation, String miwokTranslation, int soundResourceId) {
        this.imageResourceId = imageResourceId;
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.soundResourceId = soundResourceId;
    }

    /**
     * getter method for image resource id.
     * @return int image resource id.
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * getter method for default translation.
     * @return String default translation.
     */
    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    /**
     * getter method for Miwok translation.
     * @return String miwok translation.
     */
    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    /**
     * Method to check if the word has an image resource associated to it.
     * @return boolean if an image is associate to the miwok word.
     */
    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * getter method for sound resource id.
     * @return int sound resource id.
     */
    public int getSoundResourceId() {
        return soundResourceId;
    }

    @NonNull
    @Override
    public String toString() {
        return "MiwokWord{" +
                "imageResourceId=" + imageResourceId +
                ", defaultTranslation='" + defaultTranslation + '\'' +
                ", miwokTranslation='" + miwokTranslation + '\'' +
                ", soundResourceId=" + soundResourceId +
                '}';
    }
}
