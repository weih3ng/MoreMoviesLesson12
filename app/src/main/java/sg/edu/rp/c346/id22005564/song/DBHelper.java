package sg.edu.rp.c346.id22005564.song;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "song.db";
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_DATE = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_STARS + " INTEGER,"
                + COLUMN_DATE + " INTEGER )";
        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    public void insertSong(String title, String singers, int year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_DATE, year);
        values.put(COLUMN_STARS, stars);
        db.insert(TABLE_SONG, null, values);
        db.close();
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONG, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String singers = cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE));
                int stars = cursor.getInt(cursor.getColumnIndex(COLUMN_STARS));

                Song song = new Song(id, title, singers, year, stars);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return songList;
    }

    public ArrayList<Song> getAllSongsWithFiveStars() {
        ArrayList<Song> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_STARS + " = ?";
        String[] selectionArgs = {"5"};
        Cursor cursor = db.query(TABLE_SONG, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String singers = cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE));
                int stars = cursor.getInt(cursor.getColumnIndex(COLUMN_STARS));

                Song song = new Song(id, title, singers, year, stars);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return songList;
    }

    public ArrayList<Integer> getUniqueYears() {
        ArrayList<Integer> yearsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_DATE};
        Cursor cursor = db.query(true, TABLE_SONG, columns, null, null, null, null, null, null);

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

    public ArrayList<Song> getSongsByYear(int year) {
        ArrayList<Song> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(year)};
        Cursor cursor = db.query(TABLE_SONG, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String singers = cursor.getString(cursor.getColumnIndex(COLUMN_SINGERS));
                int stars = cursor.getInt(cursor.getColumnIndex(COLUMN_STARS));

                Song song = new Song(id, title, singers, year, stars);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return songList;
    }

    public void updateSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_SINGERS, song.getSingers());
        values.put(COLUMN_DATE, song.getYear());
        values.put(COLUMN_STARS, song.getStars());
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(song.getId())};
        db.update(TABLE_SONG, values, selection, selectionArgs);
        db.close();
    }

    public void deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(TABLE_SONG, selection, selectionArgs);
        db.close();
    }
}






