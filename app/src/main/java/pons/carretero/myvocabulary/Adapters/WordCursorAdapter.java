package pons.carretero.myvocabulary.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pons.carretero.myvocabulary.R;
import pons.carretero.myvocabulary.data.WordContract;

/**
 * Created by carre on 06/12/2017.
 */

public class WordCursorAdapter extends CursorAdapter {
    private wordCursorAdapterInterface mInterface;
    /**
     * Constructs a new {@link WordCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public WordCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        try {
            mInterface = (wordCursorAdapterInterface) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement wordCursorAdapterInterface");
        }


    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);

        final ViewHolder viewHolder = new ViewHolder();

        viewHolder.nameEngTextView = (TextView) view.findViewById(R.id.engTX);
        viewHolder.nameEspTextView = (TextView) view.findViewById(R.id.espTX);
        viewHolder.soundImageView = (ImageView) view.findViewById(R.id.wordSound);

        viewHolder.soundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.onPlayButtonClick(viewHolder.directionSound, viewHolder.soundImageView, viewHolder.id);
            }
        });
        viewHolder.favourite = (ImageView) view.findViewById(R.id.favourite);

        viewHolder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.onFavouriteButtonClick(viewHolder.favourite, viewHolder.id);
            }
        });

        view.setTag(viewHolder);

        return view;
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */



    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int id = cursor.getColumnIndex(WordContract.WordEntry._ID);
        int eng = cursor.getColumnIndex(WordContract.WordEntry.COLUMN_ENG);
        int esp = cursor.getColumnIndex(WordContract.WordEntry.COLUMN_ESP);
        int sound = cursor.getColumnIndex(WordContract.WordEntry.COLUMN_AUDIO);
        int favo = cursor.getColumnIndex(WordContract.WordEntry.COLUMN_FAVOURITE);


        String idTexView = cursor.getString(id);
        String engTexView = cursor.getString(eng);
        String espTextView = cursor.getString(esp);
        String soundIV = cursor.getString(sound);
        int favouriteIV = cursor.getInt(favo);

        viewHolder.id = Integer.valueOf(idTexView);
        viewHolder.nameEngTextView.setText(engTexView);
        viewHolder.nameEspTextView.setText(espTextView);
        viewHolder.directionSound = soundIV;
        viewHolder.isFavourite = favouriteIV;

        if(soundIV == null){
            viewHolder.soundImageView.setImageResource(R.drawable.grey_play_button);
        }else{
            viewHolder.soundImageView.setImageResource(R.drawable.play_circle);
        }

        if(favouriteIV == 0){
            viewHolder.favourite.setImageResource(R.drawable.unfavourite);
        }else{
            viewHolder.favourite.setImageResource(R.drawable.favorite);
        }

        //wordIdTextView.setContentDescription(idTexView);
    }

    class ViewHolder {
        int id;
        String directionSound;
        int isFavourite;
        TextView nameEngTextView;
        TextView nameEspTextView;
        ImageView soundImageView;
        ImageView favourite;

    }

    public interface wordCursorAdapterInterface{
        public void onPlayButtonClick(String direction, ImageView playImageView, int id);
        public void onFavouriteButtonClick(ImageView favImageView, int id);
    }



}
