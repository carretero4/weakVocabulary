package pons.carretero.myvocabulary;

/**
 * Created by carre on 27/11/2017.
 */
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import pons.carretero.myvocabulary.Objects.Word;
import pons.carretero.myvocabulary.data.WordContract.WordEntry;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class QuizActivity extends Fragment {
    Word wordQ;
    ArrayList<Integer> disponibles;
    View rootView;
    int engesp;
    int buttonCount = 0;
    EditText editTextESP;
    TextView esp1, esp2, esp3, esp4, esp5, eng1, eng2, eng3, eng4, eng5;
    ArrayList<TextView> relationTV = new ArrayList<>();
    //TextView correctTV;
    TextView englishTV;
    LinearLayout loC, leng, qPalabra, qEnlazar;
    TextView engc, labeling;
    TextView espc;
    ImageView audioIV;
    EditText spanishTB;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer ;
    Boolean reproduciendo = false;

    int whoIsPressed = -1;
    int correctas = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.quiz_fragment, container, false);

        //correctTV = (TextView) rootView.findViewById(R.id.correctLbl);
        qEnlazar = (LinearLayout) rootView.findViewById(R.id.qRelacionar);
        qPalabra = (LinearLayout) rootView.findViewById(R.id.qPalabras);
        englishTV = (TextView) rootView.findViewById(R.id.englisWord);
        spanishTB = (EditText) rootView.findViewById(R.id.etEsp);
        loC = (LinearLayout) rootView.findViewById(R.id.loCorrection);
        engc = (TextView) rootView.findViewById(R.id.engCorrect);
        espc = (TextView) rootView.findViewById(R.id.espCorrect);
        editTextESP= (EditText) rootView.findViewById(R.id.etEsp);
        audioIV = (ImageView) rootView.findViewById(R.id.sonidoIV);
        leng = (LinearLayout) rootView.findViewById(R.id.layoutenglish);
        labeling = (TextView) rootView.findViewById(R.id.labelingles);
        eng1 = (TextView) rootView.findViewById(R.id.eng1);
        eng2 = (TextView) rootView.findViewById(R.id.eng2);
        eng3 = (TextView) rootView.findViewById(R.id.eng3);
        eng4 = (TextView) rootView.findViewById(R.id.eng4);
        eng5 = (TextView) rootView.findViewById(R.id.eng5);
        esp1 = (TextView) rootView.findViewById(R.id.esp1);
        esp2 = (TextView) rootView.findViewById(R.id.esp2);
        esp3 = (TextView) rootView.findViewById(R.id.esp3);
        esp4 = (TextView) rootView.findViewById(R.id.esp4);
        esp5 = (TextView) rootView.findViewById(R.id.esp5);
        relationTV = new ArrayList<>();
        relationTV.add(esp1);
        relationTV.add(esp2);
        relationTV.add(esp3);
        relationTV.add(esp4);
        relationTV.add(esp5);
        relationTV.add(eng1);
        relationTV.add(eng2);
        relationTV.add(eng3);
        relationTV.add(eng4);
        relationTV.add(eng5);

        //CHANGE FONT TEXT
        Typeface tf1 = Typeface.createFromAsset(getContext().getAssets(), "letterforlearners.ttf");
        englishTV.setTypeface(tf1);

        ////OPEN KEYBOARD ON EDIT TEXT SELECTED
        editTextESP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(editTextESP, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        editTextESP.requestFocus();

        disponibles = palabrasDisponibles();
        Collections.shuffle(disponibles);
        if(disponibles.size()!=0){
            wordQ = wordFromId(disponibles.get(0));
            siguientePalabra();
        }else{
             spanishTB.setVisibility(View.INVISIBLE);
        //mButton.setVisibility(View.INVISIBLE);
         }

        reproducirOnClickListener();


        //correctTV.setVisibility(View.INVISIBLE);
        loC.setVisibility(View.INVISIBLE);
        ViewGroup.LayoutParams params = qEnlazar.getLayoutParams();
        params.height = 0;
        qEnlazar.setLayoutParams(params);

        //correctTV.setHeight(0);

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;
                TextView adv = (TextView) rootView.findViewById(R.id.advertence);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    ViewGroup.LayoutParams params = adv.getLayoutParams();
                    params.height = 0;
                    adv.setLayoutParams(params);
                }
                else {
                    ViewGroup.LayoutParams params = adv.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    adv.setLayoutParams(params);
                }
            }
        });

        return rootView;
    }


    public ArrayList<Integer> palabrasDisponibles (){
        ArrayList<Integer> idDisponibles = new ArrayList<Integer>();
        ////GENERA UN NUMERO ALEATORIO ENTRE 1 Y EL MAXIMO DE PREGUNTAS
        String[] projection = {
                WordEntry._ID};

        String selection = WordEntry.COLUMN_FAVOURITE + "=?";
        String[] selectionArgs = new String[] {Integer.toString(1)};

        Cursor cursor = getActivity().getContentResolver().query(
                WordEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);

        int idWordRand = cursor.getColumnIndex(WordEntry._ID);

        //GUARDA TODAS LAS IDs DE LAS PALABRAS EN FAVORITOS
        while (cursor.moveToNext()) {
            int IDwordRand = cursor.getInt(idWordRand);
            idDisponibles.add(IDwordRand);
        }
        return idDisponibles;
    }


    public  Word wordFromId (int id) {
            ///PRINT RANDOM QUESTION
            String[] projection2 = {
                    WordEntry._ID,
                    WordEntry.COLUMN_ENG,
                    WordEntry.COLUMN_ESP,
                    WordEntry.COLUMN_CATEGORY,
                    WordEntry.COLUMN_FAVOURITE,
                    WordEntry.COLUMN_AUDIO};

            Cursor cursor = getActivity().getContentResolver()
                    .query(Uri.withAppendedPath(WordEntry.CONTENT_URI, Integer.toString(id)),
                            //Uri.withAppendedPath(PreguntasEntry.CONTENT_URI, "1"),
                            projection2,
                            null,
                            null,
                            null);

            int idW = cursor.getColumnIndex(WordEntry._ID);
            int engW = cursor.getColumnIndex(WordEntry.COLUMN_ENG);
            int espW = cursor.getColumnIndex(WordEntry.COLUMN_ESP);
            int cat = cursor.getColumnIndex(WordEntry.COLUMN_CATEGORY);
            int audi = cursor.getColumnIndex(WordEntry.COLUMN_AUDIO);
            int favW = cursor.getColumnIndex(WordEntry.COLUMN_FAVOURITE);

            cursor.moveToFirst();
            int IDword = cursor.getInt(idW);
            String eng = cursor.getString(engW);
            String esp = cursor.getString(espW);
            int cate = cursor.getInt(cat);
            String audio = cursor.getString(audi);
            AudioSavePathInDevice = cursor.getString(audi);
            int fav = cursor.getInt(favW);

            Word palabra = new Word(IDword, eng, esp, cate, audio, fav);

            return palabra;

    }

    public void siguientePalabra(){
        buttonCount = 0;
        ViewGroup.LayoutParams params = qEnlazar.getLayoutParams();
        params.height = 0;
        qEnlazar.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = qPalabra.getLayoutParams();
        params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        qPalabra.setLayoutParams(params2);

        englishTV.setTextColor(Color.parseColor("#111111"));
        //correctTV.setHeight(0);
        //correctTV.setVisibility(View.INVISIBLE);
        loC.setVisibility(View.INVISIBLE);
        editTextESP.setText("");
        editTextESP.setEnabled(true);
        editTextESP.clearFocus();
        editTextESP.requestFocus();

        Collections.shuffle(disponibles);
        wordQ = wordFromId(disponibles.get(0));

        engesp = (int) (Math.random() * 2) + 1;

        if(engesp == 1){
            englishTV.setText(wordQ.getEnglish().toUpperCase());
            editTextESP.setHint("Write the translation on your lenguge");
        }else if (engesp == 2){
            englishTV.setText(wordQ.getSpanish().toUpperCase());
            editTextESP.setHint("Write the translation on english");
        }

    }

    public void siguienteEnlazar (){
        correctas=0;
        ViewGroup.LayoutParams params = qEnlazar.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        qEnlazar.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = qPalabra.getLayoutParams();
        params2.height = 0;
        qPalabra.setLayoutParams(params2);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setVisibility(View.INVISIBLE);

        for(int i = 0; i <=9; i++){
            relationTV.get(i).setEnabled(true);
            GradientDrawable currendPressed = (GradientDrawable) relationTV.get(i).getBackground().mutate();
            currendPressed.setColor(Color.parseColor("#effaff"));
            currendPressed.invalidateSelf();
        }

        ArrayList<Word> wordsForRelation = fiveWords();
        randomFill(wordsForRelation);

        setOnClickWord();
    }

    public void randomFill (ArrayList<Word> words){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<5; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        Integer[] resp = list.toArray(new Integer[list.size()]);
        Collections.shuffle(list);
        Integer[] reng = list.toArray(new Integer[list.size()]);

        for (int i = 0 ; i < 5 ; i++){
            relationTV.get(i).setText(words.get(resp[i]).getSpanish());
            relationTV.get(i).setContentDescription(Integer.toString(words.get(resp[i]).getIdWord()));
            relationTV.get(i+5).setText(words.get(reng[i]).getEnglish());
            relationTV.get(i+5).setContentDescription(Integer.toString(words.get(reng[i]).getIdWord()));
        }
    }

    public ArrayList fiveWords (){
        ArrayList<Word> fillWords = new ArrayList();
        boolean isAlreadyIn = false;

        Collections.shuffle(disponibles);
        wordQ = wordFromId(disponibles.get(0));
        fillWords.add(wordQ);
        wordQ = wordFromId(disponibles.get(1));
        fillWords.add(wordQ);
        wordQ = wordFromId(disponibles.get(2));
        fillWords.add(wordQ);
        wordQ = wordFromId(disponibles.get(3));
        fillWords.add(wordQ);
        wordQ = wordFromId(disponibles.get(4));
        fillWords.add(wordQ);


        return fillWords;
    }

    public void corregir (String engtxt, String esptxt){
        buttonCount = 1;
        //correctTV.setHeight(200);
        if(engtxt.equals(esptxt)){
            //correcto
            englishTV.setTextColor(Color.parseColor("#009900"));
            //correctTV.setTextColor(Color.parseColor("#009900"));
            //correctTV.setText("CORRECT!");
        }else {
            //fallo
            englishTV.setTextColor(Color.parseColor("#DF0101"));
            //correctTV.setTextColor(Color.parseColor("#DF0101"));
            //correctTV.setText("WRONG!");
        }
        if (wordQ.getAudio() != null){
            audioIV.setImageResource(R.drawable.play_circle);
        }else {
            audioIV.setImageResource(R.drawable.grey_play_button);
        }
        //correctTV.setVisibility(View.VISIBLE);
        loC.setVisibility(View.VISIBLE);
        editTextESP.setEnabled(false);
    }

    public void rep () throws IllegalArgumentException, SecurityException, IllegalStateException{

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

                audioIV.setImageResource(R.drawable.pause_circle);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        audioIV.setImageResource(R.drawable.play_circle);
                        reproduciendo = false;
                    }
                });
            }
        }else {
            reproduciendo = false;
            audioIV.setImageResource(R.drawable.play_circle);
            mediaPlayer.stop();
        }
    }

    public void reproducirOnClickListener(){
        leng.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(wordQ.getAudio()!=null){
                    rep();
                }
            }
        });
        labeling.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(wordQ.getAudio()!=null){
                    rep();
                }
            }
        });


    }

    ///LISENER PARA EL FLOATING ACTION BUTTON QUE ESTA EN EL MAIN
    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            MainActivity mainActivity = (MainActivity) getActivity();

            //Static toolBar
            AppBarLayout appBarLayout = (AppBarLayout) mainActivity.findViewById(R.id.appbar);
            appBarLayout.setExpanded(false, true); ///setExpanded(expanded, animation)

            disponibles = palabrasDisponibles();
            Collections.shuffle(disponibles);
            if(disponibles.size()==0){
                mainActivity.fab.setVisibility(View.INVISIBLE);
                spanishTB.setVisibility(View.INVISIBLE);
                qEnlazar.setVisibility(View.INVISIBLE);
                englishTV.setText("YOU DON'T HAVE ANY FAVOURITE WORD");
            }else {
                //wordQ = wordFromId(disponibles.get(0));
                siguientePalabra();
                mainActivity.fab.setVisibility(View.VISIBLE);
                spanishTB.setVisibility(View.VISIBLE);
                mainActivity.fab.setImageResource(R.drawable.ic_spellcheck_white_24dp);
                activarFab();
            }
        }

    }

    public void activarFab()
    {
        if (!getUserVisibleHint())
        {
            return;
        }
        final MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String esptxt = editTextESP.getText().toString().toUpperCase().trim();
                String engtxt;

                engc.setText(wordQ.getEnglish());
                espc.setText(wordQ.getSpanish());

                if(engesp==1){
                    engtxt = wordQ.getSpanish().toUpperCase().trim();
                }else{
                    engtxt = wordQ.getEnglish().toUpperCase().trim();
                }

                if(buttonCount == 0){
                    corregir(engtxt,esptxt);
                    mainActivity.fab.setImageResource(R.drawable.ic_arrow_forward_white_24dp);
                }else if (buttonCount == 1){
                    if (disponibles.size() >= 5){
                        int qTipe = (int) (Math.random() * 2) + 1;
                        if(qTipe == 1){
                            siguientePalabra();
                        }else {
                            siguienteEnlazar();
                        }

                    } else{
                        siguientePalabra();
                    }

                    // mainActivity.fab.setImageResource(R.drawable.ic_spellcheck_white_24dp);
                }

            }
        });
    }

    public void setOnClickWord(){
        final int notPressed = Color.parseColor("#effaff");
        final int pressed = Color.parseColor("#16b9ff");
        final int correct = Color.parseColor("#a3ffaf");
        final MainActivity mainActivity = (MainActivity)getActivity();

        relationTV.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(0).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 0;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 0;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(0).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(0).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                    mainActivity.fab.setImageResource(R.drawable.ic_arrow_forward_white_24dp);
                    buttonCount = 1;
                }
            }
        });
        relationTV.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(1).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 1;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 1:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 2:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 1;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(1).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(1).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });

        relationTV.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(2).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 2;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 2:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 1:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 2;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(2).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(2).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas ++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });

        relationTV.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(3).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 3;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 3:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 2:
                    case 1:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 3;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(3).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(3).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });

        relationTV.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(4).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 4;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 4:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 2:
                    case 3:
                    case 1:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 4;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(4).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(4).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });

        relationTV.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(5).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 5;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 5:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 5;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(5).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(5).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });
        relationTV.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(6).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 6;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 6:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 5:
                    case 7:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 6;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(6).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(6).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });
        relationTV.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(7).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 7;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 7:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 6:
                    case 5:
                    case 8:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 7;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(7).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(7).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });
        relationTV.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(8).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 8;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 8:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 6:
                    case 7:
                    case 5:
                    case 9:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 8;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(8).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(8).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });

        relationTV.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable sd = (GradientDrawable) relationTV.get(9).getBackground().mutate();
                GradientDrawable currendPressed;
                switch (whoIsPressed){
                    case -1:
                        whoIsPressed = 9;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 9:
                        whoIsPressed = -1;
                        sd.setColor(notPressed);
                        sd.invalidateSelf();
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 5:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        currendPressed.setColor(notPressed);
                        currendPressed.invalidateSelf();

                        whoIsPressed = 9;
                        sd.setColor(pressed);
                        sd.invalidateSelf();
                        break;
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        currendPressed = (GradientDrawable) relationTV.get(whoIsPressed).getBackground().mutate();
                        if(relationTV.get(9).getContentDescription().equals(relationTV.get(whoIsPressed).getContentDescription())){
                            currendPressed.setColor(correct);
                            currendPressed.invalidateSelf();
                            sd.setColor(correct);
                            sd.invalidateSelf();
                            relationTV.get(9).setEnabled(false);
                            relationTV.get(whoIsPressed).setEnabled(false);
                            correctas++;
                        }else{
                            currendPressed.setColor(notPressed);
                            currendPressed.invalidateSelf();
                            sd.setColor(notPressed);
                            sd.invalidateSelf();
                        }
                        whoIsPressed = -1;
                        break;
                }
                if(correctas == 5){
                    mainActivity.fab.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
