package com.example.videoplayer.model;

import java.util.ArrayList;

public class FavoritesVideos {
    private String userID;
    private ArrayList<String> favoriteVideos;

    public FavoritesVideos(String userID, ArrayList<String> favoriteVideos) {
        this.userID = userID;
        this.favoriteVideos = favoriteVideos;
    }

    public ArrayList<String> getFavoriteVideos() {
        return favoriteVideos;
    }

    public String getUserID() {
        return userID;
    }
}
