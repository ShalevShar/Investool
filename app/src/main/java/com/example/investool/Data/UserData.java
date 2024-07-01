package com.example.investool.Data;

import com.example.investool.Logic.Currency;
import com.example.investool.Logic.Stock;
import com.example.investool.User.User;

import java.util.List;

public class UserData {
    private static UserData instance;

    private User user;
    private String objectId;
    private List<Currency> currencyList;
    private List<Stock> stockList;

    private UserData() {
    }

    public static synchronized UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}