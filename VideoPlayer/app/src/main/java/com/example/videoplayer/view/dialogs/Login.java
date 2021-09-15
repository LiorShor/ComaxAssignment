package com.example.videoplayer.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.videoplayer.R;
import com.example.videoplayer.databinding.LoginDialogBinding;
import com.example.videoplayer.remote.DataFetching;
import com.example.videoplayer.view.activities.VideoPlayer;
import com.example.videoplayer.view.activities.VideosListActivity;

public class Login extends ConstraintLayout  {
    private LoginDialogBinding binding;
    private final Dialog loginDialog;

    public Login(Context context) {
        super(context);
        loginDialog = new Dialog(context);
        setDialogSettings();
        setOnClickLoginButton();
        setOnClickRegisterButton();
    }

    private void setOnClickRegisterButton() {
        binding.editTextSignUp.setOnClickListener(view ->
        {
            loginDialog.dismiss();
            new Register(getContext());
        });
    }

    private void setOnClickLoginButton() {
        binding.signInButton.setOnClickListener(view ->
        {
            String userEmail = binding.editTextEmailAddress.getText().toString();
            String userPassword = binding.editTextPassword.getText().toString();
            if(stringChecker(userEmail,userPassword)) {
                DataFetching dataFetching = new DataFetching(getContext());
                boolean results= dataFetching.signIn(userEmail, userPassword);
                Intent intent = new Intent(getContext(), VideosListActivity.class);
                intent.putExtra("email",userEmail);
                if(results)
                    getContext().startActivity(intent);
            }
        });
    }

    private boolean stringChecker(String userEmail, String userPassword) {
        boolean isValid = true;
        if(userEmail.isEmpty())
        {
            binding.editTextEmailAddress.setHintTextColor(Color.RED);
            isValid = false;
        }
        if(userPassword.isEmpty())
        {
            binding.editTextPassword.setHintTextColor(Color.RED);
            isValid = false;
        }
        return isValid;
    }

    private void setDialogSettings(){
        binding = LoginDialogBinding.inflate(LayoutInflater.from(getContext()));
        loginDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loginDialog.setContentView(binding.getRoot());
        loginDialog.show();
        loginDialog.setCanceledOnTouchOutside(true);
        setDialogWidthAndHeight();

    }

    private void setDialogWidthAndHeight()
    {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int m_Width = metrics.widthPixels;
        int m_Height = metrics.heightPixels;
        loginDialog.getWindow().setLayout((6 * m_Width)/7, (4 * m_Height)/5);
    }
}
