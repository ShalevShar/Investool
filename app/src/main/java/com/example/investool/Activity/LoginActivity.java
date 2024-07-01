package com.example.investool.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.investool.Command.InvokedBy;
import com.example.investool.Command.MiniAppCommandBoundary;
import com.example.investool.Command.ObjectId;
import com.example.investool.Command.TargetObject;
import com.example.investool.Command.UserKey;
import com.example.investool.Logic.Currency;
import com.example.investool.Logic.Stock;
import com.example.investool.Network.ApiService;
import com.example.investool.Network.ApproveCallback;
import com.example.investool.Network.CurrencyCallback;
import com.example.investool.Network.StockCallback;
import com.example.investool.R;
import com.example.investool.User.User;
import com.example.investool.Data.UserData;
import com.example.investool.User.UserId;

import java.io.Serializable;
import java.util.ArrayList;
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


public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText login_EDT_email;
    private AppCompatTextView login_BTN_signUp;
    private AppCompatButton login_BTN_signIn;
    private ProgressBar login_PRG_loading;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initRetrofit();

        login_BTN_signIn.setOnClickListener(v -> {
            String email = login_EDT_email.getText().toString().trim();
            processLogin(email);
        });

        login_BTN_signUp.setOnClickListener(v -> {
            startSignUpActivity();
        });
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void processLogin(String email) {
        if (!email.isEmpty()) {
            if (isEmailValidFormat(email)) {
                isEmailExists(email, approve -> {
                    if (approve) {
                        showProgressBarLoading();
                        retrieveMarketData(email);
                        Toast.makeText(LoginActivity.this, "Login successful..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username " + email + " does not exist", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username format, must contain email!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Enter username!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressBarLoading() {
        login_BTN_signUp.setVisibility(View.INVISIBLE);
        login_BTN_signIn.setVisibility(View.INVISIBLE);
        login_PRG_loading.setVisibility(View.VISIBLE);
    }

    private boolean isEmailExists(String email, ApproveCallback callback) {
        ObjectId objectId = new ObjectId();
        objectId.setSuperapp(getString(R.string.superapp));
        objectId.setId(getString(R.string.user_approver_key));

        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary()
                .setCommand("loginUser")
                .setTargetObject(new TargetObject()
                        .setObjectId(objectId))
                .setInvokedBy(new InvokedBy()
                        .setUserId(new UserKey()
                                .setSuperapp(getString(R.string.superapp))
                                .setEmail(email)))
                .setCommandAttributes(new HashMap<>());


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


                        User user = extractUserDetails(commandResponse);
                        if (user != null) {

                            UserData.getInstance().setUser(user);
                        }
                    }
                    callback.onResult(isSucceeded);
                } else {

                    Log.e("CreateMiniAppCommand", "Error: " + response.code());
                    callback.onResult(false);
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

    private User extractUserDetails(MiniAppCommandBoundary commandResponse) {
        Map<String, Object> commandAttributes = commandResponse.getCommandAttributes();
        if (commandAttributes != null && commandAttributes.containsKey("user")) {

            Map<String, Object> userAttributes = (Map<String, Object>) commandAttributes.get("user");


            String userRole = (String) userAttributes.get("role");
            String username = (String) userAttributes.get("username");
            String avatar = (String) userAttributes.get("avatar");
            Map<String, String> userIdMap = (Map<String, String>) userAttributes.get("userId");
            String userEmail = userIdMap.get("email");


            String objectId = "default";
            if (commandAttributes.containsKey("ObjectId")) {
                Map<String, Object> objectIdMap = (Map<String, Object>) commandAttributes.get("ObjectId");
                if (objectIdMap.containsKey("objectId")) {
                    Map<String, String> objectIdValueMap = (Map<String, String>) objectIdMap.get("objectId");
                    if (objectIdValueMap != null) {
                        objectId = objectIdValueMap.get("id");
                    }
                }
            }


            List<Currency> currencyList = new ArrayList<>();
            List<Stock> stockList = new ArrayList<>();


            List<Map<String, Object>> objectDetailsList = null;
            if (commandAttributes.containsKey("ObjectId")) {
                Map<String, Object> objectIdMap = (Map<String, Object>) commandAttributes.get("ObjectId");
                if (objectIdMap.containsKey("objectDetails")) {
                    Object detailsObject = objectIdMap.get("objectDetails");
                    if (detailsObject instanceof Map) {

                        objectDetailsList = new ArrayList<>();
                        objectDetailsList.add((Map<String, Object>) detailsObject);
                    }
                }
            }

            if (objectDetailsList != null) {
                for (Map<String, Object> detailMap : objectDetailsList) {
                    for (Map.Entry<String, Object> entry : detailMap.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();


                        if (key.startsWith("Currency_")) {

                            Map<String, Object> currencyDetails = (Map<String, Object>) value;
                            Currency currency = new Currency(
                                    (String) currencyDetails.get("name"),
                                    (double) currencyDetails.get("intradayBid"),
                                    (double) currencyDetails.get("intradayAsk")
                            );
                            currencyList.add(currency);
                        }

                        else if (key.startsWith("Stock_")) {

                            Map<String, Object> stockDetails = (Map<String, Object>) value;
                            Stock stock = new Stock(
                                    (String) stockDetails.get("name"),
                                    (double) stockDetails.get("open"),
                                    (double) stockDetails.get("close")
                            );
                            stockList.add(stock);
                        }
                    }
                }
            }



            UserId userId = new UserId();
            userId.setSuperapp(userIdMap.get("superapp"));
            userId.setEmail(userEmail);

            User user = new User(username, avatar, userId, userRole);


            UserData.getInstance().setUser(user);
            UserData.getInstance().setObjectId(objectId);

            UserData.getInstance().setCurrencyList(currencyList);
            UserData.getInstance().setStockList(stockList);

            return user;
        } else {
            Log.e("CreateMiniAppCommand", "User not found in command attributes");
            return null;
        }
    }



    private boolean isEmailValidFormat(String email) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";


        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    private void findViews() {
        login_EDT_email = findViewById(R.id.login_EDT_email);
        login_BTN_signIn = findViewById(R.id.login_BTN_signIn);
        login_BTN_signUp = findViewById(R.id.login_BTN_signUp);
        login_PRG_loading = findViewById(R.id.login_PRG_loading);
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }


    private void retrieveMarketData(String email) {
        retrieveCurrencies(email, "currency", currencyData -> {
            retrieveStocks(email, "stocks", stocksData -> {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("currenciesData", (Serializable) currencyData);
                intent.putExtra("stocksData", (Serializable) stocksData);
                startActivity(intent);
                finish();
            });
        });
    }

    private void retrieveCurrencies(String email, String miniapp, CurrencyCallback callback) {
        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary()
                .setCommand("getCurrentCurrencies")
                .setTargetObject(new TargetObject()
                        .setObjectId(new ObjectId()
                                .setSuperapp(getString(R.string.superapp))
                                .setId(getString(R.string.user_approver_key))))
                .setInvokedBy(new InvokedBy()
                        .setUserId(new UserKey()
                                .setSuperapp(getString(R.string.superapp))
                                .setEmail(email)))
                .setCommandAttributes(new HashMap<>());
        apiService.createMiniAppCommand(miniAppCommand, miniapp).enqueue(new Callback<List<MiniAppCommandBoundary>>() {
            @Override
            public void onResponse(Call<List<MiniAppCommandBoundary>> call, Response<List<MiniAppCommandBoundary>> response) {
                if (response.isSuccessful()) {
                    List<MiniAppCommandBoundary> commandResponseList = response.body();
                    if (commandResponseList != null && !commandResponseList.isEmpty()) {
                        MiniAppCommandBoundary commandResponse = commandResponseList.get(0);
                        Log.d("CommandResponse", commandResponse.toString());
                        Map<String, Object> commandAttributes = commandResponse.getCommandAttributes();
                        if (commandAttributes != null && commandAttributes.containsKey("currency")) {
                            Map<String, Double> currencyData = (Map<String, Double>) commandAttributes.get("currency");
                            callback.onCurrencyRetrieved(currencyData);
                        }
                    } else {

                        Log.e("CreateMiniAppCommand", "Empty response body");
                    }
                } else {

                    Log.e("CreateMiniAppCommand", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MiniAppCommandBoundary>> call, Throwable t) {

                Log.e("CreateMiniAppCommand", "Network error: " + t.getMessage());
            }
        });
    }


    private void retrieveStocks(String email, String miniapp, StockCallback callback) {

        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary()
                .setCommand("getCurrentStocks")
                .setTargetObject(new TargetObject()
                        .setObjectId(new ObjectId()
                                .setSuperapp("2024a.omri.chen.yosef")
                                .setId(getString(R.string.user_approver_key))))
                .setInvokedBy(new InvokedBy()
                        .setUserId(new UserKey()
                                .setSuperapp(getString(R.string.superapp))
                                .setEmail(email)))
                .setCommandAttributes(new HashMap<>());

        apiService.createMiniAppCommand(miniAppCommand, miniapp).enqueue(new Callback<List<MiniAppCommandBoundary>>() {
            @Override
            public void onResponse(Call<List<MiniAppCommandBoundary>> call, Response<List<MiniAppCommandBoundary>> response) {
                if (response.isSuccessful()) {
                    List<MiniAppCommandBoundary> commandResponseList = response.body();
                    if (commandResponseList != null && !commandResponseList.isEmpty()) {
                        MiniAppCommandBoundary commandResponse = commandResponseList.get(0);
                        Log.d("CommandResponse", commandResponse.toString());
                        Map<String, Object> commandAttributes = commandResponse.getCommandAttributes();
                        if (commandAttributes != null && commandAttributes.containsKey("stock")) {
                            Map<String, Map<String, Object>> stockData = (Map<String, Map<String, Object>>) commandAttributes.get("stock");
                            callback.onStocksRetrieved(stockData);
                        }
                    } else {
                        Log.e("CreateMiniAppCommand", "Empty response body");
                    }
                } else {
                    Log.e("CreateMiniAppCommand", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MiniAppCommandBoundary>> call, Throwable t) {
                Log.e("CreateMiniAppCommand", "Network error: " + t.getMessage());
            }
        });
    }


}
