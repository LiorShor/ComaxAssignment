package com.example.videoplayer.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.example.videoplayer.R;
import com.example.videoplayer.databinding.ActivityVideoPlayerBinding;

public class VideoPlayer extends AppCompatActivity {
    private String videoName, videoPath;
    private ActivityVideoPlayerBinding binding;
    private boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        videoName = getIntent().getStringExtra("videoName");
        videoPath = getIntent().getStringExtra("videoPath");

        binding.videoView.setVideoURI(Uri.parse(videoPath));
        binding.videoView.setOnPreparedListener(mediaPlayer -> {
            binding.customControls.videoSeekBar.setMax(binding.videoView.getDuration());
            binding.videoView.start();
        });

        binding.customControls.videoName.setText(videoName);
        binding.customControls.replayButton.setOnClickListener(view -> binding.videoView.seekTo(binding.videoView.getCurrentPosition() + 10000));
        binding.customControls.forwardButton.setOnClickListener(view -> binding.videoView.seekTo(binding.videoView.getCurrentPosition() - 10000));
        binding.customControls.playPauseButton.setOnClickListener(view -> {
            if (binding.videoView.isPlaying()) {
                binding.videoView.pause();
                binding.customControls.playPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            } else {
                binding.videoView.start();
                binding.customControls.playPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            }
        });
        binding.relativeLayoutVideo.setOnClickListener(view -> {
            if (isOpen) {
                hideControls();
            } else {
                showControls();
            }
            isOpen = !isOpen;
        });
        setHandler();
        initializeSeekBar();
    }

    private void setHandler() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (binding.videoView.getDuration() > 0) {
                    int curPosition = binding.videoView.getCurrentPosition();
                    binding.customControls.videoSeekBar.setProgress(curPosition);
                    binding.customControls.timeRemainTextView.setText(convertTime(binding.videoView.getDuration() - curPosition));
                }
                handler.postDelayed(this, 0);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    private String convertTime(int ms) {
        String time;
        int x, seconds, minutes, hours;
        x = ms / 1000;
        seconds = x % 60;
        x /= 60;
        minutes = x % 60;
        x /= 60;
        hours = x % 60;
        if (hours != 0) {
            time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            time = String.format("%02d:%02d", minutes, seconds);
        }
        return time;
    }

    private void initializeSeekBar() {
        binding.customControls.videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (binding.customControls.videoSeekBar.getId() == R.id.videoSeekBar) {
                    if (b) {
                        binding.videoView.seekTo(i);
                        binding.videoView.start();
                        int currentPosition = binding.videoView.getCurrentPosition();
                        binding.customControls.timeRemainTextView.setText(convertTime(binding.videoView.getDuration() - currentPosition));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void showControls() {
        binding.customControls.relativeLayoutControls.setVisibility(View.VISIBLE);
        final Window window = this.getWindow();
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView != null) {
            decorView.getSystemUiVisibility();
            int uiOptions = ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions = ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions = ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void hideControls() {
        binding.customControls.relativeLayoutControls.setVisibility(View.GONE);
        final Window window = this.getWindow();
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView != null) {
            int uiOptions = decorView.getSystemUiVisibility();
            uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}