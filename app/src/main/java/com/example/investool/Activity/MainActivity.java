package com.example.investool.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.investool.Command.InvokedBy;
import com.example.investool.Command.MiniAppCommandBoundary;
import com.example.investool.Command.ObjectId;
import com.example.investool.Command.TargetObject;
import com.example.investool.Command.UserKey;
import com.example.investool.View.FavoriteAdapter;
import com.example.investool.Network.ApiService;
import com.example.investool.Dialog.UserMenuDialog;
import com.example.investool.Logic.Commodity;
import com.example.investool.Logic.Cryptocurrency;
import com.example.investool.Logic.Currency;
import com.example.investool.Network.ApproveCallback;
import com.example.investool.R;
import com.example.investool.Logic.Stock;
import com.example.investool.Data.UserData;

import com.example.investool.View.StockAdapter;
import com.example.investool.View.CommodityAdapter;
import com.example.investool.View.CryptocurrencyAdapter;
import com.example.investool.View.CurrencyAdapter;
import com.google.android.material.textview.MaterialTextView;
import com.example.investool.Animation.DateTimeAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CurrencyAdapter.OnItemClickListener, StockAdapter.OnItemClickListener, FavoriteAdapter.OnItemClickListener, FavoriteAdapter.OnItemRemovedListener{

    private MaterialTextView main_LBL_dateTime;
    private AppCompatImageButton main_BTN_user;
    private RecyclerView main_RCV_stocks;
    private RecyclerView main_RCV_commodity;
    private RecyclerView main_RCV_cryptocurrency;
    private RecyclerView main_RCV_currency;
    private CurrencyAdapter currencyAdapter;
    private StockAdapter stockAdapter;
    private RecyclerView main_fav_1;
    private FavoriteAdapter favoriteAdapter;
    private DateTimeAnimation dateTimeAnimation;
    private Set<Currency> favoriteItemListCurrency = new HashSet<>();
    private Set<Stock> favoriteItemListStock = new HashSet<>();
    private List<Currency> currencyList;
    private List<Stock> stockList;
    private ApiService apiService;
    private Intent intent;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = UserData.getInstance().getUser().getUserId().getEmail();

        intent = getIntent();

        findViews();
        initRetrofit();
        loadData();

        loadFavorites();

        loadDateTimeAnimation();


        main_BTN_user.setOnClickListener(v -> showUserMenuDialog());
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void showUserMenuDialog() {

        UserMenuDialog dialog = new UserMenuDialog(MainActivity.this, UserData.getInstance().getUser());
        dialog.show();


        dialog.findViewById(R.id.btn_logout).setOnClickListener(v1 -> {
            startLoginActivity();

            Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }


    private void loadDateTimeAnimation() {
        dateTimeAnimation = new DateTimeAnimation(this, main_LBL_dateTime);
        dateTimeAnimation.updateDateTime();
        dateTimeAnimation.startAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dateTimeAnimation.stopAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dateTimeAnimation.stopAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dateTimeAnimation.startAnimation();
    }

    private void loadData() {
        loadStocks();
        loadCommodity();
        loadCryptocurrency();
        loadCurrency();
    }

    private void findViews() {
        main_LBL_dateTime = findViewById(R.id.main_LBL_dateTime);
        main_BTN_user = findViewById(R.id.main_BTN_user);
        main_RCV_stocks = findViewById(R.id.main_RCV_stocks);
        main_RCV_commodity = findViewById(R.id.main_RCV_commodity);
        main_RCV_cryptocurrency = findViewById(R.id.main_RCV_cryptocurrency);
        main_RCV_currency = findViewById(R.id.main_RCV_currency);
        main_fav_1 = findViewById(R.id.main_fav_1);

    }

    private void loadStocks() {
        stockList = new ArrayList<>();

        if (intent != null && intent.hasExtra("stocksData")) {
            Map<String, Map<String, Object>> stocksData = (Map<String, Map<String, Object>>) intent.getSerializableExtra("stocksData");
            Log.d("MainActivity", "MAP Stocks data received: " + stocksData.toString());
            for (Map.Entry<String, Map<String, Object>> entry : stocksData.entrySet()) {
                String name = entry.getKey();
                Map<String, Object> stockData = entry.getValue();
                double open = (double) stockData.get("open");
                double close = (double) stockData.get("close");

                Stock stock = new Stock(name, open, close);
                stockList.add(stock);
            }
            Log.d("MainActivity", "Stocks data received: " + stockList);
        } else {
            Log.d("MainActivity", "No stocks data received");
            stockList = Stock.getHardcodedStocks();
        }

        stockAdapter = new StockAdapter(this, stockList, favoriteItemListStock);
        main_RCV_stocks.setAdapter(stockAdapter);
        main_RCV_stocks.setLayoutManager(new LinearLayoutManager(this));
        stockAdapter.setOnItemClickListener(this);
    }

    private void loadCommodity() {

        List<Commodity> commodityList = Commodity.getHardcodedCommodities();
        CommodityAdapter commodityAdapter = new CommodityAdapter(this, commodityList);
        main_RCV_commodity.setAdapter(commodityAdapter);
        main_RCV_commodity.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadCryptocurrency() {

        List<Cryptocurrency> cryptocurrencyList = Cryptocurrency.getHardcodedCryptocurrencies();

        CryptocurrencyAdapter cryptocurrencyAdapter = new CryptocurrencyAdapter(this, cryptocurrencyList);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        main_RCV_cryptocurrency.setLayoutManager(verticalLayoutManager);

        // Set adapter
        main_RCV_cryptocurrency.setAdapter(cryptocurrencyAdapter);
    }

    private void loadCurrency() {
        currencyList = new ArrayList<>();
        if (intent != null && intent.hasExtra("currenciesData")) {
            Map<String, Double> currencyData = (Map<String, Double>) intent.getSerializableExtra("currenciesData");
            Log.d("MainActivity", "Currency data received: " + currencyData.toString());

            for (Map.Entry<String, Double> entry : currencyData.entrySet()) {
                String currencyCode = entry.getKey();
                double rate = entry.getValue();

                double minRandomRate = rate - 0.01 * rate;
                double maxRandomRate = rate + 0.10 * rate;
                double randomRate = minRandomRate + (Math.random() * (maxRandomRate - minRandomRate));

                Currency currency = new Currency(currencyCode, rate, randomRate);
                currencyList.add(currency);
            }
        } else {
            Log.d("MainActivity", "No currency data received");
            currencyList = Currency.getHardcodedCurrencies();
        }

        currencyAdapter = new CurrencyAdapter(this, currencyList, favoriteItemListCurrency);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        main_RCV_currency.setLayoutManager(verticalLayoutManager);

        main_RCV_currency.setAdapter(currencyAdapter);

        currencyAdapter.setOnItemClickListener(this);
    }
    private void startLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadFavorites() {
        if (favoriteAdapter == null) {
            favoriteAdapter = new FavoriteAdapter(this, this); // Pass the activity and itemsFromDatabase
            favoriteAdapter.setItemRemovedListener(this); // Set the listener
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        main_fav_1.setLayoutManager(layoutManager);
        main_fav_1.setAdapter(favoriteAdapter);

        List<Currency> currencyList = UserData.getInstance().getCurrencyList();
        if (currencyList != null) {
            for (Currency currency : currencyList) {
                favoriteAdapter.addItem(currency);
            }
        }

        List<Stock> stockList = UserData.getInstance().getStockList();
        if (stockList != null) {
            for (Stock stock : stockList) {
                favoriteAdapter.addItem(stock);
            }
        }

        favoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddToFavoritesClicked(Object item) {
        if (item instanceof Currency) {
            Currency currency = (Currency) item;
            favoriteAdapter.addItem(currency);
            favoriteAdapter.notifyDataSetChanged();
        } else if (item instanceof Stock) {
            Stock stock = (Stock) item;
            favoriteAdapter.addItem(stock);
            favoriteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRemoveItemClicked(Object item) {
        if (item instanceof Currency) {
            Currency currency = (Currency) item;
            favoriteAdapter.removeItem(currency);
            favoriteAdapter.notifyDataSetChanged();
            int position = currencyAdapter.getCurrencyPosition(currency);

            if (position != -1) {
                currencyAdapter.notifyItemChanged(position);
            }
        } else if (item instanceof Stock) {
            Stock stock = (Stock) item;
            favoriteAdapter.removeItem(stock);
            favoriteAdapter.notifyDataSetChanged();

            int position = stockAdapter.getStockPosition(stock);

            if (position != -1) {
                stockAdapter.notifyItemChanged(position);
            }
        }
    }
    @Override
    public void onAddToFavoritesClicked(Currency currency) {
        favoriteAdapter.addItem(currency);
        favoriteAdapter.notifyDataSetChanged();

        addItemToFavorites(email,"Currency_"+currency.getName(), currency, approve -> {
            if (approve) {
                Toast.makeText(MainActivity.this, "Currency " + currency.getName() + " added to favorite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error adding currency to favorites", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onRemoveItemClicked(Currency currency) {
        favoriteAdapter.removeItem(currency);
        favoriteAdapter.notifyDataSetChanged();

        int position = currencyAdapter.getCurrencyPosition(currency);

        if (position != -1) {
            currencyAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onAddToFavoritesClicked(Stock stock) {
        favoriteAdapter.addItem(stock);
        favoriteAdapter.notifyDataSetChanged();
        addItemToFavorites(email,"Stock_"+stock.getName(), stock, approve -> {
            if (approve) {
                Toast.makeText(MainActivity.this, "Stock " + stock.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Error adding stock to favorites", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onRemoveItemClicked(Stock stock) {
        favoriteAdapter.removeItem(stock);
        favoriteAdapter.notifyDataSetChanged();

        int position = stockAdapter.getStockPosition(stock);

        if (position != -1) {
            stockAdapter.notifyItemChanged(position);
        }
    }


    private boolean addItemToFavorites(String email, String itemKey, Object item, ApproveCallback callback) {
        Map<String, Object> commandAttributes = new HashMap<>();
        commandAttributes.put(itemKey, item);

        ObjectId objectId = new ObjectId();
        objectId.setSuperapp(getString(R.string.superapp));
        objectId.setId(UserData.getInstance().getObjectId());

        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary()
                .setCommand("addToFavorites")
                .setTargetObject(new TargetObject()
                        .setObjectId(objectId))
                .setInvokedBy(new InvokedBy()
                        .setUserId(new UserKey()
                                .setSuperapp(getString(R.string.superapp))
                                .setEmail(email)))
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


    private boolean deleteItemFromFavorites(String email, String itemKey, Object item, ApproveCallback callback) {
        Map<String, Object> commandAttributes = new HashMap<>();
        commandAttributes.put(itemKey, item);

        ObjectId objectId = new ObjectId();
        objectId.setSuperapp(getString(R.string.superapp));
        objectId.setId(UserData.getInstance().getObjectId());

        MiniAppCommandBoundary miniAppCommand = new MiniAppCommandBoundary()
                .setCommand("deleteFromFavorites")
                .setTargetObject(new TargetObject()
                        .setObjectId(objectId))
                .setInvokedBy(new InvokedBy()
                        .setUserId(new UserKey()
                                .setSuperapp(getString(R.string.superapp))
                                .setEmail(email)))
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

    @Override
    public void onItemRemoved(Object item) {
        if (item instanceof Currency) {
            deleteItemFromFavorites(email, "Currency_" + ((Currency) item).getName(), (Currency) item, approve -> {
                if (approve) {
                    Toast.makeText(MainActivity.this, "Currency " + ((Currency) item).getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error removing currency from favorites", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (item instanceof Stock) {
            deleteItemFromFavorites(email, "Stock_" + ((Stock) item).getName(), (Stock) item, approve -> {
                if (approve) {
                    Toast.makeText(MainActivity.this, "Stock " + ((Stock) item).getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error removing stock from favorites", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
