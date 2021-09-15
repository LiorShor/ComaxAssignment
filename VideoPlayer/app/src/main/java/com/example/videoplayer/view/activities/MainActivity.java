package com.example.videoplayer.view.activities;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videoplayer.R;
import com.example.videoplayer.databinding.ActivityMainBinding;
import com.example.videoplayer.remote.DataFetching;
import com.example.videoplayer.view.dialogs.Login;


public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        // Check if user already signed in to the app
        if(sharedPreferences.getString("userPassword", null) != null) {
            String userEmail = sharedPreferences.getString("userEmail", null);
            String userPassword = sharedPreferences.getString("userPassword", null);
            DataFetching dataFetching = new DataFetching(this);
            dataFetching.signIn(userEmail,userPassword);
        }
        setContentView(R.layout.activity_main);
//        loginButton.setOnClickListener(view -> new Login(this));
    }

    public void login(View view) {
        new Login(this);
    }
}