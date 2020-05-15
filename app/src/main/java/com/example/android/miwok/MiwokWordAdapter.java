package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * This class extends an {@link ArrayAdapter} for a {@link MiwokWord} list
 * in order to display a Miwok translation and a default (e.g. English)
 * translation inside a R.layout.miwok_list_item layout.
 */
public class MiwokWordAdapter extends ArrayAdapter<MiwokWord> {
    private final int colorResourceId;

    /**
     * Custom constructor.
     * @param context The context which is used to inflate the layout file in
     *                getView() method.
     * @param objects list of objects that need to displayed via the AdapterView.
     */
    public MiwokWordAdapter(@NonNull Context context, @NonNull List<MiwokWord> objects, int colorResourceId) {
        /*
         * Here, we initialize the ArrayAdapter's internal storage for the
         * context and the list. The second argument is used when the
         * ArrayAdapter is populating a single TextView. Because this is a
         * custom adapter for two TextViews and an ImageView, the adapter is
         * not going to use this second argument, so it can be any value. Here,
         * we used 0.
         */
        super(context, 0, objects);

        this.colorResourceId = colorResourceId;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     * @param position The position in the list of data that should be
     *                 displayed in the list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.miwok_list_item, parent, false);
        }

        /* get the {@link MiwokWord} object at the given position in the list */
        final MiwokWord miwokWord = getItem(position);

        /* set the miwok_image ImageView */
        final ImageView miwokImageView = listItemView.findViewById(R.id.miwok_image);
        if (miwokWord.hasImage()) {
            miwokImageView.setImageResource(miwokWord.getImageResourceId());

            /*
             * explicitly set the image view to be visible, since the AdapterView
             * reuses old views, which might have hidden the image view.
             */
            miwokImageView.setVisibility(View.VISIBLE);
        } else {
            /* hide the image view, if there is no image available */
            miwokImageView.setVisibility(View.GONE);
        }

        /* set the miwok_text TextView */
        final TextView miwokTextView = listItemView.findViewById(R.id.miwok_text);
        miwokTextView.setText(miwokWord.getMiwokTranslation());

        /* set the default_text TextView */
        final TextView defaultTextView = listItemView.findViewById(R.id.default_text);
        defaultTextView.setText(miwokWord.getDefaultTranslation());

        /* set the background color of the linear layout containing text views */
        final LinearLayout miwokTextContainer = listItemView.findViewById(R.id.miwok_text_container);
        miwokTextContainer.setBackgroundResource(colorResourceId);

        return listItemView;
    }
}
