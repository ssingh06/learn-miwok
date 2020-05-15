package com.example.android.miwok;

import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.miwok.media.MiwokAudioManagerFactory;
import com.example.android.miwok.media.MiwokMediaPlayerFactory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_miwok_words, container, false);

        /*
         * Create an ArrayList with MiwokWord objects, which can then be passed
         * to an ArrayAdapter so it can de displayed in an AdapterView or
         * recycle view, e.g. ListView, GridView.
         */
        final ArrayList<MiwokWord> words = new ArrayList<>();
        words.add(new MiwokWord("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new MiwokWord("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new MiwokWord("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new MiwokWord("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new MiwokWord("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new MiwokWord("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new MiwokWord("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new MiwokWord("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new MiwokWord("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new MiwokWord("Come here.", "әnni'nem", R.raw.phrase_come_here));

        /*
         * Create a {@link MiwokWordAdapter} to display a list containing
         * {@link MiwokWord} objects. MiwokWordAdapter inherits ArrayAdapter
         * and serves as the data store that populates the view layer
         * represented by one of the recycle views.
         * A default {@link ArrayAdapter} can render a layout with a single
         * TextView, for e.g. android.R.layout.simple_list_item_1. If a layout
         * is more complex containing other views, then there are two options:
         *      1. Use another constructor that takes a TextView as an argument
         *          within the layout.
         *      2. Implement a custom ArrayAdapter that overrides the getView()
         *          method to return a custom View object.
         */
        final MiwokWordAdapter numbersAdapter = new MiwokWordAdapter(getContext(),
                words, R.color.category_phrases);
        final ListView numbersListView = rootView.findViewById(R.id.miwok_words_list);
        numbersListView.setAdapter(numbersAdapter);

        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /* request audio focus and play the translation audio file */
                if (MiwokAudioManagerFactory.getInstance()
                        .requestAudioFocus() == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    final MiwokWord miwokWord = (MiwokWord) parent.getItemAtPosition(position);

                    /* create a new media player object */
                    MiwokMediaPlayerFactory.getInstance()
                            .create(getContext(),
                                    miwokWord.getSoundResourceId());

                    /* play the audio */
                    MiwokMediaPlayerFactory.getInstance().getMediaPlayer().start();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        /* create a new audio manager for this activity */
        MiwokAudioManagerFactory.getInstance().create(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();

        /* app is not in foreground. Release the media player */
        MiwokMediaPlayerFactory.getInstance().releaseMediaPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();

        /* app is not visible anymore. Cleanup the audio manager */
        MiwokAudioManagerFactory.getInstance().cleanup();
    }
}
