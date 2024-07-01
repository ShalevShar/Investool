package com.example.investool.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.investool.Command.InvokedBy;
import com.example.investool.Command.MiniAppCommandBoundary;
import com.example.investool.Command.ObjectId;
import com.example.investool.Command.TargetObject;
import com.example.investool.Command.UserKey;
import com.example.investool.Network.ApiService;
import com.example.investool.Network.ApproveCallback;
import com.example.investool.R;
import com.example.investool.User.NewUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUp_EDT_email ;
    private EditText signUp_EDT_username;
    private EditText signUp_EDT_avatar;
    private AppCompatButton signUp_BTN_confirm;
    private AppCompatImageButton signUp_BTN_back;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViews();

        initRetrofit();

        signUp_BTN_confirm.setOnClickListener(v -> {
            String email = signUp_EDT_email.getText().toString().trim();
            String username = signUp_EDT_username.getText().toString().trim();
            String avatar = signUp_EDT_avatar.getText().toString().trim();
            if (!email.isEmpty() && !username.isEmpty() && !avatar.isEmpty()){
                if(isEmailValidFormat(email)) {
                    commitSignUp(email, username, avatar, approve -> {
                        if (approve) {
                            Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            goToLoginActivity();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Could not sign up user.", Toast.LENGTH_SHORT).show();
                        }

                    });
                } else {
                        Toast.makeText(SignUpActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                }
            } else {
                    Toast.makeText(SignUpActivity.this, "One or more fields are empty.", Toast.LENGTH_SHORT).show();
                }

        });
        signUp_BTN_back.setOnClickListener(v -> {
            goToLoginActivity();
        });

    }

    private void goToLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        signUp_EDT_email = findViewById(R.id.signUp_EDT_email);
        signUp_EDT_avatar = findViewById(R.id.signUp_EDT_avatar);
        signUp_BTN_confirm = findViewById(R.id.signUp_BTN_confirm);
        signUp_BTN_back = findViewById(R.id.signUp_BTN_back);
        signUp_EDT_username = findViewById(R.id.signUp_EDT_username);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private boolean commitSignUp(String email, String username, String avatar, ApproveCallback callback) {
        // Create newUser object
        NewUser newUser = new NewUser()
                .setEmail(email)
                .setAvatar(avatar)
                .setRole(getString(R.string.defaultRole))
                .setUsername(username);

        // Create MiniAppCommandBoundary object representing the request payload
        Map<String, Object> commandAttributes = new HashMap<>();
        commandAttributes.put("newUser", newUser);

        ObjectId objectId = new ObjectId();
        objectId.setSuperapp(getString(R.string.superapp));
        objectId.setId(getString(R.string.user_approver_key));

        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary()
                .setCommand("signUpUser")
                .setTargetObject(new TargetObject()
                        .setObjectId(objectId))
                .setInvokedBy(new InvokedBy()
                        .setUserId(new UserKey()
                                .setSuperapp(getString(R.string.superapp))
                                .setEmail(getString(R.string.miniapp_key_email))))
                .setCommandAttributes(commandAttributes);

        apiService.createMiniAppCommand(miniAppCommand, "investool").enqueue(new Callback<List<MiniAppCommandBoundary>>() {
            @Override
            public void onResponse(Call<List<MiniAppCommandBoundary>> call, Response<List<MiniAppCommandBoundary>> response) {
                boolean isSucceeded = false;
                if (response.isSuccessful()) {
                    List<MiniAppCommandBoundary> commandResponseList = response.body();
                    if (commandResponseList != null && !commandResponseList.isEmpty()) {
                        isSucceeded = true;
                        MiniAppCommandBoundary commandResponse = commandResponseList.get(0);
                        Log.d("CommandResponse", commandResponse.toString());
                    }
                    callback.onResult(isSucceeded);
                } else {
                    Log.e("CreateMiniAppCommand", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MiniAppCommandBoundary>> call, Throwable t) {
                Log.e("CreateMiniAppCommand", "Network error: " + t.getMessage());
                callback.onResult(false);
            }
        });

        return false;
    }

    private boolean isEmailValidFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}