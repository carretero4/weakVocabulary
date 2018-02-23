package pons.carretero.myvocabulary;


import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;;
import java.io.IOException;

import pons.carretero.myvocabulary.Adapters.WordAdapter;
import pons.carretero.myvocabulary.data.WordContract;
import pons.carretero.myvocabulary.data.WordContract.WordEntry;
import pons.carretero.myvocabulary.data.CategoryContract.CategoryEntry;
import pons.carretero.myvocabulary.Adapters.WordCursorAdapter;

public class MyWords extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>, WordCursorAdapter.wordCursorAdapterInterface {
    static private final int WORD_LOADER = 0;
    WordCursorAdapter mCursorAdapter;

    MediaPlayer mediaPlayer = null;

    WordAdapter itemsAdapter;
    ListView listView;
    String classCategory;
    int idCategory;

    ImageView lastPlayedImageView;
    int lastId;
    int IndexSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_words);

        classCategory = getIntent().getStringExtra("Category");
        setTitle(classCategory);

        listView = (ListView) findViewById(R.id.listview_flavor);
        idCategory = selectId(classCategory);

        //newListWords(idCategory);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Word category = words.get(position);
            }
        });*/

        mCursorAdapter = new WordCursorAdapter(this,null);
        listView.setAdapter(mCursorAdapter);
        registerForContextMenu(listView);
        getLoaderManager().initLoader(WORD_LOADER,null,this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabmw);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), addWordActivity.class);
                intent.putExtra("From", "myWordsAdd");
                startActivity(intent);
            }
        });


    }

    @Override
    public void onFavouriteButtonClick(ImageView favImageView, int id) {
        int isFavourite;
        if(wordFavourite(id) == 0){
            isFavourite = 1;
        }else{
            isFavourite = 0;
        }
        updateWord(isFavourite,id);
    }

    @Override
    public void onPlayButtonClick(String direction, ImageView playImageView, int id) {
        if(direction != null){
            if (mediaPlayer != null) {
                if(id == lastId){
                    playImageView.setImageResource(R.drawable.play_circle);
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }else{
                    lastPlayedImageView.setImageResource(R.drawable.play_circle);
                    mediaPlayer.stop();
                    reproducir(direction, playImageView);
                }
            } else {
                    reproducir(direction, playImageView);
            }
            lastId = id;
            lastPlayedImageView = playImageView;
        }else {
            Toast.makeText(this, "Insert an audio for this word", Toast.LENGTH_SHORT).show();
        }

    }

    public int wordFavourite (int id){
        String[] projection = {
                WordEntry.COLUMN_FAVOURITE};

        Cursor cursor = getContentResolver().query(
                Uri.withAppendedPath(WordEntry.CONTENT_URI, Integer.toString(id)),
                //Uri.withAppendedPath(PreguntasEntry.CONTENT_URI, "1"),
                projection,
                null,
                null,
                null);

        int favW = cursor.getColumnIndex(WordEntry.COLUMN_FAVOURITE);
        cursor.moveToFirst();
        int isFavorite = cursor.getInt(favW);

        return isFavorite;
    }

    public void updateWord(int newFav, int id){
        ContentValues values = new ContentValues();
        values.put(WordEntry.COLUMN_FAVOURITE, newFav);

        String selection = WordEntry._ID + "=?";
        String[] selectionArgs = new String[] {Integer.toString(id)};

        //Uri urii = Uri.withAppendedPath(WordContract.WordEntry.CONTENT_URI, Integer.toString(id));

        int newUpdate = getContentResolver().update(WordEntry.CONTENT_URI, values, selection, selectionArgs);
    }

    public void reproducir (String AudioSavePathInDevice, final ImageView playImageView) throws IllegalArgumentException, SecurityException, IllegalStateException{
        mediaPlayer = new MediaPlayer();

        if(AudioSavePathInDevice != null){
            try {
                mediaPlayer.setDataSource(AudioSavePathInDevice);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            playImageView.setImageResource(R.drawable.pause_circle);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playImageView.setImageResource(R.drawable.play_circle);
                }
            });
        }
    }

    private int selectId (String category){

        Uri uriCat = Uri.withAppendedPath(CategoryEntry.CONTENT_URI, category);
        //Log.d("uri", uriCat.toString());
        String[] projection = {
                CategoryEntry._ID};

        Cursor cursor = getApplicationContext().getContentResolver().query(
                uriCat,
                projection,
                null,
                null,
                null);

        int idCat = cursor.getColumnIndex(CategoryEntry._ID);

        cursor.moveToFirst();
        int idC = cursor.getInt(idCat);

        return idC;
    }


    ///LISTENER DE CREAR MENU
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Edit word");
        menu.add(1, v.getId(), 0, "Delete word");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        // info.position will give the index of selected item
        IndexSelected =  (int) info.id;

        if(item.getTitle()=="Delete word")
        {
            createDialogYesNo();

        }
        else if(item.getTitle()=="Edit word"){
            Intent intent = new Intent(this, addWordActivity.class);
            intent.putExtra("From", "myWordsEdit");
            intent.putExtra("Id", IndexSelected);
            startActivity(intent);
        }
        else
        {
            return false;
        }
        return true;
    }

    private void createDialogYesNo(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress
                        doOnTrueResult();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Do your No progress
                        doOnFalseResult();
                        break;
                }
            }
        };
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Are you sure to delete this word?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void doOnTrueResult() {
        deleteWord(IndexSelected);
        //do something
    }

    private void doOnFalseResult() {
        //do something else
    }

    public void deleteWord (int idW){
        Uri newUri = Uri.withAppendedPath(WordEntry.CONTENT_URI, Integer.toString(idW));
        String selection = WordEntry._ID + "=?";
        String[] selectionArgs = new String[] {""};
        int newDelete = getApplicationContext().getContentResolver().delete
                (newUri, selection, selectionArgs);

        Toast.makeText(this, "Word deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                finish();
                break;
        }
        return true;
    }




    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        super.onBackPressed();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                WordEntry._ID,WordEntry.COLUMN_ENG, WordEntry.COLUMN_ESP, WordEntry.COLUMN_CATEGORY,WordEntry.COLUMN_AUDIO, WordEntry.COLUMN_FAVOURITE};

        String selection = WordContract.WordEntry.COLUMN_CATEGORY + "=?";
        String[] selectionArgs = new String[]{Integer.toString(idCategory)};

        return new CursorLoader(getApplicationContext(),
                WordEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                WordEntry.COLUMN_ENG + " ASC ");
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}
