package com.example.investool.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.investool.R;
import com.example.investool.User.User;
import com.google.android.material.textview.MaterialTextView;

public class UserMenuDialog extends Dialog {

    private User user;
    private MaterialTextView dialog_LBL_name;
    private MaterialTextView dialog_LBL_role;
    private MaterialTextView dialog_LBL_email;
    private MaterialTextView dialog_LBL_avatar;

    public UserMenuDialog(@NonNull Context context, User user) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_menu_dialog);
        this.user = user;
        setupDialog();
    }

    private void setupDialog() {
        dialog_LBL_name = findViewById(R.id.dialog_LBL_name);
        dialog_LBL_role = findViewById(R.id.dialog_LBL_role);
        dialog_LBL_email = findViewById(R.id.dialog_LBL_email);
        dialog_LBL_avatar = findViewById(R.id.dialog_LBL_avatar);

        if (user != null) {
            dialog_LBL_name.setText(user.getUsername());
            dialog_LBL_role.setText(user.getRole());
            dialog_LBL_email.setText(user.getUserId().getEmail());
            dialog_LBL_avatar.setText(user.getAvatar());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        getWindow().setAttributes(layoutParams);

        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }
}