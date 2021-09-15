package com.example.videoplayer.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.videoplayer.databinding.ActivityVideosListBinding;
import com.example.videoplayer.model.Video;
import com.example.videoplayer.remote.DataFetching;

import java.util.ArrayList;

public class VideosListActivity extends AppCompatActivity implements VideoAdapter.IVideoClick {
    private ArrayList<Video> videoArrayList;
    private ArrayList<Video> favoriteVideosList;
    private String userEmail = "";
    private VideoAdapter videoAdapter;
    private static final int STORAGE_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userEmail = getIntent().getExtras().getString("email");
        ActivityVideosListBinding binding = ActivityVideosListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        videoArrayList = new ArrayList<>();
        favoriteVideosList = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoArrayList,favoriteVideosList, this,userEmail,getApplicationContext());
        getUserFavoriteVideos();
        binding.videoRecyclerView.setAdapter(videoAdapter);
        binding.videoRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        }
        //Permissions granted
        else {
            getUserVideos();
        }
    }

    //This is will be the results of the dialog that pops up to the user
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
                getUserVideos();
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void getUserFavoriteVideos(){
        DataFetching dataFetching = new DataFetching(getApplicationContext());
        favoriteVideosList.addAll(dataFetching.getAllVideos(userEmail));
        videoAdapter.notifyDataSetChanged();
    }

    private void getUserVideos() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String videoTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                Bitmap videoThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND);
                videoArrayList.add(new Video(videoTitle, videoPath, videoThumbnail));
            } while (cursor.moveToNext());
        }
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVideoClick(int position) {
        Intent intent = new Intent(this, VideoPlayer.class);
        intent.putExtra("videoName", videoArrayList.get(position).getName());
        intent.putExtra("videoPath", videoArrayList.get(position).getPath());
        startActivity(intent);
    }
}