package pons.carretero.myvocabulary.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static java.security.AccessController.getContext;
import static pons.carretero.myvocabulary.data.categoriasDbHelper.LOG_TAG;

/**
 * Created by carre on 27/11/2017.
 */

public class CategoryProvaider extends ContentProvider
    {
        /** Tag for the log messages */
        public static final String LOG_TAG = CategoryProvaider.class.getSimpleName();
        private static final int CATEGORY = 100;
        private static final int CATEGORY_ID = 101;
        private static final int CATEGORY_NAME = 102;
        private static final int WORD = 103;
        private static final int WORD_ID = 104;
        private static final int WORD_CATEGORY = 105;
        private static final int WORD_FAVOURITE = 106;
        private static final int CATEGORY_SELECTION = 107;

        private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        static
        {
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "categorys", CATEGORY);
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "categorys/#", CATEGORY_ID);
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "categorys/*", CATEGORY_NAME);
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "words", WORD);
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "words/#", WORD_ID);
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "words/category/#", WORD_CATEGORY);
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "words/favourite/#", WORD_FAVOURITE);
            sUriMatcher.addURI(CategoryContract.CONTENT_AUTHORITY, "categorys/selecion", CATEGORY_SELECTION);

        }

    /*Data HELPER OBJECT*/
        private categoriasDbHelper mDbHelper;


        @Override
        public boolean onCreate() {
            mDbHelper = new categoriasDbHelper(getContext());
            return true;
        }

        /**
         * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
         */
        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                String sortOrder) {


            SQLiteDatabase database = mDbHelper.getReadableDatabase();

            Cursor cursor;

            int match = sUriMatcher.match(uri);
            //Log.d("Match", Integer.toString(match));
            switch (match){
                case CATEGORY:
                    cursor = database.query(CategoryContract.CategoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                case CATEGORY_ID:
                    selection = CategoryContract.CategoryEntry._ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    cursor = database.query(CategoryContract.CategoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                case CATEGORY_NAME:
                    selection = CategoryContract.CategoryEntry.COLUMN_NAME + "=?";
                    selectionArgs = new String[]{String.valueOf(uri.getPathSegments().get(1))};
                    cursor = database.query(CategoryContract.CategoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                case CATEGORY_SELECTION:
                    selectionArgs = new String[]{String.valueOf(uri.getPathSegments().get(1))};
                    cursor = database.query(CategoryContract.CategoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                case WORD:
                    cursor = database.query(WordContract.WordEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                case WORD_ID:
                    selection = WordContract.WordEntry._ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    cursor = database.query(WordContract.WordEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                case WORD_CATEGORY:
                    selection = WordContract.WordEntry.COLUMN_CATEGORY + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    cursor = database.query(WordContract.WordEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                    break;
                default:
                    throw new IllegalArgumentException("can not query: " + uri);
            }
            //Uri BASE_CONTENT_URI = Uri.parse("content://" + "pons.carretero.myvocabulary/categorys");
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

        /**
         * Insert new data into the provider with the given ContentValues.
         */
        @Override
        public Uri insert(Uri uri, ContentValues contentValues) {

            final int match = sUriMatcher.match(uri);
            switch (match) {
                case CATEGORY:
                    return insertCategory(uri, contentValues);
                case WORD:
                    return insertWord(uri, contentValues);
                default:
                    throw new IllegalArgumentException("Insertion is not supported for " + uri);
            }
        }

    private Uri insertCategory(Uri uri, ContentValues values) {

        String pregunta = values.getAsString(CategoryContract.CategoryEntry.COLUMN_NAME);
        if (pregunta == null) {
            throw new IllegalArgumentException("Se necesita una pregunta");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert the new pet with the given values
        long id = database.insert(CategoryContract.CategoryEntry.TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //Notify all listeners that the DDBB has changed
        getContext().getContentResolver().notifyChange(uri,null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */

    private Uri insertWord(Uri uri, ContentValues values) {

        String eng = values.getAsString(WordContract.WordEntry.COLUMN_ENG);
        if (eng == null) {
            throw new IllegalArgumentException("Se necesita ingles");
        }
        String esp = values.getAsString(WordContract.WordEntry.COLUMN_ESP);
        if (esp == null) {
            throw new IllegalArgumentException("Se necesita ingles");
        }
        String idC = values.getAsString(WordContract.WordEntry.COLUMN_CATEGORY);
        if (idC == null) {
            throw new IllegalArgumentException("Se necesita ingles");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert the new pet with the given values
        long id = database.insert(WordContract.WordEntry.TABLE_NAME, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        //Uri BASE_CONTENT_URI = Uri.parse("content://" + "pons.carretero.myvocabulary/categorys");
        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORD:
                return updateWord(uri, contentValues, selection, selectionArgs);
            case CATEGORY_ID:
                return updateWord(uri, contentValues, selection, selectionArgs);
            case CATEGORY_NAME:
                //Mal
                selection = CategoryContract.CategoryEntry.COLUMN_NAME + "=?";
                selectionArgs = new String[] { String.valueOf(uri.getPathSegments().get(0)) };
                return updateWord(uri, contentValues, selection, selectionArgs);
            case WORD_ID:
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateWord(uri, contentValues, selection, selectionArgs);
            case WORD_CATEGORY:

                return updateWord(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateWord(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        //return database.update(WordContract.WordEntry.TABLE_NAME, values, selection, selectionArgs);

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(WordContract.WordEntry.TABLE_NAME, values, selection, selectionArgs);

        //Uri BASE_CONTENT_URI = Uri.parse("content://" + "pons.carretero.myvocabulary/categorys");
        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CATEGORY:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(CategoryContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORY_ID:
                // Delete a single row given by the ID in the URI
                selection = CategoryContract.CategoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(CategoryContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case WORD_ID:
                // Delete all rows that match the selection and selection args
                selection = WordContract.WordEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(WordContract.WordEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        //Uri BASE_CONTENT_URI = Uri.parse("content://" + "pons.carretero.myvocabulary/categorys");

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
