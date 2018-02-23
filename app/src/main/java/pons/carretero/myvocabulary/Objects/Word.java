package pons.carretero.myvocabulary.Objects;


import android.view.View;

import static android.R.attr.id;

/**
 * Created by carre on 29/11/2017.
 */

public class Word {
    private int idWord;
    private String english;
    private String spanish;
    private int category;
    private String audio;
    private int favourite;

    public Word (int id, String eng, String esp, int cat, String audi, int fav){
        idWord = id;
        english = eng;
        spanish = esp;
        category = cat;
        audio = audi;
        favourite = fav;
    }

    public String getAudio() {
        return audio;
    }

    public int getIdWord() {
        return idWord;
    }

    public String getEnglish() {
        return english;
    }

    public String getSpanish() {
        return spanish;
    }

    public int getCategory() {
        return category;
    }

    public int getFavourite() {
        return favourite;
    }
}
