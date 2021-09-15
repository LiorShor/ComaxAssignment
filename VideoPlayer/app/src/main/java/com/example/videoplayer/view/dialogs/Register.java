package com.example.videoplayer.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.videoplayer.databinding.RegisterDialogBinding;
import com.example.videoplayer.model.User;
import com.example.videoplayer.remote.DataFetching;

public class Register extends ConstraintLayout {
    private final Dialog m_RegisterDialog;
    private RegisterDialogBinding binding;

    public Register(Context context) {
        super(context);
        m_RegisterDialog = new Dialog(context);
        setDialogSettings();
        setOnClickRegisterButton();
    }

    private void setOnClickRegisterButton() {
        binding.registerButton.setOnClickListener(view ->
        {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextRegisterPassword.getText().toString();
            String rePassword = binding.editTextRePassword.getText().toString();
            String name = binding.editTextPersonName.getText().toString();
            if(validation(email,password,rePassword,name)){
                DataFetching dataFetching = new DataFetching(getContext());
                dataFetching.registerNewUser(email,password,name);
            }
        });
    }

    private boolean validation(String email, String password, String rePassword, String name) {
    return true; //todo: make validation
    }

    private void setDialogSettings() {
        binding = RegisterDialogBinding.inflate(LayoutInflater.from(getContext()));
        m_RegisterDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        m_RegisterDialog.setContentView(binding.getRoot());
        m_RegisterDialog.show();
        m_RegisterDialog.setCanceledOnTouchOutside(true);
        setDialogWidthAndHeight();

    }

    private void setDialogWidthAndHeight() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int m_Width = metrics.widthPixels;
        int m_Height = metrics.heightPixels;
        m_RegisterDialog.getWindow().setLayout((6 * m_Width) / 7, (4 * m_Height) / 5);
    }

}
