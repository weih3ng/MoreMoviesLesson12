package sg.edu.rp.c346.id22005564.song;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 2;
    private static final String DATABASE_NAME = "movie.db";
    private static final String TABLE_MOVIES = "movie";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_DATE = "year";
    private static final String COLUMN_RATING = "rating";
    public static final int RATING_G = 1;
    public static final int RATING_PG = 2;
    public static final int RATING_PG13 = 3;
    public static final int RATING_NC16 = 4;
    public static final int RATING_M18 = 5;
    public static final int RATING_R21 = 6;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_MOVIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_RATING + " INTEGER,"
                + COLUMN_DATE + " INTEGER )";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    public void insertMovie(String title, String genre, int year, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_DATE, year);
        values.put(COLUMN_RATING, rating);
        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }

    public ArrayList<Movies> getAllMovies() {
        ArrayList<Movies> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE));
                int rating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));

                Movies movie = new Movies(id, title, genre, year, rating);
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return movieList;
    }
    public ArrayList<Movies> getAllMoviesWithRating(int rating) {
        ArrayList<Movies> moviesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_DATE, COLUMN_RATING};
        String selection = COLUMN_RATING + " = ?";
        String[] selectionArgs = {String.valueOf(rating)};
        Cursor cursor = db.query(TABLE_MOVIES, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE));
                int movieRating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));

                // Create a new Movies object and add it to the list
                Movies movie = new Movies(id, title, genre, year, movieRating);
                moviesList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return moviesList;
    }

    public ArrayList<Movies> getAllMoviesWithPG13() {
        ArrayList<Movies> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_RATING + " = ?";
        String[] selectionArgs = {String.valueOf(3)}; // PG13 rating has value 3
        Cursor cursor = db.query(TABLE_MOVIES, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE));
                int rating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));

                Movies movie = new Movies(id, title, genre, year, rating);
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return movieList;
    }

    public ArrayList<Integer> getUniqueYears() {
        ArrayList<Integer> yearsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_DATE};
        Cursor cursor = db.query(true, TABLE_MOVIES, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE));
                yearsList.add(year);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return yearsList;
    }

    public ArrayList<Movies> getMoviesByYear(int year) {
        ArrayList<Movies> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(year)};
        Cursor cursor = db.query(TABLE_MOVIES, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                int rating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));

                Movies movie = new Movies(id, title, genre, year, rating);
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return movieList;
    }

    public void updateMovie(Movies movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_DATE, movie.getYear());
        values.put(COLUMN_RATING, movie.getRating());
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(movie.getId())};
        db.update(TABLE_MOVIES, values, selection, selectionArgs);
        db.close();
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(TABLE_MOVIES, selection, selectionArgs);
        db.close();
    }
}
