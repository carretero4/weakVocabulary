package pons.carretero.myvocabulary.data;

import android.net.Uri;
import android.provider.BaseColumns;

import static pons.carretero.myvocabulary.data.WordContract.PATH_PREGUNTAS;

/**
 * Created by carre on 27/11/2017.
 */

public class CategoryContract {
    public static final String CONTENT_AUTHORITY = "pons.carretero.myvocabulary";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CATEGORYS = "categorys";

    public static final class CategoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CATEGORYS);

        public final static String TABLE_NAME = "categorys";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "nombre";



    }
}
