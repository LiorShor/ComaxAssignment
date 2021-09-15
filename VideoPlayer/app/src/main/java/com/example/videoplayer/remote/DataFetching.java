package com.example.videoplayer.remote;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.videoplayer.model.Video;

import java.util.List;

public class DataFetching {
    private static final String TAG = "DataFetching";
    private final DatabaseHandler databaseHandler;

    public DataFetching(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }

    public boolean signIn(String userEmail, String userPassword) {
        boolean loginStatus = false;
        Log.d(TAG, "signIn");
        Cursor cursor = databaseHandler.getData(userEmail, userPassword);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getString(cursor.getColumnIndex("email")).equals(userEmail))
                loginStatus = true;
            cursor.close();
        }
        return loginStatus;
}

    public void addNewVideo(String name, String path, String email) {
        databaseHandler.addNewVideo(name, path, email);
    }


    public void registerNewUser(String email, String password, String name) {
        databaseHandler.addNewUser(email, password, name);
    }

    public List<Video> getAllVideos(String userEmail) {
        return databaseHandler.getAllVideos(userEmail);
    }
}
