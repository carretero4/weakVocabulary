package pons.carretero.myvocabulary.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by carre on 27/11/2017.
 */

public class WordContract {
    public static final String CONTENT_AUTHORITY = "pons.carretero.myvocabulary";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PREGUNTAS = "words";

    public static final class WordEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PREGUNTAS);

        public final static String TABLE_NAME = "words";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ENG = "eng";
        public final static String COLUMN_ESP = "esp";
        public final static String COLUMN_CATEGORY = "category";
        public final static String COLUMN_AUDIO = "audio";
        public final static String COLUMN_TAG = "tag";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_FAVOURITE = "favourite";

    }
}
