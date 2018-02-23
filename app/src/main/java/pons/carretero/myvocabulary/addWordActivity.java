package pons.carretero.myvocabulary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.R.attr.button;
import static android.R.attr.id;
import static android.R.attr.targetActivity;
import static android.os.Build.VERSION_CODES.M;
import static java.security.AccessController.getContext;
import static pons.carretero.myvocabulary.R.id.fab;
import static pons.carretero.myvocabulary.R.id.mDrawerLayout;
import static pons.carretero.myvocabulary.R.id.reproducir;
import static pons.carretero.myvocabulary.data.CategoryContract.PATH_CATEGORYS;

import pons.carretero.myvocabulary.data.CategoryContract;
import pons.carretero.myvocabulary.data.CategoryContract.CategoryEntry;
import pons.carretero.myvocabulary.data.WordContract;
import pons.carretero.myvocabulary.data.WordContract.WordEntry;

public class addWordActivity extends AppCompatActivity {
    ScrollView sv;
    EditText etEng;
    EditText etEsp;
    Spinner spinner;
    EditText etDesc;
    MenuItem favouriteMenu;

    ArrayList<String> spinnerArray;
    ArrayAdapter<String> spinnerArrayAdapter;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwxyz1234567890";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    boolean canPress = true;
    boolean fisrtDown = true;
    String activityFrom;
    int wordid;
    ImageButton reproducirButton;
    Boolean reproduciendo = false;
    int favourite = 1;
    boolean mEng = false, mEsp = false, mCat = false, mDesc = false, mAudio = false, mFav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        activityFrom = getIntent().getStringExtra("From");

        permisions();

        sv = (ScrollView) findViewById(R.id.scrollViewAddWord);
        etEng = (EditText) findViewById(R.id.etEng);
        etEsp = (EditText) findViewById(R.id.etEsp);
        etDesc = (EditText) findViewById(R.id.description);
        spinner = (Spinner) findViewById(R.id.categorySpinner);
        reproducirButton = (ImageButton) findViewById(R.id.reproducir);

        spinnerArray = new ArrayList<String>();

        newListCategoty();

        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setSelection(spinnerArrayAdapter.getPosition("Unknown"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabaddword);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activityFrom.equals("myWordsEdit")){
                    editWord();
                }else {
                    addWord();
                }
            }
        });

        ///AUDIO
        random = new Random();
        final ImageButton buttonA = (ImageButton) findViewById(R.id.grabar);

        buttonA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int wbutton = buttonA.getWidth()+buttonA.getWidth()/4;
                int hbutton = buttonA.getHeight()+buttonA.getHeight()/4;
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(canPress){
                            buttonA.getLayoutParams().width= wbutton;
                            buttonA.getLayoutParams().height= hbutton;

                            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                                    mediaPlayer.stop();
                            }
                            grabar();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(canPress ){
                            canPress = false;
                            buttonA.getLayoutParams().width= -wbutton;
                            buttonA.getLayoutParams().height= -hbutton;

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    // this code will be executed after 2 seconds
                                    detener();

                                    canPress = true;
                                }
                            }, 400);

                            break;
                        }
                }
                buttonA.requestLayout();
                return false;
            }
        });

        // Add on touch actions
        onTouchActions();


        if(activityFrom.equals("myWordsEdit")){
            setTitle("Edit word");
            wordid = getIntent().getIntExtra("Id",0);
            selectForId(wordid);
            fab.setImageResource(R.drawable.ic_check_white_24dp);
        }

    }

    private void onTouchActions(){
       etEng.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               mEng = true;
               return false;
           }
       });
        etEsp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mEsp = true;
                return false;
            }
        });
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mCat = true;
                return false;
            }
        });
        etDesc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mDesc = true;
                return false;
            }
        });
        reproducirButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mAudio = true;
                return false;
            }
        });
    }


    ////TOOLBAR ITEMS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        favouriteMenu = menu.findItem(R.id.action_favorite);
        if (favourite == 0){
            favouriteMenu.setIcon(R.drawable.unfavourite_white);
        }else{
            favouriteMenu.setIcon(R.drawable.favourite_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                if(favourite == 1){
                    item.setIcon(R.drawable.unfavourite_white);
                    mFav=true;
                    favourite=0;
                }else {
                    item.setIcon(R.drawable.favourite_white);
                    mFav=true;
                    favourite=1;
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    //////////////////////

    private void selectForId (int id){

        Uri uriWord = Uri.withAppendedPath(WordEntry.CONTENT_URI, Integer.toString(id));
        //Log.d("uri", uriCat.toString());
        String[] projection = {
                WordEntry._ID, WordEntry.COLUMN_ENG, WordEntry.COLUMN_ESP, WordEntry.COLUMN_CATEGORY, WordEntry.COLUMN_DESCRIPTION, WordEntry.COLUMN_AUDIO, WordEntry.COLUMN_FAVOURITE};

        Cursor cursor = getApplicationContext().getContentResolver().query(
                uriWord,
                projection,
                null,
                null,
                null);

        int idWord = cursor.getColumnIndex(WordEntry._ID);
        int engWord = cursor.getColumnIndex(WordEntry.COLUMN_ENG);
        int espWord = cursor.getColumnIndex(WordEntry.COLUMN_ESP);
        int catWord = cursor.getColumnIndex(WordEntry.COLUMN_CATEGORY);
        int descWord = cursor.getColumnIndex(WordEntry.COLUMN_DESCRIPTION);
        int audioWord = cursor.getColumnIndex(WordEntry.COLUMN_AUDIO);
        int favoriteInt = cursor.getColumnIndex(WordEntry.COLUMN_FAVOURITE);


        cursor.moveToFirst();
        int idW = cursor.getInt(idWord);
        etEng.setText(cursor.getString(engWord));
        etEsp.setText(cursor.getString(espWord));
        String category = cursor.getString(catWord);
        spinner.setSelection(Integer.valueOf(category)-1);
        etDesc.setText(cursor.getString(descWord));
        AudioSavePathInDevice = cursor.getString(audioWord);
        if(cursor.getInt(favoriteInt) == 0){
            favourite = 0;
        }else{
            favourite = 1;
        }

    }

    public void reproducir (View v) throws IllegalArgumentException, SecurityException, IllegalStateException{

        if (!reproduciendo){
            reproduciendo = true;

            mediaPlayer = new MediaPlayer();

            if(AudioSavePathInDevice != null){
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();

                reproducirButton.setImageResource(R.drawable.pausenormal);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        reproducirButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    }
                });
            }
        }else {
            reproduciendo = false;
            reproducirButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            mediaPlayer.stop();
        }
    }

    public void detener(){
        if(null != mediaRecorder){
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    public void grabar() {

        if(checkPermission()) {

            AudioSavePathInDevice =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            CreateRandomAudioFileName(10) + "_AudioRecording.3gp";

            MediaRecorderReady();

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //buttonStart.setEnabled(false);
            //buttonStop.setEnabled(true);

            //Toast.makeText(MainActivity.this, "Recording started",Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }

    }


    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }



    public void newListCategoty () {
        String unknown = "";
        ////GENERA UN NUMERO ALEATORIO ENTRE 1 Y EL MAXIMO DE PREGUNTAS
        String[] projection = {
                CategoryEntry.COLUMN_NAME};

        Cursor cursor =  getApplicationContext().getContentResolver().query(
                CategoryEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        int nameColumna = cursor.getColumnIndex(CategoryEntry.COLUMN_NAME);

        while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumna);
                spinnerArray.add(name);

        }
    }

    public void addWord (){
        String engWord = "", espWord = "", descWord = "";

        int idC = selectId(spinner.getSelectedItem().toString());
        engWord = etEng.getText().toString();
        espWord = etEsp.getText().toString();
        descWord = etDesc.getText().toString();

        if(!engWord.equals("") && !espWord.equals("")){
            int incerted = insertWord(engWord,  espWord, idC, descWord, AudioSavePathInDevice, favourite);
            etEng.setText(null);
            etEng.requestFocus();
            sv.pageScroll(View.FOCUS_UP);
            etEsp.setText(null);
            etDesc.setText(null);
            AudioSavePathInDevice = null;
            spinner.setSelection(spinnerArrayAdapter.getPosition("Unknown"));
            favouriteMenu.setIcon(R.drawable.favourite_white);
            Toast.makeText(this, "The word have been added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Fill all text boxes", Toast.LENGTH_SHORT).show();
        }

        //CONSEGUIR DEL SPINNER

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

    private int insertWord (String eng, String esp, int id_Cat, String desc, String audioPath, int fav) {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(WordEntry.COLUMN_ENG, eng);
        values.put(WordEntry.COLUMN_ESP, esp);
        values.put(WordEntry.COLUMN_CATEGORY, id_Cat);
        values.put(WordEntry.COLUMN_DESCRIPTION, desc);
        values.put(WordEntry.COLUMN_AUDIO, audioPath);
        values.put(WordEntry.COLUMN_FAVOURITE, fav);

        //POR URI
        Uri newUri = getContentResolver().insert(WordEntry.CONTENT_URI, values);
        //DIRECTAMENTE EN BBDD
        //long newRowId = db.insert(PreguntasEntry.TABLE_NAME, null, values);

        //NULL = NO SE HA ONSEGUIDO INSERTAR EL NUEVO VALOR
        if (newUri == null) {
            //SI NO SE INSERTA
            return -1;
        } else {
            //SI SE INSERTA
            return 1;
        }
    }

    public void editWord (){
        String engWord = "", espWord = "", descWord = "";

        int idC = selectId(spinner.getSelectedItem().toString());
        engWord = etEng.getText().toString();
        espWord = etEsp.getText().toString();
        descWord = etDesc.getText().toString();

        if(!engWord.equals("") && !espWord.equals("")){
            int incerted = editPalabra(wordid, engWord,  espWord, idC, descWord, AudioSavePathInDevice, favourite);
            etEng.setText(null);
            etEng.requestFocus();
            sv.pageScroll(View.FOCUS_UP);
            etEsp.setText(null);
            etDesc.setText(null);
            AudioSavePathInDevice = null;
            spinner.setSelection(spinnerArrayAdapter.getPosition("Unknown"));
            Toast.makeText(this, "The word have been edited", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "Fill all text boxes", Toast.LENGTH_SHORT).show();
        }

        //CONSEGUIR DEL SPINNER

    }

    private int editPalabra (int id, String eng, String esp, int id_Cat, String desc, String audioPath, int fav){
        ContentValues values = new ContentValues();
        values.put(WordEntry.COLUMN_ENG, eng);
        values.put(WordEntry.COLUMN_ESP, esp);
        values.put(WordEntry.COLUMN_CATEGORY, id_Cat);
        values.put(WordEntry.COLUMN_DESCRIPTION, desc);
        values.put(WordEntry.COLUMN_AUDIO, audioPath);
        values.put(WordEntry.COLUMN_FAVOURITE, fav);

        String selection = WordEntry._ID + "=?";

        //POR URI
        int newUri = getContentResolver().update(Uri.withAppendedPath(WordEntry.CONTENT_URI, Integer.toString(id)), values, selection, null);
        //DIRECTAMENTE EN BBDD
        //long newRowId = db.insert(PreguntasEntry.TABLE_NAME, null, values);

        //NULL = NO SE HA ONSEGUIDO INSERTAR EL NUEVO VALOR
        if (newUri == 0) {
            //SI NO SE INSERTA
            return -1;
        } else {
            //SI SE INSERTA
            return 1;
        }
    }

    //Create a dialog
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /////BACK KEY PRESSED
    @Override
    public void onBackPressed() {
        if(!mEng && !mEsp && !mCat && !mDesc && !mAudio && !mFav){
            super.onBackPressed();
            return;
        }
        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);

    }

    ////PERMISIONS
    public void permisions (){
        if(!checkPermission()) {
            requestPermission();
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }



}
