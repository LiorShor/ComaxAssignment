package com.example.videoplayer.remote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.videoplayer.model.User;
import com.example.videoplayer.model.Video;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "comax";
    private static final String TABLE_USER_NAME = "users";
    private static final String TAG = "DatabaseHandler";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String TABLE_VIDEO_NAME = "favoritevideos";
    private static final String KEY_PATH = "path";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_USER_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_PASSWORD + " TEXT);";
        String query2 = "CREATE TABLE " + TABLE_VIDEO_NAME +
                "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_EMAIL + " TEXT, " +
                KEY_PATH + " TEXT);";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO_NAME);
        onCreate(sqLiteDatabase);
    }


    public boolean addNewUser(String email, String password, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("email",email);
        long result = db.insert(TABLE_USER_NAME, null, contentValues);
        if (result == -1)
            Log.d(TAG, "addNewUser: Failed to add new user");
        else
            Log.d(TAG, "addNewUser: New user added successfully");
        db.close();
        return true;
    }

    public List<User> getAllContacts() {
        List<User> contactList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User contact = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public Cursor getData(String userEmail, String userPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TABLE_USER_NAME +" where email = ? and password = ? ", new String[]{userEmail, userPassword});
        return res;
    }

    public boolean addNewVideo(String name, String path,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("path", path);
        long result = db.insert(TABLE_VIDEO_NAME, null, contentValues);
        if (result == -1)
            Log.d(TAG, "addNewVideo: Failed to add new video");
        else
            Log.d(TAG, "addNewVideo: New video added successfully");
        db.close();
        return true;
    }

    public List<Video> getAllVideos(String email) {
        List<Video> favoriteVideosList = new ArrayList<>();
        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from "+TABLE_VIDEO_NAME +" where email = ? ", new String[]{email});
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Video video = new Video(cursor.getString(1), cursor.getString(2));
                // Adding contact to list
                favoriteVideosList.add(video);
            } while (cursor.moveToNext());
        }
        return favoriteVideosList;
    }

    public void deleteVideoFromFavorite(String videoPath){
        List<Video> favoriteVideosList = new ArrayList<>();
        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("DELETE from "+TABLE_VIDEO_NAME +" where videoPath = ? ", new String[]{videoPath});
    }

}
