package com.example.videoplayer.model;

import android.graphics.Bitmap;

public class Video {
    private String name;
    private String path;
    private Bitmap thumbNail;

    public Video(String name, String path, Bitmap thumbNail) {
        this.name = name;
        this.path = path;
        this.thumbNail = thumbNail;
    }

    public Video(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getThumbNail() {
        return thumbNail;
    }
}
