package pons.carretero.myvocabulary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import pons.carretero.myvocabulary.data.CategoryContract.CategoryEntry;
import pons.carretero.myvocabulary.data.WordContract.WordEntry;


import static android.graphics.PorterDuff.Mode.ADD;
import static pons.carretero.myvocabulary.data.WordContract.WordEntry.COLUMN_CATEGORY;

/**
 * Created by carre on 27/11/2017.
 */

public class categoriasDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = categoriasDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "myvocabulary.db";
    private static final int DATABASE_VERSION = 2;

    /**
     * Constructs a new instance of {@link categoriasDbHelper}.
     *
     * @param context of the app
     */
    public categoriasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onOpen(db);

        db.execSQL("PRAGMA foreign_keys=ON;");

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_CATEGORIAS_TABLE =  "CREATE TABLE IF NOT EXISTS " + CategoryEntry.TABLE_NAME + " ("
                + CategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CategoryEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_CATEGORIAS_TABLE);

        String SQL_CREATE_PALABRAS_TABLE =  "CREATE TABLE IF NOT EXISTS " + WordEntry.TABLE_NAME + " ("
                + WordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordEntry.COLUMN_ENG + " TEXT NOT NULL, "
                + WordEntry.COLUMN_ESP + " TEXT NOT NULL, "
                + WordEntry.COLUMN_TAG + " TEXT, "
                + WordEntry.COLUMN_DESCRIPTION + " TEXT, "
                + WordEntry.COLUMN_AUDIO + " TEXT, "
                + WordEntry.COLUMN_FAVOURITE + " INTEGER, "
                + WordEntry.COLUMN_CATEGORY + " INTEGER, "
                + "FOREIGN KEY ("+WordEntry.COLUMN_CATEGORY+") REFERENCES "+CategoryEntry.TABLE_NAME+"("+CategoryEntry._ID+"));";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PALABRAS_TABLE);

        //String SQL_FK = "INSERT INTO "+ WordEntry.TABLE_NAME + " VALUES('HELLO', 'HOLA', 1)";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// If you need to add a column
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE " + WordEntry.TABLE_NAME + " ADD COLUMN " + WordEntry.COLUMN_FAVOURITE + " INTEGER DEFAULT 1");
        }
    }
}

